package com.example.inventario.utils;

import com.example.inventario.model.Cliente;
import com.example.inventario.model.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Persistencia {

    public static final String RUTA_ARCHIVO_PRODUCTOS = "src\\main\\resources\\persistencia.archivos\\archivoProductos.txt";

    public static final String RUTA_ARCHIVO_CLIENTES = "src\\main\\resources\\persistencia.archivos" +
            "\\archivoClientes.txt";

    public static void guardarProductosEnArchivo(List<Producto> productos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO_PRODUCTOS))) {
            for (Producto producto : productos) {
                String linea = producto.getCodigoUnico() + "," + producto.getNombre() + "," + producto.getPrecio() + "," + producto.getCantidadInventario();
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Producto> cargarProductosDesdeArchivo() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO_PRODUCTOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    String codigo = partes[0];
                    String nombre = partes[1];
                    double precio = Double.parseDouble(partes[2]);
                    int cantidad = Integer.parseInt(partes[3]);
                    Producto producto = new Producto(codigo, nombre, precio, cantidad);
                    productos.add(producto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }
    public static void guardarClientesEnArchivo(List<Cliente> clientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO_CLIENTES))) {
            for (Cliente cliente : clientes) {
                String linea = cliente.getNumeroIdentificacion() + "," + cliente.getNombre() + "," + cliente.getDireccion();
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Cliente> cargarClientesDesdeArchivo() {
        List<Cliente> clientes = new ArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO_CLIENTES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String numeroIdentificacion = partes[0];
                    String nombre = partes[1];
                    String direccion = partes[2];

                    Cliente cliente = new Cliente(numeroIdentificacion, nombre, direccion);
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public static void actualizarProductoEnArchivo(Producto productoActualizado, List<Producto> productos) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigoUnico().equals(productoActualizado.getCodigoUnico())) {
                productos.set(i, productoActualizado);
                break;
            }
        }
        guardarProductosEnArchivo(productos);
        System.out.println("Ramiro Developer");
       


    }

}
