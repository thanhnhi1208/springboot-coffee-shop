document.addEventListener('DOMContentLoaded', function(){
	
	let imageFileEdit = document.getElementById('image-file-edit');
	imageFileEdit.addEventListener('change', function(){
	  let file = imageFileEdit.files
	  if (file != null) {
		let image = document.getElementById('image-edit');
	    image.src = URL.createObjectURL(file[0])
	  }
	});
});