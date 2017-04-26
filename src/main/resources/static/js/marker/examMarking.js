$(document).ready(function(){
    attachSubmitOnChange();
});

function notifyAccordionOfMarkSubmission(slideIndex) {
    var actualSlideIndex = parseInt(slideIndex);
    var elementWithData = $("a[slickSlide=" + actualSlideIndex + "]");
    var accordionElement = $(elementWithData).find(".panel-body")[0];

    var questionNo = elementWithData.data('questionNo');
    var sectionNo = elementWithData.parents('.panel-default').data('sectionNo');

    var marks = $('select[data-section-no='+ sectionNo +'][data-question-no='+questionNo+']');
    //$(accordionElement).addClass("accordion-question-submitted");

    var humanMarkedCount = 0;
    var autoMarkedCount = 0;
    $(marks).each(function () {
        var mark = this;
        var attemptId = $(mark).data('attemptId');
        if ($(mark).find(':selected').text() != 'Not Marked') {
            var markedBy = $('.js-marked-by[data-attempt-id='+ attemptId +'][data-section-no='+ sectionNo +'][data-question-no='+questionNo+']').text();
            if (markedBy == 'Auto-Marker ') {
                autoMarkedCount++
            } else {
                humanMarkedCount++
            }
        }

    });
    var notMarkedCount = marks.length - humanMarkedCount - autoMarkedCount;
    var chart = $('#peity-for-section-'+sectionNo+'-question-' + questionNo);
    $(chart).text(autoMarkedCount + ',' + humanMarkedCount + ',' + notMarkedCount);
    $(chart).peity('pie', {'fill': ["#0069A0", "#5CB85C", "#F0AD4E"],'radius': 18});
}

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
                buildDangerAlert(request.responseJSON.message)
            }
        })
    });
}