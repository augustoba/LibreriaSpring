package com.example.libreria1.controladores;

import com.example.libreria1.entidades.Autor;
import com.example.libreria1.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("")
    public String formulario(Model modelo, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        Autor autor = new Autor();
        modelo.addAttribute("autor", autor);
        return "autor";
    }

    @GetMapping("/modificar")
    public String formulario(@RequestParam(name = "id", required = true) String id, Model modelo, @RequestParam("archivo") MultipartFile archivo) {
        Autor autor = autorServicio.buscarPorId(id);
        modelo.addAttribute("autor", autor);
        return "autor";
    }

    @PostMapping("/save")
    public String formularioData(@RequestParam("nombre") String nombre, @RequestParam("id") String id,
            Model modelo, @RequestParam("archivo") MultipartFile archivo, @RequestParam(name = "modificar", required = false) String modificar) {
        Autor autor = new Autor();
        try {
            autor.setNombre(nombre);
            try {
                byte[] bytes = archivo.getBytes();
                Files.write(Paths.get("C:\\Users\\54381\\Desktop\\Diseño Web\\Libreriaaa\\upload\\" + archivo.getOriginalFilename()), bytes, StandardOpenOption.CREATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            autor.setId(id);
            autor.setAlta(true);
            autorServicio.guardarAutor(autor);
            modelo.addAttribute("autor", autor);
            if (modificar != null) {
                return "redirect:/autor/list";
            }
            return "autor";
        } catch (Exception ex) {
            ex.printStackTrace();
            modelo.addAttribute("autor", autor);
            modelo.addAttribute("error", ex.getMessage());
            return "autor";
        }
    }

    @GetMapping("/list")
    public String listAll(Model modelo, Principal principal) {
                 if (principal == null) {
            return "redirect:/";
        }
        List<Autor> autores = autorServicio.listarAutoresl();
        modelo.addAttribute("listaDeAutores", autores);
        return "autor-listar";
    }

    @GetMapping("/alta")
    public String alta(@RequestParam("id") String id) {
        try {
            autorServicio.altaAutor(id);
            return "redirect:/autor/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/autor/list";
        }
    }

    @GetMapping("/delete") //Baja
    public String delete(@RequestParam("id") String id) {
        try {
            autorServicio.borrarAutor(id);
            return "redirect:/autor/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/autor/list";
        }
    }

    @GetMapping("/eliminar")
    public String eliminarAutor(@RequestParam("id") String id) {
        try {
            autorServicio.eliminarAutor(id);
            return "redirect:/autor/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/autor/list";
        }
    }
}
