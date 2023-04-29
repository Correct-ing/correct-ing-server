package com.mju.correcting.domain.user.controller;

import com.mju.correcting.domain.user.dto.LoginRes;
import com.mju.correcting.domain.user.dto.LoginUserReq;
import com.mju.correcting.domain.user.dto.PostUserDto;
import com.mju.correcting.domain.user.dto.TokenReq;
import com.mju.correcting.domain.user.service.UserService;
import com.mju.correcting.global.jwt.dto.TokenDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "User", description = "사용자 API")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /*
     * 회원가입
     * */
    @PostMapping("")
    public ResponseEntity<String> signup(@RequestBody PostUserDto postUserDto) {
        return ResponseEntity.created(URI.create("/api/v1/users/"+userService.signup(postUserDto))).build();
    }

    /*
     * 아이디 중복확인
     * */
    @GetMapping("/{username}/exists")
    public ResponseEntity<Boolean> checkLoginId(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.checkUsername(username));
    }

    /*
     * 로그인
     * */
    @PostMapping("/login")
    public ResponseEntity<LoginRes> loginUser(@RequestBody LoginUserReq loginUserReq) {
        return ResponseEntity.ok(userService.loginUser(loginUserReq));
    }

    /*
     * 토큰 재발급
     * */
    @PostMapping("/token")
    public ResponseEntity<TokenDto> reissueToken(@RequestBody TokenReq tokenReq) {

        return ResponseEntity.ok(userService.reissueToken(tokenReq));
    }
}
