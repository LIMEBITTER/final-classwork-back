package com.zxb.entity.dto;

import com.zxb.entity.Files;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FormDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private Integer priority;
    private String order_id;
    private String complaint;
    private Integer creator_id;
    private Integer related_person;
    private String username;
}
