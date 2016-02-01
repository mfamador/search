function swapOptions(obj,i,j) {
    var o = obj.options;
    var i_selected = o[i].selected;
    var j_selected = o[j].selected;
    var temp = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
    var temp2= new Option(o[j].text, o[j].value, o[j].defaultSelected, o[j].selected);
    o[i] = temp2;o[j] = temp;
    o[i].selected = j_selected;o[j].selected = i_selected;
}

function moveOptionDown(obj) {
    for(i=obj.options.length-1;i>=0;i--) {
        if(obj.options[i].selected) {
            if(i !=(obj.options.length-1) && ! obj.options[i+1].selected) {
                swapOptions(obj,i,i+1);
                obj.options[i+1].selected = true;
            }
        }
    }
}

function moveOptionUp(obj) {
    for(i=0;i<obj.options.length;i++){
        if(obj.options[i].selected){
            if(i != 0 && !obj.options[i-1].selected){
                swapOptions(obj,i,i-1);
                obj.options[i-1].selected = true;
            }
        }
    }
}

function unselectAllOptions(source) {
    sourcelen = source.length ;
    for (i=(sourcelen-1); i>=0; i--) {
        source.options[i].selected = false;
    }
}

function selectAllOptions(source) {
    sourcelen = source.length ;
    for (i=(sourcelen-1); i>=0; i--) {
        source.options[i].selected = true;
    }
}

function move(source, target) {
    sourceLen = source.length ;
    for (i=0; i<sourceLen; i++){
        if (source.options[i].selected == true) {
            target.options[target.length]= new Option(source.options[i].text, source.options[i].value);
        }
    }
    remove(source);
}

function add(source, target) {
    sourceLen = source.length ;
    targetLen = target.length ;
    for (i=0; i<sourceLen; i++){
        if (source.options[i].selected == true) {
            for (j=0; j<targetLen; j++){
                if (source.options[i].value == target.options[j].value) {
                    break;
                }
            }
            if (j == targetLen) {
                target.options[target.length]= new Option(source.options[i].text, source.options[i].value);
            }
            source.options[i].selected = false;
        }
    }
}

function remove(source) {
    sourceLen = source.length ;
    for (i=(sourceLen-1); i>=0; i--){
        if (source.options[i].selected == true ) {
            source.options[i] = null;
        }
    }
}

function getElementByName(name) {
  return document.getElementsByName(name)[0];
}

function selectAllResultParams() {
  selectAllOptions(getElementByName('resultParams'));
  selectAllOptions(getElementByName('resultOrder'));
  selectAllOptions(getElementByName('selectedResultParams'));
  return true;
}
