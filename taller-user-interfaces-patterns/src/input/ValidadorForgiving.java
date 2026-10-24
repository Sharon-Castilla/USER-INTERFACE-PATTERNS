package input;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidadorForgiving {

    private static final Pattern FECHA_SEPARADA =
            Pattern.compile("^(\\d{1,4})\\s*[/\\-. ]\\s*(\\d{1,2})\\s*[/\\-. ]\\s*(\\d{1,4})$");
    private static final Pattern CORREO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public String normalizarTelefono(String entrada) {
        if (entrada == null) return "";
        String digitos = entrada.replaceAll("\\D", "");
        if (digitos.startsWith("0057")) {
            digitos = digitos.substring(4);
        } else if (digitos.startsWith("57") && digitos.length() == 12) {
            digitos = digitos.substring(2);
        }
        return digitos.length() == 10 ? digitos : "";
    }

    public String normalizarFecha(String entrada) {
        if (entrada == null) return "";
        String s = entrada.trim();

        if (s.matches("\\d{8}")) { 
            return armarFecha(s.substring(4), s.substring(2, 4), s.substring(0, 2));
        }

        Matcher m = FECHA_SEPARADA.matcher(s);
        if (!m.matches()) return "";
        String a = m.group(1), b = m.group(2), c = m.group(3);

        if (a.length() == 4) return armarFecha(a, b, c);                    
        if (c.length() == 4) return armarFecha(c, b, a);                    
        if (c.length() == 2) return armarFecha("20" + c, b, a);             
        return "";
    }

    public String normalizarCorreo(String entrada) {
        if (entrada == null) return "";
        String correo = entrada.replaceAll("\\s+", "").toLowerCase();
        return CORREO.matcher(correo).matches() ? correo : "";
    }

    private String armarFecha(String anio, String mes, String dia) {
        try {
            return LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia)).toString();
        } catch (DateTimeException e) { 
            return "";
        }
    }
}
