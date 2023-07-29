package com.exam.sbb.question;

import com.exam.sbb.answer.Answer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity // 아래 Question 클래스는 엔티티 클래스이다.
// 아래 클래스와 1:1로 매칭되는 테이블이 DB 없다면, 자동으로 생성되어야 한다.
public class Question {
  @Id // primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
  private Integer id;

  @Column(length = 200) // varchar(200)
  private String subject;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;

  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
  private List<Answer> answerList;
}
