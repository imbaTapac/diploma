$.fn.pageMe = function(opts){
    var $this = this,
        defaults = {
            perPage: [],
            rowLength:0,
            showPrevNext: false,
            hidePageNumbers: false
        },
        settings = $.extend(defaults, opts);

    var listElement = $this;
    var perPage = settings.perPage;
    var rowLength = settings.rowLength;
    var children = listElement.children();
    var pager = $('.pager');

    if (typeof settings.childSelector!="undefined") {
        children = listElement.find(settings.childSelector);
    }

    if (typeof settings.pagerSelector!="undefined") {
        pager = $(settings.pagerSelector);
    }

    var numItems = children.length;
    var numPages = perPage.length;

    var forPage = 0;
    pager.data("curr",0);

    if (settings.showPrevNext){
        $('<li><a href="#" class="prev_link">«</a></li>').appendTo(pager);
    }

    var curr = 0;
    while(numPages > curr && (settings.hidePageNumbers==false)){
        $('<li><a href="#" class="page_link">'+(curr+1)+'</a></li>').appendTo(pager);
        curr++;
    }

    if (settings.showPrevNext){
        $('<li><a href="#" class="next_link">»</a></li>').appendTo(pager);
    }

    pager.find('.page_link:first').addClass('active');
    pager.find('.prev_link').hide();
    if (numPages<=1) {
        pager.find('.next_link').hide();
    }
    pager.children().eq(1).addClass("active");

    children.hide();
    children.slice(0, perPage[forPage]).show();

    pager.find('li .page_link').click(function(){
        var clickedPage = $(this).html().valueOf()-1;
        forPage = clickedPage;
        goTo(clickedPage,perPage[clickedPage]);
        return false;
    });
    pager.find('li .prev_link').click(function(){
        previous();
        return false;
    });
    pager.find('li .next_link').click(function(){
        next();
        return false;
    });

    function previous(){
        var goToPage = parseInt(pager.data("curr")) - 1;
        goTo(goToPage);
    }

    function next(){
        goToPage = parseInt(pager.data("curr")) + 1;
        goTo(goToPage);
    }

    function goTo(page){
        var startAt;
        var endOn;
        var sum = 0;
        if(page == 0){
            startAt = 0;
            endOn = startAt + perPage[page];
        }
        else {
            for(var i = 0; i<page; i++){
                sum = sum+perPage[i];
            }
            startAt = sum;
            endOn = startAt + perPage[page]
        }
        children.css('display','none').slice(startAt, endOn).show();

        if (page>=1) {
            pager.find('.prev_link').show();
        }
        else {
            pager.find('.prev_link').hide();
        }

        if (page<(numPages-1)) {
            pager.find('.next_link').show();
        }
        else {
            pager.find('.next_link').hide();
        }

        pager.data("curr",page);
        pager.children().removeClass("active");
        pager.children().eq(page+1).addClass("active");

    }
};

$(document).ready(function(){
    var itemsPerPage = blocksLength();
    var oTable = document.getElementById('myTable');
    var tableLenght = oTable.rows.length;
    $('#tBody').pageMe({pagerSelector:'#myPager',showPrevNext:true,hidePageNumbers:false,perPage:itemsPerPage,rowLength:tableLenght});
});

function blocksLength(){
    var oTable = document.getElementById('myTable');
    var rowLength = oTable.rows.length;
    var l = 0;
    var k = [];

    for (var i = 0; i < rowLength; i++){

        //skip headers
        if(oTable.rows[i].id=="block_header"){
            continue;
        }

        //gets Block name
        if(oTable.rows[i].children[0].id=="block_name"){
            if(l>0){
                k.push(l);
                l = 0;
            }
        }
        l++;
    }
    k.push(l);
    return k;
}

function getData1() {
    var oTable = document.getElementById('myTable');
    var blockLenght = document.getElementsByName('block_name').length;
    //gets rows of table
    var rowLength = oTable.rows.length;

    var blockCounter = 0;
    var subblockCounter = 0;
    var response = [];
    var prevValue = 0;

    for (var i = 0; i < rowLength; i++){

        //gets cells of current row
        var oCells = oTable.rows.item(i).cells;

        //gets amount of cells of current row
        var cellLength = oCells.length;

        //skip headers
        if(oTable.rows[i].id=="block_header"){
            continue;
        }

        //gets Block name
        if(oTable.rows[i].children[0].id=="block_name"){
            var name = oCells.item(0).innerHTML;
            response.push({
                "name":name,
                "subblock":[]
            });
            if(i>1){
                blockCounter++;
                subblockCounter = 0;
            }
        }

        if(cellLength==1 && oTable.rows[i].children[0].id!="block_name"){
            var subblock_name = oCells.item(0).innerHTML;
            response[blockCounter].subblock.push({
                "name":subblock_name,
                "paragraph":[]
            });
            if(response[blockCounter].subblock.length>1) {
                subblockCounter++;
            }
        }

        //loops through each cell in current row
        if(cellLength>1){
            var name = null;
            var score = null;
            for(var j = 1; j < cellLength-1; j++){
                // get your cell info here
                var cellVal = oCells.item(j).innerHTML;
                if(j==1){
                    this.name = cellVal;
                }
                if(j==2){
                    this.score = cellVal;
                }
            }
            response[blockCounter].subblock[subblockCounter].paragraph.push({
                "name":this.name,
                "score":this.score
            });
        }
    }

    $.ajax({
        url:'staticData',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data:JSON.stringify(response),
        success:function () {
            alert("succes");console.log(response);
        },
        error: function(){
            alert("error getting date");console.log(response);
        }
    });

    console.log(JSON.stringify(response));
    console.log(response);
}

function getData2() {
    var oTable = document.getElementById('myTable');
    var paragraphValue = document.getElementsByName("paragraphValue");

    var paragraph =[];

    //gets rows of table
    var rowLength = oTable.rows.length;

    var n=0;
    var index=1;

    for (var i = 0; i < rowLength; i++){

        //gets cells of current row
        var oCells = oTable.rows.item(i).cells;

        //gets amount of cells of current row
        var cellLength = oCells.length;

        if(cellLength>1 && i!=1 && n<paragraphValue.length){
            var comment = null;
            if(paragraphValue[n].type=="checkbox" && paragraphValue[n].checked==true){
                paragraph.push({
                    "paragraphId":index,
                    "score":paragraphValue[n].value,
                    "comment": comment
                });
            }
            if(paragraphValue[n].type=="text" && paragraphValue[n].value!=0){
                var sum=0;

                var oCellse = oTable.rows.item(i+1).cells;
                sum=paragraphValue[n].value*(oCellse.item(2).innerHTML);
                comment = oCellse.item(4).children[0].value;
                paragraph.push({
                    "paragraphId":index,
                    "score":Number((sum).toFixed(2)),
                    "comment": comment
                });
            }
            n++;
            index++;
        }
    }


    $.ajax({
        url:'/rating/save',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data:JSON.stringify(paragraph),
        success:function () {
            alert("Дякуємо!Рейтинг було успішно заповнено.");
            window.location.href="/welcome"
        },
        error: function(){
            alert("Дякуємо! Рейтинг було успішно заповнено.");
            window.location.href="/welcome"
        }
    });
}

	