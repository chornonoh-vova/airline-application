// Get the modal
var registerModal = document.getElementById('regModal');
var loginModal = document.getElementById('logModal');

// Get the button that opens the modal
var registerButton = document.getElementById("regBtn");
var loginButton = document.getElementById('logBtn');

// Get the <span> element that closes the modal
var registerSpan = document.getElementsByClassName("close");
var loginSpan = document.getElementsByClassName("close");

// When the user clicks the button, open the modal 
registerButton.onclick = function() {
    registerModal.style.display = "block";
}
loginButton.onclick = function(){
	loginModal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
registerSpan.onclick = function() {
    registerModal.style.display = "none";
}
loginSpan.onclick = function(){
	loginModal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == registerModal) {
        registerModal.style.display = "none";
    }else if(event.target == loginModal){
        loginModal.style.display = "none";
    }
}