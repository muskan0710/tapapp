$(document).ready(function() {

    $('#age').keyup(function() {
        let age = $('#age').val();
        if (age >= 18 && age <= 65) {
            $('#update').attr('disabled', true);
            $('#popover-age-top').addClass('hide');
        } else {
            $('#popover-age-top').removeClass('hide');
        }
    });

});