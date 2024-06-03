package de.gruppe1.studydash.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "app_subtask")
public class Subtask {

    @Id
    @UuidGenerator
    private UUID id;

    private String description;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    @JsonBackReference
    private ToDo parentToDo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
