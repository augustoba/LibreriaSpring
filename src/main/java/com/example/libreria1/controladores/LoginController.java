package com.example.libreria1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("")
    public String login(@RequestParam(name = "error", required = false)String error, Model modelo) {
	if(error != null && !error.isEmpty()) {
	    modelo.addAttribute("error", "Usuario o contraseña incorrectos");
	}
	return "login";
    }

}

