package com.smsm.news;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class NewsService {

    @Value("${naver.api.client.id}")
    private String clientId;

    @Value("${naver.api.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    public NewsResponse fetchLatestEconomicNews() {
        String url = "https://openapi.naver.com/v1/search/news.json?query=경제&display=10&start=1&sort=sim";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<NewsResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, NewsResponse.class);

        return response.getBody();
    }

    public String cleanHtml(String html) {
        // HTML 태그를 제거하고 엔티티를 복원
        String cleanText = html.replaceAll("<[^>]*>", ""); // 태그 제거
        return StringEscapeUtils.unescapeHtml4(cleanText); // HTML 엔티티 복원
    }
}
