package com.one.miniproject.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.one.miniproject.dto.ResponseDto;
import com.one.miniproject.model.User;
import com.one.miniproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class RestLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        ResponseDto responseDto = new ResponseDto();

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            User user = userRepository.findByUsername(username).orElse(null);

            if(user == null){
                responseDto.setResult(false);
                responseDto.setErr_msg("없는 회원입니다");
            }else{
                responseDto.setResult(true);
                responseDto.setNickname(user.getNickname());
            }
        }


        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, responseDto);
        out.flush();


    }

}
