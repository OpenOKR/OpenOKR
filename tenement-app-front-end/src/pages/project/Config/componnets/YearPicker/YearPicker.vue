<template>
  <div :style="{width:width}">
    <h2 class="year">{{year}}年填报时间安排</h2>
    <el-row>
      <el-col v-for="(monthItem,index) in monthData" :span="(parseInt(24/column))">
        <div class="month-item">
          <h4 class="month-name">{{monthMap[index]}}<span class="fr">{{index+1}}</span></h4>
          <table>
            <tr>
              <th><span class="weekday">一</span></th>
              <th><span class="weekday">二</span></th>
              <th><span class="weekday">三</span></th>
              <th><span class="weekday">四</span></th>
              <th><span class="weekday">五</span></th>
              <th><span class="weekend">六</span></th>
              <th><span class="weekend">日</span></th>
            </tr>
            <tr v-for="weekItem in monthItem">
              <td v-for="dayItem in weekItem" @click="changeValue(dayItem)" C>
                <span
                  v-if="dayItem.name"
                  :class="{active:dayItem.active,weekend:dayItem.week>=5}"

                >{{dayItem.name}}</span>
              </td>
            </tr>
          </table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  //年历表 暂时弃用
  //model ： {year:'2018',values:['2018/2/18']}
/*
    <year-picker
      width="1000px"
      column="4"
      v-model="model">
    </year-picker>
    */
  import formMixin from "@/mixins/form.mixin";

  const MONTH_STR_AYYAY =[
    '一月',
    '二月',
    '三月',
    '四月',
    '五月',
    '六月',
    '七月',
    '八月',
    '九月',
    '十月',
    '十一月',
    '十二月',
  ];

  export default {
    name: "YearPicker",
    components: {},
    mixins: [
      formMixin
    ],
    props: {
      value: Object,
      width:{
        type: String,
        default: '1200px'
      },
      column:{
        type: Number,
        default:6
      }
    },
    data() {
      return {
        year:'',
        monthMap:MONTH_STR_AYYAY,
        monthData:[],
      };
    },
    computed: {
    },
    methods: {
      setCurrentValue(){
        this.year = this.value.year;
        this.monthData = [];
        for(let i=0;i<12;i++){
          this.monthData.push(this.getMonthData(this.year,i));
        }
      },
      getMonthData(year,month){
        let _monthData =[];
        let currDate = new Date(year+'/'+ (month+1) +'/1');
        let firstDay = currDate.getDay();
        let monthIndex = 0;

        if(firstDay===0){
          firstDay = 6;
        }else{
          firstDay--;
        }
        _monthData.push([]);
        for(let i=0;i<firstDay;i++){
          monthIndex++;
          _monthData[0].push({
            name:'',
            value:'',
            active:false,
          })
        }
        while (currDate.getMonth()== month){
          let day = currDate.getDate();
          let _value = year+'/'+ (month+1) +'/'+ day;
          _monthData[_monthData.length-1].push({
            name:day,
            value:_value,
            active: this.value.values.length>0 && this.value.values.indexOf(_value)!=-1,
            week:monthIndex%7
          });
          monthIndex++;
          currDate.setDate(day+1);
          if(monthIndex%7==0 && currDate.getMonth()== month){
            _monthData.push([])
          }
        }
        for(let i=_monthData[_monthData.length-1].length;i<7;i++){
          _monthData[_monthData.length-1].push({
            name:'',
            value:'',
            active:false,
          })
        }
        return _monthData;
      },
      changeValue(item){
        if(!item.name){
          return;
        }
        let index = this.value.values.indexOf(item.value)
        if(index==-1){
          this.value.values.push(item.value);
        }else{
          this.value.values.splice(index, 1);
        }
        this.setCurrentValue(this.value);
      }
    },
    watch: {
      value(val, oldValue) {
        this.setCurrentValue(val);
      }
    },
    mounted() {
      this.setCurrentValue(this.value);
    }
  };

</script>

<style type="text/scss" lang="scss" scoped>
  .year{ text-align: center; font-size: 20px}
  .month-item{
    padding: 10px;
    font-size: 12px;
    max-width: 210px;
    text-align: center;
    line-height: 20px;
    height: 225px;
    margin: 0 auto 20px;
    border-radius: 3px;
    border-top:#f5f5f5 solid 1px;
    box-shadow:0 5px 15px -5px #999;
    .month-name{
      text-align:left;
      font-size: 14px;
      padding: 0;
      margin:0 0 10px;
      font-weight: normal;
      .fr{

        font-family: Arail;
        font-size: 28px;
        color: #409eff;
        font-style: italic;
        margin-right: 5px;
      }
    }
    table{
      width: 100%;
      border: none;
      border-collapse: collapse;
      th{
        border: none;
        background: #dcdfe6;
        padding: 3px 0;
      }
      td{
        border: none;
        padding: 3px 0;
        span{
          cursor: pointer;
          display: block;
          margin: 0 auto;
          width: 24px;
          height: 24px;
          line-height: 24px;
          border-radius: 50%;
        };
        span:hover{
          background: #00a8ff;
        };
        span.active{
          background:#409eff;
          color: #fff;
        };
        span.weekend{
          color: #f00;
        }
      }
    }
  }
  .month-item:hover{
    box-shadow:0 5px 20px -5px #999;
  }
</style>
