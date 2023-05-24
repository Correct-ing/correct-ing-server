package com.mju.correcting.domain.weakness.dto;

import com.mju.correcting.domain.weakness.domain.Weakness;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetWeakNessRes {
    private int total;

    private List<WeaknessRes> weaknessList;

    public GetWeakNessRes(List<Weakness> weakness) {
        this.total = weakness.size();
        this.weaknessList = weakness.stream()
                .map(WeaknessRes::new)
                .collect(Collectors.toList());
    }
}
