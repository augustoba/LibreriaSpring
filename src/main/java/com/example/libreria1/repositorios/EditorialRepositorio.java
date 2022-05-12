package com.example.libreria1.repositorios;

import com.example.libreria1.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
       @Query("select a from Editorial a where a.nombre = :nombre")
    public Editorial buscarEditorialPorNombre(@Param("nombre") String nombre);
}
