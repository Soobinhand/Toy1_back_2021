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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
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
    
    //시큐리티로 인해 로그인 페이지로 가짐
    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }


    //로그인 페이지로 가짐
    @GetMapping("/login")
    public String login(){
        return "/login";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String username, @RequestParam("password") String password,
                                        String rememberMe, HttpSession session, HttpServletResponse response){
        int check = userService.userCheck(username,password);
        if (check!=1){
            String message = "";
            if (check == -1){
                message = "패스워드가 다릅니다.";
            }else if (check == 0){
                message = "존재하지 않는 아이디입니다.";
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type","text/html; charset=UTF-8");
            StringBuilder sb = new StringBuilder();
            sb.append("<script>");
            sb.append("alert('"+message+"');");
            sb.append("history.back();");
            sb.append("</script>");
            return new ResponseEntity<String>(sb.toString(),headers, HttpStatus.OK);
        }//로그인 실패
        session.setAttribute("username",username);
        //로그인 상태유지 여부확인 후

        if (rememberMe != null && rememberMe.equals("true")){
            Cookie cookie = new Cookie("username",username);
            cookie.setMaxAge(60*10);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/");
        return new ResponseEntity<>(headers,HttpStatus.FOUND);
    }

    @PostMapping("/user")
    ResponseEntity<?> newUser(@RequestBody User newUser){
        newUser.setRole(UserRole.USER);
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/admin")
    ResponseEntity<?> newAdmin(@RequestBody User newUser){
        newUser.setRole(UserRole.ADMIN);
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/user/{id}")
    public EntityModel<User> one(@PathVariable Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return assembler.toModel(user);
    }

    @PutMapping("/user/{id}")
    ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id){
        User updatedUser = repository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setRole(newUser.getRole());
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

    @DeleteMapping("/user/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
