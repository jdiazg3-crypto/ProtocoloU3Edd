package modelo;

public enum Prioridad {
    URGENTE(1),
    ALTA(2),
    NORMAL(3),
    BAJA(4);

    // Mientras más bajo el nivel, más importante es el turno
    private final int nivel;

    Prioridad(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}