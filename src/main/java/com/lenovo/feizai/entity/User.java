package com.lenovo.feizai.entity;

import lombok.Data;

@Data
public class User {
    int id;
    String username;
    String password;
    String role;
}
