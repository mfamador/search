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
package pt.maisis.search.dao.jpa;

import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.dao.ReportDAO;
import pt.maisis.search.entity.Report;
import pt.maisis.search.entity.ReportEntity;

/**
 *
 * @author amador
 */
public class JpaReportDAO extends JpaBaseDAO<Report, Long> implements ReportDAO {

    private static Log log = LogFactory.getLog(JpaReportDAO.class);

    public JpaReportDAO() {
    }

    @Override
    public Class<Report> getEntityClass() {
        return Report.class;
    }

    public List<Report> findByReportName(String reportName) {
        Query queryReportByReportName = getEntityManager().createNamedQuery("Report.findByReportName");
        
        queryReportByReportName.setParameter("reportName", reportName);
        
        return queryReportByReportName.getResultList();
    }

    public int count(ReportEntity entity, Map<String, Object> criteria) {
        StringBuilder sb = new StringBuilder().append("SELECT count(r) FROM Report r, ReportEntity re WHERE r.id = re.reportEntityPK.report.id ");
        
        if (entity.getEntityId() != null) {
            sb.append(" and entityId = :entityId");
        }
        
        if (entity.getEntityTypeId() != null) {
            sb.append(" and entityTypeId = :entityTypeId");
        }

        if (criteria != null && !criteria.isEmpty()) {
            int i = 0;
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                sb.append(" AND ");
                sb.append(entry.getKey()).append(" = :").append(entry.getKey());
                i++;
            }
        }
        
        log.debug("query = " + sb.toString());

        Query query = getEntityManager().createQuery(sb.toString());

        if (entity.getEntityId() != null) {
            query.setParameter("entityId", entity.getEntityId());
        }
        
        if (entity.getEntityTypeId() != null) {
            query.setParameter("entityTypeId", entity.getEntityTypeId());
        }

        if (criteria != null && !criteria.isEmpty()) {
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return ( (Long) query.getSingleResult() ).intValue();
    }

    public List<Report> find(ReportEntity entity, Integer start, Integer count, Map<String, Object> criteria, Map<String, String> orderCriteria) {
        StringBuilder sb = new StringBuilder().append("SELECT r FROM Report r, ReportEntity re WHERE r.id = re.reportEntityPK.report.id ");
        
        if (entity.getEntityId() != null) {
            sb.append(" and entityId = :entityId");
        }
        
        if (entity.getEntityTypeId() != null) {
            sb.append(" and entityTypeId = :entityTypeId");
        }
        
        if (criteria != null && !criteria.isEmpty()) {
            int i = 0;
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                sb.append(" AND ");
                sb.append(entry.getKey()).append(" = :").append(entry.getKey());
                i++;
            }
        }

        if (orderCriteria != null && !orderCriteria.isEmpty()) {
            for (Map.Entry<String, String> entry : orderCriteria.entrySet()) {
                sb.append(" ORDER BY ").append(entry.getKey()).append(" ").append(entry.getValue());
            }
        }

        log.debug("query = " + sb.toString());

        Query query = getEntityManager().createQuery(sb.toString());

        if (entity.getEntityId() != null) {
            query.setParameter("entityId", entity.getEntityId());
        }
        
        if (entity.getEntityTypeId() != null) {
            query.setParameter("entityTypeId", entity.getEntityTypeId());
        }
        
        if (criteria != null && !criteria.isEmpty()) {
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if (start != null) {
            if (start < 0) {
                start = 0;
            }
            query.setFirstResult(start.intValue());
        }

        if (count != null) {
            query.setMaxResults(count.intValue());
        }
        return query.getResultList();
    }

    public List<Report> find(ReportEntity entity, Map criteria, Map orderCriteria) {
        return find(entity, null, null, criteria, orderCriteria);
    }
}
