package com.prueba.bci.apirest.apirest.controller;


import com.prueba.bci.apirest.apirest.model.Telefono;
import com.prueba.bci.apirest.apirest.model.Usuario;
import com.prueba.bci.apirest.apirest.respository.UsuarioService;
import jakarta.validation.Valid;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario){

        Usuario usuarioDb = usuarioService.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDb);
    }

    @GetMapping
    public ResponseEntity<?>listar(){
        return ResponseEntity.ok().body(usuarioService.getAllUsuarios()) ;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable UUID id){
        Optional<Usuario> o = Optional.ofNullable(usuarioService.getUsuarioById(id));
        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(o.get());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable UUID id){
        Optional<Usuario> o = Optional.ofNullable(usuarioService.getUsuarioById(id));
        if(!o.isPresent()){
             return ResponseEntity.notFound().build();
        }
        Usuario usuarioDb = o.get();
        usuarioDb.setName(usuario.getName());
        usuarioDb.setEmail(usuario.getEmail());
        usuarioDb.setPassword(usuario.getPassword());

        List<Telefono> eliminados = new ArrayList<>();

        usuarioDb.getPhones()
                .stream()
                .filter(tdb -> !usuario.getPhones().contains(tdb))
                .forEach(usuarioDb::removePhones);

        usuarioDb.setPhones(usuario.getPhones());

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUsuario(usuarioDb));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable("id") UUID id){
        usuarioService.deleteUsuarios(id);
        return ResponseEntity.noContent().build();
    }


}
