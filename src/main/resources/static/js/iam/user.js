$('#userTable').bootstrapTable({
	url: '/iam/rest/users',
	columns: [
		{ field: 'id', title: 'ID' },
		{ field: 'username', title: '使用者名稱' },
		{ field: 'email', title: 'Email' },
		{ field: 'roles', title: '角色', formatter: rolesFormatter },
		{ field: 'enabled', title: '啟用狀態', formatter: enabledFormatter },
		{ field: 'actions', title: '操作', formatter: actionsFormatter }
	],
});