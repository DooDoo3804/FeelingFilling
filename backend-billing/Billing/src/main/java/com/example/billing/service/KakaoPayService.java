package com.example.billing.service;

import com.example.billing.data.billingDB.entity.*;
import com.example.billing.data.billingDB.repository.*;
import com.example.billing.data.dto.*;
import com.example.billing.data.loggingDB.document.CancellationLogDocument;
import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.InactiveLogDocumnet;
import com.example.billing.data.loggingDB.document.KakaoPayApproveLogDocument;
import com.example.billing.data.loggingDB.repository.CancellationLogRepository;
import com.example.billing.data.loggingDB.repository.DepositLogRepository;
import com.example.billing.data.loggingDB.repository.InactiveLogRepository;
import com.example.billing.data.loggingDB.repository.KakaoPayApproveLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {
    @Value("${CID}")
    private String cid; // 가맹점 테스트 코드
    private KakaoReadyDTO kakaoReadyDTO;
    private KakaoApproveDTO kakaoApproveDTO;
    @Value("${KAKAO_ADMIN_KEY}")
    private String KAKAO_ADMIN_KEY;

    private final KakaoOrderRepository kakaoOrderRepository;
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;
    private final DepositRepository depositRepository;
    private final CancellationRepository cancellationRepository;
    private final KakaoPayApproveLogRepository kakaoPayApproveLogRepository;
    private final DepositLogRepository depositLogRepository;
    private final CancellationLogRepository cancellationLogRepository;

    private final InactiveLogRepository inactiveLogRepository;

    public KakaoReadyDTO kakaoPayReady(UserDTO userDTO) {
        User user = User.builder()
                .userId(userDTO.getUserId())
                .serviceName(userDTO.getServiceName())
                .serviceUserId(userDTO.getServiceUserId())
                .build();
        KakaoOrder newOrder = KakaoOrder.builder()
                .user(user)
                .build();

        //id를 db에서 받아온다.
        newOrder = kakaoOrderRepository.save(newOrder);

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", String.valueOf(newOrder.getOrderId()));
        parameters.add("partner_user_id", String.valueOf(newOrder.getUser().getUserId()));
        parameters.add("item_name", "FeelingFilling 적금 신청");
        parameters.add("quantity", "1");
        parameters.add("total_amount", "0");
        parameters.add("vat_amount", "0");
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://13.124.31.137:8702/billing/subscription/success?orderId="+newOrder.getOrderId()); // 성공 시 redirect url
        parameters.add("cancel_url", "http://13.124.31.137:8702/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://13.124.31.137:8702/payment/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReadyDTO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyDTO.class);
        newOrder.setTid(kakaoReadyDTO.getTid());
        return kakaoReadyDTO;
    }

    public KakaoApproveDTO kakaoPayApprove(int orderId, String pgToken) {

        KakaoOrder kakaoOrder = kakaoOrderRepository.getKakaoOrderByOrderId(orderId);
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoOrder.getTid());
        parameters.add("partner_order_id", String.valueOf(kakaoOrder.getOrderId()));
        parameters.add("partner_user_id", String.valueOf(kakaoOrder.getUser().getUserId()));
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoApproveDTO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveDTO.class);
        kakaoOrder.getUser().setSid(kakaoApproveDTO.getSid());
        Action action = new Action();
        action.setAid(kakaoApproveDTO.getAid());
        actionRepository.save(action);

        kakaoOrder.getActions().add(action);
        KakaoPayApproveLogDocument kakaoPayApproveDocument = KakaoPayApproveLogDocument.builder()
                .tid(kakaoOrder.getTid())
                .aid(action.getAid())
                .sid(kakaoOrder.getUser().getSid())
                .orderId(kakaoOrder.getOrderId())
                .status("success")
                .userId(kakaoOrder.getUser().getUserId())
                .serviceName(kakaoOrder.getUser().getServiceName())
                .serviceUserId(kakaoOrder.getUser().getServiceUserId())
                .build();
        kakaoPayApproveLogRepository.save(kakaoPayApproveDocument);
        return kakaoApproveDTO;
    }

    public boolean kakaoPaySubscription(ServiceUserAndAmountDTO serviceUserAndAmountDTO){
        User user = userRepository.findUserByServiceNameAndServiceUserId(serviceUserAndAmountDTO.getServiceName(), serviceUserAndAmountDTO.getServiceUserId());
        int amount = serviceUserAndAmountDTO.getAmount();

        KakaoOrder newOrder = new KakaoOrder();
        newOrder.setUser(user);

        //id를 db에서 받아온다.
        newOrder = kakaoOrderRepository.save(newOrder);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("sid", user.getSid());
        parameters.add("partner_order_id", String.valueOf(newOrder.getOrderId()));
        parameters.add("partner_user_id", String.valueOf(newOrder.getUser().getUserId()));
        parameters.add("quantity", "1");
        parameters.add("total_amount", String.valueOf(amount));
        parameters.add("tax_free_amount", "0");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoApproveDTO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/subscription",
                requestEntity,
                KakaoApproveDTO.class);

        newOrder.setTid(kakaoApproveDTO.getTid());
        Action action = new Action();
        action.setAid(kakaoApproveDTO.getAid());
        action = actionRepository.save(action);

        //lazyloading 때문에 먼저 호출한 후 set을 적용해야 한다..
        List<Action> actions = newOrder.getActions();
        actions.add(action);
        user.setPoint(user.getPoint() + amount);

        Deposit newDeposit = new Deposit("KakaoPay", amount);
        newDeposit = depositRepository.save(newDeposit);
        user.getDeposit().add(newDeposit);

        DepositLogDocument depositLog = DepositLogDocument.builder()
                .userId(user.getUserId())
                .serviceName(user.getServiceName())
                .serviceUserId(user.getServiceUserId())
                .sid(user.getSid())
                .balance(user.getPoint())
                .status("Success")
                .orderId(newOrder.getOrderId())
                .tid(newOrder.getTid())
                .aid(action.getAid())
                .depositId(newDeposit.getDepositId())
                .depositMethod(newDeposit.getDepositMethod())
                .depositAmount(newDeposit.getDepositAmount())
                .build();

        depositLogRepository.save(depositLog);

        return true;
    }

    public KakaoInactiveDTO kakaoPayInactivate(ServiceUserDTO serviceUserDTO){
        User user = userRepository.findUserByServiceNameAndServiceUserId(serviceUserDTO.getServiceName(), serviceUserDTO.getServiceUserId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("sid", user.getSid());

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        //RestTemplate가 snake_case도 camelCase로 자동 mapping한다.
        KakaoInactiveDTO kakaoInactiveDTO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/manage/subscription/inactive",
                requestEntity,
                KakaoInactiveDTO.class);

        user.setSid("");

        InactiveLogDocumnet inactiveLog = InactiveLogDocumnet.builder()
                .userId(user.getUserId())
                .serviceName(user.getServiceName())
                .serviceUserId(user.getServiceUserId())
                .status(kakaoInactiveDTO.getStatus())
                .createdAt(kakaoInactiveDTO.getCreatedAt())
                .inactivatedAt(kakaoInactiveDTO.getInactivatedAt())
                .lastApprovedAt(kakaoInactiveDTO.getLastApprovedAt())
                .build();

        inactiveLogRepository.save(inactiveLog);

        return kakaoInactiveDTO;
    }

    public KakaoCancelDTO kakaoPayCancel(CancelDepositDTO cancelDepositDTO){
        User user = userRepository.findUserByServiceNameAndServiceUserId(cancelDepositDTO.getServiceName(), cancelDepositDTO.getServiceUserId());
        int amount = cancelDepositDTO.getAmount();
        KakaoOrder kakaoOrder = kakaoOrderRepository.getKakaoOrderByOrderId(cancelDepositDTO.getOrderId());
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoOrder.getTid());
        parameters.add("cancel_amount", String.valueOf(amount));
        parameters.add("cancel_tax_free_amount", String.valueOf(amount));


        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoCancelDTO kakaoCancelDTO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoCancelDTO.class);

        Action action = new Action();
        action.setAid(kakaoCancelDTO.getAid());
        action = actionRepository.save(action);

        //lazyloading 때문에 먼저 호출한 후 set을 적용해야 한다..
        List<Action> actions = kakaoOrder.getActions();
        actions.add(action);
        user.setPoint(user.getPoint() - amount);

        Cancellation cancellation = new Cancellation("KakaoPay", amount);
        cancellation = cancellationRepository.save(cancellation);
        user.getCancellations().add(cancellation);

        CancellationLogDocument cancellationLog = CancellationLogDocument.builder()
                .userId(user.getUserId())
                .serviceName(user.getServiceName())
                .serviceUserId(user.getServiceUserId())
                .sid(user.getSid())
                .balance(user.getPoint())
                .status("Success")
                .orderId(kakaoOrder.getOrderId())
                .tid(kakaoOrder.getTid())
                .aid(action.getAid())
                .cancellationId(cancellation.getCancellationId())
                .cancellationMethod(cancellation.getCancellationMethod())
                .cancellationAmount(cancellation.getCancellationAmount())
                .build();

        cancellationLogRepository.save(cancellationLog);

        return kakaoCancelDTO;
    }

    public KakaoPayCheckDTO kakaoPayCheck(ServiceUserDTO serviceUserDTO){
        User user = userRepository.findUserByServiceNameAndServiceUserId(serviceUserDTO.getServiceName(), serviceUserDTO.getServiceUserId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_ADMIN_KEY);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("sid", user.getSid());

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        //RestTemplate가 snake_case도 camelCase로 자동 mapping한다.
        KakaoPayCheckDTO kakaoPayCheckDTO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/manage/subscription/status",
                requestEntity,
                KakaoPayCheckDTO.class);

        return kakaoPayCheckDTO;
    }
}
