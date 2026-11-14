package modelo;

import java.util.Objects;

public class Cliente {

    private final String documento;
    private String nombre;
    private String telefono;

    public Cliente(String documento, String nombre, String telefono) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("El documento es obligatorio.");
        }
        this.documento = documento.trim();
        setNombre(nombre);
        setTelefono(telefono);
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.trim().matches("\\d{7,10}")) {
            throw new IllegalArgumentException("El teléfono debe tener entre 7 y 10 dígitos.");
        }
        this.telefono = telefono.trim();
    }

    // Dos clientes son iguales si tienen el mismo documento
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente otro = (Cliente) o;
        return documento.equals(otro.documento);
    }

    // Coherente con equals: usa solo el documento
    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }

    @Override
    public String toString() {
        return documento + " - " + nombre + " (" + telefono + ")";
    }
}