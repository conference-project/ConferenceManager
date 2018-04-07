package agh.mwo.three.data;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class BookDetails {

    @Id
    private Integer id;

    private String genre; //gatunek literacki na ten moment String
    private String  description;
    private int numberOfPages;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bookDetails", fetch = FetchType.EAGER)
    private Book book;

    public BookDetails() {
    }

    public  BookDetails(String genre, String description, int numberOfPages) {
        this.id = getBook().getId();
        this.genre = genre;
        this.description = description;
        this.numberOfPages = numberOfPages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
