package com.one.miniproject.controller;


import com.one.miniproject.dto.*;
import com.one.miniproject.service.PostService;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin
@EnableJpaAuditing
@RestController
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/posts")
    public List<GetPostDto> postDtoList(@RequestParam("page") Integer pageid, @RequestParam("username")String username){
        return postService.postDtoList(pageid,username);
    }

    @PostMapping("/api/posts")
    public ResponseDto writepost(PostDto postDto, @RequestParam("images") MultipartFile[] multipartFile)throws IOException {
        return postService.writepost(postDto,multipartFile);
    }

    @Transactional
    @PutMapping("/api/posts")
    public void modifypost(@RequestBody ModifyDto modifyDto)throws IOException{
        postService.modifypost(modifyDto);
    }

    @Transactional
    @DeleteMapping("/api/posts/{postId}")
    public ResponseDto deletepost(@PathVariable("postId") long pid){
        return postService.deletepost(pid);
    }

    @GetMapping("/api/posts/detail")
    public DetailDto detailpost(@RequestParam("postid") long pid, @RequestParam("username") String name){
        return postService.detailpost(pid,name);
    }

}