<%@ page session="true" language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="nnsp.common.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<meta name="_csrf_header" content="${_csrf.headerName}">
<meta name="_csrf" content="${_csrf.token}">
<script type="text/javascript">
$(function () {
    var token = $("meta[name='_csrf']").attr('content');
    var header = $("meta[name='_csrf_header']").attr('content');
    if(token && header) {
        $(document).ajaxSend(function(event, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }
});

// //스택 추가
// history.pushState(null, "", location.href);
// // 뒤로라기 이벤트감지 -> 현재페이지로 이동
// window.onpopstate = function (event) {
// 	history.forward();
// };
history.pushState(null, null, document.URL);
window.addEventListener('popstate', function() {
    history.pushState(null, null, document.URL);
    //alert('뒤로가기 버튼이 눌렸습니다!');
});
  
function charCodeRotate(charCode, rotate) {
    let result = charCode + (rotate % 94);

    if (result > 126) {
      result -= 94;
    } else if (result < 33) {
      result += 94;
    }

    return result;
  }

  function pwKeyDownEventHandler(event) {

    let key = String(event.key);
    let charCode = key.charCodeAt(0);
    if (key.length === 1 && charCode >= 33 && charCode <= 126) {

      const array = new Uint32Array(32);
      const code = String.fromCharCode(charCodeRotate(charCode, 32));

      const length = event.target.value.length;
      const maxLength = 130;
      if (length >= maxLength) {
        return false;
      }

      event.target.value += code;

      event.preventDefault();
      event.stopImmediatePropagation();
      return false;
    }

    return true;
  }
</script>