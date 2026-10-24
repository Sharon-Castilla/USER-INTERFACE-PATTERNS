package main;

import content.TarjetaElemento;
import input.ValidadorForgiving;
import navigation.BreadcrumbNavigator;
import state.GestorSesionUI;

public class Main {

    public static void main(String[] args) {
        ValidadorForgiving validador = new ValidadorForgiving();

        // CASO 1: Navegacion
        System.out.println("1. PATRÓN DE NAVEGACIÓN (BREADCRUMBS + TABS)");
        BreadcrumbNavigator nav = new BreadcrumbNavigator("Catálogo", "Reservas");
        System.out.println("Ruta actual: " + nav.getRuta());
        nav.cambiarPestana("Catálogo");
        System.out.println("Ruta actual: " + nav.getRuta());
        nav.entrarSubcategoria("Servidor Principal");
        System.out.println("Ruta actual: " + nav.getRuta());
        nav.cambiarPestana("Reservas");
        nav.entrarSubcategoria("Laboratorio de Redes");
        System.out.println("Ruta actual: " + nav.getRuta());
        nav.volver();
        System.out.println("Ruta actual (al volver): " + nav.getRuta());

        // CASO 2: Progressive Disclosure
        System.out.println("\n2. PATRÓN DE CONTENIDO Y ESTRUCTURA (PROGRESSIVE DISCLOSURE)");
        TarjetaElemento tarjeta = new TarjetaElemento("Servidor Principal", "Activo")
                .agregarDetalle("IP", "192.168.1.10")
                .agregarDetalle("Puerto", "8080")
                .agregarDetalle("RAM", "4GB/16GB");
        System.out.println("verAvanzado = " + tarjeta.isVerAvanzado());
        System.out.println(tarjeta.render());
        System.out.println("[Usuario solicita ver detalles avanzados...]");
        tarjeta.setVerAvanzado(true);
        System.out.println("verAvanzado = " + tarjeta.isVerAvanzado());
        System.out.println(tarjeta.render());

        // CASO 3: Forgiving Format
        System.out.println("\n3. PATRÓN DE ENTRADA DE DATOS (FORGIVING FORMAT)");
        String[] telefonos = {"+57 (310) 999-8877", "310 999 8877", "(300)123-4567", "0057 300-123-4567"};
        for (String t : telefonos) {
            System.out.println("Entrada del usuario: \"" + t + "\"");
            System.out.println("Teléfono normalizado sin error: \"" + validador.normalizarTelefono(t) + "\"");
        }
        String[] fechas = {"05 / 11 / 2026", "2026-11-05", "5/11/26", "31/02/2026"};
        for (String f : fechas) {
            String r = validador.normalizarFecha(f);
            System.out.println("Fecha \"" + f + "\" -> " + (r.isEmpty() ? "pendiente (fecha no válida, sin error)" : "\"" + r + "\""));
        }

        // CASO 4: Completeness Meter
        System.out.println("\n4. PATRÓN DE FEEDBACK Y ESTADO (COMPLETENESS METER)");
        GestorSesionUI gestor = new GestorSesionUI("Teléfono", "Fecha de reserva", "Correo de contacto");
        System.out.println(gestor.mostrarProgreso());

        gestor.completarCampo("Teléfono", validador.normalizarTelefono("+57 (310) 999-8877"));
        System.out.println("Usuario ingresa teléfono: \"+57 (310) 999-8877\"");
        System.out.println(gestor.mostrarProgreso());

        gestor.completarCampo("Fecha de reserva", validador.normalizarFecha("05/11/2026"));
        System.out.println("Usuario ingresa fecha: \"05/11/2026\"");
        System.out.println(gestor.mostrarProgreso());

        gestor.completarCampo("Correo de contacto", validador.normalizarCorreo("estudiante@udistrital.edu.co"));
        System.out.println("Usuario ingresa correo: \"estudiante@udistrital.edu.co\"");
        System.out.println(gestor.mostrarProgreso());

        // CASO 5: Lazy Registration + Toast
        System.out.println("\n5. LAZY REGISTRATION + TOAST");
        System.out.println("usuarioActivo = " + gestor.isUsuarioActivo());
        System.out.println("Usuario intenta guardar sin haber iniciado sesión...");
        gestor.guardarReserva();
        System.out.println(gestor.getUltimoMensaje());
        System.out.println("¿Borrador conservado? " + gestor.tieneBorrador());

        System.out.println("Usuario completa registro rápido -> Sesión activa = true");
        gestor.registroRapido();
        System.out.println("usuarioActivo = " + gestor.isUsuarioActivo());
        gestor.guardarReserva();
        System.out.println(gestor.getUltimoMensaje());

        // Extra: salidas directas, sin Dark Patterns
        System.out.println("\nSALIDAS DIRECTAS (SIN ROACH MOTEL)");
        gestor.cerrarSesion();
        System.out.println(gestor.getUltimoMensaje());
        gestor.cancelarReserva();
        System.out.println(gestor.getUltimoMensaje());
    }
}
