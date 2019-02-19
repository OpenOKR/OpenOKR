import { mapState } from 'vuex'

export default {
  data () {
    return {
      loading: true
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.d2admin.userInfo
    })
  }
}
