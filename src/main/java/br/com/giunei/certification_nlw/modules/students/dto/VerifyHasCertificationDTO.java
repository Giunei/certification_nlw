package br.com.giunei.certification_nlw.modules.students.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyHasCertificationDTO {

    private String email;
    private String technology;
}
