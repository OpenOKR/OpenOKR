import axios from 'axios'

export default {
  list (condition, pageInfo) {
    pageInfo = Object.assign({
      currentPage: 1,
      pageSize: 10
    }, pageInfo)

    return axios.post('/merch/region/getRegionList.json', {
      ...condition,
      ...pageInfo
    }
    )
  },
  officeOptions (siteId) {
    return axios.get('/merch/region/getOfficeList.json', {
      params: {
        siteId
      }
    })
  },
  siteOptions (unionId) {
    return axios.get('/merch/region/getSiteList.json', {
      params: {
        unionId
      }
    })
  },
  detail (regionId) {
    return axios.get('/merch/region/detail.json', {
      params: {
        regionId
      }
    })
  },
  insertMany (listVo) {
    return axios.post('/merch/region/insert.json', listVo)
  },
  update (vo) {
    return axios.post('/merch/region/update.json', vo)
  },
  delete (regionId) {
    return axios.get('/merch/region/delete.json', {
      params: {
        regionId
      }
    })
  }
}
