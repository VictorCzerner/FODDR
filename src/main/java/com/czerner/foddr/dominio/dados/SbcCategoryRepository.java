package com.czerner.foddr.dominio.dados;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.czerner.foddr.dominio.entidades.entidadesData.SbcCategory;

@Repository
public interface SbcCategoryRepository extends JpaRepository<SbcCategory, Integer> {

    Optional<SbcCategory> findByName(String name);

    boolean existsByName(String name);

    default List<SbcCategory> buscarTodasCategorias() {
        return findAll();
    }
}
