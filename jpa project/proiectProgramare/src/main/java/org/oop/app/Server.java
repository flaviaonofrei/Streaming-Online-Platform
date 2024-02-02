package org.oop.app;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Server implements Comparable<Server>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idServer;
    @NonNull private String adresaIP;
    @NonNull private Integer activ;
    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Continut> continut = new ArrayList<>();
    @ManyToOne Angajat angajat;
    @Override
    public int compareTo(Server other) {
        return this.idServer.compareTo(other.idServer);
    }
}