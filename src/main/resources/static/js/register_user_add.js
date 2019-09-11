$(function(){
	$('#addressButton').click(function() {
		$.ajax({
			url : "http://zipcoda.net/api",
			dataType : "jsonp",
			data : {
				zipcode : $('#inputZipcode').val()
			},
		})
		
		.done(function(data) {
			console.log(data);
			console.dir(JSON.stringify(data));
			$("#inputAddress").val(data.items[0].address);
		})
		
		.fail(function() {
			window.alert('該当する郵便番号はありません。');
		});
	});
});