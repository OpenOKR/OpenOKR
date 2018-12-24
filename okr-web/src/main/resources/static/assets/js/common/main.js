var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {
        /**
         * 加载OKR列表数据，成功后渲染OKR列表html结构
         */
        loadOKRList: function () {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#OKRList").block();
                $.ajax({
                    url: App["contextPath"] + "/getAllOkrList.json",
                    type: "GET",
                    dataType: "json"
                }).done(function (res) {
                    res && _this.buildOKRList(res);
                }).always(function () {
                    $("#OKRList").unblock();
                });
            });
        },

        /**
         * 构建OKR列表
         */
        buildOKRList: function (res) {
            require(["Underscore"], function () {
                var $OKRListItem = $("#OKRList");
                $.each(res.info, function(idx, item) {
                    var templateText =
                        '<div class="col-sm-3">' +
                        '    <div class="card-area2">' +
                        '       <strong class="cart-tag">[%=typeName%]</strong>' +
                        '       <div class="area-report">' +
                        '           <h3>[%=name%]</h3>' +
                        '           <p>[%=description%]</p>' +
                        '           <div class="participant">' +
                        '               <span class="name">参与人员：</span>' +
                        '               [%if(joinUsers.length>0){%]' +
                        '               <ul class="participant-list">' +
                        '                   [%_.each(joinUsers, function(user, idx){%]' +
                        '                       [%if(idx < 3){%]' +
                        '                           <li class="part-item"><span><img src="/assets/images/temp/pic.png"/></span></li>' +
                        '                       [%}%]' +
                        '                   [%});%]' +
                        '                   <li class="part-item"><a href=""><i class="iconfont icon-more"></i></a></li>' +
                        '               </ul>' +
                        '               [%}%]' +
                        '           </div>' +
                        '           <div class="area-charts">' +
                        '               <div id="[%=id%]" style="width: 100%;height: 100%;"></div>' +
                        '               <div class="charts-total"><span class="num" data-end="[%=progress%]">[%=progress%]</span><em>%</em></div>' +
                        '           </div>' +
                        '       </div>' +
                        '   </div>' +
                        '</div>';
                    var html = UnderscoreUtil.getHtmlByText(templateText, item);
                    $OKRListItem.append(html);
                    pageObj.pieEchartsFunc(item.id, item.type, item.progress);
                });
            });
        },

        pieEchartsFunc: function (id, type, progress) {
            require(["echarts"], function (echarts){
                var color = '#83affc';
                switch (type) {
                    case '2': color = '#fdb44d'; break;
                    case '3': color = '#f57677'; break;
                }
                var option = {
                    title: {text: '当前进度', x: 'center', y:"80%", textStyle: {fontSize: '12px', color:"#545454"}},
                    tooltip: {trigger: 'item', formatter: "{b} {d}%"},
                    legend: {orient: 'vertical', x: 'left', y:"-20%", data:['已完成','']},
                    series: [
                        {name:'', type:'pie', radius: ['70%', '80%'], avoidLabelOverlap: false, color:[color,'#dedede'],
                            label: {normal: {show: false, position: 'center'}, emphasis: {show: true, textStyle: {fontSize: '22px'}}},
                            labelLine: {normal: {show: false}},
                            data:[{value:progress, name: '已完成'}, {value: (100 - progress), name: '未完成'}]
                        }
                    ]
                };
                var myChart = echarts.init(document.getElementById(id));
                myChart.setOption(option);
            });
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.loadOKRList(); //初始化OKR列表
    });
});