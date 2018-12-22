<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/_include/_jsp_tags.jsp" %>
<%@ include file="/views/_include/_jsp_variable.jsp" %>
<c:set var="pageJs" value="${staticContextPath}/assets/js/common/main.js"/>
<c:set var="pageTitle" value="首页"/>
<%@ include file="/views/application/_include_top_main.jsp" %>
<div class="wapper">
    <p class="crumbs-bar">
        <a href="">OKR管理系统</a>
        <i class="iconfont icon-arrowR"></i>
        <a href="">首页</a>
    </p>
    <div class="card-box">
        <div class="card-title">
            <div class="fl">
                <span class="title">OKR列表</span>
            </div>
        </div>
        <div class="row card-area">
            <div class="col-sm-3">
                <div class="card-area2" >
                    <strong class="cart-tag">公司</strong>
                    <div class="area-report">
                        <h3>Q4新客户数量增加50%</h3>
                        <P>优化产品体验，第四季度新客户数量增加50%</P>
                        <div class="participant">
                            <span class="name">参与人员：</span>
                            <ul class="participant-list">
                                <li class="part-item"><span><img src="${staticContextPath}/assets/images/temp/pic.png"/></span></li>
                                <li class="part-item"><span><img src="${staticContextPath}/assets/images/temp/pic.png"/></span></li>
                                <li class="part-item"><a href=""><i class="iconfont icon-more"></i></a></li>
                            </ul>
                        </div>
                        <div class="area-charts">
                            <div id="ech1" style="width:100%;height:100%;"></div>
                            <div class="charts-total"><span class="num" data-end="20">20</span><em>%</em></div>
                        </div>
                    </div>

                </div>
            </div>
            <!--第一个卡片-->
        </div>
    </div>
    <div class="card-box mt20">
        <div class="row">
            <div class="col-sm-5">
                <div class="card-title ">
                    <div class="fl">
                        <span class="title">OKR列表</span>
                    </div>
                </div>
                <div class="card-area3" >
                    <div class="card-title uil">
                        <ul class="nav-tabs2 fr" id="nav-tabs2">
                            <li data-nav="option_me" class="active">我的</li>
                            <li data-nav="option_trim">团队</li>
                            <li data-nav="option_company">公司</li>
                        </ul>
                    </div>
                    <div class="card-area" style="height: 288px;">
                        <div id="ech5" style="width:100%;height:288px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-7">
                <div class="card-title ">
                    <div class="fl">
                        <span class="title">OKR提醒</span>
                        <em class="em-radio2 red">10</em>
                    </div>
                    <div class="action">
                        <a href="" class="txt-all uir">查看全部<i class="iconfont icon-arrowDR"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/views/application/_include_bottom.jsp" %>