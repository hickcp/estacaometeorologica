package com.pi.estacaometeorologica.repository;

import com.pi.estacaometeorologica.entity.Iot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IotRepository extends JpaRepository<Iot, Long> {

    public Iot getIotById(Long id);
}
