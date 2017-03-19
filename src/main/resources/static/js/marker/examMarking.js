$(document).ready(function(){
    attachSubmitOnChange();
});

function attachSubmitOnChange() {
    $('.js-submit-on-change').change(function() {
        var form = $('#' + $(this).attr('form'));
        form.submit();
    });
}

function handleUserLeavingPage() {
    $('#unlockBtn').click(function() {
        var examId = $(this).data('examId');
        postUnlockMarking(examId);
    });
}

function postUnlockMarking(examId) {
    var url = ENDPOINTS.EXAM_PREFIX + (ENDPOINTS.EXAM_UNLOCK_MARKING.replace("{examId}", examId));
    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (request) {
            request.setRequestHeader("X-CSRF-TOKEN", getCsrfTokenValue());
        },
        success: function() {
            console.log("Successfully unlocked exam " + examId + " from marking.");
            window.location.href = ENDPOINTS.DASHBOARD_PREFIX + ENDPOINTS.DASHBOARD_VIEW_TESTS;
        },
        error: function (request, status, error) {
            buildDangerAlert("Something went wrong: " + error);
        }
    });
}

function handleFinishMarking() {
    $("#submitAllBtn").click(function() {
        var examId = $(this).data('examId');
        var url = ENDPOINTS.EXAM_PREFIX + (ENDPOINTS.EXAM_FINNISH_MARKING.replace("{examId}", examId));
        $.ajax({
            type: "POST",
            url: url,
            beforeSend: function (request) {
                request.setRequestHeader("X-CSRF-TOKEN", getCsrfTokenValue());
            },
            success: function() {
                window.location.replace(ENDPOINTS.DASHBOARD_PREFIX + ENDPOINTS.DASHBOARD_GENERATE_TESTS);
            },
            error: function (request, status, error) {
                buildErrorDialog(request.responseJSON.message)
            }
        })
    });
}