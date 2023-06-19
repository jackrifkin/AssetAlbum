package com.dishianerifkinj.controller;

import com.dishianerifkinj.domain.*;
import com.dishianerifkinj.service.AssetAlbumDeleteService;
import com.dishianerifkinj.service.AssetAlbumGetService;
import com.dishianerifkinj.service.AssetAlbumPostService;
import com.dishianerifkinj.service.AssetAlbumPutService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AssetAlbumController {
    private final AssetAlbumPostService assetAlbumPostService = new AssetAlbumPostService();
    private final AssetAlbumGetService assetAlbumGetService = new AssetAlbumGetService();
    private final AssetAlbumPutService assetAlbumPutService = new AssetAlbumPutService();
    private final AssetAlbumDeleteService assetAlbumDeleteService = new AssetAlbumDeleteService();

    @GetMapping("/getAssetIds")
    public ResponseEntity<List<Integer>> getAssetIds(
            @RequestParam(value = "type", defaultValue = "ASSET") AssetType assetType,
            @RequestParam(value = "username", required = false) String artistUsername,
            @RequestParam(value = "filename", required = false) String filename,
            @RequestParam(value = "hairColor", required = false) Hair.HairColor hairColor,
            @RequestParam(value = "hairLength", required = false) Hair.HairLength hairLength,
            @RequestParam(value = "hairType", required = false) Hair.HairType hairType,
            @RequestParam(value = "skinColor", required = false) BodyPart.SkinColor skinColor,
            @RequestParam(value = "hasTattoos", required = false) Boolean hasTattoos,
            @RequestParam(value = "headShape", required = false) Head.HeadShape headShape,
            @RequestParam(value = "hasGlasses", required = false) Boolean hasGlasses,
            @RequestParam(value = "sex", required = false) Torso.Sex sex,
            @RequestParam(value = "torsoShape", required = false) Torso.TorsoShape torsoShape,
            @RequestParam(value = "isLeft", required = false) Boolean isLeft,
            @RequestParam(value = "isArm", required = false) Boolean isArm
    ) {
        AssetRequest assetRequest = new AssetRequest(
            assetType, artistUsername, filename, hairColor, hairLength, hairType, skinColor, hasTattoos,
            headShape, hasGlasses, sex, torsoShape, isLeft, isArm
        );

        return ResponseEntity.ok().body(assetAlbumGetService.getAssetIds(assetRequest));
    }

    @GetMapping("/getAssetFile")
    public ResponseEntity<Resource> getAssetFile(@RequestParam Integer assetId) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
                .body(assetAlbumGetService.getAssetFile(assetId).getResource());
    }

    @PostMapping("/addHead")
    public ResponseEntity<String> addHead(@RequestBody Head head) {
        if (!head.getFile().isEmpty()) {
            return ResponseEntity.ok().body(assetAlbumPostService.addHead(head));
        } else {
            return ResponseEntity.badRequest().body("No file received");
        }
    }

    @PostMapping("/addHair")
    public ResponseEntity<String> addHair(@RequestBody Hair hair) {
        if (!hair.getFile().isEmpty()) {
            return ResponseEntity.ok().body(assetAlbumPostService.addHair(hair));
        } else {
            return ResponseEntity.badRequest().body("No file received");
        }
    }

    @PostMapping("/addTorso")
    public ResponseEntity<String> addTorso(@RequestBody Torso torso) {
        if (!torso.getFile().isEmpty()) {
            return ResponseEntity.ok().body(assetAlbumPostService.addTorso(torso));
        } else {
            return ResponseEntity.badRequest().body("No file received");
        }
    }

    @PostMapping("/addLimb")
    public ResponseEntity<String> addLimb(@RequestBody Limb limb) {
        if (!limb.getFile().isEmpty()) {
            return ResponseEntity.ok().body(assetAlbumPostService.addLimb(limb));
        } else {
            return ResponseEntity.badRequest().body("No file received");
        }
    }

    @PutMapping("/updateAssetFilters")
    public ResponseEntity<String> updateAssetFilters(
            @RequestParam(value = "assetId") Integer assetId,
            @RequestParam(value = "type", defaultValue = "ASSET") AssetType assetType,
            @RequestParam(value = "username", required = false, defaultValue = "") String artistUsername,
            @RequestParam(value = "filename", required = false) String filename,
            @RequestParam(value = "hairColor", required = false) Hair.HairColor hairColor,
            @RequestParam(value = "hairLength", required = false) Hair.HairLength hairLength,
            @RequestParam(value = "hairType", required = false) Hair.HairType hairType,
            @RequestParam(value = "skinColor", required = false) BodyPart.SkinColor skinColor,
            @RequestParam(value = "hasTattoos", required = false) Boolean hasTattoos,
            @RequestParam(value = "headShape", required = false) Head.HeadShape headShape,
            @RequestParam(value = "hasGlasses", required = false) Boolean hasGlasses,
            @RequestParam(value = "sex", required = false) Torso.Sex sex,
            @RequestParam(value = "torsoShape", required = false) Torso.TorsoShape torsoShape,
            @RequestParam(value = "isLeft", required = false) Boolean isLeft,
            @RequestParam(value = "isArm", required = false) Boolean isArm
    ) {
        AssetRequest assetRequest = new AssetRequest(
                assetType, artistUsername, filename, hairColor, hairLength, hairType, skinColor, hasTattoos,
                headShape, hasGlasses, sex, torsoShape, isLeft, isArm
        );

        return ResponseEntity.ok().body("Updated "
                + assetAlbumPutService.updateFilters(assetRequest, assetId) + " rows");
    }

    @PutMapping("/updateAssetFile")
    public ResponseEntity<String> updateAssetFile (@RequestParam MultipartFile file, @RequestParam Integer assetId) {
        if (!file.isEmpty()) {
            return ResponseEntity.ok().body(assetAlbumPutService.updateAssetFile(file, assetId));
        } else {
            return ResponseEntity.badRequest().body("No file received");
        }
    }

    @DeleteMapping("/deleteAsset")
    public ResponseEntity<String> deleteAsset(@RequestParam Integer assetId) {
        return ResponseEntity.ok().body(assetAlbumDeleteService.deleteAsset(assetId) + " rows deleted");
    }
}