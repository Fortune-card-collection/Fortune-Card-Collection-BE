package com.example.demo.api;

import com.example.demo.domain.quote.Quote;
import com.example.demo.domain.user.User;
import com.example.demo.dto.QuoteSelectRequest;
import com.example.demo.service.QuoteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping("/random")
    public ResponseEntity<List<Quote>> getRandomQuotes() {
        List<Quote> randomQuotes = quoteService.getRandomQuotes();
        return ResponseEntity.ok(randomQuotes);
    }

    @PostMapping("/select")
    public ResponseEntity<?> selectQuote(
            @RequestBody QuoteSelectRequest request,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        try {
            Quote selectedQuote = quoteService.processSelection(request.getQuoteId(), user);
            return ResponseEntity.ok(selectedQuote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{quoteId}")
    public ResponseEntity<?> getQuote(@PathVariable Long quoteId) {
        try {
            Quote quote = quoteService.getQuote(quoteId);
            return ResponseEntity.ok(quote);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}