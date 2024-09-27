package com.smsm.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RequiredArgsConstructor
@Controller
public class NewsController {

    private final NewsService newsService;

        @GetMapping("/news")
        public String getEconomicNews(Model model) {
            NewsResponse newsResponse = newsService.fetchLatestEconomicNews();

            // newsResponse가 null인 경우 처리
            if (newsResponse == null || newsResponse.getItems() == null) {
                model.addAttribute("error", "뉴스를 가져오는 데 실패했습니다.");
                return "error"; // 에러 페이지 또는 적절한 페이지로 이동
            }

            // HTML 태그 제거 및 제목 정리
            List<NewsItem> cleanedNewsList = newsResponse.getItems().stream()
                    .map(item -> {
                        item.setTitle(item.getCleanTitle()); // HTML 엔티티 변환
                        return item;
                    })
                    .toList();

            model.addAttribute("newsList", cleanedNewsList);
            return "news/news"; // Thymeleaf 템플릿 이름
        }
    }
