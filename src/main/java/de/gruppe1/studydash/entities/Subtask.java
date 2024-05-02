package de.gruppe1.studydash.entities;

import jakarta.persistence.*;

@Entity
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private ToDo parentToDo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public ToDo getParentToDo() {
        return parentToDo;
    }

    public void setParentToDo(ToDo parentToDo) {
        this.parentToDo = parentToDo;
    }
}
