<template>
  <div>
    <HeaderBar />
    <Banner />
    <!-- <Navigator /> -->
    
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
  <div class="button-wrapper">
   <el-button @click="buttonFunction()" ref="button" id="button"></el-button>
   <!-- <div class="button-text">Trait Ontology</div> -->
  </div>
    <div class="trait-box trait-infoBox">
      <div style="padding-left:29px;margin-top: -15px;">
        <h2 class="trait-title">Trait Name : {{ traitItem.traitName }} | ID: {{ traitItem.traitId }}</h2
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
                        :value="item.commonName"
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
            <el-table-column align="center" class="titleCell" prop="traitName" label="Trait Name" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene Id" width="220px" fixed>
              <template slot-scope="scope">
                
                <a :href="'/gene-detail?hdbId='+scope.row.hdbId+'&taxonId='+scope.row.taxonId" target='_blank'>
                    {{ scope.row.geneId }}
                </a>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="160px" fixed></el-table-column>
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
                    :src="singleTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData2.indexOf(index)>-1 '
                    style="cursor:pointer !important"
                    @click=showGwasInfoTable(scope.row,index)
                  />
                   <img
                    :src="sameTraitIcon"
                    min-width="70"
                    height="70"
                    class="iconImg"
                    v-if='scope.row.traitListData1.indexOf(index)>-1'
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
                      <div class="note-info">This icon represents the gene has homolog gene informations here.</div>
          </div>
          <div style="display: flex;">
                    <img :src="singleTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">This icon represents the gene's homolog gene here has trait annotation.</div>  
          </div>
          <div style="display: flex;">
                    <img :src="sameTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">This icon represents the gene's homolog gene here has same trait annotation.</div>  
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
      <el-divider class="divider"></el-divider>
          <div class="title-box">
            <h2 class="trait-sub-title">Ortholog Gene Detail Information</h2>
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
                prop="commonName"
                label="Species">
            </el-table-column>
            <el-table-column
                prop="tax"
                label="Taxon Id1">
            </el-table-column>
            <el-table-column
                prop="ensemblGeneId"
                label="Ensembl Id">
            </el-table-column>
              <el-table-column
              prop="geneSymbol"
              label="Gene Symbol">
            </el-table-column>
            <el-table-column
                prop="hdbId"
                label="Protein Id">
                <template slot-scope="scope" v-if="scope.row.hdbId.slice(0,2)!=='EN'">
              
                  <a :href="'https://www.uniprot.org/uniprot/'+scope.row.hdbId" target='_blank'>
                      {{ scope.row.hdbId }}
                  </a>
                </template>
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
                <el-table-column prop="topTrait" label="Trait Name" align="center"></el-table-column>
                <el-table-column prop="varId" label="Var Id" align="center">
                  <template slot-scope="scope">
                    <a v-bind:href="'https://ngdc.cncb.ac.cn/gvm/snp/getSNPDetail?snpname='+scope.row.varId+'&chrom='+scope.row.chrom+'&position='+scope.row.endPos+'&orgId='+scope.row.orgId" target='_blank'>
                      {{ scope.row.varId }}
                    </a>
                  </template>
                </el-table-column>
                <el-table-column prop="traitName" label="Sub Trait Name" align="center"></el-table-column>
                <el-table-column prop="speciesCommonName" label="Species" align="center"></el-table-column>
                <el-table-column prop="pmid" label="Pubmed Id" align="center">
                  <template slot-scope="scope">
                    <a v-bind:href="'https://pubmed.ncbi.nlm.nih.gov/'+scope.row.pmid" target='_blank'>
                      {{ scope.row.pmid }}
                    </a>
                  </template>
                </el-table-column>
                <el-table-column prop="pvalue" label="Pvalue" align="center"></el-table-column>
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
                    :value="item.commonName"
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
            <el-table-column align="center" class="titleCell" prop="traitName" label="Trait Name" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene Id" width="220px" fixed>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="160px" fixed></el-table-column>
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
                      v-if='!iconClass(scope.row,index) && scope.row.speciesListData.indexOf(index)>-1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row,index)
                    />
                    <!--这个v-if，先判断在当前的cell内有数值，然后再判断值 -->
                     <img
                      :src="singleTraitIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row.traitListData2.indexOf(index)>-1'
                      style="cursor:pointer !important"
                      @click=showGwasInfoTable(scope.row,index)
                    />
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
            </template>
            </el-table-column>
          </el-table>
        <div style="position: absolute;float: left;padding-top: 0.7%;">
        <div style="display: flex;">
                  <img :src="orthoIcon"
                    style="margin-right: 6px;min-width=70px;height=70px;"
                    class="iconImg" />
                    <div class="note-info">This icon represents the gene has homolog gene informations here.</div>
        </div>
        <div style="display: flex;">
                    <img :src="singleTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">This icon represents the gene's homolog gene here has trait annotation.</div>  
          </div>
        <div style="display: flex;">
                  <img :src="sameTraitIcon"   
                    style="margin-right: 6px;min-width=70px;height=70px;"
                    class="iconImg" /><div class="note-info">This icon represents the gene's homolog gene here has a same trait annotation.</div>  
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
            <h2 class="trait-sub-title">Ortholog Gene Detail Information</h2>
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
                prop="commonName"
                label="Species">
            </el-table-column>
            <el-table-column
                prop="tax"
                label="Taxon Id1">
            </el-table-column>
            <el-table-column
                prop="ensemblGeneId"
                label="Ensembl Id">
            </el-table-column>
              <el-table-column
              prop="geneSymbol"
              label="Gene Symbol">
            </el-table-column>
            <el-table-column
                prop="hdbId"
                label="Protein Id">
                <template slot-scope="scope" v-if="scope.row.hdbId.slice(0,2)!=='EN'">
              
                  <a :href="'https://www.uniprot.org/uniprot/'+scope.row.hdbId" target='_blank'>
                      {{ scope.row.hdbId }}
                  </a>
                </template>
            </el-table-column>
              
          </el-table>
    <!-- gwas detail info表格 -->
      <div class="sub-trait-box" v-if="showSubTableBox">
        <div  class="title-box" style="padding-left:1.5%;margin-bottom:1.3%">
          <h3 class="trait-sub-title">Gwas Detail Information</h3>
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
                <el-table-column prop="topTrait" label="Trait Name" align="center"></el-table-column>
                <el-table-column prop="varId" label="Var Id" align="center">
                  <template slot-scope="scope">
                    <a v-bind:href="'https://ngdc.cncb.ac.cn/gvm/snp/getSNPDetail?snpname='+scope.row.varId+'&chrom='+scope.row.chrom+'&position='+scope.row.endPos+'&orgId='+scope.row.orgId" target='_blank'>
                      {{ scope.row.varId }}
                    </a>
                  </template>
                </el-table-column>
                <el-table-column prop="traitName" label="Sub Trait Name" align="center"></el-table-column>
                <el-table-column prop="speciesCommonName" label="Species" align="center"></el-table-column>
                <el-table-column prop="pmid" label="Pubmed Id" align="center">
                  <template slot-scope="scope">
                    <a v-bind:href="'https://pubmed.ncbi.nlm.nih.gov/'+scope.row.pmid" target='_blank'>
                      {{ scope.row.pmid }}
                    </a>
                  </template>
                </el-table-column>
                <el-table-column prop="pvalue" label="Pvalue" align="center"></el-table-column>
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
// import Navigator from "@/components/navigator.vue";
import traitOntology from "@/assets/static/traitOntology.json";
import Banner from "@/components/banner.vue";
import FooterBar from "@/components/FooterBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
// import qs from "qs";
// // icons
import sameTraitIcon from "@/assets/img/icon/star.svg";
import orthoIcon from "@/assets/img/icon/huafu.svg";
import singleTraitIcon from "@/assets/img/icon/orange.svg";
// import "@/assets/css/traits.css";
export default {
  name: "Traits",
  components: {
    // Navigator,
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
      singleTraitIcon,
      visible:false,
      visible_1:false,
      filterText: "",
      treedata: traitOntology.tree,
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
        traitName:"growth and meat production trait",
        traitId:"APTO:0000006",
        traitDefID:"8971",
        definition:"any measurable or observable characteristics related to animal growth and meat production quantity and quality",
        classification:"animal"
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
      // this.loading = true;
      if(this.classification=="animal"){this.loading_animal=true;}else{this.loading_plant=true}
      this.pageSize = val;
      this.getTraitData(this.traitItem.traitDefID,this.traitItem.classification,this.pageSize,1,this.form.querySpecies)
    },
    handleCurrentChange(val) {
      if(this.classification=="animal"){
        this.loading_animal=true;
        // traitId,classification,pagesize,pagenum,speciesName
        this.getTraitData(this.traitItem.traitDefID,this.traitItem.classification,this.pageSize,val,this.form.querySpecies)
      }else{
        this.loading_plant=true;
        this.getTraitData(this.traitItem.traitDefID,this.traitItem.classification,this.pageSize,val,this.form.querySpecies)
      }
      this.currentPage4 = val;

    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getInfoByTrait(value, data) {
      this.traitItem.traitName=value.Name;
      this.traitItem.traitId=value.traitID;
      this.traitItem.traitDefID=value.traitDefID;
      this.traitItem.definition=value.Definitions;
      this.traitItem.classification=value.classification;
      if(value.classification=="animal"){
        this.loading_animal=true;
        this.getTraitData(value.traitDefID,value.classification,this.pageSize,1,this.form.querySpecies);

      }else{
        this.loading_plant=true;
        this.getTraitData(value.traitDefID,value.classification,this.pageSize,1,this.form.querySpecies)

      }
      this.currentPage4=1;
      data.checked = true;
      this.classification=value.classification;

    },
    
  showOrthoInfoTable(rowData,index){
    // console.log("rowData,index:",rowData,index);
    // rowData.orthoList[rowData.speciesListData.indexOf(index)];
    let ortholist=[];
    rowData.speciesListData.forEach((id,idx)=>{
      if(id==index){
        ortholist.push(rowData.orthoList[idx])
    }})
    // console.log("index list:",ortholist);

    // rowData.
    this.showOrthoSubTable = true;
    this.showSubTableBox=false;
    this.orthoLoading=false;

   this.orthoTableData=[]
    ortholist.forEach(item=>{
      let hdbid=item.hdbId;
      this.$axios.get("http://localhost:9401/api/gene-detail-ortho",{params:{"hdbId":hdbid}}).then((res)=>{
        console.log("gene res:",res);
        item.ensemblGeneId=res.data.ensemblGeneId;
        item.geneSymbol=res.data.geneSymbol
        this.orthoTableData.push(item)
        this.orthoLoading=false;
      })
    })

    // this.orthoTableData=ortholist;
    console.log(this.orthoTableData);
    // 应该发送请求去genebasicinfo查具体的基因相关信息

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
    console.log("rowValue,index:",rowValue,index);
    // 获取当前位置，两物种间同源数据
    let ortholist=[];
    rowValue.speciesListData.forEach((id,idx)=>{
    if(id==index){
        let item=rowValue.orthoList[idx]
        console.log("item:",item);
        ortholist.push(item)
    }})
    
    this.showOrthoSubTable = true;
    this.orthoLoading=false;
    this.orthoTableData=ortholist;
    this.showSubTableBox=true;
    this.gwasLoading=true;
    // 左侧基因的接口数据
    let gwasid1=rowValue.gwasId
    let taxid1=rowValue.gwasOrgid;
    
    // 左侧基因请求接口
    this.$axios.get("http://192.168.164.15:9500/hdb/gwas/gwasids?gwasId="+gwasid1+"&organismId="+taxid1+"&offset=0&pagesize=10&total=10")
    .then(response=>{
      console.log("gwas response:",response);
      let datalist=response.data;
      for(let item of datalist){
        item.topTrait=rowValue.traitName;
      }
      this.gwasInfoData=datalist; 
      this.gwasInfoData.topTrait=rowValue.traitName;
      
    })
    // 右侧基因数据
    // 循环当前同源数据，请求后端得到接口所需要的参数
    ortholist.forEach((item)=>{
      let hdbid=item.hdbId;
      // let topTrait=item.traitName;
      console.log("item.hdbId:",item.hdbId);
      this.$axios.get("http://localhost:9401/api/traits-gwas-info",{params:{'hdbId': hdbid}}).then(res=>{
        console.log("res:",res);
        if(res.data.length !==0){
          let gwasid2=res.data.gwasId;
          let taxid2=res.data.gwasOrgid;
          this.$axios.get("http://192.168.164.15:9500/hdb/gwas/gwasids?gwasId="+gwasid2+"&organismId="+taxid2+"&offset=0&pagesize=10&total=10")
            .then(response=>{
              console.log("gwas response22:",response);
              let datalist=response.data;
              if(datalist.length>0){
                for(let idata of datalist){
                  idata.topTrait=item.traitName;
                }
                // let data=response.data;
                // data.topTrait=item.traitName;
                this.gwasInfoData=this.gwasInfoData.concat(datalist)
                this.gwasLoading=false;
                console.log("this.gwasInfoData:",this.gwasInfoData);
              }
          })
        }
      })
      
    })
    
    // this.$axios.get("http://localhost:9401/api/traits-gwas-info",{params:{'hdbid': hdblist}}).then(res=>{
    //     console.log("res:",res);
    // })
   },
   showSingleGwasInfoTable(rowValue,index){
    console.log("rowValue,index:",rowValue,index);
    // 获取当前位置，两物种间同源数据
    let ortholist=[];
    rowValue.speciesListData.forEach((id,idx)=>{
    if(id==index){
        let item=rowValue.orthoList[idx]
        console.log("item:",item);
        ortholist.push(item)
    }})
    
    this.showOrthoSubTable = true;
    this.orthoLoading=false;
    this.orthoTableData=ortholist;
    this.showSubTableBox=true;
    this.gwasLoading=true;
   this.gwasInfoData=[];
    // 右侧基因数据
    // 循环当前同源数据，请求后端得到接口所需要的参数
    ortholist.forEach((item)=>{
      let hdbid=item.hdbId;
      // let topTrait=item.traitName;
      console.log("item.hdbId:",item.hdbId);
      this.$axios.get("http://localhost:9401/api/traits-gwas-info",{params:{'hdbId': hdbid}}).then(res=>{
        console.log("res:",res);
        if(res.data.length !==0){
          let gwasid2=res.data.gwasId;
          let taxid2=res.data.gwasOrgid;
          this.$axios.get("http://192.168.164.15:9500/hdb/gwas/gwasids?gwasId="+gwasid2+"&organismId="+taxid2+"&offset=0&pagesize=10&total=10")
            .then(response=>{
              console.log("gwas response22:",response);
              let datalist=response.data;
              for(let idata of datalist){
                idata.topTrait=item.traitName;
              }
              // let data=response.data;
              // data.topTrait=item.traitName;
              this.gwasInfoData=this.gwasInfoData.concat(datalist)
              this.gwasLoading=false;
              console.log("this.gwasInfoData:",this.gwasInfoData);
          })
        }
      })
      
    })
   },
    hiligtDbCols({rowIndex}){
      if(rowIndex===1){
          return "writing-mode: vertical-lr; !important;text-align:right"
      }
      
    },
    iconClass(data,index){
      // if(data.speciesListData.indexOf(index)>-1){
        data.speciesListData.forEach((id,idx)=>{
          // console.log("id,index:",id,index);
          if(id==index){
            
            if( data.orthoList[idx].traitName!=null ){
              // console.log("data.orthoList[idx]:",data.orthoList[idx].traitName,data.traitName);
              let traitlist=data.orthoList[idx].traitName.split(",")
              if(traitlist.indexOf(data.traitName)>-1){
                console.log(1,"idx:",idx,"species:",data.orthoList[idx],"trait data:",data.traitName);
                console.log("data.orthoList[idx]:",data.orthoList[idx].traitName,data.traitName);
                return 1;
              }else{
                console.log(2);
                console.log("data.orthoList[idx]:",data.orthoList[idx],data.traitName);
              return 2;
            }
          }else{
            return false
          }
          }else{
            return false
          }
        })
      // }
    },
   
    searchFilter(){
      if(this.classification=="animal"){
        this.loading_animal=true;
      }else{
        this.loading_plant=true;
      }
      this.getTraitData(this.traitItem.traitDefID,this.classification,this.pageSize,1,this.form.querySpecies)

    }, 
    clearFilter(){
      if(this.classification=="animal"){
        this.loading_animal=true;
      }else{
        this.loading_plant=true;
      }
      this.getTraitData(this.traitItem.traitDefID,"animal")
      this.getTraitData(this.traitItem.traitDefID,"plant")
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
            if(varData.orthoList){
              for(let item of varData.orthoList){
                let speciesName=item.species.commonName
                if(this.speciesList_plant.indexOf(speciesName)==-1){
                  this.speciesList_plant.push(speciesName);
                  item.species.checked=true;
                  this.speciesList_plant_1.push(item.species);
                }
                varData.speciesListData.push(this.speciesList_plant.indexOf(speciesName));
                if(item.traitName !== null){
                  varData.traitListData2.push(this.speciesList_plant.indexOf(speciesName))
                  if(item.traitName==varData.traitName){
                      varData.traitListData1.push(this.speciesList_plant.indexOf(speciesName))
                  }
                }
              }
            }
          }
          this.$refs['table'].doLayout();
        }
    }else{
      this.speciesList_animal=[];
      
      console.log("this.speciesList_plant:",this.speciesList_plant);
      this.speciesList_animal_1=[];
        if(data.length>0){
          for(let varData of data){
            // 增加一个属性保存物种的index，用来显示表格里的icon
            varData.speciesListData=[];
            varData.traitListData1=[];
            varData.traitListData2=[];
            if(varData.orthoList){
              for(let item of varData.orthoList){
                let speciesName=item.species.commonName;
                if(this.speciesList_animal.indexOf(speciesName)==-1){
                  this.speciesList_animal.push(speciesName);
                  item.species.checked=true;
                  this.speciesList_animal_1.push(item.species);
                }
                varData.speciesListData.push(this.speciesList_animal.indexOf(speciesName));
                if(item.traitName !== null){
                  // 有trait但不相同
                   varData.traitListData2.push(this.speciesList_animal.indexOf(speciesName))
                  if(item.traitName==varData.traitName){
                    // 有相同trait
                      varData.traitListData1.push(this.speciesList_animal.indexOf(speciesName))
                  }
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
    async getTraitData(traitId,classification,pagesize,pagenum,speciesName){
      console.log("params:",traitId,classification,pagesize,pagenum,speciesName);
      this.$axios.get("http://localhost:9401/api/traits",{params:{'classification': classification,'traitId':traitId,'length':pagesize,'pageNo':pagenum,'speciesName':speciesName}})
      .then((response) => {
        console.log("response:",response);
        if(classification=="animal"){
          this.totalSize_animal = response.data.recordsTotal;
          // this.speciesList_animal=response.data.data[0].headerList;
          this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_animal=res;this.loading_animal=false;});
        
        }else{
          this.totalSize_plant = response.data.recordsTotal;
          // this.speciesList_plant=response.data.data[0].headerList;
          this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_plant=res; this.loading_plant=false;});
        }

      })
      .finally(()=>{ return Promise.resolve()})
   
    },
    // 根据当前classification判断获取动物或植物列表
    getSpecies(speciesType){
      this.$axios.get('http://localhost:9401/api/species-trait',{params: {speciesType: speciesType}})
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
 
    this.getTraitData(this.traitItem.traitDefID,"animal");
    this.getTraitData(this.traitItem.traitDefID,"plant");

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
