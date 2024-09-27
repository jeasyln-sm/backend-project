package com.smsm.news;

import lombok.Data;
import org.apache.commons.text.StringEscapeUtils;

@Data
public class NewsItem {
    private String title;         // 뉴스 제목
    private String link;          // 뉴스 링크
    private String originallink;  // 원본 링크
    private String description;    // 설명
    private String pubDate;        // 발행일


    public String getCleanTitle() {
        return StringEscapeUtils.unescapeHtml4(title); // HTML 엔티티 변환
    }
}
