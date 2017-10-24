package pt.maisis.search.service;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.dao.DAOFactory;
import pt.maisis.search.dao.ReportDAO;
import pt.maisis.search.entity.Report;
import pt.maisis.search.entity.ReportEntity;

/**
 *
 * @author amador
 */
public final class ReportService {

    private static Log log = LogFactory.getLog(ReportService.class);
    private ReportDAO dao;
    DAOFactory factory = DAOFactory.getInstance();

    public ReportService() {
        dao = factory.getDao(ReportDAO.class);
    }

    public List<Report> find() {
        return dao.find();
    }

    public int count() {
        return dao.count();
    }

    public int count(Map criteria) {
        return dao.count(criteria);
    }

    public int count(ReportEntity entity, Map criteria) {
        return dao.count(entity, criteria);
    }

    public List<Report> find(Integer start, Integer count) {
        return dao.find(start, count);
    }

    public List<Report> find(Map criteria) {
        return dao.find(criteria);
    }

    public Report find(Long id) {
        return dao.find(id);
    }

    public List<Report> find(Integer start, Integer count, Map criteria, Map orderCriteria) {
        return dao.find(start, count, criteria, orderCriteria);
    }

    public List<Report> find(Map criteria, Map orderCriteria) {
        return dao.find(criteria, orderCriteria);
    }

    public List<Report> find(ReportEntity entity, Integer start, Integer count, Map criteria, Map orderCriteria) {
        return dao.find(entity, start, count, criteria, orderCriteria);
    }

    public List<Report> find(ReportEntity entity, Map criteria, Map orderCriteria) {
        return dao.find(entity, criteria, orderCriteria);
    }

    public List<Report> findByReportName(String reportName) {
        return dao.findByReportName(reportName);
    }
}
