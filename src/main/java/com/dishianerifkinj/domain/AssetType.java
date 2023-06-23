package com.dishianerifkinj.domain;

import lombok.Getter;

@Getter
public enum AssetType {
    ASSET("asset"),
    HAIR("hair"),
    BODY_PART("body part"),
    HEAD("head"),
    TORSO("torso"),
    LIMB("limb");

    private final String typeString;

    AssetType(String str) {
        this.typeString = str;
    }

    public static AssetType getTypeFromString(String str) {
        for (AssetType type : AssetType.values()) {
            if (type.getTypeString().equalsIgnoreCase(str)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid type");
    }
}
