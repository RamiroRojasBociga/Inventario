package com.example.inventario.model;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.example.inventario.utils.Persistencia;

// Declaración de la clase InventarioProductos
public class InventarioProductos {
    // TreeSet para almacenar los productos con cantidad baja en inventario
    private TreeSet<Producto> productosConCantidadBaja;

    // Constante que representa la cantidad mínima en inventario considerada baja
    private static final int CANTIDAD_MINIMA_REABASTECIMIENTO = 10;

    // Constructor de la clase
    public InventarioProductos() {
        // Inicialización del TreeSet con un comparador personalizado
        productosConCantidadBaja = new TreeSet<>(new ComparadorProductosPorCantidad());
        // Cargar los productos desde el archivo de persistencia
        cargarProductosDesdeArchivo();
    }

    // Método para cargar los productos desde el archivo de persistencia
    private void cargarProductosDesdeArchivo() {
        // Cargar la lista de productos desde el archivo
        List<Producto> productos = Persistencia.cargarProductosDesdeArchivo();
        // Iterar sobre los productos cargados
        for (Producto producto : productos) {
            // Verificar si el producto tiene una cantidad en inventario menor o igual a la cantidad mínima
            if (producto.getCantidadInventario() <= CANTIDAD_MINIMA_REABASTECIMIENTO) {
                // Si sí, agregar el producto al TreeSet de productos con cantidad baja
                productosConCantidadBaja.add(producto);
            }
        }
    }

    // Método para obtener los productos con cantidad baja en inventario
    public TreeSet<Producto> getProductosConCantidadBaja() {
        return productosConCantidadBaja;
    }

    // Clase interna estática que implementa Comparator para comparar productos por cantidad en inventario
    public static class ComparadorProductosPorCantidad implements Comparator<Producto> {
        // Implementación del método compare para comparar dos productos
        @Override
        public int compare(Producto p1, Producto p2) {
            // Comparar los productos por su cantidad en inventario
            return Integer.compare(p1.getCantidadInventario(), p2.getCantidadInventario());
        }
    }
}
