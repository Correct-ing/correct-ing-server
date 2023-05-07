package com.mju.correcting.domain.user.service;

import com.mju.correcting.domain.refresh_token.domain.RefreshToken;
import com.mju.correcting.domain.refresh_token.repository.RefreshTokenRepository;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.domain.user.dto.LoginRes;
import com.mju.correcting.domain.user.dto.LoginUserReq;
import com.mju.correcting.domain.user.dto.PostUserReq;
import com.mju.correcting.domain.user.repository.UserRepository;
import com.mju.correcting.global.common.exception.CustomException;
import com.mju.correcting.global.jwt.TokenProvider;
import com.mju.correcting.global.jwt.dto.TokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private RefreshTokenRepository refreshToken;


    @DisplayName("회원가입")
    @Test
    void signup() {
        //given
        PostUserReq postUserReq = createSignupUserReq();
        User user = createUserEntity(postUserReq);

        //mocking
        given(userRepository.save(any()))
                .willReturn(user);

        //when
        Long registerUser = userService.signup(postUserReq);

        //then
        assertThat(registerUser).isEqualTo(user.getId());
    }

    @DisplayName("아이디 중복확")
    @Test
    void checkUsername() {
        //given
        PostUserReq postUserReq = createSignupUserReq();
        User user = createUserEntity(postUserReq);

        //mocking
        given(userRepository.save(any()))
                .willReturn(user);
        //when
        User newUser = userRepository.save(user);
        boolean isUsernameExists = userService.checkUsername(newUser.getUsername());

        //then
        assertThat(isUsernameExists).isFalse();
    }

    @DisplayName("로그인")
    @Test
    void loginUser() {
        //given
        PostUserReq postUserReq = createSignupUserReq();
        User user = createUserEntity(postUserReq);
        TokenDto fakeToken = new TokenDto("","accessToken",1L,"");

        //mocking
        given(userRepository.save(any()))
                .willReturn(user);
        given(userRepository.findByUsername(user.getUsername()))
                .willReturn(Optional.of(user));
        given(passwordEncoder.matches(postUserReq.getPassword(), user.getPassword()))
                .willReturn(true);
        given(tokenProvider.generateTokenDto(any()))
                .willReturn(fakeToken);

        //when
        User newUser = userRepository.save(user);
        LoginRes tokenDto = userService.loginUser(new LoginUserReq( "jinbeomk", "aaa111"));

        //then
        assertThat(tokenDto.getAccessToken()).isEqualTo(fakeToken.getAccessToken());
    }

    @Test
    @DisplayName("잘못된 아이디 요청")
    void login_with_wrong_username() throws Exception {
        //given
        PostUserReq postUserReq = createSignupUserReq();
        User user = createUserEntity(postUserReq);
        postUserReq.setPassword("wrong");

        //mocking
        given(userRepository.save(any()))
                .willReturn(user);
        given(userRepository.findByUsername(any()))
                .willReturn(Optional.empty());


        //when
        userRepository.save(user);
        LoginUserReq loginUserReq = new LoginUserReq("jinbeomk", "aaa111");

        CustomException thrown = assertThrows(
                CustomException.class,
                () -> userService.loginUser(loginUserReq),
                "Expected to throw IllegalArgumentException, but failed"
        );
        //then
        assertThat(thrown.getMessage()).contains("가입되지");
    }

    private User createUserEntity(PostUserReq postUserReq) {

        return User.builder()
                .id(1L)
                .username(postUserReq.getId())
                .password(passwordEncoder.encode(postUserReq.getPassword()))
                .name(postUserReq.getName())
                .build();
    }

    private PostUserReq createSignupUserReq() {
        return new PostUserReq("jinbeomk",  "aaaa@gmail.com", "aaa111");
    }
}