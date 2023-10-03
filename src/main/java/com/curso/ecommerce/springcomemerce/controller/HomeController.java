package com.curso.ecommerce.springcomemerce.controller;

import com.curso.ecommerce.springcomemerce.model.DetalleOrden;
import com.curso.ecommerce.springcomemerce.model.Orden;
import com.curso.ecommerce.springcomemerce.model.Producto;
import com.curso.ecommerce.springcomemerce.model.Usuario;
import com.curso.ecommerce.springcomemerce.service.ProductoService;
import com.curso.ecommerce.springcomemerce.service.UsuarioService;
import org.apache.coyote.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {
    //Tengo una variable de tipo log
    private Logger log = LoggerFactory.getLogger(HomeController.class);
 @Autowired
    private ProductoService productoService;
 //Almacenar los detalles de la orden
 List<DetalleOrden> detalles=new ArrayList<DetalleOrden>();
//Almacena los datos de la orden
 Orden orden=new Orden();

@Autowired
 private UsuarioService usuarioService;
    @GetMapping("")
    public String home(Model model){

        model.addAttribute("productos",productoService.findAll());

        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id,Model model){
        log.info("Id del producto enviado como parametro {}",id);

        model.addAttribute("producto",productoService.get(id));

        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad,Model model){

        DetalleOrden detalleOrden=new DetalleOrden();
        Producto producto =new Producto();
        double sumaTotal=0;

        Optional<Producto> optionalProducto=productoService.get(id);
        log.info("Producto añadido:{}",optionalProducto.get());
        log.info("Cantidad: {}",cantidad);

        producto=optionalProducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se añada 2 veces

        Integer idProducto= producto.getId();
        boolean ingresado=detalles.stream().anyMatch(p->p.getProducto().getId()==idProducto);

        if(!ingresado){
            detalles.add(detalleOrden);
        }

         sumaTotal = detalles.stream()
                .mapToDouble(dt -> dt.getTotal()) // Transforma los objetos en valores double
                .sum(); // Calcula la suma de los valores double

        orden.setTotal(sumaTotal);

        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        return "usuario/carrito" ;

    }

    //Quitar elemento del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable  Integer id,Model model){
        //Lista Nueva de Productos
        List<DetalleOrden> ordenesNuevas=new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles){

            if(detalleOrden.getProducto().getId()!=id){
                ordenesNuevas.add(detalleOrden);
            }
        }

        //new list with products
        detalles=ordenesNuevas;

        double sumaTotal=0;

        sumaTotal = detalles.stream()
                .mapToDouble(dt -> dt.getTotal()) // Transforma los objetos en valores double
                .sum(); // Calcula la suma de los valores double

        orden.setTotal(sumaTotal);

        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        return "usuario/carrito";
    }

    @GetMapping("/getCart")

    public String getCart(Model model){



        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);


        return "usuario/carrito";

    }

    @GetMapping("/order")
    public String order(Model model){

        Optional<Usuario> usuario=usuarioService.get(3);

        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        model.addAttribute("usuario",usuario);

        return "usuario/resumenorden";
    }
}
