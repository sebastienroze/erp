/**
 * 
 */

var canClose = "";

RegisterRADFillExtra(function(json) {
		canClose = json.canclose;
});


if (window.addEventListener) {
    window.addEventListener('beforeunload', internalHandler, true);
} else if (window.attachEvent) {
    window.attachEvent('onbeforeunload', internalHandler);
}


function internalHandler(e) {
// Confirmation de fermer le navigateur	
    if (canClose != "") {
      e.preventDefault(); // required in some browsers
      e.returnValue = ""; // required in some browsers
      return canClose; // only works in old browsers
    }        
}

