
var Solrgo = Solrgo || {} ;

Solrgo.getParam = (function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURI(r[2]);
	return null;
});

Solrgo.createSid = (function() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx'.replace(/[xy]/g, function(c) {
    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
    return v.toString(16);
  });
});

Solrgo.search = (function() {
	var q = Solrgo.getParam('q');
	$("#q").val(q);
	Solrgo.log('SEARCH', q);
	$.ajax({
	   type: "GET",
	   url: Solrgo.document_api + "?q=" + q,
	   dataType:'json',
	   success: function(res){
			// result
		   $("#list-content").setTemplateElement("list-template");
			$("#list-content").processTemplate(res);
			
			// result count
			var	result_info = '0 results';
			if(res && res.documents){
				result_info = res.documents.length + ' results';
			}
			$("#result_info").html(result_info);
	   },
	   error:function(){
		   layer.open({content: 'failed'});
	   }
	});
});

Solrgo.redirect = (function() {
	var q = $("#q").val();
	if($.trim(q) == ''){
		return;
	}
	window.location.href = Solrgo.search_page + "?q=" + q + "&sid=" + Solrgo.sid;
});

Solrgo.download = (function(obj) {
	var name = $(obj).html();
	var url = $(obj).attr("url");
	console.log('download:' + name);
	window.open(url);
	Solrgo.log('DOWNLOAD', name);
});

Solrgo.log = (function(type, value) {
	var json = JSON.stringify({sid: Solrgo.sid, type: type, value: value});
	console.log('log:' + json);
	$.ajax({
		type:"POST",
		url:Solrgo.log_api,
		contentType:"application/json",
		data:json
	});
});


Solrgo.init = (function() {
	Solrgo.search_page = 'http://' + window.location.host + "/Solrgo/search.html";
	Solrgo.document_api = 'http://' + window.location.host + "/Solrgo/api/document";
	Solrgo.log_api = 'http://' + window.location.host + "/Solrgo/api/log";
	Solrgo.sid = Solrgo.getParam('sid');
	if(!Solrgo.sid){
		Solrgo.sid = Solrgo.createSid();
	}
	Solrgo.search();
	$("#q").focus();
	$('#search').click(Solrgo.redirect);
	$("#q").keydown(function() {
        if (event.keyCode == "13") {
        	Solrgo.redirect();
        }
    });
});

Solrgo.init();

