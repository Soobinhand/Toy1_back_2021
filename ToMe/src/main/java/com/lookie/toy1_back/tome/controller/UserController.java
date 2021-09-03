package com.lookie.toy1_back.tome.controller;

import com.lookie.toy1_back.tome.assembler.UserModelAssembler;
import com.lookie.toy1_back.tome.domain.User;
import com.lookie.toy1_back.tome.exception.UserNotFoundException;
import com.lookie.toy1_back.tome.repository.UserRepository;
import com.lookie.toy1_back.tome.role.UserRole;
import com.lookie.toy1_back.tome.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
public class UserController {
    private final UserRepository repository;
    private final UserModelAssembler assembler;
    private final UserService userService;
    UserController(UserRepository repository, UserModelAssembler assembler, UserService userService){
        this.repository = repository;
        this.assembler = assembler;
        this.userService = userService;
    }
    
    //모든 유저 반환
    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    //유저네임 중복체크 요청

    //새로운 유저 추가
    @PostMapping("/users")
    ResponseEntity<?> newUser(@RequestBody User newUser){
        newUser.setUserRole(UserRole.USER);
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    
    
    //새로운 관리자 추가
    @PostMapping("/admin")
    ResponseEntity<?> newAdmin(@RequestBody User newUser){
        newUser.setUserRole(UserRole.ADMIN);
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    //유저 한 명 반환
    @GetMapping("/users/{id}")
    public EntityModel<User> one(@PathVariable Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return assembler.toModel(user);
    }
    
    @PutMapping("/users/{id}")
    ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id){
        User updatedUser = repository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setUserRole(newUser.getUserRole());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    //유저 하나 삭제
    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
