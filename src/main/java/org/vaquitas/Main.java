package org.vaquitas;
import io.javalin.Javalin;
import org.vaquitas.config.Inicio;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8548);
        System.out.println("Corriendo ene el puerto 8548");
        app.get("/", ctx  -> ctx.result("Hello Vaquita"));
        Inicio.inicioUsuario().register(app);
        Inicio.inicioGanado().register(app);
        Inicio.inicioVenta().register(app);
        Inicio.inicioRaza().register(app);
        Inicio.inicioRancho().register(app);
        Inicio.inicioMedicina().register(app);
    }
}