package br.com.giunei.certification_nlw.modules.students.controllers;

import br.com.giunei.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import br.com.giunei.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import br.com.giunei.certification_nlw.modules.students.entities.CertificationStudentEntity;
import br.com.giunei.certification_nlw.modules.students.use_cases.StudentCertificationAnswersUseCase;
import br.com.giunei.certification_nlw.modules.students.use_cases.VerifyIfHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        if(verifyIfHasCertificationUseCase.execute(verifyHasCertificationDTO)){
            return "pode dar o cu";
        }
        return "vai a merda";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationsAnswer(@RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) throws Exception {
        try {
            CertificationStudentEntity result = studentCertificationAnswersUseCase.execute(studentCertificationAnswerDTO);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
