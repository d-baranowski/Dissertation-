$(document).ready(function () {
    bindLinksToMethods();
    updateTimeAllowed(getSelectedPaperTimeAllowed());

    $('#setupExam').submit(function (e) {
        $(form).parsley().validate();
        if ($('.parsley-error').length < 0) {
            e.preventDefault();
            return false;
        }
    });
});

function bindLinksToMethods() {
     $('#viewTestsTabs').find('a').click(function(){
            switch($(this).attr('data-bind')) {
                case 'ongoingTests':
                    getFragmentFromServer(ENDPOINTS.DASHBOARD_CURRENT_TESTS);
                    break;
                case 'markedTests':
                    getFragmentFromServer(ENDPOINTS.DASHBOARD_MARKED_TESTS);
                    break;
                case 'unmarkedTests':
                    getFragmentFromServer(ENDPOINTS.DASHBOARD_UNMARKED_TESTS);
                    break;
                case 'markingOngoing':
                    getFragmentFromServer(ENDPOINTS.DASHBOARD_MARKING_ONGOING, bindForceUnlockButtons);
                    break;
            }
        })
}

function simulateExam(element) {
    var examId = $(element).data('examId');
    $(element).html('<span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Loading...');
    $.ajax({
        url: "/simulate/exam/"+examId,
        success: function(data) {
            location.reload();
        },
        error: function(xhr, textStatus, errorThrown){
            buildWarningAlert("Failed to simulate exam");
        }
    });
}

function refreshElementOnScreen(data, callWhenDataOnScreen) {
    $("#testsTableContent").html("");
    $("#testsTableContent").html(data);
    if (callWhenDataOnScreen) {
        callWhenDataOnScreen();
    }
    $(".setupWrapper").hide().fadeIn();
}

function fetchFragmentFromServer(url, onSuccess) {
    $.ajax({
        url: url,
        dataType: "html",
        success: function(data) {
            onSuccess(data);
        },
        error: function(xhr, textStatus, errorThrown){
            console.error("Failed to fetch data from " + url + " Error: " + errorThrown);
            buildDangerAlert("Failed to fetch data from " + url + " Error: " + errorThrown);
            return undefined;
        }
    });
}

function getFragmentFromServer(endpoint, callWhenDataOnScreen) {
    fetchFragmentFromServer(ENDPOINTS.DASHBOARD_PREFIX + endpoint,
    function (data) {
        refreshElementOnScreen(data, callWhenDataOnScreen);
    });
}

function bindForceUnlockButtons() {
    var forceUnlockButtons = $('.force-unlock-btn');
    if (forceUnlockButtons) {
        $(forceUnlockButtons).each(function() {
            var row = $(this).parent().parent();
            var url = $(this).attr('data-endpoint');

            $(this).click(function(e) {
                e.preventDefault();
                $.ajax({
                    type: "POST",
                    url: url,
                    beforeSend: function (request) {
                        request.setRequestHeader("X-CSRF-TOKEN", getCsrfTokenValue());
                    },
                    success: function() {
                       buildSuccessAlert('Successfully unlocked attempt from marking.');
                       $(row).remove();
                    },
                    error: function(xhr, textStatus, errorThrown){
                       buildWarningAlert('Unable to unlock attempt from marking: ' + errorThrown);
                    }
                });
            });

        });
    } else {
        console.error("Something went wrong, couldn't find force unlock buttons")
        buildDangerAlert("Force unlock buttons failed to load.");
    }
}

function getSelectedPaperTimeAllowed() {
    return parseInt($("#pickPaperSelect :selected").attr('data-time-allowed'));
}

function updateTimeAllowed(newValue) {
    $("#timeAllowedSelect").val(newValue);
}