/*
 * file name : 创建/编辑数据 Mixin
 * author : linwu (http://linwu.name)
 * datetime : 2018-08-15 20:09:06
 */

// import { mapState } from 'vuex'
import pageMixin from './page'
import { cloneDeep } from 'lodash'

export default {
  mixins: [
    pageMixin
  ],
  data () {
    return {
      formData: {},
      originFormData: {}
    }
  },
  computed: {
    token () {
      let hash = document.location.hash
      let indexToken = hash.indexOf('token=')
      return indexToken > 0 ? hash.substr(indexToken + 6) : ''
    },
    id: {
      get: function () {
        return this.formData && this.formData.id
      },
      set: function (value) {
        this.formData.id = value
      }
    },
    isCreate: function () {
      return !this.id
    },
    isUpdate: function () {
      return !!this.id
    },
    actionName: function () {
      return this.isCreate ? '创建' : '编辑'
    }
  },
  methods: {
    reset () {
      this.id = ''
      this.$refs.formMain.resetFields()
    },
    /**
     * 继续添加
     */
    refreshAdd () {
      this.formData = cloneDeep(this.originFormData)
    },
    /**
     * 回退
     */
    goBack () {
      this.$router.go(-1)
    },
    /**
     * 上传图片空间超出限制提示
     * @param files
     * @param fileList
     */
    handleUploadExceed (files, fileList) {
      this.$message.warning('选择的文件超出了限制范围')
    }
  },
  created () {
    this.originFormData = cloneDeep(this.formData)
  }
}
