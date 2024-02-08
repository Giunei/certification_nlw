package br.com.giunei.certification_nlw.modules.students.use_cases;

import br.com.giunei.certification_nlw.modules.questions.entities.AlternativesEntity;
import br.com.giunei.certification_nlw.modules.questions.entities.QuestionEntity;
import br.com.giunei.certification_nlw.modules.questions.repositories.QuestionRepository;
import br.com.giunei.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import br.com.giunei.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import br.com.giunei.certification_nlw.modules.students.entities.AnswersCertificationEntity;
import br.com.giunei.certification_nlw.modules.students.entities.CertificationStudentEntity;
import br.com.giunei.certification_nlw.modules.students.entities.StudentEntity;
import br.com.giunei.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import br.com.giunei.certification_nlw.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;


    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {
        boolean hasCertification = this.verifyIfHasCertificationUseCase.execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));


        if (hasCertification) {
            throw new Exception("Você já tirou sua certificação");
        }

        List<QuestionEntity> questionEntities = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionAnswers().forEach(questionAnswer -> {
            QuestionEntity question = questionEntities.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionID()))
                    .findFirst().get();
            AlternativesEntity findCorrectAlternative = question.getAlternatives().stream()
                    .filter(AlternativesEntity::isCorrect).findFirst().get();

            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setCorrect(true);
                correctAnswers.incrementAndGet();
            } else {
                questionAnswer.setCorrect(false);
            }

            AnswersCertificationEntity answersCertificationsEntity = AnswersCertificationEntity.builder()
                    .answerID(questionAnswer.getAlternativeID())
                    .questionID(questionAnswer.getQuestionID())
                    .isCorrect(questionAnswer.isCorrect()).build();

            answersCertifications.add(answersCertificationsEntity);
        });

        Optional<StudentEntity> student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;

        if (student.isEmpty()) {
            StudentEntity studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        } else {
            studentID = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity =
                CertificationStudentEntity.builder()
                        .technology(dto.getTechnology())
                        .studentID(studentID)
                        .grade(correctAnswers.get())
                        .build();

        CertificationStudentEntity certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);
        answersCertifications.stream().forEach(a -> {
            a.setCertificationID(certificationStudentEntity.getId());
            a.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationEntities(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);

        return certificationStudentCreated;
    }
}
