package com.dishianerifkinj.controller;

import com.dishianerifkinj.domain.*;
import com.dishianerifkinj.service.AssetAlbumDeleteService;
import com.dishianerifkinj.service.AssetAlbumGetService;
import com.dishianerifkinj.service.AssetAlbumPostService;
import com.dishianerifkinj.service.AssetAlbumPutService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AssetAlbumCommandLineController {

    private final AssetAlbumPostService assetAlbumPostService = new AssetAlbumPostService();
    private final AssetAlbumGetService assetAlbumGetService = new AssetAlbumGetService();
    private final AssetAlbumPutService assetAlbumPutService = new AssetAlbumPutService();
    private final AssetAlbumDeleteService assetAlbumDeleteService = new AssetAlbumDeleteService();

    public void run() {
        System.out.println("\nWelcome to Asset Vault!\n");
        while (true) {
            System.out.println("Please select an action (1, 2, 3, or 4) or 'q' to quit:");
            System.out.println("1. 'upload' asset");
            System.out.println("2. get asset ids");
            System.out.println("3. update asset");
            System.out.println("4. delete asset\n");

            Scanner scanner = new Scanner(System.in);
            int action;
            try {
                action = scanner.nextInt();
            } catch (Exception e) {
                if (scanner.next().equalsIgnoreCase("q")) {
                    System.out.println("quitting...");
                    return;
                }
                action = 0;
            }

            switch (action) {
                case 1 -> uploadAsset();
                case 2 -> getAssetIds();
                case 3 -> updateAsset();
                case 4 -> deleteAsset();
                default -> System.out.println("Invalid action");
            }
        }
    }

    private void uploadAsset() {
        AssetType type;
        String filename;
        String ownerUsername;

        type = askForAssetType();

        filename = askForString("filename");
        ownerUsername = askForString("artist username");

        switch (type) {
            case HAIR -> uploadHairAsset(filename, ownerUsername);
            case HEAD -> uploadBodyPartAsset(filename, ownerUsername, AssetType.HEAD);
            case TORSO -> uploadBodyPartAsset(filename, ownerUsername, AssetType.TORSO);
            case LIMB -> uploadBodyPartAsset(filename, ownerUsername, AssetType.LIMB);
            default -> throw new RuntimeException("Something went wrong while selecting asset type");
        }
    }

    private void uploadHairAsset(String filename, String ownerUsername) {
        Hair.HairColor hairColor = askForHairColor();
        Hair.HairLength hairLength = askForHairLength();
        Hair.HairType hairType = askForHairType();

        System.out.println("Creating head asset with provided filters...");
        Hair hair = new Hair();
        hair.setFileName(filename);
        hair.setArtistUsername(ownerUsername);
        hair.setColor(hairColor);
        hair.setHairLength(hairLength);
        hair.setHairType(hairType);
        try {
            System.out.println(assetAlbumPostService.addHair(hair) + "\n");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void uploadBodyPartAsset(String filename, String ownerUsername, AssetType type) {
        BodyPart.SkinColor skinColor = askForSkinColor();
        boolean hasTattoos = askForHasTattoos();

        switch (type) {
            case HEAD -> uploadHeadAsset(filename, ownerUsername, skinColor, hasTattoos);
            case TORSO -> uploadTorsoAsset(filename, ownerUsername, skinColor, hasTattoos);
            case LIMB -> uploadLimbAsset(filename, ownerUsername, skinColor, hasTattoos);
            default -> throw new RuntimeException("Something went wrong while selecting asset type (body part)");
        }
    }

    private void uploadHeadAsset(String filename, String ownerUsername, BodyPart.SkinColor skinColor,
                                 boolean hasTattoos) {
        Head.HeadShape headShape = askForHeadShape();
        boolean hasGlasses = askForHasGlasses();

        System.out.println("Creating head asset with provided filters...");
        Head head = new Head();
        head.setFileName(filename);
        head.setArtistUsername(ownerUsername);
        head.setSkinColor(skinColor);
        head.setHasTattoos(hasTattoos);
        head.setHeadShape(headShape);
        head.setHasGlasses(hasGlasses);
        try {
            System.out.println(assetAlbumPostService.addHead(head) + "\n");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean askForHasGlasses() {
        Scanner scanner = new Scanner(System.in);
        boolean hasGlasses;
        while (true) {
            System.out.println("Does this head asset have glasses? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                hasGlasses = true;
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                hasGlasses = false;
                break;
            }
            System.out.println("Invalid input");
        }
        return hasGlasses;
    }

    private Head.HeadShape askForHeadShape() {
        Scanner scanner = new Scanner(System.in);
        Head.HeadShape headShape;
        while (true) {
            System.out.println("Please input head shape:");
            System.out.println("Oval");
            System.out.println("Round");
            System.out.println("Square");
            System.out.println("Diamond");
            System.out.println("Heart");

            String shape = scanner.next();
            try {
                headShape = Head.HeadShape.getHeadShapeFromString(shape);
            } catch (Exception e) {
                System.out.println("Invalid shape");
                continue;
            }
            break;
        }

        return headShape;
    }

    private void uploadTorsoAsset(String filename, String ownerUsername, BodyPart.SkinColor skinColor,
                                  boolean hasTattoos) {
        Torso.Sex sex = askForSex();
        Torso.TorsoShape torsoShape = askForTorsoShape();

        System.out.println("Creating torso asset with provided filters...");
        Torso torso = new Torso();
        torso.setFileName(filename);
        torso.setArtistUsername(ownerUsername);
        torso.setSkinColor(skinColor);
        torso.setHasTattoos(hasTattoos);
        torso.setTorsoShape(torsoShape);
        torso.setSex(sex);
        try {
            System.out.println(assetAlbumPostService.addTorso(torso) + "\n");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    private Torso.Sex askForSex() {
        Scanner scanner = new Scanner(System.in);
        Torso.Sex sex;
        while (true) {
            System.out.println("Select sex of torso:");
            System.out.println("Male");
            System.out.println("Female");
            String input = scanner.next();
            if (input.equalsIgnoreCase("Male")) {
                sex = Torso.Sex.MALE;
                break;
            }
            if (input.equalsIgnoreCase("Female")) {
                sex = Torso.Sex.FEMALE;
                break;
            }
            System.out.println("Invalid input");
        }
        return sex;
    }

    private Torso.TorsoShape askForTorsoShape() {
        Scanner scanner = new Scanner(System.in);
        Torso.TorsoShape torsoShape;
        while (true) {
            System.out.println("Please input torso shape:");
            System.out.println("Triangle");
            System.out.println("Pear");
            System.out.println("Rectangle");
            System.out.println("Hourglass");
            System.out.println("Apple");

            String shape = scanner.next();
            try {
                torsoShape = Torso.TorsoShape.getTorsoShapeFromString(shape);
            } catch (Exception e) {
                System.out.println("Invalid shape");
                continue;
            }
            break;
        }

        return torsoShape;
    }

    private void uploadLimbAsset(String filename, String ownerUsername, BodyPart.SkinColor skinColor,
                                 boolean hasTattoos) {
        boolean isLeft = askForIsLeft();
        boolean isArm = askForIsArm();

        System.out.println("Creating limb asset with provided filters...");
        Limb limb = new Limb();
        limb.setFileName(filename);
        limb.setArtistUsername(ownerUsername);
        limb.setSkinColor(skinColor);
        limb.setHasTattoos(hasTattoos);
        limb.setArm(isArm);
        limb.setLeft(isLeft);
        try {
            System.out.println(assetAlbumPostService.addLimb(limb) + "\n");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    private boolean askForIsLeft() {
        Scanner scanner = new Scanner(System.in);
        boolean isLeft;
        while (true) {
            System.out.println("Is the limb right or left? (enter right or left)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("right")) {
                isLeft = false;
                break;
            }
            if (input.equalsIgnoreCase("left")) {
                isLeft = true;
                break;
            }
            System.out.println("Invalid input");
        }
        return isLeft;
    }

    private boolean askForIsArm() {
        Scanner scanner = new Scanner(System.in);
        boolean isArm;
        while (true) {
            System.out.println("Is the limb an arm or a leg? (enter arm or leg)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("arm")) {
                isArm = true;
                break;
            }
            if (input.equalsIgnoreCase("leg")) {
                isArm = false;
                break;
            }
            System.out.println("Invalid input");
        }

        return isArm;
    }

    private void updateAsset() {
        AssetType type;
        String filename;
        String ownerUsername;
        int id;
        Scanner scanner = new Scanner(System.in);

        type = askForAssetType();

        while (true) {
            System.out.println("Please input ID of " + type.getTypeString() + " asset:");
            try {
                id = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Non-integer input, all IDs are integers.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.println("Would you like to update the filename of this asset? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                filename = askForString("filename");
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                filename = null;
                break;
            }
            System.out.println("Invalid input");
        }

        while (true) {
            System.out.println("Would you like to update the artist username of this asset? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                ownerUsername = askForString("artist username");
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                ownerUsername = null;
                break;
            }
            System.out.println("Invalid input");
        }

        boolean updateSpecificFilters;
        while (true) {
            System.out.println("Are there any " + type.getTypeString() + " filters you would like to update? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                updateSpecificFilters = true;
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                updateSpecificFilters = false;
                break;
            }
            System.out.println("Invalid input");
        }

        if (updateSpecificFilters) {
            switch (type) {
                case HAIR -> updateHair(id, filename, ownerUsername);
                case HEAD -> updateHead(id, filename, ownerUsername);
                case TORSO -> updateTorso(id, filename, ownerUsername);
                case LIMB -> updateLimb(id, filename, ownerUsername);
                default -> throw new RuntimeException("Something went wrong while asking for asset type");
            }
        } else {
            AssetRequest request = new AssetRequest();
            request.setAssetType(AssetType.ASSET);
            request.setFileName(filename);
            request.setArtistUsername(ownerUsername);

            System.out.println("updated " + assetAlbumPutService.updateFilters(request, id) + " rows");
        }
    }

    private void updateHair(int id, String filename, String ownerUsername) {
        Hair.HairColor hairColor = askForHairColor();
        Hair.HairLength hairLength = askForHairLength();
        Hair.HairType hairType = askForHairType();

        AssetRequest request = new AssetRequest();
        request.setAssetType(AssetType.HAIR);
        request.setFileName(filename);
        request.setArtistUsername(ownerUsername);
        request.setHairColor(hairColor);
        request.setHairLength(hairLength);
        request.setHairType(hairType);

        System.out.println("updated " + assetAlbumPutService.updateFilters(request, id) + " rows");
    }

    private Hair.HairColor askForHairColor() {
        Hair.HairColor hairColor;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input hair color:");
            System.out.println("Blonde");
            System.out.println("Light Brown");
            System.out.println("Brown");
            System.out.println("Dark Brown");
            System.out.println("Black");
            System.out.println("Red");
            System.out.println("Misc");

            String color = scanner.next();
            try {
                hairColor = Hair.HairColor.getHairColorFromString(color);
            } catch (Exception e) {
                System.out.println("Invalid color");
                continue;
            }
            break;
        }
        return hairColor;
    }

    private Hair.HairLength askForHairLength() {
        Hair.HairLength hairLength;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please input hair length:");
            System.out.println("Long");
            System.out.println("Mid-length");
            System.out.println("Short");

            String length = scanner.next();
            try {
                hairLength = Hair.HairLength.getHairLengthFromString(length);
            } catch (Exception e) {
                System.out.println("Invalid length");
                continue;
            }
            break;
        }

        return hairLength;
    }

    private Hair.HairType askForHairType() {
        Scanner scanner = new Scanner(System.in);
        Hair.HairType hairType;
        while (true) {
            System.out.println("Please input hair type:");
            System.out.println("Straight");
            System.out.println("Wavy");
            System.out.println("Curly");
            System.out.println("Coily");

            String type = scanner.next();
            try {
                hairType = Hair.HairType.getHairTypeFromString(type);
            } catch (Exception e) {
                System.out.println("Invalid type");
                continue;
            }
            break;
        }

        return hairType;
    }

    private void updateHead(int id, String filename, String ownerUsername) {
        BodyPart.SkinColor skinColor = askForSkinColor();
        boolean hasTattoos = askForHasTattoos();
        Head.HeadShape headShape = askForHeadShape();
        boolean hasGlasses = askForHasGlasses();

        AssetRequest request = new AssetRequest();
        request.setAssetType(AssetType.HEAD);
        request.setFileName(filename);
        request.setArtistUsername(ownerUsername);
        request.setHasTattoos(hasTattoos);
        request.setSkinColor(skinColor);
        request.setHeadShape(headShape);
        request.setHasGlasses(hasGlasses);

        System.out.println("updated " + assetAlbumPutService.updateFilters(request, id) + " rows");
    }

    private void updateTorso(int id, String filename, String ownerUsername) {
        BodyPart.SkinColor skinColor = askForSkinColor();
        boolean hasTattoos = askForHasTattoos();
        Torso.TorsoShape torsoShape = askForTorsoShape();
        Torso.Sex sex = askForSex();

        AssetRequest request = new AssetRequest();
        request.setAssetType(AssetType.TORSO);
        request.setFileName(filename);
        request.setArtistUsername(ownerUsername);
        request.setHasTattoos(hasTattoos);
        request.setSkinColor(skinColor);
        request.setSex(sex);
        request.setTorsoShape(torsoShape);

        System.out.println("updated " + assetAlbumPutService.updateFilters(request, id) + " rows");
    }

    private void updateLimb(int id, String filename, String ownerUsername) {
        BodyPart.SkinColor skinColor = askForSkinColor();
        boolean hasTattoos = askForHasTattoos();
        boolean isLeft = askForIsLeft();
        boolean isArm = askForIsArm();

        AssetRequest request = new AssetRequest();
        request.setAssetType(AssetType.LIMB);
        request.setFileName(filename);
        request.setArtistUsername(ownerUsername);
        request.setHasTattoos(hasTattoos);
        request.setSkinColor(skinColor);
        request.setIsArm(isArm);
        request.setIsLeft(isLeft);

        System.out.println("updated " + assetAlbumPutService.updateFilters(request, id) + " rows");
    }

    private BodyPart.SkinColor askForSkinColor() {
        BodyPart.SkinColor skinColor;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input skin shade:");
            System.out.println("Light");
            System.out.println("Medium");
            System.out.println("Dark");

            String shade = scanner.next();
            try {
                skinColor = BodyPart.SkinColor.getSkinColorFromString(shade);
            } catch (Exception e) {
                System.out.println("Invalid shade");
                continue;
            }
            break;
        }

        return skinColor;
    }

    private boolean askForHasTattoos() {
        boolean hasTattoos;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Does this body part asset have tattoos? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                hasTattoos = true;
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                hasTattoos = false;
                break;
            }
            System.out.println("Invalid input");
        }

        return hasTattoos;
    }

    private AssetType askForAssetType() {
        Scanner scanner = new Scanner(System.in);
        AssetType type;
        while (true) {
            System.out.println("Please specify asset type:");
            System.out.println("Hair");
            System.out.println("Head");
            System.out.println("Torso");
            System.out.println("Limb");

            String typeString = scanner.next();
            try {
                type = AssetType.getTypeFromString(typeString);
            } catch (Exception e) {
                System.out.println("Invalid asset type");
                continue;
            }
            // user should only be able to upload assets under concrete (non-abstract) types
            if (type.equals(AssetType.ASSET) || type.equals(AssetType.BODY_PART)) {
                System.out.println("Invalid asset type");
                continue;
            }
            return type;
        }
    }

    private String askForString(String nameOfString) {
        String str;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter " + nameOfString + " for asset:");

            str = scanner.next();
            if (str.length() == 0) {
                System.out.println(nameOfString + " is empty");
                continue;
            }
            break;
        }

        return str;
    }

    private void getAssetIds() {
        AssetType type;
        String filename;
        String username;

        Map<Integer, AssetType> assetTypeMap = new HashMap<>();
        assetTypeMap.put(1, AssetType.ASSET);
        assetTypeMap.put(2, AssetType.BODY_PART);
        assetTypeMap.put(3, AssetType.HAIR);
        assetTypeMap.put(4, AssetType.HEAD);
        assetTypeMap.put(5, AssetType.TORSO);
        assetTypeMap.put(6, AssetType.LIMB);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Which asset type would you like to filter by? (select number)");
            System.out.println("1. All assets");
            System.out.println("2. Body parts");
            System.out.println("3. Hair");
            System.out.println("4. Heads");
            System.out.println("5. Torsos");
            System.out.println("6. Limbs");

            int selection;
            try {
                selection = scanner.nextInt();
                type = assetTypeMap.get(selection);
            } catch (Exception e) {
                System.out.println("Invalid selection");
                continue;
            }
            if (type == null) {
                System.out.println("Invalid selection");
                continue;
            }
            break;
        }

        while (true) {
            System.out.println("Would you like to filter by filename? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                filename = askForString("filename");
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                filename = null;
                break;
            }
            System.out.println("Invalid input");
        }
        while (true) {
            System.out.println("Would you like to filter by username? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                username = askForString("artist username");
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                username = null;
                break;
            }
            System.out.println("Invalid input");
        }

        boolean addSpecificFilters = false;
        if (type != AssetType.ASSET) {
            while (true) {
                System.out.println("Would you like to filter by any " + type.getTypeString() + " filters? (y/n)");
                String input = scanner.next();
                if (input.equalsIgnoreCase("y")) {
                    addSpecificFilters = true;
                    break;
                }
                if (input.equalsIgnoreCase("n")) {
                    break;
                }
                System.out.println("Invalid input");
            }
        }

        if (addSpecificFilters) {
            if (type.equals(AssetType.HAIR)) {
                getHairFilters(filename, username);
            } else {
                getBodyPartFilters(type, filename, username);
            }
        } else {
            AssetRequest request = new AssetRequest();
            request.setAssetType(type);
            request.setFileName(filename);
            request.setArtistUsername(username);

            System.out.println("fetching IDs that match given filter(s)...");
            for (Integer i : assetAlbumGetService.getAssetIds(request)) {
                System.out.println(i);
            }
            System.out.println();
        }
    }

    private void getHairFilters(String filename, String username) {
        Scanner scanner = new Scanner(System.in);
        Hair.HairColor hairColor;
        Hair.HairType hairType;
        Hair.HairLength hairLength;

        while (true) {
            System.out.println("Would you like to filter by hair color? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                hairColor = askForHairColor();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                hairColor = null;
                break;
            }
            System.out.println("Invalid input");
        }
        while (true) {
            System.out.println("Would you like to filter by hair length? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                hairLength = askForHairLength();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                hairLength = null;
                break;
            }
            System.out.println("Invalid input");
        }
        while (true) {
            System.out.println("Would you like to filter by hair type? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                hairType = askForHairType();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                hairType = null;
                break;
            }
            System.out.println("Invalid input");
        }

        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setFileName(filename);
        assetRequest.setArtistUsername(username);
        assetRequest.setHairColor(hairColor);
        assetRequest.setHairLength(hairLength);
        assetRequest.setHairType(hairType);

        System.out.println("fetching IDs that match given filter(s)...");
        for (Integer i : assetAlbumGetService.getAssetIds(assetRequest)) {
            System.out.println(i);
        }
        System.out.println();
    }

    private void getBodyPartFilters(AssetType type, String filename, String username) {
        Scanner scanner = new Scanner(System.in);
        BodyPart.SkinColor skinColor;
        Boolean hasTattoos;

        while (true) {
            System.out.println("Would you like to filter by skin color? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                skinColor = askForSkinColor();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                skinColor = null;
                break;
            }
            System.out.println("Invalid input");
        }
        while (true) {
            System.out.println("Would you like to filter by whether the asset has tattoos? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                hasTattoos = askForHasTattoos();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                hasTattoos = null;
                break;
            }
            System.out.println("Invalid input");
        }

        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setAssetType(type);
        assetRequest.setFileName(filename);
        assetRequest.setArtistUsername(username);
        assetRequest.setSkinColor(skinColor);
        assetRequest.setHasTattoos(hasTattoos);

        switch (type) {
            case HEAD -> getHeadFilters(assetRequest);
            case TORSO -> getTorsoFilters(assetRequest);
            case LIMB -> getLimbFilters(assetRequest);
            case BODY_PART -> {
                System.out.println("fetching IDs that match given filter(s)...");
                for (Integer i : assetAlbumGetService.getAssetIds(assetRequest)) {
                    System.out.println(i);
                }
                System.out.println();
            }
        }
    }

    private void getHeadFilters(AssetRequest assetRequest) {
        Scanner scanner = new Scanner(System.in);
        Head.HeadShape headShape;
        Boolean hasGlasses;

        while (true) {
            System.out.println("Would you like to filter by head shape? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                headShape = askForHeadShape();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                headShape = null;
                break;
            }
            System.out.println("Invalid input");
        }
        while (true) {
            System.out.println("Would you like to filter by whether the asset has glasses? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                hasGlasses = askForHasGlasses();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                hasGlasses = null;
                break;
            }
            System.out.println("Invalid input");
        }

        assetRequest.setHeadShape(headShape);
        assetRequest.setHasGlasses(hasGlasses);

        System.out.println("fetching IDs that match given filter(s)...");
        for (Integer i : assetAlbumGetService.getAssetIds(assetRequest)) {
            System.out.println(i);
        }
        System.out.println();
    }

    private void getTorsoFilters(AssetRequest assetRequest) {
        Scanner scanner = new Scanner(System.in);
        Torso.TorsoShape torsoShape;
        Torso.Sex sex;

        while (true) {
            System.out.println("Would you like to filter by torso shape? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                torsoShape = askForTorsoShape();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                torsoShape = null;
                break;
            }
            System.out.println("Invalid input");
        }
        while (true) {
            System.out.println("Would you like to filter by sex? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                sex = askForSex();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                sex = null;
                break;
            }
            System.out.println("Invalid input");
        }

        assetRequest.setTorsoShape(torsoShape);
        assetRequest.setSex(sex);

        System.out.println("fetching IDs that match given filter(s)...");
        for (Integer i : assetAlbumGetService.getAssetIds(assetRequest)) {
            System.out.println(i);
        }
        System.out.println();
    }

    private void getLimbFilters(AssetRequest assetRequest) {
        Scanner scanner = new Scanner(System.in);
        Boolean isArm;
        Boolean isLeft;

        while (true) {
            System.out.println("Would you like to filter by arms/legs? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                isArm = askForIsArm();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                isArm = null;
                break;
            }
            System.out.println("Invalid input");
        }
        while (true) {
            System.out.println("Would you like to filter by left/right? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                isLeft = askForIsLeft();
                break;
            }
            if (input.equalsIgnoreCase("n")) {
                isLeft = null;
                break;
            }
            System.out.println("Invalid input");
        }

        assetRequest.setIsLeft(isLeft);
        assetRequest.setIsArm(isArm);

        System.out.println("fetching IDs that match given filter(s)...");
        for (Integer i : assetAlbumGetService.getAssetIds(assetRequest)) {
            System.out.println(i);
        }
        System.out.println();
    }

    private void deleteAsset() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter asset ID of asset you would like to delete:");
            int id;
            try {
                id = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input, IDs are integers only");
                continue;
            }

            System.out.println("Deleting asset...");
            System.out.println("Deleted " + assetAlbumDeleteService.deleteAsset(id) + " rows");
            break;
        }
    }
}
