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
    <li><a href="#" rel="tcontent2">Campos Retorno</a></li>
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
            <td>
              <table cellspacing="2" cellpadding="0"><tr>
          </search:startComposite>
            
          <search:container type="text">
            <td>
              <span class="srclabel">${container.label}:</span><br/>
              <input class="text" name="searchParam(${container.name})" maxlength="${container.inputSize}" value="${value}" style="width:${container.size}"/>
            </td>
          </search:container>
            
          <search:container type="select">
            <td>
              <span class="srclabel">${container.label}:</span><br/>
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
          <td colspan="4" align="right">
            <input class="button" type="submit" value="pesquisar" onclick="selectAllResultParams()"/>
          </td>
        </tr>
      </table>
    </div>


    <%--------------------------->
    < result params              >
    <---------------------------%>

    <div id="tcontent2" class="tabcontent">
      <table>
        <tr>
          <td colspan="7" style="font-size:11px" height="40" valign="top">
            Seleccione os <b>campos de retorno</b> a retornar no resultado da pesquisa, bem como os <b>campos a ordenar</b>.
          </td>
        </tr>
        <tr>
          <td valign="top">
            <div class="srclabel"><b>Disponíveis:</b></div>
            <select class="multiselect" name="resultParams" multiple="true">
              <search:resultMetaData search="${param.search}">
                <option value="${resultParam.name}">${resultParam.searchLabel}</option>
              </search:resultMetaData>
            </select>
          </td>
          <td>
            <br/>
            <img class="arrow" src="images/arrow_right.gif" 
                 onClick="move(getElementByName('resultParams'),getElementByName('selectedResultParams'))"/>
            <br/>
            <img class="arrow" src="images/arrow_left.gif" 
                 onClick="move(getElementByName('selectedResultParams'),getElementByName('resultParams'))"/>
          </td>
          <td>
            <div class="srclabel"><b>Retornar:</b></div>
            <select class="multiselect" name="selectedResultParams" multiple="true">
              <search:selectedResultMetaData search="${param.search}">
                <option value="${selectedResultParam.name}">${selectedResultParam.searchLabel}</option>
              </search:selectedResultMetaData>
            </select>
          </td>
          <td>
            <br/>
            <img class="arrow" src="images/arrow_up.gif" 
                 onClick="moveOptionUp(getElementByName('selectedResultParams'))"/>
            <br/>
            <img class="arrow" src="images/arrow_down.gif" 
                 onClick="moveOptionDown(getElementByName('selectedResultParams'))"/>
          </td>
          <td>
            <br/>
            <img class="arrow" src="images/arrow_right.gif" 
                 onClick="add(getElementByName('selectedResultParams'),getElementByName('resultOrder'))"/>
            <br/>
            <img class="arrow" src="images/arrow_left.gif" 
                 onClick="remove(getElementByName('resultOrder'),getElementByName('selectedResultParams'))"/>
          </td>
          <td>
            <div class="srclabel"><b>Ordenar:</b></div>
            <select class="multiselect" name="resultOrder" multiple="true">
              <search:selectedResultMetaDataOrder search="${param.search}">
                <option value="${selectedResultParamOrder.name}">${selectedResultParamOrder.searchLabel}</option>
              </search:selectedResultMetaDataOrder>
            </select>
          </td>
          <td>
            <br/><img class="arrow" src="images/arrow_up.gif" onClick="moveOptionUp(getElementByName('resultOrder'))"/>
            <br/><img class="arrow" src="images/arrow_down.gif" onClick="moveOptionDown(getElementByName('resultOrder'))"/>
          </td>
        </tr>
        <tr>
          <td colspan="6" align="right">
            <input type="radio" name="orderType" value="0" checked><small>Asc</small></input>
            <input type="radio" name="orderType" value="1"><small>Desc</small></input>
          </td>
        </tr>
      </table>
    </form>
  </div>
</div>


<%--------------------------->
< paginacao                  >
<---------------------------%>

<search:pageableResult search="${param.search}">
  ${begin}-${end} de ${total}, Páginas:

  <search:previousPage>
    <a href="${url}&format=${param.format}">&lt;</a>
  </search:previousPage>

  <select onchange="location.href=this[this.selectedIndex].value">
    <search:pages>
      <c:choose>
        <c:when test="${selected}">
          <option selected="true">${page}</option>
        </c:when>
        <c:otherwise>
          <option value="${url}&format=${param.format}">${page}</option>
        </c:otherwise>
      </c:choose>
    </search:pages>
  </select>

  <search:nextPage>
    <a href="${url}&format=${param.format}">&gt;</a>
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
          <c:choose>
            <c:when test="${ordered}">
              <c:choose>
                <c:when test="${orderType == 0}">
                  <th align="center" rowspan="${rowspan}" colspan="${colspan}" 
                      onmouseover="this.style.backgroundColor='#dddddd'"
                      onmouseout="this.style.backgroundColor='#eeeeee'"
                      onclick="location.href='?search=${param.search}&reset=false&resultOrder=${resultParam.name}&orderType=1'">
                    ${resultParam.resultLabel} <img src="images/order_asc.gif"/>
                  </th>
                </c:when>
                <c:otherwise>
                  <th align="center" rowspan="${rowspan}" colspan="${colspan}" 
                      onmouseover="this.style.backgroundColor='#dddddd'"
                      onmouseout="this.style.backgroundColor='#eeeeee'"
                      onclick="location.href='?search=${param.search}&reset=false&resultOrder=${resultParam.name}&orderType=0'">
                    ${resultParam.resultLabel} <img src="images/order_desc.gif"/>
                  </th>
                </c:otherwise>
              </c:choose>
            </c:when>
            <c:otherwise>
              <th align="center" rowspan="${rowspan}" colspan="${colspan}" 
                  onmouseover="this.style.backgroundColor='#dddddd'"
                  onmouseout="this.style.backgroundColor='#eeeeee'"
                  onclick="location.href='?search=${param.search}&reset=false&resultOrder=${resultParam.name}&orderType=0'">
                ${resultParam.resultLabel} 
              </th>
            </c:otherwise>
          </c:choose>
        </search:resultHeaderColumn>
      </tr>
    </search:resultHeader>
    
    <search:resultBody>
      <tr>
        <search:resultBodyColumn>
          <td align="${resultParam.align}">${value}</td>
        </search:resultBodyColumn>
      </tr>
    </search:resultBody>

  </table>

  <table class="table-export">
    <tr>
      <td align="center">
        Exportar:
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
unselectAllOptions(getElementByName('selectedResultParams'));
unselectAllOptions(getElementByName('resultOrder'));
</script>
