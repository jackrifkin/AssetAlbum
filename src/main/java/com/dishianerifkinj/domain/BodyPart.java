package com.dishianerifkinj.domain;

import lombok.Data;

@Data
public abstract class BodyPart extends Asset {
    public enum SkinColor {
        LIGHT("light"),
        MEDIUM("medium"),
        DARK("dark");

        private final String str;

        SkinColor(String str) {
            this.str = str;
        }

        public static SkinColor getSkinColorFromString(String str) {
            for (SkinColor color : SkinColor.values()) {
                if (color.str.equalsIgnoreCase(str)) {
                    return color;
                }
            }
            throw new IllegalArgumentException("Invalid skin color");
        }
    }
    protected SkinColor skinColor;
    protected boolean hasTattoos;
}
