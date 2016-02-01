/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Maisis
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Maisis.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
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
