$(document).ready(function(){
	$(document).on('submit', '#password-edit-form', function(e){
		e.preventDefault();
		$.ajax({
			url : '/user/' + $('#user-id').val() + '/password',
			method : 'POST',
			data : $("#password-edit-form").serialize(),
		}).done(function(e) {
			$('#password-edit-form').trigger("reset");
			successAlert("Sua senha foi alterada com sucesso!");
		}).fail(function(e) {
			errorAlert(e.responseJSON.message);
		});
	});
});