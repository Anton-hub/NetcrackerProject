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
        // timeout: 600000,
        success: function (data) {
            var i = 0;
            var arrKey = [];
            var arrValue = [];
            for ( var key in data.result ) {
                arrKey[i] = key;
                let value = data.result[key];
                arrValue[i] = value;
                console.log( 'ключ: ' + key + ', значение: ' + value );
                i = i+1;
            }
            // var json = "<h4>Самые популярные сообщества среди ваших подписчиков</h4><pre>"
            //     + JSON.stringify(data.result, null, 4) + "</pre>";
            // $('#getResultDiv').html(json);


            const table = document.getElementById('tbody');

            for (let i = 0; i <  Object.keys( data.result ).length; i++) {
                let row = document.createElement('tr');
                row.innerHTML = `<td>${i+1}</td><td>${arrKey[i]}</td><td>${arrValue[i]}</td>`;
                table.appendChild(row);
            }
            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);
        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#getResultDiv').html(json);

            console.log("ERROR : ", e);
            $("#getResultDiv").prop("disabled", false);

        }
    });

}