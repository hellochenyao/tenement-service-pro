package com.kanata.core.entity;

import com.kanata.core.common.enums.TipOffsType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户信息 实体
 * <p>
 * Created by mumu on 2019/3/11.
 */
@Data
@Entity
@Table(name = "tip_offs_record")
public class TipOffsRecordEntity {

    //用户唯一id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //举报人id
    @Column
    private int userId;

    //对象id
    @Column
    private int targetId;

    //举报类型
    @Column
    @Enumerated(EnumType.STRING)
    private TipOffsType tipOffsType;

    //帖子id
    @Column
    private int invitationId;

    //详细信息
    @Column
    private String remark;

    //图片
    @Column
    private String image;

    private int star;

    @CreationTimestamp
    private LocalDateTime createTime;



}
