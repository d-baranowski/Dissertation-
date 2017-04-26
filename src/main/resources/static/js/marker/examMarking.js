var sectionQuestionToTableMap = {};
$(document).ready(function(){
    attachSubmitOnChange();
    var filterByOption = function( settings, data, dataIndex ) {
        var table =  settings.nTable;

        var filterSetting = table.optionsForm ? table.optionsForm()[0]['filter'].value : 'all';

        if (filterSetting == 'all') {
            return true;
        } else if (filterSetting == 'me') {
            var name = whoAmI();
            return name == data[4]; //Marked by value
        } else if (filterSetting == 'auto') {
            return data[4] == 'Auto-Marker'
        } else if (filterSetting == 'other') {
            var name = whoAmI();
            return data[4] != 'Auto-Marker' && data[4] != name && data[4] != 'Not Marked';
        } else if (filterSetting == 'not') {
            return data[4] == 'Not Marked'
        }
    };

    $.fn.dataTable.ext.search.push(
        filterByOption
    );

    $('.js-data-table').each(function () {
        table = this;
        var sectionNo = $(table).data('sectionNo');
        var questionsNo = $(table).data('questionNo');

        var dataTable = $(table).DataTable({
            "columnDefs": [
                {"orderable": false, "targets": 5},
                {"searchable": false, "targets": 5}
            ],
            "dom": '<"toolbar-for-section-'+sectionNo+'-question-'+questionsNo+'"> lfrtip'
        });
        var groupName = 'filter';
        var toolbar = $('div.toolbar-for-section-'+sectionNo+'-question-'+questionsNo+'');

        toolbar.html('Show ' +
            '<form>' +
            '<label class="radio-inline"><input value="all" type="radio" checked="checked" name="'+groupName+'">All</label>' +
            '<label class="radio-inline"><input value="me" type="radio" name="'+groupName+'">Marked By Me</label>' +
            '<label class="radio-inline"><input value="auto" type="radio" name="'+groupName+'"">Marked Automatically</label>' +
            '<label class="radio-inline"><input value="other" type="radio" name="'+groupName+'"">Marked By Someone Else</label>' +
            '<label class="radio-inline"><input value="not" type="radio" name="'+groupName+'"">Not Marked</label>' +
            '</form>'
        );


        var optionsForm = function() {return toolbar.find('form')};
        table.optionsForm = optionsForm;

        $(optionsForm).change(function () {
            dataTable.draw();
        });

        if (sectionQuestionToTableMap[sectionNo] == undefined) {
            sectionQuestionToTableMap[sectionNo] = {}
        }
        sectionQuestionToTableMap[sectionNo][questionsNo] = dataTable;
    });
});

//Overrides markerPaper.js implementation
function notifyAccordionOfMarkSubmission(slideIndex) {
    var actualSlideIndex = parseInt(slideIndex);
    var elementWithData = $("a[slickSlide=" + actualSlideIndex + "]");

    var questionNo = elementWithData.data('questionNo');
    var sectionNo = elementWithData.parents('.panel-default').data('sectionNo');

    var marks = $('select[data-section-no='+ sectionNo +'][data-question-no='+questionNo+']');

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
    $(chart).peity('pie', {'fill': ["#0069A0", "#5CB85C", "#F0AD4E"],'radius': 18, 'innerRadius': 8});
}

function attachSubmitOnChange() {
    $('.js-submit-on-change').change(function() {
        var form = $('#' + $(this).attr('form'));
        var row = $($(this).closest('tr')[0]);
        var table = $(row.closest('table.js-data-table')).DataTable();
        form.submit();
        table.rows(row).invalidate().draw();
    });
}

//Overrides markerPaper.js implementation
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

//Overrides markerPaper.js implementation
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