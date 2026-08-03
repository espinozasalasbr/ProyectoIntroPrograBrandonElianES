/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointroprograbrandonelianes;

/**
 *
 * @author espin
 */
public class Jugador {
    //Objeto
    private String nombre, pais;
    private int goles, tarjetasAmarillas, tarjetasRojas;
    
    public int getTotalIncidencias(){
        return (tarjetasRojas*2)+tarjetasAmarillas;
    }

    public Jugador(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
        this.goles = 0;
        this.tarjetasAmarillas = 0;
        this.tarjetasRojas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public void setTarjetasAmarillas(int tarjetasAmarillas) {
        this.tarjetasAmarillas = tarjetasAmarillas;
    }

    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    public void setTarjetasRojas(int tarjetasRojas) {
        this.tarjetasRojas = tarjetasRojas;
    }

    


}
