/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointroprograbrandonelianes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author espin
 */
public class UI extends JFrame {
    private GestorMundial gestor;

    private JComboBox<Integer> comboIndiceEquipo;
    private JTextField txtPais, txtDT;

    private JTextArea txtAreaGrupos, txtAreaPartidos, txtAreaLlaves, txtAreaReporte;

    public UI() {
        gestor = new GestorMundial();

        setTitle("Copa Mundial Java");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Configuración & Edición", ConfiguracionInicial());
        pestañas.addTab("Sorteo", GeneradorSorteo());
        pestañas.addTab("Partidos", GeneradorPartidos());
        pestañas.addTab("Llaves", GeneradorClasificados());
        pestañas.addTab("Reportes", ReporteFinal());

        add(pestañas);
    }

    private JPanel ConfiguracionInicial() {
        JPanel panel = new JPanel(null);

        JLabel lblTamano = new JLabel("Tamaño del Torneo:");
        lblTamano.setBounds(20, 15, 180, 25);
        panel.add(lblTamano);

        Integer[] opciones = {24, 32, 48, 64};
        JComboBox<Integer> comboTamanos = new JComboBox<>(opciones);
        comboTamanos.setBounds(180, 15, 80, 25);
        panel.add(comboTamanos);

        JButton btnAplicar = new JButton("Configurar Tamaño");
        btnAplicar.setBounds(280, 15, 180, 25);
        panel.add(btnAplicar);

        JButton btnDemo = new JButton("Cargar Equipos Demo");
        btnDemo.setBounds(20, 50, 440, 30);
        panel.add(btnDemo);

        JLabel lblMod = new JLabel("--- Edición Manual de Entidades ---");
        lblMod.setBounds(20, 95, 300, 25);
        panel.add(lblMod);

        JLabel lblSel = new JLabel("Seleccione Índice:");
        lblSel.setBounds(20, 130, 150, 25);
        panel.add(lblSel);

        comboIndiceEquipo = new JComboBox<>();
        comboIndiceEquipo.setBounds(180, 130, 80, 25);
        panel.add(comboIndiceEquipo);

        JLabel lblPais = new JLabel("Nombre del País:");
        lblPais.setBounds(20, 170, 150, 25);
        panel.add(lblPais);
        txtPais = new JTextField();
        txtPais.setBounds(180, 170, 200, 25);
        panel.add(txtPais);

        JLabel lblDT = new JLabel("Director Técnico:");
        lblDT.setBounds(20, 210, 150, 25);
        panel.add(lblDT);
        txtDT = new JTextField();
        txtDT.setBounds(180, 210, 200, 25);
        panel.add(txtDT);

        JButton btnGuardarEquipo = new JButton("Guardar Cambios");
        btnGuardarEquipo.setBounds(20, 250, 360, 30);
        panel.add(btnGuardarEquipo);

        btnAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tamanoElegido = (int) comboTamanos.getSelectedItem();
                gestor.configurarTamano(tamanoElegido);
                comboIndiceEquipo.removeAllItems();
                for (int i = 0; i < tamanoElegido; i++) {
                    comboIndiceEquipo.addItem(i);
                }
                JOptionPane.showMessageDialog(UI.this, "Torneo dimensionado para " + tamanoElegido + " equipos.");
            }
        });

        btnDemo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gestor.isConfigurado()) {
                    JOptionPane.showMessageDialog(UI.this, "Error: Debe configurar el tamaño primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                gestor.genDemo();
                JOptionPane.showMessageDialog(UI.this, "Equipos de demostración cargados en memoria.");
            }
        });

        btnGuardarEquipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gestor.isConfigurado() || comboIndiceEquipo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(UI.this, "Error: Configure el torneo primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int ind = (int) comboIndiceEquipo.getSelectedItem();
                String pais = txtPais.getText().trim();
                String directorTecnico = txtDT.getText().trim();
                if (!pais.isEmpty()) gestor.getEquipos()[ind].setSeleccion(pais);
                if (!directorTecnico.isEmpty()) gestor.getEquipos()[ind].setDirectorTecnico(directorTecnico);
                JOptionPane.showMessageDialog(UI.this, "Entidad [" + ind + "] actualizada.");
            }
        });

        return panel;
    }

    private JPanel GeneradorSorteo() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnSorteo = new JButton("Realizar Sorteo Aleatorio de Grupos");
        txtAreaGrupos = new JTextArea();
        txtAreaGrupos.setEditable(false);
        panel.add(btnSorteo, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaGrupos), BorderLayout.CENTER);

        btnSorteo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gestor.isConfigurado()) {
                    JOptionPane.showMessageDialog(UI.this, "Error: Debe realizar la configuracion inicial primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                gestor.genSorteo();
                actualizarTablasGrupos();
                JOptionPane.showMessageDialog(UI.this, "Sorteo completado con éxito.");
            }
        });

        return panel;
    }

    private JPanel GeneradorPartidos() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel pnlBotones = new JPanel();
        JButton btnUnPartido = new JButton("Simular Un Partido");
        JButton btnTodaFase = new JButton("Simular Fase Completa");
        pnlBotones.add(btnUnPartido);
        pnlBotones.add(btnTodaFase);

        txtAreaPartidos = new JTextArea();
        txtAreaPartidos.setEditable(false);

        panel.add(pnlBotones, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaPartidos), BorderLayout.CENTER);

        btnUnPartido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gestor.isSorteoRealizado()) {
                    JOptionPane.showMessageDialog(UI.this, "Error: Debe realizar el sorteo primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String log = gestor.simuladorPartido();
                txtAreaPartidos.append(log + "\n");
                actualizarTablasGrupos();
            }
        });

        btnTodaFase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gestor.isSorteoRealizado()) {
                    JOptionPane.showMessageDialog(UI.this, "Error: Debe realizar el sorteo primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String logCompleto = gestor.simuladorFaseCompleta();
                txtAreaPartidos.setText(logCompleto);
                actualizarTablasGrupos();
                JOptionPane.showMessageDialog(UI.this, "Fase de grupos completamente simulada.");
            }
        });

        return panel;
    }

    private JPanel GeneradorClasificados() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnLlaves = new JButton("Calcular Clasificados y Simular Llaves");
        txtAreaLlaves = new JTextArea();
        txtAreaLlaves.setEditable(false);

        panel.add(btnLlaves, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaLlaves), BorderLayout.CENTER);

        btnLlaves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gestor.isFaseGruposCompletada()) {
                    JOptionPane.showMessageDialog(UI.this, "Error: Debe completar la simulación de la Fase de Grupos primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String res = gestor.genClasificadosYLlaves();
                txtAreaLlaves.setText(res);
            }
        });

        return panel;
    }

    private JPanel ReporteFinal() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnReporte = new JButton("Generar Resumen Global y Estadísticas Finales");
        txtAreaReporte = new JTextArea();
        txtAreaReporte.setEditable(false);

        panel.add(btnReporte, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaReporte), BorderLayout.CENTER);

        btnReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gestor.isLlavesCompletadas()) {
                    JOptionPane.showMessageDialog(UI.this, "Error: Debe completar las Llaves Eliminatorias primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                txtAreaReporte.setText(gestor.genReporteFinal());
            }
        });

        return panel;
    }

    private void actualizarTablasGrupos() {
        StringBuilder reporteFinal = new StringBuilder();
        EstadisticasEquipo[][] grupos = gestor.getMatrizGrupos();
        if (grupos == null) return;

        for (int g = 0; g < gestor.getCantidadGrupos(); g++) {
            reporteFinal.append("=== GRUPO ").append((char) ('A' + g)).append(" ===\n");
            reporteFinal.append("País\t\t| Pts | GF | GC | DG\n");
            reporteFinal.append("------------------------------------------\n");
            for (int e = 0; e < 4; e++) {
                EstadisticasEquipo est = grupos[g][e];
                // Concatenación estándar paso a paso usando tabuladores y delimitadores sencillos
                reporteFinal.append(est.getEquipo().getSeleccion())
                            .append("\t\t| ")
                            .append(est.getPuntos())
                            .append(" | ")
                            .append(est.getGolesFavor())
                            .append(" | ")
                            .append(est.getGolesContra())
                            .append(" | ")
                            .append(est.getDiferenciaGoles())
                            .append("\n");
            }
            reporteFinal.append("\n");
        }
        txtAreaGrupos.setText(reporteFinal.toString());
    }
}