// JavaScript Document


isTelephoneCorrect = function (tel) {
	var regExp = /^\d{9}$/.test(tel);
	return regExp;
}

validateForm = function() {

	var login = document.getElementById("loginField").value;
	var pass = document.getElementById("passField").value;
	var passConfirm = document.getElementById("passConfirmField").value;
	var firstName = document.getElementById("firstNameField").value;
	var lastName = document.getElementById("lastNameField").value;
	var isEverythingCorrect = true;

	if (login == null || login == "" || login == "введіть телефон") {
		document.getElementById('telephoneError').innerHTML = "телефон не може бути порожнім";
		isEverythingCorrect = false;
	} else {
		if (isTelephoneCorrect(login)) {
			document.getElementById('telephoneError').innerHTML = "";
		} else {
			document.getElementById('telephoneError').innerHTML = "некоректний телефон";
			isEverythingCorrect = false;
		}
	}

	if (pass == null || pass == "" || pass == "password") {
		document.getElementById('passError').innerHTML = "пароль не може бути порожнім";
		isEverythingCorrect = false;
	} else {
		document.getElementById('passError').innerHTML = "";
	}

	if (pass != passConfirm) {
		document.getElementById('passConfirmError').innerHTML = "паролі не співпадають";
		isEverythingCorrect = false;
	} else {
		document.getElementById('passConfirmError').innerHTML = "";
	}
	if (firstName == null || firstName == "" || firstName == "введіть ім я") {
		document.getElementById('firstNameError').innerHTML = "ім я не може бути порожнім";
		isEverythingCorrect = false;
	} else {
		document.getElementById('firstNameError').innerHTML = "";
	}
	if (lastName == null || lastName == "" || lastName == "введіть прізвище") {
		document.getElementById('lastNameError').innerHTML = "прізвище не може бути порожнім";
		isEverythingCorrect = false;
	} else {
		document.getElementById('lastNameError').innerHTML = "";
	}
	if (isEverythingCorrect) {		
		document.forms['registerForm'].submit();
	}
	return isEverythingCorrect;
}

validateEditForm = function() {

	var login = document.getElementById("loginField").value;
	var pass = document.getElementById("passField").value;
	var passConfirm = document.getElementById("passConfirmField").value;
	var firstName = document.getElementById("firstNameField").value;
	var lastName = document.getElementById("lastNameField").value;
	var isEverythingCorrect = true;

	if (login == null || login == "" || login == "введіть телефон") {
		document.getElementById('telephoneError').innerHTML = "телефон не може бути порожнім";
		isEverythingCorrect = false;
	} else {
		if (isTelephoneCorrect(login)) {
			document.getElementById('telephoneError').innerHTML = "";
		} else {
			document.getElementById('telephoneError').innerHTML = "некоректний телефон";
			isEverythingCorrect = false;
		}
	}

	if (pass!= null && pass != passConfirm) {
		document.getElementById('passConfirmError').innerHTML = "паролі не співпадають";
		isEverythingCorrect = false;
	} else {
		document.getElementById('passConfirmError').innerHTML = "";
	}
	if (firstName == null || firstName == "" || firstName == "введіть ім я") {
		document.getElementById('firstNameError').innerHTML = "ім я не може бути порожнім";
		isEverythingCorrect = false;
	} else {
		document.getElementById('firstNameError').innerHTML = "";
	}
	if (lastName == null || lastName == "" || lastName == "введіть прізвище") {
		document.getElementById('lastNameError').innerHTML = "прізвище не може бути порожнім";
		isEverythingCorrect = false;
	} else {
		document.getElementById('lastNameError').innerHTML = "";
	}
	if (isEverythingCorrect) {		
		document.forms['editProfileForm'].submit();
	}
	return isEverythingCorrect;

}


