package org.oop.app;
import lombok.*;
import jakarta.persistence.*;
@Entity
@Getter @Setter @NoArgsConstructor
public class Producator extends Persoana{
    @NonNull private String companieProducatoare;

    public Producator(Integer idPersoana, String numeprenumePersoana, String companieProducatoare) {
        super(idPersoana, numeprenumePersoana);
        this.companieProducatoare = companieProducatoare;

    }
}