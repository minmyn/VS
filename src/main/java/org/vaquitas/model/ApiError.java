package org.vaquitas.model;

/**
 * Clase de modelo inmutable utilizada para estandarizar las respuestas de error
 * devueltas por la API al cliente.
 * <p>
 * Un objeto {@code ApiError} siempre contendrá un código de estado HTTP,
 * una descripción general del error y un mensaje detallado.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class ApiError {
    /**
     * El código de estado HTTP asociado al error (e.g., 400, 500).
     */
    private final int status;

    /**
     * La descripción breve y estandarizada del tipo de error (e.g., "Bad Request", "Internal Server Error").
     */
    private final String error;

    /**
     * El mensaje detallado y específico del error, destinado al cliente o al log de la aplicación.
     */
    private final String message;

    /**
     * Constructor para crear una nueva instancia de {@code ApiError}.
     *
     * @param status  El código de estado HTTP.
     * @param error   La descripción breve del error.
     * @param message El mensaje detallado del error.
     */
    public ApiError(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    /**
     * Obtiene el código de estado HTTP del error.
     *
     * @return El código de estado (int).
     */
    public int getStatus() { return status; }

    /**
     * Obtiene la descripción estandarizada del error.
     *
     * @return La descripción del error como String.
     */
    public String getError() { return error; }

    /**
     * Obtiene el mensaje detallado del error.
     *
     * @return El mensaje detallado del error como String.
     */
    public String getMessage() { return message; }
}