
$(document).ready(function() {
	
	$('.modalOpen').click(function(){
		$('.clearTable>tbody>tr').css('background-color','#fff');
		$('.errorMsgSpan').html('').removeClass('alert alert-danger');
		$('#storeInfoName').css('background-color','#fff');
		$('#storeInfoInfo').css('background-color','#fff');
	});
	
    $('#setChargeBt').click(function() {    
    	$('.errorMsgSpan').html(''); 
        $.ajax({
            type: 'POST',
            url: $('#setChargeForm').attr("action"),
            data: $('#setChargeForm').serialize(),
            dataType: 'json',
            success:function(data){
              if(data.isError){            
            	 $.each(data,function(key,value){
            		 if(key!='isError')
            			 $('#setChargeForm .errorMsgSpan').append(value+'<br>').addClass('alert alert-danger');
            	 });            	 
              }	
              if(data.success=='success'){
                 if($('#dogCharge')){
                     $('#dogCharge').html('<td>狗的住宿費用:</td><td>'+data.dogPrice+'元/一天</td><td>'
                    +'狀態:'+(data.dogIsOnsale=='y'?'上架':'下架')).css('background-color','#F2B27F');
                 }
                 if($('#catCharge')){
                     $('#catCharge').html('<td>貓的住宿費用:</td><td>'+data.catPrice+'元/一天</td><td>'
                    +'狀態:'+(data.catIsOnsale=='y'?'上架':'下架')).css('background-color','#F2B27F');
                 }
                 $('#setChargeModal').modal('hide');
              }             
            }
        });        
    }); //end of click

    $('#addCustBt').click(function() {
    	$('.errorMsgSpan').html(''); 
        // $.post("/Petribe0511e1/ServiceListServlet", $('#setChargeForm').serialize());
        $.ajax({
            type: 'POST',
            url: $('#addCustSrvForm').attr("action"),
            data: $('#addCustSrvForm').serialize(),
            dataType: 'json',
            success:function(data){
            	
            if(data.isError){
               	 $.each(data,function(key,value){
               		 if(key!='isError')
               			 $('#addCustSrvForm .errorMsgSpan').append(value+'<br>').addClass('alert alert-danger');
               	 });               	
             }else{            	
              if(data.petType=='dog'){            	  	
            		$('#dogTable').append('<tr><td>'+data.name+'</td>'+'<td>'+data.price+'</td>'
            				+'<td>'+(data.chargeType=='perDay'?'每天':'每次')+'</td>'
            				+'<td>'+(data.isOnsale=='y'?'上架':'下架')+'</td></tr>');
            		$('#dogTable > tr:last-child').css('background-color','#F2B27F');
            		$('#dognosrv').hide();
            	}else if(data.petType=='cat'){
            		$('#catTable').append('<tr><td>'+data.name+'</td>'+'<td>'+data.price+'</td>'
            				+'<td>'+(data.chargeType=='perDay'?'每天':'每次')+'</td>'
            				+'<td>'+(data.isOnsale=='y'?'上架':'下架')+'</td></tr>');  
            		$('#catTable > tr:last-child').css('background-color','#F2B27F');
            		$('#catnosrv').hide();
            	}
              	$('#addCustSrvModal').modal('hide');
           	  }           	 
            }//end of success;
        });//end of ajax       
    }); //end of click

    $('#setPickBt').click(function() {
    	$('.errorMsgSpan').html('');  
        // $.post("/Petribe0511e1/ServiceListServlet", $('#setChargeForm').serialize());
        $.ajax({
            type: 'POST',
            url: $('#setPickSrvForm').attr("action"),
            data: $('#setPickSrvForm').serialize(),
            dataType: 'json',
            success:function(data){
                if(data.isError){
              	 $.each(data,function(key,value){
              		 if(key!='isError')
              			 $('#setPickSrvForm .errorMsgSpan').append(value+'<br>').addClass('alert alert-danger');
              	 });            	 
                }	
                if(data.success=='success'){
                   if($('#dogPickSrv')){
                       $('#dogPickSrv').html('<td>接送費用:</td><td>'+data.dogPrice+'元('+data.dogName+'公里內)</td><td>'
                      +'狀態:'+(data.dogIsOnsale=='y'?'上架':'下架')).css('background-color','#F2B27F');
                   }                 
                   $('#setPickSrvModal').modal('hide');
                }             
              }
        });//end of ajax       
    }); //end of click

    $('#infoPicUpload').change(function(event){
    	var input = event.target;
    	var reader = new FileReader();
    	reader.onload = function(){
    		var dataURL = reader.result;
    		var infoPicResult =document.getElementById('infoPicResult');
    		infoPicResult.src = dataURL;
    	};
    	reader.readAsDataURL(input.files[0]);
    });//end of file change
    
    $('#setInfoBt').click(function(){    	
    	$('.errorMsgSpan').html('');
    	//var formDOM = $('#setInfoForm').get();
    	var formDOM = document.getElementById('setInfoForm');
    	var formData = new FormData(formDOM);
    	
    	$.ajax({
    		url: $('#setInfoForm').attr("action"),
    		type:'POST',
    		data:formData,
    		dataType: 'json',    		
    		contentType:false,
    		processData:false,
    		success:function(data){
    			if(data.hasError){
    				$.each(data,function(key,value){
    					if(key!='hasError')
    						$.each(value,function(k,v){
    							 $('#setInfoModal .errorMsgSpan').append(v+'<br>').addClass('alert alert-danger');
    						});//end of each    						
    				});//end of each
    			}else{    				
    				$('#storeInfoImg').attr("src",data.iconURL+"&"+new Date().getTime());    				
    				$('#storeInfoName').html(data.name).css('background-color','#F2B27F');
    				$('#storeInfoInfo').html(data.info).css('background-color','#F2B27F');
    				$('#setInfoModal').modal('hide');
    			}
    		}
    	});
    });//end of click
    
}); //end of ready;
