document.addEventListener('DOMContentLoaded', function(){
	let editButtons =  document.getElementsByClassName('editButton');
	Array.from(editButtons).forEach(editButton => {
		editButton.addEventListener('click', function(event){
			event.preventDefault();
			let href = editButton.getAttribute('href');
			$.get(href, function(category){
				document.getElementById('nameEdit').value = category.name;
				document.getElementById('idEdit').value = category.id;
			});
		});
	});
});