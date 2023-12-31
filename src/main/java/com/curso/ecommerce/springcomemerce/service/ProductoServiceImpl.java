package com.curso.ecommerce.springcomemerce.service;

import com.curso.ecommerce.springcomemerce.model.Producto;
import com.curso.ecommerce.springcomemerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        return Optional.of(productoRepository.getReferenceById(id));
    }

    @Override
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    public void update(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public List<Producto> findAll() {

        return productoRepository.findAll() ;
    }
}
