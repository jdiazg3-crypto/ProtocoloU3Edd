package ui;

import modelo.Cliente;
import modelo.Prioridad;
import modelo.Turno;
import servicio.GestorCentroServicios;

import java.util.Collection;
import java.util.Scanner;

public class MenuConsola {

    private final GestorCentroServicios gestor;
    private final Scanner scanner = new Scanner(System.in);

    public MenuConsola(GestorCentroServicios gestor) {
        this.gestor = gestor;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            try {
                ejecutar(opcion);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n===== CENTRO DE SERVICIOS =====");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar turno");
        System.out.println("3. Ver todos los turnos");
        System.out.println("4. Buscar turno por código");
        System.out.println("5. Registrar o consultar tipos de servicio");
        System.out.println("6. Ver turnos ordenados por código");
        System.out.println("7. Ver próximo turno a atender");
        System.out.println("8. Atender siguiente turno");
        System.out.println("9. Modificar turno");
        System.out.println("10. Cancelar o eliminar turno");
        System.out.println("11. Mostrar estado de las colecciones");
        System.out.println("0. Salir");
    }

    private void ejecutar(int opcion) {
        switch (opcion) {
            case 1: registrarCliente(); break;
            case 2: registrarTurno(); break;
            case 3: mostrarTurnos(gestor.listarTurnos()); break;
            case 4: buscarTurno(); break;
            case 5: tiposServicio(); break;
            case 6: mostrarTurnos(gestor.listarTurnosOrdenados()); break;
            case 7: verProximo(); break;
            case 8: atenderSiguiente(); break;
            case 9: modificarTurno(); break;
            case 10: cancelarOEliminar(); break;
            case 11: System.out.println(gestor.resumenColecciones()); break;
            case 0: System.out.println("Hasta pronto."); break;
            default: System.out.println("Opción no válida.");
        }
    }

    private void registrarCliente() {
        String documento = leerTexto("Documento: ");
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        gestor.registrarCliente(new Cliente(documento, nombre, telefono));
        System.out.println("Cliente registrado.");
    }

    private void registrarTurno() {
        String codigo = leerTexto("Código del turno (ej. T-001): ");
        String documento = leerTexto("Documento del cliente: ");
        String tipo = leerTexto("Tipo de servicio: ");
        Prioridad prioridad = leerPrioridad();
        Turno turno = gestor.registrarTurno(codigo, documento, tipo, prioridad);
        System.out.println("Turno registrado: " + turno);
    }

    private void buscarTurno() {
        String codigo = leerTexto("Código a buscar: ");
        Turno turno = gestor.buscarTurno(codigo);
        if (turno == null) {
            System.out.println("No existe ningún turno con el código " + codigo);
        } else {
            System.out.println("Encontrado: " + turno);
        }
    }

    private void tiposServicio() {
        System.out.println("1. Registrar tipo de servicio");
        System.out.println("2. Ver tipos registrados");
        int opcion = leerEntero("Opción: ");
        if (opcion == 1) {
            String tipo = leerTexto("Nuevo tipo de servicio: ");
            if (gestor.registrarTipoServicio(tipo)) {
                System.out.println("Tipo de servicio registrado.");
            } else {
                System.out.println("Ese tipo de servicio ya estaba registrado.");
            }
        } else if (opcion == 2) {
            System.out.println("Tipos de servicio: " + gestor.listarTiposServicio());
        } else {
            System.out.println("Opción no válida.");
        }
    }

    private void verProximo() {
        Turno turno = gestor.verProximoTurno();
        if (turno == null) {
            System.out.println("No hay turnos pendientes.");
        } else {
            System.out.println("Próximo turno: " + turno);
        }
    }

    private void atenderSiguiente() {
        Turno turno = gestor.atenderSiguienteTurno();
        if (turno == null) {
            System.out.println("No hay turnos pendientes.");
        } else {
            System.out.println("Atendiendo: " + turno);
        }
    }

    private void modificarTurno() {
        String codigo = leerTexto("Código del turno: ");
        System.out.println("1. Cambiar prioridad");
        System.out.println("2. Cambiar tipo de servicio");
        int opcion = leerEntero("Opción: ");
        if (opcion == 1) {
            gestor.modificarPrioridad(codigo, leerPrioridad());
            System.out.println("Prioridad actualizada.");
        } else if (opcion == 2) {
            gestor.modificarTipoServicio(codigo, leerTexto("Nuevo tipo de servicio: "));
            System.out.println("Tipo de servicio actualizado.");
        } else {
            System.out.println("Opción no válida.");
        }
    }

    private void cancelarOEliminar() {
        String codigo = leerTexto("Código del turno: ");
        System.out.println("1. Cancelar (queda en el historial)");
        System.out.println("2. Eliminar definitivamente");
        int opcion = leerEntero("Opción: ");
        if (opcion == 1) {
            gestor.cancelarTurno(codigo);
            System.out.println("Turno cancelado.");
        } else if (opcion == 2) {
            gestor.eliminarTurno(codigo);
            System.out.println("Turno eliminado de todas las colecciones.");
        } else {
            System.out.println("Opción no válida.");
        }
    }

    private void mostrarTurnos(Collection<Turno> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }
        for (Turno turno : lista) {
            System.out.println(turno);
        }
    }

    private Prioridad leerPrioridad() {
        Prioridad[] valores = Prioridad.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }
        int opcion = leerEntero("Prioridad: ");
        if (opcion < 1 || opcion > valores.length) {
            throw new IllegalArgumentException("Prioridad inválida.");
        }
        return valores[opcion - 1];
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
            }
        }
    }
}