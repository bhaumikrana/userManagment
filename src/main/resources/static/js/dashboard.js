// A $( document ).ready() block.
$( document ).ready(function() {
//    alert('test');
});

function deleteClicked(id, thisData) {
	
	$.post("/testV",
    {
        name: "SUCCESS"
    },
    function(data, status) {
//        alert("Data: " + data + "\nStatus: " + status);
    	$(thisData).closest('tr').remove();
    });
}

function searchButtonClickEvent() {
	
	var search = $('#autocomplete').val();
	location.href = '/user/search?search=' + search;
	
}