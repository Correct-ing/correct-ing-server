package com.mju.correcting.domain.weakness.service;

import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.domain.user.repository.UserRepository;
import com.mju.correcting.domain.weakness.domain.Weakness;
import com.mju.correcting.domain.weakness.dto.GetWeakNessRes;
import com.mju.correcting.domain.weakness.repository.WeaknessRepository;
import com.mju.correcting.global.common.error.BaseCode;
import com.mju.correcting.global.common.exception.CustomException;
import com.mju.correcting.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class WeaknessService {

    private final UserRepository userRepository;
    private final WeaknessRepository weaknessRepository;

    public GetWeakNessRes getWeakness() {

        User user = userRepository.findByUsername(SecurityUtil.getCurrentUserName())
                .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));

        List<Weakness> byUser = weaknessRepository.findByUser(user);

        return new GetWeakNessRes(byUser);
    }
}
