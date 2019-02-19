import axios from 'axios'

export default {
  /**
   * 园区类型列表
   * @returns {*}
   */
  list (condition, pageInfo) {
    pageInfo = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, pageInfo)
    return axios.post('/park/type/getParkTypePage.json', {
      ...condition,
      ...pageInfo
    })
  },
  /**
   * 园区类型删除
   * @returns {*}
   */
  delete (id) {
    return axios.get('/park/type/deleteParkType.json', {
      params: {
        id
      }
    })
  },
  /**
   * 园区类型修改
   * @returns {*}
   */
  saveParkType (detail) {
    return axios.post('/park/type/saveParkType.json', detail)
  },

}
