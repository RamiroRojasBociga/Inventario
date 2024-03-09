package com.example.inventario.controller;

import com.example.inventario.model.InventarioProductos;
import com.example.inventario.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.TreeSet;

public class InventarioController {

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombreProducto;

    @FXML
    private TableColumn<Producto, Integer> colCantidad;

    @FXML
    private TableView<Producto> tableInventario;

    @FXML
    public void initialize() {
        // Configuración de las columnas
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoUnico"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadInventario"));

        // Obtener la lista de productos del inventario con cantidad baja
        InventarioProductos inventario = new InventarioProductos();
        TreeSet<Producto> productosConCantidadBaja = inventario.getProductosConCantidadBaja();

        // Convertir el TreeSet a una lista observable
        ObservableList<Producto> productosObservable = FXCollections.observableArrayList(productosConCantidadBaja);

        // Asignar la lista observable a la tabla
        tableInventario.setItems(productosObservable);
    }
}
