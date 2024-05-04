document.addEventListener('DOMContentLoaded', function(){
	function formatVND(price) {
		let formattedPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
		return formattedPrice = formattedPrice.replace('₫', 'VNĐ');
	}
	
	
	Array.from(document.getElementsByClassName('a-detail')).forEach(element => {
		element.addEventListener('click', function(){
			let url = element.getAttribute('href');
			fetch(url)
				.then(response => response.json())
				.then(cartItemList => {
					Array.from(document.getElementById('all-smaple-detail-div').children).forEach((element, index) => {
						if(index >0 ){
							document.getElementById('all-smaple-detail-div').removeChild(element);
						}
					});
					
					Array.from(cartItemList).forEach(cartItem => {
						let newDiv = document.createElement('div');
						newDiv.classList.add('form-group');
						newDiv.innerHTML = document.getElementById('sample-detail-div').innerHTML;
						
						newDiv.querySelector('input').value = cartItem.product.name + ', ' + cartItem.quantity 
							+ ', ' + cartItem.size + ', ' + formatVND(cartItem.costPrice);
						
						document.getElementById('all-smaple-detail-div').appendChild(newDiv);
					});
				})
		});
	});
});