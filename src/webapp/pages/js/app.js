/**
 * Created by Тарас on 28.02.2018.
 */

function doAjax() {

    var inputText = $("#input_str").val();

    $.ajax({
        url : 'getCharNum',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            text: inputText
        }),
        success: function (data) {
            var result = '"'+data.text+'", '+data.count+' characters';
            $("#result_text").text(result);
        }
    });
}

function getData() {
    var date1= $("#data1").val();
    var date2= $("#data2").val();
    alert(date1);
    alert(date2);
    $.ajax({
      url:'datediff',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data : ({
            date1: date1,date2:date2
        }),
        success:function (data) {
            alert("i got this dates "+ date1+" and "+date2);
            var result ='" The differense between this dates is '+data.difference+' years"';
            $("#result").text(result);
      }, 
        error: function(){
            alert("error getting date");
        }
    })
}

function getData1() {
    var oTable = document.getElementById('myTable');
    //gets rows of table
    var rowLength = oTable.rows.length;

    var block=document.getElementById('block_name').textContent;

    alert("ROW LENGHT="+rowLength);
    alert(block);
    var myArray = new Array();
    //loops through rows
    var k =0;
    myArray[k]=block;
    k++;
    for (var i = 0; i < rowLength; i++){

        //gets cells of current row
        var oCells = oTable.rows.item(i).cells;

        //gets amount of cells of current row
        var cellLength = oCells.length;

        //gets Block name
        if(cellLength==1){
            myArray[k]=oCells.item(0).innerHTML;
            k++;
        }

        //skip headers
        if(i==1){
            continue;
        }

        //loops through each cell in current row
        for(var j = 1; j < cellLength-1; j++){
            // get your cell info here
            var cellVal = oCells.item(j).innerHTML;
                 myArray[k]=cellVal;
                 k++;
        }

    }

    alert(myArray);
    $.ajax({
        url:'getData',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data:({
            /*name:JSON.stringify(myArray)*/
            block: {
                name: JSON.stringify(myArray[0]),
                subblock: [{
                    name: myArray[1].toString()
                },
                    {
                        paragraph: [{
                            name: myArray[2].toString(), score: myArray[3].toString()
                        }
                        ]
                    }
                ]
            }
        }),
        success:function () {
           console.log(JSON.stringify(data.subblock));
        },
        error: function(){
            console.log(JSON.stringify(data.subblock));
            alert("error getting date");
        }
    })


}