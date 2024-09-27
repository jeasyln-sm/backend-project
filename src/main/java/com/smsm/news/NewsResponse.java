package com.smsm.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NewsResponse {
    @JsonProperty("items")
    private List<NewsItem> items; // 뉴스 항목 리스트
}
