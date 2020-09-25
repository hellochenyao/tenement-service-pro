package com.kanata.core.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chenyao
 * date 2020-09-24
 */
@Data
@Entity
@Table(name = "sign_rule")
public class SignRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *普通签到经验
     */
    private Integer signExp;

    /**
     * 连续签到经验
     */
    private Integer signExpContinue;

    /**
     * 最大等级
     */
    private Integer maxLevel;

}
