$('#authTable').bootstrapTable({
	url: '/iam/rest/authorities',
	columns: [
		{ field: 'id', title: 'ID' },
		{ field: 'name', title: '權限名稱' },
		{ field: 'description', title: '描述' },
		{ field: 'actions', title: '操作', formatter: actionsFormatter }
	],
});