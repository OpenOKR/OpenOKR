<template>
  <transition name="edit">
    <d2-container v-show="isVisible" v-loading="loading" class="edit-panel">
      <template slot="header">
        <i title="后退" class="el-icon-back" @click="isVisible = false"></i>
        入驻详情
      </template>

      <el-table
        ref="table"
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          prop="businessModuleStr"
          label="业务模块">
        </el-table-column>
        <el-table-column
          prop="customerTypeStr"
          label="客户类型">
        </el-table-column>
        <el-table-column
          prop="siteName"
          label="园区">
        </el-table-column>
        <el-table-column
          prop="officeName"
          label="地点">
        </el-table-column>
        <el-table-column
          prop="address"
          label="详细地址">
        </el-table-column>
        <el-table-column
          prop="startTimeStr"
          label="入驻时间">
        </el-table-column>
        <el-table-column
          prop="status"
          label="生效状态">
          <template slot-scope="props">
            <el-tag type="success" v-if="props.row.status === '1'">生效</el-tag>
            <el-tag type="info" v-if="props.row.status === '2'">过期</el-tag>
          </template>
        </el-table-column>
      </el-table>

    </d2-container>
  </transition>
</template>

<script>
  import formMixin from '@/mixins/form.mixin';

  export default {
    name: "CorpAdmissionList",
    components: {},
    mixins: [
      formMixin
    ],
    data() {
      return {
        isVisible: false,
        tableData: [],
      };
    },
    methods: {
      open(id) {
        let _this = this;

        this.$api.corp.admissionList(id).then(res => {
          _this.tableData = res.data;
          _this.isVisible = true;
          _this.loading = false;
        });

      },
    }
  };
</script>

