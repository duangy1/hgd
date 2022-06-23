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
      <el-breadcrumb-item>Gene Ontology</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 侧边栏按钮 -->
  
  <el-container style="border: 1px solid #eee;overflow: hidden;" id="trait-container">
  <div style="margin-top:3%">
   <el-button @click="buttonFunction()" ref="button" id="button"></el-button>
    <div class="trait-box trait-infoBox">
      <div style="padding-left:29px">
        <h2 class="trait-title">Gene Ontology Annotation : {{ traitItem.traitName }} | ID: {{ traitItem.traitId }}</h2
        >
        <h2 class="trait-def trait-title" v-if="traitItem.traitDef != ''"
          >Definition: {{ traitItem.definition }}</h2
        >
      </div>
    </div>
    </div>
    <el-card shadow="none" class="border-card">
    <el-tabs v-model="classification" style="margin-top:10px;margin-bottom: 1%;" @tab-click="changeClass">
    <!-- 动物tab -->
    <el-tab-pane label="Animal" name="animal" >
      <!-- <el-card shadow="none" class="tableCard" style="margin-top: 10px"> -->
      <div>
        <div>
            <div class="filt-div">
                <el-select v-model="form.querySpecies" class="filt-spe-select">
                    <el-option
                        v-for="(item,index) in querySpeciesList_animal"
                        :key="index.id"
                        :label="item.commonName"
                        :value="item.taxonId"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search" type="success"  plain id="filter-search" @click="searchFilter()">Search</el-button>
                <el-button icon="el-icon-delete" type="primary"  plain id="filter-search2" @click="clearFilter()">Clear</el-button>


                <el-popover
                  placement="bottom-end"
                  width="200"
                  trigger="manual"
                  class="popbox"
                  v-model="visible_1">
                    <div class="checkbox-box">
                      <el-checkbox v-for="item of speciesList_animal_1" :key="item.commonName" :label="item.commonName" class="chooseColBox" ref="one" v-model="item.checked"></el-checkbox>
                    </div>
                    <div class="botton-wrapper">
                      <el-button size="mini" type="primary"  @click="confirmbutton" class="choose-col-button">confirm</el-button>
                      <el-button size="mini" type="text" plain @click="visible_1 = false" class="choose-col-button">cancel</el-button>
                    </div>
                  <el-button slot="reference" icon="el-icon-s-grid" type="primary" plain id="filterCol" @click="visible_1 = !visible_1" :disabled="speciesList_animal_1.length==0"></el-button>
                </el-popover>



            </div>
          <!-- </el-card> -->
          <div id="wrapper"  >
          <div class="trait-info">
            
      <!-- 使用中的trait表格 -->
      <!-- <el-container style="width:100%;border: 1px solid darkgray;"> -->
        
          <el-table
            :data="traitData_animal"
            class="trait-table"
            row-key="id"
            ref="table"
            :header-cell-style="hiligtDbCols"
            v-loading="loading_animal"
          >
            <el-table-column align="center" class="titleCell" prop="goName" label="Gene Ontology" fixed width="250px"></el-table-column>
            <el-table-column align="center" class="titleCell" prop="topGoId" label="Go Id" fixed width="120px"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene Id" width="220px" fixed>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name"  fixed width="150px" ></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="100px" fixed></el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Ortholog Species Name">
            <template v-for="(item,index) in speciesList_animal_1">
              <el-table-column align="center" :label="item.commonName" :prop="item.commonName" v-if="item.checked" :key="item.commonName">
                  <template slot-scope="scope" >
                    <img
                    :src="orthoIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.speciesListData.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showOrthoInfoTable(scope.row,index)
                  />
                  <!--这个v-if，先判断在当前的cell内有数值，然后再判断值 -->
                  <img
                    :src="sameTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData1.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showGwasInfoTable(scope.row,index)
                  />
                  <img
                    :src="singleTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData2.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showGwasInfoTable(scope.row,index)
                  />
                  </template>
                </el-table-column>
              </template>
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
                    <img :src="singleTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">This icon represent the gene's homolog gene here has trait annotation.</div>  
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
              max-height="500px"
          >
            <el-table-column
                prop="commonName1"
                label="Species1"
                width="180px">
            </el-table-column>
            <el-table-column
                prop="tax1"
                label="Taxon Id1">
            </el-table-column>
            <el-table-column
                prop="hdbId1"
                label="Protein Id1">
            </el-table-column>
            
            <el-table-column
                prop="commonName2"
                label="Species2"
                width="180px">
            </el-table-column>
            <el-table-column
                prop="tax2"
                label="Taxon Id1">
            </el-table-column>
            <el-table-column
                prop="hdbId2"
                label="Protein Id1">
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
                v-loading="gwasLoading"
                max-height="500px"
              >
                <el-table-column prop="geneId" label="Gene Id"></el-table-column>
                <el-table-column prop="goName" label="Go Name"></el-table-column>
                <el-table-column prop="goId" label="Detail Go Id"></el-table-column>
                <el-table-column prop="topGoId" label="Go Id"></el-table-column>
                <el-table-column prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column prop="taxonId" label="Taxon Id"></el-table-column>
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
    <el-tab-pane label="Plant" name="plant">
      <div class="filt-div">
            <el-select v-model="form.querySpecies" class="filt-spe-select">
                <el-option
                    v-for="(item,index) in querySpeciesList_plant"
                    :key="index.id"
                    :label="item.commonName"
                    :value="item.taxonId"
                    >
                </el-option>
            </el-select>
            <el-button icon="el-icon-search" type="success" plain id="filter-search" @click="searchFilter()">Search</el-button>
            <el-button icon="el-icon-delete" type="primary" plain id="filter-search2" @click="clearFilter()">Clear</el-button>

            <el-popover
              placement="bottom-end"
              width="200"
              trigger="manual"
              class="popbox"
              v-model="visible">
                <div class="checkbox-box">
                  <el-checkbox v-for="item of speciesList_plant_1" :key="item.commonName" :label="item.commonName" class="chooseColBox" ref="one" v-model="item.checked"></el-checkbox>
                <!-- <el-checkbox  class="chooseColBox" @change="chooseall" ref="all">Choose All</el-checkbox> -->
                </div>
                <div class="botton-wrapper">
                  <el-button size="mini" type="primary"  @click="confirmbutton" class="choose-col-button">confirm</el-button>
                  <el-button size="mini" type="text" plain @click="visible = false" class="choose-col-button">cancel</el-button>
                </div>
                <el-button slot="reference" icon="el-icon-s-grid" type="primary" plain id="filterCol" @click="visible = !visible" :disabled="speciesList_plant_1.length==0"></el-button>
              </el-popover>

      </div>
      <!-- <div class="wraper"> -->
        
      <div class="trait-info">
   <!-- 使用中的表格 -->
        <el-table
            :data="traitData_plant"
            
            class="trait-table"
            row-key="id"
            v-loading="loading_plant"
            ref="table"
            :header-cell-style="hiligtDbCols"
          >
           <el-table-column align="center" class="titleCell" prop="goName" label="Gene Ontology" fixed width="250px"></el-table-column>
            <el-table-column align="center" class="titleCell" prop="topGoId" label="Go Id" fixed width="120px"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene Id" width="220px" fixed>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name"  fixed width="150px" ></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="100px" fixed></el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Ortholog Species Name">
            <template v-for="(item,index) in speciesList_plant_1">
              <el-table-column align="center" :label="item.commonName" :prop="item.commonName" v-if="item.checked" :key="item.commonName">
            <!-- <el-table-column :label="item" :show-overflow-tooltip="true" :prop="item" v-for="(item,index) in speciesList_plant" :key="index"
              class="imglink" height="100px"> -->
                  <template slot-scope="scope" >
                  <img
                    :src="orthoIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.speciesListData.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showOrthoInfoTable(scope.row,index)
                  />
                  <!--这个v-if，先判断在当前的cell内有数值，然后再判断值 -->
                  <img
                    :src="sameTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData1.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showGwasInfoTable(scope.row,index)
                  />
                  <img
                    :src="singleTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData2.indexOf(index)>-1'
                    style="cursor:pointer !important"
                    @click=showGwasInfoTable(scope.row,index)
                  />
                  </template>
              </el-table-column>
            </template>
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
                    <img :src="singleTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">This icon represent the gene's homolog gene here has trait annotation.</div>  
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
            max-height="500px"
        >
            <el-table-column
                prop="commonName1"
                label="Species1"
                width="180px">
            </el-table-column>
            <el-table-column
                prop="tax1"
                label="Taxon Id1">
            </el-table-column>
            <el-table-column
                prop="hdbId1"
                label="Protein Id1">
            </el-table-column>
            
            <el-table-column
                prop="commonName2"
                label="Species2"
                width="180px">
            </el-table-column>
            <el-table-column
                prop="tax2"
                label="Taxon Id1">
            </el-table-column>
            <el-table-column
                prop="hdbId2"
                label="Protein Id1">
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
                v-loading="gwasLoading"
                max-height="500px"
              >
                <el-table-column align="center" prop="geneId" label="Gene Id"></el-table-column>
                <el-table-column align="center" prop="goName" label="Go Name"></el-table-column>
                <el-table-column align="center" prop="goId" label="Detail Go Id"></el-table-column>
                <el-table-column align="center" prop="topGoId" label="Go Id"></el-table-column>
                <el-table-column align="center" prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column align="center" prop="taxonId" label="Taxon Id"></el-table-column>
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
// import traitOntology from "@/assets/static/traitOntology.json";
import geneOntology from "@/assets/static/geneOntology.json";
import Banner from "@/components/banner.vue";
import FooterBar from "@/components/FooterBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
// import qs from "qs";
// // icons
import sameTraitIcon from "@/assets/img/icon/star.svg";
import singleTraitIcon from "@/assets/img/icon/orange.svg";
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
      singleTraitIcon,
      orthoIcon,
      visible:false,
      visible_1:false,
      filterText: "",
      treedata: geneOntology.tree,
      showSubTableBox:false,
      gwasLoading:true,
      orthoLoading:true,
      drawer:false,
      direction:"ltr",
       // 保存同源物种，用于表格生成列
      speciesList_animal:[],
      speciesList_plant:[],
       // 保存同源物种obj，用于动态选择列
      speciesList_animal_1:[],
      speciesList_plant_1:[],
      traitData_animal: [],
      traitData_plant:[],
      orthoTableData: [],
      value: "",
      traitItem:{
        traitName:"protein phosphorylation",
        traitId:"GO:0006468",
        definition:"The process of introducing a phosphate group on to a protein. [GOC:hb]",
        classification:"biological_process"
      },
      currentPage4: 1,
      totalSize_animal: 0,
      totalSize_plant: 0,
      form: {
        querySpecies:"",
      },
      pageSize: 10,
      gwasInfoData:[],
      loading:true,
      loading_animal:true,
      loading_plant:true,
      showOrthoSubTable:false,
      classification:"animal",
      navBarFixed:"false",
      showTraitSameIcon:false,
       // 从接口获取所有有trait数据的物种，用于物种选择，提交search
      querySpeciesList_animal:[],
      querySpeciesList_plant:[],
      querySpeciesList:[],
    };
  },

  methods: {
   
    changeClass(){
        this.speciesList=[];
        // this.loading = true;
        this.showOrthoSubTable=false;
        this.showSubTableBox=false;
        this.$refs['table'].doLayout();
        this.form.querySpecies='';
    },

    confirmbutton(){
    this.visible_1 = false;
    this.visible = false;
    this.$refs.table.doLayout();
   },
    handleSizeChange(val){
      this.loading = true;
      this.pageSize = val;
      this.getGoData(this.traitItem.traitId,this.classification,this.pageSize,1,this.form.querySpecies)
    },
    handleCurrentChange(val) {
      console.log("this.classification:",this.classification);
      if(this.classification=="animal"){
        this.loading_animal=true;
      }else{
        this.loading_plant=true;}
      this.currentPage4 = val;

      this.getGoData(this.traitItem.traitId,this.classification,this.pageSize,val,this.form.querySpecies)

    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getInfoByTrait(value, data) {
      console.log("value, data:",value, data);
      this.traitItem.traitName=value.label;
      this.traitItem.traitId=value.goId;
      this.traitItem.definition=value.definition;
      this.traitItem.classification=value.topClass;
      // if(this.classification=="animal"){
        this.loading_animal=true;
        this.getGoData(value.goId,"animal",this.pageSize,1,this.form.querySpecies)

      // }else{
        this.loading_plant=true;
        this.getGoData(value.goId,"plant",this.pageSize,1,this.form.querySpecies)

      // }
      this.currentPage4=1;
      data.checked = true;
      this.showSubTableBox=false;
      this.showOrthoSubTable=false;
     
    },
    
  showOrthoInfoTable(rowdata,index){
    // console.log("orthoList:",orthoList);
    this.showOrthoSubTable = true;
    this.showSubTableBox=false;
    this.orthoLoading=false;
    

    let ortholist=[];
    rowdata.speciesListData.forEach((id,idx)=>{
    if(id==index){
        let item=rowdata.ortholist[idx]
        ortholist.push(item)
    }})

    this.orthoTableData=ortholist;
    console.log("ortholist:",ortholist);
  },
 
  // 表格高度自适应
  getTableMaxHeight(){
      this.$nextTick(function () {
      let box = document.querySelector(".trait-info").attributes
      let box_clientHeight = box[0].ownerElement.clientHeight;
      this.tableHeight = box_clientHeight - 100;
    })
  },
  buttonFunction(){
    this.drawer = true;
   },
  showGwasInfoTable(rowValue,index){
    
    // scope.row.gwasId,scope.row.taxonId,scope.row.orthoList[scope.row.speciesListData.indexOf(index)]
    // let gwasid=rowValue.gwasId
    // let taxid=rowValue.gwasOrgid
    // let orthoList=rowValue.ortholist[rowValue.speciesListData.indexOf(index)]
    // console.log(rowValue,index);
    let ortholist=[];
    let hdblist=[rowValue.hdbId]
    rowValue.speciesListData.forEach((id,idx)=>{
    if(id==index){
        let item=rowValue.ortholist[idx]
        ortholist.push(item)
        hdblist.push(item.hdbId)
    }})
    this.orthoTableData=ortholist;
    // console.log(rowValue,index,ortholist);
    this.showSubTableBox=true;
    this.gwasInfoData=[];
    hdblist.forEach(hdbid=>{
      this.$axios.get("http://localhost:9401/api/gene-go",{params:{'hdbid': hdbid}})
        .then(response=>{
          console.log("gwas response:",response);
          this.gwasInfoData=this.gwasInfoData.concat(response.data);
          this.gwasLoading=false;
        })
    })
    
    this.showOrthoSubTable = true;
    this.orthoLoading=false;
    this.orthoTableData=ortholist;
    // console.log(ortholist);
   },
    hiligtDbCols({rowIndex}){
      if(rowIndex===1){
          return "writing-mode: vertical-lr; !important;text-align:right"
      }
      
    },
    iconClass(data,index){
      let classFT=data.speciesListData.indexOf(index)>-1?data.ortholist[data.speciesListData.indexOf(index)].orthoTraitName.indexOf(data.traitName)>-1:false
      return classFT
    },
   
    searchFilter(){
      // this.$axios.get("http://localhost:9401/api/traits/ortho-data",{params:{"orthoSpecies":value.orthoSpecies,"querySpecies":value.querySpecies,"traitId":traitId}})
      // .then(response=>{
      //     console.log("searchFilter:",response);
      // })
      if(this.classification=="animal"){
        this.loading_animal=true;
      }else{
        this.loading_plant=true;
      }
      this.getGoData(this.traitItem.traitId,this.classification,this.pageSize,1,this.form.querySpecies)

    },
    clearFilter(){
      if(this.classification=="animal"){
        this.loading_animal=true;
      }else{
        this.loading_plant=true;
      }
      this.getGoData(this.traitItem.traitId,"animal");
      this.getGoData(this.traitItem.traitId,"plant");
      this.form.querySpecies=""
    },
    async showTableIcon(data,classss){
      if(classss == "plant"){
        this.speciesList_plant=[];
        this.speciesList_plant_1=[];
        if(data.length>0){
          for(let varData of data){
            // 增加一个属性保存物种的index，用来显示表格里的icon
            varData.speciesListData=[];
            // 增加一个属性，保存显示有trait数据的情况
            varData.traitListData1=[];
            varData.traitListData2=[];
            if(varData.ortholist){
              for(let item of varData.ortholist){
                let speciesName=item.commonName
                let indexoftablecell=this.speciesList_plant.indexOf(speciesName)
                if(indexoftablecell==-1){
                  this.speciesList_plant.push(speciesName);
                  item.species.checked=true;
                  this.speciesList_plant_1.push(item.species);
                }
                indexoftablecell=this.speciesList_plant.indexOf(speciesName)
                varData.speciesListData.push(indexoftablecell);
                if(item.goName !== null){
                  // 同源数据包含注释信息
                  varData.traitListData2.push(indexoftablecell);
                  let orthogoNamelist=item.goName.split(',');
                  let vardataNamelist=varData.goName.split(',');
                  for(let name of vardataNamelist){
                    if(orthogoNamelist.indexOf(name)>-1){
                      // 包含相同的注释信息
                        varData.traitListData1.push(indexoftablecell);
                    }
                  }
                  // if(item.goName==varData.traitName){
                  //     varData.traitListData1.push(this.speciesList_plant.indexOf(speciesName))
                  // }
                }
              }
            }
          }
          this.$refs['table'].doLayout();
        }
    }else{
      this.speciesList_animal=[];
      this.speciesList_animal_1=[];
        if(data.length>0){
          for(let varData of data){
            // 增加一个属性保存物种的index，用来显示表格里的icon
            varData.speciesListData=[];
            // 增加一个属性，保存显示有trait数据的情况
            varData.traitListData1=[];
            varData.traitListData2=[];
            if(varData.ortholist){
              for(let item of varData.ortholist){
                let speciesName=item.commonName;
                if(this.speciesList_animal.indexOf(speciesName)==-1){
                  this.speciesList_animal.push(speciesName);
                  item.species.checked=true;
                  this.speciesList_animal_1.push(item.species);
                }
                let indexoftablecell=this.speciesList_animal.indexOf(speciesName)
                varData.speciesListData.push(indexoftablecell);
                if(item.goName !== null){
                  varData.traitListData2.push(indexoftablecell)
                  let orthogoNamelist=item.goName.split(',');
                  let vardataNamelist=varData.goName.split(',');
                  for(let name of vardataNamelist){
                    if(orthogoNamelist.indexOf(name)>-1){
                        varData.traitListData1.push(indexoftablecell)
                    }
                  }
                  // if(item.goName==varData.traitName){
                  //     varData.traitListData1.push(this.speciesList_plant.indexOf(speciesName))
                  // }
                }
              }
            }
          }
        }
    }
    return Promise.resolve(data);
    },
    // 主要获取数据的方法
    // 根据动植物分开获取
    async getGoData(goName,classification,pagesize,pagenum,speciesName){
      console.log("params:",goName,classification,pagesize,pagenum,speciesName);
      this.$axios.get("http://localhost:9401/api/godata",{params:{'topGoid':goName,'classification':classification,'taxonid':speciesName,'length':pagesize,'pageNo':pagenum}})
      .then((response) => {
        console.log("response:",response);
        if(classification=="animal"){
          this.totalSize_animal = response.data.recordsTotal;
          this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_animal=res;this.loading_animal=false;});
        
        }else{
          this.totalSize_plant = response.data.recordsTotal;
          this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_plant=res; this.loading_plant=false;});
        }

      })
      .finally(()=>{ return Promise.resolve()})
   
    },
    // 根据当前classification判断获取动物或植物列表
    getSpecies(speciesType){
      this.$axios.get('http://localhost:9401/api/species-go',{params: {speciesType: speciesType}})
        .then(response=>{
          // 2是植物，1是动物
          if(speciesType==2){
            this.querySpeciesList_plant=response.data;
          }else{
            this.querySpeciesList_animal=response.data;
          }
      })
    },

  },
  

  mounted() {
    this.loading_animal = true;
    this.loading_plant=true;
    console.log();
    this.getGoData(this.traitItem.traitId,"animal",this.pageSize,1,"")
    this.getGoData(this.traitItem.traitId,"plant",this.pageSize,1,"")
    // async getGoData(traitId,classification,pagesize,pagenum,speciesName){

    window.addEventListener("scroll", this.watchScroll);
    // this.getTableMaxHeight(); 
    // let _this = this;
    // window.onresize = function () {//用于使表格高度自适应的方法  
    // _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    // }
   // 获取动植物名称列表，用于下拉选择框
    this.getSpecies(2)
    this.getSpecies(1)
    
     window.addEventListener("scroll", this.watchScroll);
      this.getTableMaxHeight(); 
      let _this = this;
      window.onresize = function () {//用于使表格高度自适应的方法  
      _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    }
    
  }
}

</script>
