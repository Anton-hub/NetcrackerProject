
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
            const statGender = data.groupStat.sexStat;
            const statCity = data.groupStat.cityStat;
            const statAge = data.groupStat.ageStat;
            var arrCity = [];
            var arrNumber = [];
            var i = 0;
            for (var key in statCity) {
                arrCity[i] = key;
                let value = statCity[key];
                arrNumber[i] = value;
                i = i + 1;
            }

            var ctx = document.getElementById("myChart").getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'horizontalBar',
                data: {
                    labels: [arrCity[0], arrCity[1], arrCity[2], arrCity[3], arrCity[4], arrCity[5]],
                    datasets: [{
                        label: '# Количество',
                        data: [arrNumber[0], arrNumber[1], arrNumber[2], arrNumber[3], arrNumber[4], arrNumber[5]],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255,99,132,1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            });

            var ctxPA = document.getElementById("polarChart").getContext('2d');
            var myPolarChart = new Chart(ctxPA, {
                type: 'pie',
                data: {
                    labels: ['менее 10', '10 - 20', '20 - 30', '30 - 40', '40 - 50','60 и более','Не указан'],
                    datasets: [{
                        data: [statAge["менее 10"], statAge["10 - 20"], statAge["20 - 30"], statAge["30 - 40"], statAge["40 - 50"], statAge["60 и более"], statAge["Не указан"]],
                        backgroundColor: ["rgba(219, 0, 0, 0.1)", "rgba(0, 165, 2, 0.1)", "rgba(255, 195, 15, 0.2)",
                            "rgba(55, 59, 66, 0.1)", "rgba(0, 0, 0, 0.3)", "rgba(90, 50, 219, 0.8)", "rgba(30, 200, 219, 0.9)"
                        ],
                        hoverBackgroundColor: ["rgba(219, 0, 0, 0.2)", "rgba(0, 165, 2, 0.2)",
                            "rgba(255, 195, 15, 0.3)", "rgba(55, 59, 66, 0.1)", "rgba(0, 0, 0, 0.4)", "rgba(90, 50, 219, 0.9)", "rgba(30, 200, 219, 1)"
                        ]
                    }]
                },
                options: {
                    responsive: true
                }
            });
            var ctxP = document.getElementById("pieChart").getContext('2d');
            var myPieChart = new Chart(ctxP, {
                type: 'pie',
                data: {
                    labels: ["Женщин", "Мужчин"],
                    datasets: [{
                        data: [statGender["Женщин"], statGender["Мужчин"]],
                        backgroundColor: ["#F7464A", "#46BFBD"],
                        hoverBackgroundColor: ["#FF5A5E", "#5AD3D1"]
                    }]
                },
                options: {
                    responsive: true
                }
            });

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
                    '<div class="container-fluid">'+
                    '     <div class="modal-body">' +
                    '<div class="col-md-6 col-right">'+
                    '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Suspendisse et justo. Praesent mattis commodo augue. Aliquam ornare hendrerit consectetuer adipiscing elit. Suspendisse et justo. augue.+' +
                    'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Suspendisse et justo. Praesent mattis commodo augue. Aliquam ornare hendrerit consectetuer adipiscing elit.</p>'+
                    '     </div>' +
                    '<div class="col-md-6 col-left">'+
                    '<b>Пол аудитории</b>'+
                    '<canvas id="pieChart_modal'+i+'"style="padding: 10px"></canvas>'+
                    '     </div>' +
                    '<div class="col-divider-margin-3"></div>'+
                    '<div class="col-md-10 ">'+
                    '<b>Возраст аудитории</b>'+

                    '<canvas id="polarChart_modal'+i+'" style="padding: 10px"></canvas>'+
                    '       </div>'+
                    '<div class="col-divider-margin-3"></div>'+
                    '<div class="col-md-12 ">'+
                    '<b>Города проживания</b>'+
                    '<canvas id="myChart'+i+'" style="padding: 10px"></canvas>'+
                    '     </div>' +
                    '</div>'+


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

                let row = document.createElement('tr');
                row.innerHTML = `<td>${i+1}</td><td><a href="https://vk.com/club${group.id}">${group.stringName}</a></td><td>${group.thisGroupSubsCount}</td><td>${group.targetSubsCount}</td><td>${arrmodal[i]}</td>`;
                table.appendChild(row);
            }
            for (let i = 0; i <  20; i++) {
                const group = data.rangeList[i];
                statGenderModal[i] = group.statistics.sexStat;
                statCityModal[i] = group.statistics.cityStat;
                statAgeModal[i] = group.statistics.ageStat;

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
                chartModalGender[i] = document.getElementById("pieChart_modal"+i).getContext('2d');
                dataGenderModal[i] = new Chart(chartModalGender[i], {
                    type: 'pie',
                    data: {
                        labels: ["Женщин", "Мужчин"],
                        datasets: [{
                            data: [CurrentGender["Женщин"], CurrentGender["Мужчин"]],
                            backgroundColor: ["#F7464A", "#46BFBD"],
                            hoverBackgroundColor: ["#FF5A5E", "#5AD3D1"]
                        }]
                    },
                    options: {
                        legend: {
                            position: 'right'
                        },
                        responsive: true
                    }
                });

                chartModalAge[i] = document.getElementById("polarChart_modal"+i).getContext('2d');
                dataAgeModal[i] = new Chart(chartModalAge[i], {
                    type: 'pie',
                    data: {
                        labels: ['менее 10', '10 - 20', '20 - 30', '30 - 40', '40 - 50','60 и более','Не указан'],
                        datasets: [{
                            data: [CurrentAge["менее 10"], CurrentAge["10 - 20"], CurrentAge["20 - 30"], CurrentAge["30 - 40"], CurrentAge["40 - 50"], CurrentAge["60 и более"], CurrentAge["Не указан"]],
                            backgroundColor: ["rgba(219, 0, 0, 0.1)", "rgba(0, 165, 2, 0.1)", "rgba(255, 195, 15, 0.2)",
                                "rgba(55, 59, 66, 0.1)", "rgba(0, 0, 0, 0.3)", "rgba(90, 50, 219, 0.8)", "rgba(30, 200, 219, 0.9)"
                            ],
                            hoverBackgroundColor: ["rgba(219, 0, 0, 0.2)", "rgba(0, 165, 2, 0.2)",
                                "rgba(255, 195, 15, 0.3)", "rgba(55, 59, 66, 0.1)", "rgba(0, 0, 0, 0.4)", "rgba(90, 50, 219, 0.9)", "rgba(30, 200, 219, 1)"
                            ]
                        }]
                    },
                    options: {
                        responsive: true,
                        legend: {
                            position: 'right'
                        }
                    }
                });
                chartModalCity[i] = document.getElementById("myChart"+i).getContext('2d');
                dataCityModal[i] = new Chart(chartModalCity[i], {
                    type: 'horizontalBar',
                    data: {
                        labels: [arrCity[0], arrCity[1], arrCity[2], arrCity[3], arrCity[4], arrCity[5]],
                        datasets: [{
                            label: 'количество человек',
                            data: [arrNumber[0], arrNumber[1], arrNumber[2], arrNumber[3], arrNumber[4], arrNumber[5]],
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255,99,132,1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                        }
                    }
                });

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
