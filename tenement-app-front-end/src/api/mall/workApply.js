import axios from 'axios'

export default {
  /**
   * 加班申请列表
   * @returns {*}
   */
  getWorkApplyList (condition, pageInfo) {
    pageInfo = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, pageInfo)
    //收款管理页面有这个字段
    if(condition.searchStartEndDate && condition.searchStartEndDate.length==2){
      condition.applyTimeBegin = condition.searchStartEndDate[0];
      condition.applyTimeEnd = condition.searchStartEndDate[1];
      delete condition.searchStartEndDate;
    }
    return axios.post('/api/mall/extraWorkApply/getApplyStatisticPage.json', {
      ...condition,
      ...pageInfo
    })
  },
  /**
   * 加班申请详情列表
   * @returns {*}
   */
  getWorkApplyDetail (condition, pageInfo) {
    pageInfo = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, pageInfo)
    //收款管理页面有这个字段
    if(condition.searchStartEndDate && condition.searchStartEndDate.length==2){
      condition.applyTimeBegin = condition.searchStartEndDate[0];
      condition.applyTimeEnd = condition.searchStartEndDate[1];
      delete condition.searchStartEndDate;
    }

    if(condition.shopTypeId){
      if( condition.shopTypeId.length>=1){
        condition.shopTypeLevelOne = condition.shopTypeId[0];
      }
      if( condition.shopTypeId.length>=2){
        condition.shopTypeLevelTwo = condition.shopTypeId[1];
      }

    }

    return axios.post('/api/mall/extraWorkApply/getApplyDetailStatisticPage.json', {
      ...condition,
      ...pageInfo
    })
  },
  /**
   * 业态选项
   */
  shopTypeOptions (siteId) {
    return axios.get('/api/mall/shopType/getShopTypeList.json',{
      params: {
        siteId
      }
    })
  },
  /**
   * Excel导出 放行申请表列表
   * @returns {*}
   */
  exportApplyStatistic (vo) {
    //json 转 url
    function setUrlK(ojson) {
      var s='',name, key;
      for(let p in ojson) {
        if(!ojson[p]) {return null;}
        if(ojson.hasOwnProperty(p)) { name = p };
        key = ojson[p];
        s += "&" + name + "=" + encodeURIComponent(key);
      };
      return s.substring(1,s.length);
    };
    //收款管理页面有这个字段
    if(vo.searchStartEndDate && vo.searchStartEndDate.length==2){
      vo.applyTimeBegin = vo.searchStartEndDate[0];
      vo.applyTimeEnd = vo.searchStartEndDate[1];
      delete vo.searchStartEndDate;
    }
    let pargam = setUrlK(vo);
    window.open(`${process.env.VUE_APP_API_PREFIX}/api/mall/extraWorkApply/exportApplyStatistic.htm?${pargam}`,'_blank');
  },
  /**
   * Excel导出 放行申请表详情
   * @returns {*}
   */
  exportApplyDetailStatistic (vo) {
    //json 转 url
    function setUrlK(ojson) {
      var s='',name, key;
      for(let p in ojson) {
        if(!ojson[p]) {return null;}
        if(ojson.hasOwnProperty(p)) { name = p };
        key = ojson[p];
        s += "&" + name + "=" + encodeURIComponent(key);
      };
      return s.substring(1,s.length);
    };
    //收款管理页面有这个字段
    if(vo.searchStartEndDate && vo.searchStartEndDate.length==2){
      vo.applyTimeBegin = vo.searchStartEndDate[0];
      vo.applyTimeEnd = vo.searchStartEndDate[1];
      delete vo.searchStartEndDate;
    }
    if(vo.shopTypeId){
      if( vo.shopTypeId.length>=1){
        vo.shopTypeLevelOne = vo.shopTypeId[0];
      }
      if( vo.shopTypeId.length>=2){
        vo.shopTypeLevelTwo = vo.shopTypeId[1];
      }
      delete vo.shopTypeId;
    }
    let pargam = setUrlK(vo);
    window.open(`${process.env.VUE_APP_API_PREFIX}/api/mall/extraWorkApply/exportApplyDetailStatistic.htm?${pargam}`,'_blank');
  },
  /**
   * 获取统计页面顶部统计数据
   * @returns {*}
   */
  getTopStatisticData (vo) {
    if(vo.searchStartEndDate && vo.searchStartEndDate.length==2){
      vo.applyTimeBegin = vo.searchStartEndDate[0];
      vo.applyTimeEnd = vo.searchStartEndDate[1];
      delete vo.searchStartEndDate;
    }
    return axios.post('/api/mall/extraWorkApply/getTopStatisticData.json', vo)
  },
  /**
   * 获取详情页面顶部统计数据
   * @returns {*}
   */
  getDetailTopStatisticData (vo) {
    return axios.post('/api/mall/extraWorkApply/getDetailTopStatisticData.json', vo)
  },


}

