$(document).ready(function(){
    //Mock ajax function
    $.ajax = function (param) {
        _mockAjaxOptions = param;
        //call success handler
        //_mockAjaxOptions.success("ok", "textStatus", "jqXHR");
    };

    $('body').submit(function(e) {e.preventDefault()})
});
