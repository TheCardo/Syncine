package com.ricardocode.Syncine.controller;


import com.ricardocode.Syncine.dto.UserDTO;
import com.ricardocode.Syncine.model.User;
import com.ricardocode.Syncine.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //identificar que essa classe é um controller

@RequestMapping("api/users") //endpoint para criar a url que vai ser o endereço para o cadastro e login
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //vai registrar um novo usuario
    @PostMapping("/register")
    public ResponseEntity<String> registerUser (@RequestBody UserDTO userDTO) {
        try {
            userService.registerNewUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //vai listar todos os usuarios
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    //vai buscar o usuario pelo id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try{
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //vai deletar o usuario pelo seu id
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        try{
            userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //atualiza o usuario ja existente
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            User updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


