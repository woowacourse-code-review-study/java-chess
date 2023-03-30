package techcourse.fp.mission;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamStudy {

    public static long countWords() throws IOException {
        String contents = Files.readString(Paths
                .get("/Users/junho/study/woowa/java-chess/src/main/resources/techcourse/fp/war-and-peace.txt"));
        return Arrays.stream(contents.split("[\\P{L}]+"))
                .filter(word -> word.length() > 12)
                .count();
    }

    public static List<Integer> doubleNumbers(List<Integer> numbers) {
        return numbers.stream()
                .map(number -> number * 2)
                .collect(Collectors.toList());
    }

    public static long sumAll(List<Integer> numbers) {
        return numbers.stream().reduce(Integer::sum)
                .orElseThrow(() -> new IllegalArgumentException("Wrong Number"));
    }

    public static long sumOverThreeAndDouble(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number > 3)
                .map(number -> number * 2)
                .reduce(Integer::sum)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static void printLongestWordTop100() throws IOException {
        String contents = Files.readString(Paths
                .get("/Users/junho/study/woowa/java-chess/src/main/resources/techcourse/fp/war-and-peace.txt"));
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        System.out.println(words);
        System.out.println(words.size());

        List<String> collect = words.stream()
                .filter(word -> word.length() > 12)
                .map(String::toLowerCase)
                .distinct()
                .sorted(Comparator.comparing(String::length).reversed())
                .limit(100)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }
}
