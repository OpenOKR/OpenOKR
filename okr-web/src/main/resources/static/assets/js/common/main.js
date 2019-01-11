var pageObj = pageObj || {};
require(["jQuery", "countUp"], function () {
    $.extend(pageObj, {

        options: {useEasing : true, useGrouping : true},
        barOptions: [],

        /**
         * 加载OKR列表数据，成功后渲染OKR列表html结构
         */
        loadOKRList: function () {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#OKRList").block();
                $.ajax({
                    url: App["contextPath"] + "/getAllOkrList.json?timeSessionId=" + top.mainObj.getCurrentTimeSession().id,
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
                        '       <strong class="cart-tag">[%=teamName%]</strong>' +
                        '       <div class="area-report">' +
                        '           <h3><a onclick="top.mainObj.menuClick(null, App.contextPath + \'/manage/okrObject/okrDetail.htm?id=[%=id%]&type=[%=type%]\', \'menu-2\');">[%=name%]</a></h3>' +
                        '           <p>[%=description%]</p>' +
                        '           <div class="participant">' +
                        '               <span class="name">参与人员：</span>' +
                        '               [%if(joinUsers.length>0){%]' +
                        '               <ul class="participant-list">' +
                        '                   [%_.each(joinUsers, function(user, idx){%]' +
                        '                       [%if(idx < 3){%]' +
                        '                        [%if(!_.isNull(user)){%]' +
                        '                       [%if(user.realName.length>2)%]' +
                        '                           <li class="part-item"><span class="image">[%=user.realName.substr(1,2)%]</span></li> [%else%]<li class="part-item"><span class="image">[%=user.realName%]</span></li>' +
                        '                       [%;%]' +
                        '                       [%}%]' +
                        '                       [%}%]' +
                        '                   [%});%]' +
                        '                   <li class="part-item"><a href="javascript:void(0);"><i class="iconfont icon-more"></i></a></li>' +
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
                var color = '#f57677';
                if (progress >= 40 && progress < 80) {
                    color = '#fdb44d';
                } else if (progress >= 80) {
                    color = '#83affc';
                }

                //0-40 #f57677,40-80 #fdb44d,80-100 #83affc
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
        },

        loadOKRExecution: function (type) {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#OKRExecution").block();
                $.ajax({
                    url: App["contextPath"] + "/execution.json?timeSessionId=" + top.mainObj.getCurrentTimeSession().id + "&type=" +type,
                    type: "GET",
                    dataType: "json"
                }).done(function (res) {
                    res && _this.buildOKRExecution(type, res);
                }).always(function () {
                    $("#OKRExecution").unblock();
                });
            });
        },

        buildOKRExecution: function (type, res) {
            require(["echarts"], function (echarts){
                var color1 = '#4d7fff', color2 = '#82aefc', color3 = '#4d7ffe';
                var option = {
                    tooltip : {trigger: 'axis', axisPointer : {type : 'shadow'}},
                    grid: {left: '2%', right: '2%', bottom: '3%', top:'12%', containLabel: true},
                    xAxis : [{type : 'category', data : ['未启动', '正常', '有风险', '暂停', '提前终止', '完成'], axisTick: {alignWithLabel: true}}],
                    yAxis : [{type : 'value', axisLine: {lineStyle: {color: '#a8a8a8'}},
                        splitLine: {show: true, lineStyle:{type:'dashed', color:'#ebebeb'}}}],
                    series : [{name:'执行情况', type:'bar', smooth:false, barWidth: '45%', color: [color1], itemStyle: {
                        normal: {barBorderRadius: 3,
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1,
                                [{offset: 0, color: color2}, {offset: 1, color: color3}])
                            }
                        },
                        data:[res.info.count0, res.info.count1, res.info.count2, res.info.count3, res.info.count4, res.info.count5]
                    }]
                };
                var barEcharts = echarts.init(document.getElementById('OKRExecutionBarEcharts'));
                barEcharts.setOption(option);
            });
        },

        loadOKRMessage: function () {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#message").block();
                $.ajax({
                    url: App["contextPath"] + "/getOkrMessage.json",
                    type: "GET",
                    dataType: "json"
                }).done(function (res) {
                    res && _this.buildOKRMessage(res);
                }).always(function () {
                    $("#message").unblock();
                });
            });
        },

        buildOKRMessage: function (res) {
            require(["Underscore", "jQueryUtils", "AppUtils"], function () {
                $('#messageCount').html(res.info.length);
                var $messageItem = $("#messageItem");
                if (res.info.length > 0) {
                    $.each(res.info, function (idx, item) {
                        item.href = App.contextPath + "/manage/okrMessage/index.htm";
                        item.createTsStr = $.DateUtils.getDateTimeString(new Date(item.createTs));
                    });
                    var markList = enumUtil.getEnum("messageMarkList.json");
                    var templateText =
                        '<ul class="new-list">' +
                        '   [%_.each(list, function(msg, idx){%]' +
                        '       <li>' +
                        '           <a class="new-item" onclick="top.mainObj.menuClick(null, \'[%=msg.href%]\', \'menu-4\');">' +
                        '               <i class="[%=markList[msg.mark - 1].cssClass%]"></i>' +
                        '               <h4>[%=msg.title%]</h4>' +
                        '               <p>[%=msg.createTsStr%]</p>' +
                        '               <div class="action">' +
                        '                   [%if(msg.isProcessed == 1 && msg.isRead == 1){%]' +
                        '                       <i class="iconfont icon-dot text-muted"></i>' +
                        '                   [%} else {%]' +
                        '                       <i class="iconfont icon-dot text-warning"></i>' +
                        '                   [%}%]' +
                        '                   <i class="iconfont icon-arrowR"></i>' +
                        '               </div>' +
                        '           </a>' +
                        '       </li>' +
                        '   [%});%]' +
                        '</ul>';
                    var html = UnderscoreUtil.getHtmlByText(templateText, {list:res.info, markList: markList});
                    $messageItem.append(html);
                } else {
                    $messageItem.append("<div class='meg-outer'>" +
                        "   <div class='meg-in'>" +
                        "       <em class='icon none1'></em>" +
                        "       <p class='meg-desc'>暂无新消息</p>" +
                        "   </div>" +
                        "</div>");
                }
            });
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.loadOKRList(); //初始化OKR列表
        $(".charts-total .num").each(function(){
            var countUp = new CountUp(this, 0, $(this).data("end"), 2, 1, pageObj.options);
            countUp.start();
        });
        pageObj.loadOKRExecution('1'); //初始化OKR执行情况
        $("#nav-tabs2 li").click(function(){
            var $this=$(this);
            $this.addClass("active").siblings("li").removeClass("active");
            pageObj.loadOKRExecution($this.data("type"));
        });
        pageObj.loadOKRMessage();
    });
});