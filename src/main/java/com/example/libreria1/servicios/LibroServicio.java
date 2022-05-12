package com.example.libreria1.servicios;

import com.example.libreria1.entidades.Libro;
import com.example.libreria1.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    public Libro guardarLibro(Libro libro) throws Exception {
	if (libro.getTitulo().isEmpty()) {
	    throw new Exception("El nombre del libro no puede estar vacio");
	}
	return libroRepositorio.save(libro);
    }

    public Libro modificarLibro(Libro libro, String titulo) throws Exception {
	if (titulo.isEmpty()) {
	    throw new Exception("El nuevo nombre del libro no puede estar vacio");
	}
	libro.setTitulo(titulo);
	return libroRepositorio.save(libro);
    }

    public Libro darBaja(Libro libro) {
	libro.setAlta(false);
	return libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros() {
	return libroRepositorio.findAll();
    }

    public Libro buscarPorTitulo(String titulo) {
	return libroRepositorio.buscarLibroPorTitulo(titulo);
    }

    public Libro buscarPorId(String id) {
	return libroRepositorio.getById(id);
    }

    public void altaLibro(String id) throws Error {
	Optional<Libro> respuesta = libroRepositorio.findById(id);
	if (respuesta.isPresent()) {
	    Libro libro = respuesta.get();
	    libro.setAlta(true);
	    libroRepositorio.save(libro);
	} else {
	    throw new Error("No se encontró un libro con ese nombre");

	}
    }

    public void borrarLibro(String id) throws Error {
	Optional<Libro> respuesta = libroRepositorio.findById(id);
	if (respuesta.isPresent()) {
	    Libro libro = respuesta.get();
	    libro.setAlta(false);
	    libroRepositorio.save(libro);
	} else {
	    throw new Error("No se encontró una libro con ese nombre");
	}
    }
    
        public void eliminarLibro(String id) throws Error{
        Libro libro = libroRepositorio.getById(id);
        if(libro == null){
            throw new Error("El autor no existe en la base de datos");
        }else{
            libroRepositorio.delete(libro);
        }
    }
}
