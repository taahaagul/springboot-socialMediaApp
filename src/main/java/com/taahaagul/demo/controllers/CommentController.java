package com.taahaagul.demo.controllers;

import com.taahaagul.demo.entities.Comment;
import com.taahaagul.demo.requests.CommentCreateRequest;
import com.taahaagul.demo.requests.CommentUpdateRequest;
import com.taahaagul.demo.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComments(@RequestParam Optional<Long> postId,
                                        @RequestParam Optional<Long> userId){
            return commentService.getAllComments(postId, userId);
    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId) {
        return commentService.getOneCommentById(commentId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest newCommentRequest) {
        return commentService.createOneComment(newCommentRequest);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest updateComment) {
        return commentService.updateOneCommentById(commentId,updateComment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId) {
        commentService.deleteOneComment(commentId);
    }
}
