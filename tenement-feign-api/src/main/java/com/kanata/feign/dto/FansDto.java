package com.kanata.feign.dto;

import com.kanata.core.common.enums.ConcernType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenyao
 * date 2020-09-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FansDto {

    private List<ConcernInfo> concernInfoList;

    @Data
    @NoArgsConstructor
    public static class ConcernInfo{
        private String id;

        private Integer userid;

        private Integer toUserid;//关注用户的

        private ConcernType concernType;

        private LocalDateTime createTime;

        private LocalDateTime updateTime;

        public ConcernInfo(Integer userid, Integer toUserid, ConcernType concernType, LocalDateTime createTime, LocalDateTime updateTime) {
            this.id = userid+"_"+toUserid;
            this.userid = userid;
            this.toUserid = toUserid;
            this.concernType = concernType;
            this.createTime = createTime;
            this.updateTime = updateTime;
        }
    }


}
