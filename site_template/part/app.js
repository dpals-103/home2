function detail_body__init() {

	if (toastui === undefined) {
		return;
	}

	var body = document.querySelector('.detail_body');
	var viewer = new toastui.Editor.factory({
		el: body, 
		initialValue: "# 안녕",
		viewer: true
	});
}

detail_body__init();

