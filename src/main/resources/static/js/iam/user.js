// Load user BootstrapTable
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

// Reload role options when modal shown
$('#userModal').on('shown.bs.modal', function () {
    loadRoleOptions();
});

// Reset entered data and reload role options when modal hidden
$('#userModal').on('hidden.bs.modal', function () {
    $('#userForm')[0].reset();
    $('#roleIds').val('').change();
    loadRoleOptions();
});

// Submit user Modal
$('#userForm').on('submit', function(e) {
	e.preventDefault();
	const formData = $(this).serialize();
	$.post('/iam/rest/users', formData).done(function() {
		$('#userModal').modal('hide'); // 關閉 modal
		loadSpaContainer('/iam#userTab', function() {
			$('#userTable').bootstrapTable('refresh');
		});
	}).fail(function() {
		alert('新增失敗，請檢查資料');
	});
});

// Load role options
function loadRoleOptions() {
	$.ajax({
		url: '/iam/rest/roles',
		method: 'GET',
		success: function(roles) {
			const $select = $('#roleIds');
			$select.empty().append('<option value="">請選擇角色</option>');
            roles.forEach(role => {
                $select.append(`<option value="${role.id}">${role.name}</option>`);
            });
		},
		error: function() {
			alert('無法載入角色列表');
		}
	});
}
