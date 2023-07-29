package com.exam.sbb.question;

import com.exam.sbb.base.RepositoryUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer>, RepositoryUtil {

  Question findBySubject(String subject);

  Question findByContent(String content);

  Question findBySubjectAndContent(String subject, String content);

  List<Question> findBySubjectLike(String subject);

  @Modifying
  @Transactional
  @Query(value = "TRUNCATE question", nativeQuery = true)
  void truncate();
}
