package com.example.inventario.model;

public class Producto {
    private String codigoUnico;
    private String nombre;
    private double precio;
    private int cantidadInventario;

    public Producto() {
    }

    public Producto(String codigoUnico, String nombre, double precio, int cantidadInventario) {
        this.codigoUnico = codigoUnico;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadInventario = cantidadInventario;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadInventario() {
        return cantidadInventario;
    }

    public void setCantidadInventario(int cantidadInventario) {
        this.cantidadInventario = cantidadInventario;
    }
    @Override
    public String toString() {
        return nombre;}

}

