import axios from 'axios'

export default {
  /**
   * 客户列表
   * @returns {*}
   */
  list (vo, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    return axios.post('/corp/corpList.json', {
      ...vo,
      ...{ industryIds: vo.industryIds ? vo.industryIds.join(',') : '' },
      ...paging
    })
  },
  /**
   * 客户列表
   * @returns {*}
   */
  getCorpListBySearchKey (vo, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    return axios.post('/corp/getCorpListBySearchKey.json', {
      ...vo,
      ...paging
    })
  },
  /**
   * 详情
   */
  detail (corpId) {
    return axios.get('/corp/corpDetail.json', {
      params: {
        corpId
      }
    })
  },
  /**
   * 产业属性选项
   */
  industryOptions () {
    return axios.get('/corp/getIndustryList.json')
  },
  /**
   * 统计数据
   */
  corpStatic (unionId) {
    return axios.get('/corp/getStatic.json', {
      params: {
        unionId
      }
    })
  },
  /**
   * 统计数据
   */
  admissionList (corpId) {
    return axios.get('/corp/admissionList.json', {
      params: {
        corpId
      }
    })
  },
  /**
   * 获取企业的标签列表
   */
  getCorpLabelList (corpId,siteId) {
    return axios.get('/pcsr/corp/label/getCorpLabelList.json', {
      params: {
        corpId,
        siteId
      }
    })
  },
  /**
   * 保存企业的标签列表
   */
  saveCorpLabelList (corpId,labelId,unionId,siteId) {
      return axios.get('/pcsr/corp/label/saveCorpLabelList.json', {
          params: {
              corpId,
              labelId,
              unionId,
              siteId
          }
      })
  },
  /**
   * 获取企业的自定义标签列表
   */
  getCorpLabel (siteId,labelType) {
    return axios.get('/pcsr/corp/label/getCorpLabel.json', {
      params: {
        siteId,
        labelType,
      }
    })
  },
  /**
   * 保存企业的自定义标签
   */
  savePcsrCorpLabel (labelName,labelType,unionId,siteId) {
    // let data = {
    //   labelName,
    //   labelType,
    //   unionId,
    //   siteId
    // };
    return axios.post('/pcsr/corp/label/savePcsrCorpLabel.json', {labelName,labelType,unionId,siteId})
  },
  /**
   * 统计标签被使用数量
   */
  judgeCorpLabelIfUsed (id) {
    return axios.get('/pcsr/corp/label/judgeCorpLabelIfUsed.json', {
      params: {
        id
      }
    })
  },
  /**
   * 删除标签
   */
  deleteCorpLabel (id) {
    return axios.get('/pcsr/corp/label/deleteCorpLabel.json', {
      params: {
        id
      }
    })
  }

}
