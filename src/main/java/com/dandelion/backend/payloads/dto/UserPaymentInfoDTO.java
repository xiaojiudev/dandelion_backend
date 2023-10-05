package com.dandelion.backend.payloads.dto;

import java.util.Date;

public class UserPaymentInfoDTO {

    private Long id;
    private String name;
    private String provider;
    private String accountNo;
    private Date expiryDate;
    private Boolean isDefault;

}
