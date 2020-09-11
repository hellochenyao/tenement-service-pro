package com.kanata.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenyao
 * date 2020-09-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String code;

    private String msg;

}
