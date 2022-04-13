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
public class postController {

    private final PostService postService;
    public postController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/posts/{page}")
    public List<GetPostDto> postDtoList(@PathVariable("page") Integer pageid){
        return postService.postDtoList(pageid);
    }

    @PostMapping("/api/posts")
    public ResponseDto writepost(PostDto postDto, @RequestParam("images") MultipartFile[] multipartFile)throws IOException {
        return postService.writepost(postDto,multipartFile);
    }

    @Transactional
    @PutMapping("/api/posts/{postId}")
    public void modifypost(@PathVariable("postId") Integer pid,@RequestParam("images") MultipartFile[] multipartFile, ModifyDto modifyDto)throws IOException{
        postService.modifypost(pid, multipartFile, modifyDto);
    }

    @Transactional
    @DeleteMapping("/api/posts/{postId}")
    public ResponseDto deletepost(@PathVariable("postId") long pid){
        return postService.deletepost(pid);
    }

    @GetMapping("/api/posts/detail/{postid}/{username}")
    public DetailDto detailpost(@PathVariable("postid") long pid, @PathVariable("username") String name){
        return postService.detailpost(pid,name);
    }

}