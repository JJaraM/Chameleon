package com.jjm.chameleon.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<Integer> findOccurrences(String str, String findStr) {
        int lastIndex = 0;
        List<Integer> indexList = new ArrayList<>();
        while (lastIndex != -1) {
            lastIndex = str.indexOf(findStr, lastIndex);
            if (lastIndex != -1) {
                lastIndex += findStr.length();
                indexList.add(lastIndex);
            }
        }
        return indexList;
    }

    public static String textBetweenWords(String sentence, String firstWord, String secondWord) {
        return sentence.substring(sentence.indexOf(firstWord) + firstWord.length(), sentence.indexOf(secondWord));
    }

    public static List<String> getClauses(String str, String findStr) {
        List<Integer> joinIndex = StringUtils.findOccurrences(str, findStr);
        List<String> join = new ArrayList<>();
        int size = joinIndex.size();
        for (int i = 0; i < size; i++) {
            Integer indexClause = joinIndex.get(i);
            Integer nextIndex = i + 1;
            if (nextIndex <= size) {
                Integer nextIndexClause = str.length();
                if (nextIndex < size) {
                    nextIndexClause = joinIndex.get(nextIndex) - findStr.length();
                }
                join.add(str.substring(indexClause, nextIndexClause).trim());
            }
        }
        return join;
    }
}
