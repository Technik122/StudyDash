package de.gruppe1.studydash.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "app_todo")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private boolean completed;
    private Date deadLine;
    private Priority priority;

    @OneToMany(mappedBy = "parentToDo", cascade = CascadeType.ALL)
    private List<Subtask> subTasks = new ArrayList<>();


    public ToDo() {
    }

}
