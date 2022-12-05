package com.example.kursach.Models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String genre;
    private String namebooks;
    private String yearrelese;
    private String author;
    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Collection<BooksOrder> booksorders;
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Collection<CopyBook> copybook;


    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public String getgenre(){return genre;};
    public void setgenre(String genre){this.genre=genre;}

    public String getnamebooks(){return namebooks;};
    public void setnamebooks(String namebooks){this.namebooks=namebooks;}

    public String getyearrelese(){return yearrelese;};
    public void setyearrelese(String yearrelese){this.yearrelese=yearrelese;}

    public String getauthor(){return author;};
    public void setauthor(String author){this.author=author;}

    public Collection<BooksOrder> getBooksorders() {
        return booksorders;
    }

    public void setBooksorders(Collection<BooksOrder> booksorders) {
        this.booksorders = booksorders;
    }

    public Collection<CopyBook> getCopybook() {
        return copybook;
    }

    public void setCopybook(Collection<CopyBook> copybook) {
        this.copybook = copybook;
    }
    public Books() { }

    public Books(String genre,String namebooks,String yearrelese, String author) {
        this.genre = genre;
        this.namebooks = namebooks;
        this.yearrelese = yearrelese;
        this.author=author;
    }

}
