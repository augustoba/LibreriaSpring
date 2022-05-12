package com.example.libreria1.controladores;

import com.example.libreria1.entidades.Usuario;
import com.example.libreria1.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("")
    public String registro(Model modelo) {
	modelo.addAttribute("username", "");
	modelo.addAttribute("password", "");
	modelo.addAttribute("password2", "");
	return "usuario-formulario";
    }

    @PostMapping("/registro")
    public String registroUsuario(@RequestParam("username") String username,
	    @RequestParam("password") String password,
	    @RequestParam("password2") String password2,
	    Model modelo) {
	try {
	    Usuario usuario = usuarioServicio.registrarUsuario(username, password, password2);
	    modelo.addAttribute("success", "Usuario registrado con exito");
	    return "usuario-formulario";
	} catch (Exception ex) {
	    ex.printStackTrace();
	    modelo.addAttribute("username", username);
	    modelo.addAttribute("password", password);
	    modelo.addAttribute("password2", password2);
	    modelo.addAttribute("error", ex.getMessage());
	    return "usuario-formulario";
	}
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/lista")
    public String lista(Model modelo) {
	List<Usuario> usuarios = usuarioServicio.findAll();
	modelo.addAttribute("usuarios", usuarios);
	return "usuario-lista";
    }
}

