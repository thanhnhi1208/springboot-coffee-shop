document.addEventListener('DOMContentLoaded', function(){
	document.getElementById('a-send-otp').addEventListener('click', function(){
		let email = document.getElementById('email').value;
		if(email != '' && email.includes('@gmail.com')){
			console.log(email)
			let url = '/customer/forgot-password/sendOtpToEmail?email='+email;
			fetch(url);
		}
	});
});