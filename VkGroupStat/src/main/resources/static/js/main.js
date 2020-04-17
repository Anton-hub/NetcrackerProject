
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
            const statAge = data.baseStat.ageStat;
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
                    // ['Не указан', statGender[0]],
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


                var dataCity = new google.visualization.DataTable();
                dataCity.addColumn('string', 'Сity');
                 dataCity.addColumn('number', 'Number');;
                // dataCity.addColumn({ role: "style" });

                dataCity.addRows([
                    [arrCity[0], arrNumber[0]],
                    [arrCity[1], arrNumber[1]],
                    [arrCity[2], arrNumber[2]],
                    [arrCity[3], arrNumber[3]],
                    [arrCity[4], arrNumber[4]],
                    [arrCity[5], arrNumber[5]]
                ]);
                // var dataCity = google.visualization.arrayToDataTable([
                //     ['Город', 'Количество'],
                //     [arrCity[0], arrNumber[0]],
                //     [arrCity[1], arrNumber[1]],
                //     [arrCity[2], arrNumber[2]],
                //     [arrCity[3], arrNumber[3]],
                //     [arrCity[4], arrNumber[4]],
                //     [arrCity[5], arrNumber[5]]
                // ]);

                // var dataCity = google.visualization.arrayToDataTable([
                //     ['Город', 'Количество'],
                //     ['arrCity[0]', arrNumber[0]],
                //     ['arrCity[1]', arrNumber[1]],
                //     ['arrCity[2]', arrNumber[2]],
                //     ['arrCity[3]', arrNumber[3]],
                //     ['arrCity[4]', arrNumber[4]],
                //     ['arrCity[5]', arrNumber[5]]
                // ]);

                // Set chart options
                var options = {'title':'Подписчики по городам',
                    is3D: true,
                    // slices: {
                    //     0: { color: '#20B2AA' },
                    //     1: { color: '#4169E1' },
                    //     2: { color: '#FF8C00' },
                    //     3: { color: '#F08080' },
                    //     4: { color: '#2E8B57' },
                    //     5: { color: '#BA55D3' },
                    //
                    // },
                    legend         : 'none',
                    colors:[['#FF8C00'],['#F08080'], ['#2E8B57'], ['#20B2AA'], ['#BA55D3'], ['#4169E1']],
                    'width':400,
                    'height':300};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
                chart.draw(dataCity, options);
            }

            // Load the Visualization API and the corechart package.
            google.charts.load('current', {'packages':['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawAgeChart);

            // Callback that creates and populates a data table,
            // instantiates the pie chart, passes in the data and
            // draws it.

            function drawAgeChart() {


                // Set a callback to run when the Google Visualization API is loaded.

                // Create the data table.
                var dataAge = new google.visualization.DataTable();
                dataAge.addColumn('string', 'Age');
                dataAge.addColumn('number', 'Number');
                dataAge.addRows([
                    // ['Не указан', statGender[0]],
                    ['менее 10', statAge["менее 10"]],
                    ['10 - 20', statAge["10 - 20"]],
                    ['20 - 30', statAge["20 - 30"]],
                    ['30 - 40', statAge["30 - 40"]],
                    ['40 - 50', statAge["40 - 50"]],
                    ['60 и более', statAge["60 и более"]],
                    ['Не указан', statAge["Не указан"]]
                ]);

                // Set chart options
                var options = {'title':'Возраст аудитории',
                    is3D: true,
                    'width':400,
                    'height':300};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('agechart_div'));
                chart.draw(dataAge, options);
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
