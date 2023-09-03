package com.pi.estacaometeorologica.repository;

import com.pi.estacaometeorologica.entity.Iot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IotRepository extends JpaRepository<Iot, Long> {
}
