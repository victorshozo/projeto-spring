$(document).ready(function(){
	$(document).on('click', '.next-page', function(e){
		var userId = $('#user').val();
		var host = $(location).attr('host');
		var url = 'http://' + host + '/cadastro/silo/user/' + userId;
		window.location.replace(url);
	});
	
	$(document).on('submit', '#silo-form', function(e){
		e.preventDefault();
		$.ajax({
			url : '/silos',
			method : 'POST',
			data : $("#silo-form").serialize(),
		}).done(function(e) {
			$('#silo-form').trigger("reset");
			successAlert("Silo cadastrado com sucesso!");
		});
	});
	
	$(document).on('click', '.add-more-silo', function(e){
		e.preventDefault();
        var farmLine = $(e.target).parent().parent();
        var newFarmLine = $(farmLine.clone()).appendTo($('.farm-box'));
        newFarmLine.find('input').val('').focus();
        $(e.target).removeClass('add-more-farm')
        	.removeClass('glyphicon-plus')
        	.addClass('remove-farm')
        	.addClass('glyphicon-minus');
	});
	
	$(document).on('click', '.remove-farm', function(e){
		console.log($(e.target).parent().parent().remove());
	});
	
	$(document).on('submit', '#silo-edit-form', function(e){
		e.preventDefault();
		$.ajax({
			url : '/silos/' + $('#silo-id').val(),
			method : 'POST',
			data : $("#silo-edit-form").serialize(),
		}).done(function(e) {
			successAlert("Dados do silo alterados com sucesso!");
		});
	});
});

function deleteSilo(siloId){
	if(!confirm("Deseja realmente excluir esse silo?")){
		return;
	}
	var url = '/silos/' + siloId + '/delete'
	console.log(url);
	$.ajax({
		url : url,
		method : 'POST'
	}).done(function(e) {
		location.reload();
	}).fail(function(e){
		errorAlert("Esse silo não pode ser excluido, pois está em uso!");
	});
}