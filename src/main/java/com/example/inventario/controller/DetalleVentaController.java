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

public class DetalleVentaController implements Initializable {

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
    void aceptarDetalle(ActionEvent event) {
        // Implementación del método
    }

    @FXML
    void adicionarACarrito(ActionEvent event) {
        // Implementación del método
    }

    @FXML
    void agregarDetalle(ActionEvent event) {
        // Construir un objeto DetalleVenta con la información capturada
        Producto productoSeleccionado = cmbProductos.getValue();
        int cantidad = Integer.parseInt(txfCantidad.getText());
        double subtotal = Double.parseDouble(txfSubtotal.getText());
        DetalleVenta detalle = new DetalleVenta(productoSeleccionado, cantidad, subtotal);

        // Verificar si los datos son válidos
        if (productoSeleccionado != null && cantidad > 0 && subtotal > 0) {
            // Agregar el detalle a la lista de detallesVenta
            detallesVenta.add(detalle);

            // Actualizar la TableView para mostrar los detalles de venta actualizados
            tableDetalle.setItems(detallesVenta);

            // Limpiar los campos después de agregar el detalle
            limpiarCampos();
        } else {
            // Mostrar un mensaje de error si los datos no son válidos
            //mostrarMensaje("Error", "Detalle de venta no agregado", "La información es incompleta o incorrecta", Alert.AlertType.ERROR);
        }
    }



    private void limpiarCampos() {
        cmbProductos.getSelectionModel().clearSelection();
        txfCantidad.clear();
        txfSubtotal.clear();
    }



    @FXML
    void cancelarDetalle(ActionEvent event) {
        // Implementación del método
    }

    @FXML
    void editarDetalle(ActionEvent event) {
        // Obtener el detalle de venta seleccionado en la tabla
        DetalleVenta detalleSeleccionado = tableDetalle.getSelectionModel().getSelectedItem();

        if (detalleSeleccionado != null) {
            // Obtener los nuevos valores ingresados en los campos de texto y la selección del ComboBox
            Producto nuevoProducto = cmbProductos.getValue();
            int nuevaCantidad = Integer.parseInt(txfCantidad.getText());
            double nuevoSubtotal = Double.parseDouble(txfSubtotal.getText());

            // Verificar si los nuevos valores son válidos
            if (nuevoProducto != null && nuevaCantidad > 0 && nuevoSubtotal > 0) {
                // Actualizar los valores del detalle de venta seleccionado
                detalleSeleccionado.setProducto(nuevoProducto);
                detalleSeleccionado.setCantidad(nuevaCantidad);
                detalleSeleccionado.setSubtotal(nuevoSubtotal);

                // Actualizar la TableView para reflejar los cambios
                tableDetalle.refresh();

                // Limpiar los campos después de la edición
                limpiarCampos();
            } else {
                // Mostrar un mensaje de error si los datos no son válidos
                mostrarMensaje("Error", "Los datos ingresados no son válidos.");
            }
        } else {
            // Mostrar un mensaje de error si no se seleccionó ningún detalle de venta
            mostrarMensaje("Error", "No se ha seleccionado ningún detalle de venta para editar.");
        }
    }


    @FXML
    void eliminarDetalle(ActionEvent event) {
        // Obtener el detalle de venta seleccionado en la tabla
        DetalleVenta detalleSeleccionado = tableDetalle.getSelectionModel().getSelectedItem();

        // Verificar si se seleccionó un detalle de venta
        if (detalleSeleccionado != null) {
            // Eliminar el detalle de venta de la lista de detallesVenta
            detallesVenta.remove(detalleSeleccionado);
            // Limpiar la selección en la tabla
            tableDetalle.getSelectionModel().clearSelection();
            mostrarMensaje("Detalle Eliminado", "El detalle de venta ha sido eliminado correctamente.");
        } else {
            // Mostrar un mensaje de error si no se seleccionó ningún detalle de venta
            mostrarMensaje("Error", "Por favor, seleccione un detalle de venta a eliminar.");
        }
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
    private void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
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
