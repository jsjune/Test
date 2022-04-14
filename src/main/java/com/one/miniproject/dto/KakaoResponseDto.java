package com.one.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class KakaoResponseDto {
    private String username;
    private String nickname;
    private Boolean result;

}
