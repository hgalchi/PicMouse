package com.example.PicMouse.Entity;

import com.example.PicMouse.Enum.Role;
import jakarta.persistence.*;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Role role;



    @Builder
    public User(String name, String email, Role role) {
        this.username=name;
        this.email=email;
        this.role=role;
    }



}
