package comparadores;

import modelo.Turno;

import java.util.Comparator;

public class ComparadorPrioridad implements Comparator<Turno> {

    @Override
    public int compare(Turno a, Turno b) {
        // 1. Primero el de menor nivel (URGENTE = 1 sale antes que BAJA = 4)
        int porPrioridad = Integer.compare(a.getPrioridad().getNivel(),
                b.getPrioridad().getNivel());
        if (porPrioridad != 0) {
            return porPrioridad;
        }
        // 2. Si tienen la misma prioridad, sale primero el que llegó antes
        return Long.compare(a.getNumeroLlegada(), b.getNumeroLlegada());
    }
}