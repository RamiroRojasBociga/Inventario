package com.example.inventario.model;

public class Cliente {
    private String numeroIdentificacion;
    private String nombre;
    private String direccion;

    public Cliente() {
    }

    public Cliente(String numeroIdentificacion, String nombre, String direccion) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
