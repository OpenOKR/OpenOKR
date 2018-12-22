var App = App || {};
//
/** ************************************************************ begin 应用级别 */
/** begin Application plugin */
//
App._j_a = App["staticContextPath"] + "/assets/js/application/";
//
require.paths["countUp"] = App._j_a + "custom/countUp.min";
require.shim["countUp"] = {
    "deps": ["jQuery"]
};
require.paths["waves"] = App._j_a + "custom/waves";
require.shim["waves"] = {
    "deps": ["jQuery"]
};
//
require.paths["jQueryNiceScroll"] = App._j_a + "nicescroll/jquery.nicescroll";
require.shim["jQueryNiceScroll"] = {
    "deps": ["jQuery"]
};
/** end Application plugin */

/** ************************************************************ end 应用级别 */