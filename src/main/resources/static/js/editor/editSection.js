var sectionQuestionDataTable;
$(document).ready(function () {
    enableFroalaEditor();
    bindCreationForm();
    $(window).load(function () {
        var sectionQuestionsTable = $('#sectionQuestions');
        sectionQuestionDataTable = sectionQuestionsTable.DataTable(
            {
                "rowReorder": true,
                "columnDefs": [
                    {"orderable": false, "targets": 8},
                    {"orderable": false, "targets": 9},
                    {"searchable": false, "targets": 8},
                    {"searchable": false, "targets": 9}
                ]
            }
        );

        sectionQuestionDataTable.on('row-reorder',function(e, diff, edit) {
            var sectionId = $('#id').val();
            var sectionVer = $('#versionNumber').val();
            var updates = [];

            for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
                var rowData = sectionQuestionDataTable.row( diff[i].node ).data();
                updates.push({
                    "sectionId": sectionId,
                    "sectionVer": sectionVer,
                    "questionId": rowData[1],
                    "questionVerNo": rowData[2],
                    "newRef": diff[i].newData[0]
                })
            }

            var url = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_MOVE_QUESTION_IN_SECTION;

            var jsonData = JSON.stringify(updates);

            $.ajax({
                type: "POST",
                url: url,
                contentType: 'application/json',
                data: jsonData,
                error: function (data) {
                    buildWarningAlert('Failed to move question')
                }
            });
        });

        handleDeletingQuestionsFromSection(sectionQuestionDataTable);

        var versionNumber = $('#versionNumber').val();
        if (parseInt(versionNumber) > 0) {
            beginUpdating();
        }
        PR.prettyPrint();
        hideLoading();
    });
});

function getSectionQuestionFromRow(row, questionNumber) {
    var array = row.data();
    var result = [questionNumber];
    array.forEach(function (object) {
        result.push(object)
    });

    return "<tr>" +
        "<td class='move-me'>" + result[0] +
            "<span data-help='CREATE_SECTION_SECTION_QUESTION_ORDER' " +
            "class='glyphicon glyphicon-align-right glyphicon-resize-vertical'></span>" +
        "</td>" +
        "<td>" + result[1] + "</td>" +
        "<td>" + result[2] + "</td>" +
        "<td>" + result[3] + "</td>" +
        "<td>" + result[4] + "</td>" +
        "<td>" + result[5] + "</td>" +
        "<td>" + result[6] + "</td>" +
        "<td>" + result[7] + "</td>" +
        "<td>" + result[8] + "</td>" +
        "<td><a target='_blank' class='js-remove-question-handle' href='test'>Remove</a></td>" +
        "</tr>";
}

function getUpdatedQuestionsTable(sectionId, sectionVersion) {
    var url = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_GET_UPDATED_SECTION_QUESTIONS
            .replace('{sectionId}', sectionId).replace('{sectionVersion}', sectionVersion);

    $.ajax({
        type: "GET",
        url: url,
        success: function (data) {
            sectionQuestionDataTable.tables()
                .body()
                .to$()
                .html(data)
                .draw();
        },
        error: function (data) {
            buildWarningAlert('Failed to get new rows');
        }
    });
}


function handleDeletingQuestionsFromSection(table) {
    var url = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_REMOVE_QUESTION_FROM_SECTION;

    table.on('click', '.js-remove-question-handle', function (e) {
        current = $(this);

        e.preventDefault();

        var row = table.row(this.parentElement.parentElement);

        var jsonData = JSON.stringify({
            "questionId": row.data()[1],
            "questionVersion": row.data()[2],
            "sectionId": $('#id').val(),
            "sectionVersion": $('#versionNumber').val(),
            "questionNo": row.data()[0]
        });

        $.ajax({
            type: "POST",
            url: url,
            contentType: 'application/json',
            data: jsonData,
            success: function (data) {
                row.remove().draw();

                table.rows().every(function(){
                    var tempData = this.data();
                    tempData[0] = this.index() + 1;
                    this.data(tempData)
                });
            },
            error: function (data) {
                var msg = "Failed to remove question. " + (data.responseJSON.message ? data.responseJSON.message : "");
                buildWarningAlert(msg)
            }
        });
    });
}

function handleAddingQuestions(table) {
    table.rows().every(function () {
        $('.js-add-question-handle', this.node()).each(function () {
            current = $(this);
            var questionId = current.data('questionId');
            var questionVer = current.data('questionVer');
            var sectionId = $('#id').val();
            var url = current.attr('href');
            var parent = table.row("#" + current.data('parentId'));

            current.click(function (e) {
                e.preventDefault();

                var jsonData = JSON.stringify({
                    "questionId": questionId,
                    "questionVersion": questionVer,
                    "sectionId": sectionId,
                    "sectionVersion": $('#versionNumber').val()
                });

                $.ajax({
                    type: "POST",
                    url: url,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: jsonData,
                    success: function (data) {
                        sectionQuestionDataTable.row
                            .add($(getSectionQuestionFromRow(parent, data))).draw();
                        handleHelp();
                    },
                    error: function (data) {
                        var msg = "Failed to add question. " + (data.responseJSON.message ? data.responseJSON.message : "");
                        if (data.responseJSON.constructor === Array) {
                            data.responseJSON.forEach(function (element) {
                                msg += (element.defaultMessage ? element.defaultMessage + ", " : "")
                            });
                        }
                        buildWarningAlert(msg)
                    }
                });
            });

        });
    });
}

function beginUpdating(questionId, questionVersionNo) {
    $('.js-hide-when-updating').hide();
    $('.js-show-when-updating').hide();

    /*$('.js-create-new-version').removeClass('hidden');
    $('.js-create-new-version').click(function () {
        var version = $('#versionNumber').val();
        $('#versionNumber').val(parseInt(version)+1);
    });*/

    if (questionId && questionVersionNo) {
        $('#id').val(questionId);
        $('#versionNumber').val(questionVersionNo);
    }

    $('#referenceName').attr('readonly', 'readonly');

    var availableQuestionsTable = $('#availableQuestions');
    var availableQuestionsDataTable = availableQuestionsTable.DataTable(
        {
            "columnDefs": [
                {"orderable": false, "targets": 7},
                {"orderable": false, "targets": 8},
                {"searchable": false, "targets": 7},
                {"searchable": false, "targets": 8}
            ],
            "order": [[ 0, "desc" ]]
        });
    availableQuestionsTable.removeClass('hidden');

    $('.js-unhide-me').removeClass('hidden');
    handleAddingQuestions(availableQuestionsDataTable);

    $('.js-save').click(function () {
        ajaxUpdate();
    });
    updatePreviewButton();

    setInterval(ajaxUpdate, 10 * 1000);
}

function updatePreviewButton() {
    var newHref = '/test-paper/view-section/{sectionId}/{sectionVer}'.replace('{sectionId}',$('#id').val());
    newHref = newHref.replace('{sectionVer}',$('#versionNumber').val());
    $('.js-preview').attr('href',newHref);
}


var oldFormData;
function ajaxUpdate() {
    var form = $('.js-ajax-form')[0];
    var formData = $(form).serialize();
    if (formData != oldFormData) {
        var url = window.location.protocol + "//" + window.location.host + $(form).data('updateEndpoint');

        $.ajax({
            type: "POST",
            url: url,
            data: formData, // serializes the form's elements.
            success: function (data) {
                $('#versionNumber').val(data);
                buildSuccessAlert("Successfully Saved");
                oldFormData = formData;
                if ($('#versionNumber').val() != data) {
                    showLoading();
                    window.location.href = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_SECTION_EDITOR + "?sectionId=" + $('#id').val() + "&sectionVersion=" + parseInt(data);
                }
                hideErrorMessages();
            },
            error: function (data) {
                displayErrorMessages(data.responseJSON);
                oldFormData = formData;
            }
        });
    }
}

function bindCreationForm() {
    var form = $('.js-ajax-form')[0];

    var url = form.action;

    $(form).submit(function (event) {
        var formData = $(form).serialize();
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: url,
            data: formData, // serializes the form's elements.
            success: function (data) {
                beginUpdating(data, 1); // show response from the php script.
                oldFormData = formData;
                showLoading();
                window.location.href = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_SECTION_EDITOR + "?sectionId=" + data + "&sectionVersion=1";
            },
            error: function (data) {
                displayErrorMessages(data.responseJSON);
                oldFormData = formData;
            }
        });
    })
}

function enableFroalaEditor() {
    var editor = $('#froala-for-instructions-text');
    enableFroalaOnTarget(editor);

    $(editor).froalaEditor('html.set', $('#instructionsText').val());
}