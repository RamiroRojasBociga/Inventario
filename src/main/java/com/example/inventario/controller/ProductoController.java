package com.example.inventario.controller;

import com.example.inventario.model.InventarioProductos;
import com.example.inventario.model.Producto;
import com.example.inventario.utils.Persistencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class ProductoController implements Initializable {

    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
    private InventarioProductos inventarioProductos;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, Integer> colInventario;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblInventario;

    @FXML
    private Label lblNombreProducto;

    @FXML
    private Label lblPrecio;

    @FXML
    private TableView<Producto> tableProducto;

    @FXML
    private TextField txtBuscar;
    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtInventario;

    @FXML
    private TextField txtNombreProducto;

    @FXML
    private TextField txtPrecio;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoUnico"));
        colInventario.setCellValueFactory(new PropertyValueFactory<>("cantidadInventario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        cargarProductosDesdeArchivo(); // Aquí se cargan los datos desde el archivo

        tableProducto.setItems(listaProductos);

        tableProducto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Producto productoSeleccionado = newValue;
                txtCodigo.setText(productoSeleccionado.getCodigoUnico());
                txtNombreProducto.setText(productoSeleccionado.getNombre());
                txtInventario.setText(String.valueOf(productoSeleccionado.getCantidadInventario()));
                txtPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
            }
        });

        // Imprimir los datos del archivo de productos al inicializar
        System.out.println("Datos del archivo de productos al iniciar:");
        imprimirListaProductosOrdenada();
    }

    @FXML
    void actualizarProducto(ActionEvent event) {
        Producto productoSeleccionado = tableProducto.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            productoSeleccionado.setCodigoUnico(txtCodigo.getText());
            productoSeleccionado.setNombre(txtNombreProducto.getText());
            productoSeleccionado.setCantidadInventario(Integer.parseInt(txtInventario.getText()));
            productoSeleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));

            Persistencia.actualizarProductoEnArchivo(productoSeleccionado, listaProductos);

            mostrarMensaje("Producto Actualizado", "El producto se ha actualizado correctamente.");
            limpiarCampos();
        } else {
            mostrarMensaje("Error", "Por favor, selecciona un producto para actualizar.");
        }
    }

    @FXML
    void buscarProducto(ActionEvent event) {
        String codigoProductoBuscado = txtBuscar.getText();
        Producto productoEncontrado = listaProductos.stream()
                .filter(producto -> producto.getCodigoUnico().equals(codigoProductoBuscado))
                .findFirst()
                .orElse(null);
        if (productoEncontrado != null) {
            txtNombreProducto.setText(productoEncontrado.getNombre());
            txtInventario.setText(String.valueOf(productoEncontrado.getCantidadInventario()));
            txtPrecio.setText(String.valueOf(productoEncontrado.getPrecio()));
            mostrarMensaje("Producto Encontrado", "El producto se ha encontrado en la base de datos.");
        } else {
            mostrarMensaje("Producto No Encontrado", "El producto no se encontró en la base de datos.");
            limpiarCampos();
        }
    }

    @FXML
    void crearProducto(ActionEvent event) {
        String codigoUnico = txtCodigo.getText();
        String nombre = txtNombreProducto.getText();
        String strPrecio = txtPrecio.getText();
        String strInventario = txtInventario.getText();

        if (codigoUnico.isEmpty() || nombre.isEmpty() || strPrecio.isEmpty() || strInventario.isEmpty()) {
            mostrarMensaje("Campos Vacíos", "Por favor, complete todos los campos antes de crear un producto.");
            return;
        }

        double precio;
        int cantidadInventario;

        try {
            precio = Double.parseDouble(strPrecio);
        } catch (NumberFormatException e) {
            mostrarMensaje("Precio Inválido", "El precio debe ser un número válido.");
            return;
        }

        try {
            cantidadInventario = Integer.parseInt(strInventario);
        } catch (NumberFormatException e) {
            mostrarMensaje("Cantidad en Inventario Inválida", "La cantidad en inventario debe ser un número válido.");
            return;
        }

        Producto nuevoProducto = new Producto(codigoUnico, nombre, precio, cantidadInventario);

        listaProductos.add(nuevoProducto);
        Persistencia.guardarProductosEnArchivo(listaProductos);

        mostrarMensaje("Producto Creado", "El producto se ha creado con éxito.");
        limpiarCampos();
        // Imprimir la lista de productos ordenada por consola
        System.out.println("Lista de productos ordenada por cantidad:");
        for (Producto producto : listaProductos) {
            System.out.println(producto);
        }
    }

    @FXML
    void eliminarProducto(ActionEvent event) {
        String codigoProducto = txtCodigo.getText();
        Producto productoEliminado = listaProductos.stream()
                .filter(producto -> producto.getCodigoUnico().equals(codigoProducto))
                .findFirst()
                .orElse(null);
        if (productoEliminado != null) {
            listaProductos.remove(productoEliminado);
            Persistencia.guardarProductosEnArchivo(listaProductos);
            mostrarMensaje("Producto Eliminado", "El producto se ha eliminado correctamente.");
            limpiarCampos();
        } else {
            mostrarMensaje("Producto No Encontrado", "El producto no se encontró en la base de datos.");
        }
    }

    @FXML
    void limpiarCampo(ActionEvent event) {
        limpiarCampos();
    }
    private void cargarProductosDesdeArchivo() {
        listaProductos.addAll(Persistencia.cargarProductosDesdeArchivo());
    }

    // Método para imprimir la lista de productos ordenada por consola
    private void imprimirListaProductosOrdenada() {
        List<Producto> productos = Persistencia.cargarProductosDesdeArchivo();
        Collections.sort(productos, new InventarioProductos.ComparadorProductosPorCantidad());

        System.out.println("Lista de productos ordenada por cantidad:");
        for (Producto producto : productos) {
            System.out.println("Producto: " + producto.getNombre() + ", Cantidad en inventario: " + producto.getCantidadInventario());
        }
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtInventario.clear();
        txtPrecio.clear();
        txtNombreProducto.clear();
    }

    private void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
