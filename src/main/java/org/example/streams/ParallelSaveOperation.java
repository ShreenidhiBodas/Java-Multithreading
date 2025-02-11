package org.example.streams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelSaveOperation {
    public static final String DIRECTORY = System.getProperty("user.dir") + "/test/";

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Paths.get(DIRECTORY));

        ParallelSaveOperation operation = new ParallelSaveOperation();

        // generate Person objects
        List<Person> people = generatePeople(100000);

        long start = System.currentTimeMillis();
        people.stream().forEach(ParallelSaveOperation::save);
        System.out.println("Time taken (sequential)" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        people.parallelStream().forEach(ParallelSaveOperation::save);
        System.out.println("Time taken (parallel)" + (System.currentTimeMillis() - start));
    }

    private static List<Person> generatePeople(int i) {
        return Stream.iterate(0, n -> n + 1)
                .limit(i)
                .map(Person::new)
                .collect(Collectors.toList());
    }

    private static void save(Person person) {
        try (FileOutputStream fos = new FileOutputStream(new File(DIRECTORY + person.getId() + ".txt"))) {

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
