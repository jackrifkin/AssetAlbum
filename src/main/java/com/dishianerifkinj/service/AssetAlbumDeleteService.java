package com.dishianerifkinj.service;

import com.dishianerifkinj.util.ConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AssetAlbumDeleteService {
    private final Connection conn;

    public AssetAlbumDeleteService () {
        try {
            conn = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public int deleteAsset(Integer assetId) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM asset WHERE asset_id = ?");
            pstmt.setInt(1, assetId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting asset", e);
        }
    }
}
