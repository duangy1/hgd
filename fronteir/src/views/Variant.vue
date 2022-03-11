<template>
  <div>
    <HeaderBar />
    <Navigator />
    <div class="title-box">
      <div style="padding-left:3%">
        <h2 class="trait-title">Consequence Type : {{ traitName }} </h2
        >
      </div>
    </div>
    <div class="wraper page-component__scroll el-scrollbar">
      <!-- 左侧树结构 -->
      <div class="tree-div">
        <el-card shadow="none" class="tree-card" >
        <el-input placeholder="输入关键字进行过滤" v-model="filterText">
        </el-input>

        <el-tree
          style="margin-top: 15px"
          class="filter-tree"
          :data="treedata"
          default-expand-all
          :filter-node-method="filterNode"
          icon-class="el-icon-s-management"
          ref="tree"
          :expand-on-click-node="false"
          @node-click="getInfoByTrait"
        >
        </el-tree>
        </el-card>
      </div>
      <div class="trait-info">
        <el-card shadow="none" class="tableCard" style="margin-top: 10px">
          <!-- 选择物种的filter表单 -->
          <div class="filter-box trait-box">
            <h3 style="text-align:left;color:rgb(128 129 130);font-weight:500">Filter your data by Choosing Species</h3>
            <el-form ref="form" :model="form" action="/traits/ortho-data" class="filterForm">
              <el-form-item  style="font-size:16px" >
                <el-tooltip class="tooltip-item"  placement="top" effect="light" content="Choose your interested species">
                  <el-select
                    v-model="form.querySpecies"
                    placeholder="Select"
                    filterable
                    class="species1_selector selector1"
                    @change="checkSpeClass(form.querySpecies)"
                  >
                    <el-option-group
                      v-for="group in species"
                      :key="group.label"
                      :label="group.label"
                      :value="group.value"
                    >
                      <el-option
                        v-for="item in group.options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-option-group>
                  </el-select>
                </el-tooltip>
                <el-tooltip class="tooltip-item"  placement="top" effect="light" content="Choose ortholog species">
                  <el-select
                    v-model="form.orthoSpecies"
                    placeholder="Select"
                    class="species1_selector selector2"
                    filterable
                    
                  >
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      :disabled="item.disabled"
                    >
                    </el-option>
                  </el-select>
                </el-tooltip>
                  <el-input
                    v-model="form.traitName"
                    v-show="notShow"
                  ></el-input>
                  <el-button type="primary" icon="el-icon-search" circle class="searchButton" @click="sendRequest()"></el-button>
                </el-form-item>
              
                
            </el-form>
              
          </div>

         <!-- 使用中的表格 -->
          <el-table
            :data="varData"
            border
            style="width: 100%;margin-top:3%;height:50%;overflow:hidden"
            class="trait-table"
            row-key="id"
            v-loading="loading"
            ref="table"
         
          >
          
            <el-table-column  prop="geneId" label="Gene Id" >
            </el-table-column>
            <el-table-column prop="snpNum" label="Snp Number">
              <template scope="scope">
                <el-tooltip class="tooltip-item"  placement="bottom" effect="light">
                  <div slot="content">Click to Show Detail<br/> Informations Below</div>
                  <a style="color:rgb(70, 121, 187);cursor:pointer;" @click="getGwasDetail(scope.row)">{{ scope.row.gwasNum}}</a>
                </el-tooltip>
              </template>
            </el-table-column>
            
            <el-table-column prop="speciesCommonName" label="CommonName" >
            </el-table-column>
            <el-table-column
              prop="varName"
              label="Variant Name"
            >
            </el-table-column>
            <el-table-column
              prop="orthoList"
              label="Ortholog Gene List"
              width="500"
            >
            <!--  v-if="scope.row.orthoList.length !=0" -->
            <template slot-scope="scope" >
                <el-table
                  :data="scope.row.orthoList"
                  
                  max-height="100px"
                  :show-header="false"
                  style="margin-bottom:15px"
                >
                  <el-table-column prop="geneId" label="geneId">GeneID</el-table-column>
                  <el-table-column prop="tax2" label="tax2"></el-table-column>
                  <el-table-column prop="traitName" label="traitName"></el-table-column>
                  
                </el-table>
                <!-- <a class="expandA" round v-if="scope.row.orthoList.length > 0" style="cursor:pointer;text-align:right;" @click="showSubOrthoTable(scope.row)" onclick="javascript:console.log(this.innerHTML);this.innerHTML=(this.innerHTML.indexOf('expand')!=-1?'close':'expand')">expand</a> -->
                <!-- <p v-if="scope.row.orthoList.length ==0">-</p>  -->
              </template>
              
              
            </el-table-column>
            <el-table-column type="expand" width="1" >
              <template slot-scope="props">
                <el-table :data="props.row.orthoList" max-height="250"  >
                  <el-table-column prop="gname2" label="Gname"></el-table-column>
                  <el-table-column prop="protein2" label="Protein"></el-table-column>
                  <el-table-column prop="tax2" label="Taxon"></el-table-column>
                  <el-table-column prop="traitName" label="Trait"></el-table-column>
                  <el-table-column prop="fromdb" label="From Database">
                    <template slot-scope="scope">
                      <p v-for="(item,index) in scope.row.fromdb" :key="index">{{item}}</p>
                    </template>
                  </el-table-column>
                  <el-table-column prop="dbEvidence" label="Evidence"></el-table-column>
                </el-table>
            </template>
          </el-table-column>
          </el-table>
          <el-pagination
            class="trait-pag"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage4"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="10"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalSize"
          >
          </el-pagination>
        </el-card>
      </div>
      
    </div>
    <!-- gwas detail info表格 -->
    <div class="gwasWrapper" v-if="showSubTableBox">
      <div  class="gwasBox" style="padding-left:3%;margin-bottom:1.3%">
        <h3 class="trait-title">Gwas Detai Information</h3>
      </div>
      <div id="subTableBox">
          <el-card shadow="none" class="gwasDetailCard">
            <el-table 
              :data="gwasInfoData"
              border
              style="width: 100%;margin-top:3%;"
              
              id="subTable"
              :row-style="{height: '0'}"
              max-height="400"
              v-loading="gwasLoading"
            >
              <el-table-column prop="varId" label="Var Id"></el-table-column>
              <el-table-column prop="traitName" label="Trait Name"></el-table-column>
              <el-table-column prop="speciesCommonName" label="Species Name"></el-table-column>
              <el-table-column prop="pmid" label="Pmid"></el-table-column>
              <el-table-column prop="pvalue" label="Pvalue"></el-table-column>
            </el-table>
          </el-card>
      </div>
    </div>
    <template>
    <el-backtop
      :bottom="100"
      style="z-index: 999"
      class="backTop"
    >
      <div
        style="
           {
            height: 100%;
            width: 100%;
            background-color: #f2f5f6;
            box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
            text-align: center;
            line-height: 40px;
            color: #1989fa;
          }
        "
      >
        UP
      </div>
    </el-backtop>
    </template>
    <FooterBar />
    <el-backtop
      target=".page-component__scroll .el-scrollbar__wrap"
      :bottom="100"
      style="z-index: 99"
    >
      <div
        style="
           {
            height: 100%;
            width: 100%;
            background-color: #f2f5f6;
            box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
            text-align: center;
            line-height: 40px;
            color: #1989fa;
          }
        "
      >
        UP
      </div>
    </el-backtop>
  </div>
</template>

<style scoped>
@import "../assets/css/traits.css";
</style>
<style src="../assets/css/variant.css" scoped></style>

<script>
import Navigator from "@/components/navigator.vue";
import varOntology from "@/assets/static/varOntology.json";
import FooterBar from "@/components/FooterBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
// import qs from "qs";
export default {
  name: "Traits",
  components: {
    Navigator,
    FooterBar,
    HeaderBar,
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  data() {
    return {
      filterText: "",
      treedata: varOntology.tree,
      showSubTableBox:false,
      gwasLoading:true,
      options: [],
      species:[
        {
        label:"Plant",
        options:[{
            value:"3635",
            label:"Cotton",
            disabled:false
          },{
            value:"102107",
            label:"Japanese apricot",
            disabled:false
          },{
            value:"4577",
            label:"Maize",
            disabled:false
          },{
            value:"3708",
            label:"Rapeseed",
            disabled:false
          },{
            value:"4530",
            label:"Rice",
            disabled:false
          },{
            value:"4558",
            label:"Sorghum",
            disabled:false
          },{
            value:"3847",
            label:"Soybean",
            disabled:false
          }]
        },
        {
        label:"animal",
        options:[{
            value:"9925",
            label:"Goat",
            disabled:false
          },{
            value:"9823",
            label:"Pig",
            disabled:false
          },{
            value:"9940",
            label:"Sheep",
            disabled:false
          },{
            value:"9031",
            label:"Chicken",
            disabled:false
          },{
            value:"9913",
            label:"Cattle",
            disabled:false
          }]
        }],
      varData: [],
      orthoTableData: [],
      value: "",
      traitName: "frameshift_variant",
      currentPage4: 1,
      totalSize: 0,
      form: {
        querySpecies:"",
        orthoSpecies: "",
        traitName: "",
      },
      tableSize: 10,
      showOrthoGene: true,
      notShow: false,
      gwasInfoData:[],
      loading:true,
      showOrthoTable:false,
      showOrthoSubTable:false,
      showGwasInfoTable:false
    };
  },

  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSizeChange(val) {
      this.loading = true;
      this.tableSize = val;
    //   this.$axios
    //     .get("http://localhost:9401/api/variant?length=" + val)
    //     .then((response) => {
    //       this.varData = response.data.data;
    //       for(let item of this.varData){
    //       console.log("item.fromdb:",item);
    //       item.gwasNum=item.gwasId.split(",").length;
    //       for(let orthoitem of item.orthoList){orthoitem.fromdb=orthoitem.fromdb.split(',')}
    //     }
    //     })
    //     .catch(function (error) {
    //       console.log(error);
    //     })
    //     .finally(() => (this.loading = false));
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      // let start=
      this.loading=true;
      this.currentPage4 = val;
    //   this.$axios
    //     .get("http://localhost:9401/api/variant?pageNo="+val+"&length="+this.tableSize)
    //     .then((response) => {
    //       this.varData = response.data.data;
    //       // console.log(this.varData);
    //       // this.varData.gwasNum=this.varData.gwasId.split(',').length
    //        for(let item of this.varData){
    //         console.log("item:",item);
    //         item.gwasNum=item.gwasId.split(",").length;
    //         for(let orthoitem of item.orthoList){orthoitem.fromdb=orthoitem.fromdb.split(',')}
    //     }
        
    //     })
    //     .catch(function (error) {
    //       console.log(error);
    //     })
    //     .finally(() => (this.loading = false));
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getInfoByTrait(value, data) {
        console.log("click tree:",value.label, data);
      this.loading=true;
      data.checked = true;
      this.traitName = value.label;
      this.form.traitName = value.label;

    //   this.$axios
    //     .get(
    //       "http://localhost:9401/api/variant/get-by-varName/" +
    //         value.Name +
    //         "?pageNo=" +
    //         this.currentPage4 +
    //         "&length=" +
    //         this.tableSize
    //     )
    //     .then((response) => {
    //       this.loading=false;
    //       this.varData = response.data.data;
    //       // this.varData.gwasNum=this.varData.gwasId.split(',').length
    //       this.totalSize = response.data.recordsTotal;
    //       for(let item of this.varData){
    //         console.log("item.fromdb:",item);
    //         item.gwasNum=item.gwasId.split(",").length;
    //         for(let orthoitem of item.orthoList){orthoitem.fromdb=orthoitem.fromdb.split(',')}
    //       }
    //     })
    //     .catch(function (error) {
    //       console.log(error);
    //     })
    //     .finally(() => (this.loading = false));
    },
    
   
    async sendRequest() {
      let formData = this.form;
      console.log("formData:",formData);
      this.loading=true;
    //   let data = await this.$axios({
    //     //这里的 this.$http 就是axios 因为 我在vue原型上，挂载axios的时候，把axios改名为$http 了。
    //     url: "http://localhost:9401/api/variant/ortho-data", //请求路径（接口）
    //     method: "POST", //请求方式
    //     headers: { "content-type": "application/x-www-form-urlencoded" }, // 请求头，发送FormData格式的数据，必须是 这种请求头。
    //     data: qs.stringify(formData), //发送请求要传的FormData参数。必须用 qs.stringify（）序列化一下。
    //   });
    //   this.loading=false;
    //   this.varData = data.data.data;
    //   this.totalSize = data.data.recordsTotal;
    //   console.log("this.varData:",this.varData);

    //    for(let item of this.varData){
    //       item.gwasNum=item.gwasId.split(",").length;
    //       for(let orthoitem of item.orthoList){orthoitem.fromdb=orthoitem.fromdb.split(',')}
    //     }
      //
    },
    objectSpanMethod({ rowIndex, columnIndex }) {
      if (rowIndex === 0){
        if (columnIndex > 3){
          return{
            rowspan: 1,
            colspan: 3,
          }
        }else{
          return{
            rowspan: 1,
            colspan: 1,
          }
        }
      }else{
        if(columnIndex < 4){
          return{
            rowspan: 1,
            colspan: 1,
          }
        }else{
          return{
            rowspan: 1,
            colspan: 3,
          }
        }
      }
      
    },
    // getGwasDetail(gwasidInfo){
    //   this.showSubTableBox = true;
    //   this.gwasLoading=true;
      // 向gwas发送请求，请求gwas库中的数据渲染详细信息的表格
      // http://192.168.164.15:9500/hdb/gwas/gwasids?gwasId=240,239&organismId=19&offset=0&pagesize=10&total=10
    //   let organismid=gwasidInfo.gwasOrgid;
    //   let gwasids=gwasidInfo.gwasId
    //   this.$axios.get("http://192.168.164.15:9500/hdb/gwas/gwasids?gwasId="+gwasids+"&organismId="+organismid+"&offset=0&pagesize=10&total=10")
    //   .then(function (response) {
    //     this.gwasInfoData=response.data
    //     // this.$("#subTableBox").attr('top')
    //     this.gwasLoading=false;
        
    //   }.bind(this))
    //   .catch(function (error) {
    //     console.log(error);
    //   });
    // },
    showSubOrthoTable(row){
    // this.showOrthoSubTable = !this.showOrthoSubTable
    let $table = this.$refs.table;
    $table.toggleRowExpansion(row)
  },
    checkSpeClass(val){
      // 判断所选择的物种是动物还是植物
      let plant=["3635","102107","4577","3708","4530","4558","3847"]
      this.form.orthoSpecies =""
      // 如果非植物就认为是动物
      // let animal=["9925","9823","9940","9031","9913"]
      if(plant.indexOf(val)!=-1){
        this.options=this.species[0].options
      }else{
        this.options=this.species[1].options
      }
      this.options.filter(item=>{if(item.value==val){item.disabled=true}else{item.disabled=false}})
  }
  },
  
  mounted() {
    const axiosInstance = this.$axios.create({
      headers: {
        "Content-Type": 'application/x-www-form-urlencoded;charset="utf-8"',
      }, // 设置传输内容的类型和编码
      withCredentials: true, // 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
    });
    axiosInstance
      .get("http://localhost:9401/api/variants")
      .then((response) => {
          console.log("response:",response);
        this.varData = response.data.data;
        this.totalSize = response.data.recordsTotal;
        // 增加gwasnum属性的地方
        // for(let item of this.varData){
        //   console.log("item.fromdb:",item);
        //   item.gwasNum=item.gwasId.split(",").length;
        //   for(let orthoitem of item.orthoList){orthoitem.fromdb=orthoitem.fromdb.split(',')}
        // }
      })
      .catch((error) => {
        console.log(error);
        this.errored = true;
      })
      .finally(() => (this.loading = false));
  },
};
</script>
