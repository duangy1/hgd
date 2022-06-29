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
      <el-breadcrumb-item>Variant</el-breadcrumb-item>
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
        <h2 class="trait-title">Consequence Type : {{ varItem.varName }} </h2>
      </div>
    </div>
    </div>
    <el-card shadow="none" class="border-card">
    <el-tabs v-model="activeName" style="margin-top:10px;margin-bottom: 1%;" @tab-click="changeClass">
    <!-- 动物tab -->
    <el-tab-pane label="Animal" name="animal" >
      <!-- <el-card shadow="none" class="tableCard" style="margin-top: 10px"> -->
      <div>
        <div>
            <div class="filt-div">
                <el-select v-model="form.querySpecies" class="filt-spe-select">
                    <el-option
                        v-for="(item,index) in querySpeciesList_animal"
                        :key="index"
                        :label="item"
                        :value="item"
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
            <el-table-column align="center" class="titleCell" prop="varName" label="Consequence Type" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene Id" width="220px" fixed>
               <template slot-scope="scope">
                <a :href="'/gene-detail?hdbId='+scope.row.hdbid+'&taxonId='+scope.row.taxonId">
                    {{ scope.row.geneId }}
                </a>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="160px" fixed></el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Homolog Species Name">
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
                    v-if='scope.row.traitListData2.indexOf(index)>-1'
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
                      <div class="note-info">exists homolog gene</div>
          </div>
          <div style="display: flex;">
                    <img :src="singleTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homolog gene with variant annotation</div>  
          </div>
          <div style="display: flex;">
                    <img :src="sameTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homolog gene with same variant annotation</div>  
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
            <h2 class="trait-sub-title">Homolog Gene Detail Information</h2>
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
                  label="Species"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="tax"
                  label="Taxon Id1"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="ensemblGeneId"
                  label="Ensembl Id"
                  align="center">
              </el-table-column>
               <el-table-column
                prop="geneSymbol"
                label="Gene Symbol"
                align="center">
              </el-table-column>
              <el-table-column
                  prop="hdbId"
                  label="Protein Id"
                  align="center">
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
          <h3 class="trait-sub-title">Variants Detail Information</h3>
        </div>
        <div id="trait-info">
            <!-- <el-card shadow="none" class="gwasDetailCard"> -->
              <el-table 
                :data="gwasInfoData"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="500"
                v-loading="gwasLoading"
              >
              <el-table-column
                    prop="gene.genename"
                    label="Gene Id"
                    align="center">
                  <!-- <template slot-scope="scope">
                    <a v-bind:href="'https://ngdc.cncb.ac.cn/gvm/snp/getGeneDetail?param1='+scope.row.genename+'&orgId'+scope.row.orgId" target='_blank'>
                      {{ scope.row.genename }}
                    </a>
                  </template> -->

              </el-table-column>
              <el-table-column
                  prop="rsnpId"
                  label="Var Id"
                  align="center">
                  <!-- <template slot-scope="scope">
                  <a v-bind:href="'https://ngdc.cncb.ac.cn/gvm/snp/getSNPDetail?snpname='+scope.row.rsnpId+'&chrom='+scope.row.chrom+'&position='+scope.row.snpStart+'&orgId='+scope.row.gwasOrgId" target='_blank'>
                    {{ scope.row.snpId }}
                  </a>
                </template> -->
              </el-table-column>
                <el-table-column
                    prop="position"
                    label="Position"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="allele"
                    label="Allele"
                    align="center"
                >
                </el-table-column>
              
                <el-table-column
                    prop="maf"
                    label="Maf"
                    align="center">
                </el-table-column>
              
                <el-table-column
                    prop="snpClassId"
                    label="Class"
                    width="80px"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="gene.conseqtype"
                    label="Consequence Type/Effect"
                    width="220px"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="gene.genealias"
                    label="Gene Symbol"
                    width="120px"
                    align="center">
                </el-table-column>
                
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
                    :key="index"
                    :label="item"
                    :value="item"
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
            <el-table-column align="center" class="titleCell" prop="varName" label="Consequence Type" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center" prop="geneId" label="Gene Id" width="220px" fixed>
               <template slot-scope="scope">
                <a :href="'/gene-detail?hdbId='+scope.row.hdbid+'&taxonId='+scope.row.taxonId">
                    {{ scope.row.geneId }}
                </a>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="160px" fixed></el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Homolog Species Name">
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
                    <img
                      :src="singleTraitIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row.traitListData2.indexOf(index)>-1'
                      style="cursor:pointer !important"
                      @click=showGwasInfoTable(scope.row,index)
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
            </template>
            </el-table-column>
          </el-table>
        <div style="position: absolute;float: left;padding-top: 0.7%;">
        <div style="display: flex;">
                    <img :src="orthoIcon"
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" />
                      <div class="note-info">exists homolog gene</div>
          </div>
          <div style="display: flex;">
                    <img :src="singleTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homolog gene with trait annotation</div>  
          </div>
          <div style="display: flex;">
                    <img :src="sameTraitIcon"   
                      style="margin-right: 6px;min-width=70px;height=70px;"
                      class="iconImg" /><div class="note-info">exists homolog gene with same trait annotation</div>  
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
            <h2 class="trait-sub-title">Homolog Gene Detail Information</h2>
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
                label="Species"
                align="center">
            </el-table-column>
            <el-table-column
                prop="tax"
                label="Taxon Id1"
                align="center">
            </el-table-column>
            <el-table-column
                prop="ensemblGeneId"
                label="Ensembl Id"
                align="center">
            </el-table-column>
              <el-table-column
              prop="geneSymbol"
              label="Gene Symbol"
              align="center">
            </el-table-column>
            <el-table-column
                prop="hdbId"
                label="Protein Id"
                align="center">
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
          <h3 class="trait-sub-title">Variants Detail Information</h3>
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
                <el-table-column align="center" prop="geneName" label="Gene Id"></el-table-column>
                <el-table-column align="center" prop="varId" label="Var Id"></el-table-column>
                <el-table-column align="center" prop="varName" label="Consequence Type"></el-table-column>
                <el-table-column align="center" prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column align="center" prop="pmid" label="Pubmed Id"></el-table-column>
                <el-table-column align="center" prop="pvalue" label="Pvalue"></el-table-column>
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
import varOntology from "@/assets/static/varOntology.json";
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
      treedata: varOntology.tree,
      showSubTableBox:false,
      gwasLoading:true,
      orthoLoading:true,
      activeName: 'animal',
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
      varItem:{
        varName:"frameshift_variant"
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
   
    changeClass(tab){
        this.speciesList=[];
        this.showOrthoSubTable=false;
        this.showSubTableBox=false;
        this.classification=tab.label;
        this.$refs['table'].doLayout();
    },

    confirmbutton(){
    this.visible_1 = false;
    this.visible = false;
    this.$refs.table.doLayout();
   },
    handleSizeChange(val){
      this.loading = true;
      this.pageSize = val;
      this.getTraitData(this.varItem.varName,this.classification,this.pageSize,1,this.form.querySpecies)
    },
    handleCurrentChange(val) {
      if(this.classification=="animal"){
        this.loading_animal=true;
        // traitId,classification,pagesize,pagenum,speciesName
        this.getTraitData(this.varItem.varName,this.classification,this.pageSize,val,this.form.querySpecies)
      }else{
        this.loading_plant=true;
        this.getTraitData(this.varItem.varName,this.classification,this.pageSize,val,this.form.querySpecies)
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
      console.log("value, data:",value, data);
      this.varItem.varName=value.label;
      this.loading_animal=true;
      this.loading_plant=true;
      this.getTraitData(value.label,"animal",this.pageSize,1,this.form.querySpecies)
      this.getTraitData(value.label,"plant",this.pageSize,1,this.form.querySpecies)
      this.currentPage4=1;
      data.checked = true;
      this.showSubTableBox=false;
      this.showOrthoSubTable=false;
    },
    
  showOrthoInfoTable(rowData,index){
    console.log("rowData,index:",rowData,index);
    // rowData.orthoList[rowData.speciesListData.indexOf(index)];
    let ortholist=[];
    rowData.speciesListData.forEach((id,idx)=>{
      if(id==index){
        ortholist.push(rowData.orthoList[idx])
    }})
    this.showOrthoSubTable = true;
    this.orthoLoading=true;
    this.orthoTableData=[]
    ortholist.forEach(item=>{
      let hdbid=item.hdbId;
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/gene-detail-ortho",{params:{"hdbId":hdbid}}).then((res)=>{
        console.log("gene res:",res);
        item.ensemblGeneId=res.data.ensemblGeneId;
        item.geneSymbol=res.data.geneSymbol
        this.orthoTableData.push(item)
        this.orthoLoading=false;
      })
    })
    this.showSubTableBox=false;
    // this.orthoTableData=ortholist;
    console.log("orthoTableData:",this.orthoTableData);

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
    let ortholist=[];
    rowValue.speciesListData.forEach((id,idx)=>{
    if(id==index){
        let item=rowValue.orthoList[idx]
        ortholist.push(item)
    }})
    this.orthoTableData=[];
    ortholist.forEach(item=>{
      let hdbid=item.hdbId;
      this.$axios.get("http://192.168.164.93:9401/api/gene-detail-ortho",{params:{"hdbId":hdbid}}).then((res)=>{
        item.ensemblGeneId=res.data.ensemblGeneId;
        item.geneSymbol=res.data.geneSymbol
        this.orthoTableData.push(item)
        this.orthoLoading=false;
      })
    })
    
    this.showOrthoSubTable = true;
    this.orthoLoading=false;
    // this.orthoTableData=ortholist;
    this.showSubTableBox=true;

    // 左侧基因的接口数据
    let snpId1 = rowValue.snpId;
    let dataSource1=rowValue.dataSource
    let BASEPATH;
    this.gwasInfoData=[];
    if(dataSource1.length!==0){
      if(dataSource1.indexOf("v2")>0){BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV2/v2/variants/getlist?dataSource="}
      else{BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV3/v2/variants/getlist?dataSource="}
      // let snpAll=i.snpList.join(',')
      let PATH=BASEPATH+dataSource1+"&snplist="+snpId1;
      if(snpId1.length>0){
          this.getVarData(PATH)
      }
    }
    // 右侧基因接口数据
    // 有问题，明天看看
    console.log("ortholist:",ortholist);
   ortholist.forEach((item)=>{
    let dataSource2=item.dataSource;
    let hdbid=item.hdbId;
    let varName=item.varName;
    let gwasOrgId=item.gwasOrgId;
    let PATH;
    console.log("gwasOrgId:",gwasOrgId);
    if(dataSource2.length>0){
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/var-snpid",{params:{'hdbId': hdbid,"varName":varName}}).then(res=>{
          console.log("res:",res);
          if(res.data.length !==0){
            let BASEPATH;
            let snpId2=res.data;
            this.gwasLoading=true;
            if(dataSource2.indexOf("v2")>0){BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV2/v2/variants/getlist?dataSource="
            }else{BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV3/v2/variants/getlist?dataSource="}
            PATH=BASEPATH+dataSource2+"&snplist="+snpId2;
          }
        }).finally(
          ()=>{
            console.log("PATH:",PATH);
            this.getVarData(PATH,gwasOrgId)
          }
        )
    }
   })
 
    
    
   },
   getVarData(PATH,gwasOrgId){
    console.log("gwasOrgIdaaaa:",gwasOrgId);
    this.$axios.get(PATH).then(response=>{
      this.gwasLoading=false;
      console.log("getvardata res:",response);
      
      // this.gwasInfoData=response.data.snp;
      this.varLoading=false;
      let datas=response.data.snp
      if(datas.length>0){
        // 返回是对象
        console.log("datas:",datas,gwasOrgId);
        // foe(let item of datas){item.orgId=orgId}
        for(let item of datas){
           item.orgId=gwasOrgId
        }
        console.log("(response.data.snp.length111:",response.data.snp.length);
        this.gwasInfoData=this.gwasInfoData.concat(datas);
      }else{
        // 返回是对象
        console.log("(response.data.snp.length222:",response.data.snp.length);
        console.log("datas:",datas,gwasOrgId);
        datas.orgId=gwasOrgId
        console.log("datas:",datas)
        this.gwasInfoData.push(datas);
      }
      console.log("this.gwasInfoData:",this.gwasInfoData);
      for(let item of this.gwasInfoData){
          let pos=item.chrom+":"+item.position;
          let allele=item.refallele+"/"+item.allele;
          let maf=item.maf+":"+item.maffreq.slice(0,7);
          let classsnp="SNP";
          item.position=pos;
          item.allele=allele;
          item.maf=maf;
          item.snpClassId=classsnp;
          if(item.gene.length>1){
            let name=[]
            item.gene.forEach(item=>{
              name.push(item.genename)
            })
            item.gene.genename=name.join(',')
        }
      }
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
            
            if( data.orthoList[idx].varName!=null ){
              // console.log("data.orthoList[idx]:",data.orthoList[idx].varName,data.varName);
              let traitlist=data.orthoList[idx].varName.split(",")
              if(traitlist.indexOf(data.varName)>-1){
                console.log(1,"idx:",idx,"species:",data.orthoList[idx],"trait data:",data.varName);
                console.log("data.orthoList[idx]:",data.orthoList[idx].varName,data.varName);
                return 1;
              }else{
                console.log(2);
                console.log("data.orthoList[idx]:",data.orthoList[idx],data.varName);
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
      this.getTraitData(this.varItem.varName,this.classification,this.pageSize,1,this.form.querySpecies)

    }, 
    clearFilter(){
      if(this.classification=="animal"){
        this.loading_animal=true;
      }else{
        this.loading_plant=true;
      }
      this.getTraitData(this.varItem.varName,"animal")
      this.getTraitData(this.varItem.varName,"plant")
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
                if(item.varName !== null){
                  varData.traitListData2.push(this.speciesList_plant.indexOf(speciesName))
                  let varlist=item.varName.split(',')
                  if(varlist.indexOf( varData.varName)>-1){
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
                if(item.varName !== null){
                  // 有trait但不相同
                   varData.traitListData2.push(this.speciesList_animal.indexOf(speciesName))
                  let varlist=item.varName.split(',')
                  if(varlist.indexOf( varData.varName)>-1){
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
    async getTraitData(varname,classification,pagesize,pagenum,speciesName){
      console.log("params:",varname,classification,pagesize,pagenum,speciesName);
      this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/variants",{params:{'classification': classification,'varname':varname,'length':pagesize,'pageNo':pagenum,'speciesName':speciesName}})
      .then((response) => {
        console.log("response:",response);
        if(classification=="animal"){
          this.getSpecies(varname,"animal")
          this.totalSize_animal = response.data.recordsTotal;
          // this.speciesList_animal=response.data.data[0].headerList;
          this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_animal=res;this.loading_animal=false;});
        
        }else{
          this.getSpecies(varname,"plant")
          this.totalSize_plant = response.data.recordsTotal;
          // this.speciesList_plant=response.data.data[0].headerList;
          this.showTableIcon(response.data.data,classification).then((res)=>{ this.traitData_plant=res; this.loading_plant=false;});
        }

      })
      .finally(()=>{ return Promise.resolve()})
   
    },
    // 根据当前classification判断获取动物或植物列表
    getSpecies(varName,speciesType){
      this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/species-var',{params: {"varName":varName,speciesType: speciesType}})
        .then(response=>{
          // 2是植物，1是动物
          if(speciesType=='plant'){
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
  // varname,classification,pagesize,pagenum,speciesName
    this.getTraitData(this.varItem.varName,"animal");
    this.getTraitData(this.varItem.varName,"plant");

    window.addEventListener("scroll", this.watchScroll);
    // this.getTableMaxHeight(); 
    // let _this = this;
    // window.onresize = function () {//用于使表格高度自适应的方法  
    // _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    // }
   // 获取动植物名称列表，用于下拉选择框
    this.getSpecies(this.varItem.varName,"animal")
    this.getSpecies(this.varItem.varName,"plant")
    
     window.addEventListener("scroll", this.watchScroll);
      this.getTableMaxHeight(); 
      let _this = this;
      window.onresize = function () {//用于使表格高度自适应的方法  
      _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    }
    
  }
}

</script>
