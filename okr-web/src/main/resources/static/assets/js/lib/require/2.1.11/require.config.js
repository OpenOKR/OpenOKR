/** ************************************************************ lib js库 */
var App = App || {};
if (!App.hasOwnProperty("staticResourceVersion") || App["staticResourceVersion"].length === 0) {
    App.ver = new Date().getTime();//给默认 版本
} else {
    App.ver = App["staticResourceVersion"];//给默认 版本
}
//被 CKEditor的config.js引用
if (!App.hasOwnProperty("language") || App["language"].length === 0) {
    App.lang = "zh_CN";//给默认 语言
} else {
    App.lang = App["language"];
}
if (!App.hasOwnProperty("jsDebug") || App["jsDebug"].length === 0) {
    App.src = false;//给默认 模式
} else {
    App.src = App["jsDebug"];
}
if (!App.hasOwnProperty("staticContextPath") || App["staticContextPath"].length === 0) {
    App.scp = "";//给默认
} else {
    App.scp = App["staticContextPath"];
}
if (!App.hasOwnProperty("staticContextPathNative") || App["staticContextPathNative"].length === 0) {
    App.scpNative = App.scp;//给默认
} else {
    App.scpNative = App["staticContextPathNative"];
}

App._jQuery = App.scp
    +"/assets/js/lib/jQuery/1.12.4/";

App._require = App.scp
    +"/assets/js/lib/require/2.1.11/";

App._validation = App.scp
    +"/assets/js/lib/jquery.validation/1.19.0/";

App._RSA = App.scp
    +"/assets/js/lib/RSA/";

App._jqGrid = App.scp
    +"/assets/js/lib/jqGrid/4.6.0.ext.0.2-SNAPSHOT/";

App._jqForm = App.scp
    +"/assets/js/lib/jqForm/1.1.3-SNAPSHOT/";

App._AutoTree = App.scp
    +"/assets/js/lib/AutoTree/1.2.1-SNAPSHOT/";

App._AutoCombobox = App.scp
    +"/assets/js/lib/AutoCombobox/1.2.1-SNAPSHOT/";

App._Tips = App.scp
    +"/assets/js/lib/Tips/1.1.2-SNAPSHOT/";

App._artDialog = App.scp
    +"/assets/js/lib/artDialog/v6.0.4-ext0.1-SNAPSHOT/";

App._zTree = App.scp
    +"/assets/js/lib/zTree/3.5.28-ext0.1-SNAPSHOT/";

App._OUI = App.scp
    +"/assets/js/lib/OUI/1.1.2-SNAPSHOT/";

App._jQueryBlockUI = App.scp
    +"/assets/js/lib/jQueryBlockUI/2.70-ext0.1-SNAPSHOT/";

App._Underscore = App.scp
    +"/assets/js/application/Underscore/1.6.0-ext0.2-SNAPSHOT/";

App._jQueryUI = App.scp
    +"/assets/js/lib/jQueryUI/1.10.4.ext.0.2-SNAPSHOT/";

App._echarts= App.scp
    +"/assets/js/lib/echarts/4.1.0/";

App._jQueryUtils = App.scp
    +"/assets/js/lib/jQueryUtils/1.1.1-SNAPSHOT/";

App._laypage = App.scp
    +"/assets/js/lib/laypage/1.2/";

var require = {
    map: {
        "*": {
            css: App.src ? App._require + "css/css.js" : App._require + "css/css.all.min.js?ver=" + App.ver
        }
    },
    shim: {
        _jQuery1: {
            deps: []
        },

        jQuery: {
            deps: ((App.src) ? ["_jQuery"] : [])
        },

        validation: {
            deps: ["jQuery"]
        },

        _RSA1: {
            deps: [""]
        },
        _RSA2: {
            deps: ["_RSA1"]
        },
        _RSA3: {
            deps: ["_RSA2"]
        },
        _RSA4: {
            deps: ["_RSA3"]
        },
        RSA: {
            deps: (App.src) ? ["_RSA4"]: ["jQuery"]
        },

        _jqGrid1: {
            deps: ["jQueryUI"]
        },
        _jqGrid2: {
            deps: ["_jqGrid1", "css!" + App._jqGrid + "plugins/ui.multiselect"]
        },
        _jqGrid3PluAppExt_zh_CN: {
            deps: ["_jqGrid2"]
        },
        _jqGrid3PluAppExt_en: {
            deps: ["_jqGrid2"]
        },
        _jqGrid4_zh_CN: {
            deps: ["_jqGrid3PluAppExt_zh_CN"]
        },
        _jqGrid4_en: {
            deps: ["_jqGrid3PluAppExt_en"]
        },
        _jqGrid5Ext_zh_CN: {
            deps: ["_jqGrid4_zh_CN"]
        },
        _jqGrid5Ext_en: {
            deps: ["_jqGrid4_en"]
        },
        _jqGrid6AppExt_zh_CN: {
            deps: ["_jqGrid5Ext_zh_CN"]
        },
        _jqGrid6AppExt_en: {
            deps: ["_jqGrid5Ext_en"]
        },
        _jqGrid7: {
            deps: ["_jqGrid6AppExt_" + App.lang]
        },
        _jqGrid8: {
            deps: ["_jqGrid7"]
        },
        _jqGrid9: {
            deps: ["_jqGrid8"]
        },
        _jqGrid10: {
            deps: ["_jqGrid9"]
        },
        _jqGrid11: {
            deps: ["_jqGrid10"]
        },
        _jqGrid12: {
            deps: ["_jqGrid11"]
        },
        _jqGrid13: {
            deps: ["_jqGrid12"]
        },
        _jqGrid14: {
            deps: ["_jqGrid13"]
        },
        jqGrid: {
            deps: App.src ?
                ["_jqGrid14",
                    "css!" + App._jqGrid + "css/ui.jqgrid",
                    "css!" + App._jqGrid + "css/ui.jqgrid.framework.extend",
                    "css!" + App._jqGrid + "css/ui.jqgrid.application.extend"] :
                ["jQueryUI",
                    "css!" + App._jqGrid + "css/ui.jqgrid.all.min"]
        },

        _jqForm1: {
            deps: ["jQuery"]
        },
        jqForm: {
            deps: (App.src) ? ["_jqForm1"]: ["jQuery"]
        },

        _AutoTree1: {
            deps: ["zTree", "OUI"]
        },
        _AutoTree_en: {
            deps: ["_AutoTree1"]
        },
        _AutoTreeAppExt_en: {
            deps: ["_AutoTree_en"]
        },
        _AutoTree_zh_CN: {
            deps: ["_AutoTree1"]
        },
        _AutoTreeAppExt_zh_CN: {
            deps: ["_AutoTree_zh_CN"]
        },
        AutoTree: {
            deps: App.src ?
                ["_AutoTreeAppExt_" + App.lang,
                    "css!" + App._AutoTree + "css/auto-tree",
                    "css!" + App._AutoTree + "css/auto-tree.application.extend"] :
                ["zTree", "OUI", "css!" + App._AutoTree + "css/auto-tree.all.min"]
        },

        _AutoCombobox1: {
            deps: ["OUI", "Underscore"]
        },
        _AutoCombobox_en: {
            deps: ["_AutoCombobox1"]
        },
        _AutoComboboxAppExt_en: {
            deps: ["_AutoCombobox_en"]
        },
        _AutoCombobox_zh_CN: {
            deps: ["_AutoCombobox1"]
        },
        _AutoComboboxAppExt_zh_CN: {
            deps: ["_AutoCombobox_zh_CN"]
        },
        AutoCombobox: {
            deps: App.src ?
                ["_AutoComboboxAppExt_" + App.lang,
                    "css!" + App._AutoCombobox + "css/auto-combobox",
                    "css!" + App._AutoCombobox + "css/auto-combobox.application.extend"] :
                ["OUI",
                    "Underscore",
                    "css!" + App._AutoCombobox + "css/auto-combobox.all.min"]
        },

        _Tips1: {
            deps: ["jQuery"]
        },
        Tips: {
            deps: App.src ?
                ["_Tips1",
                    "css!" + App._Tips + "tips",
                    "css!" + App._Tips + "tips.application.extend"] :
                ["jQuery",
                    "css!" + App._Tips + "tips.all.min"]
        },

        _artDialog1: {
            deps: ["jQuery"]
        },
        _artDialog2: {
            deps: ["_artDialog1"]
        },
        _artDialogAppExt_en: {
            deps: ["_artDialog2"]
        },
        _artDialogAppExt_zh_CN: {
            deps: ["_artDialog2"]
        },
        artDialog: {
            deps: App.src ?
                ["_artDialogAppExt_" + App.lang,
                    "css!" + App._artDialog + "css/artDialog",
                    "css!" + App._artDialog + "css/artDialog.application.extend"] :
                ["jQuery",
                    "css!" + App._artDialog + "css/artDialog.all.min"]
        },

        _zTree1: {
            deps: ["jQuery",
                "css!" + App._zTree + "css/zTreeStyle/zTreeStyle",
                "css!" + App._zTree + "css/zTreeStyle/zTreeStyle.application.extend"]
        },
        _zTree2: {
            deps: ["_zTree1"]
        },
        _zTree3: {
            deps: ["_zTree2"]
        },
        _zTree4: {
            deps: ["_zTree3"]
        },
        zTree: {
            deps: App.src ?
                ["_zTree4"] :
                ["jQuery",
                    "css!" + App._zTree + "css/zTreeStyle/zTreeStyle.all.min"]
        },

        _OUI1: {
            deps: ["jQuery"]
        },
        _OUI2: {
            deps: ["_OUI1"]
        },
        OUI: {
            deps: App.src ? ["_OUI2"] : ["jQuery"]
        },

        _jQueryBlockUI: {
            deps: ["jQuery"]
        },
        _jQueryBlockUI2: {
            deps: ["_jQueryBlockUI"]
        },
        jQueryBlockUI: {
            deps: App.src ?
                ["_jQueryBlockUI2",
                    "css!" + App._jQueryBlockUI + "css/blockUI.application.extend"] :
                ["_jQueryBlockUI",
                    "css!" + App._jQueryBlockUI + "css/blockUI.all.min"]
        },

        _Underscore1: {
            deps: []
        },
        _Underscore2: {
            deps: ["_Underscore1"]
        },
        Underscore: {
            deps: App.src ?
                ["_Underscore2"] :
                []
        },

        _jQueryUI1: {
            deps: ["jQuery"]
        },
        _jQueryUI2: {
            deps: ["_jQueryUI1"]
        },
        _jQueryUI3: {
            deps: ["_jQueryUI2"]
        },
        _jQueryUI4: {
            deps: ["_jQueryUI3"]
        },
        _jQueryUI5: {
            deps: ["_jQueryUI4"]
        },
        jQueryUI: {
            deps: App.src ?
                ["_jQueryUI5",
                    "css!" + App._jQueryUI + "css/jquery-ui-1.10.3.custom",
                    "css!" + App._jQueryUI + "css/jquery-ui-1.10.3.custom.application.extend"] :
                ["jQuery",
                    "css!" + App._jQueryUI + "css/jquery-ui-1.10.3.custom.all.min"]
        },

        _jQueryUtils1: {
            deps: ["jQuery"]
        },
        _jQueryUtils2: {
            deps: ["jQuery"]
        },
        _jQueryUtils3: {
            deps: ["jQuery"]
        },
        _jQueryUtils4: {
            deps: ["_jQueryUtils8"]
        },
        _jQueryUtils5: {
            deps: ["jQuery"]
        },
        _jQueryUtils6: {
            deps: ["jQuery"]
        },
        _jQueryUtils7: {
            deps: ["jQuery"]
        },
        _jQueryUtils8: {
            deps: ["jQuery"]
        },
        _jQueryUtils9: {
            deps: ["jQuery"]
        },
        _jQueryUtils10: {
            deps: ["jQuery"]
        },
        jQueryUtils: {
            deps: App.src ?
                ["_jQueryUtils1",
                    "_jQueryUtils2",
                    "_jQueryUtils3",
                    "_jQueryUtils4",
                    "_jQueryUtils5",
                    "_jQueryUtils6",
                    "_jQueryUtils7",
                    "_jQueryUtils8",
                    "_jQueryUtils9",
                    "_jQueryUtils10"] :
                ["jQuery"]
        },

        laypage: {
            deps: ["css!" + App._laypage + "skin/laypage"]
        }
    },
    paths: {
        _jQuery: App._jQuery + "jquery",

        jQuery: ((App.src) ? App._jQuery + "jquery-application-ext" : App._jQuery + "jquery.min.js?ver=" + App.ver),

        validation: ((App.src) ? App._validation + "jquery.validate" : App._validation + "jquery.validate.min.js?ver=" + App.ver),

        _RSA1: App._RSA + "base64",
        _RSA2: App._RSA + "jsbn",
        _RSA3: App._RSA + "prng4",
        _RSA4: App._RSA + "rng",
        RSA: ((App.src) ? App._RSA + "rsa" : App._RSA + "/RSA.all.min.js?ver=" + App.ver),

        _jqGrid1: App._jqGrid + "extends/grid.init.load",
        _jqGrid2: App._jqGrid + "plugins/ui.multiselect",
        _jqGrid3PluAppExt_zh_CN: App._jqGrid + "plugins/grid.locale.plugin.application.extend-zh_CN",
        _jqGrid3PluAppExt_en: App._jqGrid + "plugins/grid.locale.plugin.application.extend-en",
        _jqGrid4_zh_CN: App._jqGrid + "grid.locale-zh_CN",
        _jqGrid4_en: App._jqGrid + "grid.locale-en",
        _jqGrid5Ext_zh_CN: App._jqGrid + "grid.locale-ext-zh_CN",
        _jqGrid5Ext_en: App._jqGrid + "grid.locale-ext-en",
        _jqGrid6AppExt_zh_CN: App._jqGrid + "grid.locale-application-ext-zh_CN",
        _jqGrid6AppExt_en: App._jqGrid + "grid.locale-application-ext-en",
        _jqGrid7: App._jqGrid + "jquery.jqGrid.src",
        _jqGrid8: App._jqGrid + "extends/grid.config",
        _jqGrid9: App._jqGrid + "extends/grid.extend",
        _jqGrid10: App._jqGrid + "extends/grid.override",
        _jqGrid11: App._jqGrid + "extends/grid.interface",
        _jqGrid12: App._jqGrid + "extends/grid.interface.private",
        _jqGrid13: App._jqGrid + "extends/jquery.fmatter.extend",
        _jqGrid14: App._jqGrid + "extends/grid.application.extend",

        jqGrid: ((App.src) ? App._jqGrid + "debug" : App._jqGrid + "jquery.jqGrid.all.min."+App.lang+".js?ver=" + App.ver),

        _jqForm1: App._jqForm + "form-plugin",
        jqForm: ((App.src) ? App._jqForm + "form-plugin-application-ext" : App._jqForm + "form-plugin.all.min.js?ver=" + App.ver),

        _AutoTree1: App._AutoTree + "auto-tree",
        _AutoTree_en: App._AutoTree + "auto-tree-en",
        _AutoTreeAppExt_en: App._AutoTree + "auto-tree-application-ext-en",
        _AutoTree_zh_CN: App._AutoTree + "auto-tree-zh_CN",
        _AutoTreeAppExt_zh_CN: App._AutoTree + "auto-tree-application-ext-zh_CN",
        AutoTree: ((App.src) ? App._AutoTree + "auto-tree.application.extend" : App._AutoTree + "auto-tree.all.min." + App.lang + ".js?ver=" + App.ver),

        _AutoCombobox1: App._AutoCombobox + "auto-combobox",
        _AutoCombobox_en: App._AutoCombobox + "auto-combobox-en",
        _AutoComboboxAppExt_en: App._AutoCombobox + "auto-combobox-app-ext-en",
        _AutoCombobox_zh_CN: App._AutoCombobox + "auto-combobox-zh_CN",
        _AutoComboboxAppExt_zh_CN: App._AutoCombobox + "auto-combobox-app-ext-zh_CN",
        AutoCombobox: ((App.src) ? App._AutoCombobox + "auto-combobox.application.extend" : App._AutoCombobox + "auto-combobox.all.min." + App.lang + ".js?ver=" + App.ver),

        _Tips1: App._Tips + "tips",
        Tips: ((App.src) ? App._Tips + "tips-application-ext" : App._Tips + "tips.all.min.js?ver=" + App.ver),

        _artDialog1: App._artDialog + "dialog-plus",
        _artDialog2: App._artDialog + "dialog.framework.extend",
        _artDialogAppExt_en: App._artDialog + "dialog-application-ext-en",
        _artDialogAppExt_zh_CN: App._artDialog + "dialog-application-ext-zh_CN",
        artDialog: ((App.src) ? App._artDialog + "dialog.application.extend" : App._artDialog + "dialog.all.min." + App.lang + ".js?ver=" + App.ver),

        _zTree1: App._zTree + "js/jquery.ztree.all",
        _zTree2: App._zTree + "js/jquery.ztree.exhide",
        _zTree3: App._zTree + "jquery.ztree.ext",
        _zTree4: App._zTree + "jquery.ztree.framework.ext",
        zTree: ((App.src) ? App._zTree + "jquery.ztree.application.ext" : App._zTree + "jquery.ztree.all.min.js?ver=" + App.ver),

        _OUI1: App._OUI + "1_oui.util",
        _OUI2: App._OUI + "2_oui.position",
        OUI: ((App.src) ? App._OUI + "oui.application.extend" : App._OUI + "oui.all.min.js?ver=" + App.ver),

        _jQueryBlockUI: ((App.src) ? App._jQueryBlockUI + "jquery.blockUI" : App._jQueryBlockUI + "jquery.blockUI.all.min.js?ver=" + App.ver),
        _jQueryBlockUI2: App._jQueryBlockUI + "jquery.blockUI.framework.ext",
        jQueryBlockUI: ((App.src) ? App._jQueryBlockUI + "jquery.blockUI.application.extend" : App._jQueryBlockUI + "jquery.blockUI.extend.all.min.js?ver=" + App.ver),

        _Underscore1: App._Underscore + "underscore",
        _Underscore2: App._Underscore + "underscore-framework-ext",
        Underscore: ((App.src) ? App._Underscore + "underscore-application-ext" : App._Underscore + "underscore-all.min.js?ver=" + App.ver),

        _jQueryUI1: App._jQueryUI + "jquery-ui-1.10.4",
        _jQueryUI2: App._jQueryUI + "jquery-ui-1.10.4-override",
        _jQueryUI3: App._jQueryUI + "jquery-ui-1.10.4-extend",
        _jQueryUI4: App._jQueryUI + "jquery-ui-1.10.4.application.extend",
        _jQueryUI5: App._jQueryUI + "jquery.ui.datepicker-" + App.lang,
        jQueryUI: ((App.src) ? App._jQueryUI + "jquery.ui.ext-" + App.lang : App._jQueryUI + "jquery-ui-1.10.4.all.min." + App.lang + ".js?ver=" + App.ver),

        echarts: App._echarts + "echarts.min",

        _jQueryUtils1: App._jQueryUtils + "jquery.ui.position-utils",
        _jQueryUtils2: App._jQueryUtils + "jquery-array-utils",
        _jQueryUtils3: App._jQueryUtils + "jquery-bool-utils",
        _jQueryUtils4: App._jQueryUtils + "jquery-date-utils",
        _jQueryUtils5: App._jQueryUtils + "jquery-event-utils",
        _jQueryUtils6: App._jQueryUtils + "jquery-html-utils",
        _jQueryUtils7: App._jQueryUtils + "jquery-number-utils",
        _jQueryUtils8: App._jQueryUtils + "jquery-object-util",
        _jQueryUtils9: App._jQueryUtils + "jquery-string-utils",
        _jQueryUtils10: App._jQueryUtils + "jquery-url-utils",
        jQueryUtils: ((App.src) ? App._jQueryUtils + "utils-application.extend" : App._jQueryUtils + "jquery-utils.all.min.js?ver=" + App.ver),

        laypage: ((App.src) ? App._laypage + "laypage" : App._laypage + "laypage.min.js?ver=" + App.ver)
    }
};