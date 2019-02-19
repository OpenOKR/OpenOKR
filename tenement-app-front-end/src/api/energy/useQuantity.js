import axios from 'axios'

export default {
  /**
   * 查询电表用量列表数据
   * @returns {*}
   */
  list (condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    if(condition.startEndTime && condition.startEndTime.length==2){
      condition.dataStartTime = condition.startEndTime[0];
      condition.dataEndTime = condition.startEndTime[1];
      delete condition.startEndTime;
    }

    if(condition.regionId){
      if(condition.regionId.length>=1){
        condition.levelId = condition.regionId[0];
      }
      if(condition.regionId.length>=2){
        condition.level2Id = condition.regionId[1];
      }
      delete condition.regionId;
    }

    return axios.post('/energy/useQuantity/queryEcaUseQuantityByPage.json', {
      ...condition,
      ...paging
    })
  },
  /**
   * 详情
   */
  detail (id) {
    return axios.get('/energy/useQuantity/getEcaUseQuantityInfoById.json', {
      params: {
        id
      }
    })
  },
  /**
   * Excel导出 电表用量
   * @returns {*}
   */
  exportExcel (vo) {
    //json 转 url
    function setUrlK(ojson) {
      var s='',name, key;
      for(var p in ojson) {
        if(!ojson[p]) {return null;}
        if(ojson.hasOwnProperty(p)) { name = p };
        key = ojson[p];
        s += "&" + name + "=" + encodeURIComponent(key);
      };
      return s.substring(1,s.length);
    };
    var pargam = setUrlK(vo);
    window.open(`${process.env.VUE_APP_API_PREFIX}/energy/useQuantity/exportExcel.htm?${pargam}`,'_blank');
  },
}
