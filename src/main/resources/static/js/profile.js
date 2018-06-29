// A $( document ).ready() block.
$( document ).ready(function() {
//    alert('test');
	
});

$(function() {
	$('#autocomplete').autocomplete({
		serviceUrl: '/suggestFriend',
	    paramName: 'searchstr',
	    minChars: 1,
	    transformResult: function(response) {
	    	
	    	response = JSON.parse(response);
	    	
	        return {
	            suggestions: $.map(response, function(dataItem) {
	            	return { value: dataItem.firstName +' '+ dataItem.lastName, data: '' };
//	                return { value: dataItem.firstName };
	            })
	        };
	    }
	});
});

$(function() {
	$('.accept').click(function(){
		$('#popup-header').text('Accept friend Request');
		$('#popup-body').text('Are you sure you want to accept friend request?');
		$('#acc').removeClass('hide');
		$('#acc').text('Accept');
		$('#cancel').text('Cancel');
		$('#myModal').modal();
	});
	
	$('.reject').click(function(){
		$('#popup-header').text('Delete friend Request');
		$('#popup-body').text('Are you sure you want to delete friend request?');
		$('#rej').removeClass('hide');
		$('#rej').text('Delete');
    	$('#cancel').text('Cancel');
        $('#myModal').modal();
    });
})

function sendRequestClicked(fuserId,userId, thisData) {
	
	$.post("/sendFriendRequest",
			{
		fuserId : fuserId,
		userId : userId,
			},
			function(data, status) {
//        alert("Data: " + data + "\nStatus: " + status);
//				$(thisData).closest('tr').remove();
				$('#sendRequest-column').remove();
				$('#success-sendRequest').removeClass('hide');
				$('#success-sendRequest label').text('Request sent successfully');
			});
}

function acceptButtonClicked(fuserId,userId, thisData) {
	
	$.post("/acceptFriendRequest",
			{
		fuserId : fuserId,
		userId : userId,
			},
			function(data, status) {
//        alert("Data: " + data + "\nStatus: " + status);
//				$('#accept').closest('tr').remove();
//				$('#accept').html("<td><label>Request sent successfully</label></td>");
				$('#column').remove();
				$('#test').removeClass('hide');
				$('#test label').text('Request accept successfully');
			});
}

function rejectButtonClicked(fuserId,userId, thisData) {
	
	$.post("/rejectFriendRequest",
			{
		fuserId : fuserId,
		userId : userId,
			},
			function(data, status) {
//        alert("Data: " + data + "\nStatus: " + status);
//    	$(thisData).closest('tr').remove();
//    	$('#row').html(<td colspan="2"><label>Request sent successfully</label></td>);
				$('#column').remove();
				$('#test').removeClass('hide');
				$('#test label').text('Delete Request successfully');
			});
}

function yesButtonClicked(fuserId,userId, thisData) {
	
	$.post("/unFriendRequest",
    {
		fuserId : fuserId,
		userId : userId,
    },
    function(data, status) {
//        alert("Data: " + data + "\nStatus: " + status);
//    	$(thisData).closest('tr').remove();
//    	$('#row').html(<td colspan="2"><label>Request sent successfully</label></td>);
    	$('#unfriend-column').remove();
		$('#success-unfriend').removeClass('hide');
		$('#success-unfriend label').text('Unfriend successfully');
    });
}

//function searchButtonClickEvent() {
//	
//	var search = $('#autocomplete').val();
//	location.href = '/user/searchFriend?search=' + search;
//	
//}

function searchButtonClickEvent() {
	
//	var search = $('#autocomplete').val();
//	location.href = '/user/searchFriend?search=' + search;
	$.post("/user/searchFriend",
		    {
				search : $('#autocomplete').val()
				
		    },
	function(data, status) {
//  alert("Data: " + data + "\nStatus: " + status);
//		    $(thisData).closest('tr').remove();
//		    document.getElementById("friends").innerHTML
//		    var content = $(data).html();
		    $('#replaceMe').html(data);
//		    $("#feedback").html(data);
    });
	
}