package state;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GestorSesionUI {

    private final Map<String, String> campos = new LinkedHashMap<>();
    private final List<String> historialToasts = new ArrayList<>();
    private Map<String, String> borrador = null;
    private boolean usuarioActivo = false;
    private String ultimoMensaje = "";

    public GestorSesionUI(String... camposRequeridos) {
        for (String c : camposRequeridos) {
            campos.put(c, "");
        }
    }

    public void completarCampo(String campo, String valor) {
        if (campos.containsKey(campo)) {
            campos.put(campo, valor == null ? "" : valor);
        }
    }

    public int camposListos() {
        int n = 0;
        for (String v : campos.values()) {
            if (!v.isBlank()) n++;
        }
        return n;
    }

    public int calcularProgreso() {
        if (campos.isEmpty()) return 0;
        return Math.round(100f * camposListos() / campos.size());
    }

    public List<String> camposFaltantes() {
        List<String> faltan = new ArrayList<>();
        for (Map.Entry<String, String> e : campos.entrySet()) {
            if (e.getValue().isBlank()) faltan.add(e.getKey());
        }
        return faltan;
    }

    public String mostrarProgreso() {
        int pct = calcularProgreso();
        if (pct == 100) return "[Progreso: 100%] ¡Formulario completo!";
        return "[Progreso: " + pct + "%] Campos listos: " + camposListos() + " de " + campos.size()
                + " (Falta: " + String.join(", ", camposFaltantes()) + ")";
    }

    public boolean guardarReserva() {
        if (!usuarioActivo) {
            borrador = new LinkedHashMap<>(campos);
            ultimoMensaje = "[Lazy Registration] Avance guardado en borrador temporal. "
                    + "Por favor cree su cuenta para finalizar.";
            return false;
        }
        if (calcularProgreso() < 100) {
            borrador = new LinkedHashMap<>(campos);
            ultimoMensaje = "[Aviso] Aún no se puede finalizar. Falta: "
                    + String.join(", ", camposFaltantes()) + ". Tu avance quedó como borrador.";
            return false;
        }
        borrador = null;
        ultimoMensaje = emitirToast("Reserva guardada exitosamente.");
        return true;
    }

    public void registroRapido() {
        usuarioActivo = true;
        if (borrador != null) {
            for (Map.Entry<String, String> e : borrador.entrySet()) {
                if (campos.get(e.getKey()).isBlank()) {
                    campos.put(e.getKey(), e.getValue());
                }
            }
        }
    }

    public void cancelarReserva() {
        campos.replaceAll((k, v) -> "");
        borrador = null;
        ultimoMensaje = emitirToast("Reserva cancelada.");
    }

    public void cerrarSesion() {
        usuarioActivo = false;
        ultimoMensaje = emitirToast("Sesión cerrada.");
    }

    public String emitirToast(String mensaje) {
        historialToasts.add(mensaje);
        return "[Toast de Estado] " + mensaje;
    }

    public String getUltimoMensaje() { return ultimoMensaje; }

    public List<String> getHistorialToasts() { return List.copyOf(historialToasts); }

    public boolean isUsuarioActivo() { return usuarioActivo; }

    public boolean tieneBorrador() { return borrador != null; }
}
