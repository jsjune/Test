package com.one.miniproject.service;

import com.one.miniproject.dto.*;
import com.one.miniproject.model.Good;
import com.one.miniproject.model.Image;
import com.one.miniproject.model.Post;
import com.one.miniproject.model.User;
import com.one.miniproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@Service
public class PostService {

    private final S3Uploader s3Uploader;
    public PostService(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CommentRepository commentRepository;

    public List<GetPostDto> postDtoList(Integer pageid,String username){
        List<Post> posts=postRepository.findAll();


        List<GetPostDto> getPostDtos=new ArrayList<>();
        for(Post p:posts){
            GetPostDto tmp=new GetPostDto();
            tmp.setPostId(p.getPostid());
            tmp.setContent(p.getContent());
            tmp.setTitle(p.getTitle());
            tmp.setCreatedAt(p.getCreatedAt());
            tmp.setStar(p.getStar());

            Good good = goodRepository.findByPostidAndUsername(p.getPostid(),username).orElse(null);
            tmp.setNickname(p.getUser().getNickname());
            if (good == null) {
                tmp.setHeart(false);
            } else {
                tmp.setHeart(true);
            }

            List<Image> a=imageRepository.findAllByPostid(p.getPostid());
            String[] myar=new String[a.size()];
            for(int i=0;i<a.size();i++)
                myar[i]=a.get(i).getImageSrc();
            tmp.setImageSrc(myar);
            List<Good> goods=goodRepository.findAllByPostid(p.getPostid());
            int gs=goods.size();
            tmp.setGood(gs);
            getPostDtos.add(tmp);
        }
        Collections.reverse(getPostDtos);
        List<GetPostDto> resultt=new ArrayList<>();
        for(int i=pageid*10-10;i<pageid*10;i++){
            try{
                resultt.add(getPostDtos.get(i));}
            catch(Exception e){return resultt;}
        }
        return resultt;
    }

    public ResponseDto writepost(PostDto postDto, MultipartFile[] multipartFile)throws IOException{
        Post post=new Post();
        post.setContent(postDto.getContent());
        post.setStar(postDto.getStar());
        post.setTitle(postDto.getTitle());
        Optional<User> user1=userRepository.findUserByUsername(postDto.getUsername());
        User user=user1.get();
        post.setUser(user);
        int leng= multipartFile.length;
        long imagepid=postRepository.save(post).getPostid();
        try {
            for (int i = 0; i < leng; i++) {
                Image image = new Image();
                image.setImageSrc(s3Uploader.upload(multipartFile[i], "static"));
                image.setPostid(imagepid);
                imageRepository.save(image);
            }
        } catch (Exception e) {
            System.out.println("이미지 없음");
        }
        post.setPostid(imagepid);
        ResponseDto responseDto=new ResponseDto();
        responseDto.setResult(true);
        responseDto.setErr_msg("");
        return responseDto;
    }



    public void modifypost(ModifyDto modifyDto)throws IOException{
        System.out.println(modifyDto.toString());
        Post post=new Post();
        post.setPostid(modifyDto.getPostid());
        post.setContent(modifyDto.getContent());
        post.setTitle(modifyDto.getTitle());
        post.setStar(modifyDto.getStar());
        Optional<User> user1=userRepository.findUserByUsername(modifyDto.getUsername());
        User user=user1.get();
        post.setUser(user);
        Optional<Post> post1=postRepository.findById(modifyDto.getPostid());
        Post post2=post1.get();
        post.setCreatedAt(post2.getCreatedAt());
        postRepository.save(post);
//        imageRepository.deleteAllByPostid(modifyDto.getPostid());
//        int leng= multipartFile.length;;
//        for(int i=0;i<leng;i++) {
//            Image image=new Image();
//            image.setImageSrc(s3Uploader.upload(multipartFile[i], "static"));
//            image.setPostid(modifyDto.getPostid());
//            imageRepository.save(image);
//        }
    }

    @Transactional
    public ResponseDto deletepost(long pid){
        ResponseDto responseDto=new ResponseDto();
        commentRepository.deleteAllByPost_Postid(pid);
        imageRepository.deleteAllByPostid(pid);
        goodRepository.deleteAllByPostid(pid);
        postRepository.deleteById(pid);
        responseDto.setErr_msg("");
        responseDto.setResult(true);
        return responseDto;
    }

    public DetailDto detailpost(long pid, String name){
        DetailDto result= new DetailDto();
        Optional<Post> post=postRepository.findById(pid);
        Post post1=post.get();
        result.setPostId(post1.getPostid());
        result.setTitle(post1.getTitle());
        result.setContent(post1.getContent());
        result.setCreatedAt(post1.getCreatedAt());
        result.setStar(post1.getStar());
        List<Good> goods=goodRepository.findAllByPostid(post1.getPostid());
        int gs=goods.size();
        result.setGood(gs);
        List<Image> a=imageRepository.findAllByPostid(post1.getPostid());
        String[] myar=new String[a.size()];
        for(int i=0;i<a.size();i++)
            myar[i]=a.get(i).getImageSrc();
        result.setImageSrc(myar);
        result.setHeart(true);
        try{
            List<Good> ggs=goodRepository.findAllByPostidAndUsername(post1.getPostid(),name);
            if(ggs.size()==0)
                result.setHeart(false);
        }
        catch(Exception e){
            result.setHeart(false);
        }
        return result;
    }


}