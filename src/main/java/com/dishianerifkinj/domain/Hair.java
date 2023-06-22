package com.dishianerifkinj.domain;

import lombok.Data;

@Data
public class Hair extends Asset {
    public enum HairColor {
        BLONDE("blonde"),
        LIGHT_BROWN("light brown"),
        BROWN("brown"),
        DARK_BROWN("dark brown"),
        BLACK("black"),
        RED("red"),
        MISC("misc");
        private final String colorStr;

        HairColor(String str) {
            this.colorStr = str;
        }

        public static HairColor getHairColorFromString(String str) {
            for (HairColor c : HairColor.values()) {
                if (c.colorStr.equalsIgnoreCase(str)) {
                    return c;
                }
            }
            throw new IllegalArgumentException("Invalid hair color");
        }
    }
    public enum HairLength {
        SHORT("short"),
        MID_LENGTH("mid-length"),
        LONG("long");
        private final String lengthStr;

        HairLength(String str) {
            this.lengthStr = str;
        }

        public static HairLength getHairLengthFromString(String str) {
            for (HairLength l : HairLength.values()) {
                if (l.lengthStr.equalsIgnoreCase(str)) {
                    return l;
                }
            }
            throw new IllegalArgumentException("Invalid hair length");
        }
    }

    public enum HairType {
        STRAIGHT("straight"),
        WAVY("wavy"),
        CURLY("curly"),
        COILY("coily");
        private final String typeStr;

        HairType(String str) {
            this.typeStr = str;
        }

        public static HairType getHairTypeFromString(String str) {
            for (HairType t : HairType.values()) {
                if (t.typeStr.equalsIgnoreCase(str)) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Invalid hair type");
        }
    }

    private HairColor color;
    private HairLength hairLength;
    private HairType hairType;
}
