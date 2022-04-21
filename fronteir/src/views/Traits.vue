<template>
  <div>
    <HeaderBar />
    <Banner />
    <Navigator />
    <div class="decorate-bar" @click="drawer = true" style="cursor:pointer"><a style="text-decoration: underline">Click to Show Trait Ontology</a></div>
    <!-- 左侧树结构 -->
        <el-drawer
          :visible.sync="drawer"
          :direction="direction"
        >
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
        </el-drawer>
    <el-tabs v-model="activeName" type="card"  style="width:95%;margin-left:3%;margin-top:2%">
      <el-tab-pane label="plants" name="first">
      <el-card shadow="none" class="tableCard" style="margin-top: 10px">
      
      <div class="trait-box trait-infoBox">
        <div style="padding-left:3%">
          <h2 class="trait-title">Trait Name : {{ traitName }} | ID: {{ traitID }}</h2
          >
          <h2 class="trait-def trait-title" v-if="traitDef != ''"
            >Definition: {{ traitDef }}</h2
          >
        </div>
      </div>
      <div class="wraper page-component__scroll el-scrollbar">
        
        
        <div class="trait-info">
          
  <!-- 使用中的表格 -->
            <el-table
              :data="traitData"
              style="width: 100%;margin-top:3%;overflow:hidden;height:0"
              class="trait-table"
              row-key="id"
              v-loading="loading"
              ref="table"
              height="200">
              <el-table-column prop="traitName" label="Trait Name" fixed width="180px" style="background-color:white"></el-table-column>
              <el-table-column  prop="geneId" label="Gene Id" width="170px" fixed>
              </el-table-column>
  <!-- 显示gwas数量的col -->
              <!-- <el-table-column prop="gwasNum" label="Gwas Number">
                <template scope="scope">
                  <el-tooltip class="tooltip-item"  placement="bottom" effect="light">
                    <div slot="content">Click to Show Detail<br/> Informations Below</div>
                    <a style="color:rgb(70, 121, 187);cursor :pointer;" >{{ scope.row.gwasNum}}</a>
                  </el-tooltip>
                </template>
              </el-table-column> -->
              <!-- <el-table-column prop="taxonId" label="Taxon Id" fixed></el-table-column> -->
              <el-table-column prop="speciesCommonName" label="Species" fixed></el-table-column>
              <el-table-column prop="speciesCommonName" label="Species" style="text-align:center">
                <el-table-column label="Goat" prop="Goat"
                  class="imglink">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Goat"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Goat"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Pig" prop="Pig" width="180px">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Pig"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Pig"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Sheep" prop="Sheep">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Sheep"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Sheep"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Chicken" prop="Chicken">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Chicken"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Chicken"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Cattle" prop="Cattle">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Cattle"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Cattle"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
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
                <el-table-column prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column prop="pmid" label="Pubmed Id"></el-table-column>
                <el-table-column prop="pvalue" label="Pvalue"></el-table-column>
              </el-table>
            </el-card>
        </div>
      </div>
      <!-- ortho Gene info表格 -->
      <div class="gwasWrapper" v-if="showOrthoSubTable">
        <div  class="gwasBox" style="padding-left:3%;margin-bottom:1.3%">
          <h3 class="trait-title">Ortholog Gene Detai Information</h3>
        </div>
        <div id="subTableBox">
            <el-card shadow="none" class="gwasDetailCard">
              <el-table 
                :data="orthoTableData"
                border
                style="width: 100%;margin-top:3%;"
                
                id="subTable"
                :row-style="{height: '0'}"
                max-height="400"
                v-loading="orthoLoading"
              >
                <el-table-column prop="ensemblId1" label="ensemblId1"></el-table-column>
                <el-table-column prop="geneId1" label="geneId1"></el-table-column>
                <el-table-column prop="tax1" label="tax1"></el-table-column>
                <el-table-column prop="protein1" label="protein1"></el-table-column>
                <el-table-column prop="geneId1" label="geneId1"></el-table-column>
                <el-table-column prop="geneId2" label="geneId2"></el-table-column>
                <el-table-column prop="protein2" label="protein2"></el-table-column>
                <el-table-column prop="tax2" label="tax2"></el-table-column>
                <el-table-column prop="orthoTraitName" label="orthoTraitName"></el-table-column>
              </el-table>
            </el-card>
        </div>
      </div>
      </el-card>
      </el-tab-pane>
    <!-- 动物tab -->
    <el-tab-pane label="animals" name="second">
      <el-card shadow="none" class="tableCard" style="margin-top: 10px">
      
      <div class="trait-box trait-infoBox">
        <div style="padding-left:3%">
          <h2 class="trait-title">Trait Name : {{ traitName }} | ID: {{ traitID }}</h2
          >
          <h2 class="trait-def trait-title" v-if="traitDef != ''"
            >Definition: {{ traitDef }}</h2
          >
        </div>
      </div>
      <div class="wraper page-component__scroll el-scrollbar">
        <div class="trait-info">
  <!-- 使用中的表格 -->
            <el-table
              :data="traitData"
              style="width: 100%;margin-top:3%;overflow:hidden;height:0"
              class="trait-table"
              row-key="id"
              v-loading="loading"
              ref="table"
              height="200">
              <el-table-column prop="traitName" label="Trait Name" fixed width="180px" style="background-color:white"></el-table-column>
              <el-table-column  prop="geneId" label="Gene Id" width="170px" fixed>
              </el-table-column>
  <!-- 显示gwas数量的col -->
              <!-- <el-table-column prop="gwasNum" label="Gwas Number">
                <template scope="scope">
                  <el-tooltip class="tooltip-item"  placement="bottom" effect="light">
                    <div slot="content">Click to Show Detail<br/> Informations Below</div>
                    <a style="color:rgb(70, 121, 187);cursor :pointer;" >{{ scope.row.gwasNum}}</a>
                  </el-tooltip>
                </template>
              </el-table-column> -->
              <!-- <el-table-column prop="taxonId" label="Taxon Id" fixed></el-table-column> -->
              <el-table-column prop="speciesCommonName" label="Species" fixed></el-table-column>
              <el-table-column prop="speciesCommonName" label="Species" style="text-align:center">
                <el-table-column label="Cotton" prop="cotton"
                class="imglink">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["cotton"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["cotton"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Japanese apricot" prop="Japanese apricot" width="180px">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Japanese apricot"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Japanese apricot"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Maize" prop="Maize">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Maize"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Maize"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Rapeseed" prop="Rapeseed">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Rapeseed"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Rapeseed"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Rice" prop="Rice">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Rice"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Rice"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Sorghum" prop="Sorghum">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Sorghum"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Sorghum"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
                <el-table-column label="Soybean" prop="Soybean">
                  <template slot-scope="scope" >
                   <img
                      :src="yesIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Soybean"] ==1'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                    <img
                      :src="fullIcon"
                      min-width="70"
                      height="70"
                      class="iconImg"
                      v-if='scope.row["Soybean"] ==2'
                      style="cursor:pointer"
                      @click=showOrthoInfoTable(scope.row.orthoList)
                    />
                  </template>
                </el-table-column>
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
                <el-table-column prop="speciesCommonName" label="Species"></el-table-column>
                <el-table-column prop="pmid" label="Pubmed Id"></el-table-column>
                <el-table-column prop="pvalue" label="Pvalue"></el-table-column>
              </el-table>
            </el-card>
        </div>
      </div>
      <!-- ortho Gene info表格 -->
      <div class="gwasWrapper" v-if="showOrthoSubTable">
        <div  class="gwasBox" style="padding-left:3%;margin-bottom:1.3%">
          <h3 class="trait-title">Ortholog Gene Detai Information</h3>
        </div>
        <div id="subTableBox">
            <el-card shadow="none" class="gwasDetailCard">
              <el-table 
                :data="orthoTableData"
                border
                style="width: 100%;margin-top:3%;"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="400"
                v-loading="orthoLoading"
              >
                <el-table-column prop="ensemblId1" label="ensemblId1"></el-table-column>
                <el-table-column prop="geneId1" label="geneId1"></el-table-column>
                <el-table-column prop="tax1" label="tax1"></el-table-column>
                <el-table-column prop="protein1" label="protein1"></el-table-column>
                <el-table-column prop="geneId1" label="geneId1"></el-table-column>
                <el-table-column prop="geneId2" label="geneId2"></el-table-column>
                <el-table-column prop="protein2" label="protein2"></el-table-column>
                <el-table-column prop="tax2" label="tax2"></el-table-column>
                <el-table-column prop="orthoTraitName" label="orthoTraitName"></el-table-column>
              </el-table>
            </el-card>
        </div>
      </div>
      </el-card>
    </el-tab-pane>
    </el-tabs>
    <FooterBar />
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
import qs from "qs";
// // icons
import yesIcon from "@/assets/img/icon/fangkuang.svg";
import fullIcon from "@/assets/img/icon/huafu.svg";

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
     yesIcon,
     fullIcon,
      filterText: "",
      treedata: traitOntology.tree,
      showSubTableBox:false,
      gwasLoading:true,
      orthoLoading:true,
      activeName: 'first',
      options: [],
      drawer:false,
      direction:"ltr",
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
      traitData: [],
      orthoTableData: [],
      value: "",
      traitID: "PPTO:0000002",
      traitDef: "The plant trait.",
      traitName: "flora trait",
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
      otrhoGeneData:[],
      loading:true,
      showOrthoSubTable:false,
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
      {
    const axiosInstance = this.$axios.create({
      headers: {
        "Content-Type": 'application/x-www-form-urlencoded;charset="utf-8"',
      }, // 设置传输内容的类型和编码
      withCredentials: true, // 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
    });
    axiosInstance
      .get("http://localhost:9401/api/traits?length=" + val)
      .then((response) => {
        this.traitData = [];
        this.totalSize = response.data.recordsTotal;
        // let plant2Name={"3635":"Cotton","102107":"Japanese apricot","4577":"Maize","3708":"Rapeseed","4530":"Rice","4558":"Sorghum","3847":"Soybean"}
        // let animal2Name={"9925":"Goat","9823":"Pig","9940":"Sheep","9031":"Chicken","9913":"Cattle"}
        for(let item of response.data.data){
          item.gwasNum=item.gwasId.split(",").length;
          for(let orthoitem of item.orthoList){
            if(orthoitem.tax2=="3635"){
              item["cotton"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["cotton"]=2
              }
            }else if(orthoitem.tax2=="102107"){
              item["Japanese apricot"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Japanese apricot"]=2
              }
            }else if(orthoitem.tax2=="4577"){
              item["Maize"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Maize"]=2
              }
            }else if(orthoitem.tax2=="3708"){
              item["Rapeseed"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Rapeseed"]=2
              }
            }else if(orthoitem.tax2=="3708"){
              item["Rice"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Rice"]=2
              }
            }else if(orthoitem.tax2=="4558"){
              item["Sorghum"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Sorghum"]=2
              }
            }else if(orthoitem.tax2=="3847"){
              item["Soybean"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Soybean"]=2
              }
          }
        }
        this.traitData.push(item)
        console.log("traitData:",this.traitData);
      }})
      .catch((error) => {
        console.log(error);
        this.errored = true;
      })
      .finally(() => (this.loading = false));
  }

    },
    handleCurrentChange(val) {
      this.loading=true;
      this.currentPage4 = val;
    {
    const axiosInstance = this.$axios.create({
      headers: {
        "Content-Type": 'application/x-www-form-urlencoded;charset="utf-8"',
      }, // 设置传输内容的类型和编码
      withCredentials: true, // 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
    });
    axiosInstance
      .get("http://localhost:9401/api/traits?pageNo="+val+"&length="+this.tableSize)
      .then((response) => {
        this.traitData = [];
        this.totalSize = response.data.recordsTotal;
        // let plant2Name={"3635":"Cotton","102107":"Japanese apricot","4577":"Maize","3708":"Rapeseed","4530":"Rice","4558":"Sorghum","3847":"Soybean"}
        // let animal2Name={"9925":"Goat","9823":"Pig","9940":"Sheep","9031":"Chicken","9913":"Cattle"}
        for(let item of response.data.data){
          item.gwasNum=item.gwasId.split(",").length;
          for(let orthoitem of item.orthoList){
            if(orthoitem.tax2=="3635"){
              item["cotton"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["cotton"]=2
              }
            }else if(orthoitem.tax2=="102107"){
              item["Japanese apricot"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Japanese apricot"]=2
              }
            }else if(orthoitem.tax2=="4577"){
              item["Maize"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Maize"]=2
              }
            }else if(orthoitem.tax2=="3708"){
              item["Rapeseed"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Rapeseed"]=2
              }
            }else if(orthoitem.tax2=="3708"){
              item["Rice"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Rice"]=2
              }
            }else if(orthoitem.tax2=="4558"){
              item["Sorghum"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Sorghum"]=2
              }
            }else if(orthoitem.tax2=="3847"){
              item["Soybean"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Soybean"]=2
              }
          }
        }
        this.traitData.push(item)
        console.log("traitData:",this.traitData);
      }})
      .catch((error) => {
        console.log(error);
        this.errored = true;
      })
      .finally(() => (this.loading = false));
  }
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
      this.traitID = value.traitID;
      this.traitDef = value.Definitions;
      this.traitName = value.Name;
      this.form.traitName = value.Name;

      this.$axios
        .get(
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
          // this.traitData.gwasNum=this.traitData.gwasId.split(',').length
          this.totalSize = response.data.recordsTotal;
          for(let item of this.traitData){
            console.log("item.fromdb:",item);
            item.gwasNum=item.gwasId.split(",").length;
            for(let orthoitem of item.orthoList){orthoitem.fromdb=orthoitem.fromdb.split(',')}
          }
        })
        .catch(function (error) {
          console.log(error);
        })
        .finally(() => (this.loading = false));
    },
    
   
    async sendRequest() {
      let formData = this.form;
      console.log("formData:",formData);
      this.loading=true;
      let data = await this.$axios({
        //这里的 this.$http 就是axios 因为 我在vue原型上，挂载axios的时候，把axios改名为$http 了。
        url: "http://localhost:9401/api/traits/ortho-data", //请求路径（接口）
        method: "POST", //请求方式
        headers: { "content-type": "application/x-www-form-urlencoded" }, // 请求头，发送FormData格式的数据，必须是 这种请求头。
        data: qs.stringify(formData), //发送请求要传的FormData参数。必须用 qs.stringify（）序列化一下。
      });
      this.loading=false;
      this.traitData = data.data.data;
      this.totalSize = data.data.recordsTotal;
      console.log("this.traitData:",this.traitData);

       for(let item of this.traitData){
          item.gwasNum=item.gwasId.split(",").length;
          for(let orthoitem of item.orthoList){orthoitem.fromdb=orthoitem.fromdb.split(',')}
        }
     
    },
    // 先注释掉显示gwas子表的功能，这一块放在gene信息里面
    // getGwasDetail(gwasidInfo){
    //   this.showSubTableBox = true;
    //   this.gwasLoading=true;
    //   // 向gwas发送请求，请求gwas库中的数据渲染详细信息的表格
    //   // http://192.168.164.15:9500/hdb/gwas/gwasids?gwasId=240,239&organismId=19&offset=0&pagesize=10&total=10
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
    
  //   checkSpeClass(val){
  //     // 判断所选择的物种是动物还是植物
  //     let plant=["3635","102107","4577","3708","4530","4558","3847"]
  //     this.form.orthoSpecies =""
  //     // 如果非植物就认为是动物
  //     // let animal=["9925","9823","9940","9031","9913"]
  //     if(plant.indexOf(val)!=-1){
  //       this.options=this.species[0].options
  //     }else{
  //       this.options=this.species[1].options
  //     }
  //     this.options.filter(item=>{if(item.value==val){item.disabled=true}else{item.disabled=false}})
  // },
  showOrthoInfoTable(orthoList){
    this.showOrthoSubTable = true;
    this.orthoLoading=false;
    this.orthoTableData=orthoList;
    console.log(orthoList);
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
      .get("http://localhost:9401/api/traits")
      .then((response) => {
        this.traitData = [];
        this.totalSize = response.data.recordsTotal;
        // let plant2Name={"3635":"Cotton","102107":"Japanese apricot","4577":"Maize","3708":"Rapeseed","4530":"Rice","4558":"Sorghum","3847":"Soybean"}
        // let animal2Name={"9925":"Goat","9823":"Pig","9940":"Sheep","9031":"Chicken","9913":"Cattle"}
        for(let item of response.data.data){
          item.gwasNum=item.gwasId.split(",").length;
          for(let orthoitem of item.orthoList){
            // orthoitem.fromdb=orthoitem.fromdb.split(',')
            if(orthoitem.tax2=="3635"){
              item["cotton"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["cotton"]=2
              }
            }else if(orthoitem.tax2=="102107"){
              item["Japanese apricot"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Japanese apricot"]=2
              }
            }else if(orthoitem.tax2=="4577"){
              item["Maize"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Maize"]=2
              }
            }else if(orthoitem.tax2=="3708"){
              item["Rapeseed"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Rapeseed"]=2
              }
            }else if(orthoitem.tax2=="3708"){
              item["Rice"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Rice"]=2
              }
            }else if(orthoitem.tax2=="4558"){
              item["Sorghum"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Sorghum"]=2
              }
            }else if(orthoitem.tax2=="3847"){
              item["Soybean"]=1
              if (orthoitem.orthoTraitName==item.traitName){
                  item["Soybean"]=2
              }
          }
        }
        this.traitData.push(item)
        console.log("traitData:",this.traitData);
      }})
      .catch((error) => {
        console.log(error);
        this.errored = true;
      })
      .finally(() => (this.loading = false));
  },
};
</script>
