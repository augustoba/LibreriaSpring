package com.example.libreria1.repositorios;

import com.example.libreria1.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
        @Query("select a from Libro a where a.titulo = :titulo")
    public Libro buscarLibroPorTitulo(@Param("titulo") String titulo);
}
