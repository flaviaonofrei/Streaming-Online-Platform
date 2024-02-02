package org.oop.app;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Notificari implements Comparable<Notificari>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idNotificare;
    @NonNull private String titlu;
    @NonNull private String continut;
    @OneToMany(mappedBy = "notificare", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Abonament> abonament = new ArrayList<Abonament>();
    @Override
    public int compareTo(Notificari other) {
        return this.idNotificare.compareTo(other.idNotificare);
    }
}
