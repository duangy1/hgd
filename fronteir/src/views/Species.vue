<template>
<div class="table-container">
    <HeaderBar />
    <Banner />
    <!-- <Navigator />  -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item>Species</el-breadcrumb-item>
    </el-breadcrumb>
    <el-container style="border: 1px solid #eee;overflow: hidden;">
      <div class="wrapper-species-1">
        <div style="display:flex;height:42px">
        <div style="width:70%">
          <h2 class="trait-title title-species">Species List</h2>
        </div>
        <div style="width:30%">
          <h2 class="trait-title title-species">Species Statistics</h2>
        </div>
        </div>
        <div class="wrapper-species">
        <el-card style="width:70%;border-top: 2px solid rgb(64, 158, 255)!important;">
          
            <el-table
            :data="speciesTableData"
            style="width:100%;margin:auto;"
            v-loading="speciesLoading"
            empty-text="-">
            <!-- <el-table-column>
              <template>
                <img
                    min-width="70"
                    height="70"
                    class="iconImg"
                  />
                </template>
            </el-table-column> -->
            <el-table-column prop="latinName" label="Organism">
			<template slot-scope="scope">
				<i>{{scope.row.latinName}}</i>
			</template> 
			</el-table-column>
            <el-table-column prop="commonName" label="Common Name"></el-table-column>
            <el-table-column prop="taxonId" label="Ncbi Taxon Id"></el-table-column>
            <el-table-column prop="traitGeneNum" label="#Trait">
              <template slot-scope="scope" >
                <div v-if="scope.row.traitGeneNum==0">-</div>
                <div v-else>{{scope.row.traitGeneNum}}</div>
              </template>
            </el-table-column>
            <el-table-column prop="varGeneNum" label="#Variant">
              <template slot-scope="scope" >
                <div v-if="scope.row.varGeneNum==0">-</div>
                 <div v-else>{{scope.row.varGeneNum}}</div>
              </template>
            </el-table-column>
            <el-table-column prop="expGeneNum" label="#Expression">
              <template slot-scope="scope" >
                <div v-if="scope.row.expGeneNum==0">-</div>
                 <div v-else>{{scope.row.expGeneNum}}</div>
              </template>
            </el-table-column>
            <el-table-column prop="goGeneNum" label="#GO">
              <template slot-scope="scope" >
                <div v-if="scope.row.goGeneNum==0">-</div>
                <div v-else>{{scope.row.goGeneNum}}</div>
              </template>
            </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-sizes="[10, 30,100]"
              :page-size="pageSize"
              layout="sizes,total,pager,prev,next"
              :total="totalSize">
          </el-pagination>
			<div style="text-align:left;padding-top:10px;font-size:14px;">Note: # represents the number of homologous gene</div>
        </el-card>
        <el-card style="width:30%;border-top: 2px solid rgb(64, 158, 255)!important;margin-left:2%;">
          <div id="chart-div" style="width:300px;height:500px;"></div>
        </el-card>
      </div>
      <div class="trait-box">
        <div>
          <h2 class="trait-title title-species">Homologous Statistics Between Species</h2>
        </div>
      
        <el-card style="width:100%;border-top: 2px solid rgb(64, 158, 255)!important;margin-top: 1%;">
          <div id="chart-heatmap" style="width:100%;height:800px"></div>
        </el-card>
        <!-- 详细信息表，去掉，点击直接跳转ortholog info -->
        <!-- <div>
          <h2 class="trait-title title-species">Homolog Information Between Species</h2>
        </div>
        <el-table
          class="info-table-1"
          :border="false"
          fixed
        >
            <el-table-column
                prop="species1.commonName"
                label="Species">
            </el-table-column>
            <el-table-column
                prop="species1.taxonId"
                label="Taxon Id1">
            </el-table-column>
            <el-table-column
                prop="ensemblId1"
                label="Ensembl Id">
            </el-table-column>
            
            <el-table-column
                prop="protein1"
                label="Protein1">
            </el-table-column>
            <el-table-column
                prop="species2.commonName"
                label="Species2">
            </el-table-column>
            <el-table-column
                prop="species2.taxonId"
                label="Taxon Id2">
            </el-table-column>
            <el-table-column
                prop="protein2"
                label="Protein2">
            </el-table-column>
            <el-table-column
            prop="entrezId"
            label="Entrez Id">
            </el-table-column>
        </el-table> -->
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
<style src="../assets/css/species.css" scoped></style>
<script>
// @ is an alias to /src
// import Subnav from '@/components/sub-nav.vue'
// import Navigator from '@/components/navigator.vue';
// import "@/assets/css/traits.css";
import FooterBar from '@/components/FooterBar.vue'
import HeaderBar from '../components/HeaderBar.vue'
import Banner from "@/components/banner.vue";
import heatmapData from '@/assets/static/heatmap-statistic.json';

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
        speciesData:"",
        heatmapData,
        speciesTableData:[],
        currentPage:1,
        totalSize:0,
        pageSize:10,
        speciesCount:[{ value: 19, name: 'Animals' },
        { value: 16, name: 'Plants' },
        { value: 2, name: 'Others' }],
        speciesLoading:true
      }
     
    },
    
  methods:{
    handleSizeChange(val){
      this.pageSize=val;
      this.speciesTableData=this.speciesData.slice(0,val)
    },
    echartsHeatMap(){
      var chartDom = document.getElementById('chart-heatmap');
      var myChart = this.$echarts.init(chartDom);
      var option;

      const speciesName = this.heatmapData[0];

      let data =[];
      this.heatmapData[1]
        .forEach(function (item,index1) {
          let list2=item.speciesStatistic.split(",");
          for(let index2=0;index2< list2.length;index2++){
            let num=list2[index2]
            // [x,y,value]
            let valitem=[index1, Number(index2), Number(num) || '-'];
            data.push(valitem);
        }
      });
      
      option = {
        tooltip: {
          position: 'top',
          formatter:  (p)=> {
            // console.log("p:",p);
            return speciesName[p.data[0]] + ' - ' + speciesName[p.data[1]]+' </br> ' +"Homologous Protein Pairs : "+p.data[2];
          }
        },
        grid: {
          height: '81%',
          top: '4%'
        },
        xAxis: {
          type: 'category',
          data: speciesName,
          splitArea: {
            show: true
          },
          axisLabel: {interval:0,rotate:40 }
        },
        yAxis: {
          type: 'category',
          data: speciesName,
          splitArea: {
            show: true
          }
        },
        visualMap: {
          min: 0,
          max: 80000,
          calculable: true,
          orient: 'vertical',
          left: "93%",
          bottom: '60%'
        },
        series: [
          {
            name: heatmapData,
            type: 'heatmap',
            data: data,
            // label: {
            //   show: true
            // },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
           
          }
        ]
      };

      option && myChart.setOption(option);
      myChart.on('click',params=>{
        console.log("params:",params);
        this.getHomologBetweenSpecies(params)
    });
    },
    echartsDraw(){
      var chartDom = document.getElementById('chart-div');
      var myChart = this.$echarts.init(chartDom);
      var option;

      option = {
       
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '12%',
          left: 'center'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
          },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.speciesCount,
          }
        ]
      };
      option && myChart.setOption(option);
      myChart.on('click',params=>{
        let paramclass;
        if(params.name=="Animals"){
          paramclass="animal"
        }else if(params.name=="Plants"){
          paramclass="plant"
        }else{paramclass="others"}
        this.getSpeciesByClass(paramclass)
    });
    },
     getSpeciesByClass(paramclass){
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/species-by-class",{params:{'classification' : paramclass}})
       .then(response=>{
          this.speciesData=response.data;
          this.totalSize=response.data.length;
          this.speciesTableData=this.speciesData.slice(0,this.pageSize);
      })
    },
    getHomologBetweenSpecies(paramclass){
      // this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/species-by-class",{params:{'classification' : paramclass}})
      //  .then(response=>{
      //     console.log("species:",response);
      //     this.speciesData=response.data;
      //     this.totalSize=response.data.length;
      //     this.speciesTableData=this.speciesData.slice(0,this.pageSize);
      // })
		
		var index1 = paramclass.data[0];//x
		var index2 = paramclass.data[1];//y
		
	
		console.log("paramclass:",paramclass);
		var taxid1=this.heatmapData[1][index1].taxonId;
		var taxid2=this.heatmapData[1][index2].taxonId;
		var name1=this.heatmapData[1][index1].commonName;
		var name2=this.heatmapData[1][index2].commonName;
		this.$router.push({
          path: '/gene',
          query:{taxid1,taxid2,name1,name2}
        })
    },
    handleCurrentChange(){
      let num1=(this.currentPage>0?this.currentPage-1:0)*this.pageSize;
      let num2=this.currentPage*this.pageSize;
      this.speciesTableData=this.speciesData.slice(num1,num2)
    }
  },
  mounted(){
    // 获取上方物种饼图和表格的数据
    this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/species-all").then(response=>{
      console.log("response:",response);
      this.speciesData=response.data;
      this.totalSize=response.data.length;
      this.speciesTableData=this.speciesData.slice(0,this.pageSize)
    }).finally(()=>{
      this.speciesLoading=false
    })
    // 接口获取heatmap数据
    //  this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/speciesname-all").then(response=>{
    //   console.log("species:",response);
    //   // 返回值data两个列表，一个是speciesName,一个是obj包含value等信息
      // this.heatmap.speciesName=response.data[0];
      // for(let index1 in response.data[1]){
      //   let item=response.data[1][index1];
      //   let list=item.speciesStatistic.split(",");
      //   for(let index2 in list){
      //     let valitem=[Number(index2),Number(index1),Number(list[index2])]
      //     this.heatmap.value.push(valitem);
      //   }
      // }
      
    // })
    this.echartsDraw()
    this.echartsHeatMap()
  }
  
}
</script>
