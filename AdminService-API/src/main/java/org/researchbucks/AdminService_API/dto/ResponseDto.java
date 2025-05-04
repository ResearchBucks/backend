package org.researchbucks.AdminService_API.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> implements Serializable {

    private String status;
    private String message;
    private T data;

    public ResponseDto(String status, T data){
        this.status = status;
        this.data = data;
    }
}
