var paperSectionsDataTable;

$(document).ready(function () {
    enableFroalaEditor();
    bindCreationForm();
    $(window).load(function () {
        var paperSectionsTable = $('#paperSections');
        paperSectionsDataTable = paperSectionsTable.DataTable(
            {
                "rowReorder": true,
                "columnDefs": [
                    {"orderable": false, "targets": 6},
                    {"orderable": false, "targets": 7},
                    {"searchable": false, "targets": 6},
                    {"searchable": false, "targets": 7}
                ]
            }
        );

        paperSectionsDataTable.on('row-reorder',function(e, diff, edit) {
            var paperId = $('#id').val();
            var paperVersion = $('#versionNo').val();
            var updates = [];

            for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
                var rowData = paperSectionsDataTable.row( diff[i].node ).data();
                updates.push({
                    "paperId": paperId,
                    "paperVer": paperVersion,
                    "sectionId": rowData[1],
                    "sectionVer": rowData[2],
                    "newRef": diff[i].newData[0]
                })
            }

            var url = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_MOVE_SECTION_IN_PAPER;

            var jsonData = JSON.stringify(updates);

            $.ajax({
                type: "POST",
                url: url,
                contentType: 'application/json',
                data: jsonData,
                error: function (data) {
                    buildWarningAlert('Failed to move section')
                }
            });
        });

        handleDeletingSectionsFromPaper(paperSectionsDataTable);

        var versionNumber = $('#versionNo').val();
        if (parseInt(versionNumber) > 0) {
            beginUpdating();
        }
        PR.prettyPrint();
        hideLoading();
    });
});

function updateUrl() {
    var queryParameters = {}, queryString = location.search.substring(1),
        re = /([^&=]+)=([^&]*)/g, m;
    while (m = re.exec(queryString)) {
        queryParameters[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
    }
    queryParameters['paperId'] = $('#id').val();
    queryParameters['paperVersion'] = $('#versionNo').val();

    window.location.search = $.param(queryParameters);
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
                showLoading();
                hideErrorMessages();
                beginUpdating(data, 1);
                oldFormData = formData;
                updateUrl()
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
function updatePreviewButton() {
    var newHref = '/test-paper/view-section/{sectionId}/{sectionVer}'.replace('{sectionId}',$('#id').val());
    newHref = newHref.replace('{sectionVer}',$('#versionNo').val());
    $('.js-preview').attr('href',newHref);
}


function beginUpdating(paperId, paperVer) {
    $('.js-hide-when-updating').hide();
    $('.js-show-when-updating').removeClass("hidden");

   /* $('.js-create-new-version').removeClass('hidden');
    $('.js-create-new-version').click(function () {
        var version = $('#versionNo').val();
        $('#versionNo').val(parseInt(version)+1);
    });*/

    if (paperId && paperVer) {
        $('#id').val(paperId);
        $('#versionNo').val(paperVer);
    }

    $('.js-save').click(function () {
        ajaxUpdate();
    });

    $('#referenceName').attr('readonly', 'readonly');

    var availableSectionsTable = $('#availableSections');
    var availableSectionsDataTable = availableSectionsTable.DataTable(
        {
            "columnDefs": [
                {"orderable": false, "targets": 5},
                {"orderable": false, "targets": 6},
                {"searchable": false, "targets": 5},
                {"searchable": false, "targets": 6}
            ],
            "order": [[ 0, "desc" ]]
        });
    availableSectionsTable.removeClass('hidden');

    $('.js-unhide-me').removeClass('hidden');
    handleAddingSections(availableSectionsDataTable);
    updatePreviewButton();
    setInterval(ajaxUpdate, 10 * 1000);
}

function handleAddingSections(table) {
    table.rows().every(function () {
        $('.js-add-section-handle', this.node()).each(function () {
            current = $(this);
            var sectionId = current.data('sectionId');
            var sectionVersion = current.data('sectionVer');
            var paperId = $('#id').val();
            var url = current.attr('href');
            var parent = table.row("#" + current.data('parentId'));

            current.click(function (e) {
                e.preventDefault();

                var jsonData = JSON.stringify({
                    "sectionId": sectionId,
                    "sectionVersion": sectionVersion,
                    "paperId": paperId,
                    "paperVersion": $('#versionNo').val()
                });

                $.ajax({
                    type: "POST",
                    url: url,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: jsonData,
                    success: function (data) {
                        paperSectionsDataTable.row
                            .add($(getSectionFromRow(parent, data))).draw();
                    },
                    error: function (data) {
                        var msg = "Failed to add section. " + (data.responseJSON.message ? data.responseJSON.message : "");
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

function getSectionFromRow(row, sectionNumber) {
    var array = row.data();
    var result = [sectionNumber];
    array.forEach(function (object) {
        result.push(object)
    });

    return "<tr>" +
        "<td class='move-me'>" + result[0] + "" +
        "<span data-help='CREATE_PAPER_PAPER_SECTION_ORDER' " +
        "class='glyphicon glyphicon-align-right glyphicon-resize-vertical'></span>" +
        "</td>" +
        "<td>" + result[1] + "</td>" +
        "<td>" + result[2] + "</td>" +
        "<td>" + result[3] + "</td>" +
        "<td>" + result[4] + "</td>" +
        "<td>" + result[5] + "</td>" +
        "<td>" + result[6] + "</td>" +
        "<td><a target='_blank' class='js-remove-section-handle' href='test'>Remove</a></td>" +
        "</tr>";
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
                hideErrorMessages();

                if ($('#versionNo').val() != data) {
                    $('#versionNo').val(data);
                    updateUrl()
                }
                buildSuccessAlert("Successfully Saved");
                oldFormData = formData;
            },
            error: function (data) {
                displayErrorMessages(data.responseJSON);
                oldFormData = formData;
            }
        });
    }
}

function handleDeletingSectionsFromPaper(table) {
    var url = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_REMOVE_SECTION_FROM_PAPER;

    table.on('click', '.js-remove-section-handle', function (e) {
        current = $(this);

        e.preventDefault();

        var row = table.row(this.parentElement.parentElement);

        var jsonData = JSON.stringify({
            "sectionId": row.data()[1],
            "sectionVersion": row.data()[2],
            "paperId": $('#id').val(),
            "paperVersion": $('#versionNo').val(),
            "sectionNo": row.data()[0]
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
                var msg = "Failed to remove section. " + (data.responseJSON.message ? data.responseJSON.message : "");
                if (data.responseJSON.constructor === Array) {
                    data.responseJSON.forEach(function (element) {
                        msg += (element.defaultMessage ? element.defaultMessage + ", " : "")
                    });
                }
                buildWarningAlert(msg)
            }
        });
    });
}
