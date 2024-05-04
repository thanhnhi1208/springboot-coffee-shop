document.addEventListener('DOMContentLoaded', function(){
	let imageFileAdd = document.getElementById('image-file-1');
	if(imageFileAdd != null){
		imageFileAdd.addEventListener('change', function(){
		  let file = imageFileAdd.files
		  if (file != null) {
			let image = document.getElementById('image-1');
		    image.src = URL.createObjectURL(file[0])
		  }
		});
	}
	
	
	let imageFileEdit = document.getElementById('image-file-2');
	if(imageFileEdit != null){
		imageFileEdit.addEventListener('change', function(){
		  let file = imageFileEdit.files
		  if (file != null) {
			let image = document.getElementById('image-2');
		    image.src = URL.createObjectURL(file[0])
		  }
		});
	}
	
	
	
});