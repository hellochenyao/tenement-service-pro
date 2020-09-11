package com.kanata.core.dto.jwt;

import com.kanata.core.common.enums.OprUserType;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mumu on 2019/3/27.
 */
@Data
public class JwtDataDto {
    private String infoStr;
    private int userId;
    private OprUserType oprUserType;


    public JwtDataDto(Map<String, Object> body) {
        this.infoStr = (String) body.get("infoStr");
        this.userId = (int) body.get("userId");
        this.oprUserType = OprUserType.valueOf((String) body.get("oprUserType"));
    }

    public JwtDataDto(String infoStr) {
        this.infoStr = infoStr;
    }

    public JwtDataDto(Integer userId) {
        this.userId = userId;
    }

    public JwtDataDto(Integer userId, String infoStr) {
        this.userId = userId;
        this.infoStr = infoStr;
    }

    public JwtDataDto(Integer userId, OprUserType oprUserType) {
        this.userId = userId;
        this.oprUserType = oprUserType;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        Field[] declaredFields = this.getClass().getDeclaredFields();
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
