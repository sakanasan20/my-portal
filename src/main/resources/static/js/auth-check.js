let isRedirecting = false;
const OAUTH2_LOGIN_URL = '/oauth2/authorization/portal-client';

// 監控全域 AJAX 錯誤
function handleAjaxErrors() {
	$(document).ajaxError(function(event, jqxhr) {
		// console.error('AJAX error:', jqxhr);
		const isHtml = jqxhr.getResponseHeader("Content-Type")?.includes("text/html");
		if (!isRedirecting &&
		    (jqxhr.status === 401 || (isHtml && responseText?.includes('/login')))) {
			isRedirecting = true;
			setTimeout(() => {
				window.location.href = OAUTH2_LOGIN_URL;
			}, 200);
		}
	});
}

// 定期檢查 auth 是否有效
function startAuthHeartbeat() {
	setInterval(() => {
		$.ajax({
			url: '/profile', // ← 換成你系統中實際的受保護資源
			method: 'GET'
		});
	}, 5 * 60 * 1000); // 每 5 分鐘一次
}

// 初始化
$(document).ready(() => {
	handleAjaxErrors();
	startAuthHeartbeat();
});
