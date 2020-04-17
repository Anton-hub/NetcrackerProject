$( document ).ready(function() {

    // SUBMIT FORM
    $("#customerForm").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });


    function ajaxPost(){

        // PREPARE FORM DATA
        var formData = {
            groupName : $("#groupName").val()
        }

        // DO POST
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url:  "/save",
            data: JSON.stringify(formData),
            dataType: 'json',
            success : function(result) {
                if(result == "Done"){
                    $("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" +
                        "Post Successfully! ");
                }else{
                    $("#postResultDiv").html("<strong>Error</strong>");
                }
                console.log(result);
            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });

        // Reset FormData after Posting
        resetData();

    }


    function resetData(){
        $("#groupName").val("");
    }
})

