package com.dishianerifkinj.domain;

import lombok.Data;

@Data
public class Hair extends Asset {
    public enum HairColor {
        BLONDE,
        LIGHT_BROWN,
        BROWN,
        DARK_BROWN,
        BLACK,
        RED,
        MISC
    }
    public enum HairLength {
        SHORT,
        MID_LENGTH,
        LONG
    }

    public enum HairType {
        STRAIGHT,
        WAVY,
        CURLY,
        COILY
    }

    private HairColor color;
    private HairLength hairLength;
    private HairType hairType;
}
