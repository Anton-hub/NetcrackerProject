$(document).ready(function () {

    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {

    var search = {}
    search["groupId"] = $("groupId").val();
    //search["email"] = $("#email").val();

    $("#btn-search").prop("disabled", true);

    $.ajax({
        url: '/findgroup',
        method: 'post',
        dataType: 'text',
        data: {text: $('#groupId').val()},
        success: function(data){
            alert(data);
        }
    });

}