document.addEventListener('DOMContentLoaded', function(){
	Array.from(document.getElementsByClassName('fa-heart')).forEach(async element => {
		element.addEventListener('click', async function(){
			let computedStyle = window.getComputedStyle(element);
			let color = computedStyle.getPropertyValue('color');
			if(color == 'rgb(169, 169, 169)'){
				element.style.color = 'yellow';
				let tenSanPham = element.parentNode.querySelector('h3').textContent;
				await fetch('/customer/favorite/add?tenSanPham='+ tenSanPham);
			}else{
				element.style.color = 'darkgray';
				let tenSanPham = element.parentNode.querySelector('h3').textContent;
				console.log(tenSanPham)
				fetch('/customer/favorite/delete?tenSanPham='+ tenSanPham);
			}
		});
		
		let tenSanPham = element.parentNode.querySelector('h3').textContent;
		await fetch('/customer/favorite/findByCustomeAndProduct?tenSanPham='+ tenSanPham)
			.then(response=> response.text())
			.then( boolean => {
				console.log(boolean)
				if(boolean == 'true'){
					console.log(tenSanPham)
					element.style.color = 'yellow';
				}else{
					element.style.color = 'drakgray !important';
				}
			});
	});
	

});