package com.cities.controller;

import com.cities.model.Cidade;
import com.cities.repository.CidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeRepository repository;

    public CidadeController(CidadeRepository repository) {

        this.repository = repository;
    }

    @GetMapping
    public Page<Cidade> cidades(final Pageable page) {

        return repository.findAll(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCidades(@PathVariable Long id) {
        Optional<Cidade> optional = repository.findById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok().body(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
