/**
 * Created by Тарас on 03.06.2018.
 */
$(document).ready(function() {
    $(".check-btn").click(function() {
        $.ajax({
            url: '/check_rating/'+this.value,
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: function () {
                window.location = "/students_rating";
            },
            error: function () {
            }
        });
    });
});
