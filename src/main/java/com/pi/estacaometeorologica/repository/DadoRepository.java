package com.pi.estacaometeorologica.repository;

import com.pi.estacaometeorologica.entity.Dado;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DadoRepository extends JpaRepository<Dado, Long> {

    @Query("SELECT d FROM Dado d WHERE d.dataRegistro BETWEEN :dataInicio AND :dataFim AND d.iot.id = :idIot ORDER BY d.dataRegistro ASC ")
    List<Dado> getDados(LocalDate dataInicio, LocalDate dataFim, Long idIot);


    @Query("""
            SELECT d FROM Dado d
            WHERE d.iot.id = :idIot
            ORDER BY d.dataRegistro DESC      
            """)
    List<Dado> getDado(Long idIot, Pageable pageable);
}
