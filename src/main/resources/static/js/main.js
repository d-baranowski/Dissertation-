$(document).ready(function () {
    bindNavigationBarToEndPoints();
    $(window).load(function() {
      $('#datepicker').datepicker();
    });
});

function bindNavigationBarToEndPoints() {
    $('[data-toggle=tab]').click(function(){
        switch($(this).attr('id')) {
            case 'navGenTest':
                window.location.href = ENDPOINTS.DASHBOARD_PREFIX + ENDPOINTS.DASHBOARD_GENERATE_TESTS;
                break;
            case 'navViewTest':
                window.location.href = ENDPOINTS.DASHBOARD_PREFIX + ENDPOINTS.DASHBOARD_VIEW_TESTS;
                break;
            case 'navTestLibrary':
                window.location.href = ENDPOINTS.PAPER_PREFIX + ENDPOINTS.PAPER_TEST_LIBRARY;
                break;
            case 'navSettings':
                window.location.href = ENDPOINTS.DASHBOARD_PREFIX + ENDPOINTS.DASHBOARD_SETTINGS;
                break;
            case 'logOut':
                window.location.href = ENDPOINTS.LOGIN_LOGOUT;
                break;
        }
    })
}