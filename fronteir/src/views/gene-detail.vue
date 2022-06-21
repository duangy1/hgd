<template>
<div class="table-container" onscroll="menuScroll()">
    <HeaderBar />
    <Banner />
    <Navigator />   
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
        <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
        <el-breadcrumb-item>Homolog Gene</el-breadcrumb-item>
        <el-breadcrumb-item>Gene Detail</el-breadcrumb-item>
    </el-breadcrumb>
    
    <el-container style="border: 1px solid #eee;margin-top: 15px;">
        <el-aside width="240px" style="background-color: aliceblue;overflow-y: auto;">
            <!-- <img src="../assets/img/arrow1.png" style="width:100%;vertical-align:top;margin:10px 0;"> -->
            <el-menu
                default-active="2"
                :class="navBarFixed == true ? 'navBarWrap' :''"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#409EFF"
                style="text-align: left;"
                width="100%"
                @select="selectMenu">
            <el-menu-item index="1">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">Gene Basic Informations</span>
            </el-menu-item>
            <el-menu-item index="2">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">Ortholog Genes</span>
            </el-menu-item>
            <el-menu-item index="3">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">GO Annotations</span>
            </el-menu-item>
            
            
            <el-menu-item index="4">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">Variants</span>
            </el-menu-item>
            <el-menu-item index="5">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">Traits Informations</span>
            </el-menu-item>
            <el-menu-item index="6">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">Expression</span>
            </el-menu-item>
            <el-menu-item index="7">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">Sequence Viewer</span>
            </el-menu-item>
        </el-menu>
        </el-aside>
         <!-- </div> -->
    
    <div class="tables" style="overflow:hidden" v-loading="fullLoading">
        
        <div class="info-div">
            <div class="basic-info" id="item-pos-1" >
            <!-- <el-card style="width:99%;margin: auto;" shadow="none"> -->
                <h2 class="b-info">Gene Basic Information</h2>
                <el-descriptions :column="2" class="descriptions" :size="size"  v-if="JSON.stringify(geneBasicInfo) !=='{}'">
                    <el-descriptions-item label="Ensembl Gene Id"><a :href="'https://ensemblgenomes.org/search/?query='+geneBasicInfo.ensemblGeneId">{{geneBasicInfo.ensemblGeneId}}</a><img :src="linkIcon" style="width:18px;height:18px;margin-left:5px"></el-descriptions-item>
                    <el-descriptions-item label="Entrez Gene Id"><a :href="'https://www.ncbi.nlm.nih.gov/gene/?term='+geneBasicInfo.entrezGene">{{geneBasicInfo.entrezGene}}</a><img :src="linkIcon" style="width:18px;height:18px;margin-left:5px"></el-descriptions-item>
                    <el-descriptions-item label="Refseq Id"><a :href="'https://www.ncbi.nlm.nih.gov/nuccore/'+geneBasicInfo.refseqId">{{geneBasicInfo.refseqId}}</a><img :src="linkIcon" style="width:18px;height:18px;margin-left:5px"></el-descriptions-item>
                    <el-descriptions-item label="Gene Synonym">{{geneBasicInfo.geneSynonym}}</el-descriptions-item>
                    <el-descriptions-item label="Gene Type">{{geneBasicInfo.geneType}}</el-descriptions-item>
                    <el-descriptions-item label="Gene Symbol">{{geneBasicInfo.geneSymbol}}</el-descriptions-item>
                    <el-descriptions-item label="Species Name">{{geneBasicInfo.speciesName.commonName}}</el-descriptions-item>
                    <el-descriptions-item label="Chromosom">{{geneBasicInfo.chromosom}}</el-descriptions-item>
                    <el-descriptions-item label="Gene Start">{{geneBasicInfo.geneStart}}</el-descriptions-item>
                    <el-descriptions-item label="Gene End">{{geneBasicInfo.geneEnd}}</el-descriptions-item>
                    <el-descriptions-item label="Gene Description">{{geneBasicInfo.geneDescription}}</el-descriptions-item>
                </el-descriptions>
            <!-- </el-card> -->
        </div>
        <el-divider></el-divider>
        <div id="item-pos-2">
            <div class="p-ele top-p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Gene Ortholog Informations</span></div>
            <!-- <el-card style="padding:2%" shadow="none"> -->
            <!-- <div class="info-div">
                <div class="basic-info" id="item-pos-1" >
                    <h2 class="b-info">Gene Ortholog Informations</h2>
            </div>
        </div> -->
        <div class="filt-div">
            <el-select v-model="form.species.ortho" multiple placeholder="Choose Species" class="filt-spe-select">
                <!-- <el-option label="Org1" value="shanghai"></el-option>
                <el-option label="Org2" value="beijing"></el-option> -->
                <el-option
                    v-for="item in species"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                    >
                </el-option>
            </el-select>
            <el-button icon="el-icon-search"  type="success" round  plain id="filter-search" @click="searchFilter(form.species.ortho)"></el-button>
            <el-button icon="el-icon-delete"  type="primary" round  plain id="filter-search" @click="clearFilter()"></el-button>
        </div>
                <!-- </el-card> -->
            <el-table
                :data="tableOrthoList"
                class="info-table"
                v-loading="loading"
                :max-height="tableHeight"
                :border="false"
                :header-cell-style="hiligtDbCols"
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
                    label="Ensembl Id"
                    width="200px">
                </el-table-column>
                <el-table-column
                    prop="gbiInfo.geneSymbol"
                    label="Gene Symbol"
                    width="120px">
                </el-table-column>
                <el-table-column
                    prop="protein"
                    label="Protein"
                    width="200px">
                </el-table-column>
                
                <el-table-column
                prop="entrezId"
                label="Entrez Id">
                </el-table-column>
            
                <!-- dbevidence列，现在按库拆了 -->
                <!-- <el-table-column
                    prop="dbevidenceList"
                    label="Evidence ">
                    <template slot-scope="scope">
                        <p v-for="(item,index) of scope.row.dbevidenceList" :key="index">{{item}}</p>
                    </template>
                </el-table-column> -->
                <el-table-column  prop="panther" label="Panther"></el-table-column>
                <el-table-column prop="treefam" label="Treefam"></el-table-column>
                <el-table-column  prop="eggnog"  label="Eggnog"></el-table-column>
                <el-table-column prop="oma" label="Oma"></el-table-column>
                <el-table-column prop="inparanoid" label="Inparanoid"></el-table-column>
                <el-table-column prop="hieranoid" label="Hieranoid"></el-table-column>
                

            </el-table>
            
        </div>
        <div id="item-pos-3">
            <el-divider class="divider"></el-divider>
            <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Gene Ontology Annotations</span></p>
    <!-- 这里 -->
    <!-- <el-card style="padding:2%" shadow="none"> -->
        <!-- 提示信息不显示 -->
        
    <!-- <el-container direction="vertical" > -->
    <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
        <div><i class="el-icon-question question-icon"></i></div>
    </el-tooltip>
                    
    <div class="filt-div">
        <el-checkbox v-model="checked.go" style="float:left" class="filt-spe-select" :disabled="waitResponse.go">Compare Ortholog Genes</el-checkbox>
        <el-select v-model="form.species.go" multiple placeholder="Show All Species" class="filt-spe-select">
            <!-- <el-option label="Org1" value="shanghai"></el-option>
            <el-option label="Org2" value="beijing"></el-option> -->
            <el-option
                v-for="item in species"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                >
            </el-option>
        </el-select>
        <el-button icon="el-icon-search"  type="success" round  plain id="filter-search" @click="compareOrthoGo(form.species.go)"></el-button>
        <el-button icon="el-icon-delete"  type="primary" round  plain id="filter-search" @click="clearOrthoGo()"></el-button>
    </div>
    <div class="filt-div"></div>
    <!-- </el-container> -->
        
    <!-- </el-card> -->
    <div id="svg-container-go"></div>
                    <!-- <svg style="width:100%;height:380px" id="svgcontainer-go"></svg> -->
                
                
                <!-- <el-table
                :data="goList"
                class="info-table"
                v-loading="goloading"
                :border="false"
            >
                <el-table-column
                    prop="geneId"
                    label="Gene Id"
                >
                </el-table-column>
                <el-table-column
                    prop="geneName"
                    label="Gene Name"
                >
                </el-table-column>
                <el-table-column
                    prop="goDomain"
                    label="GO Domain">
                </el-table-column>
                <el-table-column
                    prop="goEvidenceCode"
                    label="Go Evidence Code">
                </el-table-column>
                <el-table-column
                    prop="goId"
                    label="GO Id">
                </el-table-column>
                <el-table-column
                    prop="goTermAccession"
                    label="GO Term Accession">
                </el-table-column>
                <el-table-column
                    prop="goTermDefinition"
                    label="GO Term Definition">
                </el-table-column>
                <el-table-column
                    prop="goTermName"
                    label="GO Term Name">
                </el-table-column>
                <el-table-column
                    prop="goslimGoaAccession"
                    label="GO slim GoaAccession">
                </el-table-column>
                <el-table-column
                    prop="hgncSymbol"
                    label="HGNC Symbol">
                </el-table-column>
                <el-table-column
                    prop="uniprotGeneName"
                    label="Uniprot Gene Name">
                </el-table-column>
                <el-table-column
                    prop="uniprotGeneSymbol"
                    label="Uniprot Gene Symbol">
                </el-table-column>
            </el-table> -->
        </div>
        <div id="item-pos-4">
            <el-divider class="divider"></el-divider>

                <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Variant</span></p>
                <!-- <el-card style="padding:2%" shadow="none"> -->
                    <!-- 提示信息不显示 -->
                    
                    <!-- <el-container direction="vertical" > -->
                        <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                            <div><i class="el-icon-question question-icon"></i></div>
                        </el-tooltip>
                <div class="filt-div">
                <el-checkbox v-model="checked.var" style="float:left" class="filt-spe-select" :disabled="waitResponse.var">Compare Ortholog Genes</el-checkbox>
                <el-select v-model="form.species.var" multiple placeholder="Show All Species" class="filt-spe-select">
                    <!-- <el-option label="Org1" value="shanghai"></el-option>
                    <el-option label="Org2" value="beijing"></el-option> -->
                    <el-option
                        v-for="item in species"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search"  type="success" round  plain id="filter-search" @click="compareOrthoVar(form.species.go)"></el-button>
                <el-button icon="el-icon-delete"  type="primary" round  plain id="filter-search" @click="clearOrthoVar()"></el-button>
                </div>
                <!-- </el-container> -->
                <!-- </el-card> -->
                <div id="svg-container-var"></div>
                <!-- 根据snplist取值的表 -->
                 <el-table
                :data="varList"
                class="info-table"
                v-loading="goloading"
                :border="false"
                v-if="showVarInfoTable"
            >
                <el-table-column
                    prop="rsnpId"
                    label="Var Id">
                </el-table-column>
                <el-table-column
                    prop="position"
                    label="Position">
                </el-table-column>
                <el-table-column
                    prop="allele"
                    label="Allele"
                >
                </el-table-column>
                <!-- <el-table-column
                    prop="chrom"
                    label="Chromosom"
                >
                </el-table-column> -->
                
                <el-table-column
                    prop="maf"
                    label="Maf">
                </el-table-column>
                <!-- <el-table-column
                    prop="maffreq"
                    label="Maf Frequency">
                </el-table-column> -->
                <!-- <el-table-column
                    prop="omimFlag"
                    label="Omim Flag">
                </el-table-column>
                <el-table-column
                    prop="pharamFlag"
                    label="Pharam Flag">
                </el-table-column> -->
                
                <!-- <el-table-column
                    prop="refallele"
                    label="Ref Allele">
                </el-table-column> -->
                <!-- <el-table-column
                    prop="refsnpname"
                    label="Ref Snp Name">
                </el-table-column> -->
                <!-- <el-table-column
                    prop="rowcount"
                    label="Row Count">
                </el-table-column> -->
                <!-- <el-table-column
                    prop="rsid"
                    label="Rsid">
                </el-table-column> -->
                
                <el-table-column
                    prop="snpClassId"
                    label="Class">
                </el-table-column>
                <!-- <el-table-column
                    prop="snpEnd"
                    label="Snp End">
                </el-table-column> -->
                <!-- <el-table-column
                    prop="snpStart"
                    label="Snp Position">
                </el-table-column> -->
                
                <el-table-column
                    prop="gene.conseqtype"
                    label="Consequence Type/Effect">
                </el-table-column>
                <el-table-column
                    prop="gene.genealias"
                    label="Gene Symbol"
                    width="120px">
                </el-table-column>
                <el-table-column
                    prop="gene.genename"
                    label="Gene Id">
                </el-table-column>
            </el-table>
        </div>
        <div id="item-pos-5">
                <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Traits</span></p>
                <el-divider class="divider"></el-divider>
                <!-- <h1 style="text-align:left;font-weight:500;color:darkgray">Traits Informations</h1> -->
                <!-- <el-card style="padding:2%" shadow="none"> -->
                    <!-- 提示信息不显示 -->
                    
                    <!-- <el-container direction="vertical" > -->
                        <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                            <div><i class="el-icon-question question-icon"></i></div>
                        </el-tooltip>
                <div class="filt-div">
                <el-checkbox v-model="checked.trait" style="float:left" class="filt-spe-select" :disabled="waitResponse.trait">Compare Ortholog Genes</el-checkbox>
                <el-select v-model="form.species.trait" multiple placeholder="Show All Species" class="filt-spe-select">
                    <!-- <el-option label="Org1" value="shanghai"></el-option>
                    <el-option label="Org2" value="beijing"></el-option> -->
                    <el-option
                        v-for="item in species"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search"  type="success" round  plain id="filter-search" @click="compareOrthoTrait(form.species.go)"></el-button>
                <el-button icon="el-icon-delete"  type="primary" round  plain id="filter-search" @click="clearOrthoTrait()"></el-button>
                </div>

                    <!-- </el-container> -->
                <!-- </el-card> -->
                <div id="svg-container-trait"></div>
        </div>
        <div id="item-pos-6">
            <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Expression</span></p>
            <el-divider class="divider"></el-divider>
            <!-- <h1 style="text-align:left;font-weight:500;color:darkgray">Expression Informations</h1> -->
             <!-- <el-card style="padding:2%" shadow="none"> -->
                    <!-- 提示信息不显示 -->
                    
                    <!-- <el-container direction="vertical" > -->
                        <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                            <div><i class="el-icon-question question-icon"></i></div>
                        </el-tooltip>
                <div class="filt-div">
                <el-checkbox v-model="checked.expression" style="float:left" class="filt-spe-select" :disabled="waitResponse.expression">Compare Ortholog Genes</el-checkbox>
                <el-select v-model="form.species.expression" multiple placeholder="Show All Species" class="filt-spe-select">
                    <!-- <el-option label="Org1" value="shanghai"></el-option>
                    <el-option label="Org2" value="beijing"></el-option> -->
                    <el-option
                        v-for="item in species"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search"  type="success" round  plain id="filter-search" @click="compareOrthoExpre(form.species.go)"></el-button>
                <el-button icon="el-icon-delete"  type="primary" round  plain id="filter-search" @click="clearOrthoExpre()"></el-button>
                </div>

                    <!-- </el-container> -->
                <!-- </el-card> -->
            <div id="svg-container-expression"></div>
        </div>
        </div>
    </div>
    </el-container>
    <!-- </el-card> -->
    <FooterBar/>
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
<style src="../assets/css/gene-detail.css" scoped></style>
<script>
// @ is an alias to /src
import HeaderBar from '@/components/HeaderBar.vue';
import FooterBar from '@/components/FooterBar.vue';
import Navigator from '@/components/navigator.vue';
import Banner from "@/components/banner.vue";
// import pantherIcon from "@/assets/img/icon/P.svg";
// import omaIcon from "@/assets/img/icon/M.svg";
// import inparanoidIcon from "@/assets/img/icon/ii.svg";
// import eggnogIcon from "@/assets/img/icon/eee.svg";
// import hieranoidIcon from "@/assets/img/icon/H.svg";
// import treefamIcon from "@/assets/img/icon/t.svg";
import linkIcon from "@/assets/img/icon/link-icon-4.svg";
import * as d3 from 'd3';
// import { Loading } from 'element-ui';
export default {
  name: 'GeneDetail',
  components: {
    Navigator,
    Banner,
    HeaderBar,
    FooterBar,
    
  },
  data(){
      return {
        tableData: [],
        tabPosition:"left",
        size:"medium",
        geneBasicInfo:{},
        orthoInfo:[],
        tableOrthoList:[],
        goList:[],
        varList:[],
        loading:true,
        goloading:true,
        linkIcon,
        dbEvidence:"",
        form:{"species":{"go":"","ortho":"","var":"","trait":"","expression":""}},
        checked:{"go":"","var":"","trait":"","expression":""},
        species:[],
        navBarFixed:"false",
        tableHeight:800,
        tooltip:"",
        showVarInfoTable:false,
        waitResponse:{"go":false,"var":false,"trait":false,"expression":false},
        clicked:{"go":false,"var":false,"trait":false,"expression":false},
        classification:"",
        windowSize:3,
        fullLoading:true
        }
    },

    methods: {
      selectMenu(key){
        // scrollIntoView() js原生方法，实现锚点滚动过渡
        const target = document.querySelector("#item-pos-"+key)
        target.scrollIntoView({ behavior: 'smooth' })
        // scrollTo() 把内容滚动到指定的坐标。减去导航高度的目的：导航用定位固定在顶部，如果不减去，导航栏会遮挡一部分内容
        window.scrollTo(0, target.offsetTop)
      },
      searchFilter(key){
          let filterOrtho=this.orthoInfo.filter(item=>{
              return  key.indexOf(""+item.tax) >-1
          })
        //   console.log("filterOrtho:",filterOrtho);
          this.tableOrthoList=filterOrtho

      },
      clearFilter(){
          this.tableOrthoList=this.orthoInfo
      },
      compareOrthoGo(filtSpecies){
        this.compareFuntion("go",filtSpecies)
      },
      clearOrthoGo(){
        this.cleatFunction('go')
      },
      compareOrthoVar(filtSpecies){
        this.compareFuntion('var',filtSpecies)
        
      },
      clearOrthoVar(){
        this.cleatFunction('var')
      },
      compareOrthoTrait(filtSpecies){
        this.compareFuntion("trait",filtSpecies)
      },
      clearOrthoTrait(){
        this.cleatFunction('trait')
      },
      compareOrthoExpre(filtSpecies){
        this.compareFuntion("expression",filtSpecies)
      },
      clearOrthoExpre(){
        this.cleatFunction('expression')
      },
      cleatFunction(key){
        this.checked[key]=false;
        this.clicked[key]=false;
        let svgContainer=d3.select("#svgcontainer-"+key)
        d3.selectAll("#group_total").remove()
        svgContainer.attr('height','300')
        this.waitResponse[key]=false;
        this.form.species[key]="";
      },
    //   点击show button 调用的函数
      compareFuntion(key,filtSpecies){
        let filterOrtho;
        if(filtSpecies.length>0){
            filterOrtho=this.orthoInfo.filter(item=>{
                return  filtSpecies.indexOf(""+item.tax) >-1
            })
        }else{
            filterOrtho=this.orthoInfo;
        }
        if(!this.clicked[key]){
        this.clicked[key]=true;
        this.checked[key]=true;
        this.waitResponse[key]=true;
        //   取当前table的同源基因，循环查询对应的数据，如果有的话，添加一行
        let index=0;
        for(let i=0;i<filterOrtho.length;i++){
              let orthoItem=filterOrtho[i];
              let classification=orthoItem.species.classification;
              if(orthoItem.gbiInfo!=null){
                let ensID=orthoItem.gbiInfo.ensemblGeneId;
                let speciesName=orthoItem.species.shortName
                this.$axios.get("http://localhost:9401/api/gene-detail-"+key,{params: {'geneName': ensID,"classification":classification}})
                .then(response=>{
                    index += 1;
                    let svgContainer=d3.select("#svgcontainer-"+key)
                    let tooltip=d3.select("#tooltip")
                    console.log("response.data:",response.data);
                    this.drawSubRectChart(response.data,svgContainer,key,index,tooltip,speciesName, ensID)
        })
                
              }
          }}
      },
      getTableMaxHeight(){
        this.$nextTick(function () {
        let box = document.querySelector(".info-div").attributes
        let box_clientHeight = box[0].ownerElement.clientHeight;
        this.tableHeight = box_clientHeight - 100;
        })
    },
      watchScroll() {
      var scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop;
      //  当滚动超过 288 时，实现吸顶效果
      if (scrollTop > 288) {
        this.navBarFixed = true;
      } else {
        this.navBarFixed = false;
      }
    },
    drawGoRectChart(value,svgContainer,dataclass,speciesName,geneName){
    let rectHeight=18;
    // 绘制文字部分
    let tooltip = d3.select('.tables')
        .append('div')
        .attr("id", "tooltip")
        .style('position', 'absolute')
        .style('z-index', '10')
        .style('display','none')
        .style('line-height','18px')
        .style('text-align','justify')
        .style('font-size', '14px')
        .style('text-align','left')
        .style('padding', '15px')
        .style('border-radius',"5%")
        .style('color','white')
        .style('background-color','#808080')
    let top_class;
    let class_count=0;
    let class_PADDING=30;
    if(dataclass=="var"){top_class=value[0].voClassification;}else if(dataclass=="go"){
        console.log("value[0]",value[0]);
        top_class=value[0].topClass;
    }else if(dataclass=="expression"){top_class=value[0].eoClassification}
    for(let iii in value ){
        let text_top=220;
        let text_x=215;
        if(dataclass=="go"){text_top+=25;text_x-=25}
        let item=value[iii]
        
        let G_PADDING=rectHeight;
        // 不同分类间的间隔
        if(dataclass=="var"){
            if(item.voClassification!==top_class){top_class=item.voClassification;class_count+=1}
        }else if(dataclass=="go"){
            if(item.topClass!==top_class){top_class=item.topClass;class_count+=1}
        }else if(dataclass=="expression"){
            if(item.eoClassification!==top_class){top_class=item.eoClassification;class_count+=1}
        }

        // trait和variant注释类型比较少，所以隔宽点
        // if(dataclass=="var"||dataclass=="trait"){G_PADDING+=7;}
        let group_text = svgContainer.append('g').attr("id", "text_group"+iii).attr("transform","translate("+((iii*G_PADDING)+(class_PADDING*class_count)-70)+","+text_top+") rotate(-45)")
        let gene_fontsize=12
        let textgs = group_text.selectAll("text").data([item]).enter()
        if(dataclass=='go'){
            textgs.append("text")
                .attr("fill", "black")
                .attr("text-anchor", "start")
                .attr("font-family", "sans-serif")
                .attr("font-size", gene_fontsize)
                .attr("x", text_x)
                .attr("y", text_top)
                .text(d => {
                    let txtfile=d.goAnnotations
                    if(d.goAnnotations.length>58){
                        txtfile=d.goAnnotations.slice(0,58)+"..."
                    }
                    return  txtfile
                })
        }else if(dataclass=='var'){
             textgs.append("text")
            .attr("fill", "black")
            .attr("text-anchor", "start")
            .attr("font-family", "sans-serif")
            .attr("font-size", gene_fontsize)
            .attr("x", text_x)
            .attr("y", text_top)
            .text(d => {
                return  d.voAnnotaion
            })
        }else if(dataclass=="expression"){
            textgs.append("text")
            .attr("fill", "black")
            .attr("text-anchor", "start")
            .attr("font-family", "sans-serif")
            .attr("font-size", gene_fontsize)
            .attr("x", text_x)
            .attr("y", text_top)
            .text(d => {
                return  d.eoAnnotation
            })
        }
        else{
             textgs.append("text")
            .attr("fill", "black")
            .attr("text-anchor", "start")
            .attr("font-family", "sans-serif")
            .attr("font-size", gene_fontsize)
            .attr("x", text_x)
            .attr("y", text_top)
            .text(d => {
                return  d.traitName
            })
        }
        }
    // let speciesName=
    // 绘制热图rect部分
    this.drawSubRectChart(value,svgContainer,dataclass,0,tooltip,speciesName,geneName,rectHeight)
    
    },
    // 封装绘制rect方法
    drawSubRectChart(value,svgContainer,dataclass,index,tooltip,speciesName,geneName){
        let rectHeight=18
        let rect_top=228;
        if(dataclass=="go"){rect_top+=60}
        // let RECT_WIDTH=rectHeight;
        // let rect_height=rectHeight;
        let PADDING=rectHeight;
        let oriSvgHeight=300;
        if(dataclass=="go"){oriSvgHeight=400}
        svgContainer.attr('height',`${oriSvgHeight+index*15}`)
        let group_total;
        if(index==0){
            group_total = svgContainer.append('g').attr("id", "group_total_0").attr('transform',`translate(0,${PADDING*index})`)
        }else{
            group_total = svgContainer.append('g').attr("id", "group_total").attr('transform',`translate(0,${PADDING*index})`)
        }
        group_total.append('text')
            .attr('x',0)
            .attr('y',rect_top+rectHeight/2+5)
            .text(`${geneName}(${speciesName})`)
        let rect_group = group_total.selectAll("rect")
            .data(value)
            .enter()
        let RECT_PADDING=rectHeight;
        let RECT_x=228;
        // 判断绘图时的数据类别，不同类别间隔开
        let top_class;
        let class_count=0;
        let class_PADDING=30;
        if(dataclass=="var"){top_class=value[0].voClassification;}else if(dataclass=="go"){
            console.log("value[0]",value[0]);
            top_class=value[0].topClass;
        }else if(dataclass=="expression"){top_class=value[0].eoClassification}


        // if(dataclass=="var"||dataclass=="trait"){RECT_PADDING=25;}
        rect_group.append('rect')
        .attr("x", (d, i) => {
             // 不同分类间的间隔
            if(dataclass=="var"){
                if(d.voClassification!==top_class){top_class=d.voClassification;class_count+=1}
            }else if(dataclass=="go"){
                if(d.topClass!==top_class){top_class=d.topClass;class_count+=1}
            }else if(dataclass=="expression"){
                if(d.eoClassification!==top_class){top_class=d.eoClassification;class_count+=1}
            }

            let startRate = RECT_x+i*RECT_PADDING+class_count*class_PADDING
            return startRate
        })
        .attr("y", rect_top )
        .attr("width", rectHeight )
        .attr("height", rectHeight )
        .attr("fill", d=>"rgba(141,192,252,"+(d.opacity+0.03)+")")
        .attr('stroke',"darkgray")
        .attr('cursor',d=>d.snpNum>0?'pointer':'')
        .on("mousemove", function(d, i){
            let htmlInfo;   
            if( dataclass =="go"){
                htmlInfo=`<div><strong>Gwas Number: </strong>${i.goNum>0?i.goNum:"-"}</div>
            <div><strong>Gwas List: </strong>${i.goList==null?"-":i.goList.slice(0,2)}${i.goList!==null&&i.goList.length>2?"...":""}</div>
            <div><strong>Go Id: </strong>${i.goId}</div>
            <div><strong>Go Term: </strong>${i.goTerm}</div>
            <div><strong>Go Classification: </strong>${i.topClass}</div>
            <div><strong>Go Definition: </strong>${i.definition}</div>
            `
            }else if( dataclass =="var"){
                 htmlInfo=`<div>Variant Number: ${i.snpNum>0?i.snpNum:"-"}</div>
            <div>Variant List: ${i.snpList==null?"-":i.snpList.slice(0,2)}${i.snpList!==null&&i.snpList.length>2?"...":""}</div>
            <div>Variant Annotaion: ${i.voAnnotaion}</div>
            <div>Variant Classification: ${i.voClassification}</div>
            
            `
            }else if( dataclass =="expression"){
                htmlInfo=`<div>Expression Number: ${i.prjNum>0?i.prjNum:"-"}</div>
            <div>Bioproject List: ${i.prjList==null?"-":i.prjList.slice(0,2)}${i.prjList!==null&&i.prjList.length>2?"...":""}</div>
            <div>Expression Classification: ${i.eoClassification}</div>
            <div>Expression Annotation: ${i.eoAnnotation}</div>
            `
            }else {
                htmlInfo=`<div>Gwas Number: ${i.gwasNum>0?i.gwasNum:"-"}</div>
            <div>Gwas List: ${i.gwasList==null?"-":i.gwasList.slice(0,2)}${i.gwasList!==null&&i.gwasList.length>2?"...":""}</div>
            <div>Trait Id: ${i.traitId}</div>
            <div>Trait Name: ${i.traitName}</div>
            `
            }
            tooltip.interrupt().style("display", "block").html(htmlInfo)
                .style("opacity","0.8")
                .style("border","none")
                .style('text-align','left')
                .style('top', d.pageY+20+"px")
                .style('left',d.pageX+20+"px")
                .style('width',350+"px")
        })
        .on('mouseout', function () {
            tooltip.style('display', 'none')
        })
        .on("click", (d,i)=>{
            // let classification=this.geneBasicInfo.speciesName.classification;
            console.log("d,i:",d,i);
            if(dataclass=="var"){
                let dataSource=i.dataSource;
                let BASEPATH;
                if(dataSource.indexOf("v2")>0){BASEPATH="http://192.168.164.14:9042/gvmRESTV2/v2/variants/getlist?dataSource="}
                else{BASEPATH="http://192.168.164.14:9201/gvmRESTV3/v2/variants/getlist?dataSource="}
                let snpAll=i.snpList.join(',')
                let PATH=BASEPATH+dataSource+"&snplist="+snpAll;
                if(i.snpList.length>0){
                    this.$axios.get(PATH).then(response=>{
                        console.log("var response:",response.data.snp);
                        this.showVarInfoTable=true;
                        // var table绑定返回的snp数据
                        this.varList=response.data.snp;
                        // for(let item of tresponse.data.snp){}
                        if(this.varList.length){this.varList=response.data.snp;}else{this.varList=[response.data.snp];}
                        for(let item of this.varList){
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
                }else if(dataclass=="go"){
                        console.log("i.snpList:",i.snpList);
                }else if(dataclass=="trait"){
                    console.log("i.snpList:",i.snpList);
                }else if(dataclass=="expression"){
                    console.log("i.snpList:",i.snpList);
                    if(i.prjList.length>0){
                    let prjlist=i.prjList.join(',')
                    console.log(i.taxonId);
                    let PATH="https://ngdc.cncb.ac.cn/gen/api/json/gene/expressions?taxonomyId="+i.taxonId+"&accession="+i.geneId+"&bioProjectId="+prjlist
                    this.$axios.get(PATH).then(response=>{
                        console.log("expression response:",response.data.result.expressionProfiles);
                        // this.showVarInfoTable=true;
                    })
                }
    }
    })},
    hiligtDbCols({rowIndex, columnIndex}){
        if(rowIndex===0 && columnIndex>=6){
            return "color: #fff;background-color:rgba(92,165,214,0.7) !important"
        }
    },
  },
  mounted:function () {
    if(window.matchMedia("(max-width: 767px)").matches){
        this.windowSize=1;
       console.log("这是一个移动设备。");

    }else if(window.matchMedia("(max-width: 992px)").matches){
        this.windowSize=2;
        console.log("这是平板电脑或台式电脑。");

    }else{
        this.windowSize=3;
        console.log("大屏");
    }

    // let loadingService = Loading.service({
    //     lock: true,
    //     text: 'Loading',
    //     spinner: 'el-icon-loading',
    //     background: 'rgba(0, 0, 0, 0.7)',
    //     fullscreen:true
    // });
    let geneName = this.$route.query.hdbId;
    // 查询go，var，trait需要ensembl gene id
    let taxonId=this.$route.query.taxonId;
    let ensemblGeneId;
    // let geneSymbol;
    //   跳转链接
    //   http://www.treefam.org/family/TF328386
    
    // let geneName="770642"
    this.$axios
    // 获取gene basic info的接口，目前是查询entrez id
    // 返回两个list数据，0是gbiinfo，1是ortholist
      .get('http://localhost:9401/api/gene-detail',{params: {'hdbid': geneName, 'taxonid': taxonId}})
      .then(response => {
        this.loading=false;
        let genedetail = JSON.parse(JSON.stringify(response.data[0]));
        this.geneBasicInfo=genedetail[0];
        console.log("geneBasicInfo:",this.geneBasicInfo);
        ensemblGeneId=this.geneBasicInfo.ensemblGeneId;
        let orthoList = JSON.parse(JSON.stringify(response.data[1]));
        // this.goBsicList=response.data[2];
        // 绘制初始热图
    //    geneSymbol=genedetail[0].geneSymbol;
        this.tableData = genedetail;
        // 存储同源数据
        this.orthoInfo=orthoList;
        this.tableOrthoList=orthoList;
        console.log("orthoList:",orthoList);
        //   将多个结果合并成一个
        if(genedetail.length>1){
            let obj=genedetail[0]
            for(let j=1;j<= genedetail.length;j++){
                let obj2=genedetail[j];
                for(let key in obj2){
                    if(obj[key] !== obj2[key]){
                        obj[key] += ","+obj2[key]
                }
            }
        }
            this.geneBasicInfo=obj
        }
        

    }).finally(()=>{
        this.fullLoading=false;
        let svg_height=480;
        // let geneName="ENSGALG00000023443"
        let specName=this.geneBasicInfo.speciesName.shortName;
        this.classification=this.geneBasicInfo.speciesName.classification;
        
        // console.log("classification:",classification);
        this.$axios.get('http://localhost:9401/api/gene-detail-go',{params: {'geneName': ensemblGeneId,'classification':this.classification}}).then(response=>{
            this.goloading=false;
            this.goList=response.data
            console.log("goList:",this.goList);
            if(response.data.length>0){
            let svgContainer = d3.select("#svg-container-go").append("svg").attr("width", "96%").attr("height", svg_height+60).attr('id', 'svgcontainer-go');
            this.drawGoRectChart(response.data,svgContainer,"go",specName,ensemblGeneId)
            }
        })
        this.$axios.get('http://localhost:9401/api/gene-detail-var',{params: {'geneName': ensemblGeneId}}).then(response=>{
            console.log("var response:",response.data);
            if(response.data.length>0){
            let svgContainer = d3.select("#svg-container-var").append("svg").attr("width", "96%").attr("height", svg_height).attr('id', 'svgcontainer-var');
            // console.log("var response.data:",response.data);
            this.drawGoRectChart(response.data,svgContainer,"var",specName,ensemblGeneId)
            }
        })
        // let traitGeneName="Os05g0556300"
        this.$axios.get('http://localhost:9401/api/gene-detail-trait',{params: {'geneName': ensemblGeneId}}).then(response=>{
            let svgContainer = d3.select("#svg-container-trait").append("svg").attr("width", "96%").attr("height", svg_height).attr('id', 'svgcontainer-trait');
            console.log("trait:",response.data);
            if(response.data.length>0){
                this.drawGoRectChart(response.data,svgContainer,"trait",specName,ensemblGeneId)
            }
        })
        this.$axios.get('http://localhost:9401/api/species-list',{params: {'taxonId': ""+taxonId}}).then(response=>{
            console.log("species:",response.data);
            this.species=response.data
        })
        this.$axios.get('http://localhost:9401/api/gene-detail-expression',{params: {'geneName': ensemblGeneId,'classification':this.classification}}).then(response=>{
            let svgContainer = d3.select("#svg-container-expression").append("svg").attr("width", "96%").attr("height", svg_height).attr('id', 'svgcontainer-expression');
            console.log("expression:",response.data);
            // this.species=response.data
            if(response.data.length>0){
                this.drawGoRectChart(response.data,svgContainer,"expression",specName,ensemblGeneId)
            }
        })
    })
        
    
    window.addEventListener("scroll", this.watchScroll);
    this.getTableMaxHeight(); 
    let _this = this;
    window.onresize = function () {//用于使表格高度自适应的方法  
        _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    }
  },
  
}
</script>
