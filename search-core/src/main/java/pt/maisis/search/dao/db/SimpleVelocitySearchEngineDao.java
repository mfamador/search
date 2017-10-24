package pt.maisis.search.dao.db;

import pt.maisis.search.SearchQuery;
import pt.maisis.search.dao.SearchDaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import pt.maisis.search.dao.SearchEngineDao;

/**
 * Implementacão de {@link SearchEngineDao} que permite efectuar
 * pesquisas a uma base de dados, tendo em conta templates SQL
 * definidas nos ficheiros de configuracão das pesquisas.<p/>
 *
 * Esta implementacão é igual à implementacão 
 * {@link VelocitySearchEngineDao}. No entanto, ...
 *
 * @todo pensar melhor nisto!!!
 *
 * @version 1.0
 */
public class SimpleVelocitySearchEngineDao extends VelocitySearchEngineDao {

    @Override
    protected void setValues(final SearchQuery query,
            final PreparedStatement ps)
            throws SQLException, SearchDaoException {
        // nada a fazer
    }
}
