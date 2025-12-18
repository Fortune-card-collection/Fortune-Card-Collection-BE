// domain/quote/QuoteRepository.java
package com.example.demo.domain.quote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    // MySQL의 RAND() 함수를 사용하여 무작위 정렬 후 limit 개수만큼 가져옴
    @Query(value = "SELECT * FROM quotes ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Quote> findRandomQuotes(@Param("limit") int limit);
}