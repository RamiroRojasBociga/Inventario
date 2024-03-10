package com.example.inventario.controller;

import com.example.inventario.model.DetalleVenta;
import com.example.inventario.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentaController implements Initializable {

    private ObservableList<DetalleVenta> detallesVenta = FXCollections.observableArrayList();

    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    @FXML
    private Button BtnAceptar;

    @FXML
    private Button btnAdicionarCarrito;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ComboBox<Producto> cmbProductos;

    @FXML
    private TableColumn<Producto, String> colProducto;

    @FXML
    private TableColumn<Producto, Integer> colCantidad;

    @FXML
    private TableColumn<Producto, Double> colSubtotal;

    @FXML
    private TableView<DetalleVenta> tableDetalle;

    @FXML
    private TextField txfCantidad;

    @FXML
    private TextField txfSubtotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarProductosDesdeArchivo("C:\\Users\\ramiro.rojas\\Documents\\" +
                "Universidad\\Sexto Semestre\\Estructura de datos\\ProyectosJava" +
                "\\inventario\\src\\main\\resources\\persistencia.archivos\\archivoProductos.txt");
        cmbProductos.setItems(listaProductos);

        // Configurar las columnas de la TableView
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        // Enlazar la lista observable con la TableView
        tableDetalle.setItems(detallesVenta);
        // Agregar un listener al campo de cantidad para calcular el subtotal automáticamente
        txfCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            calcularSubtotal();
        });
        tableDetalle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetalleSeleccionado(newValue);
            }
        });
    }

    private void cargarProductosDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Ignorar las líneas vacías
                if (linea.trim().isEmpty()) {
                    continue;
                }
                // Separar la línea en partes utilizando una coma como delimitador
                String[] partes = linea.split(",");
                // Crear un objeto Producto con los datos de la línea y agregarlo a la lista
                Producto producto = new Producto(partes[0], partes[1], Double.parseDouble(partes[2]), Integer.parseInt(partes[3]));
                listaProductos.add(producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void aceptarVenta(ActionEvent event) {
        // Implementación del método
    }

    @FXML
    void adicionarACarrito(ActionEvent event) {
        // Implementación del método
    }

    @FXML
    void agregarDetalle(ActionEvent event) {
        // Implementación del método
    }

    @FXML
    void cancelarVenta(ActionEvent event) {
        // Implementación del método
    }

    @FXML
    void editarDetalle(ActionEvent event) {
        // Implementación del método
    }

    @FXML
    void eliminarDetalle(ActionEvent event) {
        // Implementación del método
    }

    private void calcularSubtotal() {
        Producto productoSeleccionado = cmbProductos.getValue();
        if (productoSeleccionado != null) {
            try {
                int cantidad = Integer.parseInt(txfCantidad.getText());
                double subtotal = cantidad * productoSeleccionado.getPrecio();
                txfSubtotal.setText(String.valueOf(subtotal));
            } catch (NumberFormatException e) {
                // Manejar la excepción si la cantidad no es un número válido
                // Por ejemplo, mostrar un mensaje de error al usuario
            }
        }
    }

    private void mostrarDetalleSeleccionado(DetalleVenta detalleVenta) {
        Producto producto = detalleVenta.getProducto();
        int cantidad = detalleVenta.getCantidad();
        double subtotal = detalleVenta.getSubtotal();

        // Mostrar la información en los campos correspondientes
        cmbProductos.setValue(producto);
        txfCantidad.setText(String.valueOf(cantidad));
        txfSubtotal.setText(String.valueOf(subtotal));
    }
}
