package com.mju.correcting.domain.weakness.repository;

import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.domain.weakness.domain.Weakness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeaknessRepository extends JpaRepository<Weakness, Long> {
    List<Weakness> findByUser(User user);
}

