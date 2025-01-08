package com.zxb.entity.dto;

import lombok.Data;

@Data
public class AuditFormDto {

    private String orderId;
    private String operatorName;
    private String remark;
    private Integer state;
}
