
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
            for (var key in statCity) {
                arrCity[i] = key;
                let value = statCity[key];
                arrNumber[i] = value;
                i = i + 1;
            }

            // Load the Visualization API and the corechart package.
            google.charts.load('current', {'packages': ['corechart']});

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
                var options = {
                    'title': 'Пол аудитории',
                    is3D: true,
                    pieHole: 0.2,
                    'width': 400,
                    'height': 300
                };

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
                chart.draw(dataGender, options);
            }

            google.charts.load('current', {'packages': ['corechart']});
            google.charts.setOnLoadCallback(drawColumnChart);

            function drawColumnChart() {


                // Set a callback to run when the Google Visualization API is loaded.


                var dataCity = new google.visualization.DataTable();
                dataCity.addColumn('string', 'Сity');
                dataCity.addColumn('number', 'Number');
                ;
                // dataCity.addColumn({ role: "style" });

                dataCity.addRows([
                    [arrCity[0], arrNumber[0]],
                    [arrCity[1], arrNumber[1]],
                    [arrCity[2], arrNumber[2]],
                    [arrCity[3], arrNumber[3]],
                    [arrCity[4], arrNumber[4]],
                    [arrCity[5], arrNumber[5]]
                ]);

                // Set chart options
                var options = {
                    'title': 'Подписчики по городам',
                    colors: ['#660BAB','#de1f1f','#0bacb8','#d3d60b','#d60b9d','#168235'],
                    is3D: true,
                    'width': 400,
                    'height': 300
                };


                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById("citychart_values"));
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
            var arrmodal = [];
            var statGenderModal = [];
            var statCityModal = [];
            var statAgeModal = [];
            var arrCityModal = [];
            var arrNumberModal = [];
            var dataAgeModal = [];
            var dataCityModal = [];
            var dataGenderModal= [];
            var chartModalAge = [];
            var chartModalCity = [];
            var chartModalGender = [];

            for (let i = 0; i <  20; i++) {
                const group = data.rangeList[i];
                arrmodal[i] =

                    '   <button class="btn btn-info btn-small"" data-toggle="modal" data-target="#Modal'+i+'">Показать</button>' +
                    '    <div class="modal fade bd-example-modal-lg" id="Modal'+i+'">' +
                    '    <div class="modal-dialog">' +
                    '     <div class="modal-content">' +
                    '     <div class="modal-header">' +
                    '     <h5 class="modal-title"> <b>Статистика сообщества ' + group.stringName +'</b> </h5>' +
                    '     <button class="close" data-dismiss="modal">×</button>' +
                    '     </div>' +
                    '     <div class="col-centered">'+
                    '     </div>' +
                    '<br>'+
                    '     <div class="modal-body">' +
                    '<div class="container-fluid">'+
                    '<div class="col-md-6">'+
                    '       <div id="chart_div_modal'+i+'"></div>'+
                    '       </div>'+

                    '<div class="col-md-6">'+
                    '       <div id="agechart_div_modal'+i+'"></div>'+
                    '</div>'+
                    '<div class="col-divider-margin-6"></div>'+
                    '<div class="col-md-6">'+

                    '       <div id="columnchart_values_modal'+i+'"></div>'+
                    '</div>'+
                    '</div>'+
                    '     </div>' +
                    '     </div>' +
                    // '     <div class="modal-footer">' +
                    // '     <button class="btn btn-primary " data-dismiss="modal">Закрыть</button     >' +
                    // '     </div>' +
                    '     </div>' +
                    '</div>'+
                    '    </div>' +



                    '</div>';

                statGenderModal[i] = group.statistics.sexStat;
                statCityModal[i] = group.statistics.cityStat;
                statAgeModal[i] = group.statistics.ageStat;
                //var sucks :)
                let CurrentGender = statGenderModal[i];
                let CurrentCity = statCityModal[i];
                let CurrentAge = statAgeModal[i];
                let j = 0;

                for ( let key in CurrentCity ) {
                    arrCity[j] = key;
                    let value = CurrentCity[key];
                    arrNumber[j] = value;
                    j = j+1;
                }

                // Load the Visualization API and the corechart package.
                google.charts.load('current', {'packages':['corechart']});

                // Set a callback to run when the Google Visualization API is loaded.
                google.charts.setOnLoadCallback(drawCoreChartModal);

                // Callback that creates and populates a data table,
                // instantiates the pie chart, passes in the data and
                // draws it.

                function drawCoreChartModal() {


                    // Set a callback to run when the Google Visualization API is loaded.

                    // Create the data table.
                    dataGenderModal[i] = new google.visualization.DataTable();
                    dataGenderModal[i].addColumn('string', 'Gender');
                    dataGenderModal[i].addColumn('number', 'Number');
                    console.log(CurrentGender);
                    dataGenderModal[i].addRows([
                        // ['Не указан', statGender[0]],
                        ['Женщин', CurrentGender["Женщин"]],
                        ['Мужчин', CurrentGender["Мужчин"]]
                    ]);

                    // Set chart options
                    var options = {'title':'Пол аудитории',
                        is3D: true,
                        legend: 'none',
                        'width':200,
                        'height':150};

                    // Instantiate and draw our chart, passing in some options.
                    chartModalGender[i] = new google.visualization.PieChart(document.getElementById('chart_div_modal'+i));
                    chartModalGender[i].draw(dataGenderModal[i], options);
                }

                google.charts.load('current', {'packages':['corechart']});
                google.charts.setOnLoadCallback(drawColumnChartModal);
                function drawColumnChartModal() {


                    // Set a callback to run when the Google Visualization API is loaded.


                    dataCityModal[i] = new google.visualization.DataTable();
                    dataCityModal[i].addColumn('string', 'Сity');
                    dataCityModal[i].addColumn('number', 'Number');;
                    // dataCity.addColumn({ role: "style" });

                    dataCityModal[i].addRows([
                        [arrCityModal[0], arrNumberModal[0]],
                        [arrCityModal[1], arrNumberModal[1]],
                        [arrCityModal[2], arrNumberModal[2]],
                        [arrCityModal[3], arrNumberModal[3]],
                        [arrCityModal[4], arrNumberModal[4]],
                        [arrCityModal[5], arrNumberModal[5]]
                    ]);


                    // Set chart options
                    var options = {'title':'Подписчики по городам',
                        is3D: true,

                        legend: 'none',

                        'width':200,
                        'height':150};

                    // Instantiate and draw our chart, passing in some options.
                    chartModalCity[i] = new google.visualization.PieChart(document.getElementById("columnchart_values_modal"+i));
                    chartModalCity[i].draw(dataCityModal[i], options);
                }

                // Load the Visualization API and the corechart package.
                google.charts.load('current', {'packages':['corechart']});

                // Set a callback to run when the Google Visualization API is loaded.
                google.charts.setOnLoadCallback(drawAgeChartModal);

                // Callback that creates and populates a data table,
                // instantiates the pie chart, passes in the data and
                // draws it.

                function drawAgeChartModal() {


                    // Set a callback to run when the Google Visualization API is loaded.

                    // Create the data table.
                    dataAgeModal[i] = new google.visualization.DataTable();
                    dataAgeModal[i].addColumn('string', 'Age');
                    dataAgeModal[i].addColumn('number', 'Number');
                    dataAgeModal[i].addRows([
                        // ['Не указан', statGender[0]],
                        ['менее 10', CurrentAge["менее 10"]],
                        ['10 - 20', CurrentAge["10 - 20"]],
                        ['20 - 30', CurrentAge["20 - 30"]],
                        ['30 - 40', CurrentAge["30 - 40"]],
                        ['40 - 50', CurrentAge["40 - 50"]],
                        ['60 и более', CurrentAge["60 и более"]],
                        ['Не указан', CurrentAge["Не указан"]]
                    ]);

                    // Set chart options
                    var options = {'title':'Возраст аудитории',
                        is3D: true,
                        legend: 'none',
                        'width':200,
                        'height':150};

                    // Instantiate and draw our chart, passing in some options.
                    chartModalAge[i] = new google.visualization.PieChart(document.getElementById('agechart_div_modal'+i));
                    chartModalAge[i].draw(dataAgeModal[i], options);
                }

                let row = document.createElement('tr');


                row.innerHTML = `<td>${i+1}</td><td><a href="https://vk.com/club${group.id}">${group.stringName}</a></td><td>${group.thisGroupSubsCount}</td><td>${group.targetSubsCount}</td><td>${arrmodal[i]}</td>`;
                table.appendChild(row);
                // let modal = document.createElement('tr');
                // modal.innerHTML = '' +
                // var modal = '<div class="container"><div class="row mt-3">' +
                //     '   <div class="col-12">' +
                //     '   <button class="btn btn-success btn-small"" data-toggle="modal" data-target="#firstModal">Показать</button>' +
                //     '    <div class="modal fade" id="firstModal">' +
                //     '    <div class="modal-dialog">' +
                //     '     <div class="modal-content">' +
                //     '     <div class="modal-header">' +
                //     '     <h5 class="modal-title">Название модального окна</h5>' +
                //     '     <button class="close" data-dismiss="modal">×</button>' +
                //     '     </div>' +
                //     '     <div class="modal-body">' +
                //     '     <p>Соображения высшего порядка, а также курс' +
                //     '     на социально-ориентированный проект обеспечивает актуальность' +
                //     '      позиций, занимаемых участниками в отношении поставленных задач?</p>' +
                //     '     </div>' +
                //     '     <div class="modal-footer">' +
                //     '     <button class="btn btn-primary " data-dismiss="modal">Закрыть</button     >' +
                //     '     </div>' +
                //     '     </div>' +
                //     '    </div>' +
                //     '    </div>' +
                //     '   </div>' +
                //     '  </div>' +
                //     '</div>';
                // table.appendChild(modal);
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
