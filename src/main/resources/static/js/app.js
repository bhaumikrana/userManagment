$(document).ready(function() {
	changePageAndSize();
});

//function changePageAndSize() {
//	$('#pageSizeSelect').change(function(evt) {
//		window.location.replace("/?pageSize=" + this.value + "&page=1");
//	});
//}
function changePageAndSize() {
	$('#pageSizeSelect').change(function(evt) {
		window.location.replace("/user/dashboard" + "?pageSize=" + this.value + "&page=1");
	});
}
