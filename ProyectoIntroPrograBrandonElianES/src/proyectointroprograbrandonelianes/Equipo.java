/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointroprograbrandonelianes;

/**
 *
 * @author espin
 */
public class Equipo {
    //Objeto
    private String seleccion, directorTecnico;
    private Jugador[] jugadores;

    public Equipo(String seleccion, String directorTecnico, int cantidadJugadores) {
        this.seleccion = seleccion;
        this.directorTecnico = directorTecnico;
        this.jugadores =  new Jugador[cantidadJugadores];
        
        //Relleno automatico de jugadores
        for (int i = 0; i < cantidadJugadores; i++) {
            this.jugadores[i] = new Jugador("Jugador "+(i+1)+"("+seleccion+")", seleccion);
        }
    }

    public String getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }

    public String getDirectorTecnico() {
        return directorTecnico;
    }

    public void setDirectorTecnico(String directorTecnico) {
        this.directorTecnico = directorTecnico;
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }
    
    
}

