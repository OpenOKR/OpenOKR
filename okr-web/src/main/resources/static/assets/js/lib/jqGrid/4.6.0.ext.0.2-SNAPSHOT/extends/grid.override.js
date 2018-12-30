(function ($) {
    "use strict";
    $.jgrid.extend({
    	editCell : function (iRow,iCol, ed){
			return this.each(function (){
				var $t = this, nm, tmp,cc, cm;
				if (!$t.grid || $t.p.cellEdit !== true) {return;}
				iCol = parseInt(iCol,10);
				// select the row that can be used for other methods
				$t.p.selrow = $t.rows[iRow].id;
				if (!$t.p.knv) {$($t).jqGrid("GridNav");}
				// check to see if we have already edited cell
				if ($t.p.savedRow.length>0) {
					// prevent second click on that field and enable selects
					if (ed===true ) {
						if(iRow == $t.p.iRow && iCol == $t.p.iCol){
							return;
						}
					}
					// save the cell
					$($t).jqGrid("saveCell",$t.p.savedRow[0].id,$t.p.savedRow[0].ic);
				} else {
					window.setTimeout(function () { $("#"+$.jgrid.jqID($t.p.knv)).attr("tabindex","-1").focus();},0);
				}
				cm = $t.p.colModel[iCol];
				nm = cm.name;
				if (nm==='subgrid' || nm==='cb' || nm==='rn') {return;}
				cc = $("td:eq("+iCol+")",$t.rows[iRow]);
				if (cm.editable===true && ed===true && !cc.hasClass("not-editable-cell")) {
					if(parseInt($t.p.iCol,10)>=0  && parseInt($t.p.iRow,10)>=0) {
						$("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");
						$($t.rows[$t.p.iRow]).removeClass("selected-row ui-state-hover");
					}
					$(cc).addClass("edit-cell ui-state-highlight");
					$($t.rows[iRow]).addClass("selected-row ui-state-hover");
					try {
						tmp =  $.unformat.call($t,cc,{rowId: $t.rows[iRow].id, colModel:cm},iCol);
					} catch (_) {
						tmp = ( cm.edittype && cm.edittype === 'textarea' ) ? $(cc).text() : $(cc).html();
					}
					if($t.p.autoencode) { tmp = $.jgrid.htmlDecode(tmp); }
					if (!cm.edittype) {cm.edittype = "text";}
					$t.p.savedRow.push({id:iRow,ic:iCol,name:nm,v:tmp});
					if(tmp === "&nbsp;" || tmp === "&#160;" || (tmp.length===1 && tmp.charCodeAt(0)===160) ) {tmp='';}
					if($.isFunction($t.p.formatCell)) {
						var tmp2 = $t.p.formatCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
						if(tmp2 !== undefined ) {tmp = tmp2;}
					}
					$($t).triggerHandler("jqGridBeforeEditCell", [$t.rows[iRow].id, nm, tmp, iRow, iCol]);
					if ($.isFunction($t.p.beforeEditCell)) {
						$t.p.beforeEditCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
					}
					var opt = $.extend({}, cm.editoptions || {} ,{id:iRow+"_"+nm,name:nm});
					var elc = $.jgrid.createEl.call($t,cm.edittype,opt,tmp,true,$.extend({},$.jgrid.ajaxOptions,$t.p.ajaxSelectOptions || {}));
					$(cc).html("").append(elc).attr("tabindex","0");
					$.jgrid.bindEv.call($t, elc, opt);
					window.setTimeout(function () { $(elc).focus();},0);
					$("input, select, textarea",cc).bind("keydown",function(e) {
						if (e.keyCode === 27) {
							if($("input.hasDatepicker",cc).length >0) {
								if( $(".ui-datepicker").is(":hidden") )  { $($t).jqGrid("restoreCell",iRow,iCol); }
								else { $("input.hasDatepicker",cc).datepicker('hide'); }
							} else {
								$($t).jqGrid("restoreCell",iRow,iCol);
							}
						} //ESC
                        // TODO: begin 扩展
                        var $elm = $(this),currentColModel = {}, i,isReturn = false;
                        if($elm.attr("name")){
                            currentColModel = $($t).jqGrid("getColModelByName",$elm.attr("name"));
                        }
                        //判断当前单元格 是否要排除响应 指定keycode 按键事件
                        if(currentColModel && currentColModel.excludeKeyCodes){
                            for(i=0;i<currentColModel.excludeKeyCodes.length;i++){
                                if(currentColModel.excludeKeyCodes[i] === e.keyCode){
                                    isReturn = true;
                                    break;
                                }
                            }
                        }
                        //扩展 单元格编辑时 keyup 事件
                        if($.isFunction($t.p.onEditCellKeyup)) {
                            //事件、行Id、列名称、单元格值、单元格行坐标、单元格列坐标
                            $t.p.onEditCellKeyup.call($t,e, $t.rows[iRow].id,nm,tmp,iRow,iCol);
                        }
                        if(isReturn){
                            return;
                        }
						if (e.keyCode === 13) {
							/*
							$($t).jqGrid("saveCell",iRow,iCol);
							// Prevent default action
							*/
							if (!$t.grid.hDiv.loading) {
                                if (e.shiftKey) {
                                    $($t).jqGrid("editPrevCell", iRow, iCol);
                                } //Shift Enter
                                else {
                                    $($t).jqGrid("editNextCell", iRow, iCol);
                                } //Enter
                            } else {
                                return false;
                            }

							return false;
						} //Enter
						if (e.keyCode === 9)  {
							if(!$t.grid.hDiv.loading ) {
								if (e.shiftKey) {$($t).jqGrid("editPrevCell",iRow,iCol);} //Shift TAb
								else {$($t).jqGrid("editNextCell",iRow,iCol);} //Tab
							} else {
								return false;
							}
						}
                        if(!$t.grid.hDiv.loading ) {
                            // 键盘Keycode范围（向上）
                            if (event.ctrlKey && event.keyCode == 38) {
                                $($t).jqGrid("editUpCell", iRow, iCol);
                            }
                            // 键盘Keycode范围（向下）
                            if (event.ctrlKey && event.keyCode == 40) {
                                $($t).jqGrid("editDownCell", iRow, iCol);
                            }
                            // 键盘Keycode范围（向左）
                            if (event.ctrlKey && event.keyCode == 37) {
                                $($t).jqGrid("editPrevCell", iRow, iCol);
                            }
                            // 键盘Keycode范围（向右）
                            if (event.ctrlKey && event.keyCode == 39) {
                                $($t).jqGrid("editNextCell", iRow, iCol);
                            }
                        } else {
                            return false;
                        }
                        //扩展 单元格编辑时 keydown 事件
                        if($.isFunction($t.p.afterEditCellKeyDown)) {
                            //事件、行Id、列名称、单元格值、单元格行坐标、单元格列坐标
                            $t.p.afterEditCellKeyDown.call($t,e, $t.rows[iRow].id,nm,tmp,iRow,iCol);
                        }else{
                            e.stopPropagation();
                        }
                        // TODO: end 扩展
                        //TODO:begin 源代码
                        //e.stopPropagation();
                        //TODO:end 源代码
					});
					$($t).triggerHandler("jqGridAfterEditCell", [$t.rows[iRow].id, nm, tmp, iRow, iCol]);
					if ($.isFunction($t.p.afterEditCell)) {
						$t.p.afterEditCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
					}
				} else {
					if (parseInt($t.p.iCol,10)>=0  && parseInt($t.p.iRow,10)>=0) {
						$("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");
						$($t.rows[$t.p.iRow]).removeClass("selected-row ui-state-hover");
					}
					cc.addClass("edit-cell ui-state-highlight");
					$($t.rows[iRow]).addClass("selected-row ui-state-hover");
					tmp = cc.html().replace(/\&#160\;/ig,'');
					$($t).triggerHandler("jqGridSelectCell", [$t.rows[iRow].id, nm, tmp, iRow, iCol]);
					if ($.isFunction($t.p.onSelectCell)) {
						$t.p.onSelectCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
					}
				}
				$t.p.iCol = iCol; $t.p.iRow = iRow;
			});
		},
        GridNav : function() {
            return this.each(function () {
                var  $t = this;
                if (!$t.grid || $t.p.cellEdit !== true ) {return;}
                // trick to process keydown on non input elements
                $t.p.knv = $t.p.id + "_kn";
                //TODO:begin 原代码 var selection = $("<div style='position:fixed;top:0px;width:1px;height:1px;' tabindex='0'><div tabindex='-1' style='width:1px;height:1px;' id='"+$t.p.knv+"'></div></div>"),
                //如果jqGrid 控件包含的父元素是有滚动条的，采用 position:fixed 会导致滚动条置顶。
                var selection = $("<div style='position:absolute;top:0px;width:1px;height:1px;' tabindex='0'><div tabindex='-1' style='width:1px;height:1px;' id='"+$t.p.knv+"'></div></div>"),
                //TODO:end
                    i, kdir;
                function scrollGrid(iR, iC, tp){
                    if (tp.substr(0,1)==='v') {
                        var ch = $($t.grid.bDiv)[0].clientHeight,
                            st = $($t.grid.bDiv)[0].scrollTop,
                            nROT = $t.rows[iR].offsetTop+$t.rows[iR].clientHeight,
                            pROT = $t.rows[iR].offsetTop;
                        if(tp === 'vd') {
                            if(nROT >= ch) {
                                $($t.grid.bDiv)[0].scrollTop = $($t.grid.bDiv)[0].scrollTop + $t.rows[iR].clientHeight;
                            }
                        }
                        if(tp === 'vu'){
                            if (pROT < st ) {
                                $($t.grid.bDiv)[0].scrollTop = $($t.grid.bDiv)[0].scrollTop - $t.rows[iR].clientHeight;
                            }
                        }
                    }
                    if(tp==='h') {
                        var cw = $($t.grid.bDiv)[0].clientWidth,
                            sl = $($t.grid.bDiv)[0].scrollLeft,
                            nCOL = $t.rows[iR].cells[iC].offsetLeft+$t.rows[iR].cells[iC].clientWidth,
                            pCOL = $t.rows[iR].cells[iC].offsetLeft;
                        if(nCOL >= cw+parseInt(sl,10)) {
                            $($t.grid.bDiv)[0].scrollLeft = $($t.grid.bDiv)[0].scrollLeft + $t.rows[iR].cells[iC].clientWidth;
                        } else if (pCOL < sl) {
                            $($t.grid.bDiv)[0].scrollLeft = $($t.grid.bDiv)[0].scrollLeft - $t.rows[iR].cells[iC].clientWidth;
                        }
                    }
                }
                function findNextVisible(iC,act){
                    var ind, i;
                    if(act === 'lft') {
                        ind = iC+1;
                        for (i=iC;i>=0;i--){
                            if ($t.p.colModel[i].hidden !== true) {
                                ind = i;
                                break;
                            }
                        }
                    }
                    if(act === 'rgt') {
                        ind = iC-1;
                        for (i=iC; i<$t.p.colModel.length;i++){
                            if ($t.p.colModel[i].hidden !== true) {
                                ind = i;
                                break;
                            }
                        }
                    }
                    return ind;
                }

                $(selection).insertBefore($t.grid.cDiv);
                $("#"+$t.p.knv)
                    .focus()
                    .keydown(function (e){
                        kdir = e.keyCode;
                        if($t.p.direction === "rtl") {
                            if(kdir===37) { kdir = 39;}
                            else if (kdir===39) { kdir = 37; }
                        }
                        switch (kdir) {
                            case 38:
                                if ($t.p.iRow-1 >0 ) {
                                    scrollGrid($t.p.iRow-1,$t.p.iCol,'vu');
                                    $($t).jqGrid("editCell",$t.p.iRow-1,$t.p.iCol,false);
                                }
                                break;
                            case 40 :
                                if ($t.p.iRow+1 <=  $t.rows.length-1) {
                                    scrollGrid($t.p.iRow+1,$t.p.iCol,'vd');
                                    $($t).jqGrid("editCell",$t.p.iRow+1,$t.p.iCol,false);
                                }
                                break;
                            case 37 :
                                if ($t.p.iCol -1 >=  0) {
                                    i = findNextVisible($t.p.iCol-1,'lft');
                                    scrollGrid($t.p.iRow, i,'h');
                                    $($t).jqGrid("editCell",$t.p.iRow, i,false);
                                }
                                break;
                            case 39 :
                                if ($t.p.iCol +1 <=  $t.p.colModel.length-1) {
                                    i = findNextVisible($t.p.iCol+1,'rgt');
                                    scrollGrid($t.p.iRow,i,'h');
                                    $($t).jqGrid("editCell",$t.p.iRow,i,false);
                                }
                                break;
                            case 13:
                                if (parseInt($t.p.iCol,10)>=0 && parseInt($t.p.iRow,10)>=0) {
                                    $($t).jqGrid("editCell",$t.p.iRow,$t.p.iCol,true);
                                }
                                break;
                            default :
                                return true;
                        }
                        return false;
                    });
            });
        },
        setGroupHeaders : function ( o ) {
            o = $.extend({
                useColSpanStyle :  false,
                groupHeaders: []
            },o  || {});
            return this.each(function(){
                this.p.groupHeader = o;
                var ts = this,
                    i, cmi, skip = 0, $tr, $colHeader, th, $th, thStyle,
                    iCol,
                    cghi,
                    //startColumnName,
                    numberOfColumns,
                    titleText,
                    cVisibleColumns,
                    colModel = ts.p.colModel,
                    cml = colModel.length,
                    ths = ts.grid.headers,
                    $htable = $("table.ui-jqgrid-htable", ts.grid.hDiv),
                    $trLabels = $htable.children("thead").children("tr.ui-jqgrid-labels:last").addClass("jqg-second-row-header"),
                    $thead = $htable.children("thead"),
                    $theadInTable,
                    $firstHeaderRow = $htable.find(".jqg-first-row-header");
                if($firstHeaderRow[0] === undefined) {
                    $firstHeaderRow = $('<tr>', {role: "row", "aria-hidden": "true"}).addClass("jqg-first-row-header").css("height", "auto");
                } else {
                    $firstHeaderRow.empty();
                }
                var $firstRow,
                    inColumnHeader = function (text, columnHeaders) {
                        var length = columnHeaders.length, i;
                        for (i = 0; i < length; i++) {
                            if (columnHeaders[i].startColumnName === text) {
                                return i;
                            }
                        }
                        return -1;
                    };

                $(ts).prepend($thead);
                $tr = $('<tr>', {role: "rowheader"}).addClass("ui-jqgrid-labels jqg-third-row-header");
                for (i = 0; i < cml; i++) {
                    th = ths[i].el;
                    $th = $(th);
                    cmi = colModel[i];
                    // build the next cell for the first header row
                    thStyle = { height: '0px', width: ths[i].width + 'px', display: (cmi.hidden ? 'none' : '')};
                    $("<th>", {role: 'gridcell'}).css(thStyle).addClass("ui-first-th-"+ts.p.direction).appendTo($firstHeaderRow);

                    th.style.width = ""; // remove unneeded style
                    iCol = inColumnHeader(cmi.name, o.groupHeaders);
                    if (iCol >= 0) {
                        cghi = o.groupHeaders[iCol];
                        numberOfColumns = cghi.numberOfColumns;
                        titleText = cghi.titleText;

                        // caclulate the number of visible columns from the next numberOfColumns columns
                        for (cVisibleColumns = 0, iCol = 0; iCol < numberOfColumns && (i + iCol < cml); iCol++) {
                            if (!colModel[i + iCol].hidden) {
                                cVisibleColumns++;
                            }
                        }

                        // The next numberOfColumns headers will be moved in the next row
                        // in the current row will be placed the new column header with the titleText.
                        // The text will be over the cVisibleColumns columns
                        $colHeader = $('<th>').attr({role: "columnheader"})
                            .addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
                            .css({'height':'22px', 'border-top': '0 none'})
                            .html(titleText);
                        if(cVisibleColumns > 0) {
                            $colHeader.attr("colspan", String(cVisibleColumns));
                        }
                        if (ts.p.headertitles) {
                            $colHeader.attr("title", $colHeader.text());
                        }
                        // hide if not a visible cols
                        if( cVisibleColumns === 0) {
                            $colHeader.hide();
                        }

                        $th.before($colHeader); // insert new column header before the current
                        $tr.append(th);         // move the current header in the next row

                        // set the coumter of headers which will be moved in the next row
                        skip = numberOfColumns - 1;
                    } else {
                        if (skip === 0) {
                            if (o.useColSpanStyle) {
                                // expand the header height to two rows
                                //TODO：begin 原代码
                                //$th.attr("rowspan", "2");
                                //TODO：end 原代码
                                //TODO：begin 重写
                                $th.attr("rowspan", (o.basicRowSpan || 2));
                                //TODO：end 重写
                            } else {
                                $('<th>', {role: "columnheader"})
                                    .addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
                                    .css({"display": cmi.hidden ? 'none' : '', 'border-top': '0 none'})
                                    .insertBefore($th);
                                $tr.append(th);
                            }
                        } else {
                            // move the header to the next row
                            //$th.css({"padding-top": "2px", height: "19px"});
                            $tr.append(th);
                            skip--;
                        }
                    }
                }
                $theadInTable = $(ts).children("thead");
                $theadInTable.prepend($firstHeaderRow);
                $tr.insertAfter($trLabels);
                $htable.append($theadInTable);

                if (o.useColSpanStyle) {
                    // Increase the height of resizing span of visible headers
                    $htable.find("span.ui-jqgrid-resize").each(function () {
                        var $parent = $(this).parent();
                        if ($parent.is(":visible")) {
                            this.style.cssText = 'height: ' + $parent.height() + 'px !important; cursor: col-resize;';
                        }
                    });

                    // Set position of the sortable div (the main lable)
                    // with the column header text to the middle of the cell.
                    // One should not do this for hidden headers.
                    $htable.find("div.ui-jqgrid-sortable").each(function () {
                        var $ts = $(this), $parent = $ts.parent();
                        if ($parent.is(":visible") && $parent.is(":has(span.ui-jqgrid-resize)")) {
                            $ts.css('top', ($parent.height() - $ts.outerHeight()) / 2 + 'px');
                        }
                    });
                }

                $firstRow = $theadInTable.find("tr.jqg-first-row-header");
                $(ts).bind('jqGridResizeStop.setGroupHeaders', function (e, nw, idx) {
                    $firstRow.find('th').eq(idx).width(nw);
                });
            });
        },
    });
})(jQuery);