package com.dishianerifkinj.domain;

import lombok.Data;

@Data
public class Head extends BodyPart {
    public enum HeadShape {
        OVAL("oval"),
        ROUND("round"),
        SQUARE("square"),
        DIAMOND("diamond"),
        HEART("heart");
        private final String shapeStr;

        HeadShape(String str) {
            this.shapeStr = str;
        }

        public static HeadShape getHeadShapeFromString(String str) {
            for (HeadShape shape : HeadShape.values()) {
                if (shape.shapeStr.equalsIgnoreCase(str)) {
                    return shape;
                }
            }
            throw new IllegalArgumentException("Invalid head shape");
        }
    }
    private boolean hasGlasses;
    private HeadShape headShape;
}
