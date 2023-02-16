package com.example.libreria1.controladores;

import com.example.libreria1.entidades.Libro;
import com.example.libreria1.servicios.LibroServicio;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    LibroServicio libroServicio;

    @GetMapping("")
    public String formularioLibro(Model modelo, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        Libro libro = new Libro();
        modelo.addAttribute("libro", libro);
        return "libro";
    }

    @PostMapping("/save")
    public String formularioData(@RequestParam("titulo") String titulo, @RequestParam("id") String id,
            Model modelo, @RequestParam("archivo") MultipartFile archivo, @RequestParam(name = "modificar", required = false) String modificar) {
        Libro libro = new Libro();
        try {
            libro.setTitulo(titulo);
            try {
                byte[] bytes = archivo.getBytes();
                Files.write(Paths.get("C:\\Users\\54381\\Desktop\\Diseño Web\\Libreriaaa\\upload\\" + archivo.getOriginalFilename()), bytes, StandardOpenOption.CREATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            libro.setAlta(true);
            libro.setId(id);
            libroServicio.guardarLibro(libro);
            modelo.addAttribute("libro", libro);
            if (modificar != null) {
                return "redirect:/libro/list";
            }
            return "libro";
        } catch (Exception ex) {
            ex.printStackTrace();
            modelo.addAttribute("libro", libro);
            modelo.addAttribute("error", ex.getMessage());
            return "libro";
        }
    }

    @GetMapping("/modificar")
    public String formulario(@RequestParam(name = "id", required = true) String id, Model modelo) {
        Libro libro = libroServicio.buscarPorId(id);
        modelo.addAttribute("libro", libro);
        return "libro";
    }

    @GetMapping("/list")
    public String listAll(Model modelo, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        List<Libro> libro = libroServicio.listarLibros();
        modelo.addAttribute("listaDeLibros", libro);
        return "libro-listar";
    }

    @GetMapping("/alta")
    public String alta(@RequestParam("id") String id) {
        try {
            libroServicio.altaLibro(id);
            return "redirect:/libro/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/libro/list";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id) {
        try {
            libroServicio.borrarLibro(id);
            return "redirect:/libro/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/libro/list";
        }
    }

    @GetMapping("/eliminar")
    public String eliminarLibro(@RequestParam("id") String id) {
        try {
            libroServicio.eliminarLibro(id);
            return "redirect:/libro/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/libro/list";
        }
    }

}
