<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="目标审核"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrObjectAudit.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div style="width: 600px;height: 200px;border-radius: 5px; background-color: #fff; margin: 0 auto; overflow-y: auto;">
    <form id="objectAuditForm" class="ui-form">
        <input name="id" type="hidden" value="${message.id}">
        <input name="targetId" type="hidden" value="${message.targetId}">
        <ul class="form-grid font4">
            <li class="col-sm-12">
                <label class="form-lab">描述：</label>
                <div class="form-control" style="line-height: 24px;">
                    ${message.content}
                </div>
            </li>
            <li class="col-sm-12">
                <label class="form-lab">是否同意：</label>
                <div class="form-control">
                    <p class="radio-list">
                        <label class="radio"><input name="radio" type="radio" value="0"/><i class="icon"></i><em>不同意</em></label>
                        <label class="radio"><input name="radio" type="radio" value="1"/><i class="icon"></i><em>同意</em></label>
                    </p>
                </div>
            </li>
            <li class="col-sm-12" id="contentLi" style="display: none;">
                <label class="form-lab">驳回理由：</label>
                <div class="form-control">
                    <textarea id="content" name="content" class="textarea" placeholder="内容描述"></textarea>
                </div>
            </li>
        </ul>
    </form>
</div>
<%@ include file="/views/application/_include_bottom.jsp"%>