<%@page import="com.zzheng.framework.mybatis.dao.pojo.Page"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pagenav clearfix">
	<div class="fr">
		<span class="pagelist">
			<%
				String pageName = request.getParameter("pageName");
				Page mypage = (Page)request.getAttribute(pageName);
				if(mypage != null) {
			%>
			<a class="" id="<%=pageName%>FirstPage" data="1"><<</a>
			<%
					if (mypage.getTotalPage() > 1 && mypage.getCurrentPage() > 1) {
			%>
				<a class="pre-btn" id="<%=pageName%>PrevPage" data="<%=(mypage.getCurrentPage() - 1)%>"><</a>
			<%
					}
					// 起始页码
					int startPageNo = mypage.getCurrentPage();
					// 最多5页
					int maxPageNo = 5;
					if (mypage.getTotalPage() < maxPageNo) {
						maxPageNo = mypage.getTotalPage();
					}
					// （头3页，起始于1；如果超过，让当前页放中间）
					if (startPageNo <= 3) {
						startPageNo = 1;
					}
					else if (startPageNo > 3) {
						// 最后两页不跳转
						if (startPageNo + 2 > mypage.getTotalPage()) {
							startPageNo = mypage.getTotalPage() - 4;
						}
						else {
							startPageNo = startPageNo -2;
						}
					}

					for(int i = startPageNo; i < maxPageNo + startPageNo; i++){
						if(mypage.getCurrentPage() == i){
			%>
							<a id="<%=pageName%>CurrentPage" class="current" data="<%=i %>"><%=i%></a>
			<%
						}else{
			%>
						<a class="<%=pageName%>MyPage" data="<%=i %>"><%=i %></a>
			<%
						}
					}
					if (mypage.getTotalPage() > 1 && mypage.getCurrentPage() < mypage.getTotalPage()) {
			%>
			<a class="next-btn" id="<%=pageName%>NextPage" data="<%=(mypage.getCurrentPage() +1)%>">></a>
			<%
					}
			%>
			<a id="<%=pageName%>LastPage" data="<%=mypage.getTotalPage()%>"> >> </a>
			<%
				}
			%>
		</span>
	</div>
</div>
<!--分页 End-->
