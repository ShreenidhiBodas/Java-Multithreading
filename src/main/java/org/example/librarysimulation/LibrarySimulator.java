package org.example.librarysimulation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibrarySimulator {
    public static void main(String[] args) {
        Student[] students = null;
        Book[] books = null;
        ExecutorService service = Executors.newFixedThreadPool(Constants.NUM_OF_STUDENTS);

        try {
            students = new Student[Constants.NUM_OF_STUDENTS];
            books = new Book[Constants.NUM_OF_BOOKS];

            for (int i = 0; i < Constants.NUM_OF_BOOKS; i++) {
                books[i] = new Book(i+1);
            }

            for (int i = 0; i < Constants.NUM_OF_STUDENTS; i++) {
                students[i] = new Student(i+1, books);
                service.execute(students[i]);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
}
