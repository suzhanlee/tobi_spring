package com.tobi_spring.chapter5.ver2;

import lombok.Getter;

@Getter
public class DefaultUserLevelUpgradePolicy implements UserLevelUpgradePolicy {

    public static final int MIN_LOGCOUNT_FOR_SILVER = 30;
    public static final int MIN_RECOMMEND_FOR_GOLD = 50;

    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();

        return switch (currentLevel) {
            case BASIC -> user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER;
            case SILVER -> user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD;
            case GOLD -> false;
            default -> throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        };
    }

}
