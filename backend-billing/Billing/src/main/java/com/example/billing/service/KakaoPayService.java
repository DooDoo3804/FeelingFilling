package com.example.billing.service;

import com.example.billing.data.billingDB.entity.Action;
import com.example.billing.data.billingDB.entity.KakaoOrder;
import com.example.billing.data.billingDB.entity.User;
import com.example.billing.data.billingDB.repository.ActionRepository;
import com.example.billing.data.billingDB.repository.KakaoOrderRepository;
import com.example.billing.data.dto.KakaoApproveDTO;
import com.example.billing.data.dto.KakaoOrderDTO;
import com.example.billing.data.dto.KakaoReadyDTO;
import com.example.billing.data.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

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
        parameters.add("approval_url", "http://localhost:8080/billing/subscription/success?orderId="+newOrder.getOrderId()); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url

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
        kakaoOrder.getActions().add(action);
        return kakaoApproveDTO;
    }
}
