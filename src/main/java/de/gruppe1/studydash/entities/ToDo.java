package de.gruppe1.studydash.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "parentToDo", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Subtask> subTasks = new ArrayList<>();


    public ToDo() {
    }

}
