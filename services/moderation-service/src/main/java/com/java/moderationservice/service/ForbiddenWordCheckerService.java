package com.java.moderationservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForbiddenWordCheckerService {
    private final List<String> forbiddenWords = List.of(
            "sex", "violence", "drugs", "kill", "terrorist", "heroin"
    );

    private final Map<String, int[]> kmpTables = new HashMap<>();

    @PostConstruct
    public void preprocessKeywords() {
        for (String keyword : forbiddenWords) {
            kmpTables.put(keyword, buildKmpNext(keyword));
        }
    }

    public List<String> check(String text) {
        List<String> detected = new ArrayList<>();
        for (String keyword : forbiddenWords) {
            if (contains(text, keyword, kmpTables.get(keyword))) {
                detected.add(keyword);
            }
        }
        return detected;
    }

    private boolean contains(String text, String pattern, int[] kmpNext) {
        int n = text.length();
        int m = pattern.length();
        int i = 0, j = 0;

        while (i < n) {
            while (j > -1 && text.charAt(i) != pattern.charAt(j))
                j = kmpNext[j];
            i++;
            j++;
            if (j == m) return true;
        }
        return false;
    }

    private int[] buildKmpNext(String pattern) {
        int m = pattern.length();
        int[] kmpNext = new int[m + 1];
        int i = 0, j = -1;
        kmpNext[0] = -1;
        while (i < m) {
            while (j > -1 && pattern.charAt(i) != pattern.charAt(j))
                j = kmpNext[j];
            i++;
            j++;
            if (i < m && pattern.charAt(i) == pattern.charAt(j))
                kmpNext[i] = kmpNext[j];
            else
                kmpNext[i] = j;
        }
        return kmpNext;
    }
}
