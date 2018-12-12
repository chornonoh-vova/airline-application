// Get the modal
var registerModal = document.getElementById('regModal');
var loginModal = document.getElementById('logModal');

// Get the button that opens the modal
var registerButton = document.getElementById("signIn");
var loginButton = document.getElementById('logIn');

// Get the <span> element that closes the modal
var registerSpan = document.getElementsByClassName("regClose");
var loginSpan = document.getElementsByClassName("logclose");

// When the user clicks the button, open the modal 
registerButton.onclick = function() {
    registerModal.style.display = "block";
}
loginButton.onclick = function(){
	loginModal.style.display = "block";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == registerModal) {
        registerModal.style.display = "none";
    }else if(event.target == loginModal){
        loginModal.style.display = "none";
    }
}