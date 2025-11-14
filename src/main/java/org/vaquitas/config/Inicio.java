package org.vaquitas.config;

import org.vaquitas.controller.*;
import org.vaquitas.repository.*;
import org.vaquitas.route.*;
import org.vaquitas.service.*;
import org.vaquitas.util.UsuarioValidator;

public class Inicio {

    public static UsuarioRoute inicioUsuario(){
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioValidator usuarioValidator = new UsuarioValidator(usuarioRepository);
        UsuarioService usuarioService = new UsuarioService(usuarioRepository, usuarioValidator);
        TokenManager tokenManager = new TokenManager();
        UsuarioControl usuarioControl = new UsuarioControl(usuarioService, tokenManager, usuarioValidator);
        UsuarioRoute usuarioRoute = new UsuarioRoute(usuarioControl);
        return usuarioRoute;
    }

    public static AnimalRoute inicioGanado(){
        AnimalRepository animalRepository = new AnimalRepository();
        RazaRepository razaRepository = new RazaRepository();
        AnimalService animalService = new AnimalService(animalRepository, razaRepository);
        AnimalControl animalControl = new AnimalControl(animalService);
        AnimalRoute animalRoute = new AnimalRoute(animalControl);
        return animalRoute;
    }

    public static VentaRoute inicioVenta(){
        VentaRepository ventaRepository = new VentaRepository();
        AnimalRepository animalRepository = new AnimalRepository();
        VentaService ventaService = new VentaService(ventaRepository, animalRepository);
        VentaControl ventaControl = new VentaControl(ventaService);
        VentaRoute ventaRoute = new VentaRoute(ventaControl);
        return ventaRoute;
    }

    public static RazaRoute inicioRaza(){
        RazaRepository razaRepository = new RazaRepository();
        RazaService razaService = new RazaService(razaRepository);
        RazaControl razaControl = new RazaControl(razaService);
        RazaRoute razaRoute = new RazaRoute(razaControl);
        return razaRoute;
    }

    public static RanchoRoute inicioRancho(){
        RanchoRepository ranchoRepository = new RanchoRepository();
        RanchoService ranchoService = new RanchoService(ranchoRepository);
        RanchoControl ranchoControl = new RanchoControl(ranchoService);
        RanchoRoute ranchoRoute = new RanchoRoute(ranchoControl);
        return ranchoRoute;
    }

    public static MedicamentoRoute inicioMedicina(){
        MedicamentoRepository medicamentoRepository = new MedicamentoRepository();
        MedicamentoService medicamentoService = new MedicamentoService(medicamentoRepository);
        MedicamentoControl medicamentoControl = new MedicamentoControl(medicamentoService);
        MedicamentoRoute medicamentoRoute = new MedicamentoRoute(medicamentoControl);
        return medicamentoRoute;
    }

    public static AlimentoRoute inicioAlimento(){
        AlimentoRepository alimentoRepository = new AlimentoRepository();
        AlimentoService alimentoService = new AlimentoService(alimentoRepository);
        AlimentoControl alimentoControl = new AlimentoControl(alimentoService);
        AlimentoRoute alimentoRoute = new AlimentoRoute(alimentoControl);
        return alimentoRoute;
    }

    public static ConsultaRoute inicioConsulta(){
        ConsultaRepository consultaRepository = new ConsultaRepository();
        AnimalRepository animalRepository = new AnimalRepository();
        ConsultaService consultaService = new ConsultaService(consultaRepository, animalRepository);
        ConsultaControl consultaControl = new ConsultaControl(consultaService);
        ConsultaRoute consultaRoute = new ConsultaRoute(consultaControl);
        return consultaRoute;
    }

    public static RecetaRoute inicioReceta(){
        // Repositorios
        RecordatorioRepository recordatorioRepository = new RecordatorioRepository();
        RecetaRepository recetaRepository = new RecetaRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();
        MedicamentoRepository medicamentoRepository = new MedicamentoRepository(); // Se añade el repositorio

        // Services
        RecetaService recetaService = new RecetaService(recetaRepository, recordatorioRepository, consultaRepository);
        MedicamentoService medicamentoService = new MedicamentoService(medicamentoRepository); // Se añade el servicio

        // Control y Route
        // Se inyecta el nuevo servicio en el controlador
        RecetaControl recetaControl = new RecetaControl(recetaService, medicamentoService);
        // Asumiendo que tienes una clase RecetaRoute para definir las rutas.
        RecetaRoute recetaRoute = new RecetaRoute(recetaControl);
        return recetaRoute;
    }

    public static RecordatorioRoute inicioRecordatorio(){
        RecordatorioRepository recordatorioRepository = new RecordatorioRepository();
        RecordatorioService recordatorioService = new RecordatorioService(recordatorioRepository);
        RecordatorioControl recordatorioControl = new RecordatorioControl(recordatorioService);
        RecordatorioRoute recordatorioRoute= new RecordatorioRoute(recordatorioControl);
        return recordatorioRoute;
    }

    public static JwtMiddleware incioToken(){
        TokenManager tokenManager = new TokenManager();
        JwtMiddleware jwtMiddleware = new JwtMiddleware(tokenManager);
        return jwtMiddleware;
    }
}