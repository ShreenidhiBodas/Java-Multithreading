package org.example.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MockBooks {
    public static List<Book> getMockBooks() {
        List<Book> books = new ArrayList<>();
        Random random = new Random();
        Type[] types = Type.values();

        List<String> realBookNames = Arrays.asList(
                "To Kill a Mockingbird",
                "1984",
                "The Great Gatsby",
                "The Catcher in the Rye",
                "Moby Dick",
                "Pride and Prejudice",
                "The Lord of the Rings",
                "The Hobbit",
                "Harry Potter and the Sorcerer's Stone",
                "Jane Eyre",
                "The Chronicles of Narnia",
                "Animal Farm",
                "War and Peace",
                "The Grapes of Wrath",
                "Ulysses",
                "Brave New World",
                "Wuthering Heights",
                "The Odyssey",
                "Great Expectations",
                "Crime and Punishment",
                "The Brothers Karamazov",
                "Les Misérables",
                "The Divine Comedy",
                "Dracula",
                "Frankenstein",
                "The Adventures of Huckleberry Finn",
                "Anna Karenina",
                "Don Quixote",
                "The Iliad",
                "Catch-22",
                "A Tale of Two Cities",
                "Fahrenheit 451",
                "Of Mice and Men",
                "Slaughterhouse-Five",
                "The Alchemist",
                "The Road",
                "The Shining",
                "It",
                "Gone with the Wind",
                "The Old Man and the Sea",
                "One Hundred Years of Solitude",
                "Beloved",
                "The Kite Runner",
                "Life of Pi",
                "The Book Thief",
                "The Handmaid's Tale",
                "A Game of Thrones",
                "The Hunger Games",
                "Dune",
                "The Fault in Our Stars"
        );

        for (int i = 0; i < 50; i++) {
            String name = realBookNames.get(i % realBookNames.size()); // Cycle through real book names
            int price = 100 + random.nextInt(901); // Random price between 100 and 1000
            Type type = types[random.nextInt(types.length)]; // Random Type
            int pages = 200 + random.nextInt(801); // Random pages between 200 and 1000

            books.add(new Book(name, price, pages, type));
        }

        return books;
    }
}
