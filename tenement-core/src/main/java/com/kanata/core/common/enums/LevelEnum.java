package com.kanata.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author chenyao
 * date 2020-09-23
 */
@NoArgsConstructor
@AllArgsConstructor
public enum LevelEnum {

    //1-10
    FIRST_LEVEL_NEED_EXP(10L,9),
    //11-20
    SECOND_LEVEL_NEED_EXP(100L,10),
    //21-30
    THIRD_LEVEL_NEED_EXP(1000L,10),
    //31以后
    FOURTH_LEVEL_NEED_EXP(10000L,70);

    /**
     * 每级所需经验
     */
    private Long leveExp;

    /**
     * 没个分段共多少级
     */
    private Integer countLevel;


    public Long getLeveExp(){
        return leveExp;
    }

    public Integer getCountLevel(){
        return countLevel;
    }

}
