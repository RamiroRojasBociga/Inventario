package com.example.inventario.controller;

import com.example.inventario.model.*;
import com.example.inventario.utils.Persistencia;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.inventario.utils.Persistencia.RUTA_ARCHIVO_CLIENTES;

public class VentaController implements Initializable {

    Persistencia persistencia = new Persistencia();


    @FXML
    private ComboBox<Cliente> comboCliente;

    private ObservableList<DetalleVenta> detallesVenta = FXCollections.observableArrayList();

    private ObservableList<Venta> venta = FXCollections.observableArrayList();
    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

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

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtTotal;

    @FXML
    private TableView<Venta> tableVenta;

    @FXML
    private TableColumn<Venta, String> colFechaVenta;

    @FXML
    private TableColumn<Venta, String> colCodigoVenta;

    @FXML
    private TableColumn<Venta, Cliente> colCliente;

    @FXML
    private TableColumn<Venta, Double> colValorTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cargarClientesDesdeArchivo("C:\\Users\\ramiro.rojas\\Documents\\" +
                "Universidad\\Sexto Semestre\\Estructura de datos\\ProyectosJava" +
                "\\inventario\\src\\main\\resources\\persistencia.archivos\\archivoClientes.txt");



        cargarProductosDesdeArchivo("C:\\Users\\ramiro.rojas\\Documents\\" +
                "Universidad\\Sexto Semestre\\Estructura de datos\\ProyectosJava" +
                "\\inventario\\src\\main\\resources\\persistencia.archivos\\archivoProductos.txt");

        // Obtener la lista de clientes del mapa en la clase Tienda
       // listaClientes = FXCollections.observableArrayList(Tienda.mapaClientes.values());


        cmbProductos.setItems(listaProductos);
        comboCliente.setItems(listaClientes);


        // Configurar las columnas de la TableView
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        // Configurar las columnas de la tabla de venta
        colFechaVenta.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCodigoVenta.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colValorTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Enlazar la lista observable con la TableView
        tableDetalle.setItems(detallesVenta);
        tableVenta.setItems(venta);


        // Agregar un listener al campo de cantidad para calcular el subtotal automáticamente
        txfCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            calcularSubtotal();
        });

        tableDetalle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetalleSeleccionado(newValue);
            }
        });



        // Agregar un listener al campo de cantidad para calcular el subtotal automáticamente
        txfCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            calcularSubtotal();
        });

        // Agregar un listener a la lista de detalles de venta para recalcular el valor total
        detallesVenta.addListener((ListChangeListener.Change<? extends DetalleVenta> change) -> {
            double total = 0.0;
            for (DetalleVenta detalle : detallesVenta) {
                total += detalle.getSubtotal();
            }
            txtTotal.setText(String.valueOf(total));
        });

        tableDetalle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetalleSeleccionado(newValue);
            }
        });

    }

    @FXML
    void aceptarVenta(ActionEvent event) {
        // Obtener los valores de los campos de venta
        String fechaVenta = txtFecha.getText();
        String codigoVenta = txtCodigo.getText();
        Cliente cliente = comboCliente.getValue(); // Obtener el cliente seleccionado del ComboBox
        double totalVenta = Double.parseDouble(txtTotal.getText());

        // Crear un objeto Venta con los valores obtenidos
        Venta venta = new Venta(fechaVenta, codigoVenta, cliente, totalVenta);
        tableVenta.getItems().add(venta);

        limpiarCamposDetalle();
    }


    @FXML
    void adicionarACarrito(ActionEvent event) {
        // Implementación del método
    }

    @FXML
    void agregarDetalle(ActionEvent event) {
        // Obtener los valores de los campos de detalle
        Producto productoSeleccionado = cmbProductos.getValue();
        int cantidad = Integer.parseInt(txfCantidad.getText());
        double subtotal = Double.parseDouble(txfSubtotal.getText());

        // Crear un objeto DetalleVenta con los valores obtenidos
        DetalleVenta detalleVenta = new DetalleVenta(productoSeleccionado, cantidad, subtotal);

        // Agregar el detalle a la tabla de detalle
        tableDetalle.getItems().add(detalleVenta);

        // Limpiar los campos de detalle
        limpiarCamposDetalle();
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
    private void cargarClientesDesdeArchivo(String rutaArchivo) {
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
                Cliente cliente= new Cliente(partes[0], partes[1],partes[2]);
                listaClientes.add(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void limpiarCamposVenta() {
        txtFecha.clear();
        txtCodigo.clear();
        comboCliente.getSelectionModel().clearSelection();
        txtTotal.clear();
    }

    private void limpiarCamposDetalle() {
        cmbProductos.getSelectionModel().clearSelection();
        txfCantidad.clear();
        txfSubtotal.clear();
    }




    // Método estático para actualizar el ComboBox de clientes

}
