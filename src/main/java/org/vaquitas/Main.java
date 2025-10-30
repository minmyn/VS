package org.vaquitas;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8548);
        System.out.println("Corriendo ene el puerto 8548");
        app.get("/", ctx  -> ctx.result("Hello Vaquita"));
    }
}