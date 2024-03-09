module com.example.inventario {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.inventario to javafx.fxml;
    opens com.example.inventario.controller to javafx.fxml;
    opens com.example.inventario.model to javafx.base; // Abre el paquete del modelo al módulo javafx.base

    exports com.example.inventario;
    exports com.example.inventario.controller;
    exports com.example.inventario.model;
}
