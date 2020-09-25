package com.kanata.core.dto.user;

import com.kanata.core.common.enums.LevelEnum;
import com.kanata.core.entity.SignRuleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author chenyao
 * date 2020-09-23
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Level {

    private long exp;

    public LevelInfo getLevelInfo() {
        Long totalExp = 0L;
        Integer level = 1;
        LevelInfo levelInfo = new LevelInfo();
        for (LevelEnum levelEnum : LevelEnum.values()) {
            long currentLevelExp = levelEnum.getCountLevel() * levelEnum.getLeveExp();
            if (exp <= totalExp + currentLevelExp) {
                int currentLevel = (int) ((exp - totalExp) / levelEnum.getLeveExp()) + level;
                levelInfo.setLevel(currentLevel);
                Integer needExp = (int) (totalExp + levelEnum.getLeveExp() * (currentLevel - level + 1) - exp);
                levelInfo.setNeedExp(needExp);
                return levelInfo;
            }
            totalExp += currentLevelExp;
            level += levelEnum.getCountLevel();
        }
        levelInfo.setLevel(100);
        levelInfo.setNeedExp(0);
        return levelInfo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LevelInfo {

        private Integer level;
        private Integer needExp;
        private Integer incExp;
        private boolean signState;
        private Boolean isSignContinue;

    }

    /**
     * 登录签到 1-20每次10点经验 21以后100点
     */
    public LevelInfo signIn(LocalDate lastLoginTime, List<SignRuleEntity> signRules) {
        int incExp = 0;
        boolean isSignContinue = false;
        boolean isEnter = false;
        LevelInfo levelInfo = new LevelInfo();
        for (SignRuleEntity signRule : signRules) {
            if (getLevelInfo().getLevel() < signRule.getMaxLevel()) {
                isEnter = true;
                if (lastLoginTime.plusDays(1).equals(LocalDate.now())) {
                    exp += signRule.getSignExpContinue();
                    incExp = signRule.getSignExpContinue();
                    isSignContinue = true;
                } else {
                    exp += signRule.getSignExp();
                    incExp = signRule.getSignExp();
                }
                levelInfo = getLevelInfo();
                levelInfo.setIncExp(incExp);
                levelInfo.setIsSignContinue(isSignContinue);
                return levelInfo;
            }
        }
        if (signRules.size() == 0 || !isEnter) {
            if (lastLoginTime.plusDays(1).equals(LocalDate.now())) {
                exp += 1000L;
                incExp = 1000;
                isSignContinue = true;
            } else {
                exp += 500L;
                incExp = 500;
            }
        }
        levelInfo = getLevelInfo();
        levelInfo.setIncExp(incExp);
        levelInfo.setIsSignContinue(isSignContinue);
        return levelInfo;
    }


}
