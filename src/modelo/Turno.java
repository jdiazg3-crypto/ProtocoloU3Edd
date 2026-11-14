package modelo;

import java.util.Objects;

public class Turno implements Comparable<Turno> {

    private final String codigo;
    private final Cliente cliente;
    private final long numeroLlegada;
    private String tipoServicio;
    private Prioridad prioridad;
    private EstadoTurno estado;

    public Turno(String codigo, Cliente cliente, String tipoServicio,
                 Prioridad prioridad, long numeroLlegada) {
        if (codigo == null || !codigo.trim().matches("T-\\d{3}")) {
            throw new IllegalArgumentException("El código debe tener el formato T-001.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("El turno debe tener un cliente.");
        }
        this.codigo = codigo.trim();
        this.cliente = cliente;
        this.numeroLlegada = numeroLlegada;
        setTipoServicio(tipoServicio);
        setPrioridad(prioridad);
        this.estado = EstadoTurno.PENDIENTE;
    }

    public String getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public long getNumeroLlegada() { return numeroLlegada; }
    public String getTipoServicio() { return tipoServicio; }
    public Prioridad getPrioridad() { return prioridad; }
    public EstadoTurno getEstado() { return estado; }

    public void setTipoServicio(String tipoServicio) {
        if (tipoServicio == null || tipoServicio.isBlank()) {
            throw new IllegalArgumentException("El tipo de servicio es obligatorio.");
        }
        this.tipoServicio = tipoServicio.trim();
    }

    public void setPrioridad(Prioridad prioridad) {
        if (prioridad == null) {
            throw new IllegalArgumentException("La prioridad no es válida.");
        }
        this.prioridad = prioridad;
    }

    public void setEstado(EstadoTurno estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no es válido.");
        }
        this.estado = estado;
    }

    // Orden natural: por código. Lo usará el TreeSet
    @Override
    public int compareTo(Turno otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    // Dos turnos son iguales si tienen el mismo código
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Turno)) return false;
        return codigo.equals(((Turno) o).codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo + " | " + cliente.getNombre() + " | " + tipoServicio
                + " | " + prioridad + " | " + estado;
    }
}