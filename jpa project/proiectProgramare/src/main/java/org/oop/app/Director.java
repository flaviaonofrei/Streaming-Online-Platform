package org.oop.app;
import lombok.*;
import jakarta.persistence.*;
@Entity
@Getter @Setter @NoArgsConstructor
public class Director extends Persoana{
    @NonNull private String departament;

    public Director(Integer idPersoana, String numeprenumePersoana, String departament) {
        super(idPersoana, numeprenumePersoana);
        this.departament = departament;
    }
}
