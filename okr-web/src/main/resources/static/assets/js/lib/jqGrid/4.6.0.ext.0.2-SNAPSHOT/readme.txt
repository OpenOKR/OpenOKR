TODO:【注：图片放置 项目扩展的目录,放置在以外目录会被删除.CSS/JS 写入指定的项目扩展文件,框架的CSS/JS文件会被覆盖.】
本目录规范如下
    1.新建文件规则：文件名 = 在需要修改源码或要给源码增加方法的源文件名 + 关键字
	2.有关键字extend的文件为扩展文件
	3.有关键字overrride的文件为复写文件，在修改前加上TODO: begin,在修改结束的地方加上TODO: end,在变动的源码地加上注释"/* */"
	4.关键字以“_”开头的即为在自己创建的文件，在源文件中找不到的文件
	5.grid.interface.js是保持对外的接口，限外部可以调用
	6.grid.interface.private.js是不公开接口
	7.jquery.jqGrid.debug.js用于require-config配置引用使用
	8.jquery.jqGrid.all.min.js是jqGrid 源码和扩展的js 的压缩在一起
	    8.1.压缩顺序 (1)extends/grid.init.load.js,(2)jquery.jgGrid.min.js,(3)除extends/grid.init.load.js以外的所有其它extends文件夹扩展的js
    9.ui.jqgrid.all.min.css 是jqGrid 源码和扩展的css的压缩在一起
        9.1.压缩顺序 (1)ui.jqgrid.css,(2)ui.jqgrid.extend.css
    10.*.native.min.js为官方原生压缩版本,可能包含了框架扩展或重写代码

修改说明：
1.grid.celledit.override.js重写了源代码
2.grid.locale-cn.js 改为 grid.locale-zh_CN.js
3.css/ui.jqgrid.application.extend.css  项目扩展
4.extends/grid.application.extend.js  项目扩展
5.plugins/grid.locale.plugin.application.extend-en.js  项目扩展
6.plugins/grid.locale.plugin.application.extend-zh_CN.js  项目扩展
7.grid.locale-application-ext-en.js  项目扩展
8.grid.locale-application-ext-zh_CN.js  项目扩展


4.6.0.ext.0.1 更新内容
1.增加了 afterEditCellKeyDown 接口
2.修复跳到上一个或下一个单元格没有根据是否显示与可编辑来跳转
3.修复多表头拖动与调用setGridWidth表头与表身对不齐的问题
4.$.fn.fmatter.forEach增加索引，以方便取数据
5.增加框架的扩展国际化js grid.locale-ext-zh_CN.js,grid.locale-ext-en.js

4.6.0.ext.0.2 更新内容
   1、优化 addGridRowDatas
   2、增加 isDirty 判断表格是否被修改
   3、focusFirstEditableCellByRowId 聚焦到第一个可以编辑的单元格
   4、filterRow 过滤行，留下搜索文本的行
   5、grid.celledit.extend.js改名grid.extend.js
   6、grid.celledit.override.js改名grid.override.js
   7、重写 setGroupHeaders 支持多重表头
   8、formatGridData扩展
   9、getTotalValueByColumnNames（统计全部 或 统计选中行、过滤 function）
   10、getChangeStore    获取 行数据改变存储
   11、clearChangeStore  清除 行数据改变存储
   12、getColIndexByRowIdAndName 根据 rowId 和 列名 获取 列坐标
   13、setAttr   设置表格属性
   14、修改 jquery.fmatter.extend.js,grid.locale-ext-en.js,grid.locale-ext-zh_CN.js 的 customAction 默认提供的 add,edit,del，占用了关键词 new delete