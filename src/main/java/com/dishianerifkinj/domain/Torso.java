package com.dishianerifkinj.domain;

import lombok.Data;

@Data
public class Torso extends BodyPart {
    public enum TorsoShape {
        TRIANGLE,
        RECTANGLE,
        PEAR,
        HOURGLASS,
        APPLE
    }

    public enum Sex {
        MALE,
        FEMALE
    }

    private Sex sex;
    private TorsoShape torsoShape;
}