/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointroprograbrandonelianes;

/**
 *
 * @author espin
 */
public class Sede {
    //Objeto
    private String nombreEstadio, ciudad;
    private int capacidadEstadio;

    public Sede(String nombreEstadio, String ciudad, int capacidadEstadio) {
        this.nombreEstadio = nombreEstadio;
        this.ciudad = ciudad;
        this.capacidadEstadio = capacidadEstadio;
    }

    public String getNombreEstadio() {
        return nombreEstadio;
    }

    public void setNombreEstadio(String nombreEstadio) {
        this.nombreEstadio = nombreEstadio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCapacidadEstadio() {
        return capacidadEstadio;
    }

    public void setCapacidadEstadio(int capacidadEstadio) {
        this.capacidadEstadio = capacidadEstadio;
    }

    
}    
