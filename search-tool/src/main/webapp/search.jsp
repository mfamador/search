<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="css/tabcontent.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="js/common.js"/></script>
<script type="text/javascript" src="js/tabcontent.js">
  /***********************************************
  * Tab Content script- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
  * This notice MUST stay intact for legal use
  * Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
  ***********************************************/
</script>

<style>
.title {
font-weight:normal;
letter-spacing:2;
}
</style>

<br/><a href=""><b>+ Search</b></a>

<p>
  <b><search:searchName search="${param.search}"/></b>
  <br/>Esta ferramenta tem como objectivo simplificar o processo de criação de novas pesquisas.
  <br/>Para maior flexibilidade o melhor é editar os descriptors XML à mão.
</p>

<ul id="maintab" class="shadetabs">
  <li class="selected"><a href="#" rel="tcontent1">Search Params</a></li>
  <li><a href="#" rel="tcontent2">Result Params</a></li>
</ul>

<form action="search" method="post">

  <div class="tabcontentstyle">
    
    <div id="tcontent1" class="tabcontent">

      <table align="center" cellpadding="4">
        <tr>
          <td>
            <table class="table-result">
              <tr>
                <th>Name</th>
                <th>Field</th>
                <th>Required</th>
                <th>Operator</th>
                <!--
                <th>Container</th>
                -->
              </tr>
              <tr>
                <td>
                  <input class="text" name="name" autocomplete="off" style="width:95"/>
                </td>
                <td>
                  <input class="text" name="field" autocomplete="off" style="width:95"/>
                </td>
                <td>
                  <select class="select" name="required" style="width:95">
                    <option>false</option>
                    <option>true</option>
                  </select>
                </td>
                <td>
                  <select class="select" name="operator" style="width:95">
                    <option>=</option>
                    <option>IN</option>
                    <option>LIKE</option>
                    <option>NOT IN</option>
                    <option>&gt;</option>
                    <option>&lt;</option>
                    <option>&gt;=</option>
                    <option>&lt;=</option>
                    <option>&lt;&gt;</option>
                  </select>
                </td>
                <!--
                <td>
                  <select class="select" name="container" style="width:95">
                    <option>simple</option>
                    <option>composite</option>
                  </select>
                </td>
                -->
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <div id="srclabel" style="height:30"><b>Container:</b> <input style="width:50;" class="button" type="button" value="new" onclick="insertRow()"/></div>
            <table class="table-result">
              <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Custom Type</th>
                <th>Size</th>
                <th>Input Size</th>
                <th></th>
              </tr>
              <tr>
                <td>
                  <input class="text" name="name" autocomplete="off" style="width:95"/>
                </td>
                <td>
                  <select class="select" name="container" style="width:95">
                    <option>text</option>
                    <option>select</option>
                    <option>radio</option>
                    <option>custom type</option>
                  </select>
                </td>
                <td>
                  <input class="text" name="formatter" autocomplete="off" style="width:95"/>
                </td>
                <td>
                  <input class="text" name="size" autocomplete="off" style="width:95"/>
                </td>
                <td>
                  <input class="text" name="inputSize" autocomplete="off" style="width:95"/>
                </td>
                <td>
                  <input class="button" style="width:60" type="button" value="remove" onclick="deleteRow(this)"/>
                </td>
              </tr>
              <tr>
                <td><center>Fmt Class</center></td>
                <td><center>Custom Class</center></td>
                <td><center>Fmt Pattern</center></td>
              </tr>
              <tr>
                <td>
                  <input class="text" name="formatter" autocomplete="off" style="width:95"/>
                </td>
                <td>
                  <input class="text" name="size" autocomplete="off" style="width:95"/>
                </td>
                <td>
                  <input class="text" name="size" autocomplete="off" style="width:95"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <div id="srclabel" style="height:30"><b>Custom Properties:</b> <input style="width:50;" class="button" type="button" value="new" onclick="insertRow()"/></div>
            <table id="customProperties" class="table-result">
              <tr>
                <th>
                  <div id="srclabel"><b>Name</b></div>
                </th>
                <th>
                  <div id="srclabel"><b>Value</b></div>
                </th>
                <th>
                </th>
              </tr>
              <tr>
                <td>
                  <input class="text" name="formatter" style="width:95"/>
                </td>
                <td>
                  <input class="text" name="formatter" style="width:95"/>
                </td>
                <td>
                  <input class="button" type="button" style="width:60" value="remove" onclick="deleteRow(this)"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan="4" align="right">
            <input class="button" type="submit" value="add"/>
          </td>
        </tr>
      </table>
    </div>

    <div id="tcontent2" class="tabcontent">
    </div>

  </div>
</form>

<script>
function deleteRow(r) {
  var i=r.parentNode.parentNode.rowIndex;
  document.getElementById('customProperties').deleteRow(i)
}

function insertRow() {
  var table=document.getElementById('customProperties').insertRow(1);
  var a=table.insertCell(0);
  var b=table.insertCell(1);
  var c=table.insertCell(2);
  a.innerHTML="<input class=\"text\" name=\"formatter\" style=\"width:95\"/>"
  b.innerHTML="<input class=\"text\" name=\"formatter\" style=\"width:95\"/>"
  c.innerHTML="<input class=\"button\" style=\"width:60\" type=\"button\" value=\"remove\" onclick=\"deleteRow(this)\"/>"
}
</script>

<script type="text/javascript">
initializetabcontent("maintab");
</script>
