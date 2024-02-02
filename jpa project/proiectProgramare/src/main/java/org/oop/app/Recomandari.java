package org.oop.app;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Recomandari implements Comparable<Recomandari>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idRecomandare;
    @NonNull private String titluRecomandare;
    @NonNull private String descriere;
    @NonNull @Pattern(regexp = "^http://*$")
    private String linkRecomandare;
    @Temporal(TemporalType.DATE) private Date dataPublicare;
    @OneToMany(mappedBy = "recomandare", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Categorie> categorie = new ArrayList<Categorie>();
    @Override
    public int compareTo(Recomandari other) {
        return this.idRecomandare.compareTo(other.idRecomandare);
    }
}