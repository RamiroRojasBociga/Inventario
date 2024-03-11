package com.example.inventario.controller;

import com.example.inventario.model.Cliente;
import com.example.inventario.model.Main;
import com.example.inventario.model.Tienda;
import com.example.inventario.utils.Persistencia;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    private Button btnActualizarCliente;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCrearCliente;

    @FXML
    private Button btnEliminarCliente;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableColumn<Cliente, String> colIdentificacion;

    @FXML
    private TableColumn<Cliente, String> colDireccion;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private Label lblDireccion;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblNumeroIdentificacion;

    @FXML
    private TableView<Cliente> tableClientes;
    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNumeroIdentificacion;


    @FXML
    public void initialize() {
        colIdentificacion.setCellValueFactory(new PropertyValueFactory<>("numeroIdentificacion"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tableClientes.setItems(listaClientes);
        cargarClientesDesdeArchivo();

        tableClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Cliente clienteSeleccionado = newValue;
                txtNumeroIdentificacion.setText(clienteSeleccionado.getNumeroIdentificacion());
                txtNombre.setText(clienteSeleccionado.getNombre());
                txtDireccion.setText(clienteSeleccionado.getDireccion());
            }
        });
    }

    @FXML
    void actualizarCliente(ActionEvent event) {
        Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            clienteSeleccionado.setNumeroIdentificacion(txtNumeroIdentificacion.getText());
            clienteSeleccionado.setNombre(txtNombre.getText());
            clienteSeleccionado.setDireccion(txtDireccion.getText());
            limpiarCampos();
            tableClientes.refresh();
            mensajeClienteActualizado();
        }
    }

    @FXML
    void buscarCliente(ActionEvent event) {
        String numeroIdentificacionBuscado = txtBuscar.getText();
        Cliente clienteEncontrado = Tienda.mapaClientes.get(numeroIdentificacionBuscado);
        if (clienteEncontrado != null) {
            txtNombre.setText(clienteEncontrado.getNombre());
            txtDireccion.setText(clienteEncontrado.getDireccion());
            txtNumeroIdentificacion.setText(clienteEncontrado.getNumeroIdentificacion());
            mostrarMensaje("Cliente Encontrado","El cliente se encuentra registrado en la base de datos");

        } else {
            mostrarMensaje("Cliente No Encontrado", "El cliente no se encontró en la base de datos.");
            txtNombre.setText("Cliente no encontrado");
            txtDireccion.setText("Dirección no encontrada");
        }
    }

    @FXML
    void crearCliente(ActionEvent event) {
        String numeroIdentificacion = txtNumeroIdentificacion.getText();
        String nombre = txtNombre.getText();
        String direccion = txtDireccion.getText();

        // Validar que se hayan ingresado datos en todos los campos
        if (numeroIdentificacion.isEmpty() || nombre.isEmpty() || direccion.isEmpty()) {
            mostrarMensaje("Campos Vacíos", "Por favor, complete todos los campos antes de crear un cliente.");
            return; // Salir del método si hay campos vacíos

        }

        Cliente nuevoCliente = new Cliente(numeroIdentificacion, nombre, direccion);
        Tienda.mapaClientes.put(numeroIdentificacion, nuevoCliente);
        listaClientes.add(nuevoCliente);
        Persistencia.guardarClientesEnArchivo(listaClientes);
        mensajeClienteCreado();
        limpiarCampos();
    }
    private void cargarClientesDesdeArchivo() {
        listaClientes.addAll(Persistencia.cargarClientesDesdeArchivo());
    }

    @FXML
    void eliminarCliente(ActionEvent event) {
        String numeroIdentificacionCliente = txtNumeroIdentificacion.getText();
        Cliente clienteEliminado = Tienda.mapaClientes.remove(numeroIdentificacionCliente);
        if (clienteEliminado != null) {
            listaClientes.remove(clienteEliminado);
            mensajeClienteEliminado();
            limpiarCampos();
        } else {
            mostrarMensaje("Cliente No Encontrado", "El cliente no se encontró en la base de datos.");
        }
    }

    @FXML
    void limpiarCampo(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNumeroIdentificacion.clear();
        txtNombre.clear();
        txtDireccion.clear();
    }

    private void mensajeClienteCreado() {
        mostrarMensaje("Cliente Creado", "El cliente se ha creado correctamente.");
    }

    private void mensajeClienteActualizado() {
        mostrarMensaje("Cliente Actualizado", "El cliente se ha actualizado correctamente.");
    }

    private void mensajeClienteEliminado() {
        mostrarMensaje("Cliente Eliminado", "El cliente se ha eliminado correctamente.");
    }

    private void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

}
