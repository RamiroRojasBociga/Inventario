package com.example.inventario.model;

import java.util.ArrayList;
import java.util.List;

public class Venta {
    private String fecha;
    private String codigo;
    private Integer total;
    private String cantidad;
    private List<DetalleVenta> detallesVenta;

    public Venta() {
        this.detallesVenta = new ArrayList<>();
    }

    public Venta(String fecha, String codigo, Integer total, String cantidad, List<DetalleVenta> detallesVenta) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.total = total;
        this.cantidad = cantidad;
        this.detallesVenta = detallesVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }
}

