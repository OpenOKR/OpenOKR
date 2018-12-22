(function ($) {
    $.jgrid = $.jgrid || {};
    $.jgrid.defaults = $.jgrid.defaults || {};
    $.extend($.jgrid.defaults, {
        jsonReader: {
            root: "records",        //与后台Page.java的 records 映射
            rows: "pageSize",       //与后台Page.java的 pageSize 映射
            total: "totalPage",     //与后台Page.java的 totalPage 映射
            page: "currentPage",    //与后台Page.java的 currentPage 映射
            records: "totalRecord"  //与后台Page.java的 totalRecord 映射
        },
        rowNum: 20,
        rowList: [20, 50, 200, 500, 1000],
        prmNames: {page: "currentPage", rows: "pageSize"},
        rownumbers: true,
        viewsortcols: [true],
        rownumWidth: 50,

        // 激活单元格 变为可编辑状态后 选中文本
        enabledCellSelect: true,
        afterInitGrid: function () {
            var _this = this, $this = $(_this), p = _this.p;
            //
            $this.jqGrid("addEditCellIcon");
            //
            //增加序号
            if (p.rownumbers) {
                $("th#" + p.id + "_rn[role='columnheader']").append("序号");
            }
        }
    });

})(jQuery);


/**
 * 依赖 autoNumeric
 */
var jqGridUtil = {

    numericCustomElement: function (value, minVal, maxVal) {
        var $input = $('<input type="text" role="textbox">');
        //
        require(["autoNumeric"], function () {
            var timeObj = window.setTimeout(function () {
                $input.addClass("custom-input").css({"text-align": "right", width: "100%"})
                    .autoNumeric({
                        aSep: '', aDec: '.', vMin: (minVal === undefined ? 0 : minVal), vMax: (maxVal === undefined ? 99999999 : maxVal)
                    });
                if ($.isNumeric(value)) {
                    $input.autoNumeric('set', value);
                }
                window.clearTimeout(timeObj);
            }, 1);
        });
        return $input;
    },

    numericCustomValue: function ($elm) {
        if ($elm.is('input')) {
            return $elm.autoNumeric('get') * 1;
        } else {
            return "";
        }
    }
};


(function ($) {
    "use strict";
    $.jgrid.extend({

        /**
         * 添加可编辑状态图标
         */
        addEditCellIcon: function () {
            this.each(function () {
                var _grid = this,
                    colModels = _grid.p.colModel,
                    headers = this.grid.headers;

                $.each(colModels, function (i, colModel) {
                    if (colModel.editable === true) {
                        $(headers[i].el).find(".ui-jqgrid-sortable").append("<span title='可编辑列' class='ui-th-editable'></span>");
                    }
                });
            });
        },
        setFrozenColumns : function () {
            return this.each(function() {
                if ( !this.grid ) {return;}
                var $t = this, cm = $t.p.colModel,i=0, len = cm.length, maxfrozen = -1, frozen= false;
                // TODO treeGrid and grouping  Support
                if($t.p.subGrid === true || $t.p.treeGrid === true || $t.p.cellEdit === true || $t.p.sortable || $t.p.scroll )
                {
                    return;
                }
                if($t.p.rownumbers) { i++; }
                if($t.p.multiselect) { i++; }

                // get the max index of frozen col
                while(i<len)
                {
                    // from left, no breaking frozen
                    if(cm[i].frozen === true)
                    {
                        frozen = true;
                        maxfrozen = i;
                    } else {
                        break;
                    }
                    i++;
                }
                if( maxfrozen>=0 && frozen) {
                    var top = $t.p.caption ? $($t.grid.cDiv).outerHeight() : 0,
                        hth = $(".ui-jqgrid-htable","#gview_"+$.jgrid.jqID($t.p.id)).height();
                    //headers
                    if($t.p.toppager) {
                        top = top + $($t.grid.topDiv).outerHeight();
                    }
                    if($t.p.toolbar[0] === true) {
                        if($t.p.toolbar[1] !== "bottom") {
                            top = top + $($t.grid.uDiv).outerHeight();
                        }
                    }
                    $t.grid.fhDiv = $('<div style="position:absolute;left:0px;top:'+top+'px;height:'+hth+'px;" class="frozen-div ui-state-default ui-jqgrid-hdiv"></div>');
                    $t.grid.fbDiv = $('<div style="position:absolute;left:0px;top:'+(parseInt(top,10)+parseInt(hth,10) + 1)+'px;overflow-y:hidden" class="frozen-bdiv ui-jqgrid-bdiv"></div>');
                    $("#gview_"+$.jgrid.jqID($t.p.id)).append($t.grid.fhDiv);
                    var htbl = $(".ui-jqgrid-htable","#gview_"+$.jgrid.jqID($t.p.id)).clone(true);
                    // groupheader support - only if useColSpanstyle is false
                    if($t.p.groupHeader) {
                        $("tr.jqg-first-row-header, tr.jqg-third-row-header", htbl).each(function(){
                            $("th:gt("+maxfrozen+")",this).remove();
                        });
                        var swapfroz = -1, fdel = -1, cs, rs;
                        $("tr.jqg-second-row-header th", htbl).each(function(){
                            cs= parseInt($(this).attr("colspan"),10);
                            rs= parseInt($(this).attr("rowspan"),10);
                            if(rs) {
                                swapfroz++;
                                fdel++;
                            }
                            if(cs) {
                                swapfroz = swapfroz+cs;
                                fdel++;
                            }
                            if(swapfroz === maxfrozen) {
                                return false;
                            }
                        });
                        if(swapfroz !== maxfrozen) {
                            fdel = maxfrozen;
                        }
                        $("tr.jqg-second-row-header", htbl).each(function(){
                            $("th:gt("+fdel+")",this).remove();
                        });
                    } else {
                        $("tr",htbl).each(function(){
                            $("th:gt("+maxfrozen+")",this).remove();
                        });
                    }
                    $(htbl).width(1);
                    // resizing stuff
                    $($t.grid.fhDiv).append(htbl)
                        .mousemove(function (e) {
                            if($t.grid.resizing){ $t.grid.dragMove(e);return false; }
                        });
                    $($t).bind('jqGridResizeStop.setFrozenColumns', function (e, w, index) {
                        var rhth = $(".ui-jqgrid-htable",$t.grid.fhDiv);
                        $("th:eq("+index+")",rhth).width( w );
                        var btd = $(".ui-jqgrid-btable",$t.grid.fbDiv);
                        $("tr:first td:eq("+index+")",btd).width( w );
                    });
                    // sorting stuff
                    $($t).bind('jqGridSortCol.setFrozenColumns', function (e, index, idxcol) {

                        var previousSelectedTh = $("tr.ui-jqgrid-labels:last th:eq("+$t.p.lastsort+")",$t.grid.fhDiv), newSelectedTh = $("tr.ui-jqgrid-labels:last th:eq("+idxcol+")",$t.grid.fhDiv);

                        $("span.ui-grid-ico-sort",previousSelectedTh).addClass('ui-state-disabled');
                        $(previousSelectedTh).attr("aria-selected","false");
                        $("span.ui-icon-"+$t.p.sortorder,newSelectedTh).removeClass('ui-state-disabled');
                        $(newSelectedTh).attr("aria-selected","true");
                        if(!$t.p.viewsortcols[0]) {
                            if($t.p.lastsort !== idxcol) {
                                $("span.s-ico",previousSelectedTh).hide();
                                $("span.s-ico",newSelectedTh).show();
                            }
                        }
                    });

                    // data stuff
                    //TODO support for setRowData
                    $("#gview_"+$.jgrid.jqID($t.p.id)).append($t.grid.fbDiv);
                    $($t.grid.bDiv).scroll(function () {
                        $($t.grid.fbDiv).scrollTop($(this).scrollTop());
                    });
                    if($t.p.hoverrows === true) {
                        $("#"+$.jgrid.jqID($t.p.id)).unbind('mouseover').unbind('mouseout');
                    }
                    $($t).bind('jqGridAfterGridComplete.setFrozenColumns', function () {
                        $("#"+$.jgrid.jqID($t.p.id)+"_frozen").remove();
                        //TODO PFM-408 【上报审核/中心审核】修改页面底部一下金额数值，右侧固定的操作栏置顶跳转到序号1，未与预算对齐
                        $($t.grid.fbDiv).height($($t.grid.bDiv).height());
                        // $($t.grid.fbDiv).height($($t.grid.bDiv).height()-16);
                        var btbl = $("#"+$.jgrid.jqID($t.p.id)).clone(true);
                        $("tr[role=row]",btbl).each(function(){
                            $("td[role=gridcell]:gt("+maxfrozen+")",this).remove();
                        });

                        $(btbl).width(1).attr("id",$t.p.id+"_frozen");
                        $($t.grid.fbDiv).append(btbl);
                        if($t.p.hoverrows === true) {
                            $("tr.jqgrow", btbl).hover(
                                function(){ $(this).addClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)).addClass("ui-state-hover"); },
                                function(){ $(this).removeClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)).removeClass("ui-state-hover"); }
                            );
                            $("tr.jqgrow", "#"+$.jgrid.jqID($t.p.id)).hover(
                                function(){ $(this).addClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)+"_frozen").addClass("ui-state-hover");},
                                function(){ $(this).removeClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)+"_frozen").removeClass("ui-state-hover"); }
                            );
                        }
                        btbl=null;
                    });
                    if(!$t.grid.hDiv.loading) {
                        $($t).triggerHandler("jqGridAfterGridComplete");
                    }
                    $t.p.frozenColumns = true;
                }
            });
        }

    });
})(jQuery);