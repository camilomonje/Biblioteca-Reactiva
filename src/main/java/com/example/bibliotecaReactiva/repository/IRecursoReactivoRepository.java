package com.example.bibliotecaReactiva.repository;

import com.example.bibliotecaReactiva.domain.RecursoDTOReactivo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface IRecursoReactivoRepository extends ReactiveMongoRepository<RecursoDTOReactivo, String> {
    Flux<RecursoDTOReactivo> findByNombre(String nombre);
}
