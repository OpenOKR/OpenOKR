import axios from 'axios'

export default {
  /**
   * 查询物业收款账号列表数据
   * @returns {*}
   */
  getOfficeAccountList (condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    return axios.post('/tet/officeAccount/getOfficeAccountList.json', {
      ...condition,
      ...paging
    })
  },
  /**
   * 新增或者编辑费项审核
   * @returns {*}
   */
  saveFeeTypeAudit (vo) {
    return axios.post('/tet/officeAccount/addService.json', vo)
  },
  /**
   * 删除费项审核
   * @returns {*}
   */
  deleteOfficeAccount(officeId,partnerId) {
    return axios.get('/tet/officeAccount/deleteOfficeAccount.json', {
      params: {
        officeId,
        partnerId
      }
    })
  },
  /**
   * 查询地点费项数据
   * @returns {*}
   */
  getOfficeFeeTypes(officeId,partnerId) {
    //服务商id 选填
    return axios.get('/tet/officeAccount/getOfficeFeeTypes.json', {
      params: {
        officeId,
        partnerId
      }
    })
  },
  /**
   * 查询地点费项数据
   * @returns {*}
   */
  loadService(siteId) {

    return axios.get('/tet/officeAccount/loadService.json', {
      params: {
        siteId
      }
    })
  },


}
