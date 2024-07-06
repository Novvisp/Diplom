package com.articles.a;


import jakarta.persistence.*;

import java.util.*;

import java.time.LocalDateTime;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String fileName;
    @Lob
    private String journal;
    @Lob
    private String abstrakt;
    private List<String> authors = new ArrayList();
    private LocalDateTime time;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Reference> references = new ArrayList<>();

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void addAuthor(String author) {
        this.authors.add(author);
    }
    public List<String> getAuthors() {
        return authors;
    }
    public void setJournal(String journal) {
        this.journal = journal;
    }
    public String getJournal() {
        return journal;
    }
    public void setAbstrakt(String abstrakt) {
        this.abstrakt = abstrakt;
    }
    public String getAbstrakt() {
        return abstrakt;
    }
    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }
    public void addReference(Reference reference) {
        this.references.add(reference);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}




