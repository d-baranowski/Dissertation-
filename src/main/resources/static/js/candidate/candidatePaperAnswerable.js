$(document).ready(function(){
    handleQuestionSubmissions();
    enableAceCodeEditor();
    initializePaint();
    linkMultipleChoiceWellToCheckBox();
    loadAccordionSubmittedQuestions();
    assignShowSubmitDialogToButton();
    handleExpressionQuestion();
});

function linkMultipleChoiceWellToCheckBox() {
    var checkBoxes = $(".multiple-choice-tick input");

    $(checkBoxes).each(function() {
        var currentCheck = $(this);
        var surroundingWell = $(this).parent();

        $(surroundingWell).click(function() {
            $(currentCheck).click();
        });

        //Prevents infinite loop
        $(currentCheck).click(function(event) {
            event.stopPropagation();
        });
    });
}

/**
    This method calculates candidates progress, taking in account the number of questions
    required to answer in each section.

    It uses .accordion-question-submitted count per each questionLists and value of
    attribute sectionQuestionsToAnswer to calculate correct percentage.
    Note ! This method will always be called at the end of
    loadAccordionSubmittedQuestions() and notifyAccordionOfQuestionSubmission(),
    because they append class .accordion-question-submitted to submitted questions.
 **/
function updateProgressBar() {
    var progressBar = $("#questionProgress");
    var totalQuestionsToAnswer = parseInt($(progressBar).attr("totalQuestionsToAnswer"));
    var answeredQuestions = 0;

    var questionLists = $(".questionList");

    $(questionLists).each(function() {
        var maxQuestionsForThisSection = parseInt($(this).attr("sectionQuestionsToAnswer"));
        var totalSubmittedQuestions = $(this).find(".accordion-question-submitted").length;

        if (totalSubmittedQuestions <= maxQuestionsForThisSection) {
            answeredQuestions += totalSubmittedQuestions;
        } else {
            answeredQuestions += maxQuestionsForThisSection;
        }
    });

    var result = answeredQuestions * 100 / totalQuestionsToAnswer;
    result = Math.round(result).toFixed(2);


    $(progressBar).attr('aria-valuenow', result);
    $(progressBar).attr('style', "width: " + result + "%;");
    $(progressBar).find("p").html(result + "%");
}

/**
    1.Get all sections
    2. Get all submitted questions for this section
    3. If submitted questions no is less than number of questions to answer in this section
       3.1 Add all non submitted questions from this answer to the list
 **/
function getTitlesOfNonSubmittedQuestions() {
    var questionLists = $(".questionList");
    var titlesOfNonSubmittedQuestions = [];

    $(questionLists).each(function() {
        var currentSection = this;
        var maxQuestionsForThisSection = parseInt($(this).attr("sectionQuestionsToAnswer"));
        var totalSubmittedQuestions = $(this).find(".accordion-question-submitted").length;

        if (totalSubmittedQuestions < maxQuestionsForThisSection) {
            var notSubmittedQuestions = $(currentSection).find(".panel-body:not(.accordion-question-submitted)");

            $(notSubmittedQuestions).each(function() {
                var title = $(this).html();
                titlesOfNonSubmittedQuestions.push(title);
            });
        }
    });

    return titlesOfNonSubmittedQuestions;
}

function enableAceCodeEditor() {
    var codeQuestions = $(".aceEditor");

    codeQuestions.each(
        function() {
            //Find hidden input field for form
            var hidden = $(this).parent().find('input')[0];
            var selectMode = $(this).parent().find('select')[0];

            //Instantiate Ace Plugin to generate code editor
            $(this).prop('id', 'tempId');
            document.getElementById('tempId').style.fontSize='18px';
            var editor = ace.edit('tempId');
            $(this).removeAttr('id');
            editor.setValue($(hidden).val());
            //Make editor populate hidden input field
            editor.getSession().on('change', function() {
                $(hidden).val(editor.getValue());
            });

            //Allow changing modes
            $(selectMode).on('change', function () {
                 var valueSelected = this.value;
                editor.getSession().setMode("ace/mode/" + valueSelected);
            });

            //Set editor mode based on question language. Defaults to actionscript, because its the fist language on the list.
            editor.getSession().setMode("ace/mode/actionscript");
            editor.setOptions({
                enableBasicAutocompletion: true
            });
            editor.setTheme("ace/theme/monokai");
        }
    );
}

function initializePaint() {
    var paints = $('.wPaint');

    paints.each(function () {
        var editor = $(this).wPaint({
            path: '/js/lib/wPaint-2.5.0/'
        });

        var form = $(this).parent().parent().find("form")[0];
        var hidden = $(form).find('input[name="base64imagePng"]')[0];
        $(editor).wPaint('image', $(hidden).val());

        $(form).submit(
            function( submit ) {
                submit.preventDefault();
                $(hidden).val(editor.wPaint("image"));
                var formData = $(form).serialize();

                $.ajax({
                    type: "POST",
                    url: $(this).attr('action'),
                    data: formData,
                    success: function(data) {
                        if (data == "ok") {
                            //Mark form as submitted
                            $(form).attr('questionIsSubmitted', true);
                            notifyAccordionOfQuestionSubmission($(form).parent().attr("data-slick-index"));
                            $("#nextQuestion").click();
                        } else {
                            buildSubmissionFailedAlert(data)
                        }
                    },
                    error: function (request, status, error) {
                        buildDangerAlert(error);
                    }
                })
            }
        );
    });
}

function handleQuestionSubmissions() {
    $('form:not(.specialHandler)').submit(function( submit ) {
        submit.preventDefault();
        var formData = $(this).serialize();
        var form = $(this);

        $.ajax({
            type: "POST",
            url: $(this).attr('action'),
            data: formData,
            success: function(data) {
                if (data == "ok") {
                    //Mark form as submitted
                    $(form).attr('questionIsSubmitted', true);
                    notifyAccordionOfQuestionSubmission($(form).parent().attr("data-slick-index"));
                    $("#nextQuestion").click();
                } else {
                    buildSubmissionFailedAlert(data)
                }
            },
            error: function (request, status, error) {
                buildDangerAlert(error)
            }
        })
    });
}

function notifyAccordionOfQuestionSubmission(slideIndex) {
    var actualSlideIndex = parseInt(slideIndex);
    var accordionElement = $("a[slickSlide="+actualSlideIndex+"]").find(".panel-body")[0];
    $(accordionElement).addClass("accordion-question-submitted");
    updateProgressBar();
}

function loadAccordionSubmittedQuestions() {
    var submittedForms = $("form[questionIsSubmitted=true]");

    $(submittedForms).each(function() {
        notifyAccordionOfQuestionSubmission($(this).parent().attr("data-slick-index"));
    });
    updateProgressBar();
}

function assignShowSubmitDialogToButton(){
    $("#submitAllBtn").click(function() {
        var arrayOfUnansweredQuestions = getTitlesOfNonSubmittedQuestions();
        if (arrayOfUnansweredQuestions.length > 0) {
            var listOnModal = $("#listOfUnansweredQuestions");
            var str = '<p>Following questions are still not submitted:</p><ul>';
            $(arrayOfUnansweredQuestions).each(function() {
                str+= "<li>" + this + "</li>";
            });
            str += '</ul>';
            $(listOnModal).html(str);
        }

        $("#areyousure").modal();
    });
}

function validateForm(form) {
    if (form.text.value.length < 1) {
        return false
    }
}

function finishTestAttempt(){
    $("#areyousure").modal('hide');
    showLoading();
    var forms = document.getElementsByTagName("form");

    $(forms).each(function(){
        var form = this;
        if (validateForm(form)) {
            $(form).submit();
        }
    });

    $.ajax({
        type: "POST",
        url: ENDPOINTS.ATTEMPT_PREFIX + ENDPOINTS.ATTEMPT_COMPLETE,
        beforeSend: function (request) {
            request.setRequestHeader("X-CSRF-TOKEN", getCsrfTokenValue());
        },
        success: function() {
            window.location.replace(ENDPOINTS.ATTEMPT_PREFIX + ENDPOINTS.ATTEMPT_FINISH_PAGE);
        },
        error: function (request, status, error) {
            console.error(error);
            buildDangerAlert(error);
            hideLoading();
        }
    });
}

function handleExpressionQuestion() {
    $('.js-answer-hook').change(function () {
        var answerInput = $('#' + $(this).data('insertInto'));
        var blank = $(this).data('blankNumber');
        var json;
        try {
            json = JSON.parse($(answerInput).val());
        } catch(e) {}
        var currentObject = json ? json : {};
        currentObject[blank] = $(this).val();
        answerInput.val(JSON.stringify(currentObject));
    });
}
