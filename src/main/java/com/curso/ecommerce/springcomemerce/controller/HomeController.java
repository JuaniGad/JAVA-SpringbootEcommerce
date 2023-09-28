package com.curso.ecommerce.springcomemerce.controller;

import com.curso.ecommerce.springcomemerce.service.ProductoService;
import org.apache.coyote.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    //Tengo una variable de tipo log
    private Logger log = LoggerFactory.getLogger(HomeController.class);
 @Autowired
    private ProductoService productoService;

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
}
