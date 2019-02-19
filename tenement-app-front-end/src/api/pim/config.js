import axios from 'axios'

export default {
  /**
   * 查询填报时间设置
   * @returns {*}
   */
  queryFillInType(vo) {
    return axios.post('/pim/fillIn/queryFillInType.json', vo)
  },
  /**
   * 保存 查询填报时间设置
   */
  saveOrUpdateFillInType (vo) {
    return axios.post('/pim/fillIn/saveOrUpdateFillInType.json', vo)
  },

}

