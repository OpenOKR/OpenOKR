import axios from 'axios'

export default {
  /**
   * 查询收支明细列表数据
   * @returns {*}
   */
  list (condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    if(condition.startEndTime && condition.startEndTime.length==2){
      condition.queryStartTime = condition.startEndTime[0];
      condition.queryEndTime = condition.startEndTime[1];
      delete condition.startEndTime;
    }
    return axios.post('/energy/payRec/queryPayRecByPage.json', {
      ...condition,
      ...paging
    })
  },
  /**
   * 详情
   */
  detail (payBillRelId) {
    return axios.get('/energy/payRec/queryPayRecDetail.json', {
      params: {
        payBillRelId
      }
    })
  },
  /**
   * 支付明细导出Excel
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
    if(vo.startEndTime && vo.startEndTime.length==2){
      vo.queryStartTime = vo.startEndTime[0];
      vo.queryEndTime = vo.startEndTime[1];
      delete vo.startEndTime;
    }
    var pargam = setUrlK(vo);
    window.open(`${process.env.VUE_APP_API_PREFIX}/energy/payRec/exportExcel.htm?${pargam}`,'_blank');
  },
}
