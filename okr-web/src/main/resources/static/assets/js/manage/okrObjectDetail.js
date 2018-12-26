var pageObj = pageObj || {};
require(["jQuery"], function () {
    $.extend(pageObj, {

        options: {useEasing: true, useGrouping: true},

        loadOKRObjectDetail: function () {
            var _this = this;
            require(["jQueryBlockUI"], function () {
                $("#detail").block();
                $.ajax({
                    url: App["contextPath"] + "/manage/okrObject/getOkrDetail.json",
                    type: "POST",
                    data: JSON.stringify({searchVO: {objectId: pageObj.id, type: pageObj.type}}),
                    contentType: 'application/json;charset=utf-8'
                }).done(function (res) {
                    res && _this.buildOKRObjectDetail(res.info);
                }).always(function () {
                    $("#detail").unblock();
                });
            });
        },

        buildOKRObjectDetail: function (object) {
            require(["Underscore", "jQueryUtils", "AppUtils"], function () {
                var $okrObjectDetail = $("#detail"); $okrObjectDetail.empty();
                var $okrObjectHistory = $('#historyList'); $okrObjectHistory.empty();
                var statusList = enumUtil.getEnum("objectivesStatusList.json");
                var executeList = enumUtil.getEnum("executeStatusList.json");
                var okrHeader =
                    '<div class="okr-header">' +
                    '   <div class="area-charts">' +
                    '       <div id="[%=object.id%]" style="width: 100%;height: 100%;"></div>' +
                    '       <div class="charts-total">' +
                    '           <span class="num" data-end="[%=object.progress%]" data-new="[%=object.progress%]">[%=object.progress%]</span>' +
                    '           <em>%</em>' +
                    '       </div>' +
                    '   </div>' +
                    '   <div class="okr-header-desc">' +
                    '       <h3 class="name">' +
                    '           [%=object.name%]' +
                    '           <div class="action">' +
                    '               <em class="em-start [%=statusList[object.status].cssClass%]">[%=statusList[object.status].name%]</em>' +
                    '               <a class="btn-del text-primary" onclick="pageObj.deleteFunc([%=object.id%]);"><i class="icon-del"></i>删除</a>' +
                    '               <a class="btn-del text-primary"><i class="icon-edit"></i>编辑</a>' +
                    '           </div>' +
                    '       </h3>' +
                    '       <p>[%=object.description%]</p>' +
                    '   </div>' +
                    '   <div class="desc">' +
                    '       <span class="txt-item text-muted">把握度：</span>' +
                    '       <span id="confidenceLevel" class="txt-item" data-value="[%=object.confidenceLevel%]">把握</span>' +
                    '   </div>' +
                    '</div>';
                var okrCon = '<div class="okr-con">' +
                    '   <ul class="okr-list">' +
                    '       [%if(!_.isNull(object.resultsExtList) && object.resultsExtList.length>0){%]' +
                    '           [%_.each(object.resultsExtList, function(item){%]' +
                    '               <li>' +
                    '                   <div class="okr-list-in">' +
                    '                       <h4>' +
                    '                           K1：[%=item.name%]' +
                    '                           <div class="action">' +
                    '                               <span class="txt-all [%=executeList[item.status].cssClass%]">' +
                    '                                   <i class="iconfont icon-dot"></i>[%=executeList[item.status].name%]' +
                    '                               </span>' +
                    '                           </div>' +
                    '                       </h4>' +
                    '                       <div class="participant">' +
                    '                           <span class="name">参与人员：</span>' +
                    '                           <ul class="participant-list">' +
                    '                               [%if(!_.isNull(object.joinUsers) && object.joinUsers.length>0){%]' +
                    '                                   [%_.each(object.joinUsers, function(user){%]' +
                    '                                       <li class="part-item"><span><img src="/assets/images/temp/pic.png"></span></li>' +
                    '                                   [%});%]' +
                    '                                   <li class="part-item"><a href=""><i class="iconfont icon-more"></i></a></li>' +
                    '                               [%}%]' +
                    '                           </ul>' +
                    '                       </div>' +
                    '                   </div>' +
                    '                   <div class="okr-list-tab">' +
                    '                       <p class="scroll-bar">' +
                    '                           <i class="complete"></i>' +
                    '                           <i class="new"></i>' +
                    '                           <span class="vals">' +
                    '                               <em class="num" data-end="[%=item.preProgress%]" data-new="[%=item.progress%]">[%=item.progress%]</em>%' +
                    '                           </span>' +
                    '                       </p>' +
                    '                       <div class="action">' +
                    '                           <a class="btn-del text-primary"><i class="icon-del"></i>删除</a>' +
                    '                           <a class="btn-other text-primary"><i class="icon-refresh"></i>进度</a>' +
                    '                           <a class="btn-del text-primary"><i class="icon-edit"></i>编辑</a>' +
                    '                       </div>' +
                    '                   </div>' +
                    '               </li>' +
                    '           [%});%]' +
                    '       [%}%]' +
                    '   </ul>' +
                    '   <div class="okr-ohter">' +
                    '       <a href="" class="txt-all text-primary"><i class="iconfont icon-add"></i>新增KR</a>' +
                    '   </div>' +
                    '</div>' +
                    '<div class="card-footer">' +
                    '   <span class="mes-area waring">' +
                    '       <i class="iconfont icon-waring"></i>' +
                    '       对内容进行重新编辑，需要再次提交确认！' +
                    '   </span>' +
                    '</div>' +
                    '<div class="action">' +
                    '   <a href="" class="btn btn-primary waves-effect waves-light">提交确认</a>' +
                    '</div>';
                var header = UnderscoreUtil.getHtmlByText(okrHeader, {object: object, statusList: statusList});
                $okrObjectDetail.append(header);
                $('#confidenceLevel').html(pageObj.sectionToChinese($('#confidenceLevel').data('value')) + $('#confidenceLevel').html());

                var con = UnderscoreUtil.getHtmlByText(okrCon, {object: object, executeList: executeList});
                $okrObjectDetail.append(con);
                pageObj.pieEchartsFunc(object.id, object.progress);
                $.each(object.operateRecordList, function (idx, item) {
                    var okrHistory = '<div class="area-process-li past">' +
                        '   <em class="area-process-em"></em>' +
                        '   <p class="area-process-date"></p>' +
                        '   <div class="area-process-con">' +
                        '       <h4>[%=message%]</h4>' +
                        '   </div>' +
                        '</div>';
                    var history = UnderscoreUtil.getHtmlByText(okrHistory, item);
                    $okrObjectHistory.append(history);
                });
            });
        },

        pieEchartsFunc: function (id, progress) {
            require(["echarts"], function (echarts){
                var option = {
                    title: {text: '', x: 'center', y:"80%", textStyle: {fontSize: '12px', color:"#545454"}},
                    tooltip: {trigger: 'item', formatter: "{b} {d}%"},
                    legend: {orient: 'vertical', x: 'left', y:"-20%", data:['已完成','']},
                    series: [
                        {name:'', type:'pie', radius: ['60%', '75%'], avoidLabelOverlap: false, color:['#f57677','#dedede'],
                            label: {normal: {show: false, position: 'center'}, emphasis: {show: true, textStyle: {fontSize: '22px'}}},
                            labelLine: {normal: {show: false}},
                            data:[{value:progress, name: '已完成'}, {value: (100 - progress), name: '未完成'}]
                        }
                    ]
                };
                var myChart = echarts.init(document.getElementById(id));
                myChart.setOption(option);
            });
            require(["countUp"], function (CountUp) {
                $(".charts-total .num,.vals .num").each(function () {
                    var countUp = new CountUp(this, 0, $(this).data("new"), 0, 1, pageObj.options);
                    countUp.start();
                });
                $(".vals .num").each(function () {
                    var $bar=$(this).parents(".scroll-bar");
                    $bar.find(".complete").width($(this).data("end")+"%");
                    $bar.find(".new").width($(this).data("new")+"%");
                });
            });
        },

        tabClick: function (dom, type, teamId) {
            $(dom).addClass('active').siblings().removeClass('active');
            pageObj.loadOKRObjects(type, teamId);
        },

        deleteFunc: function (id) {
            require(["artDialog"], function () {
                artDialogUtil.confirm("确认删除该Object吗？", function () {
                    $.ajax({
                        url: App["contextPath"] + "/manage/okrObject/deleteObject.json?objectId=" + id,
                        dataType: "json",
                        success: function (data) {
                            require(["Tips"], function () {
                                if (data.success) {
                                    TipsUtil.info(data.message);
                                } else {
                                    TipsUtil.warn(data.message);
                                }
                            });
                        },
                        error: function (res) {
                            alert(JSON.stringify(res));
                        }
                    });
                });
            });
        },

        chnNumChar: ["零成","一成","二成","三成","四成","五成","六成","七成","八成","九成"],
        sectionToChinese: function (section) {
            return pageObj.chnNumChar[section];
        }
    });

    $(window).ready(function () {
        window.pageObj = pageObj;
        pageObj.loadOKRObjectDetail();
    });
});