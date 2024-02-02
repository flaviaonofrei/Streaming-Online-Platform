package org.oop.app;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Recenzie implements Comparable<Recenzie>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idRecenzie;
    @NonNull private String descriereRecenzie;
    @NonNull @Min(1) @Max(5) private Integer steleRecenzie;
    @OneToMany(mappedBy = "recenzie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Continut> continut = new ArrayList<Continut>();
    @ManyToMany
    @JoinTable(
            name = "recenzie_continut",
            joinColumns = @JoinColumn(name = "idRecenzie"),
            inverseJoinColumns = @JoinColumn(name = "idContinut")
    )
    private List<Continut> continuturi = new ArrayList<>();
    @Override
    public int compareTo(Recenzie other) {
        return this.idRecenzie.compareTo(other.idRecenzie);
    }
}