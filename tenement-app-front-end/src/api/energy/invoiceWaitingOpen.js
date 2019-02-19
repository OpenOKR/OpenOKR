import axios from 'axios'

export default {
  /**
   * 根据条件查询待开单据数据
   * @returns {*}
   */
  list (condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    return axios.post('/energy/ecaInvWaitingOpen/queryWaitingOpenByPage.json', {
      ...condition,
      ...paging
    })
  },

  /**
   * 撤销开票操作
   * @returns {*}
   */
  revokeInvoice (id) {
    return axios.get('/energy/ecaInvWaitingOpen/cancelOpeningInvoice.json', {
      params: {
        id
      }
    })
  },

  /**
   * 检测是否已经开票
   * @returns {*}
   */
  checkInvoiceStatus (vo) {
      return axios.post('/energy/ecaInvWaitingOpen/checkInvoiceStatus.json', vo)
  },

  /**
   * 批量执行去开票操作
   * @returns {*}
   */
  batchGoToInvoice (vo) {
      return axios.post('/energy/ecaInvWaitingOpen/batchGoToInvoice.json', vo)
  },

  /**
   * 跳转至开票预览页面
   * @returns {*}
   */
  jumpToInvoicePreview (reqUrl, reqParam) {
      window.open(`${reqUrl}?reqParam=${reqParam}`,'_blank');
  },

}
