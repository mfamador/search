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