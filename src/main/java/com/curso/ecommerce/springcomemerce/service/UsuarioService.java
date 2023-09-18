package com.curso.ecommerce.springcomemerce.service;

import com.curso.ecommerce.springcomemerce.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
  Usuario save(Usuario usuario);
  Optional<Usuario> get(Integer id);
  void delete(Integer id);
  void update(Usuario usuario);
}
