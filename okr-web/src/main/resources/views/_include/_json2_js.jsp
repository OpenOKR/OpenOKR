<c:if test="${jsDebug}">
    <script async defer src="${staticContextPath}/assets/js/framework/lib/JSON/2014-02-04/json2.js"></script>
</c:if>
<c:if test="${!jsDebug}">
    <script async defer src="${staticContextPath}/assets/js/framework/lib/JSON/2014-02-04/json2.min.js"></script>
</c:if>