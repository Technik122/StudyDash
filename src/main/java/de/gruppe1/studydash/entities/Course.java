package de.gruppe1.studydash.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "app_course")
public class Course {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
