package org.oop.app;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Abonament implements Comparable<Abonament>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idAbonament;
    @NonNull @Enumerated(EnumType.STRING) private TipAbonament tipAbonament;
    @NonNull private Double pretAbonament;
    @NonNull @Enumerated(EnumType.STRING) private Plata plataAbonament;
    @OneToMany(mappedBy = "abonament", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Utilizator> utilizator = new ArrayList<Utilizator>();
    @ManyToOne private Notificari notificare;
    @Override
    public int compareTo(Abonament other) {
        return this.idAbonament.compareTo(other.idAbonament);
    }
}

