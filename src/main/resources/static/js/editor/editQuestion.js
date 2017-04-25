$(document).ready(function(){
    showLoading();
    enableFroalaEditor();
    bindCreationForm();
    $(window).load(function() {
        PR.prettyPrint();
        showExampleQuestion();
        hideLoading();
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

    $('#show-example-btn').click(function (e) {
        e.preventDefault();
        showExampleQuestion(true);
    });

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
                    showLoading();
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

function updateAutoMarkingWizards() {
    var select = $('#question-type-select');

    if (select.val() == 'Multiple Choice') {
        var letters = getMultipleChoiceBlanksFromText();
        if (letters != lettersInText) {
            $('.js-insert-auto-marking-wizard').html('');
            buildMultipleChoiceQuestionWizard();
        }
    }
    else if (select.val() == 'Expression') {
        var numbers  = getExpressionNumbersFromText();
        if (blanksInText != numbers) {
            $('.js-click-to-update').each(function () {
                $(this).triggerHandler('click');
            })
        }
    }
}


function enableFroalaEditor() {
    var editors = $('.js-hook-froala');
    enableFroalaOnTarget($(editors[0]), ['|' ,'add-blank', 'add-letter'],updateAutoMarkingWizards);
    enableFroalaOnTarget($(editors[1]));

    $('#froala-for-question-text').froalaEditor('html.set', $('#text').val());
    $('#froala-for-marking-guide').froalaEditor('html.set', $('#markingGuide').val());
}

function showMultipleChoiceanswerBuilder() {
    $('.js-answer-builder').removeClass('hidden');
    buildMultipleChoiceQuestionWizard();
    handleHelp();
}

function updatePreviewButton() {
    var newHref = '/test-paper/view-question/{questionId}/{questionVer}'.replace('{questionId}',$('#id').val());
    newHref = newHref.replace('{questionVer}',$('#versionNo').val());
    $('.js-preview').attr('href',newHref);
}

function showExpressionAnswerBuilder() {
    $('.js-answer-builder').removeClass('hidden');
    buildExpressionQuestionWizard();
    handleHelp();
}

var exampleCodeQuestion = '<p>Using the IDE below write a program which greets the user using the following function:<br><br></p><pre class="prettyprint"><span class="kwd">function</span><span class="pln">&nbsp;write</span><span class="pun">(</span><span class="pln">msg</span><span class="pun">){</span>\n' +
'<span class="pln">&nbsp; &nbsp; console</span><span class="pun">.</span><span class="pln">log</span><span class="pun">(</span><span class="str">&quot;The message is: &quot;</span><span class="pln">&nbsp;</span><span class="pun">+</span><span class="pln">&nbsp;msg</span><span class="pun">);</span>\n' +
'<span class="pun">}</span></pre>';
var exampleEssayQuestion = '<p>Using the space below describe the <strong><u>Hartford Coliseum Collapse (1978)</u> &nbsp;</strong>software disaster.<br><br>Include:</p><ul><li>Background of the disaster <em>(250 words)</em></li><li>Reasons of the system failure <em>(500 words)</em></li><li>How if could have been avoided <em>(500 words)</em></li></ul>';
var exampleMultipleChoice = '<p>How many times is the following loop executed?</p><pre class="prettyprint"><span class="kwd">int</span><span class="pln">&nbsp;count&nbsp;</span><span class="pun">=</span><span class="pln">&nbsp;</span><span class="lit">1</span><span class="pun">;</span>\n' +
    '<span class="kwd">while</span><span class="pln">&nbsp;</span><span class="pun">(</span><span class="pln">count&nbsp;</span><span class="pun">&lt;</span><span class="pln">&nbsp;</span><span class="lit">5</span><span class="pun">)</span><span class="pln">&nbsp;</span><span class="pun">{</span>\n' +
    '<span class="pln">&nbsp;&nbsp;</span><span class="pln">&nbsp; &nbsp; count&nbsp;</span><span class="pun">+=</span><span class="pln">&nbsp;</span><span class="lit">3</span><span class="pun">;</span><span class="pln">&nbsp; &nbsp;</span>' +
'<span class="pun">}</span><span class="pln">&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></pre><p>A) Just once.<br>B) Twice.<br>C) Three times.<br>D) Four times.&nbsp;</p>';
var exampleDrawingQuestion = '<p><u><strong>Using the graphics tool below, draw a story board for restaurant reservation system.</strong></u></p>';
var exampleExpressionQuestion = '<p>Fill the blanks below with meanings of the corresponding acronyms.</p><p><strong>Acronym &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Meaning</strong><br>LACP &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; [[1]]<br>LAN &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;[[2]]<br>LAPB &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; [[3]]<br>LAPF &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; [[4]]</p>';

function showExampleQuestion(force) {
    var newValue = $('#question-type-select').val();
    var mapTypeToText = {
        'Multiple Choice': exampleMultipleChoice,
        'Expression': exampleExpressionQuestion,
        'Drawing': exampleDrawingQuestion,
        'Essay': exampleEssayQuestion,
        'Code': exampleCodeQuestion
    };

    var currentText = $('#froala-for-question-text').froalaEditor('html.get');
    if (force == true ||
        currentText == '' ||
        currentText == exampleCodeQuestion ||
        currentText == exampleEssayQuestion ||
        currentText == exampleMultipleChoice ||
        currentText == exampleDrawingQuestion||
        currentText == exampleExpressionQuestion) {
        $('#froala-for-question-text').froalaEditor('html.set', mapTypeToText[newValue]);

        $('#text').val($('#froala-for-question-text').froalaEditor('html.get'));
        $('#markingGuide').val($('#froala-for-marking-guide').froalaEditor('html.get'));
    }
}

function handleAnswerBuilder() {
    $('.js-change-type-hook').change(function () {
        var newValue = $(this).val();
        $('#type').val(newValue);

        if (newValue == 'Multiple Choice') {
            $('#add-blank-1').hide('bounce');
            $('#add-letter-1').show('bounce', { times: 5 }, "slow");
            showMultipleChoiceanswerBuilder()
        } else if (newValue == 'Expression') {
            $('#add-letter-1').hide('bounce');
            $('#add-blank-1').show('bounce', { times: 5 }, "slow");
            showExpressionAnswerBuilder();
        } else {
            $('#add-letter-1').hide('bounce');
            $('#add-blank-1').hide('bounce');
            $('#correctAnswer').val('');
            $('.js-answer-builder').addClass('hidden');
            $('.js-insert-auto-marking-wizard').html('')
        }

        showExampleQuestion();

    });
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

function getMultipleChoiceBlanksFromText() {
    var patt = /[A-Z]\)/g;
    return getPatternFromText(patt);
}

function getExpressionNumbersFromText() {
    var patt = /\[\[\d]]/g;
    return getPatternFromText(patt);
}

var lettersInText;
function buildMultipleChoiceQuestionWizard() {
    var difficulty = $('#difficulty');
    var maxScore = difficulty.val();
    var tableBody = "";

    difficulty.unbind();
    difficulty.change(function () {
        $('.js-insert-auto-marking-wizard').html('');
        buildMultipleChoiceQuestionWizard();
    });

    var label = $('#marking-wizard-label');
    $(label).attr({'data-help': 'AUTO_MARKING_MULTIPLE_CHOICE'});
    $(label).data('help','AUTO_MARKING_MULTIPLE_CHOICE');
    flashElementGreen('#marking-wizard-label');


    for (i = 1; i <= maxScore; i++) {
        tableBody +="<tr><td>"+i+"</td><td>";


        var listOfLetters = getMultipleChoiceBlanksFromText();
        lettersInText = listOfLetters;
        listOfLetters.forEach(function (val) {
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

        tableBody += (listOfLetters.length == 0 ? 'Please press "Add multiple-choice option" on question text editor toolbar.' : '') + "</td></tr>";
    }

    var wizardHtmlTemplate =
        "<table class='table table-striped'>" +
        "   <thead>" +
        "       <tr><td>Score</td><td>Answers</td></tr>   " +
        "   </thead>" +
        "   <tbody>" +
        tableBody + (maxScore > 0 ? '' : "Max mark is equal to 0. Increase the mark to enable the wizard. Press 'Refresh Wizard' when ready. ") +
        "   </tbody>" +
        "</table>" +
        "<button data-help='AUTO_MARKING_MULTIPLE_CHOICE_REFRESH' id='js-rebuild-wizard' class='btn btn-primary'>Refresh Wizard</button>";

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

    $('#add-letter-1').unbind();
    $('#add-letter-1').click(function () {
        $('.js-insert-auto-marking-wizard').html('');
        buildMultipleChoiceQuestionWizard();
    })
}

var blanksInText;
function buildExpressionQuestionWizard() {
    var label = $('#marking-wizard-label');
    $(label).attr({'data-help': 'AUTO_MARKING_EXPRESSION'});
    $(label).data('help','AUTO_MARKING_EXPRESSION');
    flashElementGreen('#marking-wizard-label');

    var getRegexBuilderRow = function(jsonRow, jsonRowNumber) {
        var getSelectForBlanks = function(id) {
            var optionTemplate = '<option value="{blankNo}" data-blank="{blankNo}" {selected}>{blankNo}</option>';
            var selectTemplate =
                '<span class="input-group-addon" id="{describedBy}">Blank No <span data-help="AUTO_MARKING_EXPRESSION_BLANK_NO" class="glyphicon glyphicon-align-right glyphicon-question-sign"></span></span>' +
                '<select name="blankNo" class="form-control" id="{id}" aria-describedby="{describedBy}">{selectBody}</select>';
            var result = '';
            var numbers = getExpressionNumbersFromText();
            numbers.forEach(function (val) {
                result += optionTemplate.replaceAll('{blankNo}', val).replace('{selected}', (jsonRow ? jsonRow.blankNo : null) == val ? "selected" : "")
            });
            blanksInText = numbers;
            return selectTemplate.replaceAll('{selectBody}', result).replace('{id}', id).replaceAll('{describedBy}', id + '-describing');
        };

        getRegexBuilderRow["getSelectForBlanks"] = getSelectForBlanks;

        function getFormItem(id, name, label, type, value, style, help) {
            var answerBoxTemplate =
                '<span  class="input-group-addon {style}" id="{describedBy}">{label} <span data-help="'+help+'" class="glyphicon glyphicon-align-right glyphicon-question-sign"></span></span>' +
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
        
        function getOption(id, option, label, checked, help) {
            var rowTemplate =
                '<div class="checkbox">' +
                    '<label>' +
                        '<input name="{option}" type="checkbox" id="{id}" aria-describedby="{describeBy}" {checked}>{label}' +
                    ' <span data-help="'+help+'" class="glyphicon glyphicon-align-right glyphicon-question-sign"></span></label>' +
                '</div>';

            return rowTemplate
                .replaceAll('{describeBy}', id + '-describing')
                .replace('{id}', id + '-' + option)
                .replace('{option}', option)
                .replace('{label}', label)
                .replace('{checked}', (checked ? 'checked' : ''))
                .replace('{style}')
        }


        var rowNo = jsonRowNumber ? jsonRowNumber : $('div.regex-builder-row').length + 1;
        var selectId = 'regex-builder-blank-select-' + rowNo;
        var answerId  = 'regex-builder-answer-' + rowNo;
        var optionId = 'regex-builder-option' + rowNo;
        var rowTemplate =
            '<div id="{row-id}" data-row-no="{rowNo}" class="row regex-builder-row margin-bottom">' +
                '<div class="col-md-12 margin-bottom">' +
                    '<div class="input-group">' +
                        getSelectForBlanks(selectId) +
                        getFormItem(answerId,'answer','Answer', 'text', jsonRow ? jsonRow.answer : null, '' ,'AUTO_MARKING_EXPRESSION_ANSWER') +
                        getFormItem('regex-for-' + rowNo,'regex','Regex', 'text', jsonRow ? jsonRow.regex : null, 'hidden js-enable-regex', 'AUTO_MARKING_EXPRESSION_REGEX') +
                        getFormItem('mark-for-' + rowNo,'mark','Mark', 'number', jsonRow ? jsonRow.mark : null,'','AUTO_MARKING_EXPRESSION_MARK') +
                    '</div>' +
                '</div>' +
                '<div class="col-md-3">' +
                    '<div class="btn-group-vertical" role="group" aria-label="Regex-options">' +
                        getOption(optionId,'whiteSpace','White Space Collapsing', (jsonRow ? jsonRow.options.space : false), 'AUTO_MARKING_EXPRESSION_OPTION_WHITE_SPACE') +
                        getOption(optionId,'alternatePunctuation','Alternative Punctuation', (jsonRow ? jsonRow.options.punctuation : false), 'AUTO_MARKING_EXPRESSION_OPTION_ALTERNATIVE_PUNCTUATION') +
                        getOption(optionId,'caseInsensitive','Case Insensitive', (jsonRow ? jsonRow.options.case : false), 'AUTO_MARKING_EXPRESSION_OPTION_CASE_INSENSITIVE') +
                    '</div>' +
                '</div>' +
                '<div class="col-md-6">' +
                    '<div class="btn-group-vertical" role="group" aria-label="Regex-controls">' +
                        '<button data-help="AUTO_MARKING_EXPRESSION_ADD" class="btn btn-success js-add-new-row">Add Answer</button>' +
                        '<button data-help="AUTO_MARKING_EXPRESSION_CUSTOM_REGEX" class="btn btn-primary js-click-to-customize">Customize Regex</button>' +
                    '</div>' +
                    '<div class="btn-group-vertical" role="group" aria-label="Regex-controls">' +
                        '<button data-help="AUTO_MARKING_EXPRESSION_UPADTE" class="btn btn-warning js-click-to-update">Update Blanks</button>' +
                        '<button data-help="AUTO_MARKING_EXPRESSION_REMOVE" class="btn btn-danger js-click-to-remove">Remove Answer</button>' +
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
                handleHelp();
            });

            var customizeRegex = $(row).find('.js-click-to-customize');
            $(customizeRegex).unbind();

            var initialFunction = function (e) {
                var state = {};
                e.preventDefault();
                $(row).find('.js-enable-regex').removeClass('hidden');
                state['answer'] = $(answer).val();
                $(answer).attr("disabled", true);
                $(answer).addClass('disabled');
                state['whiteSpace'] = $(whiteSpace).prop('checked');
                $(whiteSpace).prop('checked', false);
                $(whiteSpace).attr("disabled", true);
                $(whiteSpace).addClass('disabled');
                state['caseInsensitive'] = $(caseInsensitive).prop('checked');
                $(caseInsensitive).prop('checked', false);
                $(caseInsensitive).attr("disabled", true);
                $(caseInsensitive).addClass('disabled');
                state['punctuation'] = $(punctuation).prop('checked');
                $(punctuation).prop('checked', false);
                $(punctuation).attr("disabled", true);
                $(punctuation).addClass('disabled');
                appendToCorrectAnswer(row);
                var regex = $(row).find('[name=regex]');
                state['regex'] = $(regex).val();
                $(regex).unbind();
                $(regex).keyup(function () {
                    appendToCorrectAnswer(row);
                });
                $(customizeRegex).text('Restore Wizard');
                $(customizeRegex).unbind();
                $(customizeRegex).click(function (e) {
                    e.preventDefault();
                    $(row).find('.js-enable-regex').addClass('hidden');
                    $(answer).val(state['answer']);
                    $(answer).attr("disabled", false);
                    $(answer).removeClass('disabled');
                    $(whiteSpace).prop('checked', state['whiteSpace']);
                    $(whiteSpace).attr("disabled", false);
                    $(whiteSpace).removeClass('disabled');
                    $(caseInsensitive).prop('checked', state['caseInsensitive']);
                    $(caseInsensitive).attr("disabled", false);
                    $(caseInsensitive).removeClass('disabled');
                    $(punctuation).prop('checked', state['punctuation']);
                    $(punctuation).attr("disabled", false);
                    $(punctuation).removeClass('disabled');
                    $(regex).val(state['regex']);
                    $(customizeRegex).text('Customize Regex');
                    $(customizeRegex).unbind();
                    $(customizeRegex).click(initialFunction);
                });
            };

            $(customizeRegex).click(initialFunction);

            var removeBtn = $(row).find('.js-click-to-remove');
            $(removeBtn).unbind();
            $(removeBtn).click(function (e) {
                e.preventDefault();
                removeRowFromAnswer(row);
                $(row).remove();
                if ($('.js-click-to-remove').size() == 0) {
                    $('.js-insert-auto-marking-wizard').append(getRegexBuilderRow());
                    bindJavaScript();
                }
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


            $('#add-blank-1').click(function () {
                $(updateBtn).click()
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
