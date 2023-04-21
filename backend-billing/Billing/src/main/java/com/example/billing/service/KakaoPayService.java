package com.example.billing.service;

import com.example.billing.data.dto.KakaoReadyDTO;
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
    @Value(${CID})
    static final String cid; // 가맹점 테스트 코드
    static final String admin_Key = "${ADMIN_KEY}"; // 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요
    private KakaoReadyDTO kakaoReady;

    @Value("${KAKAO_ADMIN}")
    private final String KAKAO_ADMIN;

    public KakaoReadyDTO kakaoPayReady() {
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_ADMIN);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "subscription_order_id_1");
        parameters.add("partner_user_id", "subscription_user_id_1");
        parameters.add("item_name", "음악정기결제");
        parameters.add("quantity", "1");
        parameters.add("total_amount", "9900");
        parameters.add("vat_amount", "900");
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8080/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyDTO.class);

        System.out.println(kakaoReady);
        return kakaoReady;
    }

    public KakaoReadyDTO kakaoPayApprove() {
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "ad8b8c0f8031b6753789acc3decf76ca");
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "subscription_order_id_1");
        parameters.add("partner_user_id", "subscription_user_id_1");
        parameters.add("item_name", "음악정기결제");
        parameters.add("quantity", "1");
        parameters.add("total_amount", "9900");
        parameters.add("vat_amount", "900");
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8080/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyDTO.class);

        System.out.println(kakaoReady);
        return kakaoReady;
    }
}
