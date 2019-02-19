import axios from 'axios'

export default {
  /**
   * 统计数据
   */
  queryStatistics(siteId) {
    return axios.post('/invoice/apply/queryStatistics.json', {
      siteId
    })
  },
  /**
   * 列表
   */
  list(condition, pageInfo) {
    pageInfo = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, pageInfo)

    return axios.post('/invoice/apply/queryApplyList.json', {
      ...condition,
      ...pageInfo
    })
  },
  /**
   * 根据企业ID获取可开票限额
   */
  queryCorpEtaxInfo(corpIds) {
    return axios.post('/invoice/apply/queryCorpEtaxInfo.json', corpIds)
  },
  /**
   * 获取账单开票企业、费项、金额信息
   */
  queryEnergyInfo(vo) {
    return axios.post('/invoice/apply/queryEnergyInfo.json', vo)
  },
  /**
   * 根据企业ID和账单ID列表，获取详细信息
   */
  queryBatchInfo(siteId,billIds) {
      return axios.post('/invoice/apply/queryBatchInfo.json', {
          siteId,
          billIds
      })
  },

  /**
   * 提交开票申请
   */
  applyInvoice(vo) {
    return axios.post('/invoice/apply/applyInvoice.json', vo)
  },

  /**
   * 批量申请开票
   */
  batchApply(vo) {
      return axios.post('/invoice/apply/batchApply.json', vo)
  },
  /**
   * 获取费项列表
   */
  getFeeTypeList() {
    return axios.get('/energy/common/getFeeTypeList.json')
  },
   /**
   * 获取账单已开票发票列表
   */
   getInvoiceList(billId) {
    return axios.get('/invoice/apply/listInvoiceByBillId.json',{
      params: {
        billId
      }
    })
  },

}
