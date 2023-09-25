package com.tobi_spring.chapter5.ver2;

import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class UserService {
    UserDao userDao;
    UserLevelUpgradePolicy userLevelUpgradePolicy;

    public static void main(String[] args) {
        Map<String, Integer> i = Map.of(
            "I", 1,
            "V", 5);
        char a = '1';
        String s = String.valueOf(a);
        System.out.println(Integer.MAX_VALUE);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    private void upgradeLevel(User user) {
        userLevelUpgradePolicy.upgradeLevel(user);
    }

    private boolean canUpgradeLevel(User user) {
        return userLevelUpgradePolicy.canUpgradeLevel(user);
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
