package com.dandelion.backend.payloads;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OptionBody {

    private Long id;

    private String name;

    private List<String> options;
}
