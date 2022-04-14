package com.one.miniproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyDto {
    private long postid;
    private String title;
    private String content;
    private int star;
    private String username;

    @Override
    public String toString() {
        return "ModifyDto{" +
                "postid=" + postid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", star=" + star +
                ", username='" + username + '\'' +
                '}';
    }
}