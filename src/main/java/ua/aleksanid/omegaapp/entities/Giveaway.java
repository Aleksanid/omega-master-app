package ua.aleksanid.omegaapp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Data
@NoArgsConstructor
public class Giveaway {
    @Id
    @SequenceGenerator(name = "giveaway_id_gen", sequenceName = "giveaway_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "giveaway_id_gen")
    private Long id;

    private String name;
    private String link;

    public Giveaway(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
