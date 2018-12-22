var UnderscoreUtil = UnderscoreUtil || {

    /**
     * <pre>
     *      例：
     *      templateId = "advertsImgTemplate"
     *      <script id="advertsImgTemplate" type="text/template">
     *          <li style="background-image:url([%=httppath%]);">
     *              [% if (url==="") { %]
     *                  <a target="_blank"></a>
     *              [%} else { %]
     *                  <a href="[%=url%]" target="_blank"></a>
     *              [% } %]
     *          </li>
     *      </script>
     *      templateData = {httppath:"a",url:"b"};
     * </pre>
     *
     * 通过模板id与模板数据获得模板内容
     * @param templateId 模板id()
     * @param templateData 模板要用到的数据
     * @returns {*}
     */
    getHtmlById: function (templateId, templateData) {
        var ele = document.getElementById(templateId),
            templateText = ele.textContent || ele.text;
        return UnderscoreUtil.getHtmlByText(templateText, templateData);
    },

    /**
     * <pre>
     *      例：
     *      templateId = '<li style="background-image:url([%=httppath%]);">'
     *              '[% if (url==="") { %]'+
     *                  '<a target="_blank"></a>'+
     *              '[%} else { %]'+
     *                  '<a href="[%=url%]" target="_blank"></a>'+
     *              '[% } %]'+
     *          '</li>';
     *      templateData = {httppath:"a",url:"b"};
     * </pre>
     *
     * 通过模板文本与模板数据获得模板内容
     * @param templateText 模板文本
     * @param templateData 模板要用到的数据
     * @returns {*}
     */
    getHtmlByText: function (templateText, templateData) {
        var templateText = templateText.replace(/\r/g, "").replace(/\n/g, ""),
            templateSetting = {
                evaluate: /\[%([\s\S]+?)%\]/g,
                interpolate: /\[%=([\s\S]+?)%\]/g,
                escape: /\[%-([\s\S]+?)%\]/g
            },
            rel;
        try {
            rel = _.template(String(templateText).replace(/^\s+|\s+$/g, ""), templateData, templateSetting);
        } catch (e) {
            console && console.log(e.source);
        }
        return rel;
    }

};