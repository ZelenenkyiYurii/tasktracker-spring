package com.zelenenkyi.tasktracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name = "position")
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne()
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Use only the unique identifier
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }
}
