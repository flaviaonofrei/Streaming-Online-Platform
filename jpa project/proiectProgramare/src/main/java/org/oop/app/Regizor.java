package org.oop.app;
import lombok.*;
import jakarta.persistence.*;
@Entity
@Getter @Setter @NoArgsConstructor
public class Regizor extends Persoana{
    @NonNull private String premiiCastigate;
    public Regizor(Integer idPersoana, String numeprenumePersoana, String premiiCastigate) {
        super(idPersoana, numeprenumePersoana);

        this.premiiCastigate = premiiCastigate;
    }
}