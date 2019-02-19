import axios from 'axios'

export default {
  /**
   * 查询电表单元关系列表数据
   * @returns {*}
   */
  list (condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    if(condition.regionId){
      if(condition.regionId.length>=1){
        condition.levelId = condition.regionId[0];
      }
      if(condition.regionId.length>=2){
        condition.level2Id = condition.regionId[1];
      }
      delete condition.regionId;
    }
    return axios.post('/energy/meterUnitRel/getMeterUnitRelList.json', {
      ...condition,
      ...paging
    })
  },
  /**
   * 新增或编辑电表单元关系数据
   * @returns {*}
   */
  save (vo) {
    return axios.post('/energy/meterUnitRel/saveOrUpdateMeterUnitRel.json', vo)
  },
  /**
   * 导出电表单元关系 Excel
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
    window.open(`${process.env.VUE_APP_API_PREFIX}/energy/meterUnitRel/exportExcel.htm?${pargam}`,'_blank');
  },
}
