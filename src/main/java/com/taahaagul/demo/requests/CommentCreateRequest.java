package com.taahaagul.demo.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {
    String text;
    Long postId;
    Long userId;
}
