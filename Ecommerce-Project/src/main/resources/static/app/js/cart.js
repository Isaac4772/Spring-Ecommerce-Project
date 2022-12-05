$(document).ready(function() {
	initializeLocalStorage()
	showCartCount()
	showCart()

	//update quantity on change
	$('tbody.cart-items').on('change', '.update-qty', function() {
		let idVal = $(this).data('id')
		let qtyVal = $(this).val()

		let cartItemList = localStorage.getItem('cart-item-data')
		let cartItemListObj = JSON.parse(cartItemList)
		console.log(cartItemListObj)

		let updateIndex = -1
		let updateProduct = ''
		$.each(cartItemListObj, function(i, prod) {
			if (prod.productId == idVal) {
				updateIndex = i;
				updateProduct = prod;
			}
		})

		//update

		updateProduct.qty = qtyVal
		console.log(updateIndex)
		console.log('updata')
		console.log(updateProduct)
		cartItemListObj[updateIndex] = updateProduct
		localStorage.setItem('cart-item-data', JSON.stringify(cartItemListObj))
		showCart()
	})

	//remove product from cart
	$('tbody.cart-items').on('click', '.btn-remove', function(event) {
		event.preventDefault()

		let idVal = $(this).data('id')

		let cartItemList = localStorage.getItem('cart-item-data')
		let cartItemListObj = JSON.parse(cartItemList)

		let deleteIndex = -1
		$.each(cartItemListObj, function(i, prod) {
			if (prod.productId == idVal) {
				deleteIndex = i;
			}
		})
		cartItemListObj.splice(deleteIndex, 1);
		localStorage.setItem('cart-item-data', JSON.stringify(cartItemListObj))

		showCartCount()
		showCart()
	})
	//add product to cart
	$('div.product').on('click', 'a.add-to-cart', function(event) {
		event.preventDefault();

		let id = $(this).data('id')
		let name = $(this).data('name')
		let price = $(this).data('price')

		console.log(id, name, price)

		let cartItemList = localStorage.getItem('cart-item-data')
		let cartItemListObj = JSON.parse(cartItemList)

		let flag = false
		let foundIndex = -1
		let oldProduct = ''
		$.each(cartItemListObj, function(i, product) {
			if (id == product.productId) {
				flag = true
				foundIndex = i
				oldProduct = product
			}
		})

		if (flag) { // old product
			oldProduct.qty += 1
			cartItemListObj[foundIndex] = oldProduct
		}
		else { // new product
			let newProduct = {
				productId: id,
				productName: name,
				productPrice: price,
				qty: 1
			}
			cartItemListObj.push(newProduct)
		}

		//Store array object to localstorage
		localStorage.setItem('cart-item-data', JSON.stringify(cartItemListObj))
		showCartCount()

	})

	function showCart() {
		let cartItemList = localStorage.getItem('cart-item-data')
		let cartItemListObj = JSON.parse(cartItemList)
		console.log(cartItemListObj)
		let html = ``
		let total = 0

		$.each(cartItemListObj, function(i, prod) {
			let subTotal = prod.productPrice * prod.qty
			total += subTotal
			let tr = `<tr class="text-center">
									<td class="product-remove"><a href="#" class="btn-remove" data-id="${prod.productId}"><span
											class="ion-ios-close"></span></a></td>

									<td class="product-name">
										<p>${prod.productName}</p>
									</td>

									<td class="price">${prod.productPrice}</td>

									<td class="quantity">
										<div class="input-group mb-3">
											<input type="number" name="quantity"
												class="quantity form-control input-number update-qty" value="${prod.qty}" data-id="${prod.productId}" min="1"
												max="100">
										</div>
									</td>

									<td class="total">${subTotal} ks.</td>
								</tr>`

			html += tr
		})

		$('tbody.cart-items').html(html);

		$('.sub-total').text(total + " ks.")
		$('.grand-total').text(total + " ks.")

		if (cartItemListObj.length == 0) {
			$('a.btn-checkout').removeAttr('href')
		}
	}

	function showCartCount() {
		let cartItemList = localStorage.getItem('cart-item-data')
		let cartItemListObj = JSON.parse(cartItemList)
		$('.cart-count').text(cartItemListObj.length)
	}

	function initializeLocalStorage() {
		let cartItemList = localStorage.getItem('cart-item-data')

		if (!cartItemList) {
			cartItemList = []
			localStorage.setItem('cart-item-data', JSON.stringify(cartItemList))
		}
	}

})