package com.dishianerifkinj;

import com.dishianerifkinj.service.AssetAlbumDeleteService;
import com.dishianerifkinj.service.AssetAlbumGetService;
import com.dishianerifkinj.domain.Hair;
import com.dishianerifkinj.domain.AssetRequest;

import com.dishianerifkinj.domain.*;

import java.util.List;

public class CommandLineApplication {
    public static void main(String[] args) {
        System.out.println("\nWelcome to Asset Vault!\n");
        System.out.println("To use this application, try run the following commands:");
        System.out.println("upload [asset name]");
        System.out.println("edit [asset name]");
        System.out.println("filter-by [asset name]");
        System.out.println("delete [asset name]\n");

        if (args[0].equals("filter-by")) {
            String input = args[1];

            AssetRequest assetRequest = new AssetRequest();

            if (isValidHairColor(input)) {
                AssetAlbumGetService assetAlbumGetService = new AssetAlbumGetService();

                Hair hairFilter = new Hair();
                hairFilter.setColor(Hair.HairColor.valueOf(input.toUpperCase()));
                
                assetRequest.setHairColor(hairFilter.getColor());

                List<Integer> hairAssetIds = assetAlbumGetService.getHairAssetIds(assetRequest);

                for (Integer assetId : hairAssetIds) {
                    System.out.println("Hair Asset ID: " + assetId);
                }
            } else if (isValidHairLength(input)) {
                AssetAlbumGetService assetAlbumGetService = new AssetAlbumGetService();

                Hair hairFilter = new Hair();
                hairFilter.setHairLength(Hair.HairLength.valueOf(input.toUpperCase()));
                
                assetRequest.setHairLength(hairFilter.getHairLength());

                List<Integer> hairAssetIds = assetAlbumGetService.getHairAssetIds(assetRequest);

                for (Integer assetId : hairAssetIds) {
                    System.out.println("Hair Asset ID: " + assetId);
                }
            } else if (isValidHairTexture(input)) {
                AssetAlbumGetService assetAlbumGetService = new AssetAlbumGetService();

                Hair hairFilter = new Hair();
                hairFilter.setHairType(Hair.HairType.valueOf(input.toUpperCase()));
                
                assetRequest.setHairType(hairFilter.getHairType());

                List<Integer> hairAssetIds = assetAlbumGetService.getHairAssetIds(assetRequest);

                for (Integer assetId : hairAssetIds) {
                    System.out.println("Hair Asset ID: " + assetId);
                }
            }
        } else if (args[0].equals("delete")) {
            String filename = args[1]; 

            AssetAlbumGetService assetAlbumGetService = new AssetAlbumGetService();
            AssetAlbumDeleteService assetAlbumDeleteService = new AssetAlbumDeleteService();
            AssetRequest assetRequest = new AssetRequest();
            assetRequest.setFileName(filename);

            List<Integer> assetIds = assetAlbumGetService.getAssetIds(assetRequest);

            if (!assetIds.isEmpty()) {
                // Assuming there is only one asset with the given filename, delete it
                Integer assetId = assetIds.get(0);
                int deletedRows = assetAlbumDeleteService.deleteAsset(assetId);
                if (deletedRows > 0) {
                    System.out.println("Asset with filename " + filename + " has been deleted.");
                } else {
                    System.out.println("Failed to delete asset with filename " + filename + ".");
                }
            } else {
                System.out.println("No asset found with filename " + filename + ".");
            }
        }
    }   

    private static boolean isValidHairColor(String color) {
        return color.equalsIgnoreCase("blonde") ||
                color.equalsIgnoreCase("light-brown") ||
                color.equalsIgnoreCase("brown") ||
                color.equalsIgnoreCase("dark-brown") ||
                color.equalsIgnoreCase("black") ||
                color.equalsIgnoreCase("red") ||
                color.equalsIgnoreCase("misc");
    }

    private static boolean isValidHairLength(String length) {
        return length.equalsIgnoreCase("short") ||
                length.equalsIgnoreCase("mid-length") ||
                length.equalsIgnoreCase("long");
    }

    private static boolean isValidHairTexture(String texture) {
        return texture.equalsIgnoreCase("straight") ||
                texture.equalsIgnoreCase("wavy") ||
                texture.equalsIgnoreCase("curly") ||
                texture.equalsIgnoreCase("coily");
    }
}
