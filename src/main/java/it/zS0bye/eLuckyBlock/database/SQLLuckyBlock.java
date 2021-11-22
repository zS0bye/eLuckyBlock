package it.zS0bye.eLuckyBlock.database;

import it.zS0bye.eLuckyBlock.eLuckyBlock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;

public class SQLLuckyBlock {

    private final SQLConnection sql;

    public SQLLuckyBlock(final eLuckyBlock plugin) {
        this.sql = plugin.getSqlConnection();
        this.create();
    }

    protected void create() {
        CompletableFuture.runAsync(() -> {
            if (!this.sql.hasConnection()) {
                return;
            }
            String sql = "CREATE TABLE IF NOT EXISTS luckyblock(id int AUTO_INCREMENT PRIMARY KEY, PlayerName varchar(30) NOT NULL, breaks int NOT NULL)";
            try {
                Statement st = this.sql.getConnection().createStatement();
                st.execute(sql);
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public CompletableFuture<Boolean> hasNotLuckyBreaks(final String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PreparedStatement pst = this.sql.getConnection().prepareStatement("SELECT breaks FROM luckyblock WHERE PlayerName = ?");
                pst.setString(1, name);
                ResultSet rs = pst.executeQuery();
                return !rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return true;
        });
    }

    public void setLuckyBreaks(final String name) {
        CompletableFuture.runAsync(() -> {
            try {
                PreparedStatement pst = this.sql.getConnection().prepareStatement("INSERT INTO luckyblock(PlayerName,breaks) VALUES(?,?)");
                pst.setString(1, name);
                pst.setInt(2, 0);
                pst.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void addLuckyBreaks(final String name, final int luckybreaks) {
        CompletableFuture.runAsync(() -> {
            try {
                PreparedStatement pst = this.sql.getConnection().prepareStatement("UPDATE luckyblock SET breaks = ? WHERE PlayerName = ?");
                pst.setInt(1, luckybreaks);
                pst.setString(2, name);
                pst.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


    }

    public CompletableFuture<Integer> getLuckyBreaks(final String name) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        hasNotLuckyBreaks(name).thenAccept(check -> {
            if(check) {
                setLuckyBreaks(name);
            }
            CompletableFuture.runAsync(() -> {
                try {
                    PreparedStatement pst = this.sql.getConnection().prepareStatement("SELECT breaks FROM luckyblock WHERE PlayerName = ?");
                    pst.setString(1, name);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        future.complete(rs.getInt("breaks"));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
    });
        return  future;
    }

}