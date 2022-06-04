package com.project.zipsa.service;

import com.project.zipsa.entity.Test;
import com.project.zipsa.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public void createTest(Test test) {
        testRepository.save(test);
    }

}
