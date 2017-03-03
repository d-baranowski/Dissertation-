$(document).ready(function(){
    startTimer();
});

function startTimer() {
    $.ajax({
                type: "GET",
                url: ENDPOINTS.ATTEMPT_PREFIX + ENDPOINTS.ATTEMPT_TIME_REMAINING,
                success: function(data) {
                    var duration = data * 60;
                    var timer = duration, minutes, seconds;
                    setInterval(function () {
                        minutes = parseInt(timer / 60, 10);
                        seconds = parseInt(timer % 60, 10);

                        minutes = minutes < 10 ? "0" + minutes : minutes;
                        seconds = seconds < 10 ? "0" + seconds : seconds;

                        $("#clockText").text(minutes + ":" + seconds);

                        if (--timer < 0) {
                            finishTestAttempt();
                            timer = duration;
                        }
                    }, 1000);
                },
                error: function (request, status, error) {
                    alert(request.responseText);
                }
            });
}