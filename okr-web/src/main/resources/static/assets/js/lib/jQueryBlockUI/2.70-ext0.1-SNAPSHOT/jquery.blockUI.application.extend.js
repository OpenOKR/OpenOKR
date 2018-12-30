;
(function () {
    "use strict";

    $.extend($.blockUI.defaults, {
        message: '<div class="blockUI-loadingbar-img"></div>',//TODO:原值<h1>Please wait...</h1>
        css: {
            top: '50%',  //top:		'40%',
            width: '32px',//TODO:原值width:		'30%',和 css样式.blockUI-loadingbar-img 宽度一致
            left: '50%',//TODO:原值left:		'35%'
            border: '0px solid #aaa',//TODO:原值border:		'3px solid #aaa'
            backgroundColor: 'none'// TODO: 原值backgroundColor:'#fff',
        },
        // styles for the overlay
        overlayCSS: {
            opacity: 0//TODO:原值opacity:			0.6
        },

        // z-index for the blocking overlay
        baseZ: 9989//TODO:原值baseZ: 1000
    });

})();