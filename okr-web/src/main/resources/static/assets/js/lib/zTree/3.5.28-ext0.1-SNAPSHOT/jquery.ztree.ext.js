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