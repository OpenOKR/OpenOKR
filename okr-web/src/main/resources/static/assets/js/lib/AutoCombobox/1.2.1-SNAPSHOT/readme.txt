TODO:【注：图片放置 项目扩展的目录,放置在以外目录会被删除.CSS/JS 写入指定的项目扩展文件,框架的CSS/JS文件会被覆盖.】
2015-11-18 1.1.0发布
    2015-11-18 之前未做版本管理，基本做到了向前兼容，默认为1.0.0。
    2015-11-18 开始为1.1.0。

1.css/auto-combobox.application.extend.css 项目扩展
2.auto-combobox.application.extend.js  项目扩展
3.css/images/extend  项目扩展

版本1.2.0
1.扩展国际化 auto-combobox-app-ext-en.js,auto-combobox-app-ext-zh_CN.js
2.弃用 beforeInput 和 afterInput,并兼容之前版本
3.增加回调实现beforeKeyDown 和 afterKeyDown (代替beforeInput 和 afterInput)
4.将面板的点击事件由mousedown 改为click,解决焦点穿透bug

版本1.2.1
    1、按键按下 改为 keydown
    2、不响应 keycode 9 (tab)按键，解决集成到jqGrid时场景
    3、分页问题
    4、reload 增加 重置当前选择行
    5、async.data 更换为 async.requestData
    6、增加支持 p.async.contentType=application/json
    7、增加弃用beforeInput、afterInput、p.view.dropDownContainer 命名 的检查
    8、在 auto-combobox.js 扩展 AutoComboboxUtil
    9、新增isEncode 解决 html 编码的转义
    10、showClearButton 显示清除按钮
    11、修复 triggerAction 重复点击无效
