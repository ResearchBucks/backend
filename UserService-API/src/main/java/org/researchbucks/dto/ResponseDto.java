package org.researchbucks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto implements Serializable {

    private HttpStatus status;
    private String message;
    private Object data;

    public ResponseDto(HttpStatus status, Object data){
        this.status = status;
        this.data = data;
    }
}
