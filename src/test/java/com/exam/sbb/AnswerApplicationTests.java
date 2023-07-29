package com.exam.sbb;

import com.exam.sbb.answer.Answer;
import com.exam.sbb.answer.AnswerRepository;
import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
public class AnswerApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;
	private int lastSampleDataId;

	@BeforeEach
	void beforeEach(){
		clearData();
		createSampleData();
	}

	private void clearData(){
		QuestionApplicationTests.clearData(questionRepository);
		answerRepository.truncateTable();
	}

	private void createSampleData(){
		QuestionApplicationTests.createSampleData(questionRepository);
	}

	@Test
	void DataSave(){
		Question q = questionRepository.findById(2).get();

		Answer a = new Answer();
		a.setContent("네, 자동으로 생성됩니다.");
		a.setQuestion(q); //어떤 질문의 답변인지 파악하기 위해서 Question 객체가 필요함.
		a.setCreateDate(LocalDateTime.now());
		answerRepository.save(a);
	}
}
