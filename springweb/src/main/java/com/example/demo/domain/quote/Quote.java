// domain/quote/Quote.java
package com.example.demo.domain.quote;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quote_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    // 필요하다면 작가(author) 등의 필드를 추가할 수 있습니다.
    // private String author;
}