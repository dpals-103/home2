function detail_body__init() {

		if (toastui === undefined) {
			return;
		}

		var body = document.querySelector('.detail_body');
		var initialValue = body.innerHTML.trim();
		
		const viewer = new toastui.Editor.factory({
			el : body,
			initialValue : initialValue,
			viewer : true
		});
	}
	detail_body__init();
	