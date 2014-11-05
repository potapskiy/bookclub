$(document).ready(function() {
	$(function() {
		$("#town").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "./get_town_name/",
					type : "POST",
					data : {
						term : request.term
					},

					dataType : "json",

					success : function(data) {
						response(data);
					}
				});
			}
		});
	});

	$(function() {
		$("#town").blur(function() {
			$.ajax({
				url : './get_depart/',
				type : 'POST',
				data : {
					term : $("#town").val()
				}
			}).done(function(data) {
				$("#department").empty();
				var obj = jQuery.parseJSON(data);
				for ( var i = 0; i < obj.Departments.length; i++) {
					var o = new Option("option text", obj.Departments[i]);
					$(o).html(obj.Departments[i]);
					$("#department").append(o);
				}
				;
			});

		});
	});

	$(function() {
		$("#telephonet").blur(function() {
			$.ajax({
				url : './get_client_name/',
				type : 'POST',
				data : {
					term : this.value
				}
			}).done(function(data) {
				if (!isTelephoneCorrect){
					$("#error_text").show();
				}
				var obj = jQuery.parseJSON(data);
				$("#name_t").val(obj.name);
				$("#surn_t").val(obj.sname);
				
			});

		});
	});

	$(function() {
		$("#townt").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "./get_town_name/",
					type : "POST",
					data : {
						term : request.term
					},

					dataType : "json",

					success : function(data) {
						response(data);
					}
				});
			}
		});
	});

	$(function() {
		$("#townt").blur(function() {
			$.ajax({
				url : './get_depart/',
				type : 'POST',
				data : {
					term : $("#townt").val()
				}
			}).done(function(data) {
				$("#departmentt").empty();
				var obj = jQuery.parseJSON(data);
				for ( var i = 0; i < obj.Departments.length; i++) {
					var o = new Option("option text", obj.Departments[i]);
					$(o).html(obj.Departments[i]);
					$("#departmentt").append(o);
				}
				;
			});

		});
	});

	$(function() {
		$("#telephonett").blur(function() {
			$.ajax({
				url : './get_client_name/',
				type : 'POST',
				data : {
					term : this.value
				}
			}).done(function(data) {
				var obj = jQuery.parseJSON(data);
				$("#name_tt").val(obj.name);
				$("#surn_tt").val(obj.sname);

			});

		});
	});

	$(function() {
		$("#getPrice").click(function() {
			$.ajax({
				url : './get_price/',
				type : 'POST',
				data : {
					from_t : $("#town").val(),
					to_t : $("#townt").val(),
					weight : $("#weight_p").val(),
					type : $("#type_p").val()
				},

				dataType : "json"
			}).done(function(data) {
				console.log(data);
				var obj = jQuery.parseJSON(data);
				var s = obj.price;
				console.log(obj.price);
				if (obj.price === "ERROR") {
					$("#error_text").show();
					$("#price_p").val("");
					return false;
				} else {
					$("#price_p").val(s);
					$("#error_text").hide();
					return true;
				}

			});
		});
	});

	$(function() {
		$("#sendParcel").click(function() {
			var c = $("#getPrice").click();
			console.log("@@@@@@@ " + c);
			var price = $("#price_p").val();
			$.ajax({
				url : './save_parcel/',
				type : 'POST',
				data : {
					from_t : $("#town").val(),
					to_t : $("#townt").val(),
					dep_fr : $("#department").val(),
					dep_to : $("#departmentt").val(),
					weight : $("#weight_p").val(),
					type : $("#type_p").val(),
					tel_fr : $("#telephonet").val(),
					tel_to : $("#telephonett").val(),
					name_fr : $("#name_t").val(),
					name_to : $("#name_tt").val(),
					sname_fr : $("#surn_t").val(),
					sname_to : $("#surn_tt").val(),
					address : $("#address_tt").val(),
					price : $("price_p").val(),
				},

				dataType : "json"
			}).done(function(data) {
				console.log(data);
				var obj = jQuery.parseJSON(data);
				var s = obj.status;
				console.log(obj.status);
				if (obj.status == "ERROR") {
					$("#error_text").show();
					$("#price_p").val("");
					return false;
				} else {
					$("#error_text").hide();
					return true;
				}
			});

		});
	});

});

isTelephoneCorrect = function (tel) {
	var regExp = /^\d{9}$/.test(tel);
	return regExp;
}
