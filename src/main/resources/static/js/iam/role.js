$('#roleTable').bootstrapTable({
	url: '/iam/rest/roles',
	columns: [
		{ field: 'id', title: 'ID' },
		{ field: 'name', title: '角色名稱' },
		{ field: 'authorities', title: '擁有權限', formatter: authoritiesFormatter },
		{ field: 'actions', title: '操作', formatter: actionsFormatter }
	],
});