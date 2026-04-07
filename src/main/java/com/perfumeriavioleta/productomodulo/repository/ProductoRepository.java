package com.perfumeriavioleta.productomodulo.repository;

import com.perfumeriavioleta.productomodulo.model.Producto;
import com.perfumeriavioleta.productomodulo.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para la entidad {@link Producto}.
 * <p>
 * Extiende {@link JpaRepository} lo que proporciona métodos CRUD estándar
 * (save, findById, findAll, delete, etc.). Además se definen métodos de consulta
 * personalizados mediante convención de nombres.
 * </p>
 *
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-03-15
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /**
     * Obtiene todos los productos que coinciden con una marca específica.
     *
     * @param marca nombre de la marca (p.ej., "Chanel", "Dior")
     * @return lista de productos de esa marca (vacía si no hay)
     */
    List<Producto> findByMarca(String marca);

    /**
     * Obtiene productos según su género (Hombre, Mujer o Unisex).
     *
     * @param genero el género a filtrar
     * @return lista de productos del género indicado
     */
    List<Producto> findByGenero(Genero genero);

    /**
     * Busca productos cuyo nombre contenga el texto especificado,
     * ignorando mayúsculas/minúsculas. Útil para búsquedas parciales.
     *
     * @param nombre texto a buscar dentro del nombre del producto
     * @return lista de productos que contienen ese texto
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Obtiene productos con stock por debajo de un umbral.
     * <p>
     * Este método se puede usar para generar alertas de inventario bajo.
     * </p>
     *
     * @param stockMin valor límite (productos con stock menor a este número)
     * @return lista de productos con stock bajo
     */
    List<Producto> findByStockLessThan(Integer stockMin);
}