package com.mju.correcting.domain.test.service;

import com.mju.correcting.domain.test.domain.Test;
import com.mju.correcting.domain.test.dto.GetTestRes;
import com.mju.correcting.domain.test.dto.PostTestReq;
import com.mju.correcting.domain.test.repository.TestRepository;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.domain.user.repository.UserRepository;
import com.mju.correcting.global.common.error.BaseCode;
import com.mju.correcting.global.common.exception.CustomException;
import com.mju.correcting.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postTest(PostTestReq postTestReq) {
        User user = userRepository.findByUsername(SecurityUtil.getCurrentUserName())
                .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));

        testRepository.save(Test.builder()
                .answer(postTestReq.getAnswer())
                .question(postTestReq.getQuestion())
                .userAnswer(postTestReq.getUserAnswer())
                .user(user)
                .build());
    }

    public List<GetTestRes> getTests() {
        User user = userRepository.findByUsername(SecurityUtil.getCurrentUserName())
                .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));

        List<Test> tests = testRepository.findAllByUser(user);

        return tests.stream()
                .map(GetTestRes::new)
                .collect(Collectors.toList());
    }
}
