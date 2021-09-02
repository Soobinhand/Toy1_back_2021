package com.lookie.toy1_back.tome.service;

import com.lookie.toy1_back.tome.domain.User;
import com.lookie.toy1_back.tome.repository.AnswerRepository;
import com.lookie.toy1_back.tome.repository.QuestionRepository;
import com.lookie.toy1_back.tome.repository.UserRepository;
import com.lookie.toy1_back.tome.request.UserCreationRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private static final List<User> users = new ArrayList<>();
    private static Long userCount = 3L;
    static{
        users.add(new User(1L, "soobin","s","s","s"));
        users.add(new User(2L, "h","h","h","h"));
        users.add(new User(3L, "q","q","q","q"));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user) {
        if (user.getId()==null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(Long id){
        for (User user :users){
            if (user.getId()==id){
                return user;
            }
        }
        return null;
    }

    public Optional<User> read(Long id) {
        return userRepository.findById(id);
    }



    public User delete(Long id){
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();

            if (user.getId()==id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }

}
