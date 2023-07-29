package com.exam.sbb.answer;

import com.exam.sbb.base.RepositoryUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer>, RepositoryUtil {
  @Modifying
  @Transactional
  @Query(value = "TRUNCATE answer", nativeQuery = true)
  void truncate();
}