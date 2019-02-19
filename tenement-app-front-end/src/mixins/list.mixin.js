/*
 * file name : 创建/编辑数据 Mixin
 * author : linwu (http://linwu.name)
 * datetime : 2018-08-15 20:09:06
 */

// import { mapState } from 'vuex'
import pageMixin from './page'

export default {
  data () {
    return {
    }
  },
  mixins: [
    pageMixin
  ],
  computed: {
    token () {
      let hash = document.location.hash
      let indexToken = hash.indexOf('token=')
      return indexToken > 0 ? hash.substr(indexToken + 6) : ''
    }
  },
  methods: {
    refreshTable (componentName) {
      this.$refs[componentName || 'tableMain'].fetchData()
    }
  },
  created () {
  }
}
