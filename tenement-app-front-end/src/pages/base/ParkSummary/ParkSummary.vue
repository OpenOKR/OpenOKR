<template>
  <div>
    <d2-container>
      <template slot="header" v-if="isPage">园区信息概况</template>
      <!---->
      <el-container class="container" >
        <el-aside width="350px" class="aside"  >
          <div class="h2">
            <span class="fl">{{unionName}}</span>
            <el-button class="fr" type="primary" size="small" @click="isOpenAddDialog=true">
              <i class="iconfont icon-add"></i> 添加子园区
            </el-button>
          </div>
          <div style=" margin: 10px 10px 0;">
            <el-input size="small" placeholder="搜索子园区" @input="loadSiteSummaryByUnion" suffix-icon="el-icon-search" v-model="keyWord"></el-input>
          </div>
          <div class="summaryList">
            <div v-for="(item,index) in summaryList" :key="item.id" class="list" :class="{active:currParkIndex==index}" @click="showDetail(item,index)">
              <span class="fl" :title="item.name">{{item.name}}</span>
              <span class="fr">
                <i class="el-icon-sort-up" style="display:none"></i>
                <i class="el-icon-sort-down" style="display:none"></i>
                <i class="el-icon-circle-close-outline" title="删除" @click.stop="deleteSummary(item,index)"></i>
              </span>
            </div>
          </div>
        </el-aside>
        <el-container>
          <el-main class="main" v-if="currParkDetail && currParkDetail.id">
            <div class="content" v-show="modeType=='preview'">
              <div class="h1">
                <span class="fl">{{summaryList.length>0 ? summaryList[currParkIndex].name:''}}</span>
                <el-button class="fr" type="primary" size="small" @click="modeType='edit'">编辑</el-button>
              </div>
              <el-form label-width="200px" label-position="right" style="width: 600px;" >
                <el-form-item label="园区名称：">
                  <span>{{currParkDetail.name}}</span>
                </el-form-item>
                <el-form-item label="园区地址：">
                  <span><i class="el-icon-location-outline"></i>{{(currParkDetail.address||'') +' '+ (currParkDetail.detailAddr||'')}}</span>
                </el-form-item>
                <el-form-item label="园区类型：">
                  <span>{{currParkDetail.parkTypeName}}</span>
                </el-form-item>
                <el-form-item label="建成时间：">
                  <span>{{(currParkDetail.year?(currParkDetail.year +'年'):'')+(currParkDetail.month?(currParkDetail.month+'月'):'')+(currParkDetail.day?(currParkDetail.day+'日'):'')}}</span>
                </el-form-item>
                <el-form-item label="园区占地面积：">
                  <span>{{currParkDetail.groundArea|NumFormat}} 平方米</span>
                </el-form-item>
                <el-form-item label="园区总建筑面积：">
                  <span>{{currParkDetail.buildingArea|NumFormat}}平方米</span>
                </el-form-item>
                <el-form-item label="园区各建筑面积：">
                  <el-table :data="currParkDetail.parkOfficeVOList" border size="small" style="width:500px">
                    <el-table-column prop="officeName" label="地点名称" ></el-table-column>
                    <el-table-column prop="area" label="面积（平方米）">
                      <template slot-scope="scope">
                        <span>{{ scope.row.area|NumFormat }}</span>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-form-item>
                <el-form-item label="园区图片：">
                  <img class="avatar" v-if="currParkDetail.fullParkImagePath" :src="currParkDetail.fullParkImagePath" />
                </el-form-item>
              </el-form>
            </div>
            <div class="content" v-show="modeType=='edit'">
              <div class="h1">
                <span class="fl">{{summaryList[currParkIndex].name}}</span>
                <el-button class="fr" type="info" size="small" @click="cancel" >取消</el-button>
                <el-button class="fr mr20" type="primary" size="small" :disabled="editBusy" :loading="editBusy" @click="editParkSummary"  >保存</el-button>
              </div>
              <el-form ref="currParkDetailForm" :model="currParkDetail" :rules="currParkDetailRules" label-width="200px" label-position="right" >
                <el-form-item label="园区名称：" prop="name">
                  <el-input v-model="currParkDetail.name" maxlength="50"  style="width: 400px"></el-input>
                </el-form-item>
                <el-form-item label="园区地址：" prop="address">
                  <el-cascader :options="addressOptions" v-model="cityCodeArr" style="width: 400px; margin-bottom: 10px"></el-cascader>
                  <div><el-input v-model="currParkDetail.detailAddr" placeholder="请输入详细" style="width: 400px"></el-input></div>
                </el-form-item>
                <el-form-item label="园区类型：">
                  <el-select v-model="currParkDetail.parkTypeId" placeholder="请选择" style="width: 400px;" @visible-change="getParkTypeList">
                    <el-option v-for="item in parkTypeList" :key="item.id" :label="item.name" :value="item.id">{{item.name}}</el-option>
                  </el-select>
                  <el-button @click="openParkType" style="margin-left: 30px;" type="text">管理园区类型 <i class="el-icon-arrow-right"></i></el-button>
                </el-form-item>
                <el-form-item label="建成时间：">
                  <span></span>
                  <el-date-picker value-format="yyyy" v-model="currParkDetail.year" format="yyyy年" type="year" placeholder="建成年份" style="width: 126px;" class="mr10">
                  </el-date-picker>
                  <el-select v-model="currParkDetail.month" :clearable="true" placeholder="建成月份"  style="width: 127px;" class="mr10">
                    <el-option v-for="item in monthArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select>
                  <el-select v-model="currParkDetail.day" :clearable="true" placeholder="建成日期" no-data-text="请先选择年月"  style="width: 127px;" class="mr10">
                    <el-option v-for="item in dayArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="园区占地面积：" prop="groundArea">
                  <el-input v-model="currParkDetail.groundArea"   maxlength="11" style="width: 400px"></el-input> 平方米
                </el-form-item>
                <el-form-item label="园区总建筑面积：" prop="buildingArea">
                  <el-input v-model="currParkDetail.buildingArea"   maxlength="11" style="width: 400px"></el-input> 平方米
                </el-form-item>
                <el-form-item label="园区各建筑面积：">
                  <el-table :data="currParkDetail.parkOfficeVOList" border size="small" style="width:800px; max-width: 100%">
                    <el-table-column prop="officeName" label="地点名称" >
                      <template slot-scope="scope">
                          <el-input v-model="scope.row.officeName" maxlength="50"  ></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column prop="area" label="面积（平方米）">
                      <template slot-scope="scope">
                        <el-input  v-model ="scope.row.area"  maxlength="11" ></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column prop="area" label="操作">
                      <template slot-scope="scope">
                        <el-button type="text" size="small" @click="removeOffice(scope.$index)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                  <div class="mt10"><el-button type="text" @click="addParkOffice">新增</el-button></div>
                </el-form-item>
                <el-form-item label="园区图片：">
                  <el-upload
                    class="avatar-uploader"
                    :action="$api.common.uploadUrl"
                    :show-file-list="false"
                    :data="{targetDir:'parksummary'}"
                    :headers="{token:token}"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload">
                    <img v-if="currParkDetail.fullParkImagePath" :src="currParkDetail.fullParkImagePath" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                  <span class="ml10">建议图片尺寸：170*350</span>
                </el-form-item>
              </el-form>
            </div>
          </el-main>
        </el-container>
      </el-container>

      <el-dialog title="添加子园区" :visible.sync="isOpenAddDialog" width="600px">
        <br>
        <el-form label-width="100px" :model="currPark" >
          <el-form-item label="子园区名称">
            <el-input v-model="currPark.name" maxlength="20" placeholder="请输入名称"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" :loading="addBusy" :disabled="currPark.name==''" @click="addParkSummary">添 加</el-button>
          <el-button @click="isOpenAddDialog = false">取 消</el-button>
        </div>
      </el-dialog>
    </d2-container>
    <park-type :visible.sync="showParkTypeList" :isPage="false"></park-type>
  </div>
</template>

<script>
  import { find,cloneDeep } from "lodash";
  import listMixin from "@/mixins/list.mixin";
  import { addressOptions, originAddress } from '@/libs/address';
  import ParkType from "../ParkType";
  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "ParkSummary",
    mixins: [
      listMixin
    ],
    components: {ParkType},
    data() {
        //空或者数字
        const numberOrNull = (rule, value, callback) => {
            if(value!='' && value!= null && !/\d+(\.\d{0,4})?$/.test(value)){
                callback(new Error('请输入有效数字'));
            }else{
                callback();
            }
        };
      return {
        isPage: true,
        unionName:'',
        keyWord:'',
        summaryList:[],
        isOpenAddDialog:false,
        currPark:{
          name:''
        },
        modeType:'preview',
        currParkIndex:null,
        editBusy:false,
        currParkDetail:{
          id:null
        },
        currParkDetailRules:{
          name: [
            { required: true, message: '园区名称必填', trigger: 'blur' },
            { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
          ],
          groundArea:[
            { validator:numberOrNull, trigger: 'blur'}
          ],
          buildingArea:[
            { validator:numberOrNull,trigger: 'blur'}
          ]

        },
        parkTypeList:[],
        addBusy:false,
        addressOptions: addressOptions,
        showParkTypeList:false,
        monthArr:(()=>{
            let _monthArr = [];
            for(let i=1;i<13;i++){
                _monthArr.push({
                    label:i+'月',
                    value:''+i
                })
            }
            return _monthArr;
        })()

      };
    },
    computed: {
      unionId() {
        return this.$route.params.unionId
      },
      address() {
        let provice, city, district = "";
        if (this.cityCodeArr) {
          if (this.cityCodeArr[0]) {
            provice = find(originAddress, {codevalue: this.cityCodeArr[0]}).codename
          }
          if (this.cityCodeArr[1]) {
            city = find(originAddress, {codevalue: this.cityCodeArr[1]}).codename
          }
          if (this.cityCodeArr[2]) {
            district = find(originAddress, {codevalue: this.cityCodeArr[2]}).codename
          }
        }
        return [provice, city, district]
      },
      cityCodeArr: {
        set(value) {
          // 返回数组的最后一个数即可
          this.currParkDetail.cityCode = value[value.length - 1];
          // this.formData.address = "对应的地区中文名字"
          this.currParkDetail.cityStr = this.currParkDetail.cityCode + "#" + this.address.join('/');
          return value;
        },
        get() {
          let res = [];
          let provice, city, district = "";
          let length = this.currParkDetail.cityCode ? this.currParkDetail.cityCode.length : 0;
          if (length) {
            // 把 086240203 这样的代码 拆成 ['08624', '0862402', '086240203']
            for (let i = 1; i <= (length - 3) / 2; i++) {
              res.push(this.currParkDetail.cityCode.substr(0, 3 + i * 2));
            }
          }
          return res;
        }
      },
      dayArr(){
          //已经选择
          if(this.currParkDetail.id){
              let _MonthMaxDayArr =  [31,28,31,30,31,30,31,31,30,31,30,31];
              let _year = this.currParkDetail.year ||0;
              let _month = this.currParkDetail.month;
              if((_year%4==0 && _year%100!=0) ||_year%400==0){
                  _MonthMaxDayArr[1] = 29;
              }
              let _dayArr = [];
              for(let i=1;i<=_MonthMaxDayArr[_month-1];i++){
                  _dayArr.push({
                      label:i+'日',
                      value:''+i
                  })
              }
              return _dayArr;
          }else{
              return [];
          }


      }
    },
    methods: {
      //获取左侧子园区列表
      loadSiteSummaryByUnion(type){
        const _this = this;
        this.$api.base.parkSummary.loadSiteSummaryByUnion(this.unionId,this.keyWord).then(res => {
          _this.summaryList = res.data;
          if(_this.summaryList.length >0){
            if(type=='add'){
              _this.currParkIndex = _this.summaryList.length-1
            }
            if(_this.currParkIndex===null || _this.summaryList.length <= _this.currParkIndex){
              //第一次加载时显示第一条
              _this.showDetail(_this.summaryList[0],0);
            }else{
                _this.showDetail(_this.summaryList[_this.currParkIndex],_this.currParkIndex);
            }
          }else{
            //删除最后一条数据时
            _this.currParkIndex = 0;
            _this.currParkDetail = {id:null};
          }

        });
      },
      //获取园区类型列表
      getParkTypeList(bol){
        const _this = this;
        if(bol){
          this.$api.base.parkType.list({unionId:this.unionId},{currentPage:1,pageSize:1000}).then(res => {
            _this.parkTypeList = res.data.data;
          });
        }
      },
      //添加子园区
      addParkSummary(){
        const _this = this;
        this.addBusy = true;
        this.$api.base.parkSummary.saveParkSummary({name:this.currPark.name,unionId:this.unionId}).then(res => {
          _this.addBusy = false;
          if(res.code==0){
            _this.$message({
              message: '添加成功',
              type: 'success'
            });
            _this.isOpenAddDialog = false;
            _this.currPark={name:''};
            _this.loadSiteSummaryByUnion('add');
          }else{
            _this.$message.error(res.message);
          }

        });
      },
      //编辑子园区
      editParkSummary(){
        const _this = this;
        this.$refs['currParkDetailForm'].validate((valid) => {
          if (valid) {
            let pass = true;
              _this.currParkDetail.parkOfficeVOList.map((item) =>{
              // if(!item.officeName || !item.area){
              //   _this.$message.error('园区建筑地址和面积必填');
              //   pass = false;
              //   return ;
              // }
              if(item.area!='' && item.area!=null && !/\d+(\.\d{0,4})?$/.test(item.area)){
                  _this.$message.error('面积填写有误！');
                  pass = false;
                  return ;
              }
            });
            if(!pass){
              return false
            }
            this.editBusy = true;
            _this.$api.base.parkSummary.saveParkSummary({...this.currParkDetail,cityCode:this.currParkDetail.cityStr}).then(res => {
              _this.editBusy = false;
              _this.modeType = 'preview';
              if(res.code==0){
                _this.$message({
                  message: '保存成功！',
                  type: 'success'
                });
                _this.loadSiteSummaryByUnion();
              }else{
                _this.$message.error(res.message);
              }

            });
          } else {

            return false;
          }
        });

      },
      //删除子园区
      deleteSummary(item,index){
        const _this = this;
        this.$confirm(`是否要删除"${item.name}"园区？`).then(_ => {
          this.$api.base.parkSummary.deleteParkSummary(item.id).then(res => {
            if(res.code==0){
              if(index<_this.currParkIndex){
                _this.currParkIndex--;
              }else if(index==_this.currParkIndex){
                _this.currParkIndex = 0;
              }
              _this.loadSiteSummaryByUnion();
              _this.$message({
                message: '删除成功!',
                type: 'success'
              });
            }else{
              _this.$message.error(res.message);
            }
          });
        }).catch(_ => {});
      },
      //获取子园区详情
      showDetail(detail,index){
        const _this = this;
        //如果是编辑模式，要先提示是否放弃当前编辑的
        if(this.modeType=='edit'){
          this.$confirm(`当前园区可能有修改，是否舍弃修改？`).then(_ => {
            _this.modeType = 'preview';
            _this.showDetail(detail,index);
          }).catch(_ => {});
          return false;
        }

        this.$api.base.parkSummary.getParkSummaryDetail(detail.id).then(res => {
          if(res.code==0){
            _this.currParkIndex = index;
            _this.currParkDetail = res.data;
            //初始化地区选择控件
            if(res.data.cityCode){
              var cityCodeArr=[];
              for (let i = 1; i <= (res.data.cityCode.length - 3) / 2; i++) {
                cityCodeArr.push(res.data.cityCode.substr(0, 3 + i * 2));
              }
              _this.cityCodeArr = cityCodeArr;
            }

          }else{
            _this.$message.error(res.message);
          }

        });
      },
      //取消编辑
      cancel(){
        const _this = this;
        this.$confirm(`当前园区可能有修改，是否舍弃修改？`).then(_ => {
          _this.modeType = 'preview';
          _this.showDetail(detail,index);
        }).catch(_ => {});
      },
      handleAvatarSuccess(res, file) {
        this.currParkDetail.parkImage =res.data.path;
        this.currParkDetail.fullParkImagePath =res.data.httppath;
      },
      beforeAvatarUpload(file) {
        const isImage = /.(gif|jpg|jpeg|png|gif|jpg|png)$/.test(file.type);
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isImage) {
          this.$message.error('只能上传图片格式的文件!');
        }
        if (!isLt2M) {
          this.$message.error('图片大小不能超过2MB!');
        }
        return isImage && isLt2M;
      },
      addParkOffice(){
        this.currParkDetail.parkOfficeVOList.push({
          officeName:'',
          area:''
        })
      },
      removeOffice(index){
        this.currParkDetail.parkOfficeVOList.splice(index,1);
      },
      openParkType(){
        this.showParkTypeList = true;
      }

    },
    mounted() {
      const _this = this;
      //获取 unionName
      this.$api.base.parkSummary.getUnionById(this.unionId).then(res => {
        _this.unionName = res.data.name;
        _this.loadSiteSummaryByUnion();
      });
      _this.getParkTypeList(true);
    }
  };
</script>

<style type="text/scss" lang="scss" scoped>
  .aside{
    background:#fff;
    border:#dedede solid 1px;
    border-radius: 2px;
  }
  .el-main{
    padding: 0;
  }
  .main{
    @extend .aside;
    margin-left:10px;
  }
  .h1{
    padding: 10px;
    height: 36px;
    font-weight: 700;
    border-bottom:#dedede solid 1px;
  }
  .h2{
    line-height: 36px;
    height: 36px;
    padding: 5px 10px;
    background: #f4f4f4;
  }
  .summaryList{
    min-height: 400px;
    padding: 10px;
    .list{
      cursor: pointer;
      padding:0 10px;
      line-height:40px;
      height: 40px;
      &:hover{
        background: #f7f7f7;
        i{
          display: inline-block;
        }
      };
      &.active{
        background: #b3d8ff;
        color: #409eff;
      };
      i{
        display: none;
        margin: 0 1px;
        &:hover{
          color:#2f74ff;
        }
      }
      .fl{
        width: 280px;
        height: 40px;
        display:inline-block;
        white-space: nowrap;
        word-break: break-all;
        text-overflow:ellipsis;
        overflow: hidden;
      }
    }
  }
  .el-form{ padding: 20px;}
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
    background: #f4f4f4;
    border:#dedede dashed 1px;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
    border: 1px solid #d9d9d9;
  }
</style>
