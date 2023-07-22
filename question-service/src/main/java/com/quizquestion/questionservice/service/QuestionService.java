package com.quizquestion.questionservice.service;


import com.quizquestion.questionservice.dao.QuestionDao;
import com.quizquestion.questionservice.model.Question;
import com.quizquestion.questionservice.model.QuestionWrapper;
import com.quizquestion.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> allQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
         questionDao.save(question);
         try{
         return new ResponseEntity<>("success",HttpStatus.CREATED);
    }catch (Exception e){
             e.printStackTrace();
         }
         return new ResponseEntity<>("error",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.getByCategory(category), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public String deleteQuestionById(Integer id) {
        questionDao.deleteById(id);
        return "Deleted Successfully";
    }

    public String updateQuestion(Question question) {
          questionDao.save(question);
          return "updated successfully";


    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQuestions) {

        List<Integer> questions=questionDao.findRandomQuestionsByCategory(category,numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIDs) {
        List<QuestionWrapper> wrappers=new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id:questionIDs){
            questions.add(questionDao.findById(id).get());
        }
        for( Question question:questions){
            QuestionWrapper wrapper=new QuestionWrapper();
                wrapper.setId(question.getId());
                wrapper.setQuestion(question.getQuestion());
                wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrappers.add(wrapper);
        }
        return  new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right= 0;

        for(Response response:responses){
            Question question= questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getCorrectAnswer()))
            right++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
