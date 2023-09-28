package com.dandelion.backend.exceptions;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StandardError {
    private Date timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
