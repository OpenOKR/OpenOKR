import axios from 'axios'

export default {
  importDataUrl: process.env.VUE_APP_API_PREFIX + '/merch/station/batchImport.json',
  templateUrl: process.env.VUE_APP_API_PREFIX + '/merch/station/template.htm',
  list (condition, pageInfo) {
    pageInfo = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, pageInfo)

    if (condition.region) {
      condition.region1 = condition.region[0]
      condition.region2 = condition.region[1]
      delete condition.region
    }

    return axios.post('/merch/station/getStationList.json', {
      ...condition,
      ...pageInfo
    }
    )
  },
  //根据区域id，获取单元列表（无分页）
  stationOptions (regionArray,officeId) {
    let params = {}
    //拼接参数 params = {
    //  regionLabelId:'value',
    //  regionLabel2Id:'value'
    // }
    regionArray.map((item,index)=>{
      params['regionLabel'+(index>0?(index+1):'')+'Id'] = item;
    });
    params.officeId = officeId;
    return axios.get('/merch/station/getStationByRegion.json', {
      params
    })
  },
  propertyOptions (siteId) {
    return axios.get('/merch/station/getPropertyList.json', {
      params: {
        siteId
      }
    })
  },
  regionOptions (officeId) {
    return axios.get('/merch/station/getRegionList.json', {
      params: {
        officeId
      }
    })
  },
  stationTypeOptions (officeId) {
    return axios.get('/merch/station/getStationType.json', {
      params: {
        officeId
      }
    })
  },
  detail (stationId) {
    return axios.get('/merch/station/detail.json', {
      params: {
        stationId
      }
    })
  },
  insert (vo) {
    return axios.post('/merch/station/insert.json', vo)
  },
  update (vo) {
    return axios.post('/merch/station/update.json', vo)
  },
  delete (stationId) {
    return axios.get('/merch/station/delete.json', {
      params: {
        stationId
      }
    })
  },
  deleteMany (regionIds) {
    regionIds = regionIds.join(',')
    return axios.get('/merch/station/batchDelete.json', {
      params: {
        regionIds
      }
    })
  }
}
