package com.quizquestion.questionservice.controller;

import com.quizquestion.questionservice.model.Question;
import com.quizquestion.questionservice.model.QuestionWrapper;
import com.quizquestion.questionservice.model.Response;
import com.quizquestion.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> allQuestions() {
        return questionService.allQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getByCategory(@PathVariable String category) {
        return questionService.getByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQuestionById(@PathVariable Integer id) {
        return questionService.deleteQuestionById(id);
    }

    @PutMapping("/update")
    public String updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String category,@RequestParam Integer numQuestions) {
        return questionService.getQuestionsForQuiz(category,numQuestions);
    }
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIDs){
        return questionService.getQuestionsFromId(questionIDs);
    }
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);

    }
}