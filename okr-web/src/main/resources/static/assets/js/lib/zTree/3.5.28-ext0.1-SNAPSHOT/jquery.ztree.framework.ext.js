var zTreeUtil = {

    defaultSetting: {
        data: {
            keep: {
                leaf: false,
                parent: true
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            }
        }
    },

    /**
     * 私有存储
     */
    _store: {},

    /**
     * 根据 文本 选中 匹配的节点
     * @param treeId            树Id
     * @param searchText        搜索文本
     * @param fieldNameArray    要匹配的字段（数组）
     * @returns {*}
     */
    selectNodeByText: function (treeId, searchText, fieldNameArray) {
        return zTreeUtil._selectAndClickNodeByText(treeId, searchText, fieldNameArray, false);
    },

    /**
     * 根据 文本 选中并点击 匹配的节点
     * @param treeId            树Id
     * @param searchText        搜索文本
     * @param fieldNameArray    要匹配的字段（数组）
     * @returns {*}
     */
    selectAndClickNodeByText: function (treeId, searchText, fieldNameArray) {
        return zTreeUtil._selectAndClickNodeByText(treeId, searchText, fieldNameArray, true);
    },

    /**
     * 根据 文本 选中或点击 匹配的节点
     * @param treeId            树Id
     * @param searchText        搜索文本
     * @param fieldNameArray    要匹配的字段（数组）
     * @param isClick           是否点击
     * @returns {*}
     */
    _selectAndClickNodeByText: function (treeId, searchText, fieldNameArray, isClick) {
        if (!searchText || searchText.length == 0) {
            return null;
        }
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        //要匹配的
        fieldNameArray = ($.isArray(fieldNameArray) && fieldNameArray.length > 0) ? fieldNameArray : ["name"];
        //
        var treeDatas = treeObj.transformToArray(treeObj.getNodes()),
            curIndex = (zTreeUtil._store[treeId + "curIndex"] + 1) || 0,//区分 树 搜索到那个节点 索引
            endIndex = curIndex > 0 ? (curIndex + treeDatas.length) : treeDatas.length,
            index,
            tempNode,
            matchNode = null, isMatch = false,
            i,
            fieldName;
        while (curIndex < endIndex && !isMatch) {
            index = curIndex < treeDatas.length ? curIndex : (curIndex - treeDatas.length);
            tempNode = treeDatas[index];
            //
            isMatch = false;
            matchNode = null;

            for (i = 0; i < fieldNameArray.length; i++) {
                fieldName = fieldNameArray[i];
                if (tempNode[fieldName] && tempNode[fieldName].toLowerCase().indexOf(searchText) >= 0) {
                    matchNode = tempNode;
                    isMatch = true;
                    break;
                }
            }
            curIndex++;
        }
        //没有匹配的节点
        if (!matchNode) {
            return null;
        }
        //记下当前树匹配的下标
        zTreeUtil._store[treeId + "curIndex"] = index;
        //选中匹配节点
        treeObj.selectNode(matchNode);
        //点击树节点
        if (isClick) {
            var $aNode = $("#" + matchNode.tId + "_a");
            if ($aNode && $aNode.length > 0) {
                $aNode.trigger("click");
            }
        }
        return matchNode;
    },

    /**
     * 根据 zTree 的唯一标识获得其所有层级的父节点
     * @param treeId
     * @param tId 标识
     * @returns {Array}
     */
    getAllParentsNodesByTid: function (treeId, tId) {
        var treeObj, tempNode, relNodes = [];

        treeObj = $.fn.zTree.getZTreeObj(treeId);
        tempNode = treeObj.getNodeByTId(tId);

        while (tempNode != null) {
            relNodes.push(tempNode);
            tempNode = treeObj.getParentNode();
        }

        return relNodes;
    },

    /**
     * 根据 zTree 的唯一标识获得其所有层级的子孙节点
     * @param treeId
     * @param tId 标识
     * @returns {Array}
     */
    getAllChidrenNodesByTid: function (treeId, tId) {
        function getChildren(node) {
            var rel = [];

            if (node == null || node.children == null || node.children.length === 0) {
                return [];
            }

            rel = rel.concat(node.children);

            for (var i = 0, length = node.children.length; i < length; i++) {
                var node = node.children[i];

                rel = rel.concat(getChildren(node));
            }

            return rel;
        }

        var treeObj, tempNode, relNodes = [];

        treeObj = $.fn.zTree.getZTreeObj(treeId);
        tempNode = treeObj.getNodeByTId(tId);

        if (tempNode == null) {
            return relNodes;
        }

        return getChildren(tempNode);
    }
};