package com.exam.sbb;

import com.exam.sbb.answer.Answer;
import com.exam.sbb.answer.AnswerRepository;
import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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

		Question q = questionRepository.findById(1).get();

		Answer a1 = new Answer();
		a1.setContent("sbb는 질문답변 게시판을 말합니다.");
		a1.setQuestion(q); //어떤 질문의 답변인지 파악하기 위해서 Question 객체가 필요함.
		a1.setCreateDate(LocalDateTime.now());
		answerRepository.save(a1);

		Answer a2 = new Answer();
		a2.setContent("sbb에서는 주로 스프링 관련 내용을 다룹니다.");
		a2.setQuestion(q); //어떤 질문의 답변인지 파악하기 위해서 Question 객체가 필요함.
		a2.setCreateDate(LocalDateTime.now());
		answerRepository.save(a2);
	}

	//답변 저장
	@Test
	void DataSave(){
		Question q = questionRepository.findById(2).get();

		Answer a = new Answer();
		a.setContent("네, 자동으로 생성됩니다.");
		a.setQuestion(q); //어떤 질문의 답변인지 파악하기 위해서 Question 객체가 필요함.
		a.setCreateDate(LocalDateTime.now());
		answerRepository.save(a);
	}

	//답변 조회
	@Test
	void DataSearch(){
		Answer a = answerRepository.findById(1).get();
		assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판을 말합니다.");
		//isEqualTo()에 주어진 답변과 Sample로 만든 답변이 일치하지 않아서 발생한 문제
		//답변을 일치하게 해서 문제 해결
	}

	//Question으로부터 관련된 질문 조회
	@Test
	@Transactional
	@Rollback(false)
	void QuestionSearch(){
		Question q = questionRepository.findById(1).get();
		//DB 연결이 끊기는 문제가 발생함.
		List<Answer> answerList = q.getAnswerList();
		//질문 하나에 답변이 하나만 존재하는 것은 아니기에 List<>를 기용함.
		//=> Cannot invoke "java.util.List.size()" because "answerList" is null

		assertThat(answerList.size()).isEqualTo(2);
		assertThat(answerList.get(0).getContent()).isEqualTo("sbb는 질문답변 게시판을 말합니다.");
	}
}
