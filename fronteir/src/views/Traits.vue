<template>
  <div>
    <HeaderBar />
    <Banner />
    <Navigator />
    
    <!-- 左侧树结构 -->
    <!-- 抽屉结构 -->
    <el-drawer
      :visible.sync="drawer"
      :direction="direction"
    >
      <div class="tree-div">
      <el-card shadow="none" class="tree-card" >
      <el-input placeholder="Enter keywords to filter" v-model="filterText" class="filterInput"> 
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
    </el-drawer>
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
      <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
      <el-breadcrumb-item>Traits</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 侧边栏按钮 -->
  
  <el-container style="border: 1px solid #eee;overflow: hidden;" id="trait-container">
  <div style="margin-top:3%">
   <el-button @click="buttonFunction()" ref="button" id="button"></el-button>
    <div class="trait-box trait-infoBox">
      <div style="padding-left:29px">
        <h2 class="trait-title">Trait Name : {{ traitItem.traitName }} | ID: {{ traitItem.traitId }}</h2
        >
        <h2 class="trait-def trait-title" v-if="traitItem.traitDef != ''"
          >Definition: {{ traitItem.definition }}</h2
        >
      </div>
    </div>
    </div>
    <el-card shadow="none" class="border-card">
    <el-tabs v-model="activeName" style="margin-top:10px;margin-bottom: 1%;" @tab-click="changeClass">
    <!-- 动物tab -->
    <el-tab-pane label="animal" name="first" >
      <!-- <el-card shadow="none" class="tableCard" style="margin-top: 10px"> -->
      <div>
        <div>
            <div class="filt-div">
              <el-select v-model="form.querySpecies" placeholder="Query Species" style="float:left"> 
                <el-option
                  v-for="item in querySpeciesList"
                  :key="item.id"
                  :label="item.commonName"
                  :value="item.taxonId">
                </el-option>
              </el-select>
              <p style="float:left;margin-left:3%;vertical-align:middle;margin-top: 0.5%;font-family: 'Noto Sans', sans-serif;">Targer Species:</p>
                <el-select v-model="form.orthoSpecies" multiple class="filt-spe-select">
                    <!-- <el-option label="Org1" value="shanghai"></el-option>
                    <el-option label="Org2" value="beijing"></el-option> -->
                    <el-option
                        v-for="(item,index) in speciesInfoList"
                        :key="index.id"
                        :label="item.commonName"
                        :value="item.taxonId"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search" type="success" round  plain id="filter-search" @click="searchFilter(form,traitItem.traitId)"></el-button>
                <el-button icon="el-icon-delete" type="primary" round  plain id="filter-search2" @click="clearFilter()"></el-button>
            </div>
          <!-- </el-card> -->
          <div id="wrapper"  v-loading="loading">
          <div class="trait-info">
            
      <!-- 使用中的trait表格 -->
      <!-- <el-container style="width:100%;border: 1px solid darkgray;"> -->
        
          <el-table
            :data="traitData_animal"
            class="trait-table"
            row-key="id"
            ref="table"
            :header-cell-style="hiligtDbCols"
          >
            <el-table-column align="center" class="titleCell" prop="traitName" label="Trait Name" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene Id" width="220px" fixed>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="160px" fixed></el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Ortholog Species Name">
            <el-table-column align="center" :label="item" :show-overflow-tooltip="true" :prop="item" v-for="(item,index) in speciesList_animal" :key="index"
              class="imglink" height="100px">
                  <template slot-scope="scope" >
                  <img
                      :src="orthoIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='!iconClass(scope.row,index) && scope.row.speciesListData.indexOf(index)>-1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList[scope.row.speciesListData.indexOf(index)])
                    />
                    <!--这个v-if，先判断在当前的cell内有数值，然后再判断值 -->
                    <img
                      :src="sameTraitIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='iconClass(scope.row,index)'
                      style="cursor:pointer"
                      @click=showGwasInfoTable(scope.row,index)
                    />
                  </template>
              </el-table-column>
            </el-table-column>
          </el-table>
      
          <div style="position: absolute;float: left;padding-top: 0.7%;">
          <div style="display: flex;">
                    <img :src="orthoIcon"
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" />
                      <div class="note-info">This icon represent the gene has homolog gene informations here.</div>
          </div>
          <div style="display: flex;">
                    <img :src="sameTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">This icon represent the gene's homolog gene here has a same trait annotation.</div>  
          </div>
          </div>
          <el-pagination
          
            class="trait-pag"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage4"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="10"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalSize_animal"
          >
          </el-pagination>
      <!-- </el-container> -->
      </div>
</div>

    <!-- 同源表格 -->
    <!-- <el-card shadow="none" class="sub-table-card"> -->
      
      <div class="sub-trait-box " v-if="showOrthoSubTable">
      <el-divider></el-divider>
          <div class="title-box">
            <h2 class="trait-sub-title">Ortholog Gene Detai Information</h2>
          </div>
      </div>
      <!-- <div id="wrapper"> -->
          <div class="trait-info">
            <el-table
              :data="orthoTableData"
              class="info-table"
              v-loading="orthoLoading"
              v-if="showOrthoSubTable"
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
            </el-table>
          </div>

      <!-- </div> -->
    <!-- </el-card> -->

      <!-- gwas 表格 -->
    <!-- <el-card shadow="none"  class="sub-table-card"> -->
       
      <div class="sub-trait-box" v-if="showSubTableBox">
        <div  class="title-box" >
          <h3 class="trait-sub-title">Gwas Detail Information</h3>
        </div>
        <div id="trait-info">
            <!-- <el-card shadow="none" class="gwasDetailCard"> -->
              <el-table 
                :data="gwasInfoData"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="400"
                v-loading="gwasLoading"
              >
                <el-table-column prop="varId" label="Var Id"></el-table-column>
                <el-table-column prop="traitName" label="Trait Name"></el-table-column>
                <el-table-column prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column prop="pmid" label="Pubmed Id"></el-table-column>
                <el-table-column prop="pvalue" label="Pvalue"></el-table-column>
              </el-table>
            <!-- </el-card> -->
        </div>
      </div>
    <!-- </el-card> -->
      </div>
      </div>
      <!-- </div> -->
      <!-- </el-card> -->
    </el-tab-pane>
     <!-- 植物tab -->
    <el-tab-pane label="plant" name="second">
      <div class="filt-div">
          <el-select v-model="form.querySpecies" placeholder="Query Species" style="float:left"> 
            <el-option
              v-for="item in querySpeciesList"
              :key="item.id"
              :label="item.commonName"
              :value="item.taxonId">
            </el-option>
          </el-select>
          <p style="float:left;margin-left:3%;vertical-align:middle;margin-top: 0.5%;font-family: 'Noto Sans', sans-serif;">Targer Species:</p>
            <el-select v-model="form.orthoSpecies" multiple class="filt-spe-select">
                <!-- <el-option label="Org1" value="shanghai"></el-option>
                <el-option label="Org2" value="beijing"></el-option> -->
                <el-option
                    v-for="(item,index) in speciesInfoList"
                    :key="index.id"
                    :label="item.commonName"
                    :value="item.taxonId"
                    >
                </el-option>
            </el-select>
            <el-button icon="el-icon-search" type="success" round  plain id="filter-search" @click="searchFilter(form,traitItem.traitId)"></el-button>
            <el-button icon="el-icon-delete" type="primary" round  plain id="filter-search2" @click="clearFilter()"></el-button>
          </div>
      <!-- <div class="wraper"> -->
        
      <div class="trait-info">
   <!-- 使用中的表格 -->
        <el-table
            :data="traitData_plant"
            
            class="trait-table"
            row-key="id"
            v-loading="loading"
            ref="table"
            :header-cell-style="hiligtDbCols"
          >
            <el-table-column class="titleCell" prop="traitName" label="Trait Name" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column  prop="geneId" label="Gene Id" width="220px" fixed>
            </el-table-column>
            <el-table-column prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column prop="taxonId" label="Taxon Id" width="160px" fixed></el-table-column>
            <el-table-column prop="speciesCommonName" label="Ortholog Species Name">
            <el-table-column :label="item" :show-overflow-tooltip="true" :prop="item" v-for="(item,index) in speciesList_plant" :key="index"
              class="imglink" height="100px">
                  <template slot-scope="scope" >
                  <img
                      :src="orthoIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='!iconClass(scope.row,index) && scope.row.speciesListData.indexOf(index)>-1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList[scope.row.speciesListData.indexOf(index)])
                    />
                    <!--这个v-if，先判断在当前的cell内有数值，然后再判断值 -->
                    <img
                      :src="sameTraitIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='iconClass(scope.row,index)'
                      style="cursor:pointer"
                      @click=showGwasInfoTable(scope.row,index)
                    />
                  </template>
              </el-table-column>
            </el-table-column>
          </el-table>
        <div style="position: absolute;float: left;padding-top: 0.7%;">
        <div style="display: flex;">
                  <img :src="orthoIcon"
                    style="margin-right: 6px;min-width=70px;height=70px;"
                    class="iconImg" />
                    <div style="color:dimgray">This icon represent the gene has homolog gene informations here.</div>
        </div>
        <div style="display: flex;">
                  <img :src="sameTraitIcon"   
                    style="margin-right: 6px;min-width=70px;height=70px;"
                    class="iconImg" /><div style="color:dimgray">This icon represent the gene's homolog gene here have a same trait annotation.</div>  
        </div>
        </div>
        <el-pagination
          class="trait-pag"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalSize_plant"
        >
        </el-pagination>
        <div class="sub-trait-box " v-if="showOrthoSubTable">
      <el-divider style="padding-top:5px"></el-divider>
          <div class="title-box">
            <h2 class="trait-sub-title">Ortholog Gene Detai Information</h2>
          </div>
      </div>
    <!-- 同源表格 -->
          <el-table
            :data="orthoTableData"
            class="info-table"
            v-loading="orthoLoading"
            v-if="showOrthoSubTable"
            :border="false"
        >
            <el-table-column
                prop="species.commonName"
                label="Species"
                width="180px">
            </el-table-column>
            <el-table-column
                prop="species.taxonId"
                label="Taxon Id">
            </el-table-column>
            <el-table-column
                prop="gbiInfo.ensemblGeneId"
                label="Ensembl Id">
            </el-table-column>
            <el-table-column
                prop="gbiInfo.geneSymbol"
                label="Gene Symbol">
            </el-table-column>
            <el-table-column
                prop="protein"
                label="Protein">
            </el-table-column>
            
            <el-table-column
            prop="entrezId"
            label="Entrez Id">
            </el-table-column>
          </el-table>
    <!-- gwas detail info表格 -->
      <div class="sub-trait-box" v-if="showSubTableBox">
        <div  class="title-box" style="padding-left:1.5%;margin-bottom:1.3%">
          <h3 class="trait-sub-title">Gwas Detai Information</h3>
        </div>
        <div id="trait-info">
            <el-card shadow="none" class="gwasDetailCard">
              <el-table 
                :data="gwasInfoData"
                style="width: 100%;margin-top:3%;"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="400"
                v-loading="gwasLoading"
              >
                <el-table-column prop="varId" label="Var Id"></el-table-column>
                <el-table-column prop="traitName" label="Trait Name"></el-table-column>
                <el-table-column prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column prop="pmid" label="Pubmed Id"></el-table-column>
                <el-table-column prop="pvalue" label="Pvalue"></el-table-column>
              </el-table>
            </el-card>
        </div>
      </div>
      </div>
    <!-- </div> -->
     
     
      <!-- </el-card> -->
    </el-tab-pane>
    
    
    </el-tabs>
    </el-card>
    </el-container>
    <FooterBar />
    <template>
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
    </template>
  </div>
</template>

<style src="../assets/css/traits.css" scoped></style>

<script>
// @ is an alias to /src
import Navigator from "@/components/navigator.vue";
import traitOntology from "@/assets/static/traitOntology.json";
import Banner from "@/components/banner.vue";
import FooterBar from "@/components/FooterBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
// import qs from "qs";
// // icons
import sameTraitIcon from "@/assets/img/icon/star.svg";
import orthoIcon from "@/assets/img/icon/huafu.svg";

// import "@/assets/css/traits.css";
export default {
  name: "Traits",
  components: {
    Navigator,
    FooterBar,
    HeaderBar,
    Banner,
    
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  data() {
    return {
      sameTraitIcon,
      orthoIcon,
      filterText: "",
      treedata: traitOntology.tree,
      showSubTableBox:false,
      gwasLoading:true,
      orthoLoading:true,
      activeName: 'first',
      drawer:false,
      direction:"ltr",
      speciesList_animal:[],
      speciesList_plant:[],
      speciesInfoList:[],
      traitData_animal: [],
      traitData_plant:[],
      orthoTableData: [],
      value: "",
      traitItem:"",
      currentPage4: 1,
      totalSize_animal: 0,
      totalSize_plant: 0,
      form: {
        querySpecies:"",
        orthoSpecies: "",
      },
      tableSize: 10,
      showOrthoGene: true,
      notShow: false,
      gwasInfoData:[],
      otrhoGeneData:[],
      loading:true,
      showOrthoSubTable:false,
      classification:"animal",
      navBarFixed:"false",
      showTraitSameIcon:false,
      querySpeciesList:[],
      querySpecies:""
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
    changeClass(tab, event){
        this.speciesList=[];
        this.speciesInfoList=[];
        // this.loading = true;
        this.showOrthoSubTable=false;
        console.log("value:",tab, event);
        console.log(tab.label);
        this.classification=tab.label;
        this.$axios.get("http://localhost:9401/api/traits-item",{params: {classification: this.classification=="animal"?1:2}}).then(response=>{
          console.log("traitsItem:",response);
          this.traitItem=response.data
      })
      // this.loading=false;
    //   this.$axios.get("http://localhost:9401/api/traits",{params:{'classification': this.classification}})
    //   .then((response) => {
    //       this.totalSize = response.data.recordsTotal;
    //       this.loading = false;
    //       console.log("response:",response.data);
    //       this.traitData=response.data.data;
    //       if(this.traitData.length>0){
    //         for(let traitData of this.traitData){
    //           // 增加一个属性保存物种的index，用来显示表格里的icon
    //           traitData.speciesListData=[]
    //           if(traitData.orthoList){
    //             for(let item of traitData.orthoList){
    //               let speciesName=item.species.commonName
    //               if(this.speciesList.indexOf(speciesName)==-1){
    //                 this.speciesList.push(speciesName);
    //                 this.speciesInfoList.push(item.species)
    //               }
    //               traitData.speciesListData.push(this.speciesList.indexOf(speciesName));
    //             }
    //           }
    //         }
    //       }
    // })
    },


    handleSizeChange(val){
      this.loading = true;
      this.tableSize = val;
      this.$axios.get("http://localhost:9401/api/traits?length=" + val)
      .then((response) => {
        this.traitData = response.data.data;
        this.totalSize = response.data.recordsTotal;
        console.log("response:",response.data);
      })
    },
    handleCurrentChange(val) {
      this.loading=true;
      this.currentPage4 = val;
      console.log(this.classification);
      this.$axios.get("http://localhost:9401/api/traits?pageNo="+val+"&length="+this.tableSize,{params:{'classification':this.classification}})
      .then((response) => {
        this.traitData = response.data.data;
        this.totalSize = response.data.recordsTotal;
        console.log("response:",response);       
        for(let item of this.traitData){
        item.speciesListData=[]
            if(item.orthoList){
              for(let item of item.orthoList){
                let speciesName=item.species.commonName
                if(this.speciesList.indexOf(speciesName)==-1){
                  this.speciesList.push(speciesName);
                  this.speciesInfoList.push(item.species)
                }
                item.speciesListData.push(this.speciesList.indexOf(speciesName));
              }
            }
        }

        this.loading=false;
      })
  
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getInfoByTrait(value, data) {
      this.loading=true;
      data.checked = true;
      // this.traitID = value.traitID;
      // this.traitDef = value.Definitions;
      // this.traitName = value.Name;
      this.traitItem=value;
      // this.form.traitName = value.Name;

      this.$axios.get(
          "http://localhost:9401/api/traits/get-by-trait-name/" +
            value.Name +
            "?pageNo=" +
            this.currentPage4 +
            "&length=" +
            this.tableSize
        )
        .then((response) => {
          this.loading=false;
          this.traitData = response.data.data;
          this.totalSize = response.data.recordsTotal;
          for(let item of this.traitData){
            item.gwasNum=item.gwasId.split(",").length;
          }
          this.loading = false
        })
    },
    
  showOrthoInfoTable(orthoList){
    this.showOrthoSubTable = true;
    this.showSubTableBox=false;
    this.orthoLoading=false;
    this.orthoTableData=[orthoList];
  },
  // watchScroll() {
  //     var scrollTop =
  //       window.pageYOffset ||
  //       document.documentElement.scrollTop ||
  //       document.body.scrollTop;
  //     //  当滚动超过 288 时，实现吸顶效果
  //     if (scrollTop > 288) {
  //       this.navBarFixed = true;
  //     } else {
  //       this.navBarFixed = false;
  //     }
  // },
  // 表格高度自适应
  // getTableMaxHeight(){
  //     this.$nextTick(function () {
  //     let box = document.querySelector(".trait-info").attributes
  //     let box_clientHeight = box[0].ownerElement.clientHeight;
  //     this.tableHeight = box_clientHeight - 100;
  //   })
  // },
  buttonFunction(){
    this.drawer = true;
   },
  showGwasInfoTable(rowValue,index){
    // scope.row.gwasId,scope.row.taxonId,scope.row.orthoList[scope.row.speciesListData.indexOf(index)]
    console.log("row value:",rowValue);
    let gwasid=rowValue.gwasId
    let taxid=rowValue.gwasOrgid
    let orthoList=rowValue.orthoList[rowValue.speciesListData.indexOf(index)]
    this.showSubTableBox=true;
    console.log("gwasid,taxid:",gwasid,taxid);
    this.$axios.get("http://192.168.164.15:9500/hdb/gwas/gwasids?gwasId="+gwasid+"&organismId="+taxid+"&offset=0&pagesize=10&total=10")
    .then(response=>{
      console.log("gwas response:",response);
      this.gwasInfoData=response.data
      this.gwasLoading=false;
    })
    this.showOrthoSubTable = true;
    this.orthoLoading=false;
    this.orthoTableData=[orthoList];
    console.log(orthoList);
   },
    hiligtDbCols({rowIndex}){
      if(rowIndex===1){
          return "writing-mode: vertical-lr; !important;text-align:right"
      }
      
    },
    iconClass(data,index){
      let classFT=data.speciesListData.indexOf(index)>-1?data.orthoList[data.speciesListData.indexOf(index)].orthoTraitName.indexOf(data.traitName)>-1:false
      return classFT
    },
    // dealSpeName(itemName){
    //   let name=itemName.length>13?itemName.slice(0,13)+"...":itemName;
    //   return name
    // }
    searchFilter(value,traitId){
      this.$axios.get("http://localhost:9401/api/traits/ortho-data",{params:{"orthoSpecies":value.orthoSpecies,"querySpecies":value.querySpecies,"traitId":traitId}})
      .then(response=>{
          console.log("searchFilter:",response);
      })
      // console.log(value,traitName);
    }
  },
  

  mounted() {
    this.loading = true;
    this.$axios.get("http://localhost:9401/api/traits",{params:{'classification': 'animal'}})
      .then((response) => {
        this.totalSize_animal = response.data.recordsTotal;
        console.log("response:",response.data);
        this.traitData_animal=response.data.data;
        if(this.traitData_animal.length>0){
          for(let traitData of this.traitData_animal){
            // 增加一个属性保存物种的index，用来显示表格里的icon
            traitData.speciesListData=[]
            if(traitData.orthoList){
              for(let item of traitData.orthoList){
                let speciesName=item.species.commonName
                if(this.speciesList_animal.indexOf(speciesName)==-1){
                  this.speciesList_animal.push(speciesName);
                  this.speciesInfoList.push(item.species)
                }
                traitData.speciesListData.push(this.speciesList_animal.indexOf(speciesName));
              }
            }
          }
        }
       
        console.log("this.traitData:",this.traitData);
  }).finally(()=>{
      this.loading = false;
  })

   this.$axios.get("http://localhost:9401/api/traits",{params:{'classification': 'plant'}})
      .then((response) => {
        this.totalSize_plant = response.data.recordsTotal;
        console.log("response:",response.data);
        this.traitData_plant=response.data.data;
        if(this.traitData_plant.length>0){
          for(let traitData of this.traitData_plant){
            // 增加一个属性保存物种的index，用来显示表格里的icon
            traitData.speciesListData=[]
            if(traitData.orthoList){
              for(let item of traitData.orthoList){
                let speciesName=item.species.commonName
                if(this.speciesList_plant.indexOf(speciesName)==-1){
                  this.speciesList_plant.push(speciesName);
                  this.speciesInfoList.push(item.species)
                }
                traitData.speciesListData.push(this.speciesList_plant.indexOf(speciesName));
              }
            }
          }
        }
        console.log("this.traitData:",this.traitData);
  })
    






    window.addEventListener("scroll", this.watchScroll);
    // this.getTableMaxHeight(); 
    // let _this = this;
    // window.onresize = function () {//用于使表格高度自适应的方法  
    // _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    // }
    this.$axios.get('http://localhost:9401/basic/getSpecies',{params: {speciesType: this.classification=="animal"?1:2}})
    .then(response=>{
      this.querySpeciesList=response.data;
      console.log("speciesdata:",response.data);
    })
    this.$axios.get("http://localhost:9401/api/traits-item",{params: {classification: this.classification=="animal"?1:2}}).then(response=>{
      console.log("traitsItem:",response);
      this.traitItem=response.data
    })
    
  }
}

</script>
