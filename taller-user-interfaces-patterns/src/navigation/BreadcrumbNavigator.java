package navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BreadcrumbNavigator {

    private static final String RAIZ = "Inicio";
    private static final String SEPARADOR = " > ";

    private final List<String> pestanas;
    private final List<String> ruta = new ArrayList<>();

    public BreadcrumbNavigator(String... pestanas) {
        this.pestanas = new ArrayList<>(Arrays.asList(pestanas));
        irAInicio();
    }


    public void cambiarPestana(String pestana) {
        if (!pestanas.contains(pestana)) {

            pestanas.add(pestana);
        }
        ruta.clear();
        ruta.add(RAIZ);
        ruta.add(pestana);
    }

    public void entrarSubcategoria(String subcategoria) {
        ruta.add(subcategoria);
    }

    public void volver() {
        if (ruta.size() > 1) {
            ruta.remove(ruta.size() - 1);
        }
    }

    public final void irAInicio() {
        ruta.clear();
        ruta.add(RAIZ);
    }

    public String getRuta() {
        return String.join(SEPARADOR, ruta);
    }

    public List<String> getPestanas() {
        return Collections.unmodifiableList(pestanas);
    }
}
