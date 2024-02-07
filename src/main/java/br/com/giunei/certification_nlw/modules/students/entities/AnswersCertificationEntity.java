package br.com.giunei.certification_nlw.modules.students.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "answers_certification_students")
public class AnswersCertificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "certification_in")
    private UUID certificationID;

    @ManyToOne
    @JoinColumn(name = "certification_in", insertable = false, updatable = false)
    private CertificationStudentEntity certificationStudentEntity;

    @Column(name = "student_in")
    private UUID studentID;

    @ManyToOne
    @JoinColumn(name = "student_in", insertable = false, updatable = false)
    private StudentEntity studentEntity;

    @Column(name = "question_in")
    private UUID questionID;

    @Column(name = "answer_in")
    private UUID answerID;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
