<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ taglib uri="http://search.maisis.pt/taglib/search" prefix="search" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/header.jsp"/>

<%--------------------------->
< erros ocorridos            >
<---------------------------%>

<search:searchErrorsAvailable>
  <search:searchErrors>
    - <b>${error}!</b><br/>
  </search:searchErrors>
  <br/>
</search:searchErrorsAvailable>

<form action="search" method="post">
  <input type="hidden" name="search" value="${param.search}"/> 

  <ul id="maintab" class="shadetabs">
    <li class="selected"><a href="#" rel="tcontent1">Critérios Pesquisa</a></li>
  </ul>

  <div class="tabcontentstyle">
    
    <%--------------------------->
    < search params              >
    <---------------------------%>
    
    <div id="tcontent1" class="tabcontent">
      <table>
        <search:searchMetaData search="${param.search}" numberColumns="4">
          <search:startRow>
            <tr>
          </search:startRow>

	  <search:startComposite>
	    <td><span style="font-size:11px;"><i>${label}</i></span><table cellpadding="0" cellspacing="0"><tr>
	  </search:startComposite>

          <search:container type="text">
            <td valign="bottom">
              <span class="srclabel">${label}:</span><br/>
              <input class="text" name="searchParam(${container.name})" maxlength="${container.inputSize}" value="${value}" style="width:${container.size}"/>
	    </td>
          </search:container>
          
          <search:container type="select">
            <td>
              <span class="srclabel">${label}:</span><br/>
              <select class="select" name="searchParam(${container.name})" style="width:${container.size}">
                <option></option>
                <c:forEach var="value" items="${values}">
                  <option value="${value.key}" ${value.selected ? 'selected' : ''}>${value.value}</option>
                </c:forEach>
              </select>
            </td>
          </search:container>
          
	  <search:endComposite>
    	    </tr></table></td>
	  </search:endComposite>

          <search:endRow>
            </tr>
          </search:endRow>
        </search:searchMetaData>
        
        <tr>
          <td colspan="8" align="right">
	    <input class="button" type="submit" value="pesquisar"/>
          </td>
        </tr>
      </form>
    </table>
  </div>
</div>


<%--------------------------->
< paginacao                  >
<---------------------------%>

<search:pageableResult search="${param.search}" numberVisiblePages="3">
  ${begin}-${end} de ${total}, Páginas:

  <search:previousGroup>
    [<a href="${url}">&lt;&lt;</a>]
  </search:previousGroup>

  <search:previousPage>
    [<a href="${url}">&lt;</a>]
  </search:previousPage>

  <search:pages>
    <c:choose>
      <c:when test="${selected}">
        <b>${page}</b>
      </c:when>
      <c:otherwise>
        <a href="${url}">${page}</a>
      </c:otherwise>
    </c:choose>
  </search:pages>
  
  <search:nextPage>
    [<a href="${url}">&gt;</a>]
  </search:nextPage>

  <search:nextGroup>
    [<a href="${url}">&gt;&gt;</a>]
  </search:nextGroup>

</search:pageableResult>

<%--------------------------->
< resultados                 >
<---------------------------%>

<search:result search="${param.search}">
  <table class="table-result">

    <search:resultHeader>
      <tr>
        <search:resultHeaderColumn>
          <th class="${resultParam.headerStyle}" align="center" rowspan="${rowspan}" colspan="${colspan}">
            ${label}
          </th>
        </search:resultHeaderColumn>
      </tr>
    </search:resultHeader>
    
    <search:resultBody>
      <tr>
        <search:resultBodyColumn>
          <td class="${resultParam.valueStyle}" align="${resultParam.align}">${value}</td>
        </search:resultBodyColumn>
      </tr>
    </search:resultBody>

  </table>

  <table class="table-export">
    <tr>
      <td align="center">
        Exportar:
        <a href="search?search=${param.search}&reset=false&start=0&format=htmlfragment&selectAllResults=true">html</a>
        <a href="search?search=${param.search}&reset=false&start=0&format=xls&selectAllResults=true">xls</a>
        <a href="search?search=${param.search}&reset=false&start=0&format=pdf&selectAllResults=true">pdf</a>
        <a href="search?search=${param.search}&reset=false&start=0&format=csv&selectAllResults=true">csv</a>
        <a href="search?search=${param.search}&reset=false&start=0&format=rtf&selectAllResults=true">rtf</a>
        <a href="search?search=${param.search}&reset=false&start=0&format=png&selectAllResults=true">png</a>
      </td>
    </tr>
  </table>
</search:result>

<script type="text/javascript">
initializetabcontent("maintab");
</script>
