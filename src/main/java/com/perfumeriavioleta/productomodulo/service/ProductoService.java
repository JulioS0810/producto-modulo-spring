/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.perfumeriavioleta.productomodulo.service;

import com.perfumeriavioleta.productomodulo.model.Producto;
import com.perfumeriavioleta.productomodulo.model.Genero;
import com.perfumeriavioleta.productomodulo.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que encapsula la lógica de negocio para la entidad Producto.
 * Actúa como una fachada entre el controlador y el repositorio, aplicando
 * el patrón de inyección de dependencias.
 * 
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-03-15
 */
@Service
public class ProductoService {
    
    private final ProductoRepository productoRepository;

    /**
     * Constructor que inyecta el repositorio de productos.
     * 
     * @param productoRepository el repositorio a inyectar
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
     * Busca un producto por su ID.
     * 
     * @param id el identificador único del producto
     * @return Optional con el producto si existe, vacío si no
     */
    public Optional<Producto> obtenerPorId(Integer id) {
        return productoRepository.findById(id);
    }

    /**
     * Guarda un nuevo producto o actualiza uno existente.
     * 
     * @param producto el producto a guardar
     * @return el producto guardado con su ID asignado
     */
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Elimina un producto por su ID.
     * 
     * @param id el identificador del producto a eliminar
     */
    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }

    /**
     * Busca productos por marca.
     * 
     * @param marca el nombre de la marca
     * @return lista de productos de esa marca
     */
    public List<Producto> buscarPorMarca(String marca) {
        return productoRepository.findByMarca(marca);
    }

    /**
     * Busca productos por género.
     * 
     * @param genero el género del perfume (Hombre, Mujer, Unisex)
     * @return lista de productos del género especificado
     */
    public List<Producto> buscarPorGenero(Genero genero) {
        return productoRepository.findByGenero(genero);
    }

    /**
     * Busca productos por nombre (búsqueda parcial, insensible a mayúsculas).
     * 
     * @param nombre el texto a buscar dentro del nombre
     * @return lista de productos que contienen el texto en su nombre
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}