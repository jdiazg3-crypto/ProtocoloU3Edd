# Sistema de atención en un centro de servicios

**Actividad:** Unidad 3 - Protocolo Individual (Diseño de un sistema de gestión utilizando Java Collections Framework)
**Asignatura:** Estructuras de datos - Ingeniería del software - Universidad de Cartagena
**Estudiante:** Juan Sebastian Diaz Gonzalez

## Descripción del problema
Aplicación Java de consola que gestiona clientes, tipos de servicio y turnos de atención
de un centro de servicios. Los turnos se atienden según su prioridad y, a igual prioridad,
según su orden de llegada.

## Versión de Java
Java 17 (ajustar a la versión usada en el proyecto)

## Cómo ejecutar
1. Clonar el repositorio:
   `git clone https://github.com/jdiazg3-crypto/ProtocoloU3Edd.git`
2. Abrir el proyecto en IntelliJ IDEA.
3. Ejecutar la clase `Main` (`src/main/java/Main.java`).

## Colecciones principales
| Necesidad | Colección |
|---|---|
| Registro general de turnos | `ArrayList` |
| Tipos de servicio sin duplicados | `HashSet` |
| Búsqueda de turnos por código y clientes por documento | `HashMap` |
| Turnos ordenados por código | `TreeSet` |
| Atención por prioridad | `PriorityQueue` |

## Estado
En desarrollo.