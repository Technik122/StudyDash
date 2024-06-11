package de.gruppe1.studydash.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "app_event")
public class Event {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private Date date;
    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
