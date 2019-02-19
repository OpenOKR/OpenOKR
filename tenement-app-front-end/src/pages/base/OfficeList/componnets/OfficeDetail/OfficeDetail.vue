<template>
  <transition name="edit">
    <div>
      <d2-container v-show="isVisible" v-loading="loading" class="edit-panel">
        <template slot="header">
          <i title="后退" class="el-icon-back" @click="back"></i>
          地点详情
        </template>

        <div class="office-detail">
          <div class="thumb" v-if="officeVO.httpPic">
            <img :src="officeVO.httpPic" alt=""/>
          </div>
          <div class="content">
            <div class="title">
              {{officeVO.name}}
              <small>{{officeVO.siteName}}</small>
            </div>
            <div class="meta">地址：{{officeVO.address}} {{officeVO.addr}}</div>
            <div class="meta"><span>产业属性：{{officeVO.industryName}}</span><!--<span>区域：{{}}</span>--></div>
            <div class="meta"><span>总面积：{{officeVO.proportion}}</span><span>单元数量：{{officeVO.stationCount}}</span><span>车位数量：{{officeVO.parkingCount}}</span></div>
          </div>
          <div class="controls">
            <el-button type="primary" @click="openEditDialog(officeVO)"><i class="iconfont icon-edit"></i> 编辑</el-button>
            <el-button type="danger" @click="remove(officeVO.id)">删除</el-button>
          </div>
        </div>

        <el-tabs v-model="activeTab" type="border-card" class="custom-tab">
          <el-tab-pane label="区域" name="first">
            <region-list ref="regionList" :uid="unionId" :officeId="officeId" :siteId="siteId" @update="handleChildrenPageUpdate" style="height: 700px;"></region-list>
          </el-tab-pane>
          <el-tab-pane label="单元" name="second">
            <station-list ref="stationList" :uid="unionId" :officeId="officeId" :siteId="siteId" @update="handleChildrenPageUpdate" style="height: 700px;"></station-list>
          </el-tab-pane>
          <el-tab-pane label="车位" name="third">
            <parking-list ref="parkingList" :uid="unionId" :officeId="officeId" :siteId="siteId" @update="handleChildrenPageUpdate" style="height: 700px;"></parking-list>
          </el-tab-pane>
        </el-tabs>

      </d2-container>
      <office-edit ref="editDialog" @ok="handleUpdateSuccess" :siteOptions="siteOptions"></office-edit>
    </div>
  </transition>
</template>

<script>
  import formMixin from "@/mixins/form.mixin";
  import OfficeEdit from "../../componnets/OfficeEdit";
  import RegionList from "../../../RegionList";
  import StationList from "../../../StationList";
  import ParkingList from "../../../ParkingList";
  import { cloneDeep, find } from 'lodash';

  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "OfficeDetail",
    components: {OfficeEdit, RegionList, StationList, ParkingList},
    mixins: [
      formMixin
    ],
    props:['siteOptions'],
    data() {
      return {
        isVisible: false,
        activeTab: 'first',
        officeVO: {},
      };
    },
    computed: {
      unionId() {
        return this.$route.params.unionId
      },
      officeId() {
        return this.officeVO.id;
      },
      siteId() {
        return this.officeVO.siteId;
      },
    },
    methods: {
      open(vo) {
        this.officeVO = vo
        this.$nextTick(() => {
          this.isVisible = true;
        })
      },
      // 打开新增/编辑页面
      openEditDialog(vo) {
        this.$refs.editDialog.open(vo, {siteOptions: this.siteOptions});
      },
      handleUpdateSuccess(vo) {
        this.officeVO = vo;
        this.$emit("ok");
        this.handleChildrenPageUpdate();
      },
      handleChildrenPageUpdate(){
        console.log('handleChildrenPageUpdate');
        this.$refs.regionList.refresh();
        this.$refs.stationList.refresh();
        this.$refs.parkingList.refresh();
      },
      back(){
        this.isVisible = false
        this.$emit("ok");
      },
      remove(id) {
        let _this = this

        this.$msgbox({
          title: '提示',
          message: `确定要删除该数据？`,
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(action => {
          if (action === 'confirm') {
            _this.loading = true
            _this.$api.merch.office.delete(id).then(res => {
              if (res.code === 0) {
                _this.$message.success(`删除数据成功`)
                _this.$emit("ok");
                _this.back()
              } else {
                _this.$message.error(res.message)
              }

              _this.loading = false
            }).catch(() => {
              _this.$message.error(`删除数据失败`)
            })
          }
        })
      }

    }
  };
</script>

<style type="text/scss" lang="scss">
  @import '~@/assets/style/public.scss';
  .office-detail {
    position: relative;
    padding-bottom: 20px;
    .thumb {
      float: left;
      display: block;
      margin-right: 20px;
      img {display: block; width: 144px; height: 144px; }
    }
    .content {
      .title {
        font-size: 22px;
        font-weight: bold;
        line-height: 50px;
        color: $color-text-main;
        vertical-align: middle;
        small {
          font-size: 14px;
          font-weight: normal;
          color: $color-text-sub;
          padding-left: 20px;
          margin-left: 20px;
          border-left: solid 1px $color-text-placehoder;
        }
      }
      .meta {
        font-size: 14px;
        color: $color-text-normal;
        line-height: 30px;
        span {
          padding: 0 20px; border-left: solid 1px $color-text-placehoder;
          &:first-child {padding-left: 0;border-left: none;}
        }
      }
    }
    .controls {
      position: absolute;
      top: 0;
      right: 0;
    }
  }
  .custom-tab .el-tabs__header {margin: 0;}
</style>
