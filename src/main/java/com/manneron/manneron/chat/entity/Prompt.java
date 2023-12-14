package com.manneron.manneron.chat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "prompts")
@Getter
@NoArgsConstructor
public class Prompt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String substitution;

    public Prompt(String origin, String substitution) {
        this.origin = origin;
        this.substitution = substitution;
    }
}
