/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.perfumeriavioleta.productomodulo.controller;

import com.perfumeriavioleta.productomodulo.model.Producto;
import com.perfumeriavioleta.productomodulo.model.Genero;
import com.perfumeriavioleta.productomodulo.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST que expone los endpoints para la gestión de productos.
 * Proporciona operaciones de listado, búsqueda por ID, marca, género y nombre.
 * 
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-03-15
 */
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (útil para frontend)
public class ProductoController {
    
    private final ProductoService productoService;

    /**
     * Constructor que inyecta el servicio de productos.
     * 
     * @param productoService servicio que contiene la lógica de negocio
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Obtiene todos los productos disponibles.
     * 
     * @return ResponseEntity con la lista de productos y estado HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    /**
     * Obtiene un producto específico por su ID.
     * 
     * @param id Identificador único del producto
     * @return ResponseEntity con el producto encontrado (HTTP 200) o vacío (HTTP 404) si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
        return productoService.obtenerPorId(id)
                .map(producto -> new ResponseEntity<>(producto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Busca productos por marca.
     * 
     * @param marca Nombre de la marca a buscar
     * @return ResponseEntity con la lista de productos de esa marca y estado HTTP 200 OK
     */
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> buscarPorMarca(@PathVariable String marca) {
        List<Producto> productos = productoService.buscarPorMarca(marca);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    /**
     * Busca productos por género (Hombre, Mujer, Unisex).
     * 
     * @param genero Género como cadena (debe coincidir con los valores del enum)
     * @return ResponseEntity con la lista de productos del género especificado,
     *         o HTTP 400 si el género no es válido
     */
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Producto>> buscarPorGenero(@PathVariable String genero) {
        try {
            Genero enumGenero = Genero.valueOf(genero);
            List<Producto> productos = productoService.buscarPorGenero(enumGenero);
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Retorna error 400 si el género proporcionado no es válido
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Busca productos cuyo nombre contenga el texto especificado (búsqueda parcial e insensible a mayúsculas).
     * 
     * @param nombre Texto a buscar dentro del nombre del producto
     * @return ResponseEntity con la lista de productos que coinciden y estado HTTP 200 OK
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}