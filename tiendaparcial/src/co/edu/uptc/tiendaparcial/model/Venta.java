package co.edu.uptc.tiendaparcial.model;

public class Venta {
    private Producto producto;
    private int cantidad;
    private double totalPagado;

    public Venta(Producto producto, int cantidad, double totalPagado) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.totalPagado = totalPagado;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getTotalPagado() { return totalPagado; }
}