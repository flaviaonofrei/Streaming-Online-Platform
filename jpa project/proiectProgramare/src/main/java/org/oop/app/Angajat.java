package org.oop.app;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Angajat implements Comparable<Angajat>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idAngajat;
    @NonNull private String numeAngajat;
    @NonNull private String nrTelefon;
    @OneToMany(mappedBy = "angajat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Server> server = new ArrayList<Server>();
    @Override
    public int compareTo(Angajat other) {
        return this.idAngajat.compareTo(other.idAngajat);
    }
}
