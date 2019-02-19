import axios from 'axios'

export default {
  /**
   * 项目类型列表
   * @returns {*}
   */
  queryPageData (condition, pageInfo) {
    pageInfo = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, pageInfo)
    return axios.post('/pim/type/queryPageData.json', {
      ...condition,
      ...pageInfo
    })
  },
  /**
   * 保存/更新项目类型
   * @returns {*}
   */
  saveOrUpdate (vo) {
    return axios.post('/pim/type/saveOrUpdate.json', vo)
  },
  /**
   * 删除项目类型
   */
  deleteType (id) {
    return axios.get('/pim/type/deleteType.json',{
      params: {
        id
      }
    })
  },



}

