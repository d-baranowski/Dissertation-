$(document).ready(function(){
    enableFroalaEditor();
    bindCreationForm();
    $(window).load(function() {
        hideLoading();
        PR.prettyPrint()
    });

    var versionNumber = $('#versionNo').val();
    if (parseInt(versionNumber) > 0) {
        if ($('#type').val() == 'Multiple Choice') {
            showMultipleChoiceanswerBuilder();
        } else if ($('#type').val() == 'Expression') {
            showExpressionAnswerBuilder();
        }
        beginUpdating();
    }

    $('#type').val($('.js-change-type-hook').val());

    handleAnswerBuilder();
});

function beginUpdating(questionId, questionVersionNo) {
    $('.js-hide-when-updating').hide();

   /* $('.js-create-new-version').removeClass('hidden');
    $('.js-create-new-version').click(function () {
        var version = $('#versionNo').val();
        $('#versionNo').val(parseInt(version)+1);
    });*/
    $('.js-show-when-updating').removeClass("hidden");


    if (questionId) {
        $('#id').val(questionId);
    }
    if (questionVersionNo) {
        $('#versionNo').val(questionVersionNo);
    }

    updatePreviewButton();

    $('#language').attr('readonly','readonly');
    $('#difficulty').attr('readonly','readonly');
    $('#referenceName').attr('readonly','readonly');
    $('#questionTypeId').attr('readonly','readonly');
    $('.js-change-type-hook').attr('disabled', 'true');

    $('.js-save').click(function () {
        var result = ajaxUpdate();

        if (result == "up-to-date") {
            buildSuccessAlert("Everything up to date.");
        }
    });

    setInterval(ajaxUpdate, 10 * 1000);
}


var oldFormData;
function ajaxUpdate() {
    var form = $('.js-ajax-form')[0];
    var formData = $(form).serialize();
    if (formData != oldFormData) {
        var url =  window.location.protocol + "//" + window.location.host + $(form).data('updateEndpoint');

        $.ajax({
            type: "POST",
            url: url,
            data: formData, // serializes the form's elements.
            success: function(data)
            {
                hideErrorMessages();
                if ($('#versionNo').val() != data) {
                    window.location.href = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_QUESTION_EDITOR
                        + '?questionId='+$('#id').val()+'&questionVersion='+data;
                }

                buildSuccessAlert("Successfully Saved");
                oldFormData = formData;
            },
            error: function (data) {
                displayErrorMessages(data.responseJSON);
                oldFormData = formData;
            }
        });
    } else {
        return "up-to-date";
    }
}

function bindCreationForm() {
    var form = $('.js-ajax-form')[0];

    var url =  form.action;

    $(form).submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: url,
            data: $(form).serialize(), // serializes the form's elements.
            success: function(data)
            {
                hideErrorMessages();
                window.location.href = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_QUESTION_EDITOR
                    + '?questionId='+data+'&questionVersion='+1;
            },
            error: function (data) {
                displayErrorMessages(data.responseJSON);
            }
        });
    })
}

function enableFroalaEditor() {
    var editors = $('.js-hook-froala');
    editors.froalaEditor({
        paragraphStyles: {
            'prettyprint lang-sql': 'SQL',
            'prettyprint': 'Code'
        },
        fontSizeDefaultSelection: '18',
        htmlRemoveTags: ['script','video','source','input','form','picture'],
        htmlAllowedTags: ["a", "abbr", "address", "area", "article", "aside", "b", "base", "bdi", "bdo", "blockquote", "br", "button", "caption", "cite", "code", "col", "colgroup", "datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt", "em", "fieldset", "figcaption", "figure", "footer", "form", "h1", "h2", "h3", "h4", "h5", "h6", "header", "hgroup", "hr", "i", "img", "ins", "kbd", "keygen", "label", "legend", "li", "main", "map", "mark", "menu", "menuitem", "meter", "nav", "object", "ol", "optgroup", "option", "output", "p", "param", "pre", "progress", "queue", "rp", "rt", "ruby", "s", "samp", "style", "section", "select", "small", "source", "span", "strike", "strong", "sub", "summary", "sup", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "u", "ul", "var", "wbr"],
        toolbarButtons: ['bold', 'italic', 'underline', 'specialCharacters', 'strikeThrough', 'subscript', 'superscript', 'fontFamily', 'fontSize', '|', 'specialCharacters', 'color', 'inlineStyle', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', '-', 'quote', 'insertHR', 'insertLink', 'insertImage', 'insertTable', '|', 'undo', 'redo', 'clearFormatting', 'selectAll', 'html', 'applyFormat', 'removeFormat', 'fullscreen'],
        pluginsEnabled: null
    });

    editors.on('froalaEditor.commands.after', function (e, editor, cmd, param1, param2) {
        if (param1 == 'prettyprint') {
            PR.prettyPrint();
        }
    });

    editors.on('froalaEditor.contentChanged', function (e, editor) {
        var html = editor.html.get();
        $('.'+$(this).data('paste-to')).val(html);
    });

    $('#froala-for-question-text').froalaEditor('html.set', $('#text').val());
    $('#froala-for-marking-guide').froalaEditor('html.set', $('#markingGuide').val());
}

function hideErrorMessages() {
    $('.js-hook-error-msg').text('');
    $(' .js-hook-form-status').removeClass('has-danger');
}

function displayErrorMessages(errors) {
    errors.forEach(function(error) {
        var formGroup = $('#form-group-' + error.field);
        var field = $('#error-' + error.field);
        field.text(error.defaultMessage);
        formGroup.addClass('has-danger')
    });
}

function showMultipleChoiceanswerBuilder() {
    $('.js-answer-builder').removeClass('hidden');
    buildMultipleChoiceQuestionWizard();
}

function updatePreviewButton() {
    var newHref = '/test-paper/view-question/{questionId}/{questionVer}'.replace('{questionId}',$('#id').val());
    newHref = newHref.replace('{questionVer}',$('#versionNo').val());
    $('.js-preview').attr('href',newHref);
}

function showExpressionAnswerBuilder() {
    $('.js-answer-builder').removeClass('hidden');
    buildExpressionQuestionWizard();
}

function handleAnswerBuilder() {
    $('.js-change-type-hook').change(function () {
        var newValue = $(this).val();
        $('#type').val(newValue);

        if (newValue == 'Multiple Choice') {
            showMultipleChoiceanswerBuilder()
        } else if (newValue == 'Expression') {
            showExpressionAnswerBuilder();
        } else {
            $('#correctAnswer').val('');
            $('.js-answer-builder').addClass('hidden');
            $('.js-insert-auto-marking-wizard').html('')
        }
    });
}

function getPatternFromText(pattern) {
    var questionText = $('#text').val();
    var result = questionText.match(pattern);
    return result ? result : [];
}

function getCurrentJson() {
    var current = $('#correctAnswer').val();
    if (current.length > 0) {
        try {
            return currentJson = JSON.parse(current);
        } catch(err) {
            return {}
        }
    }
    return {};
}

function safeParse(someString) {
    try {
        return JSON.parse(someString)
    } catch(err) {
        return {}
    }
}

function buildMultipleChoiceQuestionWizard() {
    var maxScore = $('#difficulty').val();
    var tableBody = "";

    for (i = 0; i < maxScore; i++) {
        tableBody +="<tr><td>"+i+"</td><td>";
        var patt = /[A-Z]\)/g;
        getPatternFromText(patt).forEach(function (val) {
            function getChecked() {
                var current = $('#correctAnswer').val();
                if (current.length > 0) {
                    var currentJson = safeParse(current);
                    if (currentJson[i]) {
                        var checked = currentJson[i].match(val.replace("\)",''));
                        return checked ? 'checked' : '';
                    }
                }
                return '';
            }

            tableBody += " <b>" + val + "</b> <input class='js-build-score' data-score='" + i + "' data-letter-required='" + val + "' type='checkbox' " + getChecked() + ">"
        });


        tableBody += "</td></tr>";
    }

    var wizardHtmlTemplate =
        "<table class='table table-striped'>" +
        "   <thead>" +
        "       <tr><td>Score</td><td>Answers</td></tr>   " +
        "   </thead>" +
        "   <tbody>" +
        tableBody +
        "   </tbody>" +
        "</table>" +
        "<button id='js-rebuild-wizard' class='btn btn-primary'>Update</button>";

    $('.js-insert-auto-marking-wizard').html(wizardHtmlTemplate);

    var correctAnswer = getCurrentJson();

    $('.js-build-score').change(function () {
        var score = $(this).data('score');
        var checkboxes = $(this).parent().children('.js-build-score:checked');
        var letters = [];
        for (var i = 0; i<checkboxes.length; i++) {
            letters.push($(checkboxes[i]).data('letterRequired').replace("\)",''));
        }
        var scoreRow = "\\b";
        scoreRow +="(["+letters+"])";
        if (letters.length > 1) {
            for (var j = 0; j < letters.length - 1; j++) {
                scoreRow +=",(?!\\1)(["+letters+"])";
            }
        }
        scoreRow += "\\b";
        correctAnswer[score] = scoreRow;
        $('#correctAnswer').val(JSON.stringify(correctAnswer))
    });

    $('#js-rebuild-wizard').click(function () {
        $('.js-insert-auto-marking-wizard').html('');
        buildMultipleChoiceQuestionWizard();
    });
}

function buildExpressionQuestionWizard() {
    var getRegexBuilderRow = function(jsonRow, jsonRowNumber) {
        var getSelectForBlanks = function(id) {
            var optionTemplate = '<option value="{blankNo}" data-blank="{blankNo}" {selected}>{blankNo}</option>';
            var selectTemplate =
                '<span class="input-group-addon" id="{describedBy}">Blank No</span>' +
                '<select name="blankNo" class="form-control" id="{id}" aria-describedby="{describedBy}">{selectBody}</select>';
            var result = '';
            getPatternFromText(patt).forEach(function (val) {
                result += optionTemplate.replaceAll('{blankNo}', val).replace('{selected}', (jsonRow ? jsonRow.blankNo : null) == val ? "selected" : "")
            });
            return selectTemplate.replaceAll('{selectBody}', result).replace('{id}', id).replaceAll('{describedBy}', id + '-describing');
        };

        getRegexBuilderRow["getSelectForBlanks"] = getSelectForBlanks;

        function getFormItem(id, name, label, type, value, style) {
            var answerBoxTemplate =
                '<span class="input-group-addon {style}" id="{describedBy}">{label}</span>' +
                '<input {value} name="{name}" id="{id}" type="{type}" class="form-control {style}" placeholder="{label}" aria-describedby="{describedBy}">';

            return answerBoxTemplate
                .replace('{value}', (value ? 'value="' +value + '"' : ""))
                .replaceAll('{describedBy}', id + '-describing')
                .replace('{id}', id)
                .replaceAll('{label}', label)
                .replace('{name}',name)
                .replace('{type}', type)
                .replaceAll('{style}', style ? style : "")
        }
        
        function getOption(id, option, label, checked) {
            var rowTemplate =
                '<div class="checkbox">' +
                    '<label>' +
                        '<input name="{option}" type="checkbox" id="{id}" aria-describedby="{describeBy}" {checked}>{label}' +
                    '</label>' +
                '</div>';

            return rowTemplate
                .replaceAll('{describeBy}', id + '-describing')
                .replace('{id}', id + '-' + option)
                .replace('{option}', option)
                .replace('{label}', label)
                .replace('{checked}', (checked ? 'checked' : ''))
        }

        var patt = /\[\[\d]]/g;
        var rowNo = jsonRowNumber ? jsonRowNumber : $('div.regex-builder-row').length + 1;
        var selectId = 'regex-builder-blank-select-' + rowNo;
        var answerId  = 'regex-builder-answer-' + rowNo;
        var optionId = 'regex-builder-option' + rowNo;
        var rowTemplate =
            '<div id="{row-id}" data-row-no="{rowNo}" class="row regex-builder-row margin-bottom">' +
                '<div class="col-md-12 margin-bottom">' +
                    '<div class="input-group">' +
                        getSelectForBlanks(selectId) +
                        getFormItem(answerId,'answer','Answer', 'text', jsonRow ? jsonRow.answer : null) +
                        getFormItem('regex-for-' + rowNo,'regex','Regex', 'text', jsonRow ? jsonRow.regex : null, 'hidden js-enable-regex') +
                        getFormItem('mark-for-' + rowNo,'mark','Mark', 'number', jsonRow ? jsonRow.mark : null) +
                    '</div>' +
                '</div>' +
                '<div class="col-md-3">' +
                    '<div class="btn-group-vertical" role="group" aria-label="Regex-options">' +
                        getOption(optionId,'whiteSpace','White Space Collapsing', (jsonRow ? jsonRow.options.space : false)) +
                        getOption(optionId,'alternatePunctuation','Alternative Punctuation', (jsonRow ? jsonRow.options.punctuation : false)) +
                        getOption(optionId,'caseInsensitive','Case Insensitive', (jsonRow ? jsonRow.options.case : false)) +
                    '</div>' +
                '</div>' +
                '<div class="col-md-6">' +
                    '<div class="btn-group-vertical" role="group" aria-label="Regex-controls">' +
                        '<button class="btn btn-success js-add-new-row">Add</button>' +
                        '<button class="btn btn-primary js-click-to-customize">Customize Regex</button>' +
                    '</div>' +
                    '<div class="btn-group-vertical" role="group" aria-label="Regex-controls">' +
                        '<button class="btn btn-warning js-click-to-update">Update</button>' +
                        '<button class="btn btn-danger js-click-to-remove">Remove</button>' +
                    '</div>' +
                '</div>' +
            '</div>';


        return rowTemplate.replace('{rowNo}', rowNo).replace('{rowId}', 'regex-builder-row-' + rowNo);
    };

    function isCorrectFormat(json) {
        var keys = Object.keys(json);
        var nonNullKey;
        keys.forEach(function (val) {
            if (val) {
                nonNullKey = val;
            }
        });
        try {
            if (nonNullKey) {
                return keys.length > 0 &&  json[nonNullKey].blankNo && json[nonNullKey].options;
            } else {
                return false;
            }
        } catch (error) {
            return false;
        }
    }


    function buildJsonFromRow(row) {
        var answer = $(row).find('[name=answer]').val();
        var blankNo = $(row).find('[name=blankNo]').val();
        var regex = $(row).find('[name=regex]').val();
        var mark = $(row).find('[name=mark]').val();
        var optSpace = $(row).find('[name=whiteSpace]').prop('checked');
        var optPunctuation = $(row).find('[name=alternatePunctuation]').prop('checked');
        var optCase = $(row).find('[name=caseInsensitive]').prop('checked');

        return {"blankNo": blankNo, "answer": answer, "regex": regex, "mark": mark, "options": {"space": optSpace, "punctuation": optPunctuation, "case": optCase}}
    }

    function prepRegex(row) {
        var answer = $(row).find('[name=answer]').val();
        $(row).find('[name=regex]').val(answer);
    }

    function applyWhiteSpaceOption(row) {
        var regex = $(row).find('[name=regex]');
        var oldRegex = $(regex).val();
        $(regex).val( oldRegex.replace(/\s+/g,'\\s+'));
    }

    function applyAlternativePunctuation(row) {
        var regex = $(row).find('[name=regex]');
        var oldRegex = $(regex).val();
        $(regex).val(oldRegex.replace(/[,.:;/'|]/g,"[,.:;'|\\s]{1}"));
    }

    function applyCaseInsensitive(row) {
        var regex = $(row).find('[name=regex]');
        var oldRegex = $(regex).val();
        oldRegex = '(?i)(' + oldRegex + ')';
        $(regex).val(oldRegex);
    }

    function regenerateRegex(row) {
        prepRegex(row);

        if ($(row).find('[name=whiteSpace]').prop('checked')) {
            applyWhiteSpaceOption(row);
        }
        if ($(row).find('[name=alternatePunctuation]').prop('checked')) {
            applyAlternativePunctuation(row);
        }
        if ($(row).find('[name=caseInsensitive]').prop('checked')) {
            applyCaseInsensitive(row);
        }
    }
    
    function appendToCorrectAnswer(row) {
        var correct = $('#correctAnswer');
        var current = $(correct).val();
        var currentJson = current.length > 0 ? JSON.parse(current) : [];
        var rowNo = $(row).data('rowNo');
        currentJson[parseInt(rowNo)] = buildJsonFromRow(row);
        $(correct).val(JSON.stringify(currentJson));
    }

    function removeRowFromAnswer(row) {
        var correct = $('#correctAnswer');
        var current = $(correct).val();
        var currentJson = current.length > 0 ? JSON.parse(current) : [];
        var rowNo = $(row).data('rowNo');
        delete currentJson[parseInt(rowNo)];
        $(correct).val(JSON.stringify(currentJson));
    }
    
    function bindJavaScript() {
        $('.regex-builder-row').each(function(){
            regenerateRegex(this);
            var row = this;

            function refresh() {
                if ($('.js-enable-regex').hasClass('hidden')) {
                    regenerateRegex(row);
                }
                appendToCorrectAnswer(row);
            }

            var answer = $(row).find('[name=answer]');
            $(answer).unbind();
            $(answer).keyup((function(){
                refresh();
            }));

            var blankNo = $(row).find('[name=blankNo]');
            $(blankNo).unbind();
            $(blankNo).change((function(){
                refresh();
            }));


            var mark = $(row).find('[name=mark]');
            $(mark).unbind();
            $(mark).change((function(){
                refresh();
            }));

            $(mark).keyup((function(){
                refresh();
            }));

            var whiteSpace = $(row).find('[name=whiteSpace]');
            $(whiteSpace).unbind();
            $(whiteSpace).change(function(){
                refresh();
            });

            var punctuation = $(row).find('[name=alternatePunctuation]');
            $(punctuation).unbind();
            $(punctuation).change(function(){
                refresh();
            });

            var caseInsensitive = $(row).find('[name=caseInsensitive]');
            $(caseInsensitive).unbind();
            $(caseInsensitive).change(function(){
                refresh();
            });

            var addNewRow = $(row).find('.js-add-new-row');
            $(addNewRow).unbind();
            $(addNewRow).click(function(e) {
                e.preventDefault();
                $('.js-insert-auto-marking-wizard').append(getRegexBuilderRow());
                bindJavaScript();
            });

            var customizeRegex = $(row).find('.js-click-to-customize');
            $(customizeRegex).unbind();
            $(customizeRegex).click(function (e) {
                e.preventDefault();
                $(row).find('.js-enable-regex').removeClass('hidden');
                $(answer).prop('checked', false);
                $(answer).attr("disabled", true);
                $(answer).addClass('disabled');
                $(whiteSpace).prop('checked', false);
                $(whiteSpace).attr("disabled", true);
                $(whiteSpace).addClass('disabled');
                $(caseInsensitive).prop('checked', false);
                $(caseInsensitive).attr("disabled", true);
                $(caseInsensitive).addClass('disabled');
                $(punctuation).prop('checked', false);
                $(punctuation).attr("disabled", true);
                $(punctuation).addClass('disabled');
                appendToCorrectAnswer(row);
                var regex = $(row).find('[name=regex]');
                $(regex).unbind();
                $(regex).keyup(function () {
                    appendToCorrectAnswer(row);
                })
            });

            var removeBtn = $(row).find('.js-click-to-remove');
            $(removeBtn).unbind();
            $(removeBtn).click(function (e) {
                e.preventDefault();
                removeRowFromAnswer(row);
                $(row).remove();
            });

            var updateBtn = $(row).find('.js-click-to-update');
            $(updateBtn).unbind();
            $(updateBtn).click(function (e) {
                e.preventDefault();
                var rowNumber = $(row).data('rowNo');
                var blanksId = 'regex-builder-blank-select-' + rowNumber;
                var currentBlanks = $('#' + blanksId);
                $('#' + $(currentBlanks).attr('aria-describedby')).remove();
                $(currentBlanks).replaceWith(getRegexBuilderRow.getSelectForBlanks(blanksId))
            });

        });
    }

    var current = $('#correctAnswer').val();
    var currentJson = current.length > 0 ? JSON.parse(current) : [];
    var rows = '';
    if (isCorrectFormat(currentJson)) {
        for (var i = 0; i < currentJson.length; i++) {
            if (currentJson[i]) {
                rows += getRegexBuilderRow(currentJson[i], i)
            }
        }
    }
    rows += getRegexBuilderRow(null, currentJson.length);
    $('.js-insert-auto-marking-wizard').html(rows);
    bindJavaScript();
}
