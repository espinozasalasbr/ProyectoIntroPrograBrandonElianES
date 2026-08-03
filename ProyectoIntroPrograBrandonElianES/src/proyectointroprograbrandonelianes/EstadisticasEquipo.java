/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointroprograbrandonelianes;

/**
 *
 * @author espin
 */
public class EstadisticasEquipo {
    private Equipo equipo;
    private int puntos, golesFavor, golesContra;

    public EstadisticasEquipo(Equipo equipo) {
        this.equipo = equipo;
        this.puntos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    public int getDiferenciaGoles() {
        return golesFavor = golesContra;
    }
    
}
