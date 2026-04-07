package com.perfumeriavioleta.productomodulo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot para el módulo de productos.
 * <p>
 * Esta clase arranca el contenedor de Spring, escanea componentes, configura
 * la autoconfiguración y lanza la aplicación embebida (Tomcat por defecto).
 * </p>
 *
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-03-15
 */
@SpringBootApplication
public class ProductoModuloApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos (no se utilizan en esta aplicación)
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductoModuloApplication.class, args);
    }
}