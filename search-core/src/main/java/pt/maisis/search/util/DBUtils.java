package pt.maisis.search.util;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe que disponibiliza alguns métodos úteis para operacões
 * JDBC.
 *
 * @version 1.0
 */
public final class DBUtils {

    private static Log log = LogFactory.getLog(DBUtils.class);

    public static PreparedStatement returnKeyPrepareStatement(Connection c,
            String stmt)
            throws SQLException {
        return c.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
    }

    public static PreparedStatement prepareStatement(Connection c,
            String stmt,
            boolean scrollable)
            throws SQLException {
        return (scrollable)
                ? c.prepareStatement(stmt,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)
                : prepareStatement(c, stmt);
    }

    public static PreparedStatement prepareStatement(Connection c,
            String stmt)
            throws SQLException {
        return c.prepareStatement(stmt,
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY);
    }

    public static void close(ResultSet rs) {
        close(rs, null, null);
    }

    public static void close(Connection c) {
        close(null, null, c);
    }

    public static void close(Statement s) {
        close(null, s, null);
    }

    public static void close(ResultSet rs, Statement s) {
        close(rs, s, null);
    }

    public static void close(Statement s, Connection c) {
        close(null, s, c);
    }

    public static void close(ResultSet rs, Statement s, Connection c) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error(e);
            }
        }
        if (s != null) {
            try {
                s.close();
            } catch (SQLException e) {
                log.error(e);
            }
        }
        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {
                log.error(e);
            }
        }
        return;
    }
}
