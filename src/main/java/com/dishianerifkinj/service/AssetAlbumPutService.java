package com.dishianerifkinj.service;

import com.dishianerifkinj.domain.AssetRequest;
import com.dishianerifkinj.util.ConnectionUtil;
import com.dishianerifkinj.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.*;
import java.util.Properties;

@Service
public class AssetAlbumPutService {
    private final Connection conn;
    private static final String WHERE = " WHERE";

    public AssetAlbumPutService () {
        try {
            conn = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public String updateAssetFile(MultipartFile file, Integer assetId) {
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement("UPDATE asset_file SET file = ? WHERE asset_id = " + assetId);
            pstmt.setBlob(1, FileUtil.multiPartFileToBlob(file));

            int updatedRows = pstmt.executeUpdate();
            return "Updated " + updatedRows + " rows";
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating asset file", e);
        }
    }

    public Integer updateFilters(AssetRequest assetRequest, Integer assetId) {
        switch (assetRequest.getAssetType()) {
            case ASSET -> {
                return updateAssetFilters(assetId, assetRequest);
            }
            case BODY_PART -> {
                return updateBodyPartFilters(assetId, assetRequest);
            }
            case HAIR -> {
                return updateHairFilters(assetId, assetRequest);
            }
            case HEAD -> {
                return updateHeadFilters(assetId, assetRequest);
            }
            case TORSO -> {
                return updateTorsoFilters(assetId, assetRequest);
            }
            case LIMB -> {
                return updateLimbFilters(assetId, assetRequest);
            }
            default -> throw new RuntimeException("Invalid asset type");
        }
    }

    private Integer updateAssetFilters(Integer assetId, AssetRequest assetRequest) {
        try {
            String updateQuery = "UPDATE asset SET ";
            String clauses = updateAssetFiltersQuery(assetRequest);
            if (clauses.length() > 0) {
                updateQuery += clauses;
                updateQuery += WHERE + " asset_id = " + assetId;

                Statement stmt = conn.createStatement();
                return stmt.executeUpdate(updateQuery);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating filters", e);
        }
    }

    private String updateAssetFiltersQuery(AssetRequest assetRequest) {
        String query = "";
        int clauseCount = 0;

        if (assetRequest.getArtistUsername() != null) {
            query += " owner_username = " + assetRequest.getArtistUsername();
            clauseCount++;
        }
        if (assetRequest.getFileName() != null) {
            query += (clauseCount > 0) ? "," : "";
            query += " file_name = " + assetRequest.getFileName();
        }

        return query;
    }

    private Integer updateBodyPartFilters(Integer assetId, AssetRequest assetRequest) {
        try {
            String updateQuery = "UPDATE body_part SET ";
            String clauses = updateBodyPartFiltersQuery(assetRequest);

            return executeUpdateQuery_AssetSubclasses(assetId, assetRequest, updateQuery, clauses);
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating body_part filters", e);
        }
    }

    private String updateBodyPartFiltersQuery(AssetRequest assetRequest) {
        String query = "";
        int clauseCount = 0;

        if (assetRequest.getSkinColor() != null) {
            query += " skin_color = " + assetRequest.getSkinColor();
            clauseCount++;
        }
        if (assetRequest.getHasTattoos() != null) {
            query += (clauseCount > 0) ? "," : "";
            query += " has_tattoos = " + assetRequest.getHasTattoos();
        }

        return query;
    }

    private Integer updateHairFilters(Integer assetId, AssetRequest assetRequest) {
        try {
            String updateQuery = "UPDATE hair SET ";
            String clauses = updateHairFiltersQuery(assetRequest);

            return executeUpdateQuery_AssetSubclasses(assetId, assetRequest, updateQuery, clauses);
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating hair filters", e);
        }
    }

    private String updateHairFiltersQuery(AssetRequest assetRequest) {
        String query = "";
        int clauseCount = 0;

        if (assetRequest.getHairColor() != null) {
            query += " hair_color = " + assetRequest.getHairColor();
            clauseCount++;
        }
        if (assetRequest.getHairLength() != null) {
            query += (clauseCount > 0) ? "," : "";
            query += " hair_length = " + assetRequest.getHairLength();
        }
        if (assetRequest.getHairType() != null) {
            query += (clauseCount > 0) ? "," : "";
            query += " hair_type = " + assetRequest.getHairType();
        }

        return query;
    }

    private Integer executeUpdateQuery_AssetSubclasses(Integer assetId, AssetRequest assetRequest,
                                                       String updateQuery, String clauses) throws SQLException {
        if (clauses.length() > 0) {
            updateQuery += clauses;
            updateQuery += WHERE + " asset_id = " + assetId;

            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(updateQuery) + updateAssetFilters(assetId, assetRequest);
        } else {
            return updateAssetFilters(assetId, assetRequest);
        }
    }

    private Integer updateHeadFilters(Integer assetId, AssetRequest assetRequest) {
        try {
            String updateQuery = "UPDATE head SET ";
            String clauses = updateHeadFiltersQuery(assetRequest);

            return executeUpdateQuery_BodyPartSubclasses(assetId, assetRequest, updateQuery, clauses);
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating head filters", e);
        }
    }

    private String updateHeadFiltersQuery(AssetRequest assetRequest) {
        String query = "";
        int clauseCount = 0;

        if (assetRequest.getHeadShape() != null) {
            query += " head_shape = " + assetRequest.getHeadShape();
            clauseCount++;
        }
        if (assetRequest.getHasGlasses() != null) {
            query += (clauseCount > 0) ? "," : "";
            query += " has_glasses = " + assetRequest.getHasGlasses();
        }

        return query;
    }

    private Integer updateTorsoFilters(Integer assetId, AssetRequest assetRequest) {
        try {
            String updateQuery = "UPDATE torso SET ";
            String clauses = updateTorsoFiltersQuery(assetRequest);

            return executeUpdateQuery_BodyPartSubclasses(assetId, assetRequest, updateQuery, clauses);
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating torso filters", e);
        }
    }

    private String updateTorsoFiltersQuery(AssetRequest assetRequest) {
        String query = "";
        int clauseCount = 0;

        if (assetRequest.getSex() != null) {
            query += " sex = " + assetRequest.getSex();
            clauseCount++;
        }
        if (assetRequest.getTorsoShape() != null) {
            query += (clauseCount > 0) ? "," : "";
            query += " torso_shape = " + assetRequest.getTorsoShape();
        }

        return query;
    }

    private Integer updateLimbFilters(Integer assetId, AssetRequest assetRequest) {
        try {
            String updateQuery = "UPDATE limb SET ";
            String clauses = updateLimbFiltersQuery(assetRequest);

            return executeUpdateQuery_BodyPartSubclasses(assetId, assetRequest, updateQuery, clauses);
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating limb filters", e);
        }
    }

    private String updateLimbFiltersQuery(AssetRequest assetRequest) {
        String query = "";
        int clauseCount = 0;

        if (assetRequest.getIsLeft() != null) {
            query += " is_left = " + assetRequest.getIsLeft();
            clauseCount++;
        }
        if (assetRequest.getIsArm() != null) {
            query += (clauseCount > 0) ? "," : "";
            query += " is_arm = " + assetRequest.getIsArm();
        }

        return query;
    }

    private Integer executeUpdateQuery_BodyPartSubclasses(Integer assetId, AssetRequest assetRequest,
                                                          String updateQuery, String clauses) throws SQLException {
        if (clauses.length() > 0) {
            updateQuery += clauses;
            updateQuery += WHERE + " asset_id = " + assetId;

            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(updateQuery) + updateBodyPartFilters(assetId, assetRequest);
        } else {
            return updateBodyPartFilters(assetId, assetRequest);
        }
    }
}
