package com.mju.correcting.domain.weakness.service;

import com.mju.correcting.domain.chat.Category;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        Map<Category, Long> countByCategory = byUser.stream()
                .collect(Collectors.groupingBy(Weakness::getCategory, Collectors.counting()));

        Map<Category, Integer> weaknessCounts = new HashMap<>();
        Map<Category, Double> weaknessPercentages = new HashMap<>();

        long totalWeaknessCount = byUser.size();
        for (Map.Entry<Category, Long> entry : countByCategory.entrySet()) {
            Category category = entry.getKey();
            long count = entry.getValue();

            weaknessCounts.put(category, (int)count);
            weaknessPercentages.put(category, (double)count / totalWeaknessCount * 100);
        }

        GetWeakNessRes response = new GetWeakNessRes();
        response.setWeaknessCounts(weaknessCounts);
        response.setWeaknessPercentages(weaknessPercentages);
        return response;
    }
}
