package org.oop.app;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Categorie implements Comparable<Categorie>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idCategorie;
    @NonNull @Enumerated(EnumType.STRING) private GenCinematografic genCinematografic;
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Persoana> persoana = new ArrayList<Persoana>();
    @ManyToMany(mappedBy = "categorii")
    private List<Continut> continuturi = new ArrayList<>();
    @ManyToOne private Recomandari recomandare;
    @Override
    public int compareTo(Categorie other) {
        return this.idCategorie.compareTo(other.idCategorie);
    }
}
