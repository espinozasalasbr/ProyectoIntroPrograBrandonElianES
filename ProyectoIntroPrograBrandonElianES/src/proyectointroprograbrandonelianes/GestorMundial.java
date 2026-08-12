/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointroprograbrandonelianes;

import java.util.Random;


/**
 *
 * @author espin
 */
public class GestorMundial {
    //Arreglos de objetos
    private Equipo[] equipos;
    private EstadisticasEquipo[][] matrizGrupos;
    private Partido[] calendarioPartidos;
    
    //Objetos y variables
    private Equipo campeon;
    private Equipo subcampeon;
    private double recaudacionTotal;
    private int cantidadEquipos, cantidadGrupos, asistenciaTotal, partidosSimulados;
    
    //booleanos de control
    private boolean configurado = false;
    private boolean sorteoRealizado = false;
    private boolean faseGruposCompletada = false;
    private boolean llavesCompletadas = false;

    public boolean isConfigurado() {
        return configurado;
    }

    public boolean isSorteoRealizado() {
        return sorteoRealizado;
    }

    public boolean isFaseGruposCompletada() {
        return faseGruposCompletada;
    }

    public boolean isLlavesCompletadas() {
        return llavesCompletadas;
    }
    
    //configuracion de tamaño inicial
    public void configurarTamano(int tamano) {
    this.cantidadEquipos = tamano;
    this.equipos = new Equipo[tamano];
    for (int i = 0; i < tamano; i++) {
        this.equipos[i] = new Equipo("Equipo " + (i + 1), "DT " + (i + 1), 11);
    }
    this.configurado = true;
    }
    
    //Generador de demo
    public void genDemo() {
        for (int i = 0; i < equipos.length; i++) {
            equipos[i] = new Equipo("Pais_"+(i+1),"DT_"+(i+1),11);
        }
    }
    
    //generador de sorteo
    public void genSorteo() {
        cantidadGrupos = cantidadEquipos/4;
        matrizGrupos = new EstadisticasEquipo[cantidadGrupos][4];
        
        Equipo[] bolsa = new Equipo[cantidadEquipos];
        for (int i = 0; i < cantidadEquipos; i++) {
            bolsa[i] = equipos[i];
        }
        Random random = new Random();
        for (int i = 0; i < bolsa.length; i++) {
            int aleatorio = random.nextInt(bolsa.length);
            Equipo temp = bolsa[i];
            bolsa[i] = bolsa[aleatorio];
            bolsa[aleatorio] = temp;
        }

        int ind = 0;
        for (int j = 0; j < cantidadGrupos; j++) {
            for (int h = 0; h < 4; h++) {
                matrizGrupos[j][h] = new EstadisticasEquipo(bolsa[ind++]);
            }
        }
        // calendario
        int totalPartidos = cantidadGrupos*6;
        calendarioPartidos = new Partido[totalPartidos];
        int posPartido = 0;

        for (int i = 0; i < cantidadGrupos; i++) {
            for (int j = 0; j < 3; j++) {
                for (int h = j + 1; h < 4; h++) {
                    calendarioPartidos[posPartido++] = new Partido(
                        matrizGrupos[i][j].getEquipo(),
                        matrizGrupos[i][h].getEquipo()
                    );
                }
            }
        }
        this.partidosSimulados = 0;
        this.sorteoRealizado = true;
        this.faseGruposCompletada = false;
    }
    // Goles de jugadores individuales
    private String GolesDeJugadores(Equipo equipo, int goles, Random random) {
        if (goles == 0) return "Sin goles";
        StringBuilder reportJugador = new StringBuilder();
        for (int i = 0; i < goles; i++) {
            int idx = random.nextInt(equipo.getJugadores().length);
            Jugador jugador = equipo.getJugadores()[idx];
            jugador.setGoles(jugador.getGoles() + 1);
            reportJugador.append(jugador.getNombre()).append(" ");
        }
        return reportJugador.toString();
    }
        
    //Gerador de tarjetas amarillas y rojas
    private String genTarjetas(Equipo equipo, Random random) {
        StringBuilder reportTarjetas = new StringBuilder();
        if (random.nextBoolean()) {
            int ind = random.nextInt(equipo.getJugadores().length);
            Jugador jugador = equipo.getJugadores()[ind];
            jugador.setTarjetasAmarillas(jugador.getTarjetasAmarillas() + 1);
            reportTarjetas.append("Tarjeta amarilla ").append(jugador.getNombre()).append(" ");
        }
        if (random.nextInt(8) == 0) {
            int ind = random.nextInt(equipo.getJugadores().length);
            Jugador jugador = equipo.getJugadores()[ind];
            jugador.setTarjetasRojas(jugador.getTarjetasRojas() + 1);
            reportTarjetas.append("Tarjeta roja ").append(jugador.getNombre()).append(" ");
        }
        
        if (reportTarjetas.length() == 0){
            return "Sin tarjetas";
        } else {
            return reportTarjetas.toString();
        }
    }
    
    //ordena grupos
    private boolean masPuntos(EstadisticasEquipo equipo1, EstadisticasEquipo equipo2) {
        if (equipo1.getPuntos() != equipo2.getPuntos()) return equipo1.getPuntos() > equipo2.getPuntos();
        return equipo1.getDiferenciaGoles() > equipo2.getDiferenciaGoles();
    }
    
    private void ordenarGrupo(EstadisticasEquipo[] grupo) {
        for (int i = 0; i < grupo.length - 1; i++) {
            for (int j = 0; j < grupo.length - i - 1; j++) {
                if (masPuntos(grupo[j + 1], grupo[j])) {
                    EstadisticasEquipo temp = grupo[j];
                    grupo[j] = grupo[j + 1];
                    grupo[j + 1] = temp;
                }
            }
        }
    }

    //Simulador de partidos uno por uno
    public String simuladorPartido() {
        if (faseGruposCompletada || partidosSimulados >= calendarioPartidos.length) {
            faseGruposCompletada = true;
            return "La Fase de Grupos ya ha finalizado por completo.";
        }

        Partido partido = calendarioPartidos[partidosSimulados];
        Random random = new Random();

        int g1 = random.nextInt(6);
        int g2 = random.nextInt(6);
        partido.Resultado(g1, g2);

        actualizarEstadisticasEquipo(partido.getEquipo1(), g1, g2);
        actualizarEstadisticasEquipo(partido.getEquipo2(), g2, g1);

        StringBuilder result = new StringBuilder();
        result.append("PARTIDO #").append(partidosSimulados + 1).append(": ")
               .append(partido.getEquipo1().getSeleccion()).append(" ").append(g1)
               .append(" - ").append(g2).append(" ").append(partido.getEquipo2().getSeleccion()).append("\n");

        // goles por equipo
        result.append("  Goles ").append(partido.getEquipo1().getSeleccion()).append(": ");
        result.append(GolesDeJugadores(partido.getEquipo1(), g1, random)).append("\n");
        result.append("  Goles ").append(partido.getEquipo2().getSeleccion()).append(": ");
        result.append(GolesDeJugadores(partido.getEquipo2(), g2, random)).append("\n");

        // Tarjetas
        result.append("  Incidencias: ").append(genTarjetas(partido.getEquipo1(), random))
               .append(" | ").append(genTarjetas(partido.getEquipo2(), random)).append("\n");

        int asistencia = 25000 + random.nextInt(25000);
        this.asistenciaTotal += asistencia;
        this.recaudacionTotal += (asistencia * 50.0);

        partidosSimulados++;
        if (partidosSimulados >= calendarioPartidos.length) {
            faseGruposCompletada = true;
        }

        return result.toString();
    }
    
    //Simulador de partidos fase completa
    public String simuladorFaseCompleta() {
        StringBuilder resultCompletos = new StringBuilder();
        while (!faseGruposCompletada) {
            resultCompletos.append(simuladorPartido()).append("--------------------------------------------------\n");
        }
        return resultCompletos.toString();
    }
    
    //Estadisticas de Equipo
    private void actualizarEstadisticasEquipo(Equipo equipo, int golFavor, int golContra) {
        for (int i = 0; i < cantidadGrupos; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrizGrupos[i][j].getEquipo() == equipo) {
                    EstadisticasEquipo estadisticas = matrizGrupos[i][j];
                    estadisticas.setGolesFavor(estadisticas.getGolesFavor() + golFavor);
                    estadisticas.setGolesContra(estadisticas.getGolesContra() + golContra);
                    
                    if (golFavor > golContra) {
                        estadisticas.setPuntos(estadisticas.getPuntos() + 3);
                    } else if (golFavor == golContra) {
                        estadisticas.setPuntos(estadisticas.getPuntos() + 1);
                    }
                    return;
                }
            }
        }
    }

    // generador de clasificados y llaves
    public String genClasificadosYLlaves() {
        int tamanoLlave;
        if (cantidadEquipos == 24 || cantidadEquipos == 32){
            tamanoLlave = 16;
        }else{
            tamanoLlave = 32;
        }
        
        Equipo[] participantesLlaves = new Equipo[tamanoLlave];
        int pos = 0;

        for (int i = 0; i < cantidadGrupos; i++) {
            ordenarGrupo(matrizGrupos[i]);
            participantesLlaves[pos++] = matrizGrupos[i][0].getEquipo();
            participantesLlaves[pos++] = matrizGrupos[i][1].getEquipo();
        }

        //Logica de Mejores Terceros
        if (cantidadEquipos == 24 || cantidadEquipos == 48) {
            int faltantes = tamanoLlave - pos;
            EstadisticasEquipo[] terceros = new EstadisticasEquipo[cantidadGrupos];
            for (int i = 0; i < cantidadGrupos; i++) {
                terceros[i] = matrizGrupos[i][2];
            }

            for (int i = 0; i < terceros.length - 1; i++) {
                for (int j = 0; j < terceros.length - i - 1; j++) {
                    if (masPuntos(terceros[j + 1], terceros[j])) {
                        EstadisticasEquipo temp = terceros[j];
                        terceros[j] = terceros[j + 1];
                        terceros[j + 1] = temp;
                    }
                }
            }

            for (int i = 0; i < faltantes; i++) {
                participantesLlaves[pos++] = terceros[i].getEquipo();
            }
        }

        StringBuilder reporteLlaves = new StringBuilder("---ÁRBOL DE LLAVES---\n\n");
        Equipo[] rondaActual = participantesLlaves;
        Random random = new Random();

        while (rondaActual.length > 1) {
            reporteLlaves.append("--- RONDA DE ").append(rondaActual.length).append(" EQUIPOS ---\n");
            Equipo[] siguienteRonda = new Equipo[rondaActual.length / 2];
            // goles
            for (int i = 0; i < rondaActual.length; i += 2) {
                Equipo equipo1 = rondaActual[i];
                Equipo equipo2 = rondaActual[i + 1];
                int gol1 = random.nextInt(6);
                int gol2 = random.nextInt(6);
                String infoPenales = "";

                Equipo ganador;
                Equipo perdedor;
                
                //Penales
                if (gol1 == gol2) { 
                    int penal1 = 3 + random.nextInt(3);
                    int penal2 = 3 + random.nextInt(3);
                    while (penal1 == penal2) penal1 = 3 + random.nextInt(3);
                    if(penal1 > penal2){
                        ganador = equipo1;
                        perdedor = equipo2;
                    }else{
                        ganador = equipo2;
                        perdedor = equipo1;
                    }
                    
                    infoPenales = " [Penales: " + penal1 + " - " + penal2 + "]";
                } else {
                    if(gol1 > gol2){
                        ganador = equipo1;
                        perdedor = equipo2;       
                    }else{
                        ganador = equipo2;
                        perdedor = equipo1;
                    }
                }
                // ganador
                siguienteRonda[i/2] = ganador;
                reporteLlaves.append(equipo1.getSeleccion()).append(" ").append(gol1).append(" - ")
                         .append(gol2).append(" ").append(equipo2.getSeleccion())
                         .append(infoPenales).append("Clasifica: ")
                         .append(ganador.getSeleccion()).append("\n");
                // define campeon en la ultima ronda
                if (rondaActual.length == 2) {
                    campeon = ganador;
                    subcampeon = perdedor;
                }
            }
            reporteLlaves.append("\n");
            rondaActual = siguienteRonda;
        }

        reporteLlaves.append("CAMPEON DEL MUNDO: ").append(campeon.getSeleccion()).append("\n");
        reporteLlaves.append("SUBCAMPEÓN: ").append(subcampeon.getSeleccion()).append("\n");

        this.llavesCompletadas = true;
        return reporteLlaves.toString();
    }
    
    //generador reporte jugadores
    private Jugador[] todosLosJugadores() {
        Jugador[] lista = new Jugador[cantidadEquipos * 11];
        int ind = 0;
        for (int i = 0; i < cantidadEquipos; i++) {
            Jugador[] plantilla = equipos[i].getJugadores();
            for (int j = 0; j < plantilla.length; j++) {
                lista[ind++] = plantilla[j];
            }
        }
        return lista;
    }

    //ordena jugadores por goles
    private void jugadoresPorGoles(Jugador[] lista) {
        int n = lista.length;
        for (int i = 0; i < n - 1; i++) {
            for (int f = i + 1; f < n; f++) {
                if (lista[f].getGoles() > lista[i].getGoles()) {
                    Jugador temp = lista[i];
                    lista[i] = lista[f];
                    lista[f] = temp;
                }
            }
        }
    }

    //ordena jugadores por tarjeta
    private void jugadoresPorTarjetas(Jugador[] lista) {
        boolean intercam;
        do {
            intercam = false;
            for (int i = 0; i < lista.length - 1; i++) {
                if (lista[i].getTotalIncidencias() < lista[i + 1].getTotalIncidencias()) {
                    Jugador temp = lista[i];
                    lista[i] = lista[i + 1];
                    lista[i + 1] = temp;
                    intercam = true;
                }
            }
        } while (intercam);
    }
    
    //genera reporte final
    public String genReporteFinal() {
        StringBuilder reporteFinal = new StringBuilder();
        reporteFinal.append("---RESUMEN GLOBAL Y ESTADÍSTICAS FINALES---\n\n");
        reporteFinal.append("CAMPEÓN: ").append(campeon.getSeleccion()).append("\n");
        reporteFinal.append("SUBCAMPEÓN: ").append(subcampeon.getSeleccion()).append("\n\n");

        reporteFinal.append("--- BOTA DE ORO (TOP 5 GOLEADORES) ---\n");
        Jugador[] todosJugadores = todosLosJugadores();
        jugadoresPorGoles(todosJugadores);

        for (int i = 0; i < 5 && i < todosJugadores.length; i++) {
            if (todosJugadores[i] != null && todosJugadores[i].getGoles() > 0) {
                reporteFinal.append((i + 1)).append(". ").append(todosJugadores[i].getNombre())
                  .append(" | Goles: ").append(todosJugadores[i].getGoles()).append("\n");
            }
        }

        reporteFinal.append("\n--- REPORTE DISCIPLINARIO GLOBAL (JUGADORES MÁS INFRACTORES) ---\n");
        jugadoresPorTarjetas(todosJugadores);
        int cont = 0;
        for (int i = 0; i < todosJugadores.length; i++) {
            if (todosJugadores[i].getTotalIncidencias() > 0 && cont < 10) {
                reporteFinal.append(todosJugadores[i].getNombre())
                  .append(" | Amarillas: ").append(todosJugadores[i].getTarjetasAmarillas())
                  .append(" | Rojas: ").append(todosJugadores[i].getTarjetasRojas())
                  .append(" | Total Incidencias: ").append(todosJugadores[i].getTotalIncidencias()).append("\n");
                cont++;
            }
        }

        reporteFinal.append("\n--- METRICAS FINANCIERAS Y ASISTENCIA ---\n");
        reporteFinal.append("Asistencia Total: ").append(asistenciaTotal).append(" espectadores\n");
        reporteFinal.append("Recaudación Entradas: $").append((long) recaudacionTotal).append(".00\n");

        return reporteFinal.toString();
    }
    
    //comprueba si esta configurado
    public boolean estaConfig(){
        return equipos != null;
    }

    public Equipo[] getEquipos() {
        return equipos;
    }

    public void setEquipos(Equipo[] equipos) {
        this.equipos = equipos;
    }

    public int getCantidadEquipos() {
        return cantidadEquipos;
    }

    public void setCantidadEquipos(int cantidadEquipos) {
        this.cantidadEquipos = cantidadEquipos;
    }

    public int getCantidadGrupos() {
        return cantidadGrupos;
    }

    public void setCantidadGrupos(int cantidadGrupos) {
        this.cantidadGrupos = cantidadGrupos;
    }

    public EstadisticasEquipo[][] getMatrizGrupos() {
        return matrizGrupos;
    }

    public void setMatrizGrupos(EstadisticasEquipo[][] matrizGrupos) {
        this.matrizGrupos = matrizGrupos;
    }

    
    
    
    
}


