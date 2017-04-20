/**
Generic javascript functions common to all files.
**/

function getNotyTemplate(icon, title) {
    return '<div class="noty_message">' +
    '<h4>'+title+' <span class="glyphicon glyphicon-'+icon+'"></span></h4>' +
    '<span class="noty_text"></span><div class="noty_close">' +
    '</div>' +
    '</div>'
}

function buildSuccessAlert(msg) {
    var myTemplate = getNotyTemplate('ok', 'Success');
    if (msg) {
        noty({text: msg, type: 'success', template:myTemplate});
    } else {
        console.log('Please provide a message to display alert');
    }
}

function buildWarningAlert(msg) {
    var myTemplate = getNotyTemplate('exclamation-sign', 'Warning');
    if (msg) {
        noty({text: msg, type: 'warning', template:myTemplate });
    } else {
        console.log('Please provide a message to display alert');
    }
}

function buildDangerAlert(msg) {
    var myTemplate = getNotyTemplate('exclamation-sign', 'Danger');
    if (msg) {
        noty({text: msg, type: 'error', template: myTemplate});
    } else {
        noty({text: "Something went wrong. Can't get error message.", type: 'error', template: myTemplate});
    }
}


function getCsrfTokenValue() {
    var token = $('input#csrf-token').attr("content");
    if (token) {
        return token
    } else {
        var msg = "Could not retrieve csrf token.";
        console.error(msg);
        buildDangerAlert(msg)
    }

}