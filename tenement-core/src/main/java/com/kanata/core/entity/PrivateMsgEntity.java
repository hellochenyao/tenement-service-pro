package com.kanata.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="private_msg")
public class PrivateMsgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "text")
    private String content;

    //发送方用户id
    private Integer userid;

    //接收方用户id
    private Integer receiveUserid;

    private int isRead;//-1未读 0已读

    private int type;//0 text 1 voice -1img

    private String descText;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
