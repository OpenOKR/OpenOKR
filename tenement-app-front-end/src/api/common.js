import axios from 'axios'

export default {
  /**
   * 图片上传地址
   */
  uploadUrl: process.env.VUE_APP_API_PREFIX + '/upload/attachment/upload.json',

  /**
   * 获取站点列表
   */
  getSiteList(unionId) {
    return axios.get('/common/getSiteList.json', {
      params: {
        unionId
      }
    })
  },

  /**
   * 根据平台ID获取服务商列表
   */
  loadService(unionId) {
    return axios.get('/common/loadService.json', {
      params: {
        unionId
      }
    })
  },

}
