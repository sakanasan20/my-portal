// 載入 SPA Container
const loadSpaContainer = function(urlWithHash, callback) {
	const [url, hash] = urlWithHash.split("#");

	$.get(url)
		.done(function(data) {
			$("#spa-container").html(data);

			// 等待 DOM 插入後處理 hash
			if (hash) {
				setTimeout(() => {
					const target = document.getElementById(hash) || document.querySelector(`[name='${hash}']`);

					if (target) {
						// 處理 Bootstrap tab 切換
						const tabTrigger = document.querySelector(`[data-bs-toggle="tab"][data-bs-target="#${hash}"]`);

						if (tabTrigger) {
							new bootstrap.Tab(tabTrigger).show();
						} else {
							// 若非 tab，用 scrollIntoView 處理一般錨點
							const target = document.getElementById(hash) || document.querySelector(`[name='${hash}']`);

							if (target) {
								target.scrollIntoView({ behavior: "smooth" });
							}
						}
					}
				}, 100);
			}

			// 呼叫 callback（如果有）
			if (typeof callback === "function") {
				callback();
			}
		})
		.fail(function() {
			$("#spa-container").html(
				"<div class='alert alert-danger'>無法載入內容，請稍後再試。<button class='btn btn-sm btn-link back-home-btn'>返回首頁</button></div></div>"
			);
		});
}

// 按下前往
$(document).ready(function() {
	$(".spa-link").on("click", function(e) {
		e.preventDefault();
		const url = $(this).attr("href");
		loadSpaContainer(url);
	});
});

// 按下返回首頁
$(document).off("click", ".back-home-btn").on("click", ".back-home-btn", function() {
	location.reload();
});