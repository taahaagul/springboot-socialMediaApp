package com.taahaagul.demo.services;

import com.taahaagul.demo.entities.Comment;
import com.taahaagul.demo.entities.Post;
import com.taahaagul.demo.entities.User;
import com.taahaagul.demo.repos.CommentRepository;
import com.taahaagul.demo.requests.CommentCreateRequest;
import com.taahaagul.demo.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    CommentRepository commentRepository;
    UserService userService;
    PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }


    public List<Comment> getAllComments(Optional<Long> postId, Optional<Long> userId) {
        if(postId.isPresent() && userId.isPresent()){
            return commentRepository.findByPostIdAndUserId(postId.get(), userId.get());
        } else if (userId.isPresent()) {
            return commentRepository.findByUserId(userId);
        } else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId);
        } else
            return commentRepository.findAll();
    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }


    public Comment createOneComment(CommentCreateRequest newCommentRequest) {
        User user = userService.getOneUserById(newCommentRequest.getUserId());
        Post post = postService.getOnePostById(newCommentRequest.getPostId());

        if(user == null || post == null )
            return null;

        Comment toSave = new Comment();
        toSave.setText(newCommentRequest.getText());
        toSave.setUser(user);
        toSave.setPost(post);
        toSave.setCreateDate(new Date());
        commentRepository.save(toSave);

        return toSave;
    }


    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest updateComment) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if(comment.isPresent()) {
            Comment toUpdate = comment.get();
            toUpdate.setText(updateComment.getText());
            commentRepository.save(toUpdate);

            return toUpdate;
        }
        return null;
    }

    public void deleteOneComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
