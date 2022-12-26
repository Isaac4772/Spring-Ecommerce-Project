$(document).ready(function() {

	showOrderSummary();

	//place order
	$('div.cart-detail').on('click', '.btn-place-order', function(event) {
		event.preventDefault()


		// Receiver info Obj
		let receiverInfo = {
			name: $('#r-name').val(),
			email: $('#r-email').val(),
			phone: $('#r-phone').val(),
			address: $('#r-address').val()
		}

		// ordered Items
		let cartItemList = localStorage.getItem('cart-item-data')
		let cartItemListObj = JSON.parse(cartItemList)
		console.log(cartItemListObj)

		// order info
		let orderInfo = {
			receiver: receiverInfo,
			orderItems: cartItemListObj
		}

		//call server with ajax
		$.ajax({
			type: 'POST',
			beforeSend: function(xhr) {
				xhr.setRequestHeader('X-CSRF-Token', $('meta[name="csrf-token"]').attr('content'))
			},
			contentType: 'application/json',
			dataType: 'json',
			data: JSON.stringify(orderInfo),
			url: '/cart/place-order',
			success: function(result) {
				if (result = "") {
					alert("Something went wrong! Try again.")
				}
				else {
					localStorage.clear();
					alert("Your order has been sucessfully placed. Your order id is " + result + "\n Thank you very much.")
					window.location.href = '/'
				}

			},
			error: function(result) {
				alert('Something went wrong')
			}
		})
	})


	function showOrderSummary() {
		let cartItemList = localStorage.getItem("cart-item-data")
		let cartItemListObj = JSON.parse(cartItemList);
		let total = 0;
		console.log(cartItemListObj)
		$.each(cartItemListObj, function(i, prod) {
			let subTotal = prod.qty * prod.productPrice
			total += subTotal
		})

		$('.sub-total').text(total + "ks.")
		$('.grand-total').text(total + "ks.")
	}
})