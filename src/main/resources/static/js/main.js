$(document).ready(function () {
    var csrf_token = $('#csrf-token').attr('content');
    var header = "X-CSRF-TOKEN";

    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, csrf_token);
    });

    $(window).load(function() {
      $('#datepicker').datepicker({altFormat: "dd/mm/yy", dateFormat: "dd/mm/yy"});
    });
});

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};

function showLoading() {
    $('#loading').show();
}

function hideLoading() {
    $('#loading').hide();
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

function enableFroalaOnTarget(target, additionalbuttons) {
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
        tabSpaces: 4
    });

    target.on('froalaEditor.commands.after', function (e, editor, cmd, param1, param2) {
        if (param1 == 'prettyprint') {
            PR.prettyPrint();
        }
    });

    target.on('froalaEditor.contentChanged', function (e, editor) {
        editor.selection.save();
        PR.prettyPrint();
        $('.prettyprinted').removeClass('prettyprinted');
        editor.selection.restore();
        var html = editor.html.get();
        $('.'+$(this).data('paste-to')).val(html);
    });
}