document.addEventListener('DOMContentLoaded', function(){
	
	Array.from(document.getElementsByClassName('fa-heart')).forEach(element => {
		element.addEventListener('click', function(){
			let computedStyle = window.getComputedStyle(element);
			let color = computedStyle.getPropertyValue('color');
			if(color == 'rgb(169, 169, 169)'){
				element.style.color = 'yellow';
				let tenSanPham = element.parentNode.querySelector('h3').textContent;
				fetch('/customer/favorite/add?tenSanPham='+ tenSanPham);
			}else{
				element.style.color = 'darkgray';
				let tenSanPham = element.parentNode.querySelector('h3').textContent;
				fetch('/customer/favorite/delete?tenSanPham='+ tenSanPham);
			}
		});
		
		let tenSanPham = element.parentNode.querySelector('h3').textContent;
		fetch('/customer/favorite/findByCustomeAndProduct?tenSanPham='+ tenSanPham)
			.then(response=> response.json())
			.then(boolean => {
				if(boolean == true){
					console.log(element)
					element.style.color = 'yellow';
				}else{
					element.style.color = 'drakgray !important';
				}
			});
	});
	

});