package com.curso.ecommerce.springcomemerce.controller;

import com.curso.ecommerce.springcomemerce.model.Producto;
import com.curso.ecommerce.springcomemerce.model.Usuario;
import com.curso.ecommerce.springcomemerce.service.ProductoService;
import com.curso.ecommerce.springcomemerce.service.UploadFileService;
import com.curso.ecommerce.springcomemerce.service.UsuarioService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER= LoggerFactory.getLogger(ProductoController.class);
    //El autowired permite evitar instanciar el objeto usando el new...
    @Autowired

    private ProductoService productoService;

    @Autowired
    private UploadFileService upload;


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
    public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {

    LOGGER.info("Este es el objeto producto {}",producto);
    Optional<Usuario> u=usuarioService.get(3);


    //Imagen
        //Cuando se crea un producto
        if(producto.getId()==null){
            String nombreImagen=upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }else{
            if(file.isEmpty()){
                Producto p=new Producto();
                p=productoService.get(producto.getId()).get();
                producto.setImagen(p.getImagen());
            }else{
                String nombreImagen=upload.saveImage(file);
                producto.setImagen(nombreImagen);
            }

        }

        if (u.isPresent()) {
            producto.setUsuario(u.get()); // Obtiene el Usuario del Optional
            productoService.save(producto);
        }

    return "redirect:/productos";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,Model model){
        Producto producto;

        Optional<Producto> optionalProducto=productoService.get(id);
        producto=optionalProducto.get();

        LOGGER.info("Producto buscado:{}",producto);
        model.addAttribute("producto",producto);
        return "productos/edit";
    }

@PostMapping("/update")
    public String update(Producto producto){
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){

        productoService.delete(id);
        return "redirect:/productos";
    }

}
