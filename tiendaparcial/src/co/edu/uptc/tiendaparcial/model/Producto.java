package co.edu.uptc.tiendaparcial.model;

public class Producto {
    private String nombre;
    private double precioBase;
    private int stock;
    private double porcentajeDescuento;
    private double impuestoIVA;

    public Producto(String nombre, double precioBase, int stock, double porcentajeDescuento, double impuestoIVA) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.stock = stock;
        this.porcentajeDescuento = porcentajeDescuento;
        this.impuestoIVA = impuestoIVA;
    }

    public double calcularPrecioFinal() {
        double conDescuento = precioBase * (1 - porcentajeDescuento / 100.0);
        return conDescuento * (1 + impuestoIVA / 100.0);
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public double getImpuestoIVA() {
        return impuestoIVA;
    }

    @Override
    public String toString() {
        return nombre;
    }
}