import Vue from 'vue'
import axios from 'axios'

axios.defaults.headers.post['Content-Type'] = 'application/json'
axios.defaults.headers.put['Content-Type'] = 'application/json'
axios.defaults.headers.delete['Content-Type'] = 'application/json'
axios.defaults.baseURL = process.env.VUE_APP_API_PREFIX
// axios.defaults.baseURL = '/api'
axios.interceptors.response.use(res => {
  if (res.data.code === 99) {
    // Vue.prototype.$message({
    //   message: 'token无效',
    //   type: 'error'
    // })
    console.warn('token无效')
  } else if (res.data.code !== 0) {
    // Vue.prototype.$message({
    //   message: `错误：${res.data.code} : ${res.data.message}`,
    //   type: 'error'
    // })
    console.warn(`错误：${res.data.code} : ${res.data.message}`)
  }
  return res.data
}, (error) => {
  if (error.response) {
    switch (error.response.status) {
      case 400:
        Vue.prototype.$message({
          message: error.response.data || '请求参数异常',
          type: 'error'
        })
        break
      case 401:
        sessionStorage.removeItem('user')
        Vue.prototype.$message({
          message: error.response.data || '密码错误或账号不存在！',
          type: 'warning',
          onClose: function () {
            location.reload()
          }
        })
        break
      case 403:
        Vue.prototype.$message({
          message: error.response.data || '无访问权限，请联系园区客户管理员',
          type: 'warning'
        })
        break
      default:
        Vue.prototype.$message({
          message: error.response.data || '服务端异常，请联系技术支持',
          type: 'error'
        })
    }
  } else {
    Vue.prototype.$message({
      message: error.message || '服务端异常，请联系技术支持',
      type: 'error'
    })
  }
  return Promise.reject(error)
}
)

Vue.prototype.$axios = axios
