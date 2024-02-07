package br.com.giunei.certification_nlw.modules.questions.controllers;

import br.com.giunei.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import br.com.giunei.certification_nlw.modules.questions.dto.QuestionResultDTO;
import br.com.giunei.certification_nlw.modules.questions.entities.AlternativesEntity;
import br.com.giunei.certification_nlw.modules.questions.entities.QuestionEntity;
import br.com.giunei.certification_nlw.modules.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        List<QuestionEntity> result = this.questionRepository.findByTechnology(technology);

        return result.stream().map(QuestionController::mapQuestionToDto).toList();
    }

    static QuestionResultDTO mapQuestionToDto(QuestionEntity question) {
        QuestionResultDTO dto = QuestionResultDTO.builder()
                .id(question.getId())
                .technology(question.getTechnology())
                .description(question.getDescription()).build();
        List<AlternativesResultDTO> alternativesResultDTOS = question.getAlternativesEntities()
                .stream().map(QuestionController::mapAlternativeToDTO).toList();

        dto.setAlternatives(alternativesResultDTOS);
        return dto;
    }

    static AlternativesResultDTO mapAlternativeToDTO(AlternativesEntity alternativesEntity) {
        return AlternativesResultDTO.builder()
                .id(alternativesEntity.getId())
                .description(alternativesEntity.getDescription())
                .build();
    }
}
