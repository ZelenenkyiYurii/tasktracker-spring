package com.zelenenkyi.tasktracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
@Entity
@Table(name="board")
@NamedEntityGraph(name = "board_entity-graph",
        attributeNodes = @NamedAttributeNode(value = "taskLists",subgraph = "taskListsWithTasks"),
        subgraphs = {
                @NamedSubgraph(
                        name = "taskListsWithTasks",
                        attributeNodes = {
                                @NamedAttributeNode(value = "tasks")
                        }
                )
        }
)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

//    @ManyToMany(mappedBy = "boards")
//    private Set<User> users=new HashSet<>();

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<TaskList> taskLists=new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<UserBoardRole> userBoardRoles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id) && Objects.equals(title, board.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
