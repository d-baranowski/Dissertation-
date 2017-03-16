$(document).ready(function () {
    handleMarkSubmissions();
    loadAccordionSubmittedMarks();
    handleFinishMarking();
    handleUserLeavingPage();
    var socket = new SockJS('/marking-sync-websocket');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/marking/mark-updated', function (mark) {
            updateMark(JSON.parse(mark.body))
        });
    });
});

function updateMark(mark) {
    var formId = 'form-for-attempt-' + mark.testAttemptId + '-question-' + mark.questionId + '-version-' + mark.questionVersionNo;

    $('select[form=' + formId + ']').val(mark.mark.mark);
    $('textarea[form=' + formId + ']').val(mark.mark.comment);
}

function handleMarkSubmissions() {
    $('form:not(.specialHandler)').submit(function (submit) {
        submit.preventDefault();
        var formData = $(this).serialize();
        var form = $(this);

        $.ajax({
            type: "POST",
            url: $(this).attr('action'),
            data: formData,
            success: function (data) {
                if (data == "ok") {
                    var parent = $(form).closest(".question");
                    notifyAccordionOfMarkSubmission($(parent).attr("data-slick-index"));
                } else {
                    buildSubmissionFailedAlert(data);
                }
            },
            error: function (request, status, error) {
                buildDangerAlert(error)
            }
        })
    });
}

function notifyAccordionOfMarkSubmission(slideIndex) {
    var actualSlideIndex = parseInt(slideIndex);
    var accordionElement = $("a[slickSlide=" + actualSlideIndex + "]").find(".panel-body")[0];
    $(accordionElement).addClass("accordion-question-submitted");
}

function loadAccordionSubmittedMarks() {
    var submittedForms = $("form[isMarked=true]");

    $(submittedForms).each(function () {
        notifyAccordionOfMarkSubmission($(this).closest(".question").attr("data-slick-index"));
    });
}

function handleFinishMarking() {
    var attemptId = getAttemptId();
    var url = ENDPOINTS.ATTEMPT_PREFIX + (ENDPOINTS.ATTEMPT_FINISH_MARKING.replace("{testAttemptId}", attemptId));
    $("#submitAllBtn").click(function () {
        $.ajax({
            type: "POST",
            url: url,
            beforeSend: function (request) {
                request.setRequestHeader("X-CSRF-TOKEN", getCsrfTokenValue());
            },
            success: function () {
                window.location.replace(ENDPOINTS.DASHBOARD_PREFIX + ENDPOINTS.DASHBOARD_VIEW_TESTS);
            },
            error: function (request, status, error) {
                buildErrorDialog(request.responseJSON.message)
            }
        })
    });
}

function postUnlockMarking(attemptId) {
    var url = ENDPOINTS.ATTEMPT_PREFIX + (ENDPOINTS.ATTEMPT_UNLOCK_MARKING.replace("{testAttemptId}", attemptId));
    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (request) {
            request.setRequestHeader("X-CSRF-TOKEN", getCsrfTokenValue());
        },
        success: function () {
            console.log("Successfully unlocked attempt " + attemptId + " from marking.");
            window.location.href = ENDPOINTS.DASHBOARD_PREFIX + ENDPOINTS.DASHBOARD_VIEW_TESTS;
        },
        error: function (request, status, error) {
            buildDangerAlert("Something went wrong: " + error);
        }
    });
}

function handleUserLeavingPage() {
    $('#unlockBtn').click(function () {
        var attemptId = getAttemptId();
        postUnlockMarking(attemptId);
    });
}

function getAttemptId() {
    var attemptId = $("[name='testAttemptId']:first").first().attr('value');

    if (attemptId) {
        return attemptId;
    } else {
        console.error("Could not find attempt id on the page. Please inform system administrator");
        buildWarmingAlert("Could not find attempt id on the page. Please inform system administrator");
    }
}