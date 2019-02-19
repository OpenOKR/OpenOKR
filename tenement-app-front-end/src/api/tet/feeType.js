import axios from 'axios'

export default {
  /**
   * 查询费项列表
   * @returns {*}
   */
  getFeeTypeList (condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    return axios.post('/tet/feeType/getFeeTypeList.json', {
      ...condition,
      ...paging
    })
  },
  /**
   * 新增或者编辑费项
   * @returns {*}
   */
  saveFeeType (vo) {
    return axios.post('/tet/feeType/saveFeeType.json', vo)
  },
  /**
   * 删除费项
   * @returns {*}
   */
  deleteFeeType (id) {
    return axios.get('/tet/feeType/deleteFeeType.json', {
      params: {
        id
      }
    })
  },
  /**
   * 获取全部费项-带费项审核状态
   * @returns {*}
   */
  getFeeTypesAndAuditStatus (siteId,stationId) {
    return axios.get('/tet/feeType/getFeeTypesAndAuditStatus.json', {
      params: {
        siteId,
        stationId
      }
    })
  },
  /**
   * 获取全部费项
   * @returns {*}
   */
  getFeeTypes (siteId,stationId) {
      return axios.get('/tet/feeType/getFeeTypes.json', {
          params: {
              siteId,
              stationId
          }
      })
  }

}
