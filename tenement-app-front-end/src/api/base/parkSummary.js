import axios from 'axios'

export default {
  /**
   * 根据站点id获取园区信息概况
   * @returns {*}
   */
  getParkSummaryDetail (id) {
    return axios.get('/park/summary/getParkSummaryDetail.json', {
      params: {
        id
      }
    })
  },
  /**
   * 根据平台id查名平台名称
   * @returns {*}
   */
  getUnionById (unionId) {
    return axios.get('/park/summary/getUnionById.json', {
      params: {
        unionId
      }
    })
  },
  /**
   * 根据平台id获取园区概况列表
   * @returns {*}
   */
  loadSiteSummaryByUnion (unionId,name) {
    return axios.get('/park/summary/loadSiteSummaryByUnion.json', {
        params: {
          unionId,
          name
        }
    })
  },
  /**
   * 保存园区信息概况
   * @returns {*}
   */
  saveParkSummary (detail) {
    return axios.post('/park/summary/saveParkSummary.json',detail)
  },
  /**
   * 保存园区信息概况
   * @returns {*}
   */
  deleteParkSummary (id) {
    return axios.get('/park/summary/deleteParkSummary.json',{
      params: {
        id
      }
    })
  },

}
