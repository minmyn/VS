package org.vaquitas.util;

import org.vaquitas.model.ApiError;

/**
 * Clase de utilidad para generar objetos {@link ApiError} estandarizados.
 * <p>
 * Proporciona métodos estáticos para crear respuestas de error comunes en la API,
 * facilitando la consistencia en el manejo de fallos.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Error {

    /**
     * Genera un error estándar de servidor interno (500) relacionado con problemas de base de datos.
     *
     * @return Una instancia de {@link ApiError} con status 500.
     */
    public static ApiError getApiDatabaseError(){
        return new ApiError(
                500,
                "Internal Server Error",
                "Error al procesar la solicitud debido a un problema con la base de datos.");
    }

    /**
     * Genera un error estándar de servidor interno (500) para fallos inesperados en la lógica del servicio.
     *
     * @return Una instancia de {@link ApiError} con status 500.
     */
    public static ApiError getApiServiceError(){
        return new ApiError(
                500,
                "Internal Server Error",
                "Ha ocurrido un error inesperado en el servicio. Por favor, intente de nuevo más tarde.");
    }

    /**
     * Genera un error estándar de Petición Incorrecta (400) con un mensaje genérico.
     *
     * @return Una instancia de {@link ApiError} con status 400.
     */
    public static ApiError getApiBadRequestError(){
        return new ApiError(
                400,
                "Bad Request",
                "La petición es inválida. Verifique el formato o los parámetros.");
    }

    /**
     * Genera un error de Petición Incorrecta (400) con un mensaje detallado proporcionado.
     *
     * @param message El mensaje específico que describe por qué la petición fue incorrecta.
     * @return Una instancia de {@link ApiError} con status 400.
     */
    public static ApiError getApiBadRequestError(String message){
        return new ApiError(
                400,
                "Bad Request",
                message);
    }
}