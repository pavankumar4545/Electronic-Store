package com.lcwd.electronic.store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponeMessage {

    private String fileName;
    private String message;
    private boolean success;
    private HttpStatus httpStatus;
}
