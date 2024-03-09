package com.example.inventario.model;

import java.util.HashMap;

public class RegistroClientes {
    private HashMap<String, Cliente> registroClientes;

    public RegistroClientes() {
        this.registroClientes = new HashMap<>();
    }

    public void agregarCliente(Cliente cliente) {
        registroClientes.put(cliente.getNumeroIdentificacion(), cliente);
    }

    public Cliente obtenerCliente(String numeroIdentificacion) {
        return registroClientes.get(numeroIdentificacion);
    }

    // Otros métodos según sea necesario...
}
