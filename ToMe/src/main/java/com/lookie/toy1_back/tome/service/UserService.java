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

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public User createUser(UserCreationRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        return userRepository.save(user);
    }

    public Optional<User> read(Long id) {
        return userRepository.findById(id);
    }

    public User update(Long id) {
        User user = read(id).get();
        return userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

}
