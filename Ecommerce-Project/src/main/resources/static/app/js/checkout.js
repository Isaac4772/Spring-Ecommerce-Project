$(document).ready(function() {

	showOrderSummary();
	//alert("Hello")
	
	$('div.cart')
	function showOrderSummary() {
		let cartItemList = LocalocalStorage.getItems("cart-item-data")
		let cartItemListObj = JSON.parse(cartItemList);
		let total = 0;
		console.log(cartItemListObj)
		$.each(cartItemListObj, function(i, prod) {
			let subTotal = prod.qty * prod.productPrice
			total += subTotal
		})

		$('.sub-total').text(total)
		$('.grand-total').text(total)
	}
})