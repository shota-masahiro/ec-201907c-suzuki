$(function(){
	$("#usePoint").on("change",function(){
		calc_price();
	});

	$('input[name="checkPoint"]:radio').on("change", function(){
		var check = $("#usePoint").prop('disabled');;
		if (check) {
			$("#usePoint").prop('disabled', false);
		} else {
			$("#usePoint").prop('disabled', true);
		}
	});
	
	$('input[name="paymentMethod"]:radio').change(function(){
		var creditDiv = document.getElementById("credit");
		var vals = $(this).val();
		if (vals == "2") {
			creditDiv.style.display = "block";
		} else {
			creditDiv.style.display = "none";
		}
	});
	
	function calc_price() {
		var inputMoney=$("#usePoint").val();
		var usePoint=$("#uses").val();
		var totalMoney=$("#total-money-price").val();
		if (usePoint >= inputMoney && inputMoney <= totalMoney) {
			var newPreTotalMoney = totalMoney - inputMoney;
			var newCalcMoney = newPreTotalMoney * 0.08;
			var newTotalMoney = newPreTotalMoney + newCalcMoney;
			var newPoint = usePoint - inputMoney;
			$("#total-price-calc").text(Math.floor(newCalcMoney) + "円");
			$("#total-price-money").text(Math.floor(newTotalMoney) + "円(税込)");
			$("#use").text(newPoint);
			console.log("成功!");
		} else {
			alert('ポイントの再入力をお願いします。');
		}
	}
});