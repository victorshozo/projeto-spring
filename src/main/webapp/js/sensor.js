$(document).ready(function(){
	$(document).on('click', '.next-page', function(e){
		var userId = $('#user').val();
		var host = $(location).attr('host');
		var url = 'http://' + host + '/cadastro/sensor/user/' + userId;
		window.location.replace(url);
	});
	
	$(document).on('submit', '#sensor-form', function(e){
		e.preventDefault();
		$.ajax({
			url : '/sensores',
			method : 'POST',
			data : $("#sensor-form").serialize(),
		}).done(function(e) {
			$('#sensor-form').trigger("reset");
			successAlert("Sensores cadastrados com sucesso!");
		});
	});
});