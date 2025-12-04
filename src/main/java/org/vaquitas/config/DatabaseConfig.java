package org.vaquitas.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Clase de configuración estática para establecer la conexión a la base de datos
 * usando HikariCP como pool de conexiones.
 *
 * La configuración de acceso (host, base de datos, usuario y contraseña)
 * se carga de manera segura desde el archivo de variables de entorno (.env).
 *
 * @since 1.0
 * @author VaquitaSoft
 */

public class DatabaseConfig {
    private static HikariDataSource dataSource;

    /**
     * Retorna la instancia Singleton de la fuente de datos (DataSource).
     * <p>
     * Si la fuente de datos aún no ha sido inicializada, realiza los siguientes pasos:
     * <ol>
     * <li>Carga las variables de entorno (DB_HOST, DB_SCHEMA, DB_USER, DB_PASS).</li>
     * <li>Construye la URL de conexión JDBC.</li>
     * <li>Configura y crea la instancia de {@link com.zaxxer.hikari.HikariDataSource}.</li>
     * </ol>
     *
     * @return {@link javax.sql.DataSource} Objeto de la fuente de datos para ser usado por la aplicación.
     */

    public static DataSource getDataSource(){
        if (dataSource==null){
            Dotenv dotenv = Dotenv.load();
            String host = dotenv.get("DB_HOST");
            String dbName = dotenv.get("DB_SCHEMA");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASS");
            String url = "jdbc:mysql://"+ host + ":3306/" + dbName;
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            dataSource= new HikariDataSource(config);
        }
        return dataSource;
    }
}