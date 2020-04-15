$(document).ready(function () {

    $('#searchBtn').on('click', function(event){
        event.preventDefault();
        fire_ajax();
    });

});

function fire_ajax() {

    var search = {};
    search["groupName"] =  $('#groupName').val();;


    $('#searchBtn').prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/findgroup",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        success: function (data) {

            const table = document.getElementById('tbody');
            for(let i = 0; i < data.rangeList.length; i++){
                console.log(data.rangeList[i])
            }

            for (let i = 0; i <  100; i++) {
                let row = document.createElement('tr');
                const group = data.rangeList[i];
                row.innerHTML = `<td>${i+1}</td><td><a href="https://vk.com/club${group.id}">${group.stringName}</a></td><td>${group.subsList.length}</td>`;
                table.appendChild(row);
            }
            // var i = 0;
            // var arrKey = [];
            // var arrValue = [];
            // for ( var key in data.result ) {
            //     arrKey[i] = key;
            //     let value = data.result[key];
            //     arrValue[i] = value;
            //     console.log( 'ключ: ' + key + ', значение: ' + value );
            //     i = i+1;
            // }
            // var json = "<h4>Самые популярные сообщества среди ваших подписчиков</h4><pre>"
            //     + JSON.stringify(data.result, null, 4) + "</pre>";
            // $('#getResultDiv').html(json);

            // var group = JSON.parse(data.result);

            // for (x in myObj) {
            //     txt += "<tr><td>" + myObj[x].name + "</td></tr>";
            // }
            // var groups = JSON.parse(data.result);
            //
            // for (let i = 0; i <  Object.keys( data.result ).length; i++) {
            //     let row = document.createElement('tr');
            //     row.innerHTML = `<td>${i+1}</td><td>${arrKey[i]}</td><td>${arrValue[i]}</td>`;
            //     table.appendChild(row);
            // }
            console.log("SUCCESS : ", data);
            $('#getResultDiv').html(data.result);
            $("#btn-search").prop("disabled", false);
        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#getResultDiv").prop("disabled", false);

        }
    });

}