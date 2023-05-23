package com.mju.correcting.domain.weakness.repository;

import com.mju.correcting.domain.weakness.domain.Weakness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaknessRepository extends JpaRepository<Weakness, Long> {
}

