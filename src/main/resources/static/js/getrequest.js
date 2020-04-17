$( document ).ready(function() {

	// GET REQUEST
	$('#searchBtn').on('click', function(event){
		event.preventDefault();
		ajaxGet();
	});
	
	// DO GET
	function ajaxGet(){
		var result = [];
		$.ajax({
			type : "GET",
			url : window.location.replace('api/findGroup?groupName=' + $('#groupName').val()),
			success: function(data) {
				result = JSON.parse(JSON.stringify(data));
				$('#getResultDiv').append($.map(result, function (ignore, i) {
					return '<tr><td>' + result.groupName[i]+ '</td></tr>';
				}).join());
			},
			error: function(err) {
				alert(err);
			}

		});	
	}
});