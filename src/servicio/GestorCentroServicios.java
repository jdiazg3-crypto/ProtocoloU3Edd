package servicio;

import modelo.Cliente;
import modelo.Prioridad;
import modelo.Turno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorCentroServicios {

    // Registro general: conserva el orden de registro
    private final List<Turno> turnos = new ArrayList<>();

    // Búsqueda directa de clientes por documento
    private final Map<String, Cliente> clientesPorDocumento = new HashMap<>();

    // Consecutivo que se asigna a cada turno al llegar
    private long contadorLlegada = 0;

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

    public Turno registrarTurno(String codigo, String documentoCliente,
                                String tipoServicio, Prioridad prioridad) {
        Cliente cliente = clientesPorDocumento.get(documentoCliente);
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "No existe un cliente con el documento " + documentoCliente);
        }

        Turno nuevo = new Turno(codigo, cliente, tipoServicio, prioridad, contadorLlegada + 1);

        // contains() usa equals(), o sea compara por código
        if (turnos.contains(nuevo)) {
            throw new IllegalArgumentException("Ya existe un turno con el código " + codigo);
        }

        contadorLlegada++;
        turnos.add(nuevo);
        return nuevo;
    }

    public List<Turno> listarTurnos() {
        return Collections.unmodifiableList(turnos);
    }
}