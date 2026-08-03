/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointroprograbrandonelianes;
import javax.swing.*;
import java.awt.*;
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

        setTitle("Copa Mundial Java - SC-202");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("MÓDULO 1: Configuración & Edición", crearPanelModulo1());
        pestañas.addTab("MÓDULO 2: Sorteo", crearPanelModulo2());
        pestañas.addTab("MÓDULO 4: Partidos", crearPanelModulo4());
        pestañas.addTab("MÓDULO 5: Llaves", crearPanelModulo5());
        pestañas.addTab("MÓDULO 6: Reportes", crearPanelModulo6());

        add(pestañas);
    }

    private JPanel crearPanelModulo1() {
        JPanel panel = new JPanel(null);

        JLabel lblTamano = new JLabel("1. Tamaño del Torneo:");
        lblTamano.setBounds(20, 15, 180, 25);
        panel.add(lblTamano);

        Integer[] opciones = {24, 32, 48, 64};
        JComboBox<Integer> comboTamanos = new JComboBox<>(opciones);
        comboTamanos.setBounds(180, 15, 80, 25);
        panel.add(comboTamanos);

        JButton btnAplicar = new JButton("Configurar Tamaño");
        btnAplicar.setBounds(280, 15, 180, 25);
        panel.add(btnAplicar);

        JButton btnDemo = new JButton("2. Cargar Datos Demo");
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

        btnAplicar.addActionListener(e -> {
            int size = (int) comboTamanos.getSelectedItem();
            gestor.configurarTamano(size);
            comboIndiceEquipo.removeAllItems();
            for (int i = 0; i < size; i++) comboIndiceEquipo.addItem(i);
            JOptionPane.showMessageDialog(this, "Torneo dimensionado para " + size + " equipos.");
        });

        btnDemo.addActionListener(e -> {
            if (!gestor.isConfigurado()) {
                JOptionPane.showMessageDialog(this, "Error: Debe configurar el tamaño primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                return;
            }
            gestor.genDemo();
            JOptionPane.showMessageDialog(this, "Datos de demostración cargados en memoria.");
        });

        btnGuardarEquipo.addActionListener(e -> {
            if (!gestor.isConfigurado() || comboIndiceEquipo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Error: Configure el torneo primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idx = (int) comboIndiceEquipo.getSelectedItem();
            String p = txtPais.getText().trim();
            String dt = txtDT.getText().trim();
            if (!p.isEmpty()) gestor.getEquipos()[idx].setSeleccion(p);
            if (!dt.isEmpty()) gestor.getEquipos()[idx].setDirectorTecnico(dt);
            JOptionPane.showMessageDialog(this, "Entidad [" + idx + "] actualizada.");
        });

        return panel;
    }

    private JPanel crearPanelModulo2() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnSorteo = new JButton("Realizar Sorteo Aleatorio de Grupos");
        txtAreaGrupos = new JTextArea();
        txtAreaGrupos.setEditable(false);
        panel.add(btnSorteo, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaGrupos), BorderLayout.CENTER);

        btnSorteo.addActionListener(e -> {
            if (!gestor.isConfigurado()) {
                JOptionPane.showMessageDialog(this, "Error: Debe configurar el Módulo 1 primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                return;
            }
            gestor.genSorteo();
            actualizarTablasGrupos();
            JOptionPane.showMessageDialog(this, "Sorteo completado con éxito.");
        });

        return panel;
    }

    private JPanel crearPanelModulo4() {
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

        btnUnPartido.addActionListener(e -> {
            if (!gestor.isSorteoRealizado()) {
                JOptionPane.showMessageDialog(this, "Error: Debe realizar el sorteo en el Módulo 2 primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String log = gestor.simuladorPartido();
            txtAreaPartidos.append(log + "\n");
            actualizarTablasGrupos();
        });

        btnTodaFase.addActionListener(e -> {
            if (!gestor.isSorteoRealizado()) {
                JOptionPane.showMessageDialog(this, "Error: Debe realizar el sorteo en el Módulo 2 primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String logCompleto = gestor.simuladorFaseCompleta();
            txtAreaPartidos.setText(logCompleto);
            actualizarTablasGrupos();
            JOptionPane.showMessageDialog(this, "Fase de grupos completamente simulada.");
        });

        return panel;
    }

    private JPanel crearPanelModulo5() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnLlaves = new JButton("Calcular Clasificados y Simular Llaves");
        txtAreaLlaves = new JTextArea();
        txtAreaLlaves.setEditable(false);

        panel.add(btnLlaves, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaLlaves), BorderLayout.CENTER);

        btnLlaves.addActionListener(e -> {
            if (!gestor.isFaseGruposCompletada()) {
                JOptionPane.showMessageDialog(this, "Error: Debe completar la simulación de la Fase de Grupos primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String res = gestor.genClasificadosYLlaves();
            txtAreaLlaves.setText(res);
        });

        return panel;
    }

    private JPanel crearPanelModulo6() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnReporte = new JButton("Generar Resumen Global y Estadísticas Finales");
        txtAreaReporte = new JTextArea();
        txtAreaReporte.setEditable(false);

        panel.add(btnReporte, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaReporte), BorderLayout.CENTER);

        btnReporte.addActionListener(e -> {
            if (!gestor.isLlavesCompletadas()) {
                JOptionPane.showMessageDialog(this, "Error: Debe completar las Llaves Eliminatorias en el Módulo 5 primero.", "Error de Secuencia", JOptionPane.ERROR_MESSAGE);
                return;
            }
            txtAreaReporte.setText(gestor.genReporteFinal());
        });

        return panel;
    }

    private void actualizarTablasGrupos() {
        StringBuilder sb = new StringBuilder();
        EstadisticasEquipo[][] grupos = gestor.getMatrizGrupos();
        if (grupos == null) return;

        for (int g = 0; g < gestor.getCantidadGrupos(); g++) {
            sb.append("=== GRUPO ").append((char) ('A' + g)).append(" ===\n");
            sb.append(String.format("%-15s | Pts | GF | GC | DG\n", "País"));
            sb.append("------------------------------------------\n");
            for (int e = 0; e < 4; e++) {
                EstadisticasEquipo est = grupos[g][e];
                sb.append(String.format("%-15s |  %2d | %2d | %2d | %2d\n",
                        est.getEquipo().getSeleccion(), est.getPuntos(),
                        est.getGolesFavor(), est.getGolesContra(), est.getDiferenciaGoles()));
            }
            sb.append("\n");
        }
        txtAreaGrupos.setText(sb.toString());
    }
}