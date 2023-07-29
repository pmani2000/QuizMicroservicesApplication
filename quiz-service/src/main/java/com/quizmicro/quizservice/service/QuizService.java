package com.quizmicro.quizservice.service;


import com.quizmicro.quizservice.dao.QuizDao;
//import com.quizmicro.quizservice.model.QuestionWrapper;
import com.quizmicro.quizservice.feign.QuizFeignInterface;
import com.quizmicro.quizservice.model.QuestionWrapper;
//import com.quizmicro.quizservice.model.Quiz;
import com.quizmicro.quizservice.model.Quiz;
import com.quizmicro.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
  @Autowired
  QuizFeignInterface quizFeignInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
       //call the generate url from question service -RestTemplate--http://localhost:8080/question/generate
        List<Integer> questions =quizFeignInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
       Quiz quiz=quizDao.findById(id).get();
       List<Integer> questionIds= quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions=quizFeignInterface.getQuestionsFromId(questionIds);

        return questions;

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
       ResponseEntity<Integer>score=quizFeignInterface.getScore(responses);

        return score;
    }
}
