$(function() {
	$('#addressAutoButton').click(function() {
		$.ajax({
			url : "http://zipcoda.net/api",
			dataType : "jsonp",
			data : {zipcode : $('#destinationZipcode').val()},
		})

		.done(function(data) {
			console.log(data);
			console.dir(JSON.stringify(data));
			$("#destinationAddress").val(data.items[0].address);
		})

		.fail(function() {
			window.alert('正しい結果を得られませんでした。');
		});
	});
});