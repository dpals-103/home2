function youtubePlugin() {
  toastui.Editor.codeBlockManager.setReplacer('youtube', youtubeId => {
    // Indentify multiple code blocks
    const wrapperId = `yt${Math.random().toString(36).substr(2, 10)}`;

    // Avoid sanitizing iframe tag
    setTimeout(renderYoutube.bind(null, wrapperId, youtubeId), 0);

    return `<div id="${wrapperId}"></div>`;
  });
}

function renderYoutube(wrapperId, youtubeId) {
  const el = document.querySelector(`#${wrapperId}`);

  el.innerHTML = `<iframe width="420" height="315" src="https://www.youtube.com/embed/${youtubeId}"></iframe>`;
}


function detail_body__init() {

	if (toastui === undefined) {
		return;
	}

	var body = document.querySelector('.detail_body');
	var initialValue = body.innerHTML.trim();

	const viewer = new toastui.Editor.factory({
		el: body,
		initialValue: initialValue,
		viewer: true,
        plugins: [toastui.Editor.plugin.codeSyntaxHighlight,youtubePlugin]
	});
}
detail_body__init();
