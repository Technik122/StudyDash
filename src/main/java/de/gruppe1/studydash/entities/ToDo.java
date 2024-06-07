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
@Table(name = "app_todo")
public class ToDo {

    @Id
    @UuidGenerator
    private UUID id;

    private String description;
    private boolean completed;
    private Date deadLine;
    private Priority priority;
    private UUID course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
