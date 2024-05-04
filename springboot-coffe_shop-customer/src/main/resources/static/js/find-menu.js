document.addEventListener('DOMContentLoaded', function(){
	let input = document.getElementById('input-find-menu');
	input.addEventListener('input', function(){
		let allItem = document.getElementsByClassName('item-1');
		Array.from(allItem).forEach(item => {
			let productName = item.querySelector('.card-body h3').textContent.toLowerCase().trim();
			if(productName.includes(this.value.toLowerCase())){
				item.style.display = 'block';
			}else{
				item.style.display = 'none';
			}
		});
	});
});