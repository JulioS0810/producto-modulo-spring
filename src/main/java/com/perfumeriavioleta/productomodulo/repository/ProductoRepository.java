/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.perfumeriavioleta.productomodulo.repository;

import com.perfumeriavioleta.productomodulo.model.Producto;
import com.perfumeriavioleta.productomodulo.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Producto.
 * Proporciona métodos CRUD básicos y operaciones de consulta personalizadas
 * para acceder a la tabla 'productos' de la base de datos.
 * 
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-03-15
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /**
     * Busca productos por su marca.
     * 
     * @param marca el nombre de la marca a buscar (no debe ser nulo)
     * @return lista de productos que pertenecen a la marca especificada
     */
    List<Producto> findByMarca(String marca);

    /**
     * Busca productos por su género.
     * 
     * @param genero el género del perfume (Hombre, Mujer o Unisex)
     * @return lista de productos del género indicado
     */
    List<Producto> findByGenero(Genero genero);

    /**
     * Busca productos cuyo nombre contenga el texto especificado,
     * ignorando mayúsculas y minúsculas.
     * 
     * @param nombre el texto a buscar dentro del nombre del producto
     * @return lista de productos que coinciden con la búsqueda parcial
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca productos con una cantidad en stock menor al valor dado.
     * Útil para alertas de inventario bajo.
     * 
     * @param stockMin el valor límite de stock (productos con stock menor a este valor)
     * @return lista de productos con stock inferior al especificado
     */
    List<Producto> findByStockLessThan(Integer stockMin);
}