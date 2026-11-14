import servicio.GestorCentroServicios;
import ui.MenuConsola;

public class Main {
    public static void main(String[] args) {
        GestorCentroServicios gestor = new GestorCentroServicios();
        new MenuConsola(gestor).iniciar();
    }
}