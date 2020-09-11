package com.kanata.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDynamicEntity {

    @Column
    @Id
    private String id;

    @Column
    private Integer userId;

    @Column
    private Integer dynamicId;

    @CreationTimestamp
    private LocalDateTime createTime;

    public LikeDynamicEntity(Integer userId, Integer dynamicId) {
        this.id = userId + "_" + dynamicId;
        this.userId = userId;
        this.dynamicId = dynamicId;
    }
}
