package de.gruppe1.studydash.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "app_subtask")
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private boolean completed;

    @Transient
    private String encryptedId;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    @JsonBackReference
    private ToDo parentToDo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
