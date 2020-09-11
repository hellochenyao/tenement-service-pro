package com.kanata.message.controller.api.dynamic;

import lombok.Data;

@Data
public class RequestCreateDynamicPost {

    private Integer userId;

    private Integer circleId;

    private String circleName;

    private String content;

    private String image;
}
