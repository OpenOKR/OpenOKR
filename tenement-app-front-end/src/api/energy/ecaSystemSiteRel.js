import axios from 'axios'

export default {
  /**
   * 根据条件查询能耗系统数据
   * @returns {*}
   */
  list (condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    return axios.post('/energy/ecaSystemSiteRel/getECASystemSiteRelList.json', {
      ...condition,
      ...paging
    })
  },

  /**
   * 新增或编辑能耗系统
   * @returns {*}
   */
  save (vo) {
    return axios.post('/energy/ecaSystemSiteRel/saveOrUpdateECASystemRel.json', vo)
  },

  /**
   * 同步系统设备数据
   * @returns {*}
   */
  syncDeviceList (id) {
    return axios.get('/energy/ecaSystemSiteRel/syncDeviceList.json', {
      params: {
        id
      }
    })
  },

  /**
   * 基础档案库下发
   * @returns {*}
   */
  sendLocationInfo (siteId,outSysId,outSysName) {
      return axios.get('/energy/ecaSystemSiteRel/sendLocationInfo.json', {
          params: {
              siteId,
              outSysId,
              outSysName
          }
      })
  },

  /**
   * 删除能耗系统
   * @returns {*}
   */
  delete (id) {
    return axios.get('/energy/ecaSystemSiteRel/deleteECASystemSiteRel.json', {
      params: {
        id
      }
    })
  },
  /**
   * 获取能耗厂商列表数据
   */
  getEcaSystemList () {
    return axios.get('/energy/ecaSystemSiteRel/getEcaSystemList.json')
  },
  /**
   * 能耗开通管理列表
   * @returns {*}
   */
  getEcaPageData (condition, paging) {
    paging = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, paging)

    return axios.post('api/product/getEcaPageData.json', {
      ...condition,
      ...paging
    })
  },
  /**
   * 能耗批量开通
   * @returns {*}
   */
  batchOpenEcaService (vo) {
    return axios.post('/api/product/batchOpenEcaService.json', vo)
  },

  /**
   * 导出能耗设备用量数据 Excel
   * @returns {*}
   */
  exportAirUseDailyDataExcel (vo) {
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
      window.open(`${process.env.VUE_APP_API_PREFIX}/energy/ecaAirUseDaily/exportExcel.htm?${pargam}`,'_blank');
  },

}
