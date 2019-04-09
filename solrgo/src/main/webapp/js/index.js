
var Solrgo = Solrgo || {} ;



Solrgo.redirect = (function() {
	var q = $("#q").val();
	if($.trim(q) == ''){
		return;
	}
	window.location.href = Solrgo.search_page + "?q=" + q;
});


Solrgo.init = (function() {
	Solrgo.search_page = 'http://' + window.location.host + "/solrgo/search.html";
	$("#q").focus();
	$('#search').click(Solrgo.redirect);
	$("#q").keydown(function() {
        if (event.keyCode == "13") {
        	Solrgo.redirect();
        }
    });
});

Solrgo.init();

