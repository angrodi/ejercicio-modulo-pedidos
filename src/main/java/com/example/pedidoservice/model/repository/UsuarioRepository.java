package com.example.pedidoservice.model.repository;

import com.example.pedidoservice.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByIdIn(List<Integer> ids);

}
