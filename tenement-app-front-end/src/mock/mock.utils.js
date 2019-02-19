import _ from 'lodash'
import Mock from 'mockjs'

let common = {
  ajaxPrefix: process.env.VUE_APP_API_PREFIX,
  baseSuccess: {
    'code': 0,
    'message': 'string'
  },
  pagingInfo: {
    'data': {
      'currentPage': 0,
      'pageSize': 10,
      'totalPage': 1,
      'totalRecord': 100
    }
  }
}
common.pagingDataBase = _.merge({}, common.baseSuccess, common.pagingInfo)
common.mock = (url, data = '') => {
  Mock.mock(new RegExp(common.ajaxPrefix + url), _.merge({}, common.baseSuccess, {
    'data': data
  }))
}

export default common
