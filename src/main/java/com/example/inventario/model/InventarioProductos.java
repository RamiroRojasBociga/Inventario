package com.example.inventario.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.example.inventario.utils.Persistencia;

public class InventarioProductos {


    private TreeSet<Producto> productosConCantidadBaja;

    private static final int CANTIDAD_MINIMA_REABASTECIMIENTO = 10;

    public InventarioProductos() {
        productosConCantidadBaja = new TreeSet<>(new ComparadorProductosPorCantidad());
        cargarProductosDesdeArchivo();
        System.out.println("Productos cargados desde el archivo: " + productosConCantidadBaja);
    }

    private void cargarProductosDesdeArchivo() {
        List<Producto> productos = Persistencia.cargarProductosDesdeArchivo();
        for (Producto producto : productos) {
            if (producto.getCantidadInventario() <= CANTIDAD_MINIMA_REABASTECIMIENTO) {
                productosConCantidadBaja.add(producto);
            }
        }
    }

    public List<Producto> obtenerProductosOrdenadosPorCantidad() {
        List<Producto> productosOrdenados = new ArrayList<>(productosConCantidadBaja);
        productosOrdenados.sort(new ComparadorProductosPorCantidad());
        return productosOrdenados;
    }

    public static class ComparadorProductosPorCantidad implements Comparator<Producto> {
        @Override
        public int compare(Producto p1, Producto p2) {
            return Integer.compare(p1.getCantidadInventario(), p2.getCantidadInventario());
        }
    }

    // Otros métodos relacionados con el inventario, como obtener productos, etc.
}
