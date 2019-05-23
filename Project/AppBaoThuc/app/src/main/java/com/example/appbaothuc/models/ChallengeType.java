package com.example.appbaothuc.models;

public enum ChallengeType {
    DEFAULT(1), MATH(2), SHAKE(3), WALK(4);
    private final int value;
    ChallengeType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static ChallengeType newInstanceFromValue(int value){
        switch (value){
            case 2:
                return ChallengeType.MATH;
            case 3:
                return ChallengeType.SHAKE;
            case 4:
                return ChallengeType.WALK;
            default:
                return ChallengeType.DEFAULT;
        }
    }
}