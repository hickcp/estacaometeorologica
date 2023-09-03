package com.pi.estacaometeorologica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="iot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Iot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "iot")
    private List<Dado> dados;

    public Iot(Long idIot){
        this.id = idIot;
    }
}
