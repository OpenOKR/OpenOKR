import axios from 'axios'

export default {
  /**
   * 列表
   */
  list (condition, pageInfo) {
    pageInfo = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, pageInfo)

    return axios.post('/merch/office/getOfficeList.json', {
      ...condition,
      ...pageInfo
    })
  },
  /**
   * 统计数据
   */
  officeStatic (unionId) {
    return axios.get('/merch/office/getOfficeStatic.json', {
      params: {
        unionId
      }
    })
  },
  /**
   * 查询物业的类型选项
   */
  stationOptions () {
    return axios.get('/merch/office/getStationTypeList.json')
  },
  /**
   * 产业属性选项
   */
  industryOptions () {
    return axios.get('/merch/office/getIndutryList.json')
  },
  /**
   * 详情
   */
  detail (officeId) {
    return axios.get('/merch/office/detail.json', {
      params: {
        officeId
      }
    })
  },
  /**
   * 添加
   */
  update (vo) {
    return axios.post('/merch/office/update.json', vo)
  },
  /**
   * 删除
   */
  delete (officeId) {
    return axios.get('/merch/office/delete.json', {
      params: {
        officeId
      }
    })
  }
}
