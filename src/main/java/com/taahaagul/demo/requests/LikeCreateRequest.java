package com.taahaagul.demo.requests;

import lombok.Data;

@Data
public class LikeCreateRequest {
    Long userId;
    Long postId;
}
