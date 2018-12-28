<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="每周更新"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div style="width: 680px; border-radius: 5px; background-color: #fff; margin: 0 auto">
    <form class="ui-form inner3" id="checkinForm">
        <input id="resultId" name="resultId" type="hidden" value="${resultId}">
        <ul class="form-grid font4">
            <li class="col-sm-11">
                <label class="form-lab">关键结果：</label>
                <div class="form-control">
                    <input id="objectName" name="objectName" type="text" class="inp" value="${resultVO.name}" readonly/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">目标值：</label>
                <div class="form-control">
                    <input id="targetValue" name="targetValue" type="text" class="inp" value="${resultVO.targetValue}" readonly/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">当前值：</label>
                <div class="form-control">
                    <input id="currentValue" name="currentValue" type="text" class="inp" placeholder="请输入"/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">执行状态：</label>
                <div class="form-control">
                    <p class="radio-inline">
                        <c:forEach items="${executeStatusEnum}" var="executeStatus">
                            <label class="radio">
                                <input name="metricUnit" type="radio" value="${executeStatus.code}"/>
                                <i class="icon"></i><em>${executeStatus.name}</em>
                            </label>
                        </c:forEach>
                    </p>
                </div>
            </li>
            <li class="col-sm-11 ">
                <label class="form-lab">描述：</label>
                <div class="form-control">
                    <textarea id="description" name="description" class="textarea" placeholder="请输入"></textarea>
                </div>
            </li>
        </ul>
    </form>
</div>
<%@ include file="/views/application/_include_bottom.jsp" %>