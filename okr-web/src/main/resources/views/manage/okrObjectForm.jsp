<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageTitle" value="新增/编辑Object"/>
<c:set var="pageJs" value="${staticContextPath}/assets/js/manage/okrObjectForm.js"/>
<%@ include file="/views/application/_include_top.jsp" %>
<div style="width: 680px; border-radius: 5px; background-color: #fff; margin: 20px auto">
    <form class="ui-form inner3" id="objectForm">
        <ul class="form-grid font4">
            <input id="id" name="id" type="hidden" value="${objectVO.id}">
            <input id="type" name="type" type="hidden" value="${objectVO.type}"/>
            <li class="col-sm-11">
                <label class="form-lab">目标：</label>
                <div class="form-control">
                    <input id="name" name="name" type="text" class="inp" placeholder="请输入" value="${objectVO.name}"/>
                </div>
            </li>
            <li class="col-sm-11 ">
                <label class="form-lab">描述：</label>
                <div class="form-control">
                    <textarea id="description" name="description" class="textarea" placeholder="请输入">${objectVO.description}</textarea>
                </div>
            </li>
            <%--<li class="col-sm-11">--%>
                <%--<label class="form-lab">负责人：</label>--%>
                <%--<div class="form-control">--%>
                    <%--<div class="search">--%>
                        <%--<input id="ownerId" name="ownerId" type="text" placeholder="请选择负责人名称"/><em class="icon"></em>--%>
                    <%--</div>--%>
                    <%--<p class="mt10">--%>
                        <%--<a class="text-primary mr10" onclick="$('#ownerId').val('${currentUserId}')">分配给我</a>--%>
                    <%--</p>--%>
                <%--</div>--%>
            <%--</li>--%>
            <li class="col-sm-11">
                <label class="form-lab">父目标：</label>
                <div class="form-control">
                    <div class="select">
                        <input id="parentName" name="parentName" type="text" placeholder="请选择父目标，可以不选择"/><em class="icon"></em>
                        <input id="parentId" name="parentId" type="hidden" value="${objectVO.parentId}"/>
                    </div>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">影响团队：</label>
                <div class="form-control">
                    <div class="search">
                        <input id="teamNames" name="teamNames" type="text" placeholder="请选择影响团队，可多选"/><em class="icon"></em>
                    </div>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">标签：</label>
                <div class="form-control">
                    <input id="labelNames" name="labelNames" type="text" class="inp" placeholder="请输入标签，可多选"/>
                </div>
            </li>
            <li class="col-sm-11">
                <label class="form-lab">目标把握：</label>
                <div class="form-control ">
                    <div class="of-h">
                        <div id="drag-bar" class="drag-bar " >
                            <strong class="drag-num"><span class="num" id="confidenceLevelSpan">${objectVO.confidenceLevel}</span>成</strong>
                            <input id="confidenceLevel" name="confidenceLevel" type="hidden" value="${objectVO.confidenceLevel}">
                            <div id="drag-hand" class="drag-hand" ></div>
                            <div id="drag-past" class="drag-past"></div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </form>
</div>
<script type="text/javascript">
    var pageObj = {type: '${objectVO.type}'};
</script>
<%@ include file="/views/application/_include_bottom.jsp" %>