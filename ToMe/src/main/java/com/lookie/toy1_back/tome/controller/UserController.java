package com.lookie.toy1_back.tome.controller;

import com.lookie.toy1_back.tome.domain.User;
import com.lookie.toy1_back.tome.exception.UserNotFoundException;
import com.lookie.toy1_back.tome.request.UserCreationRequest;
import com.lookie.toy1_back.tome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //사용자 리스트 리턴
    @GetMapping("/users")
    public List<User> returnUsers(){
        return userService.findAll();
    }

    //사용자 한명 리턴
    @GetMapping("/users/{id}")
    public User returnUser(@PathVariable Long id){
        User user = userService.findOne(id);
        //존재하지 않는 사용자 예외처리
        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        return user;
    }
    //새로운 사용자 등록
    @PostMapping("/users")
    public ResponseEntity<User> createUser (@RequestBody User user) {
        User savedUser = userService.save(user);

        // 사용자에게 요청 값을 변환해주기
        // fromCurrentRequest() :현재 요청되어진 request값을 사용한다는 뜻
        // path : 반환 시켜줄 값
        // savedUser.getId() : {id} 가변변수에 새롭게 만들어진 id값 저장
        // toUri() : URI 형태로 변환
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //사용자 삭제
    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id){
        User user = userService.delete(id);
        if (user==null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
    }


}
