$(document).ready(function () {
    //initializeClock();
});

function submitData() {
    var oTables = document.getElementsByTagName("table");

    var paragraphValue = document.getElementsByName("paragraphValue");
    var rating = [];

    var n = 0;
    var index = 1;

    for (var l = 0; l < oTables.length; l++) {
        var oTable = oTables[l];
        //gets rows of table
        var rowLength = oTable.rows.length;
        for (var i = 0; i < rowLength; i++) {

            //gets cells of current row
            var oCells = oTable.rows.item(i).cells;

            //gets amount of cells of current row
            var cellLength = oCells.length;

            if (cellLength > 1 && i !== 1 && n < paragraphValue.length) {
                var comment = null;
                if (paragraphValue[n].type === 'checkbox' && paragraphValue[n].checked === true) {
                    rating.push({
                        "paragraphId": index,
                        "score": paragraphValue[n].value,
                        "comment": comment
                    });
                }
                if (paragraphValue[n].type === 'text' && paragraphValue[n].value !== '') {
                    var sum = 0;
                    var oCellse = oTable.rows.item(i).cells;
                    sum = paragraphValue[n].value * (oCellse.item(2).innerHTML);
                    comment = oCellse.item(4).children[0].value;
                    rating.push({
                        "paragraphId": index,
                        "score": Number((sum).toFixed(2)),
                        "comment": comment
                    });
                }
                n++;
                index++;
            }
        }
    }

    if(rating.length === 0){
        alert("Ви не заповнили рейтинг!");
        return;
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/rating/save",
        data: JSON.stringify(rating),
        dataType: "json",
        success: function (responseData) {
            console.log(responseData);
            alert("Дякуємо! Рейтинг було успішно заповнено.");
            window.location.href = "/welcome"
        },
        error: function (responseData) {
            alert("Дублікати рейтингу заборонені!");
        }
    });
}

function getTimeRemaining() {
    var dayDeadline = 28;
    var date = new Date();
    date.setDate(dayDeadline);
    date.setHours(0, 0, 0, 0);
    var t = Date.parse(date) - Date.parse(new Date());
    var seconds = Math.floor((t / 1000) % 60);
    var minutes = Math.floor((t / 1000 / 60) % 60);
    var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
    var days = Math.floor(t / (1000 * 60 * 60 * 24));
    return {
        'total': t,
        'days': days,
        'hours': hours,
        'minutes': minutes,
        'seconds': seconds
    };
}

function initializeClock() {
    var clock = document.getElementById('clockdiv');
    var daysSpan = clock.querySelector('.days');
    var hoursSpan = clock.querySelector('.hours');
    var minutesSpan = clock.querySelector('.minutes');
    var secondsSpan = clock.querySelector('.seconds');

    function updateClock() {
        var t = getTimeRemaining();
        daysSpan.innerHTML = t.days;
        hoursSpan.innerHTML = t.hours;
        minutesSpan.innerHTML = t.minutes;
        secondsSpan.innerHTML = t.seconds;
        if (t.total <= 0) {
            clearInterval(timeInterval);
        }
    }
    this.updateClock();
    var timeInterval = setInterval(updateClock, 1000);
}

function updateClock() {
    var t = getTimeRemaining();
    daysSpan.innerHTML = t.days;
    hoursSpan.innerHTML = t.hours;
    minutesSpan.innerHTML = t.minutes;
    secondsSpan.innerHTML = t.seconds;
}

var currentSection = 0;

function handleSection(section) {
    if(section.href.endsWith('#')){
        section.href += section.id;
        return;
    }
    if (section.id === currentSection) {
        section.setAttribute("href", "#");
    }
    currentSection = section.id;
}