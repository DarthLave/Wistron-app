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
    @Column(name = "name", unique = true, nullable = false, length = (50))
    private String name;
    @Column(name = "surname", unique = true, nullable = false, length = (50))
    private String surname;
    @Column(name = "number", nullable = false)
    private String number;
}