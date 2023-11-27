package com.prueba.bci.apirest.apirest.controller;


import com.prueba.bci.apirest.apirest.model.Telefono;
import com.prueba.bci.apirest.apirest.model.Usuario;
import com.prueba.bci.apirest.apirest.respository.UsuarioService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import  javax.crypto.SecretKey;
import  javax.crypto.spec.SecretKeySpec;
@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    private String error;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result, @RequestHeader("Authorization") String token,
                                  @RequestHeader("user") String user) {

        if(result.hasErrors()){
            return this.validar(result);
        }

         if (this.validToken(token, user)) {
            Usuario usuarioDb = usuarioService.createUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDb);
           }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TOKEN NO VALIDO:" + this.error);
        }

    }

    @GetMapping
    public ResponseEntity<?>listar(){
        return ResponseEntity.ok().body(usuarioService.getAllUsuarios()) ;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable UUID id, @RequestHeader("Authorization") String token,
                                 @RequestHeader("user") String user) {
        if (this.validToken(token, user)) {
            Optional<Usuario> o = Optional.ofNullable(usuarioService.getUsuarioById(id));
            if (o.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(o.get());
        }
        return null;
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable UUID id,  @RequestHeader("Authorization") String token,
                                    @RequestHeader("user") String user){
        Optional<Usuario> o = Optional.ofNullable(usuarioService.getUsuarioById(id));

        if(result.hasErrors()){
            return this.validar(result);
        }

        if(!o.isPresent()){
            return ResponseEntity.notFound().build();
        }
        if (this.validToken(token, user)) {
        Usuario usuarioDb = o.get();
        usuarioDb.setName(usuario.getName());
        usuarioDb.setEmail(usuario.getEmail());
        usuarioDb.setPassword(usuario.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUsuario(usuarioDb));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TOKEN NO VALIDO:" + this.error);
        }




    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable("id") UUID id){
        usuarioService.deleteUsuarios(id);
        return ResponseEntity.noContent().build();
    }

    private SecretKey getSecretKey() {

        byte[] secretKeyBytes = "estaesunaclavedeejemploparalapostulaciondeunproyectoparaunclienteenparticular".getBytes();
        return new SecretKeySpec(secretKeyBytes, "HmacSHA256");
    }
    public boolean validToken(String token, String expectedUser) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Aquí asumimos que el atributo "alias" es un String en el cuerpo del token.
            String alias = claims.get("alias", String.class);

            // Verifica si el alias es "ROOT" y si el usuario esperado coincide.
            return expectedUser.equals(alias);

        } catch (Exception e) {
            // Manejar excepciones, como token expirado, firma inválida, etc.
            error = e.getMessage();
            return false;
        }
    }

    private ResponseEntity<?> validar(BindingResult result){
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put("mensaje",  "Error en : "+err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
