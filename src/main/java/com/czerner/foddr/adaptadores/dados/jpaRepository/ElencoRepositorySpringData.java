package com.czerner.foddr.adaptadores.dados.jpaRepository;

import com.czerner.foddr.dominio.entidades.elenco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElencoRepositorySpringData extends JpaRepository<elenco, Integer> {
}
