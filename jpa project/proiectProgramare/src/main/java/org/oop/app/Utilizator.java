package org.oop.app;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Utilizator implements Comparable<Utilizator>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idUtilizator;
    @NonNull private String numeUtilizator;
    @NonNull private String adresaUtilizator;
    @NonNull private String parolaUtilizator;
    @ManyToOne private Abonament abonament;
    @ManyToOne private Continut continut;


    @Override
    public int compareTo(Utilizator other) {
        return this.idUtilizator.compareTo(other.idUtilizator);
    }
}