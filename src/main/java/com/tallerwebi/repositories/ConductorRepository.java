package com.tallerwebi.repositories;

import com.tallerwebi.models.Conductor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConductorRepository {
    void save(Conductor conductor);

    List<Conductor> findAll();
}