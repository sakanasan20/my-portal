$('#licenseTable').bootstrapTable({
	url: '/license/licenses',
	columns: [
		{ field: 'id', title: 'ID' },
		{ field: 'licenseKey', title: 'licenseKey' },
		{ field: 'issuedAt', title: 'issuedAt' },
		{ field: 'expiresAt', title: 'expiresAt'},
		{ field: 'userId', title: 'userId' },
		{ field: 'actions', title: '操作', formatter: actionsFormatter }
	],
});

function actionsFormatter(_, row) {
	return `
		<div class="action-btns">
			<button class="btn btn-sm btn-primary" onclick="editFn(${row.id})">編輯</button>
			<button class="btn btn-sm btn-danger" onclick="deleteFn(${row.id})">刪除</button>
		</div>
	`;
}

function editFn(id) {
	alert(`編輯 ID: ${id}`);
}

function deleteFn(id) {
	if (confirm(`確定要刪除 ID: ${id} 嗎？`)) {
		alert(`已刪除 ID: ${id}`);
	}
}
