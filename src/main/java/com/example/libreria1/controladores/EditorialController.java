package com.example.libreria1.controladores;

import com.example.libreria1.entidades.Editorial;
import com.example.libreria1.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("")
    public String formularioEditorial(Model modelo, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        Editorial editorial = new Editorial();
        modelo.addAttribute("editorial", editorial);
        return "editorial";
    }

    @GetMapping("/modificar")
    public String formulario(@RequestParam(name = "id", required = true) String id, Model modelo) {
        Editorial editorial = editorialServicio.buscarPorId(id);
        modelo.addAttribute("editorial", editorial);
        return "editorial-list";
    }

    @PostMapping("/save")
    public String formularioData(@RequestParam("nombre") String nombre,
            @RequestParam("id") String id, Model modelo,
            @RequestParam("archivo") MultipartFile archivo, @RequestParam(name = "modificar", required = false) String modificar) {
        Editorial editorial = new Editorial();
        try {
            editorial.setNombre(nombre);
            try {
                byte[] bytes = archivo.getBytes();
                Files.write(Paths.get("C:\\Users\\54381\\Desktop\\Diseño Web\\Libreriaaa\\upload\\" + archivo.getOriginalFilename()), bytes, StandardOpenOption.CREATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            editorial.setId(id);
            editorial.setAlta(true);
            editorialServicio.guardarEditorial(editorial);
            modelo.addAttribute("editorial", editorial);
            if (modificar != null) {
                return "redirect:/editorial/list";
            }
            return "editorial";
        } catch (Exception ex) {
            ex.printStackTrace();
            modelo.addAttribute("editorial", editorial);
            modelo.addAttribute("error", ex.getMessage());
            return "editorial";
        }
    }

    @GetMapping("/list")
    public String listAll(Model modelo, Principal principal) {
                 if (principal == null) {
            return "redirect:/";
        }
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("listaDeEditoriales", editoriales);
        return "editorial-listar";
    }

    @GetMapping("/alta")
    public String alta(@RequestParam("id") String id) {
        try {
            editorialServicio.altaAutor(id);
            return "redirect:/editorial/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/editorial/list";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id) {
        try {
            editorialServicio.borrarEditorial(id);
            return "redirect:/editorial/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/editorial/list";
        }
    }

    @GetMapping("/eliminar")
    public String eliminarAutor(@RequestParam("id") String id) {
        try {
            editorialServicio.eliminarEditorial(id);
            return "redirect:/editorial/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/editorial/list";
        }
    }

}
