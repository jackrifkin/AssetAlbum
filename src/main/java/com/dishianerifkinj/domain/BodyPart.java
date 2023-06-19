package com.dishianerifkinj.domain;

import lombok.Data;

@Data
public abstract class BodyPart extends Asset {
    public enum SkinColor {
        LIGHT,
        MEDIUM,
        DARK
    }
    protected SkinColor skinColor;
    protected boolean hasTattoos;
}
