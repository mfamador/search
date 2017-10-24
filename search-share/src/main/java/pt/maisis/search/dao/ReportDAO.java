package pt.maisis.search.dao;

/**
 *
 * @author amador
 */
import java.util.List;
import java.util.Map;
import pt.maisis.search.entity.Report;
import pt.maisis.search.entity.ReportEntity;

public interface ReportDAO extends CrudDAO<Report, Long>{

    List<Report> findByReportName(String reportName);

    List<Report> find(ReportEntity entity, Integer start, Integer count, Map<String, Object> criteria, Map<String, String> orderCriteria);

    List<Report> find(ReportEntity entity, Map<String, Object> criteria, Map<String, String> orderCriteria);

    int count(ReportEntity entity, Map<String, Object> criteria);
}