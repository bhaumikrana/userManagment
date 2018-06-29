// A $( document ).ready() block.
$( document ).ready(function() {
    alert('Done');
});

function submitClicked() {
	
	var str = $('#firstName').val();
	str = $.trim(str);
	if(str == "") {
		$('#message').text('Please enter FirstName');
		return;
	}
	
	var str = $('#lastName').val();
	str = $.trim(str);
	if(str == "") {
		$('#message').text('Please enter LastName');
		return;
	}
	
	var str = $('#email').val();
	str = $.trim(str);
	if(str == "") {
		$('#message').text('Please enter Email');
		return;	
	}
	var str = $('#email').val();
	if(!str.includes("@") || !str.includes(".com") ) {
		$('#message').text('Please enter valid Email');
		return;	
	}
	
	var str = $('#password').val();
	str = $.trim(str);
	if(str == "") {
		$('#message').text('Please enter Password');
		return;	
	}
	
	var str = $('#confirmPassword').val();
	str = $.trim(str);
	if(str == "") {
		$('#message').text('Please enter Confirm Password');
		return;
	}
	
	var str = $('#password').val();
	var str1 = $('#confirmPassword').val();
	if(str!==str1) {
		$('#message').text('Password & Confirm Password not match');
		return;
	} 
	
	var radioValue = $("input[name='gender']:checked").val();
	if(radioValue == null) {
		$('#message').text('Please select Gender');
		return;
	}
	
	var checkbox = $("input[name='hobby']:checked").val();
	if(checkbox == null) {
		$('#message').text('Please select Hobbies');
		return;
	}
	
	var str = $('#city').val();
	str = $.trim(str);
	if(str == "") {
		$('#message').text('Please enter City');
		return;
	}
	
	var str = $('#address').val();
	str = $.trim(str);
	if(str == "") {
		$('#message').text('Please enter Address');
		return;
	}
	
	$('#myForm').submit();
}