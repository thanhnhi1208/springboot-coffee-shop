document.addEventListener('DOMContentLoaded', function() {
	function formatVND(price) {
		let formattedPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
		return formattedPrice = formattedPrice.replace('₫', 'VNĐ');
	}

	let decrease = document.getElementsByClassName('decrease');
	Array.from(decrease).forEach(button => {
		button.addEventListener('click', function(event) {
			let id = event.target.parentNode.parentNode.querySelector('#idOfCartItem').value;

			let quantity = event.target.parentNode.querySelector('span').textContent;
			if (quantity == 1) {
				fetch('/customer/shoppingCart/deleteCartItemById?id=' + id).then(response => response.json()).then(shoppingCart => {
					let price = shoppingCart.totalPrice;
					price = formatVND(price);
					document.getElementById('price').textContent = price;
					document.getElementById('totalPriceAll').textContent = formatVND(shoppingCart.totalPrice + 20000);

					let mechandiseqQuantity = document.getElementById('mechandiseqQuantity');
					mechandiseqQuantity.textContent = mechandiseqQuantity.textContent.replace('Số Lượng: ', '');
					mechandiseqQuantity.textContent = 'Số Lượng: ' + (parseInt(mechandiseqQuantity.textContent) - 1);

					let tr = event.target.parentNode.parentNode;
					tr.parentNode.removeChild(tr);

					document.getElementById('totalItem').textContent = shoppingCart.totalItem;
				});
			} else {
				fetch('/customer/shoppingCart/decreaseQuantity?id=' + id).then(response => response.json()).then(cartItem => {
					let buttonClick = event.target;
					buttonClick.parentNode.querySelector('span').textContent = cartItem.quantity;
					buttonClick.parentNode.parentNode.querySelector('#costPrice').textContent = formatVND(cartItem.costPrice);

					let mechandiseqQuantity = document.getElementById('mechandiseqQuantity');
					mechandiseqQuantity.textContent = mechandiseqQuantity.textContent.replace('Số Lượng: ', '');
					mechandiseqQuantity.textContent = 'Số Lượng: ' + (parseInt(mechandiseqQuantity.textContent) - 1);

					let price = cartItem.shoppingCart.totalPrice;
					price = formatVND(price);
					document.getElementById('price').textContent = price;
					document.getElementById('totalPriceAll').textContent = formatVND(cartItem.shoppingCart.totalPrice + 20000);

					document.getElementById('totalItem').textContent = cartItem.shoppingCart.totalItem;
				});
			}


		});
	});

	let increase = document.getElementsByClassName('increase');
	Array.from(increase).forEach(button => {
		button.addEventListener('click', function(event) {
			let id = event.target.parentNode.parentNode.querySelector('#idOfCartItem').value;
			fetch('/customer/shoppingCart/increaseQuantity?id=' + id).then(response => response.json()).then(cartItem => {
				let buttonClick = event.target;
				buttonClick.parentNode.querySelector('span').textContent = cartItem.quantity;
				buttonClick.parentNode.parentNode.querySelector('#costPrice').textContent = formatVND(cartItem.costPrice);

				let mechandiseqQuantity = document.getElementById('mechandiseqQuantity');
				mechandiseqQuantity.textContent = mechandiseqQuantity.textContent.replace('Số Lượng: ', '');
				mechandiseqQuantity.textContent = 'Số Lượng: ' + (parseInt(mechandiseqQuantity.textContent) + 1);

				let price = cartItem.shoppingCart.totalPrice;
				price = formatVND(price);
				document.getElementById('price').textContent = price;
				document.getElementById('totalPriceAll').textContent = formatVND(cartItem.shoppingCart.totalPrice + 20000);
			});
		});
	});

	/*dat hang*/
	document.getElementById('order-button').addEventListener('click', function(event) {
		event.preventDefault();

		fetch('/customer/findCurrentCustomer')
			.then(response => response.json())
			.then(customer => {
				if (customer.fullName == null || customer.fullName == '' || customer.phoneNumber == null ||
				 	customer.phoneNumber == '' || customer.address == null || customer.address == '' || customer.city == null ||
				  	customer.bankName == null || customer.bankname == '' ||
				  	customer.accountNumber == null || customer.accountNumber == '' ){
					   window.location.href = "http://localhost:8081/customer/profile";
			   }else{
				   document.getElementById('fullNameModal').value = customer.fullName;
				   document.getElementById('phoneNumberModal').value = customer.phoneNumber;
				   document.getElementById('addressModal').value = customer.address;
			   }
			   
			});
	});
	
	
});