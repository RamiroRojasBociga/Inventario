package com.example.inventario.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class InventarioProductos {
    private HashMap<String, Producto> inventarioProductos;
    private TreeSet<Producto> productosConCantidadBaja; // TreeSet para mantener los productos ordenados por cantidad en inventario

    private static final int CANTIDAD_MINIMA_REABASTECIMIENTO = 10; // Por ejemplo, podrías definirla como 10 unidades


    public InventarioProductos() {
        this.inventarioProductos = new HashMap<>();
        this.productosConCantidadBaja = new TreeSet<>(new ComparadorProductosPorCantidad()); // Usar un comparador para ordenar por cantidad en inventario

    }

    public void agregarProducto(Producto producto) {
        inventarioProductos.put(producto.getCodigoUnico(), producto);
        if (producto.getCantidadInventario() <= CANTIDAD_MINIMA_REABASTECIMIENTO) {
            productosConCantidadBaja.add(producto);
        }
    }

    public Producto obtenerProducto(String codigo) {
        return inventarioProductos.get(codigo);
    }
    private static class ComparadorProductosPorCantidad implements Comparator<Producto> {
        @Override
        public int compare(Producto p1, Producto p2) {
            return Integer.compare(p1.getCantidadInventario(), p2.getCantidadInventario());
        }
    }

    // Otros métodos según sea necesario...
}
