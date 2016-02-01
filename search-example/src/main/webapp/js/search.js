
var ajaxSearchParams = new Array();

var target;
var targetValue;
function ajaxSearchStart(params, ptarget, ptargetValue) {
    var page = "ajaxSearch";
    target = ptarget;
    targetValue = ptargetValue;

    new Ajax.Request(page,
                     {
                         method: 'get',
                             parameters: params,
                             onComplete: replySearchStart                                                   
                             });
}
                  
function replySearchStart (request) {
    xml=request.responseXML;
    var root = xml.documentElement;
    var responseNodes = root.getElementsByTagName("response");
    if (responseNodes.length > 0) {
        target.length=0;
        var responseNode = responseNodes[0];
        var itemNodes = responseNode.getElementsByTagName("item");
        for (var i=0; i< itemNodes.length; i++) {
            var nameNodes = itemNodes[i].getElementsByTagName("name");
            var valueNodes = itemNodes[i].getElementsByTagName("value");
            var name = "";
            if (nameNodes[0].firstChild != null) {
                name = nameNodes[0].firstChild.nodeValue;
            } 
            var value = "";
            if (nameNodes[0].firstChild != null) {
                value = valueNodes[0].firstChild.nodeValue;
            } 
            target.options[i] = new Option(name, value);
            if(value == targetValue) {
                target.options[i].selected = true;
            }
        }          
    }                   
}

function prepareSearchParams() {
    var event = this;
    var params = event.parameters;
    var allParams = params.substring(params.indexOf('params'), params.length);
    var arrayParams = splitParams(allParams);
    var i = 0;
    while (i != arrayParams.length) {
        //Teste se o param pai é uma multiple_select
        if (arrayParams[i].indexOf('_multipleResult_hidden') != -1) {
            var multipleResultHidden = document.getElementById(arrayParams[i]);
            var multipleSelect = document.getElementById(arrayParams[i].substring(0, arrayParams[i].indexOf('_multipleResult_hidden')));
            var value = '';
            for (var j=0;j<multipleSelect.options.length;j++) {
                if(multipleSelect.options[j].selected) {
                    value = value + ',' + multipleSelect.options[j].value;
                }
            }
            value = value.substring(1, value.length);
            multipleResultHidden.value = value;
        }
        i++;
    }
}
 
function splitParams(allParams) {
    var params = allParams.split(',');
    var arrayParams = new Array();
    for (var i=0;i<params.length;i++) {
        var nameParam = params[i].substring(params[i].indexOf('{') + 1, params[i].length - 1);
        arrayParams[i] = nameParam;
    }
    return arrayParams;
}