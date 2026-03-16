/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.perfumeriavioleta.productomodulo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Clase que representa la entidad Producto, mapeada a la tabla 'productos' de la base de datos.
 * Contiene los atributos básicos de un producto en el catálogo de Perfumería Violeta.
 * 
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-03-15
 */
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Producto")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "Marca", nullable = false, length = 100)
    private String marca;

    @Column(name = "Concentracion", length = 50)
    private String concentracion;

    @Enumerated(EnumType.STRING)
    @Column(name = "Genero", columnDefinition = "enum('Hombre','Mujer','Unisex')")
    private Genero genero;

    @Column(name = "Precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "Stock", nullable = false)
    private Integer stock = 0;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Producto() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getConcentracion() { return concentracion; }
    public void setConcentracion(String concentracion) { this.concentracion = concentracion; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", precio=" + precio +
                '}';
    }
}