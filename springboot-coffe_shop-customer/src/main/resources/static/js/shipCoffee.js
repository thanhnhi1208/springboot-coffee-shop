document.addEventListener('DOMContentLoaded', function(){
	Array.from(document.getElementById('table-ship').querySelectorAll('tbody tr')).forEach((element, index, arr) => {
		let i = index +1;
		let nextRow = arr[i];
		if(nextRow != undefined && element.querySelector('td').textContent == nextRow.querySelector('td').textContent){
			while(nextRow != undefined){
				if(element.querySelector('td').textContent == nextRow.querySelector('td').textContent){
					nextRow.querySelector('td').textContent = '';
					nextRow.querySelector('#detailInfo').textContent = '';
					nextRow = arr[i +1];
					i++ ;
				}else {
					break;
				}
			}
			
		}

		if(index == arr.length-1){
			getBorderInTable();
		}
	});
	
	function getBorderInTable(){
		let array = document.getElementById('table-ship').querySelectorAll('tbody tr');
		let i=0;
		while(array[i +1] != undefined){
			let nextElement = array[i + 1];
			if((array[i].querySelector('td').textContent == '' && nextElement.querySelector('td').textContent != '')
				|| (array[i].querySelector('td').textContent !=  nextElement.querySelector('td').textContent  &&
					array[i].querySelector('td').textContent != '' &&  nextElement.querySelector('td').textContent != '')){
				array[i].style.borderBottom = '1px solid rgb(224, 228, 233)';
				console.log(array[i])
				
			}
			i++;
		}
	}
})