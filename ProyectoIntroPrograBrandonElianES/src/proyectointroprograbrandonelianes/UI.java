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
    //genera el gestor
    private GestorMundial gestor;

    // declaramos visuales
    private JComboBox<Integer> IndiceEquipo;
    private JTextField modPais, modDT;

    private JTextArea txtAreaGrupos, txtAreaPartidos, txtAreaLlaves, txtAreaReporte;
    
    //pestañas y tamaño de la ventana
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
    
    //configuracion de visuales y botones
    private JPanel ConfiguracionInicial() {
        JPanel panel = new JPanel(null);

        JLabel lblTamano = new JLabel("Tamaño del Torneo:");
        lblTamano.setBounds(150, 35, 180, 25);
        panel.add(lblTamano);

        Integer[] opciones = {24, 32, 48, 64};
        JComboBox<Integer> comboTamanos = new JComboBox<>(opciones);
        comboTamanos.setBounds(300, 35, 80, 25);
        panel.add(comboTamanos);

        JButton btnAplicar = new JButton("Configurar Tamaño");
        btnAplicar.setBounds(390, 35, 180, 25);
        panel.add(btnAplicar);

        JButton btnDemo = new JButton("Cargar Equipos Demo");
        btnDemo.setBounds(150, 70, 500, 30);
        panel.add(btnDemo);

        JLabel lblMod = new JLabel("--- Edición Manual de Entidades ---");
        lblMod.setBounds(270, 195, 300, 25);
        panel.add(lblMod);

        JLabel lblSel = new JLabel("Seleccione Índice:");
        lblSel.setBounds(150, 230, 150, 25);
        panel.add(lblSel);

        IndiceEquipo = new JComboBox<>();
        IndiceEquipo.setBounds(380, 230, 80, 25);
        panel.add(IndiceEquipo);

        JLabel lblPais = new JLabel("Nombre del País:");
        lblPais.setBounds(150, 270, 150, 25);
        panel.add(lblPais);
        modPais = new JTextField();
        modPais.setBounds(380, 270, 200, 25);
        panel.add(modPais);

        JLabel lblDT = new JLabel("Director Técnico:");
        lblDT.setBounds(150, 310, 150, 25);
        panel.add(lblDT);
        modDT = new JTextField();
        modDT.setBounds(380, 310, 200, 25);
        panel.add(modDT);

        JButton btnGuardarEquipo = new JButton("Guardar Cambios");
        btnGuardarEquipo.setBounds(150, 350, 500, 30);
        panel.add(btnGuardarEquipo);

        btnAplicar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int tamanoElegido = (int) comboTamanos.getSelectedItem();
                gestor.configurarTamano(tamanoElegido);
                IndiceEquipo.removeAllItems();
                for (int i = 0; i < tamanoElegido; i++) {
                    IndiceEquipo.addItem(i);
                }
                JOptionPane.showMessageDialog(UI.this, "Torneo dimensionado para " + tamanoElegido + " equipos.");
            }
        });
        
        //genera equipos demo
        btnDemo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gestor.isConfigurado()) {
                    JOptionPane.showMessageDialog(UI.this, "Error: Debe configurar el tamaño primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                gestor.genDemo();
                JOptionPane.showMessageDialog(UI.this, "Equipos de demostración cargados en memoria.");
            }
        });

        // modificador de equipos por indice
        btnGuardarEquipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gestor.isConfigurado() || IndiceEquipo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(UI.this, "Error: Configure el torneo primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int ind = (int) IndiceEquipo.getSelectedItem();
                String pais = modPais.getText().trim();
                String directorTecnico = modDT.getText().trim();
                if (!pais.isEmpty()) gestor.getEquipos()[ind].setSeleccion(pais);
                if (!directorTecnico.isEmpty()) gestor.getEquipos()[ind].setDirectorTecnico(directorTecnico);
                JOptionPane.showMessageDialog(UI.this, "Entidad [" + ind + "] actualizada.");
            }
        });

        return panel;
    }

    //genera sorteo si la config inicial fue realizada
    private JPanel GeneradorSorteo() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnSorteo = new JButton("Realizar Sorteo Aleatorio de Grupos");
        txtAreaGrupos = new JTextArea();
        txtAreaGrupos.setEditable(false);
        panel.add(btnSorteo, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaGrupos), BorderLayout.CENTER);

        btnSorteo.addActionListener(new ActionListener() {
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

    // generador de partidos
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

    //generador de clasificados
    private JPanel GeneradorClasificados() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnLlaves = new JButton("Calcular Clasificados y Simular Llaves");
        txtAreaLlaves = new JTextArea();
        txtAreaLlaves.setEditable(false);

        panel.add(btnLlaves, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaLlaves), BorderLayout.CENTER);

        btnLlaves.addActionListener(new ActionListener() {
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

    // generador de reporte final
    private JPanel ReporteFinal() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnReporte = new JButton("Generar Resumen Global y Estadísticas Finales");
        txtAreaReporte = new JTextArea();
        txtAreaReporte.setEditable(false);

        panel.add(btnReporte, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaReporte), BorderLayout.CENTER);

        btnReporte.addActionListener(new ActionListener() {
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

    //tablas y estadisticas de grupos
    private void actualizarTablasGrupos() {
        StringBuilder reporteFinal = new StringBuilder();
        EstadisticasEquipo[][] grupos = gestor.getMatrizGrupos();
        if (grupos == null) return;

        for (int g = 0; g < gestor.getCantidadGrupos(); g++) {
            reporteFinal.append("---GRUPO ").append((char) ('A' + g)).append("---\n");
            reporteFinal.append("País\t\t| Pts | GF | GC | DG\n");
            reporteFinal.append("------------------------------------------\n");
            for (int e = 0; e < 4; e++) {
                EstadisticasEquipo est = grupos[g][e];
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