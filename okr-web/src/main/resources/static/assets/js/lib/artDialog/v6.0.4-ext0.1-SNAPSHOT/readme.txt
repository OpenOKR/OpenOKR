TODO:【注：图片放置 项目扩展的目录,放置在以外目录会被删除.CSS/JS 写入指定的项目扩展文件,框架的CSS/JS文件会被覆盖.】
1.*.native.min.js为官方原生压缩版本,可能包含了框架扩展或重写代码
2.新建css/ui-dialog.extend.css 用于扩展，重写样式
3.新建css/ui-dialog.all.min.css，将ui-dialog.css和ui-dialog.extend.css压缩至此

4.修改所有样式名 "ui-dialog" 改为 "artDialog"，解决与jQueryUI 样式冲突问题
5.包含 "ui-dialog"的css文件 改为 "artDialog"
6.css/images/extend/  项目扩展
7.css/artDialog.application.extend.css  项目扩展
8.dialog.application.extend.js  项目扩展


v6.0.4-ext0.1
1.dialog-application-ext-en.js 扩展文件引用的国际化英文文件
2.dialog-application-ext-zh_CN.js 扩展文件引用的国际化英文文件
3.在 dialog.framework.extend.js 扩展 artDialogUtil
4. 调整 skin 添加的时机，实现自定义入场动画。现在可以设置skin，并对应的css，即可实现自定义入场动画。  2017-11-23
5.增加 dragable  扩展是否可拖拽