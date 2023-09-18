package com.curso.ecommerce.springcomemerce.service;

import com.curso.ecommerce.springcomemerce.model.Usuario;
import com.curso.ecommerce.springcomemerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
     UsuarioRepository usuarioRepository;

    @Override
    public Usuario save(Usuario usuario) {

        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> get(Integer id) {
        return Optional.of(usuarioRepository.getReferenceById(id));
    }

    @Override
    public void delete(Integer id) {
    usuarioRepository.deleteById(id);
    }

    @Override
    public void update(Usuario usuario) {
usuarioRepository.save(usuario);
    }
}
