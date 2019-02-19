import axios from 'axios'
import util from '@/libs/util'

export default {
  siteOptions(unionId) {
    //invoice/record
    return axios.get('/invoice/config/item/getSiteList.json', {
      params: {
        unionId
      }
    })
  },
  queryCurrentMonthCollect(siteId) {
    return axios.get('/invoice/record/queryCurrentMonthCollect.json',{
      params:{
        siteId
      }
    })
  },
  getInvoiceType() {
    return axios.get('/invoice/record/getInvoiceType.json')
  },
  getInvoiceInfoUrlById(id) {
    return axios.get('/invoice/record/getInvoiceInfoUrlById.json',{
        params:{
            id
        }
    })
  },
  revokeInvoiceById(id) {
    return axios.get('/invoice/record/revokeInvoiceById.json',{
        params:{
            id
        }
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

    if(condition.startTimeStr){
      condition.startTimeStr = util.dateFormat(new Date(condition.startTimeStr), 'yyyy-MM-dd hh:mm')
    }
    if(condition.endTimeStr){
      condition.endTimeStr = util.dateFormat(new Date(condition.endTimeStr), 'yyyy-MM-dd hh:mm')
    }

    return axios.post('/invoice/record/queryInvoiceRecordPage.json', {
      ...condition,
      ...pageInfo
    })
  },
}
