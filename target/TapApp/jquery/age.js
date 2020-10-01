/*$(function () {
    $("#age").keyup(function()
     {
     console.log('abcd');
        let age = $('#age').val();
        if (age >= 18 && age <= 65) {
            $('#update').attr('disabled', true);
            $('#popover-age-top').addClass('hide');
        } else {
            $('#popover-age-top').removeClass('hide');
        }
    });
});*/
function agevalidate(){
 let age = document.getElementById('age').value;
 let update = document.getElementById('update');
 let popover = document.getElementById('popover-age-top');
 console.log('123');
        if (age >= 18 && age <= 65) {
        console.log('in if');
            popover.classList.add('hide');
        } else {
                console.log('in else');
                            update.disabled=true;
            popover.classList.remove('hide');
        }
}