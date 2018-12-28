/**
 * 修改目的主要是为了解决配制名称时，支持函数的方法，也支持原生的使用方法
 * e.g.
 * {
		data : {
			key : {
				name : function(node) {
					return node.code + " " + node.name;
				}
			}
		}
	}
 */

(function () {
    var
        orginZ = $.fn.zTree._z,
        consts = $.fn.zTree.consts,
        tools = orginZ.tools,
        $$ = tools.$,
        data = orginZ.data,
        view = orginZ.view;


    $.extend(orginZ.data, {
        getNodeNickname: function (setting, node) {
            var nameKey = setting.data.key.name;

            if ($.type(nameKey) === "string") {
                return "" + node[nameKey];
            } else if ($.type(nameKey) === "function") {
                return nameKey(node);
            } else {
                return "";
            }
        },

        getNodeNicktitle: function (setting, node) {
            var t = setting.data.key.title === "" ? setting.data.key.name : setting.data.key.title;

            if (t === "") {
                return data.getNodeNickname(setting, node);
            } else if ($.type(t) === "string") {
                return node[t];
            } else if ($.type(t) === "function") {
                t(node);
            } else {
                return "";
            }
        }
    });


    $.extend(orginZ.view, {
        makeDOMNodeIcon: function (html, setting, node) {
            var name = data.getNodeNickname(setting, node);
            name = setting.view.nameIsHTML ? name : name.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
            html.push("<span id='", node.tId, consts.id.ICON,
                "' title='' treeNode", consts.id.ICON, " class='", view.makeNodeIcoClass(setting, node),
                "' style='", view.makeNodeIcoStyle(setting, node), "'></span><span id='", node.tId, consts.id.SPAN,
                "'>", name, "</span>");
        },
        makeDOMNodeNameBefore: function (html, setting, node) {
            var title = data.getNodeNicktitle(setting, node),
                url = view.makeNodeUrl(setting, node),
                fontcss = view.makeNodeFontCss(setting, node),
                fontStyle = [];
            for (var f in fontcss) {
                fontStyle.push(f, ":", fontcss[f], ";");
            }
            html.push("<a id='", node.tId, consts.id.A, "' class='", consts.className.LEVEL, node.level, "' treeNode", consts.id.A, " onclick=\"", (node.click || ''),
                "\" ", ((url != null && url.length > 0) ? "href='" + url + "'" : ""), " target='", view.makeNodeTarget(node), "' style='", fontStyle.join(''),
                "'");
            if (tools.apply(setting.view.showTitle, [setting.treeId, node], setting.view.showTitle) && title) {
                html.push("title='", title.toString().replace(/'/g, "&#39;").replace(/</g, '&lt;').replace(/>/g, '&gt;'), "'");
            }
            html.push(">");
        },
        setNodeName: function (setting, node) {
            var name = data.getNodeNickname(setting, node),
                title = data.getNodeNicktitle(setting, node),
                nObj = $$(node, consts.id.SPAN, setting);

            nObj.empty();
            if (setting.view.nameIsHTML) {
                nObj.html(name);
            } else {
                nObj.text(name);
            }
            if (tools.apply(setting.view.showTitle, [setting.treeId, node], setting.view.showTitle)) {
                var aObj = $$(node, consts.id.A, setting);
                aObj.attr("title", !title ? "" : title.toString().replace(/'/g, "&#39;").replace(/</g, '&lt;').replace(/>/g, '&gt;'));
            }
        }
    });
})(jQuery);


/*
 * zzheng 添加
 * email: bigablecat@hotmail.com
 * Date: 2018-04-14
 */

/**
 * @param zTreeId the ztree id used to get the ztree object
 * @param searchField selector of your input for fuzzy search
 * @param isHighLight whether highlight the match words, default true
 * @param isExpand whether to expand the node, default false
 *
 * @returns
 */
function fuzzySearch(zTreeId, searchField, isHighLight, isExpand){
    var zTreeObj = $.fn.zTree.getZTreeObj(zTreeId);//get the ztree object by ztree id
    if(!zTreeObj){
        alert("fail to get ztree object");
    }
    var nameKey = zTreeObj.setting.data.key.name; //get the key of the node name
    isHighLight = isHighLight===false?false:true;//default true, only use false to disable highlight
    isExpand = isExpand?true:false; // not to expand in default
    zTreeObj.setting.view.nameIsHTML = isHighLight; //allow use html in node name for highlight use

    var metaChar = '[\\[\\]\\\\\^\\$\\.\\|\\?\\*\\+\\(\\)]'; //js meta characters
    var rexMeta = new RegExp(metaChar, 'gi');//regular expression to match meta characters

    // keywords filter function
    function ztreeFilter(zTreeObj,_keywords,callBackFunc) {
        if(!_keywords){
            _keywords =''; //default blank for _keywords
        }

        // function to find the matching node
        function filterFunc(node) {
            if(node && node.oldname && node.oldname.length>0){
                node[nameKey] = node.oldname; //recover oldname of the node if exist
            }
            zTreeObj.updateNode(node); //update node to for modifications take effect
            if (_keywords.length == 0) {
                //return true to show all nodes if the keyword is blank
                zTreeObj.showNode(node);
                zTreeObj.expandNode(node,isExpand);
                return true;
            }
            //transform node name and keywords to lowercase
            if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase())!=-1) {
                if(isHighLight){ //highlight process
                    //a new variable 'newKeywords' created to store the keywords information
                    //keep the parameter '_keywords' as initial and it will be used in next node
                    //process the meta characters in _keywords thus the RegExp can be correctly used in str.replace
                    var newKeywords = _keywords.replace(rexMeta,function(matchStr){
                        //add escape character before meta characters
                        return '\\' + matchStr;
                    });
                    node.oldname = node[nameKey]; //store the old name
                    var rexGlobal = new RegExp(newKeywords, 'gi');//'g' for global,'i' for ignore case
                    //use replace(RegExp,replacement) since replace(/substr/g,replacement) cannot be used here
                    node[nameKey] = node.oldname.replace(rexGlobal, function(originalText){
                        //highlight the matching words in node name
                        var highLightText =
                            '<span style="color: whitesmoke;background-color: darkred;">'
                            + originalText
                            +'</span>';
                        return 	highLightText;
                    });
                    zTreeObj.updateNode(node); //update node for modifications take effect
                }
                zTreeObj.showNode(node);//show node with matching keywords
                return true; //return true and show this node
            }

            zTreeObj.hideNode(node); // hide node that not matched
            return false; //return false for node not matched
        }

        var nodesShow = zTreeObj.getNodesByFilter(filterFunc); //get all nodes that would be shown
        processShowNodes(nodesShow, _keywords);//nodes should be reprocessed to show correctly
    }

    /**
     * reprocess of nodes before showing
     */
    function processShowNodes(nodesShow,_keywords){
        if(nodesShow && nodesShow.length>0){
            //process the ancient nodes if _keywords is not blank
            if(_keywords.length>0){
                $.each(nodesShow, function(n,obj){
                    var pathOfOne = obj.getPath();//get all the ancient nodes including current node
                    if(pathOfOne && pathOfOne.length>0){
                        //i < pathOfOne.length-1 process every node in path except self
                        for(var i=0;i<pathOfOne.length-1;i++){
                            zTreeObj.showNode(pathOfOne[i]); //show node
                            zTreeObj.expandNode(pathOfOne[i],true); //expand node
                        }
                    }
                });
            }else{ //show all nodes when _keywords is blank and expand the root nodes
                var rootNodes = zTreeObj.getNodesByParam('level','0');//get all root nodes
                $.each(rootNodes,function(n,obj){
                    zTreeObj.expandNode(obj,true); //expand all root nodes
                });
            }
        }
    }

    //listen to change in input element
    $(searchField).bind('input propertychange', function() {
        var _keywords = $(this).val();
        searchNodeLazy(_keywords); //call lazy load
    });

    var timeoutId = null;
    // excute lazy load once after input change, the last pending task will be cancled
    function searchNodeLazy(_keywords) {
        if (timeoutId) {
            //clear pending task
            clearTimeout(timeoutId);
        }
        timeoutId = setTimeout(function() {
            ztreeFilter(zTreeObj,_keywords); //lazy load ztreeFilter function
            $(searchField).focus();//focus input field again after filtering
        }, 500);
    }
}