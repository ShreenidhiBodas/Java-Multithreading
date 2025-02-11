package org.example.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
    public static List<String> filterByTypeAndSort(List<Book> books, Type type) {
        // List of String (book titles) filtering by type = Fiction
        List<String> fictionTitles = books
                .stream()
                .filter(book -> book.getType() == type)
                .sorted((b1, b2) -> b1.getName().compareTo(b2.getName()))
                .map(Book::getName)
                .collect(Collectors.toList());
        System.out.println(Arrays.toString(fictionTitles.toArray()));

        return fictionTitles;
    }

    public static Map<Type, List<Book>> getBooksByType(List<Book> books) {
        Map<Type, List<Book>> resultMap = books
                .stream()
                .collect(Collectors.groupingBy(Book::getType));
        for (Map.Entry<Type, List<Book>> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey().toString() + " : " + Arrays.toString(entry.getValue().toArray()));
        }

        return resultMap;
    }

    public static List<String> getNLongestBooks(List<Book> books, int n) {
        List<String> nPagesTitles = books.stream()
                .filter(b -> b.getPages() > n)
                .map(Book::getName)
                .collect(Collectors.toList());
        System.out.println(Arrays.toString(nPagesTitles.toArray()));
        return nPagesTitles;
    }

    // Exercise
    public static List<Book> getBooksWith2WordTitles(List<Book> books) {
        List<Book> result = books.stream()
                .filter(b -> b.getName().split(" ").length == 2)
                .collect(Collectors.toList());

        System.out.println(Arrays.toString(result.toArray()));
        return result;
    }

    public static void readFromFileUsingStreams(String filePath) throws IOException {
        Stream<String> namesStream = Files.lines(Paths.get(filePath));
        namesStream.forEach(System.out::println);
    }

    // Exercise
    public static void generatePossiblePairs(Integer[] nums1, Integer[] nums2) {
        List<List<Integer>> result = Arrays.stream(nums1)
                .flatMap(i -> Arrays.stream(nums2).map(j -> Arrays.asList(i, j)))
                .collect(Collectors.toList());
        System.out.println(Arrays.toString(result.toArray()));
    }

    public static void main(String[] args) throws IOException {
        List<Book> books = MockBooks.getMockBooks();

//        filterByTypeAndSort(books, Type.FICTION);
//        getBooksByType(books);
//        getNLongestBooks(books, 500);
//        getBooksWith2WordTitles(books);
//        readFromFileUsingStreams("/Users/shreenidhi/misc/JavaMultithreading/src/main/java/org/example/streams/names");
        generatePossiblePairs(new Integer[]{1, 2, 3}, new Integer[]{4, 5});
    }
}