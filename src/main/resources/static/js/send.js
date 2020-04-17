
$(document).ready(function () {

    $('#sendBtn').on('click', function(event){
        event.preventDefault();
        sendMailToGetFeedback();
    });

});
function sendMailToGetFeedback(){
    var email = document.getElementById("email").value;
    var subject = document.getElementById("subject").value;
    var message = document.getElementById("message").value;

    $.ajax({
        url: '/api/sendEmail',
        type: 'POST',
        data: 'email=' + email + '&subject=' + subject + '&message=' + message,
        success: function (data) {
            alert(data);
        }
    });
}