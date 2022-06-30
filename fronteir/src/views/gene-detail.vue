<template>
<div class="table-container" onscroll="menuScroll()">
    <HeaderBar />
    <Banner />
    <!-- <Navigator />    -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
        <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
        <el-breadcrumb-item>Homolog Gene</el-breadcrumb-item>
        <el-breadcrumb-item>Gene Detail</el-breadcrumb-item>
    </el-breadcrumb>

    <el-container style="border: 1px solid #eee;margin-top: 15px;">
        <el-aside width="240px" style="background-color: aliceblue;overflow-y: auto;">
            <!-- <img src="../assets/img/arrow1.png" style="width:100%;vertical-align:top;margin:10px 0;"> -->
            <el-menu
                default-active="1"
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
            <!-- <el-menu-item index="7">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">Sequence Viewer</span>
            </el-menu-item> -->
        </el-menu>
        </el-aside>
         <!-- </div> -->

    <div class="tables" style="overflow:hidden" v-loading="fullLoading">

        <div class="info-div">
            <div class="basic-info" id="item-pos-1" >
            <!-- <el-card style="width:99%;margin: auto;" shadow="none"> -->
                <!-- <h2 class="b-info">Gene Basic Information</h2> -->
                <div class="var-box var-infoBox">
                    <h3 class="var-title">Gene Basic Information</h3>
                </div>
                <el-card class="card-border">
                    <el-descriptions :column="3" class="descriptions" :size="size"  v-if="JSON.stringify(geneBasicInfo) !=='{}'">
                        <el-descriptions-item label="Ensembl Gene Id"><a target="_blank" :href="'https://ensemblgenomes.org/search/?query='+geneBasicInfo.ensemblGeneId">{{geneBasicInfo.ensemblGeneId}}</a></el-descriptions-item>
                        <el-descriptions-item label="Entrez Gene Id"><a target="_blank" :href="'https://www.ncbi.nlm.nih.gov/gene/?term='+geneBasicInfo.entrezGene">{{geneBasicInfo.entrezGene}}</a>
                        <!-- <img :src="linkIcon" style="width:18px;height:18px;margin-left:5px"> -->
                        </el-descriptions-item>
                        <el-descriptions-item label="Refseq Id">
                            <a v-if="geneBasicInfo.refseqId!=='-'" target="_blank" :href="'https://www.ncbi.nlm.nih.gov/nuccore/'+geneBasicInfo.refseqId">{{geneBasicInfo.refseqId}}</a>
                            <div v-else>{{geneBasicInfo.refseqId}}</div>
                        </el-descriptions-item>
                        <el-descriptions-item label="Gene Synonym">{{geneBasicInfo.geneSynonym}}</el-descriptions-item>
                        <el-descriptions-item label="Gene Type">{{geneBasicInfo.geneType}}</el-descriptions-item>
                        <el-descriptions-item label="Gene Symbol">{{geneBasicInfo.geneSymbol}}</el-descriptions-item>
                        <el-descriptions-item label="Species Name">{{geneBasicInfo.speciesName.commonName}}</el-descriptions-item>
                        <el-descriptions-item label="Chromosom">{{geneBasicInfo.chromosom}}</el-descriptions-item>
                        <el-descriptions-item label="Gene Start">{{geneBasicInfo.geneStart}}</el-descriptions-item>
                        <el-descriptions-item label="Gene End">{{geneBasicInfo.geneEnd}}</el-descriptions-item>
                        <el-descriptions-item label="Gene Description">{{geneBasicInfo.geneDescription}}</el-descriptions-item>
                    </el-descriptions>
                </el-card>
        </div>
        <!-- <el-divider></el-divider> -->
        <div id="item-pos-2">
            <!-- <div class="p-ele top-p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Gene Ortholog Informations</span></div> -->
            <div class="var-box var-infoBox">
                <h3 class="var-title">Gene Homolog Informations</h3>
            </div>
            <el-card class="card-border">
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
                    <el-button icon="el-icon-search"  type="success"  plain id="filter-search" @click="searchFilter(form.species.ortho)">Search</el-button>
                    <el-button icon="el-icon-delete"  type="primary"  plain id="filter-search" @click="clearFilter()">Clear</el-button>
                </div>
                <!-- </el-card> -->
            <el-table
                :data="tableOrthoList"
                class="info-table"
                v-loading="loading"
                :max-height="tableHeight"
                :border="false"
                :default-sort = "{prop: 'commonName', order: 'ascending'}"
                >
                <!-- <el-table-column> -->
                    <!-- <template slot-scope="scope"> -->
                    <el-table-column prop="commonName" label="Species" ></el-table-column>
                    <el-table-column prop="taxId" label="Taxon Id"></el-table-column>
                    <el-table-column prop="ensembId" label="Ensembl Id"  ></el-table-column>
                    <el-table-column prop="entrezId" label="Entrez Id">
                        <template slot-scope="scope">
                            <a :href="'https://www.ncbi.nlm.nih.gov/gene/?term='+scope.row.entrezId" target='_blank'  v-if="scope.row.entrezId!=='-'">
                                {{ scope.row.entrezId }}
                            </a>
                            <div  v-if="scope.row.entrezId=='-'">{{ scope.row.entrezId }}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="geneSymbol" label="Gene Symbol"></el-table-column>
                    <el-table-column prop="hdbGeneId" label="Protein">
                        <template slot-scope="scope" >
                            <a :href="'https://www.uniprot.org/uniprot/'+scope.row.hdbGeneId" target='_blank' v-if="scope.row.hdbGeneId.slice(0,2)!=='EN'">
                                {{ scope.row.hdbGeneId }}
                            </a>
                            <a :href="'https://asia.ensembl.org/Multi/Search/Results?q='+scope.row.hdbGeneId+';site=ensembl'" target='_blank' v-if="scope.row.hdbGeneId.slice(0,2)=='EN'">
                                {{ scope.row.hdbGeneId }}
                            </a>
                        </template>
                    </el-table-column>
                    <el-table-column  label="Data Source" align="center">
                        <template slot-scope="scope">
                            <el-popover
                                placement="top"
                                title="Data Source"
                                width="200"
                                trigger="hover"
                                :content="scope.row.dbEvidence">
                                <i class="el-icon-s-order" slot="reference"></i>
                            </el-popover>
                            
                        </template>
                    </el-table-column>
            </el-table>
            </el-card>
        </div>
        <div id="item-pos-3">
            <div class="var-box var-infoBox">
                    <h3 class="var-title">Gene Ontology Annotations</h3>
            </div>
        <!-- 提示信息不显示 -->
    <el-card class="card-border draw-card">
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
            
                <el-button icon="el-icon-search"  type="success"  plain id="filter-search" @click="compareOrthoGo(form.species.go)">Search</el-button>
                <el-button icon="el-icon-delete"  type="primary"  plain id="filter-search" @click="clearOrthoGo()">Clear</el-button>
        </div>
        <div class="filt-div"></div>
        <div id="svg-container-go"></div>
        <el-table 
            :data="goInfoData"
            id="subTable"
            max-height="500px"
            v-if="showGoTable"
            >
            <el-table-column prop="goId" label="GO Id" align="center" width="130px"></el-table-column>
            <el-table-column prop="goAnnotations" label="GO Name" align="center"></el-table-column>
            <el-table-column prop="topClass" label="GO classification" align="center" width="150px"></el-table-column>
            <el-table-column prop="goList" label="Sub GO Id" align="center" ></el-table-column>
            <el-table-column prop="definition" label="GO Definition" align="center"></el-table-column>
            
                
        </el-table>
    </el-card>
                   
        </div>
        <div id="item-pos-4">
            <div class="var-box var-infoBox">
                <h3 class="var-title">Variants</h3>
            </div>
            <el-card class="card-border">
                <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                    <div><i class="el-icon-question question-icon"></i></div>
                </el-tooltip>
                <div class="filt-div">
                <el-checkbox v-model="checked.var" style="float:left" class="filt-spe-select" :disabled="waitResponse.var">Compare Ortholog Genes</el-checkbox>
                <el-select v-model="form.species.var" multiple placeholder="Show All Species" class="filt-spe-select">
                    <el-option
                        v-for="item in species"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search"  type="success"  plain id="filter-search" @click="compareOrthoVar(form.species.var)">Search</el-button>
                <el-button icon="el-icon-delete"  type="primary"  plain id="filter-search" @click="clearOrthoVar()">Clear</el-button>
                </div>
                <div id="svg-container-var"></div>
                <!-- 根据snplist取值的表 -->
            <el-table
                :data="varList"
                class="info-table"
                v-loading="varLoading"
                :border="false"
                v-if="showVarInfoTable"
            >
                <el-table-column
                    prop="geneName"
                    label="Gene Id"
                    align="center"
                    width="150px"
                    >
                </el-table-column>
                
                <el-table-column
                    prop="rsnpId"
                    label="Var Id"
                    width="120px"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="finalposition"
                    label="Position"
                    width="120px"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="finalallele"
                    label="Allele"
                    width="80px"
                    align="center"
                >
                </el-table-column>
               

                <el-table-column
                    prop="finalmaf"
                    label="Maf"
                    width="120px"
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
                    
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="gene.genealias"
                    label="Gene Symbol"
                    align="center"
                    width="120px"
                   >
                </el-table-column>
                
            </el-table>
            </el-card>
        </div>
        <div id="item-pos-5">
                <!-- <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Traits</span></p> -->
            <div class="var-box var-infoBox var-box var-infoBox">
                <h3 class="var-title">Traits</h3>
            </div>
                    <!-- 提示信息不显示 -->
            <el-card class="card-border">
            <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                <div><i class="el-icon-question question-icon"></i></div>
            </el-tooltip>
            <div class="filt-div">
            <el-checkbox v-model="checked.trait" style="float:left" class="filt-spe-select" :disabled="waitResponse.trait">Compare Ortholog Genes</el-checkbox>
            <el-select v-model="form.species.trait" multiple placeholder="Show All Species" class="filt-spe-select">
                <el-option
                    v-for="item in species"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                    >
                </el-option>
            </el-select>
            <el-button icon="el-icon-search"  type="success" plain id="filter-search" @click="compareOrthoTrait(form.species.trait)">Search</el-button>
            <el-button icon="el-icon-delete"  type="primary" plain id="filter-search" @click="clearOrthoTrait()">Clear</el-button>
            </div>

                <!-- </el-container> -->
            <!-- </el-card> -->
            <div id="svg-container-trait"></div>
            <el-table :data="traitData" class="info-table"  v-loading="goloading" :border="false" v-if="showTraitTable">
                <el-table-column  prop="traitName" label="Trait Name" align="center"></el-table-column>
                <el-table-column  prop="traitId" label="Trait Id" width="80px" align="center"></el-table-column>
                <el-table-column  prop="definition" label="Trait Definition" align="center"></el-table-column>
                <el-table-column  prop="gwasList" label="Gwas Id" align="center"></el-table-column>
                
            </el-table>
            </el-card>
        </div>
        <div id="item-pos-6">
            <div class="var-box var-infoBox">
                    <h3 class=" var-title">Expression</h3>
            </div>
            <el-card class="card-border draw-card">
                    <!-- 提示信息不显示 -->

                    <!-- <el-container direction="vertical" > -->
                        <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                            <div><i class="el-icon-question question-icon"></i></div>
                        </el-tooltip>
                <div class="filt-div">
                <el-checkbox v-model="checked.expression" style="float:left" class="filt-spe-select" :disabled="waitResponse.expression">Compare Ortholog Genes</el-checkbox>
                <el-select v-model="form.species.expression" multiple placeholder="Show All Species" class="filt-spe-select">
                    <el-option
                        v-for="item in species"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search"  type="success" plain id="filter-search" @click="compareOrthoExpre(form.species.expression)">Search</el-button>
                <el-button icon="el-icon-delete"  type="primary" plain id="filter-search" @click="clearOrthoExpre()">Clear</el-button>
                </div>
                <div id="svg-container-expression"></div>

                <el-table :data="expTabledata"  class="info-table"  v-loading="exploading" :border="false" v-if="showExpInfoTable">
                    <el-table-column  prop="geneId" label="Gene Id">
                        <template slot-scope="scope">
                            <a :href="'https://ngdc.cncb.ac.cn/gen/gene/'+scope.row.genOrgId+'/'+scope.row.geneId" target='_blank'>
                                {{ scope.row.geneId }}
                            </a>
                        </template>
                    </el-table-column>
                    <el-table-column  prop="projectId" label="Bioproject Id">
                        <template slot-scope="scope">
                            <a :href="'https://ngdc.cncb.ac.cn/gen/project/accession/'+scope.row.projectId" target='_blank'>
                                {{ scope.row.projectId }}
                            </a>
                        </template>
                    </el-table-column>
                    <el-table-column  prop="tissue" label="Tissue"></el-table-column>
                    <el-table-column  prop="expAvg" label="Average Tpm"></el-table-column>
                </el-table>
            </el-card>
        </div>
        </div>
    </div>
    </el-container>
    <!-- </el-card> -->
    <FooterBar/>
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
<style src="../assets/css/gene-detail.css" scoped></style>
<script>
// @ is an alias to /src
import HeaderBar from '@/components/HeaderBar.vue';
import FooterBar from '@/components/FooterBar.vue';
import Banner from "@/components/banner.vue";
import linkIcon from "@/assets/img/icon/link-icon-4.svg";
import * as d3 from 'd3';
// import { Loading } from 'element-ui';
export default {
  name: 'GeneDetail',
  components: {
    // Navigator,
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
        // goList:[],
        varList:[],
        loading:true,
        goloading:true,
        linkIcon,
        dbEvidence:"",
        form:{"species":{"go":"","ortho":"","var":"","trait":"","expression":""}},
        checked:{"go":"","var":"","trait":"","expression":""},
        species:[],
        navBarFixed:"false",
        tableHeight:700,
        tooltip:"",
        showVarInfoTable:false,
        waitResponse:{"go":false,"var":false,"trait":false,"expression":false},
        clicked:{"go":false,"var":false,"trait":false,"expression":false},
        classification:"",
        windowSize:3,
        fullLoading:true,
        showGoTable:false,
        goInfoData:[],
        expTabledata:[],
        showExpInfoTable:false,
        exploading:false,
        traitData:[],
        showTraitTable:false
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
          let filterOrtho=this.tableOrthoList.filter(item=>{
              return  key.indexOf(""+item.taxId) >-1
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
        d3.selectAll("#group_total_"+key).remove()
        if(key=='go'){svgContainer.attr('height','400')}else{
            svgContainer.attr('height','300')
        }
        if(key=='var'){
            this.showVarInfoTable=false;
        }else if(key=='go'){
            this.showGoTable=false;
        }else if(key=='expression'){
            this.showExpInfoTable=false;
        }
        this.waitResponse[key]=false;
        this.form.species[key]="";
      },
    //   点击show button 调用的函数
      compareFuntion(key,filtSpecies){
        console.log("key,filtSpecies:",key,filtSpecies);
        let filterOrtho;
        if(filtSpecies.length>0){
            filterOrtho=this.orthoInfo.filter(item=>{
                return  filtSpecies.indexOf(""+item.taxId) >-1
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
        console.log("filterOrtho:",filterOrtho);
        let num=0;
        for(let i=0;i<filterOrtho.length;i++){
            let orthoItem=filterOrtho[i];
            //   let classification=orthoItem.species.classification;
            //   if(orthoItem.gbiInfo!=null){
            let hdbId=orthoItem.hdbGeneId;
            let symbol;
            if(orthoItem.geneSymbol!=="-" & orthoItem.geneSymbol!==""){
                // symbol-ensemblgid-entrezid-hdbid
                symbol =orthoItem.geneSymbol
            }else if(orthoItem.ensembId!=="-" & orthoItem.ensembId!==""){
                symbol=orthoItem.ensembId;
            }else if(orthoItem.entrezId!=="-" & orthoItem.entrezId!==""){
                symbol=orthoItem.entrezId;
            }else{
                symbol=orthoItem.hdbGeneId;
            }
           
            let speciesName=orthoItem.commonName.slice(0,3)
            let params= {'hdbId': hdbId}
            if(key=="expression"){params= {'hdbId': hdbId,'classification':this.classification}}
            this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/gene-detail-"+key,{params})
            .then(response=>{
                if(response.data.length>0){
                    num+=response.data.length;
                    console.log("go data:",response.data,"symbol:",symbol);
                    index += 1;
                    let svgContainer=d3.select("#svgcontainer-"+key)
                    let tooltip=d3.select("#tooltip")
                    console.log("response.data:",response.data);
                    this.drawSubRectChart(response.data,svgContainer,key,index,tooltip,speciesName, symbol)
                }
                
            })

            //   }
          }
          setTimeout(() => {
            if(num==0){
                this.$message('No Annotation Data Here');
            }
          }, 1000);
          
        }
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
    geneName=geneName.length>10?geneName.slice(0,10)+"..":geneName;
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
        let text_top=180;
        let text_x=115;
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
                    if(d.goAnnotations.length>40){
                        txtfile=d.goAnnotations.slice(0,40)+"..."
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
        svgContainer.attr('height',`${oriSvgHeight+index*18}`)
        let group_total;
        if(index==0){
            group_total = svgContainer.append('g').attr("id", "group_total_0").attr('transform',`translate(0,${PADDING*index})`)
        }else{
            group_total = svgContainer.append('g').attr("id", "group_total_"+dataclass).attr('transform',`translate(0,${PADDING*index})`)
        }
        let gene_fontsize=12
        group_total.append('text')
            .attr('x',0)
            .attr('y',rect_top+rectHeight/2+5)
            .attr("font-size", gene_fontsize)
            .text(`${geneName}(${speciesName})`)
        let rect_group = group_total.selectAll("rect")
            .data(value)
            .enter()
        let RECT_PADDING=rectHeight;
        let RECT_x=128;
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
        .attr('cursor',d=>d.opacity>0?'pointer':'')
        .on("mousemove", function(d, i){
            let htmlInfo;
            if( dataclass =="go"){
                htmlInfo=`<div><strong>GO Annotation Number: </strong>${i.goNum>0?i.goNum:"-"}</div>
            <div><strong>GO Id: </strong>${i.goId}</div>
            <div><strong>Annotate Subclass GO Id: </strong>${i.goList==null?"-":i.goList.slice(0,2)}${i.goList!==null&&i.goList.length>2?"...":""}</div>
            <div><strong>Go Term: </strong>${i.goAnnotations}</div>
            <div><strong>Go Classification: </strong>${i.topClass}</div>
            <div><strong>Go Definition: </strong>${i.definition}</div>
            `
            }else if( dataclass =="var"){
                 htmlInfo=`<div>Variant Number: ${i.snpNum>0?i.snpNum:"-"}</div>
            <div>Variant List: ${i.snpList==null?"-":i.snpList.slice(0,2)}${i.snpList!==null&&i.snpList.length>2?"...":""}</div>
            <div>Consequence Type: ${i.voAnnotaion}</div>
            <div>Effect: ${i.voClassification}</div>

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
                this.varLoading=true;
                this.showVarInfoTable=true;
                let dataSource=i.dataSource;
                let BASEPATH="";
				if(dataSource != null && dataSource.length >0 ){
                if(dataSource.indexOf("v2")>0){BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV2/v2/variants/getlist?dataSource="}
                else{BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV3/v2/variants/getlist?dataSource="}
				
                let snpAll=i.snpList.join(',')
				if(BASEPATH.length>0){
                let PATH=BASEPATH+dataSource+"&snplist="+snpAll;
                if(i.snpList.length>0){
                    this.$axios.get(PATH).then(response=>{
                        console.log("var response:",response.data.snp);
                        // this.showVarInfoTable=true;
                        // var table绑定返回的snp数据
						this.varList=[];
						if(response.data != null){
                        this.varList=response.data.snp;
                        // for(let item of tresponse.data.snp){}
                        if(this.varList.length){this.varList=response.data.snp;}else{this.varList=[response.data.snp];}
                        for(let item of this.varList){
                                     let pos=item.chrom+":"+item.position;
                                     let allele=item.refallele+"/"+item.allele;
                                     let maf=item.maf+":"+item.maffreq.slice(0,7);
                                     let classsnp="SNP";
										item.finalposition=pos;
          //item.position=pos;
          //item.allele=allele;
          //item.maf=maf;
									item.finalallele=allele;
									item.finalmaf=maf;
                            item.snpClassId=classsnp;
                            if(Array.isArray(item.gene)){
                                let geneName=[]
                                for(let geneitem of item.gene){
                                    geneName.push(geneitem.genename)
                                }
                                item.geneName=geneName.join(",");
                            }else{
                                item.geneName=item.gene.genename
                            }
                        }
                        this.varLoading=false;
						}
                    })
                }
				}
				}
				this.varLoading=false;
            }else if(dataclass=="go"){
                i.goList=i.goList.join(",")
                this.goInfoData=[i]
                // this.goInfoData.goList=listgo
                this.showGoTable=true;
            }else if(dataclass=="trait"){
                console.log("i.snpList:",i.snpList);
                this.showTraitTable=true;
                i.gwasList=i.gwasList.join(",")
                this.traitData=[i]
            }else if(dataclass=="expression"){
                console.log("i.snpList:",i.genOrgid);
                if(i.prjList.length>0){
                this.showExpInfoTable=true;
                this.exploading=true;
                let prjlist=i.prjList.join(',')
                console.log(i.taxonId);
                let PATH="https://ngdc.cncb.ac.cn/gen/api/json/gene/expressions?taxonomyId="+i.taxonId+"&accession="+i.geneId+"&bioProjectId="+prjlist
                this.$axios.get(PATH).then(response=>{
                    console.log("expression response:",response.data.result.expressionProfiles);
                    let expdata=response.data.result.expressionProfiles;
                    let expTabledata=[];
                    for(let keyaa in expdata ){
                        let prjList=expdata[keyaa]
                        let expNum=0;
                        // let sampleNum=0;
                        let tissue={};
                        for(let item of prjList){
                            // sampleNum += 1;
                            expNum += item.tpm;
                            let itemTissue=item.tissue
                            if(Object.keys(tissue).indexOf(itemTissue) == -1 ){
                                tissue[itemTissue]=[expNum]
                            }else{
                                tissue[itemTissue].push(expNum)
                            }
                        }
                        for(let tissueName in tissue){
                            let valueList=tissue[tissueName]
                            let valsum=eval(valueList.join("+"))
                            let expAvg=(valsum/valueList.length).toFixed(4)
                            let item={
                                "projectId":keyaa,
                                "tissue":tissueName,
                                "expAvg":expAvg,
                                "geneId":i.geneId,
                                "genOrgId":i.genOrgid
                            }
                            expTabledata.push(item)
                            
                        }
                    }
                    this.exploading=false;
                    this.expTabledata=expTabledata;
                    console.log("tissue:",expTabledata);
                    
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
    // if(window.matchMedia("(max-width: 767px)").matches){
    //     this.windowSize=1;
    //    console.log("这是一个移动设备。");

    // }else if(window.matchMedia("(max-width: 992px)").matches){
    //     this.windowSize=2;
    //     console.log("这是平板电脑或台式电脑。");

    // }else{
    //     this.windowSize=3;
    //     console.log("大屏");
    // }

    // let loadingService = Loading.service({
    //     lock: true,
    //     text: 'Loading',
    //     spinner: 'el-icon-loading',
    //     background: 'rgba(0, 0, 0, 0.7)',
    //     fullscreen:true
    // });
    let hdbId = this.$route.query.hdbId;
    // 查询go，var，trait需要ensembl gene id
    let taxonId=this.$route.query.taxonId;
    let ensemblGeneId;
    this.$axios
    // 获取gene basic info的接口，目前是查询entrez id
    // 返回两个list数据，0是gbiinfo，1是ortholist
      .get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail',{params: {'hdbId': hdbId, 'taxonid': taxonId}})
      .then(response => {
        this.loading=false;
        let genedetail = response.data[0];
        this.geneBasicInfo=genedetail[0];
        console.log("geneBasicInfo:",this.geneBasicInfo);
        ensemblGeneId=this.geneBasicInfo.ensemblGeneId;
        let orthoList = response.data[1];
        // this.goBsicList=response.data[2];
        // 绘制初始热图
    //    geneSymbol=genedetail[0].geneSymbol;
        this.tableData = genedetail;
        // 存储同源数据
        
        
        let ortho=[]
        let hdblist=[]
        for(let item of  orthoList){
            if(item.orthoPosition==1){
                let commonName = item.commonName1?item.commonName1:"-";
                let ensembId = item.ensembId1?item.ensembId1:"-";
                let entrezId=item.entrezId1?item.entrezId1:"-";
                let geneSymbol=item.geneSymbol1?item.geneSymbol1:"-";
                let hdbGeneId=item.hdbGeneId1?item.hdbGeneId1:"-";
                let taxId=item.taxId1?item.taxId1:"-"
                let dbEvidence=item.dbEvidence
                let orthoitem={
                    "commonName":commonName,
                    "ensembId":ensembId,
                    "entrezId":entrezId,
                    "geneSymbol":geneSymbol,
                    "hdbGeneId":hdbGeneId,
                    "taxId":taxId,
                    "dbEvidence":dbEvidence
                }
                // 去重，保存唯一的hdbid
                if(hdblist.indexOf(hdbGeneId)==-1){
                    ortho.push(orthoitem)
                    hdblist.push(hdbGeneId)
                }
            }else{
                let commonName = item.commonName2?item.commonName2:"-";
                let ensembId = item.ensembId2?item.ensembId2:"-";
                let entrezId=item.entrezId2?item.entrezId2:"-";
                let geneSymbol=item.geneSymbol2?item.geneSymbol2:"-";
                let hdbGeneId=item.hdbGeneId2?item.hdbGeneId2:"-";
                let taxId=item.taxId2?item.taxId2:"-"
                let dbEvidence=item.dbEvidence
                let orthoitem={
                    "commonName":commonName,
                    "ensembId":ensembId,
                    "entrezId":entrezId,
                    "geneSymbol":geneSymbol,
                    "hdbGeneId":hdbGeneId,
                    "taxId":taxId,
                    "dbEvidence":dbEvidence
                }
                ortho.push(orthoitem)
            }
        }
        this.tableOrthoList=ortho;
        this.orthoInfo=ortho;
        console.log("ortho:",ortho);
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
        let symbol=this.geneBasicInfo.geneSymbol;
        this.classification=this.geneBasicInfo.speciesName.classification;

        // console.log("classification:",classification);
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail-go-1',{params: {'hdbId': hdbId}}).then(response=>{
            if(response.data.length>0){
                this.goloading=false;
                // this.goList=response.data
                console.log("goList:",this.goList);
                if(response.data.length>0){
                let svgContainer = d3.select("#svg-container-go").append("svg").attr("width", "1450px").attr("height", svg_height+60).attr('id', 'svgcontainer-go');
                this.drawGoRectChart(response.data,svgContainer,"go",specName,symbol)
                }
            }
        })
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail-var-1',{params: {'hdbId': hdbId}}).then(response=>{
            console.log("var response:",response.data);
            if(response.data.length>0){
            let svgContainer = d3.select("#svg-container-var").append("svg").attr("width", "98%").attr("height", svg_height).attr('id', 'svgcontainer-var');
            // console.log("var response.data:",response.data);
            this.drawGoRectChart(response.data,svgContainer,"var",specName,symbol)
            }
        })
        // let traitGeneName="Os05g0556300"
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail-trait-1',{params: {'hdbId': hdbId}}).then(response=>{
            let svgContainer = d3.select("#svg-container-trait").append("svg").attr("width", "98%").attr("height", svg_height).attr('id', 'svgcontainer-trait');
            console.log("trait:",response.data);
            if(response.data.length>0){
                this.drawGoRectChart(response.data,svgContainer,"trait",specName,symbol)
            }
        })
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/species-list',{params: {'taxonId': ""+taxonId}}).then(response=>{
            console.log("species:",response.data);
            this.species=response.data
        })
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail-expression-1',{params: {'hdbId': hdbId,'classification':this.classification}}).then(response=>{
            let svgContainer = d3.select("#svg-container-expression").append("svg").attr("width", "1450px").attr("height", svg_height).attr('id', 'svgcontainer-expression');
            console.log("expression:",response.data);
            // this.species=response.data
            if(response.data.length>0){
                this.drawGoRectChart(response.data,svgContainer,"expression",specName,ensemblGeneId)
            }
        })
    })


    window.addEventListener("scroll", this.watchScroll);
    // this.getTableMaxHeight();
    // let _this = this;
    // window.onresize = function () {//用于使表格高度自适应的方法
    //     _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
    // }
  },

}
</script>
