package com.sbs.exam.sbb;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private  QuestionRepository questionRepository;

	@Test
	void contextLoads() {

		Question q1 = Question.builder()
				.subject("제목1")
				.content("내용1")
				.createDate(LocalDateTime.now())
				.answerList(null)
				.build();

		Question q2 = Question.builder()
				.subject("제목2")
				.content("내용2")
				.createDate(LocalDateTime.now())
				.answerList(null)
				.build();

		questionRepository.save(q1);
		questionRepository.save(q2);
	}
	@Test
	void testJpa() {
		List<Question> questionList = questionRepository.findAll();
		assertEquals(2, questionList.size());
		assertEquals("제목12", questionList.get(0).getSubject());

	}

	@Test
	void testJpa2() {
		String subject ="제목2";
		Question question = questionRepository.findBySubject(subject);
		System.out.println("question.getSubject() ::" + question.getSubject());
		assertEquals("제목2", question.getSubject());

	}

	@Test
	void testJpa3() {
		String content ="내용2";
		Question question = questionRepository.findByContent(content);
		assertEquals("내용2", question.getContent());

	}

	@Test
	void testJpa4() {

		Question question = questionRepository.findBySubjectAndContent("제목1", "내용1");
		assertEquals("제목1", question.getSubject());

	}
}
