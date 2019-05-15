$(document).ready(function () {
    $(".reject-btn").click(function () {
        var reason = document.getElementById('reason');
        var ratingsId = [];
        $('#table tr').each(function () {
            if ($(this).children('td').length > 1) {
                var ratingId = $(this).find("td").eq(1).html();
                var statusVal = $(this).find("#status").val();
                if (statusVal === 'decline') {
                    ratingsId.push(ratingId);
                }
            }
        });

        if(checkIfRatingExists(ratingsId) === true){
            return;
        }

        var data = {studentId: this.value, ratingsId: ratingsId, comment: reason.value};
        $.ajax({
            url: '/students-rating/reject',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                console.log(response);
                alert("Успішно");
                window.location.href = "/welcome";
            },
            error: function (response) {
                console.log(response);
                alert("Помилка");
                window.location.href = "/welcome";
            }
        });
    });

    $(".accept").click(function () {
        var ratingsId = [];
        $('#table tr').each(function () {
            if ($(this).children('td').length > 1) {
                var ratingId = $(this).find("td").eq(1).html();
                var statusVal = $(this).find("#status").val();
                if (statusVal === 'accept') {
                    ratingsId.push(ratingId);
                }
            }
        });

        if(checkIfRatingExists(ratingsId) === true){
            return;
        }

        var data = {studentId: this.value, ratingsId: ratingsId};
        console.log(data);
        $.ajax({
            url: '/students-rating/accept',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                console.log(response);
                alert("Успішно");
                window.location.href = "/welcome";
            },
            error: function (response) {
                console.log(response);
                alert("Помилка");
                window.location.href = "/welcome";
            }
        });
    });
});

function showDropdown() {
    var anchor = document.getElementsByClassName('rejection-dropdown');
    anchor[0].style.display = 'block';
}

function checkIfRatingExists(ratingsId) {
    if (ratingsId.length <= 0) {
        alert("Ви не вибрали жодного рейтингу для відхилення");
        return true;
    }
}

