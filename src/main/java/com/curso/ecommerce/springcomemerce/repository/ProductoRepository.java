package com.curso.ecommerce.springcomemerce.repository;

import com.curso.ecommerce.springcomemerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Es como un DAO

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {

}
