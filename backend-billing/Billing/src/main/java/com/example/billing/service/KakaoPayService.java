package com.example.billing.service;

import com.example.billing.data.billingDB.entity.Action;
import com.example.billing.data.billingDB.entity.Deposit;
import com.example.billing.data.billingDB.entity.KakaoOrder;
import com.example.billing.data.billingDB.entity.User;
import com.example.billing.data.billingDB.repository.ActionRepository;
import com.example.billing.data.billingDB.repository.DepositRepository;
import com.example.billing.data.billingDB.repository.KakaoOrderRepository;
import com.example.billing.data.billingDB.repository.UserRepository;
import com.example.billing.data.dto.KakaoApproveDTO;
import com.example.billing.data.dto.KakaoReadyDTO;
import com.example.billing.data.dto.ServiceUserAndAmountDTO;
import com.example.billing.data.dto.UserDTO;
import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.KakaoPayApproveLogDocument;
import com.example.billing.data.loggingDB.repository.DepositLogRepository;
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

    private final KakaoPayApproveLogRepository kakaoPayApproveLogRepository;

    private final DepositLogRepository depositLogRepository;
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

    public void kakaoPaySubscription(ServiceUserAndAmountDTO paySubscriptionDTO){
        User user = userRepository.findUserByServiceNameAndServiceUserId(paySubscriptionDTO.getServiceName(), paySubscriptionDTO.getServiceUserId());
        int amount = paySubscriptionDTO.getAmount();

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
    }
}
