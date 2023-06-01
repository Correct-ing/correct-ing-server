package com.mju.correcting.domain.weakness.dto;

import com.mju.correcting.domain.chat.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetWeakNessRes {

    private Map<Category, Integer> weaknessCounts;
    private Map<Category, Double> weaknessPercentages;

}
