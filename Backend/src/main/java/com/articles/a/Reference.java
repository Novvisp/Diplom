package com.articles.a;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> fields = new ArrayList<>();

    @Lob
    @ElementCollection
    private List<String> specs = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addField(String field) {
        this.fields.add(field);
    }
    public List<String> getFields() {
        return fields;
    }

    public void addSpec(String spec) {
        this.specs.add(spec);
    }
    public List<String> getSpecs() {
        return specs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
