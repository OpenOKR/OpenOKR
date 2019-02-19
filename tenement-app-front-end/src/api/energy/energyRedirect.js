import axios from 'axios'

export default {

  /**
   * 获取重定向参数
   */
  HLSystem (siteId) {
    return axios.get('/energy/redirect/getRedirectData.json', {
      params: {
        siteId
      }
    })
  },
}
