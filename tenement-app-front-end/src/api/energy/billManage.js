import axios from 'axios'

export default {
  /**
   * 查询账单添加列表数据
   * @returns {*}
   */
  billAddlist (condition, paging) {
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
    //收款管理页面有这个字段
    if(condition.billStartEndDate && condition.billStartEndDate.length==2){
      condition.queryStartTime = condition.billStartEndDate[0];
      condition.queryEndTime = condition.billStartEndDate[1];
      delete condition.billStartEndDate;
    }

    return axios.post('/energy/billManage/queryBillByPageByBillAdd.json', {
      ...condition,
      ...paging
    })
  },
  /**
    * 查询账单审核列表数据
    * @returns {*}
    */
  billAuditlist (condition, paging) {
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
        //收款管理页面有这个字段
        if(condition.billStartEndDate && condition.billStartEndDate.length==2){
            condition.queryStartTime = condition.billStartEndDate[0];
            condition.queryEndTime = condition.billStartEndDate[1];
            delete condition.billStartEndDate;
        }

        return axios.post('/energy/billManage/queryBillByPageByBillAudit.json', {
            ...condition,
            ...paging
    })
  },
  /**
    * 查询账单审核列表数据
    * @returns {*}
    */
  billReceivelist (condition, paging) {
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
        //收款管理页面有这个字段
        if(condition.billStartEndDate && condition.billStartEndDate.length==2){
            condition.queryStartTime = condition.billStartEndDate[0];
            condition.queryEndTime = condition.billStartEndDate[1];
            delete condition.billStartEndDate;
        }

        return axios.post('/energy/billManage/queryBillByPageByBillReceive.json', {
            ...condition,
            ...paging
    })
  },
  /**
   * 获取能耗账单详情数据
   * @returns {*}
   */
  detail (id) {
    return axios.get('/energy/billManage/queryBillDetail.json', {
      params: {
        id
      }
    })
  },
  /**
   * 根据站点账单审核状态统计
   */
  total (siteId) {
    return axios.get('/energy/billManage/countBillAuditData.json', {
      params: {
        siteId
      }
    })
  },
  /**
   * 根据站点账单支付状态统计
   * @returns {*}
   */
  countBillPayData (siteId) {
    return axios.get('/energy/billManage/countBillPayData.json',{
      params: {
        siteId
      }
    })
  },
  /**
   * 删除站点
   * @returns {*}
   */
  delete (id) {
    return axios.get('/energy/billManage/delBillById.json', {
      params: {
        id
      }
    })
  },
  /**
   * 保存能耗账单信息
   * @returns {*}
   */
  save(vo) {
    return axios.post('/energy/billManage/saveOrUpdateBill.json', vo)
  },
  /**
   * 下载模板
   * @returns {*}
   */
  getBillTemplate (templateType,siteId) {
    window.open(`${process.env.VUE_APP_API_PREFIX}/energy/billManage/getBillTemplate.htm?templateType=${templateType}&siteId=${siteId}`,'_blank');
  },
  /**
   * 上传模板
   * @returns {*}
   */
  batchImport (vo) {
    return axios.post('/energy/billManage/batchImport.json', vo)
  },
  /**
   * 能耗账单审核
   * @returns {*}
   */
  auditBill (vo) {
    return axios.post('/energy/billManage/auditBill.json', vo)
  },
  /**
   * 能耗账单缴费
   * @returns {*}
   */
  payment (vo) {
    return axios.post('/energy/billManage/payment.json', vo)
  },
  /**
   * 打印付款通知书
   * @returns {*}
   */
  billPaymentNotice (billId) {
    window.open(`${process.env.VUE_APP_API_PREFIX}/energy/billManage/billPaymentNotice.htm?billId=${billId}`,'_blank');
  },
  /**
   * 能耗账单催缴
   * @returns {*}
   */
  reminderPayBill (vo) {
    return axios.post('/energy/billManage/reminderPayBill.json', vo)
  },
  /**
   * 账单 Excel导出
   * @returns {*}
   */
  exportExcel (billIds) {
    window.open(`${process.env.VUE_APP_API_PREFIX}/energy/billManage/exportExcel.htm?billIds=${billIds}`,'_blank');
  },


}
