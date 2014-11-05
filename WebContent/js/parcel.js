// JavaScript Document
isParcelIdCorrect = function(val) {
	var regExp = /^[0-9]+$/.test(val);
	return regExp;
};

validateLoadParcel = function() {
	var parcelId = document.getElementById("parcelIdField").value;
	var isEverythingCorrect = true;

	if (parcelId == null || parcelId == ""
			|| parcelId == "введіть номер посилки") {
		document.getElementById('loadParcelError').innerHTML = "порожній номер";
		document.getElementById('loadParcelSuccess').innerHTML = "";
		isEverythingCorrect = false;
	} else {
		if (isParcelIdCorrect(parcelId)) {
			document.getElementById('loadParcelError').innerHTML = "";
			document.getElementById('loadParcelSuccess').innerHTML = "";

		} else {
			document.getElementById('loadParcelSuccess').innerHTML = "";
			document.getElementById('loadParcelError').innerHTML = "некоректний номер (тільки цифри)";

			isEverythingCorrect = false;
		}

	}

	if (isEverythingCorrect) {
		document.forms['loadParcelForm'].submit();
	}

};
