package com.example.bibliotecaReactiva.service.implement;

import com.example.bibliotecaReactiva.domain.RecursoDTOReactivo;
import com.example.bibliotecaReactiva.repository.IRecursoReactivoRepository;
import com.example.bibliotecaReactiva.service.IRecursoReactivoService;
import com.example.bibliotecaReactiva.utils.Tematica;
import com.example.bibliotecaReactiva.utils.Tipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class RecursoReactivoService  implements IRecursoReactivoService {

    @Autowired
    IRecursoReactivoRepository repository;

    @Override
    public Mono<RecursoDTOReactivo> save(RecursoDTOReactivo recurso) {
        return repository.save(recurso);
    }

    @Override
    public Mono<RecursoDTOReactivo> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<RecursoDTOReactivo> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<RecursoDTOReactivo> delete(String id) {
        return repository.findById(id)
                .flatMap(r -> repository.deleteById(r.getId()).thenReturn(r));
    }

    @Override
    public Mono<RecursoDTOReactivo> update(String id, RecursoDTOReactivo recurso) {
        return repository.findById(recurso.getId())
                .flatMap(r -> {
                    recurso.setId(recurso.getId());
                    return save(recurso);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<String> availability(String nombre) {
        return repository.findByNombre(nombre)
                .map(r ->  !r.isPrestamo() ? "El recurso esta disponible" :
                            "El recurso no esta disponible, se presto el dia: " + r.getFechaPrestamo());
    }

    @Override
    public Mono<String> prestar(String id) {
        Mono<RecursoDTOReactivo> recurso = repository.findById(id);
        return recurso.flatMap(r -> {
            if(!r.isPrestamo()){
                r.setPrestamo(true);
                r.setFechaPrestamo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                repository.save(r);
                return Mono.just(String.format("Se presta el recurso con id %s.", id));
            }
            return Mono.just(String.format("El recurso con id %s ya esta prestado", id));
        });
    }

    @Override
    public Mono<String> devolverRecurso(String id) {
        Mono<RecursoDTOReactivo> recurso = repository.findById(id);
        return recurso.flatMap(r -> {
            if(r.isPrestamo()){
                r.setPrestamo(false);
                repository.save(r);
                return Mono.just(String.format("Se retorna a la biblioteca el recurso con id %s.", id));
            }
            return Mono.just(String.format("El recurso con id %s ya esta en la biblioteca.", id));
        });
    }
}
