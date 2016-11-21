function successAlert(message) {
	$('#success-alert').html(message).slideDown();
	setTimeout(function(){
		$('#success-alert').slideUp().html("");
	}, 5000);
}

function errorAlert(message) {
	$('#error-alert').html(message).slideDown();
	setTimeout(function(){
		$('#error-alert').slideUp().html("");
	}, 5000);
}

$(document).on('click', '#logout-btn', function(e){
	e.preventDefault();
	$.ajax({
		url : '/logout',
		method : 'POST'
	}).done(function(e) {
		var host = $(location).attr('host');
		var url = 'http://' + host + '/login';
		window.location.replace(url);
	});
});