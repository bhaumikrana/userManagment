$(function() {
	$('#autocomplete').autocomplete({
		serviceUrl: '/suggestion',
	    paramName: 'searchstr',
	    minChars: 1,
	    transformResult: function(response) {
	    	
	    	response = JSON.parse(response);
	    	
	        return {
	            suggestions: $.map(response, function(dataItem) {
	            	return { value: dataItem.firstName, data: dataItem.firstName };
//	                return { value: dataItem.firstName };
	            })
	        };
	    }
	});
});

