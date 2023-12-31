package org.spring.data_jpa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "book_name")
    @NotEmpty(message = "Нименование не может быть пустым")
    @Size(min = 1, max = 100, message = "Размер наименования книги должен находиться в диапазоне от 2 до 100")
    private String bookName;
    @Column(name = "book_author")
    @NotEmpty(message = "Имя автора не может быть пустым")
    @Size(min = 5, max = 100, message = "Диапазон имени от 50 до 100 символов")
    private String author;
    @Column(name = "year_of_released")
    @NotEmpty(message = "Поле не должно быть пустым")
    private int yearReleased;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id", referencedColumnName = "id")
    private Reader owner;
    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;
    @Transient
    private boolean expired;

    public Book() {
    }

    public Book(String bookName, String author, int yearReleased) {
        this.bookName = bookName;
        this.author = author;
        this.yearReleased = yearReleased;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

    public Reader getOwner() {
        return owner;
    }

    public void setOwner(Reader owner) {
        this.owner = owner;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", yearReleased=" + yearReleased +
                '}';
    }
}
