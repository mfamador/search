<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ taglib uri="http://search.maisis.pt/taglib/search" prefix="search" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--------------------------->
< paginacao                  >
<---------------------------%>

<search:pageableResult search="${param.search}">
  ${begin}-${end} de ${total}, Páginas:
  <search:previousPage>
    <a href="${url}">&lt;</a>
  </search:previousPage>
  
  <select onchange="location.href=this[this.selectedIndex].value">
    <search:pages>
      <c:choose>
        <c:when test="${selected}">
          <option selected>${page}</option>
        </c:when>
        <c:otherwise>
          <option value="${url}">${page}</option>
        </c:otherwise>
      </c:choose>
    </search:pages>
  </select>
  
  <search:nextPage>
    <a href="${url}">&gt;</a>
  </search:nextPage>
</search:pageableResult>

<%--------------------------->
< resultados                 >
<---------------------------%>

<search:result search="${param.search}">    
  <table class="table-result">

    <search:resultHeader>
      <tr>
        <search:resultHeaderColumn>
          <th align="center" rowspan="${rowspan}" colspan="${colspan}">
            <c:choose>
              <c:when test="${ordered}">
                <c:if test="${orderType == 0}">
                  <img src="images/order_asc.gif"/>
                  <a href="search?reset=false&search=utilizador&start=0&resultOrder=${resultParam.name}&orderType=1">
                    <b>${resultParam.resultLabel}</b></a>
                </c:if>
         
                <c:if test="${orderType == 1}">
                  <img src="images/order_desc.gif"/>
                  <a href="search?reset=false&search=utilizador&start=0&resultOrder=${resultParam.name}&orderType=0">
                    <b>${resultParam.resultLabel}</b></a>
                </c:if>
              </c:when>
              <c:otherwise>
                <a href="search?reset=false&search=utilizador&start=0&resultOrder=${resultParam.name}&orderType=0">
                  <b>${resultParam.resultLabel}</b>
                </a>
              </c:otherwise>
            </c:choose>
          </th>
        </search:resultHeaderColumn>
      </tr>
    </search:resultHeader>
    
    <search:resultBody>
      <tr>
        <search:resultBodyColumn>
          <td align="${align}">${value}</td>
        </search:resultBodyColumn>
      </tr>
    </search:resultBody>

  </table>
</search:result>
