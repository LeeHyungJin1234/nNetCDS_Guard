<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>

<script>
  const m = "${sessionScope.RSAModulus}";
  const e = "${sessionScope.RSAExponent}";

  const rsaPublicKey = new RSAKey();
  rsaPublicKey.setPublic(m, e);

  const _csrf_header_name = "${_csrf.headerName}";
  const _csrf_param_name = "${_csrf.parameterName}";
  const _csrf_token = "${_csrf.token}";

  $(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(_csrf_header_name, _csrf_token);
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