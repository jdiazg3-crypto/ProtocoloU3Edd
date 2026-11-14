package servicio;

import modelo.Cliente;
import modelo.Prioridad;
import modelo.Turno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import comparadores.ComparadorPrioridad;
import modelo.EstadoTurno;
import java.util.PriorityQueue;
import java.util.Queue;


public class GestorCentroServicios {

    // Registro general: conserva el orden de registro
    private final List<Turno> turnos = new ArrayList<>();

    // Búsqueda directa de clientes por documento
    private final Map<String, Cliente> clientesPorDocumento = new HashMap<>();

    // Consecutivo que se asigna a cada turno al llegar
    private long contadorLlegada = 0;
    // Turnos siempre ordenados por código (orden natural: Turno.compareTo)
    private final TreeSet<Turno> turnosOrdenados = new TreeSet<>();
    // Turnos pendientes: sale primero el de mayor prioridad, no el que llegó primero
    private final Queue<Turno> turnosPendientes = new PriorityQueue<>(new ComparadorPrioridad());

    public void registrarCliente(Cliente cliente) {
        if (clientesPorDocumento.containsKey(cliente.getDocumento())) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con el documento " + cliente.getDocumento());
        }
        clientesPorDocumento.put(cliente.getDocumento(), cliente);
    }

    public Cliente buscarCliente(String documento) {
        return clientesPorDocumento.get(documento);
    }

    public boolean existeCliente(String documento) {
        return clientesPorDocumento.containsKey(documento);
    }
    // Búsqueda directa de turnos por código
    private final Map<String, Turno> turnosPorCodigo = new HashMap<>();
    // Tipos de servicio sin duplicados
    private final Set<String> tiposServicio = new HashSet<>();

    public Turno registrarTurno(String codigo, String documentoCliente,
                                String tipoServicio, Prioridad prioridad) {
        Cliente cliente = clientesPorDocumento.get(documentoCliente);
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "No existe un cliente con el documento " + documentoCliente);
        }

        Turno nuevo = new Turno(codigo, cliente, tipoServicio, prioridad, contadorLlegada + 1);

        if (!tiposServicio.contains(nuevo.getTipoServicio())) {
            throw new IllegalArgumentException(
                    "El tipo de servicio '" + tipoServicio + "' no está registrado.");
        }

        // Ahora la validación consulta el Map y no recorre la lista
        if (turnosPorCodigo.containsKey(nuevo.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un turno con el código " + codigo);
        }

        contadorLlegada++;
        turnos.add(nuevo);
        turnosPorCodigo.put(nuevo.getCodigo(), nuevo);
        turnosOrdenados.add(nuevo);
        turnosPendientes.add(nuevo);
        return nuevo;
    }

    public List<Turno> listarTurnos() {
        return Collections.unmodifiableList(turnos);
    }
    // Devuelve null si el código no existe
    public Turno buscarTurno(String codigo) {
        return turnosPorCodigo.get(codigo);
    }

    public boolean existeTurno(String codigo) {
        return turnosPorCodigo.containsKey(codigo);
    }
    // Devuelve false si el tipo ya estaba registrado
    public boolean registrarTipoServicio(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de servicio es obligatorio.");
        }
        return tiposServicio.add(tipo.trim());
    }

    public boolean existeTipoServicio(String tipo) {
        return tipo != null && tiposServicio.contains(tipo.trim());
    }

    public Set<String> listarTiposServicio() {
        return Collections.unmodifiableSet(tiposServicio);
    }
    public Set<Turno> listarTurnosOrdenados() {
        return Collections.unmodifiableSet(turnosOrdenados);
    }
    // peek() muestra el próximo sin sacarlo de la cola
    public Turno verProximoTurno() {
        return turnosPendientes.peek();
    }

    // poll() saca el de mayor prioridad; devuelve null si no hay pendientes
    public Turno atenderSiguienteTurno() {
        Turno turno = turnosPendientes.poll();
        if (turno != null) {
            turno.setEstado(EstadoTurno.ATENDIDO);
        }
        return turno;
    }

    public int cantidadPendientes() {
        return turnosPendientes.size();
    }
    public void modificarPrioridad(String codigo, Prioridad nuevaPrioridad) {
        Turno turno = turnosPorCodigo.get(codigo);
        if (turno == null) {
            throw new IllegalArgumentException("No existe un turno con el código " + codigo);
        }
        if (turno.getEstado() != EstadoTurno.PENDIENTE) {
            throw new IllegalStateException("Solo se puede cambiar la prioridad de turnos pendientes.");
        }

        // La PriorityQueue no se reordena sola cuando el objeto cambia:
        // se saca, se modifica y se vuelve a insertar
        turnosPendientes.remove(turno);
        turno.setPrioridad(nuevaPrioridad);
        turnosPendientes.add(turno);
    }

    public void modificarTipoServicio(String codigo, String nuevoTipo) {
        Turno turno = turnosPorCodigo.get(codigo);
        if (turno == null) {
            throw new IllegalArgumentException("No existe un turno con el código " + codigo);
        }
        if (!existeTipoServicio(nuevoTipo)) {
            throw new IllegalArgumentException("El tipo de servicio '" + nuevoTipo + "' no está registrado.");
        }
        // El tipo no es clave de ninguna colección, así que solo cambia el objeto
        turno.setTipoServicio(nuevoTipo);
    }

    public void cancelarTurno(String codigo) {
        Turno turno = turnosPorCodigo.get(codigo);
        if (turno == null) {
            throw new IllegalArgumentException("No existe un turno con el código " + codigo);
        }
        if (turno.getEstado() != EstadoTurno.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden cancelar turnos pendientes.");
        }
        turno.setEstado(EstadoTurno.CANCELADO);
        turnosPendientes.remove(turno);
    }
    // Eliminación definitiva: el turno debe desaparecer de TODAS las colecciones
    public void eliminarTurno(String codigo) {
        Turno turno = turnosPorCodigo.remove(codigo);
        if (turno == null) {
            throw new IllegalArgumentException("No existe un turno con el código " + codigo);
        }
        turnos.remove(turno);
        turnosOrdenados.remove(turno);
        turnosPendientes.remove(turno);
    }
    public String resumenColecciones() {
        return "Estado de las colecciones:\n"
                + "  turnos (ArrayList): " + turnos.size() + "\n"
                + "  turnosPorCodigo (HashMap): " + turnosPorCodigo.size() + "\n"
                + "  turnosOrdenados (TreeSet): " + turnosOrdenados.size() + "\n"
                + "  turnosPendientes (PriorityQueue): " + turnosPendientes.size() + "\n"
                + "  clientesPorDocumento (HashMap): " + clientesPorDocumento.size() + "\n"
                + "  tiposServicio (HashSet): " + tiposServicio.size();
    }
}