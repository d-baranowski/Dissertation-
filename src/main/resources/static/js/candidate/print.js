$(document).ready(function(){
    setupAfterPrintListener();
});

function setupAfterPrintListener() {

    if (window.matchMedia) {
        var mediaQueryList = window.matchMedia('print');
        mediaQueryList.addListener(function(mql) {
            if (mql.matches) {
                beforePrint();
            } else {
                afterPrint();
            }
        });
    }

    // Run this manually before triggering print event, otherwise carousell is not 'unslicked' in time for print.
    window.onbeforeprint = beforePrint;
    window.onafterprint = afterPrint;

    $('#print-button').click(function() {
        window.print();
    });
}

function beforePrint() {
    console.log('Unslicking carousell class');
    var slickCurrentSlide = $('.carousell').slick('slickCurrentSlide');
    console.log('Current slide is: ' + slickCurrentSlide);
    $('.carousell').data('slickCurrentSlide', slickCurrentSlide);
    $('.carousell').slick('unslick');
    $('#progressbar').hide();
}

function afterPrint() {
    console.log('Slicking carousell class');
    var slickCurrentSlide = $('.carousell').data('slickCurrentSlide');
    enableCarousel();
    $('.carousell').slick('slickGoTo', slickCurrentSlide);
    $('#progressbar').show();
}