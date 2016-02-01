<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ taglib uri="http://search.maisis.pt/taglib/search" prefix="search" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="css/tabcontent.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/tabcontent.js">
  /***********************************************
  * Tab Content script- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
  * This notice MUST stay intact for legal use
  * Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
  ***********************************************/
</script>


<%-- search ajax --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/search.js"></script>

<%-- ajaxtags js --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/scriptaculous/scriptaculous.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_crossframe.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_iframe.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_hide.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_shadow.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax/ajaxtags.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax/ajaxtags_controls.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax/ajaxtags_parser.js"></script>

<%@ taglib uri="http://search.maisis.pt/taglib/search" prefix="search" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ajaxtags.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />


<a href=""><b>+ Search</b></a>

<table class="table-header">
  <tr>
    <td align="right">
      \\ Exemplos: 
      <search:searches>
        <a class="option${searchMetaData.name == param.search ? '-selected' : ''}" 
           href="search?search=${searchMetaData.name}&prepare=true"><b>${searchMetaData.label}</b></a> 
      </search:searches>
    </td>
  </tr>
</table>

<p>
  <search:searches search="${param.search}">
    <b>${searchMetaData.label}</b>
    <br/>${searchMetaData.description}
  </search:searches>
</p>
