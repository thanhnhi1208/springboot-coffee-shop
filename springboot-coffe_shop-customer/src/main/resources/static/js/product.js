document.addEventListener('DOMContentLoaded', function(){
	let imageFileAdd = document.getElementById('image-file');
	imageFileAdd.addEventListener('change', function(){
	  let file = imageFileAdd.files
	  if (file != null) {
		let image = document.getElementById('image');
	    image.src = URL.createObjectURL(file[0])
	  }
	});
	
	let imageFileEdit = document.getElementById('image-file-edit');
	imageFileEdit.addEventListener('change', function(){
	  let file = imageFileEdit.files
	  if (file != null) {
		let image = document.getElementById('image-edit');
	    image.src = URL.createObjectURL(file[0])
	  }
	});
	
	let editButtons =  document.getElementsByClassName('editButton');
	Array.from(editButtons).forEach(editButton => {
		editButton.addEventListener('click', function(event){
			event.preventDefault();
			let href = editButton.getAttribute('href');
			$.get(href, function(product){
				document.getElementById('editName').value = product.name;
				document.getElementById('idEdit').value = product.id;
				document.getElementById('editCategory').value = product.category.id;
				document.getElementById('editPrice').value = product.price;
				document.getElementById('editSale').value = product.sale_price;
				document.getElementById('editDiscription').value = product.discription;
				document.getElementById('editQuantity').value = product.quantity;
				document.getElementById('image-edit').src = 'data:image/jpeg;base64,' + product.image;
			});
		});
	});
});