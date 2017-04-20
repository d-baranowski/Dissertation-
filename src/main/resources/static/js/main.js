$(document).ready(function () {
    var csrf_token = $('#csrf-token').attr('content');
    var header = "X-CSRF-TOKEN";

    setNotyDefaults();

    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, csrf_token);
    });


    $('#datepicker').datepicker({altFormat: "dd/mm/yy", dateFormat: "dd/mm/yy"});
    $('.js-search-select').select2();

    handleHelp();
});

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};

function setNotyDefaults() {
    $.noty.defaults = {
        layout: 'topRight',
        theme: 'metroui',
        type: 'information', // success, error, warning, information, notification
        text: '', // [string|html] can be HTML or STRING

        dismissQueue: true, // [boolean] If you want to use queue feature set this true
        force: true, // [boolean] adds notification to the beginning of queue when set to true
        maxVisible: 5, // [integer] you can set max visible notification count for dismissQueue true option,

        template: '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',

        timeout: 5000, // [integer|boolean] delay for closing event in milliseconds. Set false for sticky notifications
        progressBar: true, // [boolean] - displays a progress bar

        animation: {
            open: {height: 'toggle'}, // or Animate.css class names like: 'animated bounceInLeft'
            close: {height: 'toggle'}, // or Animate.css class names like: 'animated bounceOutLeft'
            easing: 'swing',
            speed: 500 // opening & closing animation speed
        },
        closeWith: ['click'], // ['click', 'button', 'hover', 'backdrop'] // backdrop click will close all notifications

        modal: false, // [boolean] if true adds an overlay
        killer: false, // [boolean] if true closes all notifications and shows itself

        callback: {
            onShow: function() {},
            afterShow: function() {},
            onClose: function() {},
            afterClose: function() {},
            onCloseClick: function() {},
        },

        buttons: false // [boolean|array] an array of buttons, for creating confirmation dialogs.
    };
}


function messageSlideIn(message) {
    $("<footer />", { class:'navbar-fixed-bottom blueNavigation js-footer-help-slide-out', text:message, id: 'footer-msg' }).hide().prependTo("body").slideDown('fast')
}

function messageSlideOut() {
    $('.js-footer-help-slide-out').slideUp('fast',function() { $(this).remove(); })
}

function flashElementGreen(target) {
    $(target).animate({color: "#5CB85C"}).animate({color: '#000000'}, {duration: 800});
}

var notifyHelpHolderChanged = function() {
    flashElementGreen('#help-for-question-type');
};

function handleHelp() {
    messageSlideOut();
    $('.js-footer-help-slide-out').remove();
    var target = $('[data-help]');
    $(target).mouseover(function () {
        messageSlideIn(MY_HELP_DATA[$(this).data('help')])
    });
    $(target).mouseout(function () {
        messageSlideOut()
    });
    $('select[data-help-holder]').each(function () {
        var helpHolder = $('#' + $(this).data('helpHolder'));
        var currentSelect = $(this);

        $(currentSelect).unbind('change', notifyHelpHolderChanged);
        $(currentSelect).change(notifyHelpHolderChanged);

        $(helpHolder).unbind();
        $(helpHolder).mouseover(function () {
            var optionSelected = $("option:selected", currentSelect);
            var message = MY_HELP_DATA[$(optionSelected).data('help')];
            messageSlideIn(message)
        });
        $(helpHolder).mouseout(function () {
            messageSlideOut()
        });
    });
}

function showLoading() {
    $('#loading').show();
    $('')
}

function hideLoading() {
    $('#loading').fadeOut();
}

function updateQueryStringParameter(uri, key, value) {
    var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (uri.match(re)) {
        return uri.replace(re, '$1' + key + "=" + value + '$2');
    }
    else {
        return uri + separator + key + "=" + value;
    }
}

function getPatternFromText(pattern) {
    var questionText = $('#text').val();
    var result = questionText.match(pattern);
    return result ? result : [];
}

Array.prototype.diff = function(a) {
    return this.filter(function(i) {return a.indexOf(i) < 0;});
};

function enableFroalaOnTarget(target, additionalbuttons, contentChangedCallback) {
    $.FroalaEditor.DefineIcon('pretty-code', {NAME: 'hashtag'});
    $.FroalaEditor.RegisterCommand('pretty-code', {
        title: 'Prettify code',
        focus: false,
        undo: true,
        refreshAfterCallback: true,
        callback: function () {
            this.paragraphFormat.apply('PRE');
            this.paragraphStyle.apply('prettyprint');
            $('.prettyprinted').removeClass('prettyprinted');
            PR.prettyPrint();
        }
    });

    $.FroalaEditor.DefineIcon('add-blank', {NAME: 'my-brackets'});
    $.FroalaEditor.RegisterCommand('add-blank', {
        title: 'Add blank for answer',
        focus: true,
        undo: true,
        refreshAfterCallback: true,
        callback: function () {
            var numbers = ['[[1]]','[[2]]','[[3]]','[[4]]','[[5]]','[[6]]','[[7]]','[[8]]','[[9]]'];

            var patt = /\[\[\d]]/g;
            var currentBlanks = getPatternFromText(patt);
            var blank =  numbers.diff(currentBlanks)[0] ? ' ' + numbers.diff(currentBlanks)[0] : '';

            this.html.insert(blank);
            this.undo.saveStep();
        }
    });

    $.FroalaEditor.DefineIcon('add-letter', {NAME: 'my-letters'});
    $.FroalaEditor.RegisterCommand('add-letter', {
        title: 'Add multiple-choice option',
        focus: true,
        undo: true,
        refreshAfterCallback: true,
        callback: function () {
            var letters = ['A)', 'B)', 'C)', 'D)', 'E)', 'F)', 'G)', 'H)', 'I)', 'J)', 'K)', 'L)', 'M)', 'N)', 'O)', 'P)', 'Q)', 'R)', 'S)', 'T)', 'U)', 'V)', 'W)', 'X)', 'Y)', 'Z)'];

            var patt = /[A-Z]\)/g;
            var currentLetters = getPatternFromText(patt);

            var letter =  letters.diff(currentLetters)[0] ? ' ' + letters.diff(currentLetters)[0] : '';

            this.html.insert(letter);
            this.undo.saveStep();
        }
    });


    var buttons = ['bold', 'italic', 'underline', 'specialCharacters', 'strikeThrough', 'subscript', 'superscript', 'fontFamily', 'fontSize', 'pretty-code', '|', 'specialCharacters', 'color', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', '-', 'quote', 'insertHR', 'insertLink', 'insertImage',  '|', 'undo', 'redo', 'clearFormatting', 'selectAll', 'html', 'applyFormat', 'removeFormat', 'fullscreen' ];
    buttons = buttons.concat(additionalbuttons);

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $.ajaxPrefilter(function(options, originalOptions, jqXHR){
        if (options.type.toLowerCase() === "post") {
            // initialize `data` to empty string if it does not exist
            options.data = options.data || "";

            // add leading ampersand if `data` is non-empty
            options.data += options.data?"&":"";

            // add _token entry
            options.data += "_csrf=" + token;
        }
    });

    target.off('froalaEditor.initialized');
    target.on('froalaEditor.initialized', function (e, editor) {
        var old_enter = editor.cursor.enter;
        editor.cursor.enter = function (shift) {
            return old_enter(!shift);
        }
    }).froalaEditor({
        paragraphStyles: {
            'prettyprint lang-sql': 'SQL',
            'prettyprint': 'Code'
        },
        fontSizeDefaultSelection: '18',
        htmlRemoveTags: ['script','video','source','input','form','picture'],
        htmlAllowedTags: ["a", "abbr", "address", "area", "article", "aside", "b", "base", "bdi", "bdo", "blockquote", "br", "button", "caption", "cite", "code", "col", "colgroup", "datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt", "em", "fieldset", "figcaption", "figure", "footer", "form", "h1", "h2", "h3", "h4", "h5", "h6", "header", "hgroup", "hr", "i", "img", "ins", "kbd", "keygen", "label", "legend", "li", "main", "map", "mark", "menu", "menuitem", "meter", "nav", "object", "ol", "optgroup", "option", "output", "p", "param", "pre", "progress", "queue", "rp", "rt", "ruby", "s", "samp", "style", "section", "select", "small", "source", "span", "strike", "strong", "sub", "summary", "sup", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "u", "ul", "var", "wbr"],
        toolbarButtons: buttons,
        toolbarButtonsMD: buttons,
        toolbarButtonsSM: buttons,
        toolbarButtonsXS: buttons,
        pluginsEnabled: null,
        fontFamilySelection: true,
        fontSizeSelection: true,
        paragraphFormatSelection: true,
        tabSpaces: 4,
        imageUploadURL: '/upload/image',
        imageUploadParams: { '_csrf': token},
        imageUploadMethod: 'POST',
        imageMaxSize: 50 * 1024 * 1024,
        imageAllowedTypes: ['jpeg', 'jpg', 'png'],
        imageManagerLoadURL: '/upload/load_images'
    });

    target.off('froalaEditor.commands.after');
    target.on('froalaEditor.commands.after', function (e, editor, cmd, param1, param2) {
        if (param1 == 'prettyprint') {
            PR.prettyPrint();
        }
    });

    target.off('froalaEditor.keyup');
    target.on('froalaEditor.keyup', function (e, editor, keyupEvent) {
        $('.prettyprint').children().each(function () {
            editor.selection.save();
            $(this).replaceWith(function () {
                return $(this)[0].localName == 'br' ? $(this)[0].outerHTML : $(this).contents();
            });
            editor.selection.restore();
        });
        editor.selection.save();
        PR.prettyPrint();
        $('.prettyprinted').removeClass('prettyprinted');
        editor.selection.restore();
    });

    target.off('froalaEditor.contentChanged');
    target.on('froalaEditor.contentChanged', function (e, editor) {
        var html = editor.html.get();
        $('.'+$(this).data('paste-to')).val(html);
        if (contentChangedCallback) {
            contentChangedCallback()
        }
    });
}

function displayErrorMessages(errors) {
    errors.forEach(function(error) {
        var formGroup = $('#form-group-' + error.field);
        var field = $('#error-' + error.field);
        field.text(error.defaultMessage);
        formGroup.addClass('has-error')
    });
}

function hideErrorMessages() {
    $('.js-hook-error-msg').text('');
    $(' .js-hook-form-status').removeClass('has-danger');
}