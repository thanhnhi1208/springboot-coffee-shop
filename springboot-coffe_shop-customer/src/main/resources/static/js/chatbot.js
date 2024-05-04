document.addEventListener('DOMContentLoaded', function(){
	let iconSend = document.getElementById('icon-send');
	if(iconSend != null){
		document.getElementById('icon-send').addEventListener('click', function(){
			let message = document.getElementById('message-customer').value;
			
			let divCustomer = document.createElement('div');
			divCustomer.classList.add('content-customer');
			divCustomer.innerHTML = document.getElementById('example-chat-customer').innerHTML;
			divCustomer.querySelector('.detail-you').textContent = message;
			document.getElementById('container-chat-bot').insertBefore(divCustomer, document.getElementById("container-send"));
			
			fetch('/customer/robot?speech='+message)
				.then(response => response.text())
				.then(responseText => {
					document.getElementById('message-customer').value = '';
					
					let div = document.createElement('div');
					div.classList.add('content-chat-bot');
					div.innerHTML = document.getElementById('example-chat').innerHTML;
					div.querySelector('.detail-chatbot').textContent = responseText;
					
					document.getElementById('container-chat-bot').insertBefore(div, document.getElementById("container-send"));
				})
		});
	}
	
});