import Mock from 'mockjs'
// import '@/mock/login'
import '@/mock/merch/corp'
import '@/mock/merch/region'
// import '@/mock/merch/office'
import '@/mock/merch/station'
// import common from './mock.utils'

// Mock.mock(new RegExp(common.ajaxPrefix + '/uploadAndDown/file/upload.json'), _.merge({}, common.baseSuccess, {
//   "data": {
//     "id": "1234567",
//     "url": "https://cn.vuejs.org/images/logo.png",
//   },
// }));

// 设置全局延时 没有延时的话有时候会检测不到数据变化 建议保留
Mock.setup({
  timeout: '300-600'
})
