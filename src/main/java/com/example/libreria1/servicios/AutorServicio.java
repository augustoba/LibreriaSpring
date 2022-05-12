package com.example.libreria1.servicios;

import com.example.libreria1.entidades.Autor;
import com.example.libreria1.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    public Autor guardarAutor(Autor autor) throws Exception {
	if (autor.getNombre().isEmpty()) {
	    throw new Exception("El nombre del autor no puede estar vacio");
	}
	return autorRepositorio.save(autor);
    }

    public Autor modificarAutor(Autor autor, String nombre) throws Exception {
	if (nombre.isEmpty()) {
	    throw new Exception("El nuevo nombre del autor no puede estar vacio");
	}
	autor.setNombre(nombre);
	return autorRepositorio.save(autor);
    }

    public Autor darBaja(Autor autor) {
	autor.setAlta(false);
	return autorRepositorio.save(autor);
    }

    public List<Autor> listarAutoresl() {
	return autorRepositorio.findAll();
    }

    public Autor buscarPorNombre(String nombre) {
	return autorRepositorio.buscarAutorPorNombre(nombre);
    }

    public Autor buscarPorId(String id) {
	return autorRepositorio.getById(id);
    }

    public void borrarAutor(String id) throws Error {
	Optional<Autor> respuesta = autorRepositorio.findById(id);
	if (respuesta.isPresent()) {
	    Autor autor = respuesta.get();
	    autor.setAlta(false);
	    autorRepositorio.save(autor);
	} else {
	    throw new Error("No se encontró un autor con ese nombre");
	}
    }

    public void altaAutor(String id) throws Error {
	Optional<Autor> respuesta = autorRepositorio.findById(id);
	if (respuesta.isPresent()) {
	    Autor autor = respuesta.get();
	    autor.setAlta(true);
	    autorRepositorio.save(autor);
	} else {
	    throw new Error("No se encontró un autor con ese nombre");
	}
    }
    
    public void eliminarAutor(String id) throws Error{
        Autor autor = autorRepositorio.getById(id);
        if(autor == null){
            throw new Error("El autor no existe en la base de datos");
        }else{
            autorRepositorio.delete(autor);
        }
    }
}
