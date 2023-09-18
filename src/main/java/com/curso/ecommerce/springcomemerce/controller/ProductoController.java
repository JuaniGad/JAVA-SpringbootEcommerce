package com.curso.ecommerce.springcomemerce.controller;

import com.curso.ecommerce.springcomemerce.model.Producto;
import com.curso.ecommerce.springcomemerce.model.Usuario;
import com.curso.ecommerce.springcomemerce.service.ProductoService;
import com.curso.ecommerce.springcomemerce.service.UsuarioService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER= LoggerFactory.getLogger(ProductoController.class);
    //El autowired permite evitar instanciar el objeto usando el new...
    @Autowired

    private ProductoService productoService;

    @Autowired
    private     UsuarioService usuarioService;

    @GetMapping("")
    //El objeto model pasa info del backend hacia la vista
    public String show(Model model
    ){
        model.addAttribute("productos",productoService.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
    return "productos/create";
    }


    @PostMapping("/save")
    public String save(Producto producto){

    LOGGER.info("Este es el objeto producto {}",producto);
    Optional<Usuario> u=usuarioService.get(3);

        if (u.isPresent()) {
            producto.setUsuario(u.get()); // Obtiene el Usuario del Optional
            productoService.save(producto);
        }

    return "redirect:/productos";

    }



}
