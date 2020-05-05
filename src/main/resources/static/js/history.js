
$(document).ready(function () {

    $('#historyBtn').on('click', function(event){
        event.preventDefault();
        fire_ajax();
    });

});

function fire_ajax() {

    $('#historyBtn').prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/showhistory",
        dataType: 'json',
        cache: false,
        success: function (data) {

            const table = document.getElementById('tbody');


            const tableHead = document.getElementById('thead');
            let tableHtml = document.createElement('tr');
            tableHtml.innerHTML = ' <th scope="col">№</th>\n' +
                '            <th scope="col">Группа</th>\n';
            tableHead.appendChild(tableHtml);
            for (let i = 0; i <  data.length; i++) {
                console.log(data)
                let row = document.createElement('tr');
                const group = data[i];
                row.innerHTML = `<td>${i+1}</td><td><a href="https://vk.com/club${group.clubId}">${group.stringName}</a></td>`;
                table.appendChild(row);
            }
            console.log("SUCCESS : ", data);
            // $('#getResultDiv').html(data.result);
            // $("#btn-search").prop("disabled", false);
        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#getResultDiv").prop("disabled", false);

        }
    });
};
