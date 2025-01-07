package com.zxb.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;
    private String code;
    private String token;

}
