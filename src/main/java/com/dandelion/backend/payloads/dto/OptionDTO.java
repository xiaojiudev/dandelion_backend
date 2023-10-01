package com.dandelion.backend.payloads.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OptionDTO {

    private Long id;

    private String value;
}
