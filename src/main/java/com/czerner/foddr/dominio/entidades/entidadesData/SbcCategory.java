package com.czerner.foddr.dominio.entidades.entidadesData;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sbc_categories")
@Data
@NoArgsConstructor
public class SbcCategory {

    @Id
    @Column(name = "category_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
}
