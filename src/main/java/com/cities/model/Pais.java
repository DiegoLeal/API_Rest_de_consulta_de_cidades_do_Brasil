package com.cities.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Pais {

    @Id
    private Long id;

    private String nome;

    private String nome_pt;

    private String sigla;

    private Integer bacen;

    public Pais() {
    }
}
