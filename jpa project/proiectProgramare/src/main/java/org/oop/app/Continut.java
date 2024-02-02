package org.oop.app;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor @RequiredArgsConstructor
public class Continut implements Comparable<Continut>{
    @EqualsAndHashCode.Include
    @NonNull @Id private Integer idContinut;
    @NonNull private String titluContinut;
    @NonNull @Enumerated(EnumType.STRING) private TipContinut tipContinut;
    @NonNull private Double durataContinut;
    @OneToMany(mappedBy = "continut", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Utilizator> utilizator = new ArrayList<Utilizator>();
    @ManyToMany
    @JoinTable(
            name = "continut_categorie",
            joinColumns = @JoinColumn(name = "idContinut"),
            inverseJoinColumns = @JoinColumn(name = "idCategorie")
    )
    private List<Categorie> categorii = new ArrayList<>();
    @ManyToMany(mappedBy = "continuturi")
    private List<Recenzie> recenzii = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "participare_continut_persoana",
            joinColumns = @JoinColumn(name = "idContinut"),
            inverseJoinColumns = @JoinColumn(name = "idPersoana")
    )
    private List<Persoana> persoaneParticipante = new ArrayList<>();
    @ManyToOne private Recenzie recenzie;
    @ManyToOne private Server server;
    @Override
    public int compareTo(Continut other) {
        return this.idContinut.compareTo(other.idContinut);
    }
}
