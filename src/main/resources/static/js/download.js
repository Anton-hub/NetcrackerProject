$(document).ready(function () {

   $('#downloadBtn').on('click', function(event){
       event.preventDefault();
       fire_ajaxExp();
   });

});

function fire_ajaxExp() {

   var search = {};
   search["groupName"] =  $('#groupName').val();


   $('#downloadBtn').prop("disabled", true);

   $.ajax({
       type: "GET",
       contentType: "application/json",
       url: "/export/downloadXLS",
       data: search,
       cache: false,
       success: function () {
           window.open('http://localhost:8080/export/downloadXLS?groupName='+search["groupName"],'_blank' );
       }
   });
}