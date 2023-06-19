package com.dishianerifkinj.domain;

import lombok.Data;

@Data
public class Head extends BodyPart {
    public enum HeadShape {
        OVAL,
        ROUND,
        SQUARE,
        DIAMOND,
        HEART
    }
    private boolean hasGlasses;
    private HeadShape headShape;
}
