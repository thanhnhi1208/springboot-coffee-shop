document.addEventListener('DOMContentLoaded', function(){
	
	Array.from(document.getElementsByClassName('delete')).forEach(button => {
		button.addEventListener('click', function(){
			let tensp = button.parentNode.parentNode.parentNode.querySelectorAll('td')[1].textContent;
			
			fetch('/customer/favorite/delete?tenSanPham='+tensp);
			
			let table = document.getElementById('table-favorite');
			let tr = button.parentNode.parentNode.parentNode;
			table.querySelector('tbody').removeChild(tr);
		});
		
	});
});