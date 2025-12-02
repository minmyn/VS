package org.vaquitas.service;

import org.vaquitas.model.Alimento;
import org.vaquitas.repository.AlimentoRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Alimento}.
 * <p>
 * Actúa como intermediario entre el controlador y el repositorio, coordinando las operaciones CRUD.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AlimentoService {
    private final AlimentoRepository alimentoRepository;

    /**
     * Constructor que inyecta la dependencia del repositorio.
     *
     * @param alimentoRepository El repositorio para operaciones CRUD de Alimento.
     */
    public AlimentoService(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    /**
     * Guarda un nuevo registro de compra de alimento en la base de datos.
     *
     * @param alimento El objeto {@link Alimento} a guardar.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public void guardarAlimentos(Alimento alimento) throws SQLException{
        alimentoRepository.save(alimento);
    }

    /**
     * Recupera una lista de todas las compras de alimentos.
     *
     * @return Una lista de objetos {@link Alimento}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Alimento> verAlimentos()throws SQLException{
        return alimentoRepository.findAll();
    }

    /**
     * Actualiza la información de una compra de alimento existente.
     *
     * @param alimento El objeto {@link Alimento} con la información actualizada.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public void editarAlimentos(Alimento alimento) throws SQLException{
        alimentoRepository.update(alimento);
    }

    /**
     * Elimina un registro de compra de alimento por su identificador.
     *
     * @param idCompra El ID de la compra a eliminar.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public void eliminarAlimento (int idCompra) throws SQLException{
        alimentoRepository.delete(idCompra);
    }

    /**
     * Busca y recupera un registro de alimento por su identificador.
     *
     * @param idCompra El ID de la compra a buscar.
     * @return El objeto {@link Alimento} encontrado, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Alimento encontrarAlimento(int idCompra) throws SQLException{
        return alimentoRepository.findAlimento(idCompra);
    }
}