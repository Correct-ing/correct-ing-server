package com.mju.correcting.domain.weakness.dto;

import com.mju.correcting.domain.chat.Category;
import com.mju.correcting.domain.weakness.domain.Weakness;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetWeakNessRes {

    private Map<Category, Integer> weaknessCounts;
    private Map<Category, Double> weaknessPercentages;

}
