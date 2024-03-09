package com.example.inventario.model;

import com.example.inventario.model.DetalleVenta;

import java.util.HashSet;
import java.util.Set;

public class Carrito {
    private Set<String> codigosProductos;

    public Carrito() {
        this.codigosProductos = new HashSet<>();
    }

    public void agregarProducto(DetalleVenta detalle) {
        codigosProductos.add(detalle.getProducto().getCodigoUnico());
    }

    public void eliminarProducto(DetalleVenta detalle) {
        codigosProductos.remove(detalle.getProducto().getCodigoUnico());
    }

    public boolean contieneProducto(DetalleVenta detalle) {
        return codigosProductos.contains(detalle.getProducto().getCodigoUnico());
    }

    public void vaciarCarrito() {
        codigosProductos.clear();
    }

    // Otros métodos para manejar el carrito, como obtener la lista de productos, calcular el total, etc.
}
