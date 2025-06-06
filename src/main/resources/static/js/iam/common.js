function authoritiesFormatter(authorities) {
	if (!authorities || authorities.length === 0) return '-';
	return authorities.map(authority => `<span class="badge bg-secondary me-1">${authority.name}</span>`).join('');
}

function rolesFormatter(roles) {
	if (!roles || roles.length === 0) return '-';
	return roles.map(role => `<span class="badge bg-info text-dark me-1">${role.name}</span>`).join('');
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