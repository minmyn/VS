package org.vaquitas;
import io.javalin.Javalin;
import org.eclipse.jetty.http.HttpMethod;
import org.vaquitas.config.Inicio;
import org.vaquitas.controller.JwtMiddleware;
import org.vaquitas.controller.TokenManager;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create( javalinConfig ->{
            //CORS BASICO;
            javalinConfig.bundledPlugins.enableCors(corsPluginConfig -> {
                corsPluginConfig.addRule(it ->{

                    //Puertos en local
                    it.allowHost("http://localhost:5500");
                    it.allowHost("http://127.0.0.1:5500");

                    // PuestosAWS
                    it.allowHost("http://172.31.29.241");
                    it.allowHost("http://52.200.235.132");
                    it.allowCredentials = true;
                });
            });
            javalinConfig.bundledPlugins.enableRouteOverview("/route");
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.http.defaultContentType = "application/json";
            javalinConfig.showJavalinBanner = false;
        });
        app.start(8548);



        app.get("/", ctx  -> ctx.result("Hello Vaquita"));
        Inicio.incioToken().apply(app);
        Inicio.inicioUsuario().register(app);
        Inicio.inicioGanado().register(app);
        Inicio.inicioVenta().register(app);
        Inicio.inicioAlimento().register(app);
        Inicio.inicioConsulta().register(app);
        Inicio.inicioReceta().register(app);
        Inicio.inicioRecordatorio().register(app);
        Inicio.inicioReporte().register(app);

        //CATALOGOS
        Inicio.inicioMedicina().register(app);
        Inicio.inicioRaza().register(app);
        Inicio.inicioRancho().register(app);

        System.out.println("Corriendo en el puerto 8548");

    }
}