$(document).ready(function(){
    enableFroalaEditor();
    bindCreationForm();
    $(window).load(function() {
        hideLoading();
        PR.prettyPrint()
    });
    handleMultipleChoiceAnswerBuilder();
});

function beginUpdating(questionId, questionVersionNo) {
    $('.js-hide-when-updating').hide();
    $('#id').val(questionId);
    $('#versionNo').val(questionVersionNo);

    $('#language').attr('readonly','readonly');
    $('#difficulty').attr('readonly','readonly');
    $('#referenceName').attr('readonly','readonly');
    $('#questionTypeId').attr('readonly','readonly');
    $('#type').attr('disabled',true);
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
                beginUpdating(data, 1); // show response from the php script.
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
        htmlRemoveTags: ['script','video','source','input','form','picture'],
        htmlAllowedTags: ["a", "abbr", "address", "area", "article", "aside", "b", "base", "bdi", "bdo", "blockquote", "br", "button", "caption", "cite", "code", "col", "colgroup", "datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt", "em", "fieldset", "figcaption", "figure", "footer", "form", "h1", "h2", "h3", "h4", "h5", "h6", "header", "hgroup", "hr", "i", "img", "ins", "kbd", "keygen", "label", "legend", "li", "main", "map", "mark", "menu", "menuitem", "meter", "nav", "object", "ol", "optgroup", "option", "output", "p", "param", "pre", "progress", "queue", "rp", "rt", "ruby", "s", "samp", "style", "section", "select", "small", "source", "span", "strike", "strong", "sub", "summary", "sup", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "u", "ul", "var", "wbr"],
        toolbarButtons: ['bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', 'fontFamily', 'fontSize', '|', 'specialCharacters', 'color', 'inlineStyle', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', '-', 'quote', 'insertHR', 'insertLink', 'insertImage', 'insertTable', '|', 'undo', 'redo', 'clearFormatting', 'selectAll', 'html', 'applyFormat', 'removeFormat', 'fullscreen'],
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


function handleMultipleChoiceAnswerBuilder() {
    $('#type').change(function () {
        if ($(this).val() == 'Multiple Choice') {
            $('.js-answer-builder').removeClass('hidden');
            buildMultipleChoiceQuestionWizard();
        } else {
            $('#correctAnswer').val('');
            $('.js-answer-builder').addClass('hidden');
            $('.js-insert-auto-marking-wizard').html('')
        }
    });

    function getAnswerCharactersFromText() {
        var questionText = $('#text').val();
        var patt = /[A-Z]\)/g;
        var result = questionText.match(patt);
        return result ? result : [];
    }


    function buildMultipleChoiceQuestionWizard() {
        var maxScore = $('#difficulty').val();
        var tableBody = "";

        for (i = 0; i < maxScore; i++) {
            tableBody +="<tr><td>"+i+"</td><td>";
            getAnswerCharactersFromText().forEach(function (val) {
                function getChecked() {
                    var current = $('#correctAnswer').val();
                    if (current.length > 0) {
                        var currentJson = JSON.parse(current);
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

        function getCurrentJson() {
            var current = $('#correctAnswer').val();
            if (current.length > 0) {
                return currentJson = JSON.parse(current);
            }
            return {};
        }

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
                    scoreRow +="(?!\\1)(["+letters+"])";
                }
            }
            scoreRow += "\\b";
            correctAnswer[scoreRow] = score;
            $('#correctAnswer').val(JSON.stringify(correctAnswer))
        });
        
        $('#js-rebuild-wizard').click(function () {
            $('.js-insert-auto-marking-wizard').html('');
            buildMultipleChoiceQuestionWizard();
        });
    }
}
