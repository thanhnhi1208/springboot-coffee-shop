document.addEventListener('DOMContentLoaded', function(){
	function formatVND(price) {
    	let formattedPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    	return formattedPrice = formattedPrice.replace('₫', 'VNĐ');
	}

    let sizeButton = document.querySelectorAll('.size button');
    
    Array.from(sizeButton).forEach(button => {
        button.addEventListener('click', function(){
            let elementHaveClassClick = document.getElementsByClassName('click-size');
            Array.from(elementHaveClassClick).forEach(element => {
                element.classList.remove('click-size');
            });
            
            button.classList.add('click-size');
        });
    });
    
    let buttonSmall =document.getElementById('button-small');
    buttonSmall.addEventListener('click', function(){
		document.getElementById('size').value= 'S';
		let price = document.getElementById('price');
		let priceSize = document.getElementById('price-size');
		price.value = parseFloat(priceSize.value);
		
		let priceSale = document.getElementById('price-sale');
		let priceSaleSize = document.getElementById('price-sale-size');
		if(parseFloat(priceSale.value) != 0){
			priceSale.value = parseFloat(priceSaleSize.value);
		}
		
		
		if(parseFloat(priceSaleSize.value) != 0){
			document.getElementById('display-price').textContent = formatVND(priceSale.value) ;
		}else{
			document.getElementById('display-price').textContent = formatVND(price.value);
		}
	});
	
	let buttonMedium =document.getElementById('button-medium');
    buttonMedium.addEventListener('click', function(){
		document.getElementById('size').value= 'M';
		let price = document.getElementById('price');
		let priceSize = document.getElementById('price-size');
		price.value = parseFloat(priceSize.value) + 5000;
		
		
		let priceSale = document.getElementById('price-sale');
		let priceSaleSize = document.getElementById('price-sale-size');
		if(parseFloat(priceSale.value) != 0){
			priceSale.value = parseFloat(priceSaleSize.value) + 5000;
		}
		
		
		
		if(parseFloat(priceSaleSize.value) != 0){
			document.getElementById('display-price').textContent = formatVND(priceSale.value) ;
		}else{
			document.getElementById('display-price').textContent = formatVND(price.value);
		}
	});
	
	let buttonLarge =document.getElementById('button-large');
    buttonLarge.addEventListener('click', function(){
		document.getElementById('size').value= 'L';
		let price = document.getElementById('price');
		let priceSize = document.getElementById('price-size');
		price.value = parseFloat(priceSize.value) + 10000;
		
		let priceSale = document.getElementById('price-sale');
		let priceSaleSize = document.getElementById('price-sale-size');		
		if(parseFloat(priceSale.value) != 0){
			priceSale.value = parseFloat(priceSaleSize.value) + 10000;
		}
		
		
		if(parseFloat(priceSaleSize.value) != 0){
			document.getElementById('display-price').textContent = formatVND(priceSale.value) ;
		}else{
			document.getElementById('display-price').textContent = formatVND(price.value);
		}
	});
});