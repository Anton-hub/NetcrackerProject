
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


            for (let i = 0; i <  data.listGroups.length; i++) {
                let row = document.createElement('tr');
                const group = data.listGroups[i];
                row.innerHTML = `<td>${i+1}</td><td><a href="https://vk.com/club${group.id}">${group.stringName}</a></td>`;
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
