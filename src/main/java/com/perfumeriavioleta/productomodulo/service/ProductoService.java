package com.perfumeriavioleta.productomodulo.service;

import com.perfumeriavioleta.productomodulo.model.Producto;
import com.perfumeriavioleta.productomodulo.model.Genero;
import com.perfumeriavioleta.productomodulo.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Capa de servicio para la entidad {@link Producto}.
 * <p>
 * Contiene la lógica de negocio relacionada con productos. Actúa como intermediario
 * entre el controlador y el repositorio, permitiendo agregar reglas de negocio
 * en el futuro (validaciones, cálculos, etc.).
 * </p>
 *
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-03-15
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Constructor con inyección de dependencia del repositorio.
     *
     * @param productoRepository repositorio JPA de productos
     */
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Obtiene todos los productos del catálogo.
     *
     * @return lista completa de productos
     */
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su identificador único.
     *
     * @param id ID del producto
     * @return Optional que contiene el producto si existe, o vacío
     */
    public Optional<Producto> obtenerPorId(Integer id) {
        return productoRepository.findById(id);
    }

    /**
     * Guarda un producto (nuevo o actualizado).
     *
     * @param producto producto a persistir
     * @return producto guardado con el ID asignado (si es nuevo)
     */
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id identificador del producto a borrar
     */
    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }

    /**
     * Busca productos por marca (coincidencia exacta).
     *
     * @param marca nombre de la marca
     * @return lista de productos de esa marca
     */
    public List<Producto> buscarPorMarca(String marca) {
        return productoRepository.findByMarca(marca);
    }

    /**
     * Busca productos por género.
     *
     * @param genero género (Hombre, Mujer, Unisex)
     * @return lista de productos del género indicado
     */
    public List<Producto> buscarPorGenero(Genero genero) {
        return productoRepository.findByGenero(genero);
    }

    /**
     * Busca productos cuyo nombre contenga el texto dado (búsqueda insensible a mayúsculas).
     *
     * @param nombre texto parcial o completo del nombre
     * @return lista de productos que coinciden
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}