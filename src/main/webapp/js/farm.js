$(document).ready(function(){
	$(document).on('submit', '#farm-edit-form', function(e){
		e.preventDefault();
		$.ajax({
			url : '/farms/' + $('#farm-id').val(),
			method : 'POST',
			data : $("#farm-edit-form").serialize(),
		}).done(function(e) {
			successAlert("Dados da fazenda alterados com sucesso!");
		});
	});
	
	$(document).on('submit', '#farm-form', function(e){
		e.preventDefault();
		$.ajax({
			url : '/cadastro/fazenda/user/' + $("#user-id").val(),
			method : 'POST',
			data : $("#farm-form").serialize(),
		}).done(function(e) {
			$('#farm-form').trigger("reset");
			successAlert("Fazenda cadastrada com sucesso!");
		});
	});
});