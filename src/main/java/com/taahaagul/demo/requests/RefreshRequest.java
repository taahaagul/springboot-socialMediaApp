package com.taahaagul.demo.requests;

import lombok.Data;

@Data
public class RefreshRequest {

    Long userId;
    String refreshToken;
}
