package com.prueba.bci.apirest.apirest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios" )
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    @Pattern(regexp = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message = "Formato incorrecto")
    private String email;

    @NotEmpty
    @Size(min = 8, max = 12, message = "El largo de la password debe ser 8 a 12 digítos")
    @Pattern(regexp ="^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,12}$", message = "Patron incorrecto")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name= "modified_at")
    private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_login")
    private Date last_login;


    @JsonIgnoreProperties(value ={"usuario"}, allowSetters = true)
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty
    private List<Telefono> phones;



     @PrePersist
     public void prePersist(){

         this.createdAt = new Date();
         this.modified = new Date();
         this.last_login = new Date();
     }



    public Usuario(UUID id, String name, String email, String password, Date createdAt, Date modified, Date last_login ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.modified = modified;
        this.last_login = last_login;

    }

    public Usuario() {
        this.phones = new ArrayList<>();
        name = null;
        email = null;
        password = null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public List<Telefono> getPhones() {
        return phones;
    }

    public void setPhones(List<Telefono> phones) {
         this.phones.clear();
         phones.forEach(this::addPhone);
    }

    public void addPhone(Telefono phone) {
        this.phones.add(phone);
        phone.setUsuario(this);
    }

    public void removePhones(Telefono phone){
         this.phones.remove(phone);
         phone.setUsuario(null);
    }

}
