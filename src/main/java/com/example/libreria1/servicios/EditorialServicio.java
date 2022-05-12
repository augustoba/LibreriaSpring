package com.example.libreria1.servicios;

import com.example.libreria1.entidades.Editorial;
import com.example.libreria1.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    public Editorial guardarEditorial(Editorial editorial) throws Exception {
	if (editorial.getNombre().isEmpty()) {
	    throw new Exception("El nombre del editor no puede estar vacio");
	}
	return editorialRepositorio.save(editorial);
    }

    public Editorial modificarEditorial(Editorial editorial, String nombre) throws Exception {
	if (nombre.isEmpty()) {
	    throw new Exception("El nuevo nombre del editor no puede estar vacio");
	}
	editorial.setNombre(nombre);
	return editorialRepositorio.save(editorial);
    }

    public Editorial darBaja(Editorial editorial) {
	editorial.setAlta(false);
	return editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditoriales() {
	return editorialRepositorio.findAll();
    }

    public Editorial buscarPorNombre(String nombre) {
	return editorialRepositorio.buscarEditorialPorNombre(nombre);
    }

    public Editorial buscarPorId(String id) {
	return editorialRepositorio.getById(id);
    }


    public void altaAutor(String id) throws Error {
	Optional<Editorial> respuesta = editorialRepositorio.findById(id);
	if (respuesta.isPresent()) {
	    Editorial editorial = respuesta.get();
	    editorial.setAlta(true);
	   editorialRepositorio.save(editorial);
	} else {
	    throw new Error("No se encontró una editorial con ese nombre");

	}
    }

    public void borrarEditorial(String id) throws Error {
	Optional<Editorial> respuesta = editorialRepositorio.findById(id);
	if (respuesta.isPresent()) {
	   Editorial editorial = respuesta.get();
	    editorial.setAlta(false);
	    editorialRepositorio.save(editorial);
	} else {
	    throw new Error("No se encontró una editorial con ese nombre");
	}
    }
    
      public void eliminarEditorial(String id) throws Error{
       Editorial editorial = editorialRepositorio.getById(id);
        if(editorial == null){
            throw new Error("El autor no existe en la base de datos");
        }else{
           editorialRepositorio.delete(editorial);
        }
    }
}
