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
                    "newRef": diff[i].newData
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
                    buildWarningAlert('Failed to move question')
                }
            });
        });

        handleDeletingSectionsFromPaper(paperSectionsDataTable);

        var versionNumber = $('#versionNo').val();
        if (parseInt(versionNumber) > 0) {
            beginUpdating();
        }
        PR.prettyPrint()
    });


    hideLoading();
});

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
                hideErrorMessages();
                beginUpdating(data, 1); // show response from the php script.
                oldFormData = formData;
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
    editor.froalaEditor({
        paragraphStyles: {
            'prettyprint lang-sql': 'SQL',
            'prettyprint': 'Code'
        },
        htmlRemoveTags: ['script', 'video', 'source', 'input', 'form', 'picture'],
        htmlAllowedTags: ["a", "abbr", "address", "area", "article", "aside", "b", "base", "bdi", "bdo", "blockquote", "br", "button", "caption", "cite", "code", "col", "colgroup", "datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt", "em", "fieldset", "figcaption", "figure", "footer", "form", "h1", "h2", "h3", "h4", "h5", "h6", "header", "hgroup", "hr", "i", "img", "ins", "kbd", "keygen", "label", "legend", "li", "main", "map", "mark", "menu", "menuitem", "meter", "nav", "object", "ol", "optgroup", "option", "output", "p", "param", "pre", "progress", "queue", "rp", "rt", "ruby", "s", "samp", "style", "section", "select", "small", "source", "span", "strike", "strong", "sub", "summary", "sup", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "u", "ul", "var", "wbr"],
        toolbarButtons: ['bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', 'fontFamily', 'fontSize', '|', 'specialCharacters', 'color', 'inlineStyle', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', '-', 'quote', 'insertHR', 'insertLink', 'insertImage', 'insertTable', '|', 'undo', 'redo', 'clearFormatting', 'selectAll', 'html', 'applyFormat', 'removeFormat', 'fullscreen'],
        pluginsEnabled: null
    });

    editor.froalaEditor('html.set', $('#instructionsText').val());

    editor.on('froalaEditor.commands.after', function (e, editor, cmd, param1, param2) {
        if (param1 == 'prettyprint') {
            PR.prettyPrint();
        }
    });

    editor.on('froalaEditor.contentChanged', function (e, editor) {
        var html = editor.html.get();
        $('.' + $(this).data('paste-to')).val(html);
    });
}

function beginUpdating(paperId, paperVer) {
    $('.js-hide-when-updating').hide();
    $('.js-show-when-updating').hide();

    if (paperId && paperVer) {
        $('#id').val(paperId);
        $('#versionNo').val(paperVer);
    }

    $('#referenceName').attr('readonly', 'readonly');

    var availableSectionsTable = $('#availableSections');
    var availableSectionsDataTable = availableSectionsTable.DataTable(
        {
            "columnDefs": [
                {"orderable": false, "targets": 5},
                {"orderable": false, "targets": 6},
                {"searchable": false, "targets": 5},
                {"searchable": false, "targets": 6}
            ]
        });
    availableSectionsTable.removeClass('hidden');

    $('.js-unhide-me').removeClass('hidden');
    handleAddingSections(availableSectionsDataTable);

    setInterval(ajaxUpdate, 10 * 1000);
}

function handleAddingSections(table) {
    table.rows().every(function () {
        $('.js-add-section-handle', this.node()).each(function () {
            current = $(this);
            var sectionId = current.data('sectionId');
            var sectionVersion = current.data('sectionVer');
            var paperId = current.data('paperId');
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
        "<td>" + result[0] + "</td>" +
        "<td>" + result[1] + "</td>" +
        "<td>" + result[2] + "</td>" +
        "<td>" + result[3] + "</td>" +
        "<td>" + result[4] + "</td>" +
        "<td>" + result[5] + "</td>" +
        "<td>" + result[6] + "</td>" +
        "<td><a class='js-remove-section-handle' href='test'>Remove</a></td>" +
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
                $('#versionNo').val(data);
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

function hideErrorMessages() {
    $('.js-hook-error-msg').text('');
    $(' .js-hook-form-status').removeClass('has-danger');
}
