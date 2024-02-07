package br.com.giunei.certification_nlw.modules.students.use_cases;

import br.com.giunei.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import br.com.giunei.certification_nlw.modules.students.entities.CertificationStudentEntity;
import br.com.giunei.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyIfHasCertificationUseCase {

    @Autowired
    private CertificationStudentRepository repo;

    public boolean execute(VerifyHasCertificationDTO dto) {
        List<CertificationStudentEntity> result = this.repo.findByEmailAndTechnology(dto.getEmail(), dto.getTechnology());
        return !result.isEmpty();
    }
}
