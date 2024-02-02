package org.oop.app;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Persoana implements Comparable<Persoana>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idPersoana;
    @NonNull private String numeprenumePersoana;
    @ManyToOne
    private Categorie categorie;
    @ManyToMany(mappedBy = "persoaneParticipante")
    private List<Continut> continuturiParticipante = new ArrayList<Continut>();
    @Override
    public int compareTo(Persoana other) {
        return this.idPersoana.compareTo(other.idPersoana);
    }
}