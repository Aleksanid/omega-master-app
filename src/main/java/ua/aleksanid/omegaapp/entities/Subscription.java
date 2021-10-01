package ua.aleksanid.omegaapp.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Subscription {
    @Id
    Long userId;

    public Subscription() {
    }

    public Subscription(Long userId) {
        this.userId = userId;
    }
}
