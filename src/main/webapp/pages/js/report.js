$(document).ready(function () {
    $('.anchor1').click(function () {
        $.ajax({
            url: "/statistic_report1", // json datasource
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            async: false,
            success: function (data) {
                $('#example1').DataTable({
                    data: data,
                    dom: 'Bfrtip',
                    buttons: [
                        {
                            extend: 'excel',
                            text: 'Імпорт в Excel'
                        },
                        {
                            extend: 'pdf',
                            text: 'Імпорт в PDF'
                        },
                        {
                            extend: 'print',
                            text: 'Друкувати'
                        }
                    ],
                    "columns": [
                        {data: "number"},
                        {data: "groupName"},
                        {data: "score"}
                    ],
                    "language": {
                        "lengthMenu": "Показати _MENU_ записів на сторінку",
                        "zeroRecords": "Нічого не знайдено - вибачте",
                        "info": "Поточна сторінка _PAGE_ з _PAGES_",
                        "infoEmpty": "No records available",
                        "infoFiltered": "(filtered from _MAX_ total records)",
                        "search": "Пошук",
                        "paginate": {
                            "first": "Перша",
                            "last": "Остання",
                            "next": "Наступна",
                            "previous": "Попередня"
                        }
                    }
                });
                var anchor = document.getElementsByClassName('statreport1-dropdown');
                anchor[0].style.display = 'block';
            },
            error: function (data) {
            }
        });
    });


    $('.anchor2').click(function () {
        $.ajax({
            url: "/statistic_report2", // json datasource
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            async: false,
            success: function (data) {
                $('#example2').DataTable({
                    data: data,
                    dom: 'Bfrtip',
                    buttons: [
                        {
                            extend: 'excel',
                            text: 'Імпорт в Excel'
                        },
                        {
                            extend: 'pdf',
                            text: 'Імпорт в PDF'
                        },
                        {
                            extend: 'print',
                            text: 'Друкувати'
                        }
                    ],
                    "columns": [
                        {data: "idStudent"},
                        {data: "studentName"},
                        {data: "studentSurname"},
                        {data: "groupName"},
                        {data: "blockName"},
                        {data: "subblockName"},
                        {data: "paragraphName"},
                        {data: "score"}
                    ],
                    "language": {
                        "lengthMenu": "Показати _MENU_ записів на сторінку",
                        "zeroRecords": "Нічого не знайдено - вибачте",
                        "info": "Поточна сторінка _PAGE_ з _PAGES_",
                        "infoEmpty": "No records available",
                        "infoFiltered": "(filtered from _MAX_ total records)",
                        "search": "Пошук",
                        "paginate": {
                            "first": "Перша",
                            "last": "Остання",
                            "next": "Наступна",
                            "previous": "Попередня"
                        }
                    }
                });
                var anchor = document.getElementsByClassName('statreport2-dropdown');
                anchor[0].style.display = 'block';
            },
            error: function (data) {
            }
        });
    });

});

