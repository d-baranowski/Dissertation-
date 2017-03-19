$(document).ready(function () {
    var csrf_token = $('#csrf-token').attr('content');
    var header = "X-CSRF-TOKEN";

    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, csrf_token);
    });

    $(window).load(function() {
      $('#datepicker').datepicker({altFormat: "dd/mm/yy", dateFormat: "dd/mm/yy"});
    });
});

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};

function showLoading() {
    $('#loading').show();
}

function hideLoading() {
    $('#loading').hide();
}

function updateQueryStringParameter(uri, key, value) {
    var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (uri.match(re)) {
        return uri.replace(re, '$1' + key + "=" + value + '$2');
    }
    else {
        return uri + separator + key + "=" + value;
    }
}