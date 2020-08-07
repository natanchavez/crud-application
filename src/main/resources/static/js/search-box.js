/**
 * This function is called when the page is being loaded and checks if inside the query string there’s a search
 * parameter, and if there is one then than value is inserted on the search field input in order to keep the searched
 * keyword across pages.
 */
$(window).on("load", function () {
	
	let inputSearch = $("#input-search-x");
	let searchButton = $("#search-btn-container");
	let removeFilterButton = $("#clear-btn-container");
	let queryString = location.search;
	let searchKeyword;
	
	if (queryString !== '') {
		searchKeyword = queryString.match(/([?&]search=)([^&]*)/)[2];
		
		if (searchKeyword !== '') {
			// inputSearch.val(searchKeyword);
			inputSearch.prop("disabled", true);
			$("#search-input-container-x").css("background", "#e3e3e3");
			// clearBtn.css("visibility", "visible");
			searchButton.css("display", "none")
			removeFilterButton.css("display", "block");
		}
	}
	
})

/**
 * If text is being input into the search field then an icon is enabled, and its functionality allows the user to
 * quickly delete all text inside that input field.
 */
$("#input-search-x").keyup(function () {
	let inputSearch = $(this);
	let clearBtn = $("#clear-btn-x");
	
	if (inputSearch.val().length > 0) {
		clearBtn.css("visibility", "visible");
	} else {
		clearBtn.css("visibility", "hidden");
	}
})

/**
 * If the specified icon is pressed, then all text inside the search field is deleted.
 */
$("#clear-btn-x").on("click", function () {
	let clearBtn = $(this);
	let inputSearch = $("#input-search-x");
	
	inputSearch.val("");
	clearBtn.css("visibility", "hidden");
	inputSearch.prop("disabled", false)
})

/**
 * if this button is pressed then the current searched keyword is removed from the search field and from the query
 * string on the URL.
 */
$("#clear-btn-container").on("click", function () {
	let url_no_query_string = location.toString().replace(location.search, "");
	window.location.replace(url_no_query_string);
})