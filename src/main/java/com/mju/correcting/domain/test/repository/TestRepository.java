package com.mju.correcting.domain.test.repository;

import com.mju.correcting.domain.test.domain.Test;
import com.mju.correcting.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByUser(User user);
}
