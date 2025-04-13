package org.loop.troop.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.loop.troop.entity.BookStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private UUID id;

    @NotBlank(message = "{book.name.required}")
    private String name;

    @NotNull(message = "{book.authorId.required}")
    private UUID authorId;

    private String authorName;

    @DecimalMin(value = "0.0", inclusive = true, message = "{book.rating.min}")
    @DecimalMax(value = "5.0", inclusive = true, message = "{book.rating.max}")
    private double rating;

    private long bookmarked;

    private String lastUpdated;

    private BookStatus status;

    private String wordCount;

    @NotBlank(message = "{book.imageUrl.required}")
    private String imageUrl;

    @NotNull(message = "{book.clubId.required}")
    private UUID clubId;

    private List<String> genres = new ArrayList<>();

    private List<String> tags = new ArrayList<>();
}

