package com.lcwd.electronic.store.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductDto {

    private String id;

    private String title;

    private String about;

    private int cost;

    private int quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate addedDate;

    @JsonProperty("isLive")
    private boolean isLive;

    private boolean stock;

    private String imageName;

    private CategoryDto category;
}





