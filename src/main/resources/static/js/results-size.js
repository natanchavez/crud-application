/**
 * This function is called every time the user wants to display a different number of results on the table, the query
 * string on the URL is modified according to the new selected value and then a GET request is made with the new URL.
 */
$('#show-results').on('change', function () {
	let num_results = this.value.toString();
	let query_string = location.search;
	let url_no_query_string = location.toString().replace(location.search, "");
	let new_url
	
	if (query_string === '') {
		new_url = url_no_query_string + "?show=" + num_results;
		
	} else {
		let showQueryStringArray = query_string.match(/([?&]show=)([^&]*)/);
		
		if (showQueryStringArray === null) {
			new_url = query_string + "&show=" + num_results;
			
		} else {
			new_url = url_no_query_string + query_string.replace(/([?&]show=)([^&]*)/, "$1" + num_results);
			new_url = new_url.replace(/([?&]page=)([^&]*)/, "$1" + "1");
			new_url = new_url.replace("?&", "");
		}
	}
	
	window.location.replace(new_url);
})

