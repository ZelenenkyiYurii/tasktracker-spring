package com.zelenenkyi.tasktracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
@Entity
@Table(name = "task_list")
@NamedEntityGraph(name = "task_list_entity-graph", attributeNodes = @NamedAttributeNode("tasks"))
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "taskList",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Task> tasks=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @Override
    public int hashCode() {
        return Objects.hash(id);  // Use only the unique identifier
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskList taskList = (TaskList) o;
        return Objects.equals(id, taskList.id);
    }
}
