package org.vaquitas.config;

import org.vaquitas.controller.*;
import org.vaquitas.repository.*;
import org.vaquitas.route.*;
import org.vaquitas.service.*;

public class Inicio {

    public static UsuarioRoute inicioUsuario(){
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        TokenManager tokenManager = new TokenManager();
        UsuarioControl usuarioControl = new UsuarioControl(usuarioService, tokenManager);
        UsuarioRoute usuarioRoute = new UsuarioRoute(usuarioControl);
        return usuarioRoute;
    }

    public static AnimalRoute inicioGanado(){
        AnimalRepository animalRepository = new AnimalRepository();
        AnimalService animalService = new AnimalService(animalRepository);
        AnimalControl animalControl = new AnimalControl(animalService);
        AnimalRoute animalRoute = new AnimalRoute(animalControl);
        return animalRoute;
    }

    public static VentaRoute inicioVenta(){
        VentaRepository ventaRepository = new VentaRepository();
        VentaService ventaService = new VentaService(ventaRepository);
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
        RecordatorioRepository recordatorioRepository = new RecordatorioRepository();
        RecetaRepository recetaRepository = new RecetaRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();
        ConsultaService consultaService = new ConsultaService(recetaRepository, recordatorioRepository, consultaRepository);
        ConsultaControl consultaControl = new ConsultaControl(consultaService);
        ConsultaRoute consultaRoute = new ConsultaRoute(consultaControl);
        return consultaRoute;
    }

    public static RecetaRoute inicioReceta(){
        RecetaRepository recetaRepository = new RecetaRepository();
        RecetaService recetaService = new RecetaService(recetaRepository);
        RecetaControl recetaControl = new RecetaControl(recetaService);
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
}