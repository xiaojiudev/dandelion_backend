package com.dandelion.backend.payloads;

import com.dandelion.backend.payloads.dto.ProductDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {

    private List<ProductDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
