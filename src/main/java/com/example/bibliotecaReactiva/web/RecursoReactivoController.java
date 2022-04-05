package com.example.bibliotecaReactiva.web;

import com.example.bibliotecaReactiva.domain.RecursoDTOReactivo;
import com.example.bibliotecaReactiva.service.IRecursoReactivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/bibliotecaReactiva")
public class RecursoReactivoController {

    @Autowired
    IRecursoReactivoService service;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RecursoDTOReactivo> save(@RequestBody RecursoDTOReactivo recursoDTO) {
        return service.save(recursoDTO);
    }

    @GetMapping("/{id}")
    public Mono<RecursoDTOReactivo> findById(@PathVariable("id") String id){
        return service.findById(id);
    }

    @GetMapping("")
    public Flux<RecursoDTOReactivo> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<RecursoDTOReactivo>> delete(@PathVariable("id") String id){
       return service.delete(id)
               .flatMap(recurso -> Mono.just(ResponseEntity.ok(recurso)))
               .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<RecursoDTOReactivo>> update(@PathVariable("id") String id,@RequestBody RecursoDTOReactivo recursoDTO){
        return service.update(id, recursoDTO)
                .flatMap(recurso -> Mono.just(ResponseEntity.ok(recurso)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/{nombre}/disponibilidad")
    public Flux<String> availability(@PathVariable("nombre") String nombre){
        return service.availability(nombre);

    }

    @PutMapping("/{id}/prestamo")
    public Mono<String> prestar(@PathVariable("id") String id){
        return service.prestar(id);
    }


    @PutMapping("/{id}/devolucion")
    public Mono<String> devolverRecurso(@PathVariable("id") String id){
        return service.devolverRecurso(id);
    }
}
