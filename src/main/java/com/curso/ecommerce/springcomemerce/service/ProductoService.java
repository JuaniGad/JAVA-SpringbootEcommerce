package com.curso.ecommerce.springcomemerce.service;

import com.curso.ecommerce.springcomemerce.model.Producto;

import java.beans.PropertyDescriptor;
import java.util.Optional;

public interface ProductoService {
 public Producto save(Producto producto);
 public Optional<Producto> get(Integer id);
 public void delete(Integer id);
 public void update(Producto producto);
}
