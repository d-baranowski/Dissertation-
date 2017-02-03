$(document).ready(function(){
    changeImageOnAccordionEvents();
    enableCarousel();
    changeAccordionOnCarouselEvents();
    linkCarouselToAccordion();
    handleAccordionPanelClicks();
    $(window).load(function() {
        hideLoading();
    });
});

function showLoading() {
    $('#loading').show();
}

function hideLoading() {
    $('#loading').hide();
}

function enableCarousel() {
    $('.carousell').slick({
        adaptiveHeight: true,
        draggable: false,
        infinite: false,
        swipe: false,
        touchMove: false,
        speed: 0,
        prevArrow: $('#prevQuestion'),
        nextArrow: $('#nextQuestion')
    });
}

function changeAccordionOnCarouselEvents() {
    $('.carousell').on('beforeChange', function(event, slick, currentSlide, nextSlide){
        $("a[slickSlide="+currentSlide+"]").find(".panel-body").removeClass("accordion-active-question");
        $("a[slickSlide="+nextSlide+"]").find(".panel-body").addClass("accordion-active-question");

        var parentNext = $("a[slickSlide="+nextSlide+"]").parent();
        var parentCurrent = $("a[slickSlide="+currentSlide+"]").parent();

        if ($(parentCurrent).hasClass("panel-title") || $(parentNext).hasClass("panel-title")) {
            var collapsible = $(parentNext).parent().parent().find('.collapse');
            $(collapsible).collapse('show');
            $('.collapse').not(collapsible).collapse('hide');
        }
    });
}

function changeImageOnAccordionEvents() {
    var accordionPanelBodies = $('.collapse');

    $(accordionPanelBodies).each(function() {
        var currentCollapsible = $(this);
        $(currentCollapsible).on('show.bs.collapse', function () {
            $(currentCollapsible).parent().find(".heading-decorator-holder img").attr("src", "/images/question_arrow_down.svg");
            $(currentCollapsible).parent().addClass("accordion-open");
        });
        $(currentCollapsible).on('hide.bs.collapse', function () {
            $(currentCollapsible).parent().find(".heading-decorator-holder img").attr("src", "/images/question_plus.svg");
            $(currentCollapsible).parent().removeClass("accordion-open");
        });
    });
}

function linkCarouselToAccordion() {
    var controlList = $(".slickControl a");
    $(controlList[1]).click();
    $.each(controlList, function( index, value) {
        $(value).attr('slickSlide', index);
        $(value).click(function() {
            $( '.carousell' ).slick('slickGoTo', index);
        });
    });
}

/*
   This function links panel titles to the action links inside them,
   so when user clicks anywhere on the panel it will still trigger the carousell.
   Note: To stop the click on myAnchorTag from triggering a click I need to return false.
   Otherwise it would result in an infinite loop of clicks.
*/
function handleAccordionPanelClicks() {
    var listOfPanelHeadings = $(".slickControl .panel-heading");

    $(listOfPanelHeadings).each(function() {
        var myAnchorTag = $(this).find("a")[0];

        $(myAnchorTag).click(function() {
            return false;
        });

        $(this).click(function() {
            $(myAnchorTag).click();
        });
    });
}

function buildSubmissionFailedAlert(data) {
    var reasons = "<ul>";
    $(data).each(function() {
        reasons = reasons + ('<li>' + this.defaultMessage + '</li>');
    });
    reasons = reasons + '</ul>';
    buildWarningAlert('Submission failed for following reasons:' + reasons);
}

