package com.prueba.bci.apirest.apirest.respository;

import com.prueba.bci.apirest.apirest.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
   @Autowired
    private UsuarioRepository usuarioRepository;

   public Usuario createUsuario(Usuario usuario){
       return usuarioRepository.save(usuario);
   }

   public Usuario getUsuarioById(UUID id){
       Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
       return optionalUsuario.get();
   }

   public List<Usuario> getAllUsuarios(){
       return usuarioRepository.findAll();
   }


   public void deleteUsuarios(UUID id){
       usuarioRepository.deleteById(id);
   }



}
