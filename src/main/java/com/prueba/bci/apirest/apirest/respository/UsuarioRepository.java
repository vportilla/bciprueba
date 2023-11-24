package com.prueba.bci.apirest.apirest.respository;

import com.prueba.bci.apirest.apirest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, UUID> {
}
