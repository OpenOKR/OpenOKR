import axios from 'axios'

export default {
  /**
   * 查询费项审核列表
   * @returns {*}
   */
  getFeeTypeAuditList (condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    //修改参数
    if(condition.regionId){
      if(condition.regionId.length>=1){
        condition.levelId = condition.regionId[0];
      }
      if(condition.regionId.length>=2){
        condition.level2Id = condition.regionId[1];
      }
      delete  condition.regionId;
    }

    return axios.post('/tet/feeTypeAudit/getFeeTypeAuditList.json', {
      ...condition,
      ...paging
    })
  },
  /**
   * 新增或者编辑费项审核
   * @returns {*}
   */
  saveFeeTypeAudit (vo) {
    return axios.post('/tet/feeTypeAudit/saveFeeTypeAudit.json', vo)
  },
  /**
   * 删除费项审核
   * @returns {*}
   */
  deleteFeeTypeAudit(stationId) {
    return axios.get('/tet/feeTypeAudit/deleteFeeTypeAudit.json', {
      params: {
        stationId
      }
    })
  }

}
