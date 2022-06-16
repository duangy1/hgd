<template>
  <div>
    <HeaderBar />
    <Banner />
    <Navigator />
    <el-drawer
      :visible.sync="drawer"
      :direction="direction"
      style="width:100%"
    >
      <div class="tree-div">
      <el-card shadow="none" class="tree-card" >
      <el-input placeholder="输入关键字进行过滤" v-model="filterText">
      </el-input>

      <el-tree
        style="margin-top: 15px"
        class="filter-tree"
        :data="treedata"
        node-key="label"
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
      <el-breadcrumb-item>Variants</el-breadcrumb-item>
    </el-breadcrumb>

    <el-container style="border: 1px solid #eee;overflow: hidden;" id="trait-container">
    <div style="margin-top:3%">
    <el-button @click="buttonFunction()" ref="button" id="button"></el-button>
      <div class="trait-box trait-infoBox">
          <div style="padding-left:29px">
            <h2 class="trait-title">Consequence Type : {{ varName }}</h2
            >
          </div>
      </div>
    </div>
    <el-card shadow="none" class="border-card">
    <el-tabs v-model="activeName" style="margin-bottom: 1%;" @tab-click="changeClass">
    <!-- 动物tab -->
    <el-tab-pane label="animal" name="animal" >
      <!-- <el-card shadow="none" class="tableCard" style="margin-top: 10px"> -->
      <div>
        <div>
          <div class="filt-div">
              <el-select v-model="form.querySpecies" placeholder="Choose Species" class="filt-spe-select"> 
                <el-option
                  v-for="item in querySpeciesList_animal"
                  :key="item.id"
                  :label="item.commonName"
                  :value="item.commonName">
                </el-option>
              </el-select>
              <!-- <p style="float:left;margin-left:3%;vertical-align:middle;margin-top: 0.5%;font-family: 'Noto Sans', sans-serif;">Targer Species:</p> -->
                <!-- <el-select v-model="form.orthoSpecies" multiple class="filt-spe-select">
                    <el-option
                        v-for="(item,index) in speciesInfoList"
                        :key="index.id"
                        :label="item.commonName"
                        :value="item.taxonId"
                        >
                    </el-option>
                </el-select> -->
                <el-button icon="el-icon-search" type="success"   plain id="filter-search" @click="searchFilter()">Search</el-button>
                <el-button icon="el-icon-delete" type="primary"   plain id="filter-search2" @click="clearFilter()">Clear</el-button>


                <!-- <el-button icon="el-icon-s-grid" type="primary"  circle plain id="filterCol" @click="chooseCols()"></el-button> -->
                <el-popover
                  placement="bottom-end"
                  width="200"
                  trigger="manual"
                  class="popbox"
                  v-model="visible">
                  <!-- <el-checkbox-group v-model="checkList"> -->
                  <div class="checkbox-box">
                  <el-checkbox v-for="item of querySpeciesList_animal" :key="item.id" :label="item.commonName" class="chooseColBox" ref="one"></el-checkbox>
                  <el-checkbox  class="chooseColBox" @change="chooseall" ref="all">Choose All</el-checkbox>
                  </div>
                  <!-- </el-checkbox-group> -->
                  <div class="botton-wrapper">
                  <!-- <el-button class="choose-col-button">confirm</el-button> -->
                  
                  <el-button size="mini" type="primary"  @click="visible = false" class="choose-col-button">confirm</el-button>
                  <el-button size="mini" type="text" plain @click="visible = false" class="choose-col-button">cancel</el-button>

                  <!-- <el-button class="choose-col-button">clear</el-button> -->
                  </div>
                  <el-button slot="reference" icon="el-icon-s-grid" type="primary"   plain id="filterCol" @click="visible = !visible"></el-button>
                </el-popover>


            </div>
          <div id="wrapper">
          <div class="trait-info">
      <!-- 使用中的trait表格 -->
          <el-table
            :data="variantData_animal"
            style="width: 100%;margin-top:30px;overflow:hidden"
            class="trait-table"
            row-key="id"
            v-loading="loading"
            ref="table"
            :header-cell-style="hiligtDbCols"
          >
            <el-table-column align="center" class="titleCell" prop="varName" label="Consequence Type" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center"  prop="geneId" label="Gene Id" width="220px" fixed>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="160px" fixed></el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Ortholog Species Name">
            <el-table-column align="center" :label="item" :prop="item" v-for="(item,index) in speciesList_animal" :key="index"
              class="imglink" height="100px">
                  <template slot-scope="scope">
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
                      @click=showsnpInfoTable(scope.row.snpId,scope.row.dataSource,scope.row.orthoList[scope.row.speciesListData.indexOf(index)])
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
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalSize_animal"
          >
          </el-pagination>
      </div>
</div>
    <!-- 同源表格 -->
    <!-- <el-card shadow="none" class="sub-table-card"> -->
      <div class="sub-trait-box" v-if="showOrthoSubTable">
      <el-divider style="margin-top:5px"></el-divider>
          <div class="title-box">
            <h3 class="trait-sub-title">Ortholog Gene Detai Information</h3>
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
                  align="center"
                  prop="species1.commonName"
                  label="Species">
              </el-table-column>
              <el-table-column
                  align="center"
                  prop="species1.taxonId"
                  label="Taxon Id1">
              </el-table-column>
              <el-table-column
                  align="center"
                  prop="ensemblId1"
                  label="Ensembl Id">
              </el-table-column>
              
              <el-table-column
                  align="center"
                  prop="protein1"
                  label="Protein1">
              </el-table-column>
              <el-table-column
                  align="center"
                  prop="species2.commonName"
                  label="Species2">
              </el-table-column>
              <el-table-column
                  align="center"
                  prop="species2.taxonId"
                  label="Taxon Id2">
              </el-table-column>
              <el-table-column
                  align="center"
                  prop="protein2"
                  label="Protein2">
              </el-table-column>
              <el-table-column
              align="center"
              prop="entrezId"
              label="Entrez Id">
              </el-table-column>
            </el-table>
          </div>

      <!-- </div>
    </el-card> -->

      <!-- variant 表格 -->
    <!-- <el-card shadow="none"  class="sub-table-card">
      <div class="gwasWrapper" v-if="showVarTableBox">
        <div  class="gwasBox" style="padding-left:3%;margin-bottom:1.3%">
          <h3 class="trait-title">Variant Detai Information</h3>
        </div> -->
        <div class="sub-trait-box" v-if="showVarTableBox">
        <div  class="title-box" >
          <h3 class="trait-sub-title">Variant Detai Information</h3>
        </div>
        <div id="trait-info">
          <el-table 
              :data="gwasInfoData"
              style="width: 100%;"
              id="subTable"
              v-loading="varLoading"
            >
             <el-table-column
                    prop="rsnpId"
                    label="Var Id"
                    align="center">
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
                <el-table-column
                    prop="gene.genename"
                    label="Gene Id"
                    align="center">
                </el-table-column>

          </el-table>
        </div>
      </div>
      </div>
      </div>
    </el-tab-pane>
    
     <el-tab-pane label="plant" name="plant" >
        <div class="filt-div">
          
          <!-- <p style="float:left;margin-left:3%;vertical-align:middle;margin-top: 0.5%;font-family: 'Noto Sans', sans-serif;">Target Species:</p> -->
            <!-- <el-select v-model="form.orthoSpecies" multiple class="filt-spe-select">
                <el-option
                    v-for="(item,index) in speciesInfoList"
                    :key="index.id"
                    :label="item.commonName"
                    :value="item.taxonId"
                    >
                </el-option>
            </el-select> -->
            <el-select v-model="form.querySpecies" class="filt-spe-select" placeholder="Query Species"> 
            <el-option
              v-for="item in querySpeciesList_plant"
              :key="item.id"
              :label="item.commonName"
              :value="item.commonName">
            </el-option>
          </el-select>
            <el-button icon="el-icon-search" type="success"  plain id="filter-search" @click="searchFilter()">Search</el-button>
            <el-button icon="el-icon-delete" type="primary"  plain id="filter-search2" @click="clearFilter()">Clear</el-button>
      </div>

      <!-- <el-card shadow="none" class="tableCard" style="margin-top: 10px"> -->
      <div>
        <div>
          
          <div class="trait-info">
      <!-- 使用中的trait表格 -->
          <el-table
            :data="variantData_plant"
            style="width: 100%;margin-top:3%;overflow:hidden"
            class="trait-table"
            row-key="id"
            v-loading="loading"
            ref="table"
            :header-cell-style="hiligtDbCols"
          >
            <el-table-column align="center" class="titleCell" prop="varName" label="Consequence Type" fixed width="280px" style="background-color:white"></el-table-column>
            <el-table-column align="center"  prop="geneId" label="Gene Id" width="220px" fixed>
            </el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Species Name" width="150px" fixed></el-table-column>
            <el-table-column align="center" prop="taxonId" label="Taxon Id" width="160px" fixed></el-table-column>
            <el-table-column align="center" prop="speciesCommonName" label="Ortholog Species Name">
            <el-table-column align="center" :label="item" :show-overflow-tooltip="true" :prop="item" v-for="(item,index) in speciesList_plant" :key="index"
              class="imglink" height="100px">
                  <template slot-scope="scope">
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
                      @click=showsnpInfoTable(scope.row.snpId,scope.row.dataSource,scope.row.orthoList[scope.row.speciesListData.indexOf(index)])
                    />
                  </template>
              </el-table-column>
            </el-table-column>
          </el-table>
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
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalSize_plant"
          >
          </el-pagination>
          <div class="sub-trait-box " v-if="showOrthoSubTable">
            <el-divider style="margin-top:5px"></el-divider>
            <div class="title-box">
              <h3 class="trait-sub-title">Ortholog Gene Detai Information</h3>
            </div>

      </div>

    <!-- 同源表格 -->
   
      <el-table
        :data="orthoTableData"
        class="info-table"
        v-loading="orthoLoading"
        v-if="showOrthoSubTable"
        :border="false"
        fixed
    >
        <el-table-column
            align="center"
            prop="species1.commonName"
            label="Species">
        </el-table-column>
        <el-table-column
            align="center"
            prop="species1.taxonId"
            label="Taxon Id1">
        </el-table-column>
        <el-table-column
            align="center"
            prop="ensemblId1"
            label="Ensembl Id">
        </el-table-column>
        
        <el-table-column
            align="center"
            prop="protein1"
            label="Protein1">
        </el-table-column>
        <el-table-column
            align="center"
            prop="species2.commonName"
            label="Species2">
        </el-table-column>
        <el-table-column
            align="center"
            prop="species2.taxonId"
            label="Taxon Id2">
        </el-table-column>
        <el-table-column
            align="center"
            prop="protein2"
            label="Protein2">
        </el-table-column>
        <el-table-column
        align="center"
        prop="entrezId"
        label="Entrez Id">
        </el-table-column>
      </el-table>
      <!-- variant 表格 -->
      <div class="sub-trait-box" v-if="showVarTableBox">
          <div  class="title-box" style="padding-left:1.5%;margin-bottom:1.3%">
            <h3 class="trait-sub-titlee">Variant Detai Information</h3>
          </div>
          <div id="trait-info">
              <el-card shadow="none" class="gwasDetailCard">
              <el-table 
                :data="gwasInfoData"
                style="width: 100%;margin-top:3%;"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="400"
                v-loading="varLoading"
              >
                <el-table-column align="center" prop="varId" label="Var Id"></el-table-column>
                <el-table-column align="center" prop="varName" label="Trait Name"></el-table-column>
                <el-table-column align="center" prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column align="center" prop="pmid" label="Pubmed Id"></el-table-column>
                <el-table-column align="center" prop="pvalue" label="Pvalue"></el-table-column>
              </el-table>
            </el-card>
        </div>
      </div>
      </div>
      </div>
    </el-tab-pane>
    </el-tabs>
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
    </el-card>
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

<style src="../assets/css/traits.css" scoped></style>
<style src="../assets/css/variant.css" scoped></style>

<script>
import Navigator from "@/components/navigator.vue";
import varOntology from "@/assets/static/varOntology.json";
import FooterBar from "@/components/FooterBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
import Banner from "@/components/banner.vue";
import sameTraitIcon from "@/assets/img/icon/star.svg";
import orthoIcon from "@/assets/img/icon/huafu.svg";
// import qs from "qs";
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
    isAll(newValue) {
          if (newValue) {
              this.$refs.all.checked = true
          } else {
              this.$refs.all.checked = false
          }
      }
  },
  data() {
    return {
      orthoIcon,
      isAll: false,
      num:0,
      visible:false,
      pageSize:10,
      sameTraitIcon,
      filterText: "",
      treedata: varOntology.tree,
      varLoading:true,
      options: [],
      orthoTableData: [],
      value: "",
      varName: "missense_variant",
      currentPage4: 1,
      totalSize_animal: 0,
      totalSize_plant: 0,
      form: {
        querySpecies:"",
        // orthoSpecies: "",
        varName: "",
      },
      notShow: false,
      gwasInfoData:[],
      loading:true,
      showOrthoSubTable:false,
      showVarTableBox:false,
      drawer:false,
      direction:"ltr",
      activeName: 'animal',
      variantData_animal:[],
      variantData_plant:[],
      speciesList_animal:[],
      speciesList_plant:[],
      classification:"animal",
      querySpeciesList_animal:[],
      querySpeciesList_plant:[],
      speciesInfoList:[],
    };
  },

  methods: {
    searchFilter(){
      this.getInfobyvalue(this.varName,this.form.querySpecies)
   },
  chooseall(){
      // let checkboxs=document.getElementsByClassName("chooseColBox")
      // for(let dom in  checkboxs){console.log("dom.checked:",);dom.checked=true}
      // console.log(checkboxs);
 
    // isAll代表是否为全选
    this.isAll = !this.isAll
    let list = this.$refs.one
    if (this.isAll) {
        // 如果是全选的话，给每一个子元素的checked属性设置为true
        for (let i = 0; i < list.length; i++) {
            list[i].checked = this.isAll
        }
        // num用来计数，表示当前子元素有几个被选中
        this.num = this.times
    } else {
        // 如果不是全选的话，给每一个子元素的checked属性设置为false
        for (let i = 0; i < list.length; i++) {
            list[i].checked = this.isAll
        }
        this.num = 0
    }

   },
   click1(e) {
        if (e.target.checked) {
            // 如果当前元素被选中，那么num++
            this.num++
        } else {
            // 有一个没有被选中的话，那么就说明不是全选，就设置this.isAll为false
            this.num--
            this.isAll = false
        }
        if (this.num == this.times) {
            this.isAll = true
        }
    },
   clearFilter(){
    // 点clear的时候仍然显示当前trait的所有物种信息
    this.getvarData(this.varName,"animal",1,this.pageSize,"")
    this.getvarData(this.varName,"plant",1,this.pageSize,"")
    // this.getInfobyvalue(this.varName,"")
    this.form.querySpecies=""
   },
    handleSizeChange(val) {
      this.loading = true;
      this.pageSize = val;
      this.getvarData(this.varName,this.classification,this.currentPage4,this.pageSize,this.form.querySpecies)
    },
    handleCurrentChange(val) {
      this.loading=true;
      this.currentPage4 = val;
      this.getvarData(this.varName,this.activeName,val,this.pageSize,this.form.querySpecies)
      
    },
   
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getInfoByTrait(value) {
      // this.loading=true;
      // data.checked = true;
      this.varName = value.label;
      this.form.varName = value.label;
      console.log("params var:",this.form.querySpecies);
      // 发两个请求，分别动植物
      // this.getInfobyvalue(value.label,this.form.querySpecies)
      this.getvarData(value.label,"animal",1,this.pageSize,"")
      this.getvarData(value.label,"plant",1,this.pageSize,"")
    },
    // 查询参数：var注释，物种名
    getInfobyvalue(traitname,species){
      this.loading=true;
      
      this.$axios.get("http://localhost:9401/api/get-by-varName",{params: {varName: traitname,species: species}})
        .then((response) => {
          if(this.classification=="plant"){
          this.variantData_plant=this.showTableIcon(response.data.data,"plant");
          this.totalSize_plant = response.data.recordsTotal;
          }else{
            this.variantData_animal=this.showTableIcon(response.data.data,"animal");
            this.totalSize_animal = response.data.recordsTotal;
          }
          // if(response.data.recordsTotal==0){this.activeName='animal'}
          }).finally(() => {this.loading = false})

      // this.$axios.get("http://localhost:9401/api/get-by-varName",{params: {varName: traitname,species: species,classification:"animal"}})
      // .then((response) => {
        
      //   this.variantData_animal=this.showTableIcon(response.data.data,"animal");
      //   this.totalSize_animal = response.data.recordsTotal;
      //   // 如果动物的没有数据就切换到植物
      //   // if(response.data.recordsTotal==0){this.activeName='plant'}
      //   // this.loading=false;
      // })
      // .finally(() => {this.loading = false
      // console.log("this.totalSize_animal:",this.totalSize_animal);
      // });
  },
  showTableIcon(data,classss){
    if(classss == "plant"){
        this.speciesList_plant=[];
        if(data.length>0){
          for(let varData of data){
            // 增加一个属性保存物种的index，用来显示表格里的icon
            varData.speciesListData=[];
            if(varData.orthoList){
              for(let item of varData.orthoList){
                let speciesName=item.species.commonName
                if(this.speciesList_plant.indexOf(speciesName)==-1){
                  this.speciesList_plant.push(speciesName);
                }
                varData.speciesListData.push(this.speciesList_plant.indexOf(speciesName));
              }
            }
          }
          this.$refs['table'].doLayout();
        }
    }else{
      this.speciesList_animal=[];
        if(data.length>0){
          for(let varData of data){
            // 增加一个属性保存物种的index，用来显示表格里的icon
            varData.speciesListData=[];
            if(varData.orthoList){
              for(let item of varData.orthoList){
                let speciesName=item.species.commonName;
                if(this.speciesList_animal.indexOf(speciesName)==-1){
                  this.speciesList_animal.push(speciesName);
                }
                varData.speciesListData.push(this.speciesList_animal.indexOf(speciesName));
              }
            }
          }
          // this.$refs['table'].doLayout();
        }
    }
      
    return data;
   },
  buttonFunction(){
    this.drawer = true;
  },
  getTableMaxHeight(){
      this.$nextTick(function () {
      let box = document.querySelector(".trait-info").attributes
      let box_clientHeight = box[0].ownerElement.clientHeight;
      this.tableHeight = box_clientHeight - 100;
    })
  },
  changeClass(tab){
    this.showVarTableBox = false;
    this.showOrthoSubTable=false;
    this.classification=tab.label;
    // this.form.querySpecies="";
  },
  hiligtDbCols({rowIndex,columnIndex}){
    console.log("rowIndex,colIndex:",rowIndex,columnIndex);
      if(rowIndex===1){
          return "writing-mode: vertical-lr; !important;text-align:right;background: #F5F7FA;"
      }
  },
  iconClass(data,index){
      let classFT=data.speciesListData.indexOf(index)>-1?data.orthoList[data.speciesListData.indexOf(index)].orthoVarName.indexOf(data.varName)>-1:false
      return classFT
  },
   showOrthoInfoTable(orthoList){
    this.showOrthoSubTable = true;
    this.orthoLoading=false;
    this.orthoTableData=[orthoList];
    this.showVarTableBox=false
  },
  showsnpInfoTable(snpId,dataSource,orthoList){
    let BASEPATH;
    if(dataSource.indexOf("v2")>0){BASEPATH="http://192.168.164.14:9042/gvmRESTV2/v2/variants/getlist?dataSource="}
    else{BASEPATH="http://192.168.164.14:9201/gvmRESTV3/v2/variants/getlist?dataSource="}
    // let snpAll=i.snpList.join(',')
    let PATH=BASEPATH+dataSource+"&snplist="+snpId;
    if(snpId.length>0){
        this.$axios.get(PATH).then(response=>{
          this.gwasInfoData=response.data.snp;
          this.varLoading=false;
            if(this.gwasInfoData.length){this.gwasInfoData=response.data.snp;}else{this.gwasInfoData=[response.data.snp];}
            for(let item of this.gwasInfoData){
                let pos=item.chrom+":"+item.position;
                let allele=item.refallele+"/"+item.allele;
                let maf=item.maf+":"+item.maffreq.slice(0,7);
                let classsnp=item.snpClassId=="7"?"SNP":"-"
                item.position=pos;
                item.allele=allele;
                item.maf=maf;
                item.snpClassId=classsnp;
            }
        })

    }

    this.showVarTableBox = true;
    this.showOrthoSubTable=true;
    this.orthoTableData=[orthoList];
    this.orthoLoading=false;

   },
  //  进页面时获取数据方法
  // 查询参数：var注释，物种分类，动物名（可有可无）
   getvarData(varname,classification,pageNum,pageSize,speciesName){
    this.loading=true;
    this.$axios.get("http://localhost:9401/api/variants",{params:{'varname':varname,'classification': classification,"pageNo":pageNum,"length":pageSize,"speciesName":speciesName}})
      .then((response) => {
        if(classification=="animal"){
        this.totalSize_animal = response.data.recordsTotal;
        this.variantData_animal =this.showTableIcon(response.data.data,classification);
        
        }else{
          this.totalSize_plant = response.data.recordsTotal;
          this.variantData_plant =this.showTableIcon(response.data.data,classification);
        }
        
      }).finally(()=>{
        this.loading = false
      })
   },
   getSpecies(speciesType){
       this.$axios.get('http://localhost:9401/api/species-trait',{params: {speciesType: speciesType}})
        .then(response=>{
          if(speciesType==2){
          this.querySpeciesList_plant=response.data;}
          else{
            this.querySpeciesList_animal=response.data;
          }
      })
   }
  },
 
  
  mounted() {
    // 获取当前默认variant的动物、植物数据，赋值
      this.loading = true;
      this.getvarData(this.varName,"animal",this.pageNo,this.pageSize,"")
      this.getvarData(this.varName,"plant",this.pageNo,this.pageSize,"")
    // 获取动植物名称列表，用于下拉选择框
      this.getSpecies(2)
      this.getSpecies(1)
     
      window.addEventListener("scroll", this.watchScroll);
      this.getTableMaxHeight(); 
      let _this = this;
      window.onresize = function () {//用于使表格高度自适应的方法  
      _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    }
  },



};
</script>
