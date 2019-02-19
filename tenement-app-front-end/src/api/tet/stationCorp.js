import axios from 'axios'

export default {
  /**
   * 查询单元企业关系列表
   * @returns {*}
   */
  list(condition, paging) {
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
    return axios.post('/tet/stationOccupyInfo/queryListByPage.json', {
      ...condition,
      ...paging
    })

  },
  /**
   * 查询单元记录列表
   * @returns {*}
   */
  stationHistory(condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    return axios.post('/tet/stationOccupyInfo/pageStationOccupyRec.json', {
      ...condition,
      ...paging
    })

  },
  /**
   * （批量）编辑单元企业关系
   * @returns {*}
   */
  saveStationCorp (vo) {
    return axios.post('/tet/stationOccupyInfo/saveStationOccupyInfo.json', vo)
  },
  /**
   * 删除单元企业关系
   * @returns {*}
   */
  deleteStationCorp(id) {
    return axios.get('/tet/stationOccupyInfo/delStationOccupyInfo.json', {
      params: {
        id
      }
    })
  },
  /**
   * 查询单元当前占用企业信息
   * @returns {*}
   */
  getStationCurrCorpInfo(stationId) {
    return axios.get('/tet/stationOccupyInfo/getStationCurrCorpInfo.json', {
      params: {
        stationId
      }
    })
  }

}
