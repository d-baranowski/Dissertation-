/**
Generic javascript functions common to all files.
**/

function buildWarningDialog(message){
    $("#messageBanner").html('<div class="alert alert-warning"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Error:</strong> ' + message + '</div>');
    $(".carousell-content").animate({ scrollTop: 0 }, "slow");
}

function buildInfoDialog(message){
    $("#messageBanner").html('<div class="alert alert-info"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Info:</strong> ' + message + '</div>');
    $(".carousell-content").animate({ scrollTop: 0 }, "slow");
}

function buildErrorDialog(message){
    $("#messageBanner").html('<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Warning:</strong> ' + message + '</div>');
    $(".carousell-content").animate({ scrollTop: 0 }, "slow");
}

function buildSuccessDialog(message){
    $("#messageBanner").html('<div class="alert alert-success"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success:</strong> ' + message + '</div>');
    $(".carousell-content").animate({ scrollTop: 0 }, "slow");
}


function buildSuccessAlert(msg) {
    if (msg) {
        cleanPreviousClasses();
        $('#alert').addClass('alert-success');
        $('#alert p').html('<strong>Success!</strong> ' + msg);
        showAlert(2000);
    } else {
        console.log('Please provide a message to display alert');
    }
}

function buildInfoAlert(msg) {
    if (msg) {
        cleanPreviousClasses();
        $('#alert').addClass('alert-info');
        $('#alert p').html('<strong>Info!</strong> ' + msg);
        showAlert(3000);
    } else {
        console.log('Please provide a message to display alert');
    }
}

function buildWarningAlert(msg) {
    if (msg) {
        cleanPreviousClasses();
        $('#alert').addClass('alert-warning');
        $('#alert p').html('<strong>Warning!</strong> ' + msg);
        showAlert(5000);
    } else {
        console.log('Please provide a message to display alert');
    }
}

function buildDangerAlert(msg) {
    if (msg) {
        cleanPreviousClasses();
        $('#alert').addClass('alert-danger');
        $('#alert p').html('<strong>Danger!</strong> ' + msg);
        showAlert();
    } else {
        console.log('Please provide a message to display alert');
    }
}

function cleanPreviousClasses() {
    $('#alert').removeClass('alert-success');
    $('#alert').removeClass('alert-info');
    $('#alert').removeClass('alert-warning');
    $('#alert').removeClass('alert-danger');
}

function showAlert(hideAfter) {
    $('#alert').fadeIn().show("puff", 100);
    $('#alert button').click(function() {
        $('#alert').fadeOut();
    });

    if (hideAfter) {
        setTimeout(function() {
            $('#alert').fadeOut();
        }, hideAfter);
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