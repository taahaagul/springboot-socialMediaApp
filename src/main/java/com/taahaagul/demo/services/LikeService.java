package com.taahaagul.demo.services;

import com.taahaagul.demo.entities.Like;
import com.taahaagul.demo.entities.Post;
import com.taahaagul.demo.entities.User;
import com.taahaagul.demo.repos.LikeRepository;
import com.taahaagul.demo.requests.LikeCreateRequest;
import com.taahaagul.demo.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    LikeRepository likeRepository;
    UserService userService;
    PostService postService;

    public LikeService (LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {

        List<Like> list;

        if(userId.isPresent() && postId.isPresent()) {
            list =  likeRepository.findByUserIdAndPostId(userId, postId);
        } else if (userId.isPresent()) {
            list =  likeRepository.findByUserId(userId);
        } else if (postId.isPresent()) {
            list =  likeRepository.findByPostId(postId);
        } else
            list =  likeRepository.findAll();

        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like getOneLike(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());

        if(user == null || post == null)
            return null;

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);

        return like;
    }

    public void deleteOneLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
