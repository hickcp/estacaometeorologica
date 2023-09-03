package com.pi.estacaometeorologica.entity;

import com.pi.estacaometeorologica.dto.dado.DadoCadastro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
    private Float temperatura;

    @Column(name = "umidade", nullable = false)
    private Float umidade;

    @Column(name = "pressao", nullable = false)
    private Float pressao;

    @Column(name = "altitude", nullable = false)
    private Float altitude;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;


    public Dado(DadoCadastro dado) {
        this.iot = new Iot(dado.idIot());
        this.temperatura = dado.temperatura();
        this.umidade = dado.umidade();
        this.pressao = dado.pressao();
        this.altitude = dado.altitude();
        this.dataRegistro = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }
}
