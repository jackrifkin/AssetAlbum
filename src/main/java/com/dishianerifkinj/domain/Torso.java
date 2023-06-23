package com.dishianerifkinj.domain;

import lombok.Data;

@Data
public class Torso extends BodyPart {
    public enum TorsoShape {
        TRIANGLE("triangle"),
        RECTANGLE("rectangle"),
        PEAR("pear"),
        HOURGLASS("hourglass"),
        APPLE("apple");
        private final String shapeStr;

        TorsoShape(String str) {
            this.shapeStr = str;
        }

        public static TorsoShape getTorsoShapeFromString(String str) {
            for (TorsoShape shape : TorsoShape.values()) {
                if (shape.shapeStr.equalsIgnoreCase(str)) {
                    return shape;
                }
            }
            throw new IllegalArgumentException("Invalid torso shape");
        }
    }

    public enum Sex {
        MALE,
        FEMALE
    }

    private Sex sex;
    private TorsoShape torsoShape;
}