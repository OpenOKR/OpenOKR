import axios from 'axios'

export default {
  /**
   * 开票费项设置列表
   */
  list(condition, pageInfo) {
    pageInfo = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, pageInfo)

    return axios.post('/invoice/config/item/queryPage.json', {
      ...condition,
      ...pageInfo
    })
  },
  /**
   * 备注里面的站点列表
   */
  siteOptions(unionId) {
    return axios.get('/invoice/config/item/getSiteList.json', {
      params: {
        unionId
      }
    })
  },
  /**
   * 新增编辑时获取关联费项checkbox列表
   */
  queryFeeCheckBoxList(siteId) {
    return axios.get('/invoice/config/item/queryFeeCheckBoxList.json', {
      params: {
        siteId
      }
    })
  },
  /**
   * 保存
   */
  save(vo) {
    return axios.post('/invoice/config/item/saveOrUpdate.json', vo)
  },
  /**
   * 删除
   */
  logicDeleteById(id) {
    return axios.get('/invoice/config/item/logicDeleteById.json', {
      params: {
        id
      }
    })
  },
  /**
   * 获取全部业务变量
   */
  getAllBizVar(siteId) {
    return axios.get('/invoice/config/remark/getAllBizVar.json', {
      params: {
        siteId
      }
    })
  },
  /**
   * 根据站点id获取默认备注信息
   */
  getVOBySiteId(siteId) {
    return axios.get('/invoice/config/remark/getVOBySiteId.json', {
      params: {
        siteId
      }
    })
  },
  /**
   * 新增或编辑备注设置
   */
  saveRemark(vo) {
    return axios.post('/invoice/config/remark/saveOrUpdate.json', vo)
  },
  /**
   * 根据站点id获取默认合并设置信息
   */
  getCombine(siteId) {
    return axios.get('/invoice/config/combine/getVOBySiteId.json', {
      params: {
        siteId
      }
    })
  },
  /**
   * 新增或编辑合并设置
   */
  saveCombine(vo) {
    return axios.post('/invoice/config/combine/saveOrUpdate.json', vo)
  },
  /**
   * 根据站点id获取默认合并设置信息
   */
  getBatch(siteId) {
    return axios.get('/invoice/config/batch/getVOBySiteId.json', {
      params: {
        siteId
      }
    })
  },
  /**
   * 新增或编辑合并设置
   */
  saveBatch(vo) {
    return axios.post('/invoice/config/batch/saveOrUpdate.json', vo)
  },

}
