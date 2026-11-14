package servicio;

import modelo.Cliente;

import java.util.HashMap;
import java.util.Map;

public class GestorCentroServicios {

    // Búsqueda directa de clientes por documento
    private final Map<String, Cliente> clientesPorDocumento = new HashMap<>();

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
}