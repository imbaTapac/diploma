$(document).ready(function () {
    $(".reject-btn").click(function () {
        var reason = document.getElementById('reason');
        var data = {studentId: this.value, comment: reason.value};
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
                alert("Помилка");
                console.log(response);
                window.location.href = "/welcome";
            }
        });
    });

    $(".accept").click(function () {
        $.ajax({
            url: 'students-rating/accept',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(this.value),
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


