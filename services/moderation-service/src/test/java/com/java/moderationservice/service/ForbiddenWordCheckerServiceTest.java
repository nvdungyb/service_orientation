package com.java.moderationservice.service;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ForbiddenWordCheckerServiceTest {

    public static void main(String[] args) {
        ForbiddenWordCheckerService checkerService = new ForbiddenWordCheckerService();
        String text = "This is a forbidden word: drugs, sex, kills, heroin";

        checkerService.preprocessKeywords();
        List<String> detected = checkerService.check(text);

        System.out.println(detected);
    }
}
