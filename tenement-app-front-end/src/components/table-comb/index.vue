<template>
  <section class="m-table-comb">
    <!--数据过滤和按钮组-->
    <section class="table-filter">
      <div class="controls">
        <slot name="controls"></slot>
      </div>
        <el-form :model="searchModelDataBase" label-width="120px" size="small">
        <el-input
          placeholder="请输入关键字"
          prefix-icon="el-icon-search"
          clearable
          v-model="searchModelDataBase.name"
          v-if="isShowBaseSearch"
          style="width: 160px;">
        </el-input>
        <slot name="baseSearchForm" :form="this.searchModelDataBase"></slot>
        <el-button type="primary" @click="search" v-if="!isOpenAdvancedSearch" style="margin-left: 10px;">查询</el-button>
        <el-button @click="openAdvancedSearch()" v-if="searchModel" style="margin-left: 10px;">{{ isOpenAdvancedSearch ? '关闭高级查询' : '高级查询' }}</el-button>
      </el-form>
    </section>
    <!--高级查询-->
    <section class="table-filter-advanced" v-show="isOpenAdvancedSearch">
      <el-form :model="searchModelData" label-width="120px" size="small">
        <slot name="advancedSearchForm" :form="this.searchModelData"></slot>
        <div class="form-controls">
          <el-button type="primary" size="mini" @click="search">查询</el-button>
          <el-button size="small" @click="reset">重置</el-button>
          <el-button size="small" @click="isOpenAdvancedSearch = false">取消</el-button>
        </div>
      </el-form>
    </section>
    <!--数据表格-->
    <section class="table-content">
      <el-table
        ref="table"
        :data="tableData"
        v-loading="loading"
        border
        @select="checkedOne"
        @select-all="checkedAll"
        @sort-change="tableSortChange"
        style="width: 100%">
        <slot name="tableColumns"></slot>
      </el-table>
    </section>
    <!--多选数据视图-->
    <section v-show="this.checkedItems && this.checkedItems.length > 0">
      <div class="checked-items">
        <transition-group tag="div" name="scale" mode="out-in">

          <span v-for="item in this.checkedItems" :key="item[customId]">{{item[checkedDispalyName]|| item.username || item.name || item.title || item[customId]}} <i @click="removeCheckedItem(item)"></i></span>
        </transition-group>
        <a href="javascript:void(0);" class="clear-all" @click="removeAllCheckedItem()">清空</a>
      </div>
    </section>
    <!--分页-->
    <section class="table-paging">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="fetchData"
        :current-page="pagingInfo.currentPage"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagingInfo.totalRecord">
      </el-pagination>
    </section>

  </section>
</template>

<script>
import { clone } from 'lodash'

export default {
  name: 'DataTableComb',
  props: {
    name: String,
    getAction: Function,
    getActionWhere: Object,
    removeAction: Function,
    removeBatchAction: Function,
    //自定义id
    customId:{
      type: String,
      default: 'id'
    },
    //显示给用户的名字
    checkedDispalyName:{
      type: String,
      default: 'name'
    },
    editPath: String,
    listPath: String,
    searchModel: Object,
    searchModelBase: {
      type: Object,
      default: () => {
        return {
          name: ''
        }
      }
    },
    pagingConfig: Object,
    autoFetch: { // 是否自动加载数据
      type: Boolean,
      default: true
    },
    sortChange: Function,
    afterFetchData:Function,
  },
  data () {
    return {
      loading: true, // 数据是否在加载中
      tableData: [], // 表格的数据
      checkedItems: [], // 选中的数据
      isOpenAdvancedSearch: false, // 是否打开高级搜索
      sortInfo: null,
      pagingInfo: { // 分页信息
        pageSize: 10,
        currentPage: 1,
        totalRecord: 0
      },
      searchModelDataBase: {},
      searchModelData: {}
    }
  },
  watch: {
    searchModelBase() {
      this.searchModelDataBase = clone(this.searchModelBase)
    },
    searchModel() {
      this.searchModelData = clone(this.searchModel)
    },
    checkedItems(oldVal, newVal){
      if(oldVal.length !== newVal){
        this.$emit('on-checked-item-change', this.checkedItems)
      }
    }
  },
  computed: {
    isShowBaseSearch () {
      return !this.$scopedSlots.baseSearchForm
    }
  },
  methods: {
    tableSortChange ({ column, prop, order }) {
      if (this.sortChange) {
        this.sortChange({ column, prop, order })
      } else if (column) {
        this.sortInfo = prop + '_' + order.substr(0, 3) // ascending descending
      } else {
        this.sortInfo = null
      }
      this.fetchData()
    },
    /**
       * 单个选中
       */
    checkedOne (selection, row) {
      let customId = this.customId
      let isExist
      let isChecked = selection.some(item => {
        return item[customId] === row[customId]
      })
      // findIndex(selection, row) >= 0;
      // 判断是否选中
      if (!isChecked) {
        for (let i = 0; i < this.checkedItems.length; i++) {
          if (this.checkedItems[i][customId] === row[customId]) {
            this.checkedItems.splice(i, 1)
            break
          }
        }
      } else {
        // 判断是否存在
        isExist = this.checkedItems.some(item => {
          return item[customId] === row[customId]
        })
        // findIndex(this.checkedItems, row) >= 0;
        if (!isExist) {
          this.checkedItems.push(row)
        }
      }
    },
    /**
       * 全选
       */
    checkedAll (selection) {
      if (selection.length > 0) {
        selection.map(item => {
          this.checkedOne(selection, item) // 逐个选中
        })
      } else {
        this.tableData.map(item => {
          this.checkedOne([], item) // 逐个取消
        })
      }
    },
    /**
       * 删除选中项
       */
    removeCheckedItem (item) {
      let customId = this.customId;
      for (let i = 0; i < this.checkedItems.length; i++) {
        if (this.checkedItems[i][customId] === item[customId]) {
          this.$refs.table.toggleRowSelection(item, false)
          for (let j = 0; j < this.tableData.length; j++) {
            if (this.tableData[j][customId] === item[customId]) {
              this.$refs.table.toggleRowSelection(this.tableData[j], false)
              break
            }
          }
          this.checkedItems.splice(i, 1)
          break
        }
      }
    },
    /**
       * 删除所有选中项
       */
    removeAllCheckedItem () {
      let customId = this.customId;
      for (let i = 0; i < this.checkedItems.length; i++) {
        this.$refs.table.toggleRowSelection(this.checkedItems[i], false)
        for (let j = 0; j < this.tableData.length; j++) {
          if (this.tableData[j][customId] === this.checkedItems[i][customId]) {
            this.$refs.table.toggleRowSelection(this.tableData[j], false)
            break
          }
        }
        this.checkedItems.splice(i, 1)
        i--
      }
    },
    /**
       * 初始化选中项
       */
    initRecordChecked () {
      let customId = this.customId;
      // 很奇怪，checkedItems 和 tableData 的值一样，但是就是用不了checkedItems 的数据
      for (let i = 0; i < this.checkedItems.length; i++) {
        for (let j = 0; j < this.tableData.length; j++) {
          if (this.checkedItems[i][customId] === this.tableData[j][customId]) {
            this.$refs.table.toggleRowSelection(this.tableData[j], true)
            break
          }
        }
      }
    },
    /**
       * 打开高级搜索
       */
    openAdvancedSearch () {
      this.isOpenAdvancedSearch = !this.isOpenAdvancedSearch
    },
    /**
       * 编辑
       */
    edit (id) {
      if (!this.editPath) {
        this.$message.error('编辑失败，因为对应的Action没有设置')
        return
      }
      this.$router.push({ path: this.editPath + '/' + id })
    },
    /**
       * 删除
       */
    remove (id) {
      let customId = this.customId
      let _this = this
      console.log(_this)
      if (!_this.removeAction) {
        _this.$message.error('删除失败，因为对应的Action没有设置')
        return
      }

      this.$msgbox({
        title: '提示',
        message: `确定要删除该数据？`,
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if (action === 'confirm') {
          _this.loading = true
          _this.removeAction(id).then(res => {
            if (res.code === 0) {
              for (let i = 0; i < _this.tableData.length; i++) {
                if (_this.tableData[i][customId] === id) {
                  _this.tableData.splice(i, 1)
                  i++
                }
              }
              if(_this.tableData.length>0){
                _this.fetchData();
              }else{
                _this.fetchData(_this.pagingInfo.currentPage-1 || 1);
              }
              _this.$message.success(`删除数据成功`)
              _this.$emit('on-remove-success')
            } else {
              _this.$message.error(res.message)
            }

            _this.loading = false
          }).catch(() => {
            _this.$message.error(`删除数据失败`)
          })
        }
      }).catch(() => {
      })
    },
    /**
       * 批量删除
       */
    removeBatch () {
      let _this = this
      let customId =this.customId
      let ids = this.checkedItems.map(item => {
        return item[customId]
      })

      if (!this.removeBatchAction) {
        this.$message.error('删除失败，因为对应的Action没有设置')
        return
      }

      if (ids.length === 0) {
        _this.$message.warning('请选择数据')
        return
      }

      this.$msgbox({
        title: '提示',
        message: `确定要删除选中的数据？`,
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(action => {
        if (action === 'confirm') {
          _this.loading = true
          _this.removeBatchAction(ids).then(() => {
            // 删除当前表格里面的数据
            for (let i = 0; i < _this.tableData.length; i++) {
              for (let j = 0; j < ids.length; j++) {
                if (_this.tableData[i][customId] === ids[j]) {
                  _this.tableData.splice(i, 1)
                  i--
                  break
                }
              }
            }
            // 删除选中的数据
            _this.checkedItems = []

            _this.loading = false
            _this.$message.success(`删除数据成功`)
            _this.$emit('on-remove-success')
          }).catch(() => {
            _this.$message.error(`删除数据失败`)
          })
        }
      }).catch(() => {
      })
    },
    /**
       * 获取数据
       * @param pageIndex
       */
    fetchData (pageIndex) {
      let _this = this

      if (!this.getAction) {
        this.$message.error('获取数据失败，因为对应的Action没有设置')
        return
      }

      _this.pagingInfo.currentPage = pageIndex || _this.pagingInfo.currentPage || 1
      _this.loading = true

      let conditionObj = this.getPageVo();
      // 请求数据
      this.getAction(conditionObj, _this.pagingInfo).then(res => {
        _this.$env === 'development' && console.log('[DEBUG] tableData:')
        _this.$env === 'development' && console.log(res)
        _this.tableData = res.data.data
        _this.pagingInfo.totalRecord = res.data.totalRecord || res.data.data.length
        // 设置已选择项目
        _this.$nextTick(() => {
          _this.initRecordChecked()
        })
        if(_this.afterFetchData && typeof _this.afterFetchData =='function'){
            _this.afterFetchData();
        }

        _this.loading = false
      }).catch(error => {
        console.error(error)
        _this.$message.error(`获取数据列表失败`)
        _this.loading = false
      })
    },
    /**
     * 获取请求的参数
     * @param pageIndex
     */
    getPageVo(pageIndex){
      let _this = this
      let conditionObj = {}
      // 基础搜索
      for (let key in this.searchModelDataBase) {
        if (this.searchModelDataBase.hasOwnProperty(key)) {
          let value = this.searchModelDataBase[key]
          if (value != undefined && value != null && value !== '' && value.length !== 0) {
            conditionObj[key] = this.searchModelDataBase[key]
          }
        }
      }
      // 高级搜索
      if (this.isOpenAdvancedSearch) {
        for (let key in this.searchModelData) {
          if (this.searchModelData.hasOwnProperty(key)) {
            let value = this.searchModelData[key]
            if (value != undefined && value != '' && value != null && value.length !== 0) {
              conditionObj[key] = this.searchModelData[key]
            }
          }
        }
      }
      // 添加排序条件
      if (_this.sortInfo) {
        conditionObj.sort = _this.sortInfo
      }
      // 添加自定义条件
      Object.assign(conditionObj, this.getActionWhere)
      return conditionObj;
    },
    /**
       * 重置查询条件
       */
    reset () {
      // this.searchModelDataBase = clone(this.searchModelBase)
      this.searchModelData = clone(this.searchModel)
    },
    /**
       * 分页改变事件
       */
    handleSizeChange (val) {
      this.pagingInfo.pageSize = val
      this.fetchData()
    },
    /**
       * 查找, 点击事件
       */
    search () {
      this.fetchData(1)
    }
  },
  mounted () {
    let _this = this
    if (this.autoFetch) {
      _this.fetchData()
    } else {
      _this.loading = false
    }
    this.searchModelData = clone(this.searchModel)
    this.searchModelDataBase = clone(this.searchModelBase)
    Object.assign(this.pagingInfo, this.pagingConfig)
  }
}
</script>

<style type="text/scss" scoped>
  /* --------------------------------------------------------------------------
     组合表格补充样式
     -------------------------------------------------------------------------- */
  /* 过滤 */
  .m-table-comb .table-filter { padding-bottom: 20px; }
  .m-table-comb .table-filter .controls { float: right; }
  .m-table-comb .table-filter .controls > a,
  .m-table-comb .table-filter .controls > button { margin-left: 5px; }
  .m-table-comb .table-paging { padding: 30px; text-align: center; }
  /* 高级搜索 */
  .m-table-comb .table-filter-advanced { margin-bottom: 20px; padding: 16px 10px; border: 1px solid #cdd7ef; border-radius: 5px; background-color: #edf7ff; }
  .m-table-comb .table-filter-advanced .form-controls { text-align: right; }
  /* 选中项目 */
  .m-table-comb .checked-items { position: relative; margin-top: 10px; padding: 8px 50px 0 8px; border: 1px solid #cdd7ef; border-radius: 5px; background-color: #edf7ff; }
  .m-table-comb .checked-items span { position: relative; display: inline-block; margin: 0 8px 8px 0; padding: 0 28px 0 6px; height: 22px; border-radius: 3px; background-color: #4db3ff; color: #fff; font-size: 13px; line-height: 22px; }
  .m-table-comb .checked-items span i { position: absolute; top: 0; right: 0; display: inline-block; width: 22px; height: 22px; border-left: solid 1px #3da3df; border-radius: 0 3px 3px 0; background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQAgMAAABinRfyAAAADFBMVEUAAAD///////////84wDuoAAAAA3RSTlMAoDByqEibAAAAKUlEQVQI12NABw1MDAxMF9gbGBhKZIFc2RIgwX4BTEC4IAmIErBiNAAAMaMHqaYF4lAAAAAASUVORK5CYII=) 50% 50%; text-align: center; font-style: normal; cursor: pointer; -webkit-transition: all .3s; transition: all .3s; }
  .m-table-comb .checked-items span i:hover { background-color: #3da3ef; }
  .m-table-comb .checked-items .clear-all { position: absolute; top: 8px; right: 10px; color: #20a0ff; text-decoration: none; font-size: 14px; line-height: 22px; }

</style>
