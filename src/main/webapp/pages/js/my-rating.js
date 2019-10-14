$(document).ready(function () {
    $('#month-picker').change(function () {
        var date = new Date(this.value);
        $.ajax({
            url: "/my-rating",
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            async: false,
            data: JSON.stringify(date),
            success: function (response) {
                createNewTableBody(response);
            },
            error: function (response) {
                console.log(response);
                alert("Помилка");
            }
        });
    });

    function createNewTableBody(response) {
        var table = document.querySelector('.table');
        if (table === null) {
            table = createNewTable();
        }
        var body = table.querySelector('tbody');
        var sum = document.getElementById("sum");
        var final = document.getElementById("final");
        var blockName = '';
        var subblockName = '';
        var score = 0.0;
        if (response !== 'undefined' && response.length > 0) {
            handleTableVisibility(table, sum, final, 1);
            body.innerHTML = '';
            var counter = 1;
            response.forEach(function (rating) {
                if (blockName !== rating.blockName) {
                    blockName = rating.blockName;
                    var blockTr = document.createElement('tr');
                    blockTr.className = "bg-tr-blue";
                    var blockTh = document.createElement('th');
                    setScopeAndSpanAttr(blockTh);
                    blockTh.innerHTML = rating.blockName;
                    appender(blockTr, blockTh);
                    appender(body, blockTr);
                }
                if (subblockName !== rating.subblockName) {
                    subblockName = rating.subblockName;
                    var subblockTr = document.createElement('tr');
                    subblockTr.className = "text-center";
                    var subblockTh = document.createElement('th');
                    setScopeAndSpanAttr(subblockTh);
                    subblockTh.innerHTML = rating.subblockName;
                    appender(subblockTr, subblockTh);
                    appender(body, subblockTr);
                }
                var tr = document.createElement('tr');
                var counterTd = document.createElement('td');
                counterTd.setAttribute("scope", "row");
                var nameTd = document.createElement('td');
                nameTd.className = "criteria";
                var dateTd = document.createElement('td');
                var scoreTd = document.createElement('td');
                var commentTd = document.createElement('td');
                var status = document.createElement('td');
                counterTd.innerHTML = counter;
                nameTd.innerHTML = rating.paragraphName;
                dateTd.innerHTML = rating.date;
                scoreTd.innerHTML = Number(rating.score).toFixed(2);
                commentTd.innerHTML = rating.comment;
                status = getRatingStatus(status,rating);
                var toAppend = [];
                toAppend.push(counterTd, nameTd, dateTd, scoreTd, commentTd, status);
                appender(tr, toAppend);
                appender(body, tr);
                score += rating.score;
                counter++;
            });
            setFinalScore(score);
        }
        else if (response.length === 0) {
            handleTableVisibility(table, sum, final, 2);
        }
    }

    function setScopeAndSpanAttr(column) {
        column.setAttribute("scope", "row");
        column.setAttribute("colspan", "6");
    }

    function appender(elem, toAppend) {
        if (toAppend instanceof Array && toAppend.length > 0) {
            toAppend.forEach(function (element) {
                elem.appendChild(element);
            });
        }
        else {
            elem.appendChild(toAppend);
        }
    }

    function setFinalScore(score) {
        if (typeof score === 'number') {
            var result = document.getElementById("result");
            var finalResult = document.getElementById("final-result");
            result.innerHTML = Number(score).toFixed(2);
            if (score < 0.0) {
                finalResult.innerHTML = '0';
            }
            else if (score > 10.0) {
                finalResult.innerHTML = '10';
            }
            else {
                finalResult.innerHTML = Number(score).toFixed(2);
            }
        }
    }

    function handleTableVisibility(table, sum, final, modifier) {
        if (modifier === 1) {
            table.style.display = 'table';
            sum.style.display = 'block';
            final.style.display = 'block';
        }
        else if (modifier === 2) {
            table.style.display = 'none';
            sum.style.display = 'none';
            final.style.display = 'none';
        }
    }

    function createNewTable() {
        var div = document.getElementById('table-wrapper');
        var tableDiv = document.createElement('div');
        var table = document.createElement('table');
        table.className = "table";
        table.innerHTML =
            "<thead>\n" +
            "  <tr>\n" +
            "   <th>#</th>\n" +
            "   <th class=\"criteria\">Критерій оцінки</th>\n" +
            "   <th>Дата</th>\n" +
            "   <th>Кількість балів</th>\n" +
            "   <th>Коментар</th>\n" +
            "   <th>Статус</th>\n" +
            "  </tr>\n" +
            "</thead>\n" +
            "<tbody></tbody>";
        var additionalInfo = document.createElement('div');
        additionalInfo.className = "row justify-content-center raiting_margin";
        additionalInfo.innerHTML =
            "<div id = \"sum\" class=\"col-3 text-center\">Сумарно балів -\n" +
            "   <span id=\"result\" class=\"bg-blue span-bal\">\n" +
            "   </span>\n" +
            "</div>\n" +
            "<div id = \"final\" class=\"col-3 text-center\">Кінцевий результат-  <span id=\"final-result\" class=\"bg-green span-bal\">\n" +
            " </span>\n" +
            "</div>";

        tableDiv.appendChild(table);
        div.appendChild(tableDiv);
        div.appendChild(additionalInfo);
        return table;
    }

    function getRatingStatus(status,rating){
        if(rating.status === -1){
            status.classList.add("text-dark", "status");
            status.innerHTML = "Відхилено";
            return status;
        }
        else if(rating.status === 0){
            status.classList.add("text-warning", "status");
            status.innerHTML = "Потребує перевірки старостою";
            return status;
        }
        else if(rating.status === 1){
            status.classList.add("text-warning","status");
            status.innerHTML = "Потребує перевірки головою СО";
            return status;
        }
        else if (rating.status === 2){
            status.classList.add("text-success","status");
            status.innerHTML = "Перевірено";
            return status;
        }
    }
});