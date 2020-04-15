
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
            const statGender = data.baseStat.sexStat;
            const statCity = data.baseStat.cityStat;
            var arrCity = [];
            var arrNumber = [];
            var i = 0;
            for ( var key in statCity ) {
                arrCity[i] = key;
                let value = statCity[key];
                arrNumber[i] = value;
                i = i+1;
            }

            // Load the Visualization API and the corechart package.
            google.charts.load('current', {'packages':['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawCoreChart);

            // Callback that creates and populates a data table,
            // instantiates the pie chart, passes in the data and
            // draws it.

            function drawCoreChart() {


                // Set a callback to run when the Google Visualization API is loaded.

                // Create the data table.
                var dataGender = new google.visualization.DataTable();
                dataGender.addColumn('string', 'Gender');
                dataGender.addColumn('number', 'Number');
                dataGender.addRows([
                    ['Не указан', statGender[0]],
                    ['Женщин', statGender["Женщин"]],
                    ['Мужчин', statGender["Мужчин"]]
                ]);

                // Set chart options
                var options = {'title':'Пол аудитории',
                    is3D: true,
                    'width':400,
                    'height':300};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
                chart.draw(dataGender, options);
            }

            google.charts.load('current', {'packages':['columnchart']});
            google.charts.setOnLoadCallback(drawColumnChart);
            function drawColumnChart() {


                // Set a callback to run when the Google Visualization API is loaded.

                // Create the data table.
                // var dataCity = new google.visualization.DataTable();
                // dataCity.addColumn('string', 'Сiry');
                // dataCity.addColumn('number', 'Number');
                //
                // dataCity.addRows([
                //     [arrCity[0], arrNumber[0]],
                //     [arrCity[1], arrNumber[1]],
                //     [arrCity[2], arrNumber[2]],
                //     [arrCity[3], arrNumber[3]],
                //     [arrCity[4], arrNumber[4]],
                //     [arrCity[5], arrNumber[5]]
                // ]);
                var dataCity = google.visualization.arrayToDataTable([
                    ['Город', 'Количество'],
                    [arrCity[0], arrNumber[0]],
                    [arrCity[1], arrNumber[1]],
                    [arrCity[2], arrNumber[2]],
                    [arrCity[3], arrNumber[3]],
                    [arrCity[4], arrNumber[4]],
                    [arrCity[5], arrNumber[5]]
                ]);


                // Set chart options
                var options = {'title':'Города',
                    is3D: true,
                    colors: ['#4169E1','#FF8C00','#F08080', '#2E8B57', '#20B2AA', '#BA55D3'],
                    'width':400,
                    'height':300};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
                chart.draw(dataCity, options);
            }

            const table = document.getElementById('tbody');


            for (let i = 0; i <  100; i++) {
                let row = document.createElement('tr');
                const group = data.rangeList[i];
                row.innerHTML = `<td>${i+1}</td><td><a href="https://vk.com/club${group.id}">${group.stringName}</a></td><td>${group.subsList.length}</td>`;
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

}
