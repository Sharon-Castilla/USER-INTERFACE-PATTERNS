package content;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;


public class TarjetaElemento {

    private final String nombre;
    private final String estado;
    private final Map<String, String> detallesTecnicos = new LinkedHashMap<>();
    private boolean verAvanzado = false;

    public TarjetaElemento(String nombre, String estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public TarjetaElemento agregarDetalle(String clave, String valor) {
        detallesTecnicos.put(clave, valor);
        return this;
    }

    public void desplegar() { verAvanzado = true; }

    public void plegar() { verAvanzado = false; }

    public void setVerAvanzado(boolean verAvanzado) { this.verAvanzado = verAvanzado; }

    public boolean isVerAvanzado() { return verAvanzado; }


    public String render() {
        String resumen = "[Vista Resumen] " + nombre + " · Estado: " + estado;
        if (!verAvanzado) {
            return resumen;
        }
        StringJoiner sj = new StringJoiner(" | ");
        detallesTecnicos.forEach((k, v) -> sj.add(k + ": " + v));
        return "[Acordeon Desplegado] " + sj;
    }
}
