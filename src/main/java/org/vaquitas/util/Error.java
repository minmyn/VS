package org.vaquitas.util;

import org.vaquitas.model.ApiError;

public class Error {

    public static ApiError getApiDatabaseError(){
        return new ApiError(
                500,
                "Internal Server Error",
                "Error al procesar la solicitud debido a un problema con la base de datos.");
    }

    public static ApiError getApiServiceError(){
        return new ApiError(
                500,
                "Internal Server Error",
                "Ha ocurrido un error inesperado en el servicio. Por favor, intente de nuevo más tarde.");
    }

    public static ApiError getApiBadRequestError(){
        return new ApiError(
                400,
                "Bad Request",
                "La petición es inválida. Verifique el formato o los parámetros.");
    }

    public static ApiError getApiBadRequestError(String message){
        return new ApiError(
                400,
                "Bad Request",
                message);
    }

    public static ApiError getApiBadJson(){
        return new ApiError(
                400,
                "Bad Request",
                "Json mal formado"
        );
    }
}
