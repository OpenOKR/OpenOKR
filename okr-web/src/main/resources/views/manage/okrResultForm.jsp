<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="新增/编辑Result"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrResultForm.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div style="width: 680px; border-radius: 5px; background-color: #fff; margin: 0 auto">
    <div class="ui-form inner3">
        <ul class="form-grid font4">
            <li class="col-sm-11">
                <label class="form-lab">目标：</label>
                <div class="form-control">
                    <input id="objectName" name="objectName" type="text" class="inp" placeholder="请输入" value="${resultVO.objectName}"/>
                    <input id="objectId" name="objectId" type="hidden"/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">关键结果：</label>
                <div class="form-control">
                    <input id="name" name="name" type="text" class="inp" placeholder="请输入" value="${resultVO.name}"/>
                </div>
            </li>
            <li class="col-sm-11 ">
                <label class="form-lab">描述：</label>
                <div class="form-control">
                    <textarea id="description" name="description" class="textarea" placeholder="请输入">${resultVO.description}</textarea>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">评价单位：</label>
                <div class="form-control">
                    <p class="radio-inline">
                        <c:forEach items="${metricUnitEnumList}" var="metricUnit">
                            <span class="radio">
                                <input name="metricUnit" type="radio" value="${metricUnit.code}" checked="${resultVO.metricUnit == metricUnit.code ? 'checked' : ''}"/>
                                <i class="icon"></i><em>${metricUnit.name}</em>
                            </span>
                        </c:forEach>
                    </p>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">起始值：</label>
                <div class="form-control">
                    <input id="initialValue" name="initialValue" type="text" class="inp" placeholder="请输入" value="${resultVO.initialValue}"/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">目标值：</label>
                <div class="form-control">
                    <input id="targetValue" name="targetValue" type="text" class="inp" placeholder="请输入" value="${resultVO.targetValue}"/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">协同者：</label>
                <div class="form-control">
                    <input id="users" name="users" type="text" class="inp" placeholder="请输入协同者，可多选"/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">影响团队：</label>
                <div class="form-control">
                    <input id="teams" name="teams" type="text" class="inp" placeholder="请输入影响团队，可多选"/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">完成时间：</label>
                <div class="form-control">
                    <input id="endTs" name="endTs" type="text" class="inp" style="width: 60%;" placeholder="完成时间">
                    <img id="iEndTs" src="${staticContextPath}/assets/images/all/date-icon.png">
                </div>
            </li>
        </ul>
    </div>
</div>
<%@ include file="/views/application/_include_bottom.jsp" %>