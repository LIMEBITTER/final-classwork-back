package com.zxb.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderAHistoryDto {

    private Integer id;
    private String title;
    private String orderId;
    private String currentNode;
    private String operatorName;
    private Integer priority;
    private Integer state;
    private Integer creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
