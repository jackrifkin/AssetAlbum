package com.dishianerifkinj.service;

import com.dishianerifkinj.domain.AssetRequest;
import com.dishianerifkinj.domain.AssetType;
import com.dishianerifkinj.util.BlobMultipartFile;
import com.dishianerifkinj.util.ConnectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

@Service
public class AssetAlbumGetService {
    private final Connection conn;
    private static final String WHERE = " WHERE";
    private static final String AND = " AND";

    public AssetAlbumGetService () {
        try {
            conn = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public List<Integer> getAssetIds(AssetRequest assetRequest) {
        Map<AssetType, Function<AssetRequest, List<Integer>>> functionMap = new HashMap<>();
        functionMap.put(AssetType.ASSET, this::getAssetTypeIds);
        functionMap.put(AssetType.BODY_PART, this::getBodyPartAssetIds);
        functionMap.put(AssetType.HAIR, this::getHairAssetIds);
        functionMap.put(AssetType.HEAD, this::getHeadAssetIds);
        functionMap.put(AssetType.TORSO, this::getTorsoAssetIds);
        functionMap.put(AssetType.LIMB, this::getLimbAssetIds);

        return functionMap.get(assetRequest.getAssetType()).apply(assetRequest);
    }

    public MultipartFile getAssetFile(Integer assetId) {
        try {
            String query = "SELECT file, file_name FROM asset_file WHERE asset_id = " + assetId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            byte[] bytes;
            if (rs.next()) {
                Blob blob = rs.getBlob(1);
                InputStream inputStream = blob.getBinaryStream();
                bytes = inputStream.readAllBytes();
                return new BlobMultipartFile(bytes, rs.getString(2));
            } else {
                throw new RuntimeException("No file with given asset id");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error thrown while fetching asset file", e);
        }
    }

    private List<Integer> getAssetTypeIds(AssetRequest assetRequest) {
        String query = "SELECT asset_id FROM asset NATURAL JOIN asset_file";
        int clauseCount = 0;

        if (assetRequest.getArtistUsername() != null) {
            query = query.concat("WHERE owner_username = " + assetRequest.getArtistUsername());
            clauseCount++;
        }
        if (assetRequest.getFileName() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " file_name = " + assetRequest.getFileName();
        }

        try {
            return this.executeQueryForIntList(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error while querying for asset ids", e);
        }
    }

    // I CHANGED THIS TO PUBLIC :(((((((
        public List<Integer> getHairAssetIds(AssetRequest assetRequest) {
        String query = "SELECT asset_id FROM hair NATURAL JOIN asset NATURAL JOIN asset_file";

        int clauseCount = 0;
        if (assetRequest.getHairColor() != null) {
            query += WHERE;
            query += " hair_color = " + assetRequest.getHairColor();
            clauseCount++;
        }
        if (assetRequest.getHairLength() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " hair_length = " + assetRequest.getHairLength();
            clauseCount++;
        }
        if (assetRequest.getHairType() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " hair_type = " + assetRequest.getHairType();
            clauseCount++;
        }
        if (assetRequest.getArtistUsername() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " owner_username = " + assetRequest.getArtistUsername();
        }
        if (assetRequest.getFileName() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " file_name = " + assetRequest.getFileName();
        }

        try {
            return this.executeQueryForIntList(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error while querying for hair asset ids", e);
        }
    }

    private List<Integer> getBodyPartAssetIds(AssetRequest assetRequest) {
        String query = "SELECT asset_id FROM body_part NATURAL JOIN asset NATURAL JOIN asset_file";

        query = getAdditionalBodyPartQuery(assetRequest, query, 0);

        try {
            return this.executeQueryForIntList(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error while querying for hair asset ids", e);
        }
    }

    private List<Integer> getHeadAssetIds(AssetRequest assetRequest) {
        String query = "SELECT asset_id FROM head NATURAL JOIN body_part NATURAL JOIN asset NATURAL JOIN asset_file";

        int clauseCount = 0;
        if (assetRequest.getHeadShape() != null) {
            query += WHERE;
            query += " head_shape = " + assetRequest.getHeadShape();
            clauseCount++;
        }
        if (assetRequest.getHasGlasses() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " has_glasses = " + assetRequest.getHasGlasses();
            clauseCount++;
        }
        query = getAdditionalBodyPartQuery(assetRequest, query, clauseCount);

        try {
            return this.executeQueryForIntList(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error while querying for head asset ids", e);
        }
    }

    private List<Integer> getLimbAssetIds(AssetRequest assetRequest) {
        String query = "SELECT asset_id FROM limb NATURAL JOIN body_part NATURAL JOIN asset NATURAL JOIN asset_file";

        int clauseCount = 0;
        if (assetRequest.getIsLeft() != null) {
            query += WHERE;
            query += " is_left = " + assetRequest.getIsLeft();
            clauseCount++;
        }
        if (assetRequest.getIsArm() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " is_arm = " + assetRequest.getIsArm();
        }
        query = getAdditionalBodyPartQuery(assetRequest, query, clauseCount);

        try {
            return this.executeQueryForIntList(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error while querying for limb asset ids", e);
        }
    }

    private List<Integer> getTorsoAssetIds(AssetRequest assetRequest) {
        String query = "SELECT asset_id FROM torso NATURAL JOIN body_part NATURAL JOIN asset NATURAL JOIN asset_file";

        int clauseCount = 0;
        if (assetRequest.getTorsoShape() != null) {
            query += WHERE;
            query += " torso_shape = " + assetRequest.getTorsoShape();
            clauseCount++;
        }
        if (assetRequest.getSex() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " sex = " + assetRequest.getSex();
        }
        query = getAdditionalBodyPartQuery(assetRequest, query, clauseCount);

        try {
            return this.executeQueryForIntList(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error while querying for torso asset ids", e);
        }
    }

    private String getAdditionalBodyPartQuery(AssetRequest assetRequest, String query, int clauseCount) {
        if (assetRequest.getSkinColor() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " skin_color = " + assetRequest.getSkinColor();
            clauseCount++;
        }
        if (assetRequest.getHasTattoos() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " has_tattoos = " + assetRequest.getHasTattoos();
            clauseCount++;
        }
        if (assetRequest.getArtistUsername() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " owner_username = " + assetRequest.getArtistUsername();
        }
        if (assetRequest.getFileName() != null) {
            query += (clauseCount == 0) ? WHERE : AND;
            query += " file_name = " + assetRequest.getArtistUsername();
        }
        return query;
    }

    private List<Integer> executeQueryForIntList(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        List<Integer> ids = new ArrayList<>();
        while (rs.next()) {
            ids.add(rs.getInt(1));
        }

        rs.close();
        return ids;
    }
}
