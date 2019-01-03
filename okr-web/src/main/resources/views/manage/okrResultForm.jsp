<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="新增/编辑关键结果"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrResultForm.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div style="width: 680px; height: auto; border-radius: 5px; background-color: #fff; margin: 0 auto;">
    <form id="resultForm" class="ui-form inner3">
        <input id="id" name="id" type="hidden" value="${resultVO.id}">
        <ul class="form-grid font4">
            <li class="col-sm-11">
                <label class="form-lab">目标：</label>
                <div class="form-control">
                    <input id="objectName" name="objectName" type="text" readonly class="inp" placeholder="请输入" value="${resultVO.objectName}"/>
                    <input id="objectId" name="objectId" type="hidden" value="${resultVO.objectId}" readonly/>
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
                <label class="form-lab">协同者：</label>
                <div class="form-control">
                    <c:set var="joinUsersIdStr" value=""></c:set>
                    <c:set var="joinUsersStr" value=""></c:set>
                    <c:if test="${resultVO.joinUsers != null && resultVO.joinUsers.size() > 0}">
                        <c:forEach items="${resultVO.joinUsers}" var="user" varStatus="status">
                            <c:if test="${user != null}">
                                <c:choose>
                                    <c:when test="${!status.last}">
                                        <c:set var="joinUsersIdStr" value="${joinUsersIdStr.concat(user.id).concat(',')}"></c:set>
                                        <c:set var="joinUsersStr" value="${joinUsersStr.concat(user.realName).concat(',')}"></c:set>
                                    </c:when>
                                    <c:otherwise>

                                        <c:set var="joinUsersIdStr" value="${joinUsersIdStr.concat(user.id)}"></c:set>
                                        <c:set var="joinUsersStr" value="${joinUsersStr.concat(user.realName)}"></c:set>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <input id="usersId" name="usersId" type="hidden" value="${joinUsersIdStr}">
                    <input id="users" name="users" type="text" class="inp" onclick="pageObj.showTree();" value="${joinUsersStr}" readonly placeholder="请选择协同者，可多选"/>
                    <div id="treeContent" style="border:1px solid #000; height: 160px; display: none; overflow-y: auto;">
                        <ul id="ulUsersTree" class="ztree" style="overflow-y: auto;"></ul>
                    </div>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">评价单位：</label>
                <div class="form-control">
                    <p class="radio-inline">
                        <c:forEach items="${metricUnitEnumList}" var="metricUnit">
                            <c:if test="${metricUnit.code != ''}">
                                <label class="radio">
                                    <input name="metricUnit" type="radio" value="${metricUnit.code}" ${resultVO.metricUnit == metricUnit.code ? "checked" : ""}/>
                                    <i class="icon"></i><em>${metricUnit.name}</em>
                                </label>
                            </c:if>
                        </c:forEach>
                    </p>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">目标值：</label>
                <div class="form-control">
                    <input id="targetValue" name="targetValue" type="text" class="inp" placeholder="请输入" value="${resultVO.targetValue}"/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">起始值：</label>
                <div class="form-control">
                    <input id="initialValue" name="initialValue" type="text" class="inp" placeholder="请输入" value="${resultVO.initialValue}"/>
                </div>
            </li>
            <%--<li class="col-sm-11">--%>
                <%--<label class="form-lab">影响团队：</label>--%>
                <%--<div class="form-control">--%>
                    <%--<c:if test="${resultVO.joinUsers != null && resultVO.joinUsers.size() > 0}">--%>
                        <%--<c:forEach items="${resultVO.joinUsers}" var="user">--%>
                            <%--<c:if test="${user != null}">--%>
                                <%--<input data-name="joinUsers" type="hidden" value="${user.realName}">--%>
                            <%--</c:if>--%>
                        <%--</c:forEach>--%>
                    <%--</c:if>--%>
                    <%--<c:forEach items=""></c:forEach>--%>
                    <%--<input id="teams" name="teams" type="text" class="inp" placeholder="请输入影响团队，可多选"/>--%>
                <%--</div>--%>
            <%--</li>--%>
            <li class="col-sm-11">
                <label class="form-lab">完成时间：</label>
                <div class="form-control">
                    <div class="datepicker">
                        <input id="endTs" name="endTs" type="text" class="inp" value="${endTs}" style="width: 60%;" placeholder="完成时间">
                        <img id="iEndTs" src="${staticContextPath}/assets/images/all/date-icon.png">
                    </div>
                </div>
            </li>
        </ul>
    </form>
</div>
<%@ include file="/views/application/_include_bottom.jsp" %>