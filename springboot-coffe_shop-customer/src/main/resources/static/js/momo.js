document.addEventListener('DOMContentLoaded', function(){
	let currentUrl = window.location.href;
	if(currentUrl.includes('message=Successful')){
		let href =  currentUrl.replace(/shoppingCart/g, 'order/addFromMoMo');
		fetch(href);
		window.location.href = 'http://localhost:8081/customer/shipCoffee?thanks';
		
	}else if(currentUrl.includes('message=Giao')){
		window.location.href = 'http://localhost:8081/customer/shoppingCart';
	}
	
	if(currentUrl.includes('thanks')){
		document.getElementById('totalItem').textContent = 0;
	}
})