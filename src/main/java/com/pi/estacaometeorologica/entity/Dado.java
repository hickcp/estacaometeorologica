package com.pi.estacaometeorologica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="dados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_iot")
    private Iot iot;

    @Column(name = "temperatura", nullable = false)
    private float temperatura;

    @Column(name = "umidade", nullable = false)
    private float umidade;

    @Column(name = "pressao", nullable = false)
    private float pressao;


}
