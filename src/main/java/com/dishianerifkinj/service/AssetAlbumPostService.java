package com.dishianerifkinj.service;

import com.dishianerifkinj.domain.*;
import com.dishianerifkinj.util.ConnectionUtil;
import com.dishianerifkinj.util.FileUtil;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AssetAlbumPostService {
    private final Connection conn;

    public AssetAlbumPostService() {
        try {
            conn = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public String addHair(Hair hair) {
        try {
            CallableStatement stmt = conn.prepareCall("CALL add_hair(?,?,?,?,?,?,?)");
            stmt.setString(1, hair.getArtistUsername());
            stmt.setString(2, hair.getFileName());
            // if statement for CLI, would have been single line if upload file functionality was used in front-end
            if (hair.getFile() != null) {
                stmt.setBlob(3, FileUtil.multiPartFileToBlob(hair.getFile()));
            } else {
                stmt.setNull(3, Types.BLOB);
            }
            stmt.setString(4, hair.getColor().toString());
            stmt.setString(5, hair.getHairLength().toString());
            stmt.setString(6, hair.getHairType().toString());

            stmt.registerOutParameter(7, Types.VARCHAR);

            stmt.execute();

            String errorMessage = stmt.getString(7);

            if (errorMessage == null) {
                return "Successfully added Hair data";
            } else {
                throw new RuntimeException(errorMessage);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error thrown while inserting hair data", e);
        }
    }

    public String addHead(Head head) {
        try {
            CallableStatement stmt = conn.prepareCall("CALL add_head(?,?,?,?,?,?,?,?)");
            stmt.setString(1, head.getArtistUsername());
            stmt.setString(2, head.getFileName());
            // if statement for CLI, would have been single line if upload file functionality was used in front-end
            if (head.getFile() != null) {
                stmt.setBlob(3, FileUtil.multiPartFileToBlob(head.getFile()));
            } else {
                stmt.setNull(3, Types.BLOB);
            }
            stmt.setString(4, head.getSkinColor().toString());
            stmt.setBoolean(5, head.isHasTattoos());
            stmt.setBoolean(6, head.isHasGlasses());
            stmt.setString(7, head.getHeadShape().toString());

            stmt.registerOutParameter(8, Types.VARCHAR);

            stmt.execute();

            String errorMessage = stmt.getString(8);

            if (errorMessage == null) {
                return "Successfully added Head data";
            } else {
                throw new RuntimeException(errorMessage);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error thrown while inserting head data", e);
        }
    }

    public String addTorso(Torso torso) {
        try {
            CallableStatement stmt = conn.prepareCall("CALL add_torso(?,?,?,?,?,?,?,?)");
            stmt.setString(1, torso.getArtistUsername());
            stmt.setString(2, torso.getFileName());
            // if statement for CLI, would have been single line if upload file functionality was used in front-end
            if (torso.getFile() != null) {
                stmt.setBlob(3, FileUtil.multiPartFileToBlob(torso.getFile()));
            } else {
                stmt.setNull(3, Types.BLOB);
            }
            stmt.setString(4, torso.getSkinColor().toString());
            stmt.setBoolean(5, torso.isHasTattoos());
            stmt.setString(6, torso.getSex().toString());
            stmt.setString(7, torso.getTorsoShape().toString());

            stmt.registerOutParameter(8, Types.VARCHAR);

            stmt.execute();

            String errorMessage = stmt.getString(8);

            if (errorMessage == null) {
                return "Successfully added Torso data";
            } else {
                throw new RuntimeException(errorMessage);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error thrown while inserting torso data", e);
        }
    }

    public String addLimb(Limb limb) {
        try {
            CallableStatement stmt = conn.prepareCall("CALL add_limb(?,?,?,?,?,?,?,?)");
            stmt.setString(1, limb.getArtistUsername());
            stmt.setString(2, limb.getFileName());
            // if statement for CLI, would have been single line if upload file functionality was used in front-end
            if (limb.getFile() != null) {
                stmt.setBlob(3, FileUtil.multiPartFileToBlob(limb.getFile()));
            } else {
                stmt.setNull(3, Types.BLOB);
            }
            stmt.setString(4, limb.getSkinColor().toString());
            stmt.setBoolean(5, limb.isHasTattoos());
            stmt.setBoolean(6, limb.isLeft());
            stmt.setBoolean(7, limb.isArm());

            stmt.registerOutParameter(8, Types.VARCHAR);

            stmt.execute();

            String errorMessage = stmt.getString(8);

            if (errorMessage == null) {
                return "Successfully added Limb data";
            } else {
                throw new RuntimeException(errorMessage);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error thrown while inserting limb data", e);
        }
    }
}
