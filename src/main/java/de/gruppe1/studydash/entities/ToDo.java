package de.gruppe1.studydash.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@Entity
@Table(name = "app_todo")
public class ToDo {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            type = org.hibernate.id.uuid.UuidGenerator.class
    )
    private UUID id;
    private String description;
    private boolean completed;
    private Date deadLine;
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "parentToDo", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Subtask> subTasks = new ArrayList<>();

    public ToDo() {
    }
}
