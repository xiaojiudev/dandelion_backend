package com.dandelion.backend.payloads.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartDTO {
    
    private Long userId;
    private Boolean status;
    private List<CartDetailDTO> items;

}
