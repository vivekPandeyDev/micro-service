package org.loop.troop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private UUID authorId;

    private double rating = 0.0;

    private long bookmarked = 0;

    private LocalDateTime lastUpdated;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.ON_GOING;

    private String wordCount;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private UUID clubId;

    @ElementCollection
    private List<String> genres = new ArrayList<>();

    @ElementCollection
    private List<String> tags = new ArrayList<>();

}
