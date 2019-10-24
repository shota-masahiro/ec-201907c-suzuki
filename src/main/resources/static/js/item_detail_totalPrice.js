$(function() {
	calc_price();
	$(".size").on("change",function(){
		calc_price();
	});
	$(".topping_checkbox").on("change",function() {
		calc_price();
	});
	$("#pizanum").on("change",function(){
		calc_price();
	});
	function calc_price() {
		var size=$(".size:checked").val();
		var toppingCount=$(".topping_checkbox:input:checkbox:checked").length;
		var pizanum=$("#pizanum option:selected").val();
		if (size=="M") {
			var size_price=$("#price_M").attr("value");
			var int_size_price=Number(size_price);
			var topping_price=toppingCount*200;
		} else {
			var size_price=$("#price_L").attr("value");
			var int_size_price=Number(size_price);
			var topping_price=toppingCount*300;
		}
		var price=(int_size_price+topping_price)*pizanum;
		$("#totalPrice").text(price.toLocaleString());
	}
});
