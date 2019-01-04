<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="协同审核"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrResultAudit.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div style="width: 600px;height: 440px;border-radius: 5px; background-color: #fff; margin: 0 auto;">
    <form id="resultAuditForm" class="ui-form inner3">
        <input name="id" type="hidden" value="${message.id}">
        <strong class="search-title">${message.content}</strong>
        <ul class="form-grid font4">
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
                <label class="form-lab">理由：</label>
                <div class="form-control">
                    <textarea id="content" name="content" class="textarea" placeholder="内容描述"></textarea>
                </div>
            </li>
            <li class="col-sm-12" data-name="addResult" style="display: none;">
                <label class="form-lab">归属目标：</label>
                <div class="form-control">
                    <div class="select">
                        <input id="objectName" name="objectName" type="text" placeholder="请选择归属目标"><i class="icon"></i>
                    </div>
                </div>
            </li>
            <li class="col-sm-12" data-name="addResult" style="display: none;">
                <label class="form-lab">主要结果：</label>
                <div class="form-control">
                    <input id="name" name="name" type="text" class="inp" placeholder="请输入"/>
                </div>
            </li>
            <li class="col-sm-12" data-name="addResult" style="display: none;">
                <label class="form-lab">内容描述：</label>
                <div class="form-control">
                    <textarea id="description" name="description" class="textarea" placeholder="请输入"></textarea>
                </div>
            </li>
            <li class="col-sm-12" data-name="addResult" style="display: none;">
                <label class="form-lab">执行单位：</label>
                <div class="form-control">
                    <p class="radio-inline">
                        <c:forEach items="${metricUnitEnumList}" var="metricUnit">
                            <c:if test="${metricUnit.code != ''}">
                                <label class="radio">
                                    <input name="metricUnit" type="radio" value="${metricUnit.code}"/>
                                    <i class="icon"></i><em>${metricUnit.name}</em>
                                </label>
                            </c:if>
                        </c:forEach>
                    </p>
                </div>
            </li>
            <li class="col-sm-12" style="display: none;">
                <label class="form-lab">目标值：</label>
                <div class="form-control">
                    <input id="targetValue" name="targetValue" type="text" class="inp" placeholder="请输入" value=""/>
                </div>
            </li>
            <li class="col-sm-12" style="display: none;">
                <label class="form-lab">起始值：</label>
                <div class="form-control">
                    <input id="initialValue" name="initialValue" type="text" class="inp" placeholder="请输入" value=""/>
                </div>
            </li>
            <li class="col-sm-12" data-name="addResult" style="display: none;">
                <label class="form-lab">完成时间：</label>
                <div class="form-control">
                    <div class="datepicker">
                        <input id="endTs" name="endTs" type="text" class="inp" value="" placeholder="请选择预计完成时间">
                        <img id="iEndTs" src="${staticContextPath}/assets/images/all/date-icon.png">
                    </div>
                </div>
            </li>
        </ul>
    </form>
</div>
<%@ include file="/views/application/_include_bottom.jsp"%>