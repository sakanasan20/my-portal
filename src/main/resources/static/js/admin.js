const $table = $('#table')

$(function() {
	$('#table').bootstrapTable({
		url: '/admin/systems',
		idField: 'id',
		showColumns: true,
		columns: [
			{ field: 'ck', checkbox: true },
			{ field: 'name', title: '名稱' },
			{ field: 'description', title: '描述' },
			{ field: 'actions', title: '操作', formatter: actionsFormatter }
		],
		treeShowField: 'name',
		parentIdField: 'pid',
		onPostBody() {
			const columns = $table.bootstrapTable('getOptions').columns

			if (columns && columns[0][1].visible) {
				$table.treegrid({
					treeColumn: 1,
					initialState: 'collapsed',
					expanderExpandedClass: 'bi bi-caret-down-fill',
					expanderCollapsedClass: 'bi bi-caret-right-fill',
					onChange() {
						$table.bootstrapTable('resetView')
					}
				})
			}
		}
	})
})

function actionsFormatter(_, row) {
	const safeId = encodeURIComponent(String(row.id));
	return `
		<div class="action-btns">
			<button class="btn btn-sm btn-primary" onclick="editFn('${safeId}')">編輯</button>
			<button class="btn btn-sm btn-danger" onclick="deleteFn('${safeId}')">刪除</button>
		</div>
	`;
}

function editFn(encodedId) {
	const id = decodeURIComponent(encodedId);
	alert(`編輯 ID: ${id}`);
}

function deleteFn(encodedId) {
	const id = decodeURIComponent(encodedId);
	if (confirm(`確定要刪除 ID: ${id} 嗎？`)) {
		alert(`已刪除 ID: ${id}`);
	}
}
