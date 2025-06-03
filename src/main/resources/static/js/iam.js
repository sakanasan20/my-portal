$('#userTable').bootstrapTable({
	url: '/iam/users',
	columns: [
		{ field: 'id', title: 'ID' },
		{ field: 'username', title: '使用者名稱' },
		{ field: 'email', title: 'Email' },
		{ field: 'roles', title: '角色', formatter: rolesFormatter },
		{ field: 'enabled', title: '啟用狀態', formatter: enabledFormatter },
		{ field: 'actions', title: '操作', formatter: actionsFormatter }
	],
});

$('#roleTable').bootstrapTable({
	url: '/iam/roles',
	columns: [
		{ field: 'id', title: 'ID' },
		{ field: 'name', title: '角色名稱' },
		{ field: 'authorities', title: '擁有權限', formatter: authoritiesFormatter },
		{ field: 'actions', title: '操作', formatter: actionsFormatter }
	],
});

$('#authTable').bootstrapTable({
	url: '/iam/authorities',
	columns: [
		{ field: 'id', title: 'ID' },
		{ field: 'name', title: '權限名稱' },
		{ field: 'description', title: '描述' },
		{ field: 'actions', title: '操作', formatter: actionsFormatter }
	],
});

function rolesFormatter(roles) {
	if (!roles || roles.length === 0) return '-';
	return roles.map(role => `<span class="badge bg-info text-dark me-1">${role.name}</span>`).join('');
}

function authoritiesFormatter(authorities) {
	if (!authorities || authorities.length === 0) return '-';
	return authorities.map(authority => `<span class="badge bg-secondary me-1">${authority.name}</span>`).join('');
}

function enabledFormatter(value) {
	return value
		? '<span class="badge bg-success">啟用</span>'
		: '<span class="badge bg-secondary">停用</span>';
}

function actionsFormatter(_, row) {
	return `
		<div class="action-btns">
			<button class="btn btn-sm btn-primary" onclick="editFn(${row.id})">編輯</button>
			<button class="btn btn-sm btn-danger" onclick="deleteFn(${row.id})">刪除</button>
			<button class="btn btn-sm btn-warning" onclick="assignRoleFn(${row.id})">指派角色</button>
			<button class="btn btn-sm btn-warning" onclick="assignAuthorityFn(${row.id})">指派權限</button>
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

function assignRoleFn(id) {
	alert(`指派角色給 ID: ${id}`);
}

function assignAuthorityFn(id) {
	alert(`指派權限給 ID: ${id}`);
}