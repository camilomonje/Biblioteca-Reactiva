package com.example.bibliotecaReactiva.service;

import com.example.bibliotecaReactiva.domain.RecursoDTOReactivo;
import com.example.bibliotecaReactiva.utils.Tematica;
import com.example.bibliotecaReactiva.utils.Tipo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IRecursoReactivoService {

    Mono<RecursoDTOReactivo> save(RecursoDTOReactivo recurso);
    Mono<RecursoDTOReactivo> findById(String id);
    Flux<RecursoDTOReactivo> findAll();
    Mono<RecursoDTOReactivo> delete(String id);
    Mono<RecursoDTOReactivo> update(String id, RecursoDTOReactivo recurso);
    Flux<String> availability(String nombre);
    Mono<String> prestar(String id);
    Mono<String> devolverRecurso(String id);
}
