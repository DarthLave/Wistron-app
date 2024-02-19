package com.example.wistron.service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;
    @Column(name = "name", nullable = false, length = (50))
    private String name;
    @Column(name = "surname", nullable = false, length = (50))
    private String surname;
    @Column(name = "number", unique = true, nullable = false)
    private String number;
}