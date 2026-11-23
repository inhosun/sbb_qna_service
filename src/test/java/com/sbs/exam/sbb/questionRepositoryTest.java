package com.sbs.exam.sbb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class questionRepositoryTest {
    @Autowired
    private  QuestionRepository questionRepository;

    private static int lastSampleDataId;

    @BeforeEach
    @Test
    void beforEach() {
        crearData();
        createSampleData();
    }
    void crearData() {
        questionRepository.disalbleForeignKey();
        questionRepository.truncate();
        questionRepository.ensalbleForeignKey();
    }

    void createSampleData() {

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

        Question q3 = Question.builder()
                .subject("sbb가 무엇인가?")
                .content("sbb로 시작하기")
                .createDate(LocalDateTime.now())
                .answerList(null)
                .build();

        questionRepository.save(q1);
        lastSampleDataId = q1.getId();

        questionRepository.save(q2);
        lastSampleDataId = q2.getId();

        questionRepository.save(q3);
        lastSampleDataId = q3.getId();
    }

    @Test
    void 저장() {
        Question q1 = Question.builder()
                .subject("sbb가 무엇인가?")
                .content("sbb로 시작하기")
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

        assertThat(q1.getId()).isEqualTo(++lastSampleDataId);
        assertThat(q2.getId()).isEqualTo(++lastSampleDataId);

    }

    @Test
    void 수정() {
        assertThat(questionRepository.count()).isEqualTo(lastSampleDataId);
        Question q1 = questionRepository.findById(1).get();
        q1.setSubject("수정된 제목");
        questionRepository.save(q1);
       // assertEquals(--lastSampleDataId, questionRepository.count());
        q1 = questionRepository.findById(1).get();
        assertThat(q1.getSubject()).isEqualTo("수정된 제목");

    }
    @Test
    void 삭제() {
        assertThat(questionRepository.count()).isEqualTo(lastSampleDataId);
        Question q1 = questionRepository.findById(1).get();
        questionRepository.delete(q1);
        //assertEquals(--lastSampleDataId, questionRepository.count());
        assertThat(questionRepository.count()).isEqualTo(--lastSampleDataId);

    }
}



