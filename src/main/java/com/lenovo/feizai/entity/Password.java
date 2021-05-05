package com.lenovo.feizai.entity;

import lombok.Data;

@Data
public class Password {
    int id;
    String username;
    String oldpassword;
    String newpassword;
    String role;
}
