package br.com.giunei.certification_nlw.modules.students.controllers;

import br.com.giunei.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import br.com.giunei.certification_nlw.modules.students.use_cases.VerifyIfHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        if(verifyIfHasCertificationUseCase.execute(verifyHasCertificationDTO)){
            return "pode dar o cu";
        }
        return "vai a merda";
    }
}
