package com.lookie.toy1_back.tome.controller;

import com.lookie.toy1_back.tome.domain.Answer;
import com.lookie.toy1_back.tome.domain.Question;
import com.lookie.toy1_back.tome.domain.User;
import com.lookie.toy1_back.tome.repository.AnswerRepository;
import com.lookie.toy1_back.tome.repository.QuestionRepository;
import com.lookie.toy1_back.tome.repository.UserRepository;
import com.lookie.toy1_back.tome.request.AnswerCreateRequest;
import com.lookie.toy1_back.tome.request.QuestionCreateRequest;
import com.lookie.toy1_back.tome.service.AnswerService;
import com.lookie.toy1_back.tome.service.QuestionService;
import com.lookie.toy1_back.tome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    private final UserRepository userRepository;
    
    //완료
    @GetMapping("")
    public ResponseEntity<?> getQuestions(){
        return ResponseEntity.ok(questionRepository.findAll());
    }
    //완료
    @PostMapping("")
    public ResponseEntity<Question> createQuestion (@RequestBody QuestionCreateRequest request) {
        return ResponseEntity.ok(questionService.createQuestion(request));
    }
    //완료
    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable Long questionId) {
        return ResponseEntity.ok(questionRepository.findById(questionId));
    }
    //완료
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion (@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Void> updateQuestion (@PathVariable Long questionId, QuestionCreateRequest request) {
        questionService.updateQuestion(questionId, request);
        return ResponseEntity.ok().build();
    }
    //완료
    @GetMapping("/answer")
    public ResponseEntity<?> getAnswers(){
        return ResponseEntity.ok(answerRepository.findAll());
    }
    //완료
    @PostMapping("/{questionId}/answer")
    public ResponseEntity<Answer> createAnswer (@PathVariable Long questionId, @RequestBody AnswerCreateRequest request) {
        return ResponseEntity.ok(answerService.createAnswer(questionId, request));
    }
    //완료
    @GetMapping("/{questionId}/answer/{answerId}")
    public ResponseEntity<?> getAnswer (@PathVariable Long questionId,@PathVariable Long answerId) {
        return ResponseEntity.ok(questionRepository.getById(questionId).getAnswerList().get(Math.toIntExact(answerId)-1));
    }
    //완료
    @DeleteMapping("/{questionId}/answer/{answerId}")
    public ResponseEntity<Void> deleteAnswer (@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{questionId}/answer/{answerId}")
    public ResponseEntity<Void> updateAnswer (@PathVariable Long answerId, AnswerCreateRequest request) {
        answerService.updateAnswer(answerId, request);
        return ResponseEntity.ok().build();
    }
}