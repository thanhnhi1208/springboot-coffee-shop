document.addEventListener('DOMContentLoaded', function() {


	fetch('/admin/findRevenue')
		.then(response => response.json())
		.then(revenue => {
			var data = {
				labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12',],
				datasets: [{
					label: 'Doanh số bán hàng',
					data: [],
					backgroundColor: 'rgba(255, 165, 0, 0.5)', // Màu nền
					borderColor: 'rgba(75, 192, 192, 1)', // Màu đường viền
					borderWidth: 1 // Độ rộng đường viền
				}]
			};

			Array.from(revenue).forEach(element => {
				let monthIndex = element[0];
				let total = element[1];
				console.log(monthIndex)
				data.datasets[0].data[monthIndex - 1] = total;
			});

			// Lấy tham chiếu đến phần tử canvas
			var ctx = document.getElementById('myChart').getContext('2d');

			// Tạo biểu đồ
			var myChart = new Chart(ctx, {
				type: 'bar', // Loại biểu đồ (có thể thay đổi thành 'line', 'pie',...)
				data: data, // Dữ liệu đã chuẩn bị
				options: {
					scales: {
						y: {
							beginAtZero: true
						}
					}
				}
			});
		});



});