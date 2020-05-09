//$(document).ready(function () {
//
//    $('#downloadBtn').on('click', function(event){
//        event.preventDefault();
//        fire_ajaxExp();
//    });
//
//});
//
//function fire_ajaxExp() {
//
//    var search = {};
//    search["groupName"] =  $('#groupName').val();;
//
//
//    $('#downloadBtn').prop("disabled", true);
//
//    $.ajax({
//        type: "POST",
//        contentType: "application/json",
//        url: "/export/downloadXLS",
//        data: JSON.stringify(search),
//        dataType: 'json',
//        cache: false,
//        success: function () {
//            console.log("Success");
//        }
//    });
//}