# Sistema de atención en un centro de servicios

**Actividad:** Unidad 3 - Protocolo Individual (Diseño de un sistema de gestión utilizando Java Collections Framework)
**Asignatura:** Estructuras de datos - Ingeniería del software - Universidad de Cartagena
**Estudiante:** Juan Sebastian Diaz Gonzalez
**Tutor:** John Carlos Arrieta Arrieta

## Descripción del problema
Aplicación Java de consola que gestiona clientes, tipos de servicio y turnos de atención
de un centro de servicios. Los turnos se atienden según su prioridad (URGENTE, ALTA, NORMAL,
BAJA) y, a igual prioridad, según su orden de llegada. Se pueden registrar, buscar, modificar,
cancelar y eliminar turnos manteniendo sincronizadas todas las colecciones.

## Versión de Java
Java 17 (ajustar a la versión usada en el proyecto)

## Cómo ejecutar
1. Clonar el repositorio:
   `git clone https://github.com/jdiazg3-crypto/ProtocoloU3Edd.git`
2. Abrir el proyecto en IntelliJ IDEA.
3. Ejecutar la clase `Main` (`src/Main.java`).

Al iniciar se cargan 5 clientes, 5 tipos de servicio y 16 turnos de prueba.

## Estructura del proyecto
- `modelo/`: Cliente, Turno, Prioridad, EstadoTurno
- `servicio/`: GestorCentroServicios (reglas de negocio y colecciones), DatosPrueba
- `comparadores/`: ComparadorPrioridad
- `ui/`: MenuConsola
- `Main.java`: punto de entrada

## Colecciones principales
| Necesidad | Interfaz | Implementación | Justificación |
|---|---|---|---|
| Registro general de turnos | `List<Turno>` | `ArrayList` | Conserva el orden de registro y se recorre con frecuencia |
| Tipos de servicio sin duplicados | `Set<String>` | `HashSet` | Solo importa la unicidad, no el orden |
| Búsqueda de turnos por código | `Map<String, Turno>` | `HashMap` | Acceso directo por clave única |
| Búsqueda de clientes por documento | `Map<String, Cliente>` | `HashMap` | Valida que el cliente exista al crear un turno |
| Turnos ordenados por código | `TreeSet<Turno>` | `TreeSet` | Orden natural mediante `Comparable` |
| Atención por prioridad | `Queue<Turno>` | `PriorityQueue` | Orden definido por `ComparadorPrioridad`, no por llegada |

## Consistencia entre colecciones
Un mismo turno participa en varias colecciones sin duplicarse. Al cambiar la prioridad se
saca y se reinserta en la `PriorityQueue`; al cancelar se saca de la cola y conserva su
estado en el historial; al eliminar se quita de todas las colecciones.

## Estado
Completado.