import servicio.DatosPrueba;
import servicio.GestorCentroServicios;
import ui.MenuConsola;

public class Main {
    public static void main(String[] args) {
        GestorCentroServicios gestor = new GestorCentroServicios();
        DatosPrueba.cargar(gestor);
        System.out.println("Datos de prueba cargados.");
        new MenuConsola(gestor).iniciar();
    }
}