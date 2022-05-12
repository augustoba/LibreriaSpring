/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.libreria1.enum1;

/**
 *
 * @author Nickler
 */

//Plantilla de Notas
public enum Status {
    IDEA("Idea"), IMPORTANTE("Importante"), TERMINADA("Terminada");
    
    private String nombreVista;

    private Status(String nombreVista) {
        this.nombreVista = nombreVista;
    }
    
    public String getNombreVista() {
        return nombreVista;
    }
}
