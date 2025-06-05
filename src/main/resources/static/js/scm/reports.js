// 每月訂單圖表
new Chart(document.getElementById("ordersChart"), {
	type: 'line',
	data: {
		labels: monthlyLabels,
		datasets: [{
			label: '訂單數',
			data: monthlyOrders,
			borderColor: '#0d6efd',
			backgroundColor: 'rgba(13, 110, 253, 0.2)',
			tension: 0.4
		}]
	},
	options: {
		responsive: true,
		plugins: { legend: { position: 'top' } }
	}
});

// 庫存分布圖表
new Chart(document.getElementById("inventoryChart"), {
	type: 'doughnut',
	data: {
		labels: inventoryLabels,
		datasets: [{
			data: inventoryData,
			backgroundColor: ['#0d6efd', '#6f42c1', '#198754', '#ffc107', '#dc3545']
		}]
	},
	options: {
		responsive: true,
		plugins: { legend: { position: 'bottom' } }
	}
});