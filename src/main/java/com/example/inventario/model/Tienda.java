package com.example.inventario.model;

import java.util.HashMap;
import java.util.Map;

public class Tienda {
    public static Map<String, Cliente> mapaClientes = new HashMap<>();
    public static Map<String, Producto> mapaProductos = new HashMap<>();
    private String nombre;
    private String nit;

    public Tienda(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;

    }

    public Tienda() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
}
