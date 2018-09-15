/*
$(document).ready(function() {
    var oTable = document.getElementById('myTable');
    var edit = document.createElement("button");
    edit.setAttribute("class","btn btn-info btn-sm");
    edit.textContent = "Перевірити рейтинг";
    //gets rows of table
    var rowLength = oTable.rows.length;
    for (var i = 0; i < rowLength; i++){
        var cells = oTable.rows[i].getElementsByTagName('td');
           for(var j=0; j < cells.length; j++) {
               if (cells[j].id == "action") {
                   alert(cells[j].id);
                   cells[j].innerHTML=edit.outerHTML;
                   alert(edit.outerHTML);
               }
           }
    }
    
});
*/
