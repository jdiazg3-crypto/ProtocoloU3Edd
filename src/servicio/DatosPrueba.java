package servicio;

import modelo.Cliente;
import modelo.Prioridad;

// Carga datos preparados para demostrar el comportamiento de las colecciones
public class DatosPrueba {

    public static void cargar(GestorCentroServicios gestor) {
        // 5 clientes
        gestor.registrarCliente(new Cliente("1001", "Ana Perez", "3001112233"));
        gestor.registrarCliente(new Cliente("1002", "Luis Gomez", "3012223344"));
        gestor.registrarCliente(new Cliente("1003", "Marta Diaz", "3023334455"));
        gestor.registrarCliente(new Cliente("1004", "Carlos Ruiz", "3034445566"));
        gestor.registrarCliente(new Cliente("1005", "Sofia Mena", "3045556677"));

        // 5 tipos de servicio
        gestor.registrarTipoServicio("Pagos");
        gestor.registrarTipoServicio("Reclamos");
        gestor.registrarTipoServicio("Consultas");
        gestor.registrarTipoServicio("Asesoria");
        gestor.registrarTipoServicio("Certificados");

        // Intento repetido a propósito: el HashSet lo rechaza (add devuelve false)
        gestor.registrarTipoServicio("Pagos");

        // 16 turnos registrados en desorden de prioridad
        gestor.registrarTurno("T-001", "1001", "Pagos", Prioridad.BAJA);
        gestor.registrarTurno("T-002", "1002", "Reclamos", Prioridad.NORMAL);
        gestor.registrarTurno("T-003", "1003", "Consultas", Prioridad.ALTA);
        gestor.registrarTurno("T-004", "1001", "Certificados", Prioridad.BAJA);
        gestor.registrarTurno("T-005", "1004", "Pagos", Prioridad.URGENTE);
        gestor.registrarTurno("T-006", "1005", "Asesoria", Prioridad.NORMAL);
        gestor.registrarTurno("T-007", "1002", "Consultas", Prioridad.BAJA);
        gestor.registrarTurno("T-008", "1003", "Pagos", Prioridad.NORMAL);
        gestor.registrarTurno("T-009", "1004", "Reclamos", Prioridad.ALTA);
        gestor.registrarTurno("T-010", "1005", "Certificados", Prioridad.URGENTE);
        gestor.registrarTurno("T-011", "1001", "Asesoria", Prioridad.ALTA);
        gestor.registrarTurno("T-012", "1002", "Pagos", Prioridad.BAJA);
        gestor.registrarTurno("T-013", "1003", "Reclamos", Prioridad.NORMAL);
        gestor.registrarTurno("T-014", "1004", "Consultas", Prioridad.ALTA);
        gestor.registrarTurno("T-015", "1005", "Pagos", Prioridad.BAJA);
        gestor.registrarTurno("T-016", "1001", "Reclamos", Prioridad.URGENTE);
    }
}