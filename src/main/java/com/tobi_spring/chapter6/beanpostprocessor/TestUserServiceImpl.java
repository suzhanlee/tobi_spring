package com.tobi_spring.chapter6.beanpostprocessor;

public class TestUserServiceImpl extends UserServiceImpl {
    private String id = "madnite1";
    public void upgradeLevel(User user) {
        if (user.getId().equals(id)) {
            throw new TestUserServiceException();
        }
        super.upgradeLevel(user);
    }

    static class TestUserServiceException extends RuntimeException {

    }

}
