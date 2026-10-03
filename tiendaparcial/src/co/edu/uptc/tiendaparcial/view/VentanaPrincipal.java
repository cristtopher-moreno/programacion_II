package co.edu.uptc.tiendaparcial.view;

import co.edu.uptc.tiendaparcial.model.Producto;
import co.edu.uptc.tiendaparcial.model.Venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    // Componentes de la GUI
    private JComboBox<Producto> cbProductos;
    private JLabel lblPrecioBase, lblStock, lblDescuento, lblIVA, lblPrecioFinal;
    private JTextField txtCantidad;
    private JButton btnRegistrar, btnCancelarPedido;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;

    // Listas para almacenar datos en memoria
    private List<Producto> listaProductos;
    private List<Venta> listaVentas;

    public VentanaPrincipal() {
        setTitle("Control de Inventario y Ventas - Tienda Parcial");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarDatos();
        construirUI();
        actualizarCamposProducto((Producto) cbProductos.getSelectedItem());
    }

    private void inicializarDatos() {
        listaProductos = new ArrayList<>();
        // Datos de prueba iniciales con tipos de datos acordes al modelo
        listaProductos.add(new Producto("Portátil Asus", 2500000.0, 10, 10.0, 19.0));
        listaProductos.add(new Producto("Mouse Inalámbrico", 80000.0, 25, 5.0, 19.0));
        listaProductos.add(new Producto("Teclado Mecánico", 150000.0, 15, 0.0, 19.0));

        listaVentas = new ArrayList<>();
    }

    private void construirUI() {
        // --- 1. PANEL SUPERIOR: Detalles del Producto ---
        JPanel panelSuperior = new JPanel(new GridLayout(6, 2, 5, 5));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Detalles del Producto"));

        cbProductos = new JComboBox<>(listaProductos.toArray(new Producto[0]));
        lblPrecioBase = new JLabel();
        lblStock = new JLabel();
        lblDescuento = new JLabel();
        lblIVA = new JLabel();
        lblPrecioFinal = new JLabel();

        panelSuperior.add(new JLabel("Seleccionar Producto:"));
        panelSuperior.add(cbProductos);
        panelSuperior.add(new JLabel("Precio Base:"));
        panelSuperior.add(lblPrecioBase);
        panelSuperior.add(new JLabel("Stock Disponible:"));
        panelSuperior.add(lblStock);
        panelSuperior.add(new JLabel("Descuento (%):"));
        panelSuperior.add(lblDescuento);
        panelSuperior.add(new JLabel("IVA (%):"));
        panelSuperior.add(lblIVA);
        panelSuperior.add(new JLabel("Precio Final Unitario:"));
        panelSuperior.add(lblPrecioFinal);

        // EVENTO 1: Selección Dinámica (ItemListener)
        cbProductos.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Producto seleccionado = (Producto) e.getItem();
                actualizarCamposProducto(seleccionado);
            }
        });

        // --- 2. PANEL CENTRAL: Formulario de Registro ---
        JPanel panelFormulario = new JPanel(new FlowLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Pedido"));

        txtCantidad = new JTextField(10);
        btnRegistrar = new JButton("Registrar Pedido");

        panelFormulario.add(new JLabel("Cantidad:"));
        panelFormulario.add(txtCantidad);
        panelFormulario.add(btnRegistrar);

        // EVENTO 2: Registrar Pedido (ActionListener)
        btnRegistrar.addActionListener(e -> registrarPedido());

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelSuperior, BorderLayout.CENTER);
        panelNorte.add(panelFormulario, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        // --- 3. PANEL INFERIOR: Tabla y Reversión ---
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Historial de Ventas"));

        String[] columnas = {"Producto", "Cantidad", "Total Pagado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaHistorial = new JTable(modeloTabla);

        btnCancelarPedido = new JButton("Cancelar Pedido Seleccionado");

        // EVENTO 3: Cancelar Pedido y Revertir Stock (ActionListener)
        btnCancelarPedido.addActionListener(e -> cancelarPedido());

        panelTabla.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);
        panelTabla.add(btnCancelarPedido, BorderLayout.SOUTH);

        add(panelTabla, BorderLayout.CENTER);
    }

    private void actualizarCamposProducto(Producto p) {
        if (p != null) {
            lblPrecioBase.setText(String.format("$%.2f", p.getPrecioBase()));
            lblStock.setText(String.valueOf(p.getStock()));
            lblDescuento.setText(p.getPorcentajeDescuento() + "%");
            lblIVA.setText(p.getImpuestoIVA() + "%");
            lblPrecioFinal.setText(String.format("$%.2f", p.calcularPrecioFinal()));
        }
    }

    private void registrarPedido() {
        Producto productoActual = (Producto) cbProductos.getSelectedItem();

        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cantidad > productoActual.getStock()) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente. Stock actual: " + productoActual.getStock(), "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 1. Decrementar stock
            productoActual.setStock(productoActual.getStock() - cantidad);
            double total = productoActual.calcularPrecioFinal() * cantidad;

            // 2. Registrar Venta
            Venta venta = new Venta(productoActual, cantidad, total);
            listaVentas.add(venta);

            // 3. Agregar a la tabla
            modeloTabla.addRow(new Object[]{productoActual.getNombre(), cantidad, String.format("$%.2f", total)});

            // 4. Actualizar vista
            actualizarCamposProducto(productoActual);
            txtCantidad.setText("");
            JOptionPane.showMessageDialog(this, "Pedido registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad numérica entera.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarPedido() {
        int filaSeleccionada = tablaHistorial.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla para cancelar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Venta ventaACancelar = listaVentas.get(filaSeleccionada);
        Producto producto = ventaACancelar.getProducto();

        // Restablecer stock
        producto.setStock(producto.getStock() + ventaACancelar.getCantidad());

        // Eliminar registro
        listaVentas.remove(filaSeleccionada);
        modeloTabla.removeRow(filaSeleccionada);

        // Actualizar UI
        actualizarCamposProducto((Producto) cbProductos.getSelectedItem());
        JOptionPane.showMessageDialog(this, "Pedido cancelado. El stock fue restablecido.", "Cancelación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
}