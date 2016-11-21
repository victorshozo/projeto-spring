$(document).ready(function(){
	$(document).on('submit', '#login-form', function(e){
		e.preventDefault();
		$.ajax({
			url : '/login',
			method : 'POST',
			data : $("#login-form").serialize(),
		}).done(function(e) {
			var host = $(location).attr('host');
			var url = 'http://' + host + '/home';
			window.location.replace(url);
		}).fail(function(e){
			errorAlert('Usuário e(ou) senha inválido(s)');
		});
	});
});