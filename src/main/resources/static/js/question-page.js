$(document).ready(function(){
    resizeOnPanelCollapse();
    setupCollapseExpandAllElementsButtons();
    updateAllCollapseExpandElementButtonsAppearance();
    $('#nextQuestion').click();
});

function setSlideHeightAuto() {
    $('.slick-list').each(function() {
      $(this).attr("style", "height: auto;");
    });
}

function refreshHeight() {
    $('.slick-slider').each(function() {
      $(this).slick("getSlick").refresh();
    });
}

function resizeOnPanelCollapse() {
    $('.collapsible').on('show.bs.collapse', function(e) {setSlideHeightAuto()});
    $('.collapsible').on('hide.bs.collapse', function (e) {setSlideHeightAuto()});
    $('.collapsible').on('shown.bs.collapse', function (e) {refreshHeight()});
    $('.collapsible').on('hidden.bs.collapse', function (e) {refreshHeight()});
}

var buttonTargets = {
    '#collapse-expand-all-correct-answers': '.panel-answer-section',
    '#collapse-expand-all-markers-comments': '.panel-comment-section',
    '#collapse-expand-all-candidate-answers': '.panel-candidate-answer'
}

function setupCollapseExpandAllElementsButtons() {
    $('.collapse-expand-element-button').click(function(event) {
        var button = '#' + event.delegateTarget.id;
        var target = buttonTargets[button];

        /* To compare a normal javascript object with a jQuery wrapper object it is necessary to select the first
           element of the jQuery object, which is the first javascript object matched by the selector. */
        if (event.delegateTarget === $(button)[0]) {
            var showOrHide = $(button).hasClass('expanded') ? 'hide' : 'show';
            $(target).collapse(showOrHide);
            $(event.delegateTarget).toggleClass('expanded');
            updateCollapseExpandElementButtonAppearance(button);
        }
    })
}

function updateCollapseExpandElementButtonAppearance(button) {
    if ( $(button).hasClass('expanded') ) {
        $(button + ' .plus').hide();
        $(button + ' .minus').show();
    }
    else {
        $(button + ' .plus').show();
        $(button + ' .minus').hide();
    }
}

function updateAllCollapseExpandElementButtonsAppearance() {
    for (button in buttonTargets) {
        if (buttonTargets.hasOwnProperty(button)) {
            updateCollapseExpandElementButtonAppearance(button);
        }
    }
}