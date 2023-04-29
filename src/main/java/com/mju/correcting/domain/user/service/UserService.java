package com.mju.correcting.domain.user.service;

import com.mju.correcting.domain.refresh_token.domain.RefreshToken;
import com.mju.correcting.domain.refresh_token.repository.RefreshTokenRepository;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.domain.user.dto.LoginRes;
import com.mju.correcting.domain.user.dto.LoginUserReq;
import com.mju.correcting.domain.user.dto.PostUserDto;
import com.mju.correcting.domain.user.dto.TokenReq;
import com.mju.correcting.domain.user.repository.UserRepository;
import com.mju.correcting.global.common.error.BaseCode;
import com.mju.correcting.global.common.exception.CustomException;
import com.mju.correcting.global.jwt.TokenProvider;
import com.mju.correcting.global.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /*
     * 회원가입
     * */
    @Transactional
    public Long signup(PostUserDto postUserDto) {

        // 아이디 중복 확인
        userRepository.findByUsername(postUserDto.getId())
                .ifPresent(exists -> {
                    throw new CustomException(BaseCode.EXISTS_USERNAME);
                });

        // 패스워드 암호화
        String encodePassword = passwordEncoder.encode(postUserDto.getPassword());

        return userRepository.save(User.builder()
                .username(postUserDto.getId())
                .password(encodePassword)
                .email(postUserDto.getEmail())
                .build()).getId();

    }

    /*
     * 아이디 중복확인
     * */
    public boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /*
     * 로그인
     * */
    public LoginRes loginUser(LoginUserReq loginUserReq) {

        User user = userRepository.findByUsername(loginUserReq.getId())
                .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));

        // 패스워드 복호화
        if (!passwordEncoder.matches(loginUserReq.getPassword(), user.getPassword())) {
            throw new CustomException(BaseCode.WRONG_PASSWORD);
        }

        // 토큰 만들기
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(),"", user.getAuthorities());
        TokenDto token = tokenProvider.generateTokenDto(auth);

        //Refresh Token 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .key(auth.getName())
                .value(token.getRefreshToken())
                .build());

        return LoginRes.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .registerStatus(token.getRegisterStatus())
                .accessTokenExpiresIn(token.getAccessTokenExpiresIn())
                .grantType(token.getGrantType())
                .userId(user.getId())
                .build();
    }

    /*
     * 토큰 재발급
     * */
    public TokenDto reissueToken(TokenReq tokenReq) {
        //Access Token 추출
        Authentication authentication = tokenProvider.getAuthentication(tokenReq.getAccessToken());

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));

        //토큰 검증
        if (!tokenProvider.validateToken(tokenReq.getRefreshToken())) {
            throw new CustomException(BaseCode.INVALID_REFRESH_TOKEN);
        }

        // 레디스에서 리프레쉬 토큰 가져오기
        RefreshToken refreshToken = refreshTokenRepository.findByKey(user.getUsername())
                .orElseThrow(() -> new CustomException(BaseCode.INVALID_REFRESH_TOKEN));

        if (!tokenReq.getRefreshToken().equals(refreshToken.getValue())) {
            throw new CustomException(BaseCode.INVALID_REFRESH_TOKEN);
        }

        // 새로운 토큰 생성
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(),"", user.getAuthorities());
        TokenDto tokenDto = tokenProvider.generateTokenDto(auth);

        // 저장소 정보 업데이트
        refreshToken.updateValue(tokenDto.getRefreshToken());

        return tokenDto;

    }
}
