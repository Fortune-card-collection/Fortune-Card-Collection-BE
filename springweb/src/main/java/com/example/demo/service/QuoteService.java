package com.example.demo.service;

import com.example.demo.domain.quote.Quote;
import com.example.demo.domain.quote.QuoteRepository;
import com.example.demo.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;

    @Transactional(readOnly = true)
    public List<Quote> getRandomQuotes() {
        return quoteRepository.findRandomQuotes(3);
    }

    @Transactional(readOnly = true)
    public Quote processSelection(Long quoteId, User user) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("해당 명언을 찾을 수 없습니다."));

        log.info("사용자 [{}]님이 명언 ID [{}]번을 선택했습니다: {}",
                user.getNickname(), quote.getId(), quote.getText());

        return quote;
    }

    @Transactional(readOnly = true)
    public Quote getQuote(Long quoteId) {
        return quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("명언이 존재하지 않습니다."));
    }
}