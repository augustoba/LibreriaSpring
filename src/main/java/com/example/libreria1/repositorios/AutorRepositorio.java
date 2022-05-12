package com.example.libreria1.repositorios;

import com.example.libreria1.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    @Query("select a from Autor a where a.nombre = :nombre")
    public Autor buscarAutorPorNombre(@Param("nombre") String nombre);
    
}
