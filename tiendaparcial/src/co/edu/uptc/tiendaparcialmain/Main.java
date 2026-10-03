package co.edu.uptc.tiendaparcialmain;

import javax.swing.SwingUtilities;
import co.edu.uptc.tiendaparcial.view.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}