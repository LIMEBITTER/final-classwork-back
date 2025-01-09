package com.zxb.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserOrderDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer allocUserId;
    private String orderId;
    private String operatorName;
    private String allocUserName;

}
