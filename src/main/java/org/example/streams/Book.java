package org.example.streams;

public class Book {
    private String name;
    private Integer price;
    private Integer pages;
    private Type type;

    public Book() {
    }

    public Book(String name, Integer price, Integer pages, Type type) {
        this.name = name;
        this.price = price;
        this.pages = pages;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
