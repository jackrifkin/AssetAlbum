package com.dishianerifkinj.domain;

import com.dishianerifkinj.domain.Hair.*;
import com.dishianerifkinj.domain.BodyPart.*;
import com.dishianerifkinj.domain.Head.*;
import com.dishianerifkinj.domain.Torso.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequest {
    // asset filters
    private AssetType assetType;
    private String artistUsername;
    private String fileName;
    // hair filters
    private HairColor hairColor;
    private HairLength hairLength;
    private HairType hairType;
    // body part filters
    private SkinColor skinColor;
    private Boolean hasTattoos;
    // head filters
    private HeadShape headShape;
    private Boolean hasGlasses;
    // torso filters
    private Sex sex;
    private TorsoShape torsoShape;
    // limb filters
    private Boolean isLeft;
    private Boolean isArm;
}
