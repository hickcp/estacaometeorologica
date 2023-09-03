package com.pi.estacaometeorologica.repository;

import com.pi.estacaometeorologica.entity.Dado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadoRepository extends JpaRepository<Dado, Long> {
}
