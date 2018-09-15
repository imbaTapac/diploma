$(document).ready(function () {
    /*var oTable = document.getElementById('myTable');
    var sum = document.getElementById('sum');
    var total = document.getElementById('total');
    var summary = 0;
    var rowLength = oTable.rows.length;
    for (var i = 0; i < rowLength; i++) {

        //gets cells of current row
        var oCells = oTable.rows.item(i).cells;

        //gets amount of cells of current row
        var cellLength = oCells.length;
        if (cellLength > 1 && i>1) {
            var score = oCells.item(3).innerHTML;
            alert(score);
            summary = summary + Number(score);

        }
    }
    alert(Number.parseFloat(summary));
    sum.innerText = summary;
    total.innerText = summary * 0.9;*/

    $(".reject-btn").click(function () {
        var reason = document.getElementById('reason');
        var data = {studentId: this.value, comment: reason.value};
        $.ajax({
            url: '/students_rating/reject',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                alert("Успішно");
                window.location.href = "/welcome";
            },
            error: function (response) {
                alert("Успішно");
                window.location.href = "/welcome";
            }
        });
    });

    $(".accept").click(function () {
        $.ajax({
            url: '/students_rating/accept',
            type: 'PUT',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: JSON.stringify(this.value),
            success: function () {
                alert("Успішно");
                window.location.href = "/welcome";
            },
            error: function () {
                alert("Успішно");
                window.location.href = "/welcome";
            }
        });
    });
});

function showDropdown() {
    var anchor = document.getElementsByClassName('rejection-dropdown');
    anchor[0].style.display = 'block';
}


