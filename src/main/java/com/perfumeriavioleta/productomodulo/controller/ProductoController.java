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
 * <p>
 * Todos los endpoints de este controlador son públicos según la configuración
 * de seguridad actual (permitAll en /api/productos/**).
 * </p>
 *
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-03-15
 */
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
        return productoService.obtenerPorId(id)
                .map(producto -> new ResponseEntity<>(producto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> buscarPorMarca(@PathVariable String marca) {
        List<Producto> productos = productoService.buscarPorMarca(marca);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Producto>> buscarPorGenero(@PathVariable String genero) {
        try {
            Genero enumGenero = Genero.valueOf(genero);
            List<Producto> productos = productoService.buscarPorGenero(enumGenero);
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}