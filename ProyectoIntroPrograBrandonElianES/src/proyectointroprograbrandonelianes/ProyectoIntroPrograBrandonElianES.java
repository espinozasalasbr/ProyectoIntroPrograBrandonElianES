/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectointroprograbrandonelianes;

import javax.swing.SwingUtilities;

/**
 *
 * @author espin
 */
public class ProyectoIntroPrograBrandonElianES {
    public static void main(String[] args) {
        // Lanzamos la interfaz gráfica en el hilo seguro de Swing
        SwingUtilities.invokeLater(() -> {
            UI ventanaPrincipal = new UI();
            ventanaPrincipal.setVisible(true);
        });
    }
}