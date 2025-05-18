package org.researchbucks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailParamDto {

    private String sub;
    private Map<String, Object> properties;
    private String htmlTemplate;
}
