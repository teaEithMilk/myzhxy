package com.tu.pojo;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
