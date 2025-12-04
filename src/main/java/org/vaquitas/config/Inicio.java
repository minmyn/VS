package org.vaquitas.config;

import org.vaquitas.controller.*;
import org.vaquitas.repository.*;
import org.vaquitas.route.*;
import org.vaquitas.service.*;
import org.vaquitas.util.UsuarioValidator;

/**
 * Clase de inicialización y configuración central de la aplicación VaquitaSoft.
 * <p>
 * Su principal responsabilidad es la composición de todos los
 * componentes de la aplicación (Repository, Service, Control y Route)
 * inyectando sus dependencias de forma manual.
 * <p>
 * Cada método estático actúa como un "constructor" que retorna
 * la instancia de la capa Route lista para ser registrada en el servidor Javalin.
 *
 * @since 1.0
 * @author VaquitaSoft
 */
public class Inicio {

    /**
     * Inicializa y compone el módulo completo de manejo de Usuarios.
     * Inyecta {@code UsuarioRepository}, {@code UsuarioValidator} y {@code TokenManager}.
     *
     * @return {@link org.vaquitas.route.UsuarioRoute} con todas las dependencias configuradas.
     */
    public static UsuarioRoute inicioUsuario(){
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioValidator usuarioValidator = new UsuarioValidator(usuarioRepository);
        UsuarioService usuarioService = new UsuarioService(usuarioRepository, usuarioValidator);
        TokenManager tokenManager = new TokenManager();
        UsuarioControl usuarioControl = new UsuarioControl(usuarioService, tokenManager, usuarioValidator);
        UsuarioRoute usuarioRoute = new UsuarioRoute(usuarioControl);
        return usuarioRoute;
    }

    /**
     * Inicializa y compone el módulo completo de manejo de Animales (Ganado).
     * Inyecta {@code AnimalRepository} y {@code RazaRepository}.
     *
     * @return {@link org.vaquitas.route.AnimalRoute} con todas las dependencias configuradas.
     */
    public static AnimalRoute inicioGanado(){
        AnimalRepository animalRepository = new AnimalRepository();
        RazaRepository razaRepository = new RazaRepository();
        AnimalService animalService = new AnimalService(animalRepository, razaRepository);
        AnimalControl animalControl = new AnimalControl(animalService);
        AnimalRoute animalRoute = new AnimalRoute(animalControl);
        return animalRoute;
    }

    /**
     * Inicializa y compone el módulo completo de manejo de Ventas.
     * Inyecta {@code VentaRepository} y {@code AnimalRepository}.
     *
     * @return {@link org.vaquitas.route.VentaRoute} con todas las dependencias configuradas.
     */
    public static VentaRoute inicioVenta(){
        VentaRepository ventaRepository = new VentaRepository();
        AnimalRepository animalRepository = new AnimalRepository();
        VentaService ventaService = new VentaService(ventaRepository, animalRepository);
        VentaControl ventaControl = new VentaControl(ventaService);
        VentaRoute ventaRoute = new VentaRoute(ventaControl);
        return ventaRoute;
    }

    /**
     * Inicializa y compone el módulo de manejo de Razas de Ganado (Cátalogo).
     *
     * @return {@link org.vaquitas.route.RazaRoute} con todas las dependencias configuradas.
     */
    public static RazaRoute inicioRaza(){
        RazaRepository razaRepository = new RazaRepository();
        RazaService razaService = new RazaService(razaRepository);
        RazaControl razaControl = new RazaControl(razaService);
        RazaRoute razaRoute = new RazaRoute(razaControl);
        return razaRoute;
    }

    /**
     * Inicializa y compone el módulo de manejo de Ranchos (Cátalogo).
     *
     * @return {@link org.vaquitas.route.RanchoRoute} con todas las dependencias configuradas.
     */
    public static RanchoRoute inicioRancho(){
        RanchoRepository ranchoRepository = new RanchoRepository();
        RanchoService ranchoService = new RanchoService(ranchoRepository);
        RanchoControl ranchoControl = new RanchoControl(ranchoService);
        RanchoRoute ranchoRoute = new RanchoRoute(ranchoControl);
        return ranchoRoute;
    }

    /**
     * Inicializa y compone el módulo de manejo de Medicamentos (Catálogo).
     *
     * @return {@link org.vaquitas.route.MedicamentoRoute} con todas las dependencias configuradas.
     */
    public static MedicamentoRoute inicioMedicina(){
        MedicamentoRepository medicamentoRepository = new MedicamentoRepository();
        MedicamentoService medicamentoService = new MedicamentoService(medicamentoRepository);
        MedicamentoControl medicamentoControl = new MedicamentoControl(medicamentoService);
        MedicamentoRoute medicamentoRoute = new MedicamentoRoute(medicamentoControl);
        return medicamentoRoute;
    }

    /**
     * Inicializa y compone el módulo de manejo de Alimentos.
     *
     * @return {@link org.vaquitas.route.AlimentoRoute} con todas las dependencias configuradas.
     */
    public static AlimentoRoute inicioAlimento(){
        AlimentoRepository alimentoRepository = new AlimentoRepository();
        AlimentoService alimentoService = new AlimentoService(alimentoRepository);
        AlimentoControl alimentoControl = new AlimentoControl(alimentoService);
        AlimentoRoute alimentoRoute = new AlimentoRoute(alimentoControl);
        return alimentoRoute;
    }

    /**
     * Inicializa y compone el módulo de manejo de Consultas Veterinarias.
     * Inyecta {@code ConsultaRepository} y {@code AnimalRepository}.
     *
     * @return {@link org.vaquitas.route.ConsultaRoute} con todas las dependencias configuradas.
     */
    public static ConsultaRoute inicioConsulta(){
        ConsultaRepository consultaRepository = new ConsultaRepository();
        AnimalRepository animalRepository = new AnimalRepository();
        ConsultaService consultaService = new ConsultaService(consultaRepository, animalRepository);
        ConsultaControl consultaControl = new ConsultaControl(consultaService);
        ConsultaRoute consultaRoute = new ConsultaRoute(consultaControl);
        return consultaRoute;
    }

    /**
     * Inicializa y compone el módulo de manejo de Recetas Médicas.
     * Inyecta {@code RecetaRepository}, {@code RecordatorioRepository}, {@code ConsultaRepository} y {@code AnimalRepository}.
     *
     * @return {@link org.vaquitas.route.RecetaRoute} con todas las dependencias configuradas.
     */
    public static RecetaRoute inicioReceta(){
        RecordatorioRepository recordatorioRepository = new RecordatorioRepository();
        RecetaRepository recetaRepository = new RecetaRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();
        AnimalRepository animalRepository = new AnimalRepository();

        RecetaService recetaService = new RecetaService(recetaRepository, recordatorioRepository, consultaRepository, animalRepository);
        RecetaControl recetaControl = new RecetaControl(recetaService);
        RecetaRoute recetaRoute = new RecetaRoute(recetaControl);
        return recetaRoute;
    }

    /**
     * Inicializa y compone el módulo de manejo de Recordatorios.
     *
     * @return {@link org.vaquitas.route.RecordatorioRoute} con todas las dependencias configuradas.
     */
    public static RecordatorioRoute inicioRecordatorio(){
        RecordatorioRepository recordatorioRepository = new RecordatorioRepository();
        RecordatorioService recordatorioService = new RecordatorioService(recordatorioRepository);
        RecordatorioControl recordatorioControl = new RecordatorioControl(recordatorioService);
        RecordatorioRoute recordatorioRoute= new RecordatorioRoute(recordatorioControl);
        return recordatorioRoute;
    }

    /**
     * Inicializa y retorna el Middleware para la gestión y validación de tokens JWT.
     *
     * @return {@link org.vaquitas.controller.JwtMiddleware} configurado.
     */
    public static JwtMiddleware incioToken(){
        TokenManager tokenManager = new TokenManager();
        JwtMiddleware jwtMiddleware = new JwtMiddleware(tokenManager);
        return jwtMiddleware;
    }

    /**
     * Inicializa y compone el módulo de manejo de Reportes.
     *
     * @return {@link org.vaquitas.route.ReporteRoute} con todas las dependencias configuradas.
     */
    public static ReporteRoute inicioReporte(){
        ReportesRepository reportesRepository = new ReportesRepository();
        ReporteServicee reporteServicee = new ReporteServicee(reportesRepository);
        ReporteControl reporteControl = new ReporteControl(reporteServicee);
        ReporteRoute reporteRoute = new ReporteRoute(reporteControl);
        return reporteRoute;
    }
}