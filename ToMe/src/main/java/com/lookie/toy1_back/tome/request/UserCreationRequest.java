package com.lookie.toy1_back.tome.request;

import lombok.Data;

@Data
public class UserCreationRequest {
    private String username;
    private String password;
}
