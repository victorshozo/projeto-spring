$(document).ready(function(){
	$(document).on('click', '.add-more-farm', function(e){
		e.preventDefault();
        var farmLine = $(e.target).parent().parent();
        var newFarmLine = $(farmLine.clone()).appendTo($('.farm-box'));
        newFarmLine.find('input').val('').focus();
        $(e.target).removeClass('add-more-farm')
        	.removeClass('glyphicon-plus')
        	.addClass('remove-farm')
        	.addClass('glyphicon-minus');
	});
	
	$(document).on('click', '.remove-silo', function(e){
		$(e.target).parent().parent().remove();
	});
	
	$(document).on('submit', '#user-form', function(e){
		e.preventDefault();
		$.ajax({
			url : '/users',
			method : 'POST',
			data : $("#user-form").serialize(),
		}).done(function(e) {
			$('#user-form').trigger("reset");
			successAlert("Usuário cadastrado com sucesso!");
		}).fail(function(e){
			errorAlert('Preencha dados válidos');
		});
	});
	
	$(document).on('submit', '#user-edit-form', function(e){
		e.preventDefault();
		$.ajax({
			url : '/users/' + $('#user-id').val(),
			method : 'POST',
			data : $("#user-edit-form").serialize(),
		}).fail(function(e){
			errorAlert('Preencha dados válidos');
		});
	});
	
	$(document).on('click', '#delete-user', function(e){
		e.preventDefault();
		var userId = $(e.target).parent().parent().children().html();
		$.ajax({
			url : '/users/' + userId + '/delete',
			method : 'POST'
		}).done(function(e) {
			successAlert("Dados do usuário alterados com sucesso!");
			$(e.target).parent().parent().remove();
		}).fail(function(e){
			errorAlert('Preencha dados válidos');
		});
	});
});