const articleList = [];

$.get(
	'article_list.json',
	{},
	function(data) {
		data.forEach((row) => {
		
			console.log(row);
			
			const article = {
				id : row.id,
				title: row.title,
				writer: row.extra__writer,
				count: row.count,
				likes: row.likesCount,
				regDate: row.regDate
			};
			articleList.push(article);
		});
	},
	'json'
);

const searchBox = new Vue({
	el: '#list-vue',
	data: {
		articleList: articleList,
		searchKeyWord: ''
	},
	methods: {
		searchKeyWordInputed: _.debounce(function(e) {
			this.searchKeyWord = e.target.value;
		}, 500)
	},
	computed: {
		filterKey: function() {
			return this.searchKeyWord.toLowerCase();
		},
		filtered: function() {
			if (this.filterKey.length == 0) {
				return null;
			}
			return this.articleList.filter((row) => {
				const keys = ['title', 'writer'];
				const match = keys.some((key) => {
					if (row[key].toLowerCase().indexOf(this.filterKey) > -1) {
						return true;
					}
				});
				return match;
			});
		}
	}
});