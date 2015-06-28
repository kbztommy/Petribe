$(document).ready(function() {
    $('#myAffix').affix({
        offset: {
            top: 100,
            bottom: function() {
                return (this.bottom = $('.footer').outerHeight(true))
            }
        }
    })//end affix
}); //end ready
