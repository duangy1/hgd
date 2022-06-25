<template>
<div class="table-container">
    <HeaderBar />
    <Banner />
    <!-- <Navigator />  -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
      <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
      <el-breadcrumb-item>Statistics</el-breadcrumb-item>
    </el-breadcrumb>
    <el-container style="border: 1px solid #eee;overflow: hidden;">
    <div class="wrapper-species-1">
    
    <div >
    <!-- <el-card style="width:50%;border-top: 2px solid rgb(64, 158, 255)!important;">
      <div id="chart-trait2homolog-0" style="width:100%;height:450px;"></div>
    </el-card>
    <el-card style="width:50%;border-top: 2px solid rgb(64, 158, 255)!important;margin-left:2%;">
      <div id="chart-trait2homolog-1" style="width:100%;height:450px"></div>
      <div id="extensionOne" style="width:150px;height:150px"></div>
    </el-card> -->
    <div style="width:100%;">
        
        <el-card style="width:100%;border-top: 2px solid rgb(64, 158, 255)!important;">
          <h2 class="trait-title title-species">Homolog Data Integration</h2>
            <el-table
              :data="statisticData.tableData"
              stripe
              border
              style="width: 90%;margin: auto;"
              :header-row-style="{background:'dark-gray'}">
              <el-table-column
                prop="dataclass"
                label="Data Item"
                align="center"
                >
              </el-table-column>
              <el-table-column
                prop="protein number"
                label="Number"
                align="center"
                >
              </el-table-column>
              
            </el-table>
            <h2 class="trait-title title-species">Homolog Data Annotation Integration</h2>
            <el-table
              :data="statisticData.tableDataAnnotat"
              stripe
              border
              style="width: 90%;margin: auto;">
              <el-table-column
                prop="dataclass"
                label="Data Item"
                align="center"
                >
              </el-table-column>
              <el-table-column
                prop="protein number"
                label="Number"
                align="center"
                >
              </el-table-column>
              
            </el-table>
        </el-card>
      </div>
    </div>
    <div class="wrapper-species">
       
      <el-card style="width:100%;border-top: 2px solid rgb(64, 158, 255)!important;">
      <div>
          <div>
            <h2 class="trait-title title-species font-title" >Traits Statistics</h2>
          </div>
          <div id="chart-trait2homolog-0" class="chartsLayout section-titile"></div>

          <div id="chart-trait2homolog-1" class="chartsLayout section-titile"></div>

        </div>
        <div>
          <div>
            <h2 class="trait-title title-species font-title">Variant Statistics</h2>
          </div>
          <div id="chart-trait2homolog-2" class="chartsLayout section-titile"></div>
          <div id="chart-trait2homolog-3" class="chartsLayout section-titile"></div>
        </div>
        <div>
          <div>
            <h2 class="trait-title title-species font-title">Gene Ontology Statistics</h2>
          </div>
          <div id="chart-trait2homolog-4" class="chartsLayout section-titile"></div>
          <div id="chart-trait2homolog-5" class="chartsLayout section-titile"></div>
        </div>
        <div>
          <div>
            <h2 class="trait-title title-species font-title">Expression Statistics</h2>
          </div>
          <div id="chart-trait2homolog-6" class="chartsLayout section-titile" ></div>
          <div id="chart-trait2homolog-7" class="chartsLayout section-titile"></div>
        </div>

        <!-- <div>
          <div>
            <h2 class="trait-title title-species">Traits Statistics</h2>
          </div>
          <div id="chart-trait2homolog-8" style="width:50%;height:300px;float: left;"></div>
          <div id="chart-trait2homolog-9" style="width:50%;height:300px;float: left;"></div>
        </div> -->
      </el-card>
    </div>
    <div >
    </div>
    
    </div>
    </el-container>
  <FooterBar />
   <el-backtop
      :bottom="100"
      style="z-index: 999;"
      class="backTop"
    >
      <div
        style="
           {
            height: 100%;
            width: 100%;
            box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
            text-align: center;
            line-height: 40px;
            color: #1989fa;
            border-radius: 50%;
            background-color: white;
          }
        "
      >
      <i class="el-icon-caret-top"></i>
      </div>
    </el-backtop>
</div>

</template>
<style src="../assets/css/statistics.css" scoped></style>
<style scoped>
.myclass{
    position: absolute;
    color: #90979c;
    /* color: "#90979c", */
    /* // border: solid 0px white; */
    font-family: "Arial";
    font-size: 14px;
    padding: 5px;
    display: inline;
  }
.font-title{
  font-size: 1.5em;
  font-weight: bolder;

}
.chartsLayout{
width:70%;
  height:450px;
  margin-left: 15%;
  margin-right: 15%


}

</style>
<script>
// @ is an alias to /src
import FooterBar from '@/components/FooterBar.vue'
import HeaderBar from '../components/HeaderBar.vue'
import Banner from "@/components/banner.vue";
// import traitStatData from '@/assets/static/traitStatistic.json';
import statisticData from '@/assets/static/statistic.json';

export default {
  name: 'Species',
  components: {
    // Navigator,
    FooterBar,
    HeaderBar,
    Banner
  },
  data(){
      return {
        speciesCount:[{ value: 16, name: 'Animals' },
        { value: 16, name: 'Plants' },
        { value: 5, name: 'Others' }],
        statisticData,

      }
    },
    
  methods:{
    // echartsDraw(){
    //   var chartDom = document.getElementById('chart-div-2');
    //   var myChart = this.$echarts.init(chartDom);
    //   var option;

    //   option = {
    //     title: {
    //       text: 'Traits annotat on Homolog gene',
    //     },
    //     tooltip: {
    //       trigger: 'item'
    //     },
    //     legend: {
    //       top: '10%',
    //       left: 'center'
    //     },
    //     series: [
    //       {
    //         name: 'Access From',
    //         type: 'pie',
    //         radius: ['40%', '70%'],
    //         avoidLabelOverlap: false,
    //         itemStyle: {
    //           borderRadius: 10,
    //           borderColor: '#fff',
    //           borderWidth: 2
    //       },
    //         label: {
    //           show: false,
    //           position: 'center'
    //         },
    //         emphasis: {
    //           label: {
    //             show: true,
    //             fontSize: '40',
    //             fontWeight: 'bold'
    //           }
    //         },
    //         labelLine: {
    //           show: false
    //         },
    //         data: this.speciesCount,
    //         left:'center',
    //         top:'10%'
    //       }
    //     ]
    //   };
    //   option && myChart.setOption(option);
    //   myChart.on('click',params=>{
    //     let paramclass;
    //     if(params.name=="Animals"){
    //     console.log(params);
    //       paramclass="animal"
    //     }else if(params.name=="Plants"){
    //       paramclass="plant"
    //     }else{paramclass="others"}
    //     this.getSpeciesByClass(paramclass)
    // });
    // },
     trait2speciesBar(domid,data,height1){
      var chartDom = document.getElementById(domid);
      var myChart = this.$echarts.init(chartDom);
      var option;

      option = {
         title: {
          text: data.title,
          subtext: 'The number of homolog gene on each trait ontology term'
        },
        xAxis: {
          type: 'category',
          data: data.xaxis,
          // axisLabel: {interval:0,rotate:40 },
          axisLabel: { //设置x轴的字
              show:true,
              interval:0,//使x轴横坐标全部显示
              textStyle: {//x轴字体样式
                // color: "rgba(219,225,255,1)",
                margin: 0
              },
              rotate:30,
            },
        },
        yAxis: {
         
          type: 'value'
        },
        series: [
          {
            data: data.data,
            type: 'bar',
            top:'10%',
            left:'center',
          }
          
        ],
        grid:{
           y2:height1
        }
        


      };

      option && myChart.setOption(option);

      
    },
    traitBar(){
      var chartDom = document.getElementById('chart-div-1');
      var myChart = this.$echarts.init(chartDom);
      var option;

      option = {
         title: {
          text: 'Traits annotat on Homolog gene',
          subtext: 'The number of homolog gene on each trait ontology term'
        },
        xAxis: {
          type: 'category',
          data: ["growth and meat production trait","animal welfare trait","mammary gland and milk production trait","animal health trait","nutrition trait","reproduction trait","biochemical trait","plant quality trait","plant growth and development trait","biological process trait","plant morphology trait","yield trait","plant vigor trait","sterility or fertility trait","stress trait"],
          // axisLabel: {interval:0,rotate:40 }
           axisLabel: { //设置x轴的字
              show:true,
              interval:0,//使x轴横坐标全部显示
              textStyle: {//x轴字体样式
                // color: "rgba(219,225,255,1)",
                margin: 15,
              },
              rotate:30,
              triggerEvent: true,
              formatter: function (value) {
                if (value.length > 20) {
                value = value.substring(0, 20) + "..";
                }
                return value;
                },
            },
        },
        yAxis: {
         
          type: 'value'
        },
        series: [
          {
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar'
          }
        ]
      };

      option && myChart.setOption(option);

      // myChart.on('mouseover', function (params) {
      //   console.log("params:",params);
      //   if (params.componentType != "xAxis") {
      //   return;
      //   }
        
      //   //设置div框样式，并为其填充值。
      //   let dom=document.getElementById('#extensionOne')
      //   dom.classList.add('myclass')
        
      //   // .text(params.value);
      //   // var xx_text = params.event.offsetX - 35;
      //   // arrow_left = xx_text;
      
      //   document.getElementById("#talentDemandDistribution").mousemove(function (event) {
      //   // console.log("X轴坐标：" + event.pageX + " Y轴坐标：" + event.pageY);
      //   var xx = event.pageX - 30;
      //   var yy = event.pageY + 10;
      //   document.getElementById('#extensionOne').css('top', yy).css('left', xx);
      //   });
      // });
      
      // myChart.on('mouseout', function () {
      //   document.getElementById('#extensionOne').css('display', 'none');
      // });

    },
    trait2homologBar(title,domid,data,xaxis){
      var chartDom = document.getElementById(domid);
      var myChart = this.$echarts.init(chartDom);
      var option;

      option = {
         title: {
          text: title,
          subtext: 'The number of homolog gene on each trait ontology term'
        },
        xAxis: {
          type: 'category',
          data: xaxis
        },
        yAxis: {
         
          type: 'value'
        },
        series: [
          {
            data: data,
            type: 'bar',
            top:'20%'
          }
          
        ],
        
      };

      option && myChart.setOption(option);
    },
  },
  mounted(){
    // this.echartsDraw()
    // this.traitBar()
    // this.trait2speciesBar('chart-div-2',statisticData.trait2species)
      let i=0;
      for(let index in statisticData.barData){
        console.log("key:",index,statisticData);
        // let num=index;
        let item=statisticData.barData[index]
        console.log("item:",item);
        // console.log("item:",item);
        let heigth = [120,120,120,120,120,220,120,120,]
        this.trait2speciesBar('chart-trait2homolog-'+i,item,heigth[i]);
        i+=1;
        console.log(i)
      }
   

  }
  
}
</script>
