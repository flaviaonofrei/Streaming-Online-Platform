package org.oop.app;
import lombok.*;
import jakarta.persistence.*;
@Entity
@Getter @Setter @NoArgsConstructor
public class Actor extends Persoana{
    @NonNull private String rolActor;
    @NonNull private Integer experienta;
    public Actor(Integer idPersoana, String numeprenumePersoana, String rolActor, Integer experienta) {
        super(idPersoana, numeprenumePersoana);
        this.rolActor = rolActor;
        this.experienta = experienta;
    }
}
