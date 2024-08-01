package com.zelenenkyi.tasktracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
public class User {

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Size(min=3,max = 30)
    @Column(name="username")
    private String username;

    @Size(max=320)
    @Email
    @Column(name="email", unique = true)
    private String email;

    @Size(min=8,max = 120)
    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserBoardRole> userBoardRoles;
//    @ManyToMany()
//    private Set<Board> boards;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    private Set<Role> roles=new HashSet<>();
}
