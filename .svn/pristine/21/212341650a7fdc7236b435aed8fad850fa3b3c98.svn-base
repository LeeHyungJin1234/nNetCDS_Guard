$(document).ready(function() {

	// 체크박스 모두 선택
	$("#delck").click(function() {
		if($(this).prop("checked")){
			$("tbody input[name=delck]").prop("checked", true);
			$("tbody input[name=delck]").parent().parent().css("background-color", "#fafafa");
		} else {
			$("tbody input[name=delck]").prop("checked", false);
			$("tbody input[name=delck]").parent().parent().css("background-color", "");
		}		
	});

	// 체크박스 하나 선택 시
	$("tbody input:checkbox[name=delck]").click(function(){
		if($(this).prop('checked')){
			$(this).parent().parent().css("background-color", "#fafafa");
		} else {
			$(this).parent().parent().css("background-color", "");
		}
	});

	// 켜진 스위치에 관한 제이쿼리
	$("tbody .list-group7 input.checked").click(function(){
		$(this).parent().parent().siblings().children().addClass("opacity4");
		$(this).parent().parent().siblings().children("img").removeClass("opacity4");
		$(this).parent().parent().siblings().children("img").addClass("opacity2");
		$(this).parent().parent().siblings(".list-group1, .list-group7, .list-group8, .list-group9").children().removeClass("opacity4");	
	});

	$("tbody .list-group3 input.checked").click(function(){
		$(this).parent().parent().siblings().children().addClass("opacity4");
		$(this).parent().parent().siblings(".list-group4, .list-group5").children().removeClass("opacity4");
	});

	// 꺼진 스위치에 관한 제이쿼리
	$("tbody .list-group7 input.unchecked").click(function(){
		$(this).parent().parent().siblings().children().removeClass("opacity4");
		$(this).parent().parent().siblings().children("img").removeClass("opacity2");
	});

	$("tbody .list-group3 input.unchecked").click(function(){
		$(this).parent().parent().siblings().children().removeClass("opacity4");
	});

	// 검색 TAB 버튼
	$("#right-content .right-wrapper .top-section #search > li .tab div").click(function(){
		$(this).parent().siblings().children().removeClass("active");
		$(this).addClass("active");
	});
	$("#main-form .main-wrapper #search > li .tab div").click(function(){
		$(this).parent().siblings().children().removeClass("active");
		$(this).addClass("active");
	});

    // datepicker start
	$( function() {
		from = $( "#from" ).datepicker({
	         defaultDate: "+1w",
	        dayNamesMin:["월","화","수","목","금","토","일"],
	        monthNames:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
	        dateFormat:"yy-mm-dd",
	    })
	    .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	        init_datetab();
	    }),
	    to = $( "#to" ).datepicker({
	        defaultDate: "+1w",
	        dayNamesMin:["월","화","수","목","금","토","일"],
	        monthNames:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
	        dateFormat:"yy-mm-dd",
	    })
	    .on( "change", function() {
	        from.datepicker( "option", "maxDate", getDate( this ) );
	        init_datetab();
	    });
		
		sdate = $( "#stat_sdate" ).datepicker({
	        defaultDate: "+1w",
	        dayNamesMin:["월","화","수","목","금","토","일"],
	        monthNames:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
	        dateFormat:"yy-mm-dd",
	    })
	    .on( "change", function() {
	        edate.datepicker( "option", "minDate", getDate( this ) );
	        end_term_set();
	        init_datetab();
	    }),
	    edate = $( "#stat_edate" ).datepicker({
	        defaultDate: "+1w",
	        dayNamesMin:["월","화","수","목","금","토","일"],
	        monthNames:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
	        dateFormat:"yy-mm-dd",
	    })
	    .on( "change", function() {
	        sdate.datepicker( "option", "maxDate", getDate( this ) );
	        start_term_set();
	        init_datetab();
	    }),
	    today = $( "#date" ).datepicker({
	         defaultDate: "+1w",
	        dayNamesMin:["월","화","수","목","금","토","일"],
	        monthNames:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
	        dateFormat:"yy-mm-dd",
	    });
	 
	    function getDate( element ) {
		    var date;
		    try {
		        date = $.datepicker.parseDate( dateFormat, element.value );
		    } catch( error ) {
		        date = null;
		    }
		    return date;
	    }
	} );

	
	// 메인 left-line 애니메이션
    var leftline01=["../../img/leftline01/leftline01_01.png", "../../img/leftline01/leftline01_02.png", "../../img/leftline01/leftline01_03.png", "../../img/leftline01/leftline01_04.png", "../../img/leftline01/leftline01_05.png", "../../img/leftline01/leftline01_06.png", "../../img/leftline01/leftline01_07.png", "../../img/leftline01/leftline01_08.png", "../../img/leftline01/leftline01_09.png", "../../img/leftline01/leftline01_10.png", "../../img/leftline01/leftline01_11.png", "../../img/leftline01/leftline01_12.png", "../../img/leftline01/leftline01_13.png", "../../img/leftline01/leftline01_14.png", "../../img/leftline01/leftline01_15.png", "../../img/leftline01/leftline01_16.png", "../../img/leftline01/leftline01_17.png", "../../img/leftline01/leftline01_18.png", "../../img/leftline01/leftline01_19.png", "../../img/leftline01/leftline01_20.png", "../../img/leftline01/leftline01_21.png"];
    var leftline01Index=0;
    var moveleftline01=setInterval(function(){
    	leftline01Index++;
    	if(leftline01Index==21){
    		leftline01Index=0;
    	}	
    	$(".tx .main-icon .icon09.normal > img").attr("src", leftline01[leftline01Index]);
    }, 100);

    var leftline02=["../../img/leftline02/leftline02_01.png", "../../img/leftline02/leftline02_02.png", "../../img/leftline02/leftline02_03.png", "../../img/leftline02/leftline02_04.png", "../../img/leftline02/leftline02_05.png", "../../img/leftline02/leftline02_06.png", "../../img/leftline02/leftline02_07.png", "../../img/leftline02/leftline02_08.png", "../../img/leftline02/leftline02_09.png", "../../img/leftline02/leftline02_10.png", "../../img/leftline02/leftline02_11.png", "../../img/leftline02/leftline02_12.png", "../../img/leftline02/leftline02_13.png", "../../img/leftline02/leftline02_14.png", "../../img/leftline02/leftline02_15.png", "../../img/leftline02/leftline02_16.png", "../../img/leftline02/leftline02_17.png", "../../img/leftline02/leftline02_18.png", "../../img/leftline02/leftline02_19.png", "../../img/leftline02/leftline02_20.png", "../../img/leftline02/leftline02_21.png"];
    var leftline02Index=0;
    var moveleftline02=setInterval(function(){
	  leftline02Index++;
	  if(leftline02Index==21){
		leftline02Index=0;
      }	
        $(".tx .main-icon .icon07.normal > img").attr("src", leftline02[leftline02Index]);
    }, 100);

    var leftline03=["../../img/leftline03/leftline03_01.png", "../../img/leftline03/leftline03_02.png", "../../img/leftline03/leftline03_03.png", "../../img/leftline03/leftline03_04.png", "../../img/leftline03/leftline03_05.png", "../../img/leftline03/leftline03_06.png", "../../img/leftline03/leftline03_07.png", "../../img/leftline03/leftline03_08.png", "../../img/leftline03/leftline03_09.png", "../../img/leftline03/leftline03_10.png", "../../img/leftline03/leftline03_11.png", "../../img/leftline03/leftline03_12.png", "../../img/leftline03/leftline03_13.png", "../../img/leftline03/leftline03_14.png", "../../img/leftline03/leftline03_15.png", "../../img/leftline03/leftline03_16.png", "../../img/leftline03/leftline03_17.png", "../../img/leftline03/leftline03_18.png", "../../img/leftline03/leftline03_19.png", "../../img/leftline03/leftline03_20.png", "../../img/leftline03/leftline03_21.png"];
    var leftline03Index=0;
    var moveleftline03=setInterval(function(){
	  leftline03Index++;
	  if(leftline03Index==21){
		leftline03Index=0;
      }	
        $(".tx .main-icon .icon05.normal > img").attr("src", leftline03[leftline03Index]);
    }, 100);

    var leftline04=["../../img/leftline04/leftline04_01.png", "../../img/leftline04/leftline04_02.png", "../../img/leftline04/leftline04_03.png", "../../img/leftline04/leftline04_04.png", "../../img/leftline04/leftline04_05.png", "../../img/leftline04/leftline04_06.png", "../../img/leftline04/leftline04_07.png", "../../img/leftline04/leftline04_08.png", "../../img/leftline04/leftline04_09.png", "../../img/leftline04/leftline04_10.png", "../../img/leftline04/leftline04_11.png", "../../img/leftline04/leftline04_12.png", "../../img/leftline04/leftline04_13.png", "../../img/leftline04/leftline04_14.png", "../../img/leftline04/leftline04_15.png", "../../img/leftline04/leftline04_16.png", "../../img/leftline04/leftline04_17.png", "../../img/leftline04/leftline04_18.png", "../../img/leftline04/leftline04_19.png", "../../img/leftline04/leftline04_20.png", "../../img/leftline04/leftline04_21.png"];
    var leftline04Index=0;
    var moveleftline04=setInterval(function(){
	  leftline04Index++;
	  if(leftline04Index==21){
		leftline04Index=0;
      }	
        $(".tx .main-icon .icon03.normal > img").attr("src", leftline04[leftline04Index]);
    }, 100);
    
    var leftline05=["../../img/leftline05/leftline05_01.png", "../../img/leftline05/leftline05_02.png", "../../img/leftline05/leftline05_03.png", "../../img/leftline05/leftline05_04.png", "../../img/leftline05/leftline05_05.png", "../../img/leftline05/leftline05_06.png", "../../img/leftline05/leftline05_07.png", "../../img/leftline05/leftline05_08.png", "../../img/leftline05/leftline05_09.png", "../../img/leftline05/leftline05_10.png", "../../img/leftline05/leftline05_11.png", "../../img/leftline05/leftline05_12.png", "../../img/leftline05/leftline05_13.png", "../../img/leftline05/leftline05_14.png", "../../img/leftline05/leftline05_15.png", "../../img/leftline05/leftline05_16.png", "../../img/leftline05/leftline05_17.png", "../../img/leftline05/leftline05_18.png", "../../img/leftline05/leftline05_19.png", "../../img/leftline05/leftline05_20.png", "../../img/leftline05/leftline05_21.png"];
    var leftline05Index=0;
    var moveleftline05=setInterval(function(){
	  leftline05Index++;
	  if(leftline05Index==21){
		leftline05Index=0;
      }	
        $(".tx .main-icon .icon01.normal > img").attr("src", leftline05[leftline05Index]);
    }, 100);

    var leftline06=["../../img/leftline06/leftline06_01.png", "../../img/leftline06/leftline06_02.png", "../../img/leftline06/leftline06_03.png", "../../img/leftline06/leftline06_04.png", "../../img/leftline06/leftline06_05.png", "../../img/leftline06/leftline06_06.png", "../../img/leftline06/leftline06_07.png", "../../img/leftline06/leftline06_08.png", "../../img/leftline06/leftline06_09.png", "../../img/leftline06/leftline06_10.png", "../../img/leftline06/leftline06_11.png", "../../img/leftline06/leftline06_12.png", "../../img/leftline06/leftline06_13.png", "../../img/leftline06/leftline06_14.png", "../../img/leftline06/leftline06_15.png", "../../img/leftline06/leftline06_16.png", "../../img/leftline06/leftline06_17.png", "../../img/leftline06/leftline06_18.png", "../../img/leftline06/leftline06_19.png", "../../img/leftline06/leftline06_20.png", "../../img/leftline06/leftline06_21.png"];
    var leftline06Index=0;
    var moveleftline06=setInterval(function(){
	  leftline06Index++;
	  if(leftline06Index==21){
		leftline06Index=0;
      }	
        $(".tx .main-icon .icon02.normal > img").attr("src", leftline06[leftline06Index]);
    }, 100);

    var leftline07=["../../img/leftline07/leftline07_01.png", "../../img/leftline07/leftline07_02.png", "../../img/leftline07/leftline07_03.png", "../../img/leftline07/leftline07_04.png", "../../img/leftline07/leftline07_05.png", "../../img/leftline07/leftline07_06.png", "../../img/leftline07/leftline07_07.png", "../../img/leftline07/leftline07_08.png", "../../img/leftline07/leftline07_09.png", "../../img/leftline07/leftline07_10.png", "../../img/leftline07/leftline07_11.png", "../../img/leftline07/leftline07_12.png", "../../img/leftline07/leftline07_13.png", "../../img/leftline07/leftline07_14.png", "../../img/leftline07/leftline07_15.png", "../../img/leftline07/leftline07_16.png", "../../img/leftline07/leftline07_17.png", "../../img/leftline07/leftline07_18.png", "../../img/leftline07/leftline07_19.png", "../../img/leftline07/leftline07_20.png", "../../img/leftline07/leftline07_21.png"];
    var leftline07Index=0;
    var moveleftline07=setInterval(function(){
	  leftline07Index++;
	  if(leftline07Index==21){
		leftline07Index=0;
      }	
        $(".tx .main-icon .icon04.normal > img").attr("src", leftline07[leftline07Index]);
    }, 100);

    var leftline08=["../../img/leftline08/leftline08_01.png", "../../img/leftline08/leftline08_02.png", "../../img/leftline08/leftline08_03.png", "../../img/leftline08/leftline08_04.png", "../../img/leftline08/leftline08_05.png", "../../img/leftline08/leftline08_06.png", "../../img/leftline08/leftline08_07.png", "../../img/leftline08/leftline08_08.png", "../../img/leftline08/leftline08_09.png", "../../img/leftline08/leftline08_10.png", "../../img/leftline08/leftline08_11.png", "../../img/leftline08/leftline08_12.png", "../../img/leftline08/leftline08_13.png", "../../img/leftline08/leftline08_14.png", "../../img/leftline08/leftline08_15.png", "../../img/leftline08/leftline08_16.png", "../../img/leftline08/leftline08_17.png", "../../img/leftline08/leftline08_18.png", "../../img/leftline08/leftline08_19.png", "../../img/leftline08/leftline08_20.png", "../../img/leftline08/leftline08_21.png"];
    var leftline08Index=0;
    var moveleftline08=setInterval(function(){
	  leftline08Index++;
	  if(leftline08Index==21){
		leftline08Index=0;
      }	
        $(".tx .main-icon .icon06.normal > img").attr("src", leftline08[leftline08Index]);
    }, 100);

    var leftline09=["../../img/leftline09/leftline09_01.png", "../../img/leftline09/leftline09_02.png", "../../img/leftline09/leftline09_03.png", "../../img/leftline09/leftline09_04.png", "../../img/leftline09/leftline09_05.png", "../../img/leftline09/leftline09_06.png", "../../img/leftline09/leftline09_07.png", "../../img/leftline09/leftline09_08.png", "../../img/leftline09/leftline09_09.png", "../../img/leftline09/leftline09_10.png", "../../img/leftline09/leftline09_11.png", "../../img/leftline09/leftline09_12.png", "../../img/leftline09/leftline09_13.png", "../../img/leftline09/leftline09_14.png", "../../img/leftline09/leftline09_15.png", "../../img/leftline09/leftline09_16.png", "../../img/leftline09/leftline09_17.png", "../../img/leftline09/leftline09_18.png", "../../img/leftline09/leftline09_19.png", "../../img/leftline09/leftline09_20.png", "../../img/leftline09/leftline09_21.png"];
    var leftline09Index=0;
    var moveleftline09=setInterval(function(){
	  leftline09Index++;
	  if(leftline09Index==21){
		leftline09Index=0;
      }	
        $(".tx .main-icon .icon08.normal > img").attr("src", leftline09[leftline09Index]);
    }, 100);
    
    var leftline10=["../../img/leftline10/leftline10_01.png", "../../img/leftline10/leftline10_02.png", "../../img/leftline10/leftline10_03.png", "../../img/leftline10/leftline10_04.png", "../../img/leftline10/leftline10_05.png", "../../img/leftline10/leftline10_06.png", "../../img/leftline10/leftline10_07.png", "../../img/leftline10/leftline10_08.png", "../../img/leftline10/leftline10_09.png", "../../img/leftline10/leftline10_10.png", "../../img/leftline10/leftline10_11.png", "../../img/leftline10/leftline10_12.png", "../../img/leftline10/leftline10_13.png", "../../img/leftline10/leftline10_14.png", "../../img/leftline10/leftline10_15.png", "../../img/leftline10/leftline10_16.png", "../../img/leftline10/leftline10_17.png", "../../img/leftline10/leftline10_18.png", "../../img/leftline10/leftline10_19.png", "../../img/leftline10/leftline10_20.png", "../../img/leftline10/leftline10_21.png"];
    var leftline10Index=0;
    var moveleftline10=setInterval(function(){
	  leftline10Index++;
	  if(leftline10Index==21){
		leftline10Index=0;
      }	
        $(".tx .main-icon .icon10.normal > img").attr("src", leftline10[leftline10Index]);
    }, 100);

    
    // 메인 right-line 애니메이션
    var rightline01=["../../img/rightline01/rightline01_01.png", "../../img/rightline01/rightline01_02.png", "../../img/rightline01/rightline01_03.png", "../../img/rightline01/rightline01_04.png", "../../img/rightline01/rightline01_05.png", "../../img/rightline01/rightline01_06.png", "../../img/rightline01/rightline01_07.png", "../../img/rightline01/rightline01_08.png", "../../img/rightline01/rightline01_09.png", "../../img/rightline01/rightline01_10.png", "../../img/rightline01/rightline01_11.png", "../../img/rightline01/rightline01_12.png", "../../img/rightline01/rightline01_13.png", "../../img/rightline01/rightline01_14.png", "../../img/rightline01/rightline01_15.png", "../../img/rightline01/rightline01_16.png", "../../img/rightline01/rightline01_17.png", "../../img/rightline01/rightline01_18.png", "../../img/rightline01/rightline01_19.png", "../../img/rightline01/rightline01_20.png", "../../img/rightline01/rightline01_21.png"];
    var rightline01Index=0;
    var moverightline01=setInterval(function(){
	  rightline01Index++;
	  if(rightline01Index==21){
		rightline01Index=0;
      }	
        $(".rx .main-icon .icon09.normal > img").attr("src", rightline01[rightline01Index]);
    }, 100);

    var rightline02=["../../img/rightline02/rightline02_01.png", "../../img/rightline02/rightline02_02.png", "../../img/rightline02/rightline02_03.png", "../../img/rightline02/rightline02_04.png", "../../img/rightline02/rightline02_05.png", "../../img/rightline02/rightline02_06.png", "../../img/rightline02/rightline02_07.png", "../../img/rightline02/rightline02_08.png", "../../img/rightline02/rightline02_09.png", "../../img/rightline02/rightline02_10.png", "../../img/rightline02/rightline02_11.png", "../../img/rightline02/rightline02_12.png", "../../img/rightline02/rightline02_13.png", "../../img/rightline02/rightline02_14.png", "../../img/rightline02/rightline02_15.png", "../../img/rightline02/rightline02_16.png", "../../img/rightline02/rightline02_17.png", "../../img/rightline02/rightline02_18.png", "../../img/rightline02/rightline02_19.png", "../../img/rightline02/rightline02_20.png", "../../img/rightline02/rightline02_21.png"];
    var rightline02Index=0;
    var moverightline02=setInterval(function(){
	  rightline02Index++;
	  if(rightline02Index==21){
		rightline02Index=0;
      }	
        $(".rx .main-icon .icon07.normal > img").attr("src", rightline02[rightline02Index]);
    }, 100);

    var rightline03=["../../img/rightline03/rightline03_01.png", "../../img/rightline03/rightline03_02.png", "../../img/rightline03/rightline03_03.png", "../../img/rightline03/rightline03_04.png", "../../img/rightline03/rightline03_05.png", "../../img/rightline03/rightline03_06.png", "../../img/rightline03/rightline03_07.png", "../../img/rightline03/rightline03_08.png", "../../img/rightline03/rightline03_09.png", "../../img/rightline03/rightline03_10.png", "../../img/rightline03/rightline03_11.png", "../../img/rightline03/rightline03_12.png", "../../img/rightline03/rightline03_13.png", "../../img/rightline03/rightline03_14.png", "../../img/rightline03/rightline03_15.png", "../../img/rightline03/rightline03_16.png", "../../img/rightline03/rightline03_17.png", "../../img/rightline03/rightline03_18.png", "../../img/rightline03/rightline03_19.png", "../../img/rightline03/rightline03_20.png", "../../img/rightline03/rightline03_21.png"];
    var rightline03Index=0;
    var moverightline03=setInterval(function(){
	  rightline03Index++;
	  if(rightline03Index==21){
		rightline03Index=0;
      }	
        $(".rx .main-icon .icon05.normal > img").attr("src", rightline03[rightline03Index]);
    }, 100);

    var rightline04=["../../img/rightline04/rightline04_01.png", "../../img/rightline04/rightline04_02.png", "../../img/rightline04/rightline04_03.png", "../../img/rightline04/rightline04_04.png", "../../img/rightline04/rightline04_05.png", "../../img/rightline04/rightline04_06.png", "../../img/rightline04/rightline04_07.png", "../../img/rightline04/rightline04_08.png", "../../img/rightline04/rightline04_09.png", "../../img/rightline04/rightline04_10.png", "../../img/rightline04/rightline04_11.png", "../../img/rightline04/rightline04_12.png", "../../img/rightline04/rightline04_13.png", "../../img/rightline04/rightline04_14.png", "../../img/rightline04/rightline04_15.png", "../../img/rightline04/rightline04_16.png", "../../img/rightline04/rightline04_17.png", "../../img/rightline04/rightline04_18.png", "../../img/rightline04/rightline04_19.png", "../../img/rightline04/rightline04_20.png", "../../img/rightline04/rightline04_21.png"];
    var rightline04Index=0;
    var moverightline04=setInterval(function(){
	  rightline04Index++;
	  if(rightline04Index==21){
		rightline04Index=0;
      }	
        $(".rx .main-icon .icon03.normal > img").attr("src", rightline04[rightline04Index]);
    }, 100);

    var rightline05=["../../img/rightline05/rightline05_01.png", "../../img/rightline05/rightline05_02.png", "../../img/rightline05/rightline05_03.png", "../../img/rightline05/rightline05_04.png", "../../img/rightline05/rightline05_05.png", "../../img/rightline05/rightline05_06.png", "../../img/rightline05/rightline05_07.png", "../../img/rightline05/rightline05_08.png", "../../img/rightline05/rightline05_09.png", "../../img/rightline05/rightline05_10.png", "../../img/rightline05/rightline05_11.png", "../../img/rightline05/rightline05_12.png", "../../img/rightline05/rightline05_13.png", "../../img/rightline05/rightline05_14.png", "../../img/rightline05/rightline05_15.png", "../../img/rightline05/rightline05_16.png", "../../img/rightline05/rightline05_17.png", "../../img/rightline05/rightline05_18.png", "../../img/rightline05/rightline05_19.png", "../../img/rightline05/rightline05_20.png", "../../img/rightline05/rightline05_21.png"];
    var rightline05Index=0;
    var moverightline05=setInterval(function(){
	  rightline05Index++;
	  if(rightline05Index==21){
		rightline05Index=0;
      }	
        $(".rx .main-icon .icon01.normal > img").attr("src", rightline05[rightline05Index]);
    }, 100);

    var rightline06=["../../img/rightline06/rightline06_01.png", "../../img/rightline06/rightline06_02.png", "../../img/rightline06/rightline06_03.png", "../../img/rightline06/rightline06_04.png", "../../img/rightline06/rightline06_05.png", "../../img/rightline06/rightline06_06.png", "../../img/rightline06/rightline06_07.png", "../../img/rightline06/rightline06_08.png", "../../img/rightline06/rightline06_09.png", "../../img/rightline06/rightline06_10.png", "../../img/rightline06/rightline06_11.png", "../../img/rightline06/rightline06_12.png", "../../img/rightline06/rightline06_13.png", "../../img/rightline06/rightline06_14.png", "../../img/rightline06/rightline06_15.png", "../../img/rightline06/rightline06_16.png", "../../img/rightline06/rightline06_17.png", "../../img/rightline06/rightline06_18.png", "../../img/rightline06/rightline06_19.png", "../../img/rightline06/rightline06_20.png", "../../img/rightline06/rightline06_21.png"];
    var rightline06Index=0;
    var moverightline06=setInterval(function(){
	  rightline06Index++;
	  if(rightline06Index==21){
		rightline06Index=0;
      }	
        $(".rx .main-icon .icon02.normal > img").attr("src", rightline06[rightline06Index]);
    }, 100);

    var rightline07=["../../img/rightline07/rightline07_01.png", "../../img/rightline07/rightline07_02.png", "../../img/rightline07/rightline07_03.png", "../../img/rightline07/rightline07_04.png", "../../img/rightline07/rightline07_05.png", "../../img/rightline07/rightline07_06.png", "../../img/rightline07/rightline07_07.png", "../../img/rightline07/rightline07_08.png", "../../img/rightline07/rightline07_09.png", "../../img/rightline07/rightline07_10.png", "../../img/rightline07/rightline07_11.png", "../../img/rightline07/rightline07_12.png", "../../img/rightline07/rightline07_13.png", "../../img/rightline07/rightline07_14.png", "../../img/rightline07/rightline07_15.png", "../../img/rightline07/rightline07_16.png", "../../img/rightline07/rightline07_17.png", "../../img/rightline07/rightline07_18.png", "../../img/rightline07/rightline07_19.png", "../../img/rightline07/rightline07_20.png", "../../img/rightline07/rightline07_21.png"];
    var rightline07Index=0;
    var moverightline07=setInterval(function(){
	  rightline07Index++;
	  if(rightline07Index==21){
		rightline07Index=0;
      }	
        $(".rx .main-icon .icon04.normal > img").attr("src", rightline07[rightline07Index]);
    }, 100);

    var rightline08=["../../img/rightline08/rightline08_01.png", "../../img/rightline08/rightline08_02.png", "../../img/rightline08/rightline08_03.png", "../../img/rightline08/rightline08_04.png", "../../img/rightline08/rightline08_05.png", "../../img/rightline08/rightline08_06.png", "../../img/rightline08/rightline08_07.png", "../../img/rightline08/rightline08_08.png", "../../img/rightline08/rightline08_09.png", "../../img/rightline08/rightline08_10.png", "../../img/rightline08/rightline08_11.png", "../../img/rightline08/rightline08_12.png", "../../img/rightline08/rightline08_13.png", "../../img/rightline08/rightline08_14.png", "../../img/rightline08/rightline08_15.png", "../../img/rightline08/rightline08_16.png", "../../img/rightline08/rightline08_17.png", "../../img/rightline08/rightline08_18.png", "../../img/rightline08/rightline08_19.png", "../../img/rightline08/rightline08_20.png", "../../img/rightline08/rightline08_21.png"];
    var rightline08Index=0;
    var moverightline08=setInterval(function(){
	  rightline08Index++;
	  if(rightline08Index==21){
		rightline08Index=0;
      }	
        $(".rx .main-icon .icon06.normal > img").attr("src", rightline08[rightline08Index]);
    }, 100);

    var rightline09=["../../img/rightline09/rightline09_01.png", "../../img/rightline09/rightline09_02.png", "../../img/rightline09/rightline09_03.png", "../../img/rightline09/rightline09_04.png", "../../img/rightline09/rightline09_05.png", "../../img/rightline09/rightline09_06.png", "../../img/rightline09/rightline09_07.png", "../../img/rightline09/rightline09_08.png", "../../img/rightline09/rightline09_09.png", "../../img/rightline09/rightline09_10.png", "../../img/rightline09/rightline09_11.png", "../../img/rightline09/rightline09_12.png", "../../img/rightline09/rightline09_13.png", "../../img/rightline09/rightline09_14.png", "../../img/rightline09/rightline09_15.png", "../../img/rightline09/rightline09_16.png", "../../img/rightline09/rightline09_17.png", "../../img/rightline09/rightline09_18.png", "../../img/rightline09/rightline09_19.png", "../../img/rightline09/rightline09_20.png", "../../img/rightline09/rightline09_21.png"];
    var rightline09Index=0;
    var moverightline09=setInterval(function(){
	  rightline09Index++;
	  if(rightline09Index==21){
		rightline09Index=0;
      }	
        $(".rx .main-icon .icon08.normal > img").attr("src", rightline09[rightline09Index]);
    }, 100);

    var rightline10=["../../img/rightline10/rightline10_01.png", "../../img/rightline10/rightline10_02.png", "../../img/rightline10/rightline10_03.png", "../../img/rightline10/rightline10_04.png", "../../img/rightline10/rightline10_05.png", "../../img/rightline10/rightline10_06.png", "../../img/rightline10/rightline10_07.png", "../../img/rightline10/rightline10_08.png", "../../img/rightline10/rightline10_09.png", "../../img/rightline10/rightline10_10.png", "../../img/rightline10/rightline10_11.png", "../../img/rightline10/rightline10_12.png", "../../img/rightline10/rightline10_13.png", "../../img/rightline10/rightline10_14.png", "../../img/rightline10/rightline10_15.png", "../../img/rightline10/rightline10_16.png", "../../img/rightline10/rightline10_17.png", "../../img/rightline10/rightline10_18.png", "../../img/rightline10/rightline10_19.png", "../../img/rightline10/rightline10_20.png", "../../img/rightline10/rightline10_21.png"];
    var rightline10Index=0;
    var moverightline10=setInterval(function(){
	  rightline10Index++;
	  if(rightline10Index==21){
		rightline10Index=0;
      }	
        $(".rx .main-icon .icon10.normal > img").attr("src", rightline10[rightline10Index]);
    }, 100);

    
   $(".main-pop .title > img").click(function(){
       $(".main-pop").css("display", "none");                               
   });
   $(".tx .main-icon > li.icon01").click(function(){
       $(".main-pop").css({"left": "-28px","top":"96px"});
       $(".main-pop").css("display", "block");
   });
   $(".tx .main-icon > li.icon02").click(function(){
       $(".main-pop").css({"left": "-28px","top":"160px"});
       $(".main-pop").css("display", "block");
   });
   $(".tx .main-icon > li.icon03").click(function(){
       $(".main-pop").css({"left": "28px","top":"60px"});
       $(".main-pop").css("display", "block");
   });
   $(".tx .main-icon > li.icon04").click(function(){
       $(".main-pop").css({"left": "27px","top":"176px"});
       $(".main-pop").css("display", "block");
   });
   $(".tx .main-icon > li.icon05").click(function(){
       $(".main-pop").css({"left": "-28px","top":"-64px"});
       $(".main-pop").css("display", "block");
   });
   $(".tx .main-icon > li.icon06").click(function(){
       $(".main-pop").css({"left": "-28px","top":"258px"});
       $(".main-pop").css("display", "block");
   });
   $(".tx .main-icon > li.icon07").click(function(){
       $(".main-pop").css({"left": "-28px","top":"-83px"});
       $(".main-pop").css("display", "block");
   });
   $(".tx .main-icon > li.icon08").click(function(){
       $(".main-pop").css({"left": "-28px","top":"347px"});
       $(".main-pop").css("display", "block");
   });
   $(".tx .main-icon > li.icon09").click(function(){
       $(".main-pop").css({"left": "53px","top":"-83px"});
       $(".main-pop").css("display", "block");
   });
   $(".tx .main-icon > li.icon10").click(function(){
       $(".main-pop").css({"left": "50px","top":"300px"});
       $(".main-pop").css("display", "block");
   });


   $(".rx .main-icon > li.icon01").click(function(){
       $(".main-pop").css({"left": "1106px","top":"96px"});
       $(".main-pop").css("display", "block");
   });
   $(".rx .main-icon > li.icon02").click(function(){
       $(".main-pop").css({"left": "1106px","top":"160px"});
       $(".main-pop").css("display", "block");
   });
   $(".rx .main-icon > li.icon03").click(function(){
       $(".main-pop").css({"left": "1064px","top":"60px"});
       $(".main-pop").css("display", "block");
   });
   $(".rx .main-icon > li.icon04").click(function(){
       $(".main-pop").css({"left": "1064px","top":"176px"});
       $(".main-pop").css("display", "block");
   });
   $(".rx .main-icon > li.icon05").click(function(){
       $(".main-pop").css({"left": "1106px","top":"-64px"});
       $(".main-pop").css("display", "block");
   });
   $(".rx .main-icon > li.icon06").click(function(){
       $(".main-pop").css({"left": "1106px","top":"258px"});
       $(".main-pop").css("display", "block");
   });
   $(".rx .main-icon > li.icon07").click(function(){
       $(".main-pop").css({"left": "1106px","top":"-83px"});
       $(".main-pop").css("display", "block");
   });
   $(".rx .main-icon > li.icon08").click(function(){
       $(".main-pop").css({"left": "1106px","top":"347px"});
       $(".main-pop").css("display", "block");
   });
   $(".rx .main-icon > li.icon09").click(function(){
       $(".main-pop").css({"left": "1038px","top":"-83px"});
       $(".main-pop").css("display", "block");
   });
   $(".rx .main-icon > li.icon10").click(function(){
       $(".main-pop").css({"left": "1038px","top":"300px"});
       $(".main-pop").css("display", "block");
   });
   
   
   // main 아이콘들 깜빡임
   var folder=["../../img/main_icon/main_folder01.png", "../../img/main_icon/main_folder02.png", "../../img/main_icon/main_folder03.png", "../../img/main_icon/main_folder04.png", "../../img/main_icon/main_folder05.png", "../../img/main_icon/main_folder06.png", "../../img/main_icon/main_folder07.png", "../../img/main_icon/main_folder07.png", "../../img/main_icon/main_folder06.png", "../../img/main_icon/main_folder05.png", "../../img/main_icon/main_folder04.png", "../../img/main_icon/main_folder03.png", "../../img/main_icon/main_folder02.png", "../../img/main_icon/main_folder01.png"];
   var folderIndex=0;

   var ftp=["../../img/main_icon/main_ftp01.png", "../../img/main_icon/main_ftp02.png", "../../img/main_icon/main_ftp03.png", "../../img/main_icon/main_ftp04.png", "../../img/main_icon/main_ftp05.png", "../../img/main_icon/main_ftp06.png", "../../img/main_icon/main_ftp07.png", "../../img/main_icon/main_ftp07.png", "../../img/main_icon/main_ftp06.png", "../../img/main_icon/main_ftp05.png", "../../img/main_icon/main_ftp04.png", "../../img/main_icon/main_ftp03.png", "../../img/main_icon/main_ftp02.png", "../../img/main_icon/main_ftp01.png"];
   var ftpIndex=0;

   var sftp=["../../img/main_icon/main_sftp01.png", "../../img/main_icon/main_sftp02.png", "../../img/main_icon/main_sftp03.png", "../../img/main_icon/main_sftp04.png", "../../img/main_icon/main_sftp05.png", "../../img/main_icon/main_sftp06.png", "../../img/main_icon/main_sftp07.png", "../../img/main_icon/main_sftp07.png", "../../img/main_icon/main_sftp06.png", "../../img/main_icon/main_sftp05.png", "../../img/main_icon/main_sftp04.png", "../../img/main_icon/main_sftp03.png", "../../img/main_icon/main_sftp02.png", "../../img/main_icon/main_sftp01.png"];
   var sftpIndex=0;

   var database=["../../img/main_icon/main_database01.png", "../../img/main_icon/main_database02.png", "../../img/main_icon/main_database03.png", "../../img/main_icon/main_database04.png", "../../img/main_icon/main_database05.png", "../../img/main_icon/main_database06.png", "../../img/main_icon/main_database07.png", "../../img/main_icon/main_database07.png", "../../img/main_icon/main_database06.png", "../../img/main_icon/main_database05.png", "../../img/main_icon/main_database04.png", "../../img/main_icon/main_database03.png", "../../img/main_icon/main_database02.png", "../../img/main_icon/main_database01.png"];
   var databaseIndex=0;

   var broadcast=["../../img/main_icon/main_broadcast01.png", "../../img/main_icon/main_broadcast02.png", "../../img/main_icon/main_broadcast03.png", "../../img/main_icon/main_broadcast04.png", "../../img/main_icon/main_broadcast05.png", "../../img/main_icon/main_broadcast06.png", "../../img/main_icon/main_broadcast07.png", "../../img/main_icon/main_broadcast07.png", "../../img/main_icon/main_broadcast06.png", "../../img/main_icon/main_broadcast05.png", "../../img/main_icon/main_broadcast04.png", "../../img/main_icon/main_broadcast03.png", "../../img/main_icon/main_broadcast02.png", "../../img/main_icon/main_broadcast01.png"];
   var broadcastIndex=0;

   var multicast=["../../img/main_icon/main_multicast01.png", "../../img/main_icon/main_multicast02.png", "../../img/main_icon/main_multicast03.png", "../../img/main_icon/main_multicast04.png", "../../img/main_icon/main_multicast05.png", "../../img/main_icon/main_multicast06.png", "../../img/main_icon/main_multicast07.png", "../../img/main_icon/main_multicast07.png", "../../img/main_icon/main_multicast06.png", "../../img/main_icon/main_multicast05.png", "../../img/main_icon/main_multicast04.png", "../../img/main_icon/main_multicast03.png", "../../img/main_icon/main_multicast02.png", "../../img/main_icon/main_multicast01.png"];
   var multicastIndex=0;

   var ntp=["../../img/main_icon/main_ntp01.png", "../../img/main_icon/main_ntp02.png", "../../img/main_icon/main_ntp03.png", "../../img/main_icon/main_ntp04.png", "../../img/main_icon/main_ntp05.png", "../../img/main_icon/main_ntp06.png", "../../img/main_icon/main_ntp07.png", "../../img/main_icon/main_ntp07.png", "../../img/main_icon/main_ntp06.png", "../../img/main_icon/main_ntp05.png", "../../img/main_icon/main_ntp04.png", "../../img/main_icon/main_ntp03.png", "../../img/main_icon/main_ntp02.png", "../../img/main_icon/main_ntp01.png"];
   var ntpIndex=0;

   var opc=["../../img/main_icon/main_opc01.png", "../../img/main_icon/main_opc02.png", "../../img/main_icon/main_opc03.png", "../../img/main_icon/main_opc04.png", "../../img/main_icon/main_opc05.png", "../../img/main_icon/main_opc06.png", "../../img/main_icon/main_opc07.png", "../../img/main_icon/main_opc07.png", "../../img/main_icon/main_opc06.png", "../../img/main_icon/main_opc05.png", "../../img/main_icon/main_opc04.png", "../../img/main_icon/main_opc03.png", "../../img/main_icon/main_opc02.png", "../../img/main_icon/main_opc01.png"];
   var opcIndex=0;

   var proxy=["../../img/main_icon/main_proxy01.png", "../../img/main_icon/main_proxy02.png", "../../img/main_icon/main_proxy03.png", "../../img/main_icon/main_proxy04.png", "../../img/main_icon/main_proxy05.png", "../../img/main_icon/main_proxy06.png", "../../img/main_icon/main_proxy07.png", "../../img/main_icon/main_proxy07.png", "../../img/main_icon/main_proxy06.png", "../../img/main_icon/main_proxy05.png", "../../img/main_icon/main_proxy04.png", "../../img/main_icon/main_proxy03.png", "../../img/main_icon/main_proxy02.png", "../../img/main_icon/main_proxy01.png"];
   var proxyIndex=0;

   var rtsp=["../../img/main_icon/main_rtsp01.png", "../../img/main_icon/main_rtsp02.png", "../../img/main_icon/main_rtsp03.png", "../../img/main_icon/main_rtsp04.png", "../../img/main_icon/main_rtsp05.png", "../../img/main_icon/main_rtsp06.png", "../../img/main_icon/main_rtsp07.png", "../../img/main_icon/main_rtsp07.png", "../../img/main_icon/main_rtsp06.png", "../../img/main_icon/main_rtsp05.png", "../../img/main_icon/main_rtsp04.png", "../../img/main_icon/main_rtsp03.png", "../../img/main_icon/main_rtsp02.png", "../../img/main_icon/main_rtsp01.png"];
   var rtspIndex=0;

   var snmp=["../../img/main_icon/main_snmp01.png", "../../img/main_icon/main_snmp02.png", "../../img/main_icon/main_snmp03.png", "../../img/main_icon/main_snmp04.png", "../../img/main_icon/main_snmp05.png", "../../img/main_icon/main_snmp06.png", "../../img/main_icon/main_snmp07.png", "../../img/main_icon/main_snmp07.png", "../../img/main_icon/main_snmp06.png", "../../img/main_icon/main_snmp05.png", "../../img/main_icon/main_snmp04.png", "../../img/main_icon/main_snmp03.png", "../../img/main_icon/main_snmp02.png", "../../img/main_icon/main_snmp01.png"];
   var snmpIndex=0;

   var syslog=["../../img/main_icon/main_syslog01.png", "../../img/main_icon/main_syslog02.png", "../../img/main_icon/main_syslog03.png", "../../img/main_icon/main_syslog04.png", "../../img/main_icon/main_syslog05.png", "../../img/main_icon/main_syslog06.png", "../../img/main_icon/main_syslog07.png", "../../img/main_icon/main_syslog07.png", "../../img/main_icon/main_syslog06.png", "../../img/main_icon/main_syslog05.png", "../../img/main_icon/main_syslog04.png", "../../img/main_icon/main_syslog03.png", "../../img/main_icon/main_syslog02.png", "../../img/main_icon/main_syslog01.png"];
   var syslogIndex=0;

   var modbus=["../../img/main_icon/main_modbus01.png", "../../img/main_icon/main_modbus02.png", "../../img/main_icon/main_modbus03.png", "../../img/main_icon/main_modbus04.png", "../../img/main_icon/main_modbus05.png", "../../img/main_icon/main_modbus06.png", "../../img/main_icon/main_modbus07.png", "../../img/main_icon/main_modbus07.png", "../../img/main_icon/main_modbus06.png", "../../img/main_icon/main_modbus05.png", "../../img/main_icon/main_modbus04.png", "../../img/main_icon/main_modbus03.png", "../../img/main_icon/main_modbus02.png", "../../img/main_icon/main_modbus01.png"];
   var modbusIndex=0;
   
   // $ 안에 .tx .main-icon .icon06.normal > span > img 를 넣어주시면 됩니다.
   var movefolder=setInterval(function(){
	  folderIndex++;
	  if(folderIndex==14){
		folderIndex=0;
     }	
	  $("").attr("src", folder[folderIndex]);
   }, 150);
   var moveftp=setInterval(function(){
	  ftpIndex++;
	  if(ftpIndex==14){
		ftpIndex=0;
     }	
       $("").attr("src", ftp[ftpIndex]);
   }, 150);
   var movesftp=setInterval(function(){
	  sftpIndex++;
	  if(sftpIndex==14){
		sftpIndex=0;
     }	
       $("").attr("src", sftp[sftpIndex]);
   }, 150);
   var movedatabase=setInterval(function(){
	  databaseIndex++;
	  if(databaseIndex==14){
		databaseIndex=0;
     }	
	  $("").attr("src", database[databaseIndex]);
   }, 150);
   var movebroadcast=setInterval(function(){
	  broadcastIndex++;
	  if(broadcastIndex==14){
		broadcastIndex=0;
     }	
	  $("").attr("src", broadcast[broadcastIndex]);
   }, 150);
   var movemulticast=setInterval(function(){
	  multicastIndex++;
	  if(multicastIndex==14){
		multicastIndex=0;
     }	
       $("").attr("src", multicast[multicastIndex]);
   }, 150);
   var moventp=setInterval(function(){
	  ntpIndex++;
	  if(ntpIndex==14){
		ntpIndex=0;
     }	
       $("").attr("src", ntp[ntpIndex]);
   }, 150);
   var moveopc=setInterval(function(){
	  opcIndex++;
	  if(opcIndex==14){
		opcIndex=0;
     }	
       $("").attr("src", opc[opcIndex]);
   }, 150);
   var moveproxy=setInterval(function(){
	  proxyIndex++;
	  if(proxyIndex==14){
		proxyIndex=0;
     }	
       $("").attr("src", proxy[proxyIndex]);
   }, 150);
   var movertsp=setInterval(function(){
	  rtspIndex++;
	  if(rtspIndex==14){
		rtspIndex=0;
     }	
       $("").attr("src", rtsp[rtspIndex]);
   }, 150);
   var movesnmp=setInterval(function(){
	  snmpIndex++;
	  if(snmpIndex==14){
		snmpIndex=0;
     }	
       $("").attr("src", snmp[snmpIndex]);
   }, 150);
   var movesyslog=setInterval(function(){
	  syslogIndex++;
	  if(syslogIndex==14){
		syslogIndex=0;
     }	
       $("").attr("src", syslog[syslogIndex]);
   }, 150);
   var movemodbus=setInterval(function(){
	  modbusIndex++;
	  if(modbusIndex==14){
		modbusIndex=0;
     }	
       $("").attr("src", modbus[modbusIndex]);
   }, 150);
   
});