<template>
  <div>
    <d2-container>
      <template slot="header">账单添加</template>
      <el-row>
        <el-col :span="12">
          <div class="grid-content-top">
            <p class="key">待审核记录</p>
            <p class="value">{{totalData.auditStatusWaitNum||0}}个</p>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="grid-content-top">
            <p class="key">待审核金额</p>
            <p class="value">{{totalData.auditStatusWaitAmt||0}}元</p>
          </div>
        </el-col>
      </el-row>
      <!---->
      <table-comb
        name="账单列表"
        ref="tableMain"
        :search-model-base="tableMainSearchModelBase"
        :search-model="tableMainSearchModel"
        :get-action="$api.energy.billManage.billAddlist"
        :get-action-where="getActionWhere"
        :autoFetch="false"
        :remove-action="$api.energy.billManage.delete"
        @on-remove-success="getTotal"
        checkedDispalyName="billNo"
      >
        <!--基础查询-->
        <template slot="baseSearchForm" slot-scope="props">
          <el-select
            v-model="props.form.siteId"
            @change="changeSite"
            filterable
            placeholder="请选择站点"
            style="width: 300px;">
            <el-option
              v-for="item in siteOptions"
              :key="item.siteId"
              :label="item.name"
              :value="item.siteId">
            </el-option>
          </el-select>
          <el-input
            placeholder="流水号/客户名称/联系人/电话"
            prefix-icon="el-icon-search"
            clearable
            v-model="props.form.searchKey"
            style="width: 240px; margin-left: 10px;"
          >
          </el-input>
          <el-button class="fr ml10" @click="auditBill()">提交审核</el-button>
          <el-button class="fr ml10" @click="openImportDialog()">导入</el-button>
          <el-button class="fr" @click="openSaveDialog()">添加</el-button>
        </template>
        <!--高级查询-->
        <template slot="advancedSearchForm" slot-scope="props">
          <el-row>
            <el-col :span="8">
              <el-form-item label="地点：">
                <el-select
                  v-model="props.form.officeId"
                  @change="changeOffice"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    v-for="item in officeOptions"
                    :key="item.id"
                    :label="item.description"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="区域：">
                <el-cascader
                  placeholder="请选择"
                  :props="regionProps"
                  :options="regionOptions"
                  v-model="props.form.regionId"
                  :debounce="400"
                  change-on-select
                  @change="changeRegion">
                </el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="单元：">
                <el-select
                  v-model="props.form.stationId"
                  @change="changeStation"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    v-for="item in stationOptions"
                    :key="item.id"
                    :label="item.staDescription"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审核状态：">
                <el-select
                        v-model="props.form.auditStatus"
                        @change="changeStation"
                        placeholder="请选择"
                        filterable
                >
                  <el-option
                          v-for="item in auditStatusOptions"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="年月：">
                <el-date-picker
                    v-model="props.form.billMonth"
                    type="month"
                    format="yyyy 年 MM 月"
                    value-format="yyyyMM"
                    placeholder="选择月">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <!--表格-->
        <template slot="tableColumns">
          <el-table-column type="selection" :selectable="function(row, index){ return row.auditStatus=='99'}"  fixed="left" width="35"></el-table-column>
          <el-table-column prop="corpName"  fixed="left" label="操作" width="120px">
            <template slot-scope="props">
              <el-button type="text" size="mini" @click="detail(props.row)">查看</el-button>
              <template v-if="props.row.auditStatus=='02' || props.row.auditStatus=='99'">
                <el-button type="text" size="mini" @click="openSaveDialog(props.row)">编辑</el-button>
                <el-button type="text" size="mini" @click="deleteItem(props.row)">删除</el-button>
              </template>
            </template>
          </el-table-column>
          <el-table-column width="170px" prop="billNo" label="业务流水号"></el-table-column>
          <el-table-column prop="auditStatusDesc" label="审核状态"></el-table-column>
          <el-table-column width="200px" prop="officeName" label="地点"></el-table-column>
          <el-table-column prop="levelDesc" width="150px" label="区域">
            <template slot-scope="props">
              {{props.row.levelDesc||'-'}} | {{props.row.level2Desc||'-'}}
            </template>
          </el-table-column>
          <el-table-column prop="stationName" width="100px" label="单元"></el-table-column>
          <el-table-column width="120px" prop="billMonthStr" label="月份"></el-table-column>
          <el-table-column prop="feeTypeDesc" label="费项"></el-table-column>
          <el-table-column prop="receivableAmt" label="应收金额">
            <template slot-scope="props">
              {{props.row.receivableAmt| NumFormat}}
            </template>
          </el-table-column>
          <el-table-column width="120px" prop="receivableDateStr" label="应收日期">
            <template slot-scope="props">
              {{props.row.receivableDate| dateFormat('yyyy-MM-dd')}}
            </template>
          </el-table-column>
          <el-table-column prop="corpName"  width="200px" label="客户名称"></el-table-column>
          <el-table-column prop="telephone"  width="150px" label="联系人">
            <template slot-scope="props">
              {{(props.row.corpContacts||'-')+'/'+(props.row.telephone||'-')}}
            </template>
          </el-table-column>
        </template>
      </table-comb>
      <template slot="footer"></template>
    </d2-container>

    <el-dialog :title="editDialogCustomer.billNo?'编辑':'添加'" :visible.sync="showSaveDialog" width="750px">
      <el-form label-width="100px" :model="editDialogCustomer" :rules="rules" ref="saveForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="地点：" prop="officeId">
              <el-select
                v-model="editDialogCustomer.officeId"
                @change="changeOfficeForSave"
                placeholder="请选择"
                filterable
                style="width: 250px;"
              >
                <el-option
                  v-for="item in editDialogCustomer.officeOptions"
                  :key="item.id"
                  :label="item.description"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业客户：" prop="corpName">
              <el-input style="width: 250px;" :disabled="corpDisabled" v-model="editDialogCustomer.corpName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="区域：" prop="regionId">
                <el-cascader
                    placeholder="请选择"
                    :props="regionProps"
                    :options="editDialogCustomer.regionOptions"
                    v-model="editDialogCustomer.regionId"
                    :debounce="400"
                    style="width: 250px;"
                    change-on-select
                    @change="changeRegionForSave">
                </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业联系人：" >
              <el-input style="width: 250px;" :disabled="corpDisabled" v-model="editDialogCustomer.corpContacts"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="12">
            <el-form-item label="单元：" prop="stationId">
              <el-select
                v-model="editDialogCustomer.stationId"
                placeholder="请选择"
                filterable
                style="width: 250px;"
                @change="changeStationForSave"
              >
                <el-option
                  v-for="item in editDialogCustomer.stationOptions"
                  :key="item.id"
                  :label="item.staDescription"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人电话：" >
              <el-input style="width: 250px;" :disabled="corpDisabled" v-model="editDialogCustomer.telephone" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="line"></div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="模板：" prop="billTemplateType">
              <el-select
                v-model="editDialogCustomer.billTemplateType"
                placeholder="请选择模板"
                filterable
                style="width: 250px;"
              >
                <el-option
                  v-for="item in templateTypeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="月份：" prop="billMonth">
              <el-date-picker
                v-model="editDialogCustomer.billMonth"
                type="month"
                format="yyyy 年 MM 月"
                value-format="yyyyMM"
                style="width: 250px;"
                placeholder="选择月">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="选择费项：" prop="feeTypeId">
              <el-select
                v-model="editDialogCustomer.feeTypeId"
                placeholder="请选择费项"
                filterable
                style="width: 250px;"
              >
                <el-option
                  v-for="item in feeTypesOptions"
                  :key="item.id"
                  :label="item.description"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应收日期：" prop="receivableDate">
              <el-date-picker
                v-model="editDialogCustomer.receivableDate"
                type="date"
                format="yyyy-MM-dd"
                style="width: 250px;"
                placeholder="">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应收金额：" prop="receivableAmt">
              <el-input style="width: 250px;"  v-model.number="editDialogCustomer.receivableAmt" placeholder="请输入应收金额"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="计费期间：">
              <el-date-picker
                v-model="editDialogCustomer.billStartEndDate"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                style="width: 250px;"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12"  v-if="editDialogCustomer.billTemplateType=='01'">
            <el-form-item label="总数量：" prop="totalUseQuantity">
              <el-input style="width: 250px;"  v-model.number="editDialogCustomer.totalUseQuantity" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <template v-if="editDialogCustomer.billTemplateType=='00'">
          <el-row>
            <el-col :span="12">
              <el-form-item label="数量：" prop="totalUseQuantity">
                <el-input v-model="editDialogCustomer.totalUseQuantity" placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="上月抄数：">
                <el-input v-model="editDialogCustomer.beginMeterReading" placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="单价：" >
                <el-input v-model="editDialogCustomer.price" placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="本月抄数：" >
                <el-input v-model="editDialogCustomer.endMeterReading" placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="倍率：" >
                <el-input v-model="editDialogCustomer.rate" placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <template v-if="editDialogCustomer.billTemplateType=='01'">
          <el-tabs v-model="activeName" tab-position="left">
            <el-tab-pane label="尖阶段" name="first">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="数量：">
                    <el-input v-model="editDialogCustomer.tipUseQuantity" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="上月抄数：">
                    <el-input v-model="editDialogCustomer.tipBeginMeterReading" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单价：" >
                    <el-input v-model="editDialogCustomer.tipPrice" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="本月抄数：" >
                    <el-input v-model="editDialogCustomer.tipEndMeterReading" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="倍率：" >
                    <el-input v-model="editDialogCustomer.tipRate" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane label="峰阶段" name="second">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="数量：">
                    <el-input v-model="editDialogCustomer.peakUseQuantity" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="上月抄数：">
                    <el-input v-model="editDialogCustomer.peakBeginMeterReading" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单价：" >
                    <el-input v-model="editDialogCustomer.peakPrice" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="本月抄数：" >
                    <el-input v-model="editDialogCustomer.peakEndMeterReading" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="倍率：" >
                    <el-input v-model="editDialogCustomer.peakRate" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane label="平阶段" name="third">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="数量：">
                    <el-input v-model="editDialogCustomer.plainUseQuantity" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="上月抄数：">
                    <el-input v-model="editDialogCustomer.plainBeginMeterReading" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单价：" >
                    <el-input v-model="editDialogCustomer.plainPrice" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="本月抄数：" >
                    <el-input v-model="editDialogCustomer.plainEndMeterReading" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="倍率：" >
                    <el-input v-model="editDialogCustomer.plainRate" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane label="谷阶段" name="fourth">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="数量：">
                    <el-input v-model="editDialogCustomer.valleyUseQuantity" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="上月抄数：">
                    <el-input v-model="editDialogCustomer.valleyBeginMeterReading" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单价：" >
                    <el-input v-model="editDialogCustomer.valleyPrice" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="本月抄数：" >
                    <el-input v-model="editDialogCustomer.valleyEndMeterReading" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="倍率：" >
                    <el-input v-model="editDialogCustomer.valleyRate" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>
        </template>
        <div class="line"></div>
        <el-form-item label="费用说明：">
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入"
            v-model="editDialogCustomer.remark">
          </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">保 存</el-button>
        <el-button type="primary" @click="save(false)">保存并提交</el-button>
        <el-button @click="showSaveDialog = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="导入数据" :visible.sync="showImportDialog">
      <el-form label-width="100px" :model="importVo" :rules="importVoRules" ref="importVoForm">
        <el-form-item label="模板类型：" prop="templateType">
          <el-select
            v-model="importVo.templateType"
            placeholder="请选择模板"
          >
            <el-option
              v-for="item in templateTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <el-button class="ml10" size="mini" @click="getBillTemplate">下载模板</el-button>
        </el-form-item>
        <el-form-item label="选择文件：" prop="officeId">
          <el-upload
            :class="fileList.length==0?'':'upload-demo'"
            ref="upload"
            :action="batchImportAction"
            :on-change="changeFile"
            :before-upload="beforeAvatarUpload"
            :on-success="UploadSuccess"
            :headers="{token:token}"
            accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, .csv"
            :limit="1"
            :auto-upload="false">
            <el-button v-if="fileList.length==0"  slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
          <el-button v-if="fileList.length==1" style="margin-left: 10px;" size="small" type="success" @click="submitUpload">导入数据</el-button>
          <div class="el-upload__tip">提示：<br/>① 导入数据时，请先选择模板类型，再导入文件。点击“下载模板”按钮下载对应数据类型的模板<br/>② 月份一栏请按要求填写格式为YYYYMM，举例：2019年1月，填写为201901。</div>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="showImportDialog = false">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog title="查看" :visible.sync="showDetailDialog" class="details-info">
      <el-form label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="地点：">
              {{currentBill.officeName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业客户：">
              {{currentBill.corpName}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="区域：">
              {{currentBill.levelDesc}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人：">
              {{currentBill.corpContacts}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="12">
            <el-form-item label="单元：">
              {{currentBill.stationName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人电话：" >
              {{currentBill.telephone }}
            </el-form-item>
          </el-col>
        </el-row>
        <div class="line"></div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="模板类型：">
              {{ getLabelByValue(templateTypeOptions,currentBill.billTemplateType)||'未定义' }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="月份：">
              {{currentBill.billMonthStr }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="费项：">
             {{currentBill.feeTypeDesc}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应收日期：">
              {{currentBill.receivableDate| dateFormat('yyyy-MM-dd') }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应收金额：">
              {{currentBill.receivableAmt | NumFormat }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="计费期间：">
              {{currentBill.billStartEndDate }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总数量：" v-if="currentBill.billTemplateType=='01'">
              {{currentBill.totalUseQuantity }}
            </el-form-item>
          </el-col>
        </el-row>
        <template v-if="currentBill.billTemplateType=='00'">
          <el-row>
            <el-col :span="12">
              <el-form-item label="数量：">
                {{currentBill.totalUseQuantity }}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="上月抄数：">
                {{currentBill.beginMeterReading }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="单价：" >
                {{currentBill.price }}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="本月抄数：" >
                {{currentBill.endMeterReading }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="倍率：" >
                {{currentBill.rate }}
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <template v-if="currentBill.billTemplateType=='01'">
          <el-tabs v-model="currentBillActiveName">
            <el-tab-pane label="尖阶段" name="first">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="数量：">
                    {{currentBill.tipUseQuantity }}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="上月抄数：">
                    {{currentBill.tipBeginMeterReading }}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单价：" >
                    {{currentBill.tipPrice }}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="本月抄数：" >
                    {{currentBill.tipEndMeterReading }}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="倍率：" >
                    {{currentBill.tipRate }}
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane label="峰阶段" name="second">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="数量：">
                    {{currentBill.peakUseQuantity }}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="上月抄数：">
                    {{currentBill.peakBeginMeterReading }}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单价：" >
                    {{currentBill.peakPrice }}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="本月抄数：" >
                    {{currentBill.peakEndMeterReading }}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="倍率：" >
                    {{currentBill.peakRate }}
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane label="平阶段" name="third">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="数量：">
                    {{currentBill.plainUseQuantity }}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="上月抄数：">
                    {{currentBill.plainBeginMeterReading }}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单价：" >
                    {{currentBill.plainPrice }}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="本月抄数：" >
                    {{currentBill.plainEndMeterReading }}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="倍率：" >
                    {{currentBill.plainRate }}
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane label="谷阶段" name="fourth">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="数量：">
                    {{currentBill.valleyUseQuantity }}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="上月抄数：">
                    {{currentBill.valleyBeginMeterReading }}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="单价：" >
                    {{currentBill.valleyPrice }}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="本月抄数：" >
                    {{currentBill.valleyEndMeterReading }}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="倍率：" >
                    {{currentBill.valleyRate }}
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>
        </template>
        <div class="line"></div>
        <el-form-item label="费用说明：" prop="officeId">
          <div v-html="currentBill.remark"></div>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDetailDialog = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import listMixin from "@/mixins/list.mixin";

  //时间格式化
  Date.prototype.format = function (format) {
    var o = {
      "M+": this.getMonth() + 1, //month
      "d+": this.getDate(),    //day
      "h+": this.getHours(),   //hour
      "m+": this.getMinutes(), //minute
      "s+": this.getSeconds(), //second
      "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
      "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
      (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)if (new RegExp("(" + k + ")").test(format))
      format = format.replace(RegExp.$1,
        RegExp.$1.length == 1 ? o[k] :
          ("00" + o[k]).substr(("" + o[k]).length));
    return format;
  };


  export default {
    // 如果需要缓存页面
    // name 字段需要设置为和本页路由 name 字段一致
    name: "BillAdd",
    components: {},
    mixins: [
      listMixin
    ],
    data() {

      let checkAmt = (rule, value, callback)=>{
        let isCash=/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        if(value ===''){
          return callback(new Error('请输入金额'));
        }
        if(!isCash.test(value)){
          return callback(new Error('输入金额有误，最多保留两位小数'));
        }
        if(parseFloat(value)==0){
          return callback(new Error('至少需要缴费0.01元'));
        }
        callback();
      };
      let checkTotalUseQuantity = (rule, value, callback)=>{
        let isCash=/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        console.log(value)
        if(!value){
          //return callback(new Error('请输入金额'));
           callback();
        }else if(!isCash.test(value)){
           callback(new Error('输入有误，最多保留两位小数'));
        }else if(parseFloat(value)==0){
           callback(new Error('至少输入0.01'));
        }else{
          callback();
        }

      };

      return {
        totalData:{
          auditStatusWaitNum:0,
          auditStatusWaitAmt:0
        },
        tableMainSearchModelBase:{
          siteId:'',
          searchKey: ''
        },
        tableMainSearchModel:{
          officeId:'',
          regionId:[],
          stationId:'',
          auditStatus:'',
          billMonth:''

        },
        siteOptions:[],
        officeOptions:[],
        regionOptions:[],
        regionProps:{
          value: 'id',
          label:'name',
          children: 'childLists'
        },
        corpDisabled:true,
        templateTypeOptions:[
          {value: '00', label: '通用'},
          {value: '01', label: '多电价阶段'},
        ],
        auditStatusOptions:[
          {value: '00', label: '待审核'},
          {value: '01', label: '审核通过'},
          {value: '02', label: '审核拒绝'},
          {value: '99', label: '草稿'},
        ],
        stationOptions:[],
        feeTypesOptions:[],
        tableData: [],
        fileList:[],
        activeName:'first',
        currentBillActiveName:'first',
        showSaveDialog:false,
        showImportDialog:false,
        showDetailDialog:false,
        currentBill:{},
        editDialogCustomer:{},
        importVo:{},
        importVoRules:{
          templateType: [
            { required: true, message: '费用名称不能为空', trigger: 'blur' }
          ],
        },
        rules:{
          officeId:[
            { required: true, message: '请选择地点', trigger: 'blur' }
          ],
          corpName:[
            { required: true, message: '企业不能为空', }
          ],
          regionId:[
            { required: true, message: '请选择区域', trigger: 'blur' }
          ],
          stationId:[
            { required: true, message: '请选择单元', trigger: 'blur' }
          ],
          billTemplateType:[
            { required: true, message: '请选择模板', trigger: 'change' }
          ],
          billMonth:[
            { required: true, message: '请选择月份', trigger: 'change' }
          ],
          feeTypeId:[
            { required: true, message: '请选择费项', trigger: 'change' }
          ],
          receivableDate:[
            { required: true, message: '请选择应收日期', trigger: 'change' }
          ],
          receivableAmt:[
            {required: true, validator: checkAmt,trigger: 'blur' }
          ],
          totalUseQuantity:[
            {validator: checkTotalUseQuantity,trigger: 'blur' }
          ],

        }
      };
    },
    computed: {
      getActionWhere(){
        return {
          unionId : this.unionId
        }
      },
      unionId() {
        return this.$route.params.unionId
      },
      batchImportAction(){
        return `${process.env.VUE_APP_API_PREFIX}/energy/billManage/batchImport.json?templateType=${this.importVo.templateType=='00'?'0':'1'}`;
      },
    },
    methods: {
      //打开新增或编辑弹窗
      openSaveDialog(item){
        this.showSaveDialog = true;
        //移除校验结果并重置字段值
        this.$refs.saveForm && this.$refs.saveForm.clearValidate();
        //新增不传item 编辑要传入item
        console.log(item);
        if(item && item.id){
          this.editDialogCustomer = {
            ...item,
            regionId:[item.levelId||'',item.level2Id||''],
            officeOptions:this.officeOptions,
            regionOptions:[],
            stationOptions:[],
            billStartEndDate:[],
          };
          if(item.billStartDate){
            this.editDialogCustomer.billStartEndDate = [new Date(item.billStartDate),new Date(item.billEndDate)]
          }
          //地点，地区，单元初始化
          this.initEditDialogCustomerOptions()
        }else{
          this.editDialogCustomer = {
            officeId:'',
            regionId:[],
            stationId:'',
            id:'',
            corpName:'',
            corpContacts:'',
            billMonth:'',
            telephone:'',
            receivableAmt:'',
            receivableDate:'',
            officeOptions:this.officeOptions,
            regionOptions:[],
            stationOptions:[],
            remark:'',
            totalUseQuantity:'',
            price:'',
            beginMeterReading:'',
            endMeterReading:'',
            rate:'',
            billStartEndDate:'',

          }
        }
      },
      //初始化弹窗内容
      initEditDialogCustomerOptions(){
        let officeId = this.editDialogCustomer.officeId;
        let regionId = this.editDialogCustomer.regionId;
        this.$api.merch.station.regionOptions(officeId).then(res => {
          this.editDialogCustomer.regionOptions = res.data;
        });
        this.$api.merch.station.stationOptions(regionId,officeId).then(res => {
          this.editDialogCustomer.stationOptions = res.data;
        });
      },
      //打开导入弹窗
      openImportDialog(){
        this.showImportDialog = true;
      },
      //下载模板
      getBillTemplate(){
        let templateType = this.importVo.templateType;
        if(!templateType){
          this.$message.error('请选择模板类型')
          return;
        }
        this.$api.energy.billManage.getBillTemplate(templateType,this.$refs.tableMain.searchModelDataBase.siteId);
      },
      //导入数据
      submitUpload(){
        const isChoseType = !(this.importVo.templateType!=='00'&& this.importVo.templateType!=='01');
        if(isChoseType){
          this.$refs.upload.submit();
        }else{
          this.$message.error('请先选择模板类型');
        }

      },
      //导入数据回调
      UploadSuccess(res){
        if(res.code==0){
          this.$message({
            message:res.message,
            dangerouslyUseHTMLString:true,
            type: 'success'
          });
          this.importVo={};
          this.fileList=[];
          this.$refs.upload.clearFiles();
          //更新列表
          this.$refs.tableMain.fetchData()
        }else{
          this.$message.error(res.message);
        }
      },
      //上传模板
      changeFile(file, fileList){
        this.fileList = fileList;
      },
      //上传前验证
      beforeAvatarUpload(file, fileList){
        const isExcel = file.type === 'application/vnd.ms-excel';
        if(!isExcel){
          this.$message.error('文件格式错误');
        }
        return isExcel;
      },
      //查看详情
      detail(item){
        console.log(item);
        this.showDetailDialog = true;
        this.currentBill = item;
        if(item.billStartDate){
          this.currentBill.billStartEndDate = `${new Date(item.billStartDate).format('yyyy-MM-dd')} - ${new Date(item.billEndDate).format('yyyy-MM-dd')}`;
        }else{
          this.currentBill.billStartEndDate = '';
        }

      },
      //删除
      deleteItem(item){
        this.$refs.tableMain.remove(item.id);
      },
      //获取value 获取 lable
      getLabelByValue(list,value){
        console.log(value)
        let arr =  list.filter(item=>{
          return item.value == value;
        });
        if(!value || arr.length==0){
          return ''
        };
        return arr[0].label;
      },
      //保存或者新增费项
      save(onlySave=true){
        //验证
        console.log(this.editDialogCustomer);
        this.$refs.saveForm.validate((valid) => {
          console.log(valid)
          if (valid) {
            let _vo = {...this.editDialogCustomer};
            if(_vo.billStartEndDate && _vo.billStartEndDate[0]){
              _vo.billStartDate = _vo.billStartEndDate[0];
              _vo.billEndDate = _vo.billStartEndDate[1];
            }
            if(!onlySave){
              _vo.auditStatus='00'
            }

            delete  _vo.billStartEndDate;
            delete  _vo.officeOptions;
            delete  _vo.regionOptions;
            delete  _vo.regionId;
            delete  _vo.stationOptions;


            this.$api.energy.billManage.save(_vo).then(res => {
              if(res.code==0){
                let message = '操作成功！'
                this.showSaveDialog = false;
                this.$message({
                  message,
                  type: 'success'
                });
                //更新列表
                this.getTotal();
                this.$refs.tableMain.fetchData()
              }else{
                this.$message.error(res.message);
              }
            });
          } else {
            return false;
          }
        });
      },
      //切换站点
      changeSite(siteId){
        this.getTotal(siteId);
        this.$api.merch.region.officeOptions(siteId).then(res => {
          this.officeOptions  = res.data;
          //清空查询条件后面的级联 地点，区域，单元
          let searchModelData = this.$refs.tableMain.searchModelData;
          searchModelData.officeId='';
          searchModelData.regionId=[];
          searchModelData.stationId='';
          this.regionOptions=[];
          this.stationOptions=[];
          //刷新列表
          this.$refs.tableMain.fetchData();

        });
      },
      //获取账单审核状态统计
      getTotal(siteId){
        if(!siteId){
          siteId = this.$refs.tableMain.searchModelDataBase.siteId;
        }
        this.$api.energy.billManage.total(siteId).then(res=>{
          console.log(res.data)
          this.totalData =  res.data;
        });

      },
      //切换地点
      changeOffice(officeId){
        this.$api.merch.station.regionOptions(officeId).then(res => {
          this.regionOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          let searchModelData = this.$refs.tableMain.searchModelData;
          searchModelData.regionId=[];
          searchModelData.stationId='';
          this.stationOptions=[];
          //刷新列表
          this.$refs.tableMain.fetchData();

        });
      },
      //切换区域
      changeRegion(regionIdArray){
        let officeId = this.$refs.tableMain.searchModelData.officeId
        this.$api.merch.station.stationOptions(regionIdArray,officeId).then(res => {
          this.stationOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          this.$refs.tableMain.searchModelData.stationId='';
          //刷新列表
          this.$refs.tableMain.fetchData();
        });
      },
      //切换单元
      changeStation(stationId){
        this.$refs.tableMain.fetchData();
      },
      //新增或者编辑里面的级联
      changeOfficeForSave(officeId,callback){
        this.$api.merch.station.regionOptions(officeId).then(res => {
          let editDialogCustomer = this.editDialogCustomer;
          editDialogCustomer.regionOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          editDialogCustomer.regionId=[];
          editDialogCustomer.stationId='';
          editDialogCustomer.stationOptions=[];
          if(callback){
            callback()
          }
        });
      },
      changeRegionForSave(regionIdArray){
        let officeId = this.editDialogCustomer.officeId
        this.$api.merch.station.stationOptions(regionIdArray,officeId).then(res => {
          let editDialogCustomer = this.editDialogCustomer;
          editDialogCustomer.stationOptions = res.data;
          //清空查询条件后面的级联 区域，单元
          editDialogCustomer.stationId='';
        });
      },
      changeStationForSave(stationId){
        let siteId = this.editDialogCustomer.siteId;
        //获取企业信息
        this.$api.tet.stationCorp.getStationCurrCorpInfo(stationId).then(res=>{
          this.editDialogCustomer.corpName = res.data.corpName;
          this.editDialogCustomer.corpId = res.data.corpId;
          this.editDialogCustomer.corpContacts = res.data.corpContacts;
          this.editDialogCustomer.telephone = res.data.telephone;
        })

      },
      //新增或者编辑里面的级联 end

      //审核
      auditBill(){
        let status = '00';
        //获取选中项的id
        let vo = {
          status,
          billIds:[]
        };
        //获取待审核的记录的id集合
        this.$refs.tableMain.checkedItems.map(item =>{
          if(item.auditStatus==='99'){
            vo.billIds.push(item.id);
          }
        });
        console.log(vo.billIds);

        if(vo.billIds.length==0){
          this.$message.error('没有选中待审核的记录');
          return;
        }

        this.$api.energy.billManage.auditBill(vo).then(res=>{
          if(res.code==0){
            let message = '操作成功！'
            this.$message({
              message,
              type: 'success'
            });
            //更新列表,清空勾选的审核项
            this.getTotal();
            this.$refs.tableMain.checkedItems=[];
            this.$refs.tableMain.fetchData()
          }else{
            this.$message.error(res.message);
          }
        });
      },
    },
    mounted() {
      //获取站点列表
      setTimeout(()=>{
          this.$api.merch.region.siteOptions(this.unionId).then(res => {
            let siteId = res.data[0].siteId
            this.$refs.tableMain.searchModelDataBase.siteId = siteId
            this.siteOptions = res.data;
            this.changeSite(siteId);
          });

          //获取费项列表
          this.$api.tet.feeType.getFeeTypes().then(res =>{
            console.log(res)
            this.feeTypesOptions = res.data;
          })
      },0)
    }
  };
</script>


<style type="text/scss" lang="scss" scoped>
  .el-tag{ margin-right: 10px; cursor: pointer;}
  .el-dialog__body{ padding: 0 20px;}

  .grid-content-top{
     text-align: center;
    margin-bottom: 20px;
    .key{
      font-size: 14px;
      margin: 0;
      color:#aaa;
    }
    .value{
      font-size: 24px;
      font-weight: 700;
    }
  }
  .line{ width: 100%; height: 1px;background: #ddd; overflow: hidden; margin:0 0 20px 0}
  .el-upload__tip{ color: #E6A23C; line-height: 25px;}

  .details-info .el-form-item{margin-bottom: 0;}
</style>
<style>
  .upload-demo .el-upload{
    display: none;
  }
</style>