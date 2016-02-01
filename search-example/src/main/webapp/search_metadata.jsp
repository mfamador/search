<search:searchMetaData search="${param.search}" numberColumns="4" startIndex="1" endIndex="2">
  <tr>

    <search:containers>
      <td>
        <search:startComposite>
          <div class="composite">
        </search:startComposite>

        <span class="srclabel">${container.label}:</span><br/>
        
        <search:container type="text">  
          <input class="text" 
                 name="searchParam(${container.name})" 
                 maxlength="${container.inputSize}" 
                 value="${value}" 
                 style="width:${container.size}"/>
        </search:container>

        <search:container type="select">
          <select class="select" name="searchParam(${container.name})" style="width:${container.size}">
            <option></option>
            <c:forEach var="value" items="${values}">
              <option value="${value.key}" ${value.selected ? 'selected' : ''}>${value.value}</option>
            </c:forEach>
          </select>
        </search:container>

        <search:endComposite>
          </div>
        </search:endComposite>
      </td>
    </search:containers>

  </tr>
</search:searchMetaData>
