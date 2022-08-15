<template>
<div class="table-container" onscroll="menuScroll()">
    <HeaderBar />
    <Banner />
    <!-- <Navigator />    -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
        <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/gene' }">Homologous Gene</el-breadcrumb-item>
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
                        <el-descriptions-item label="Ensembl Gene Id"><a target="_blank" :href="'https://www.ensembl.org/id/'+geneBasicInfo.ensemblGeneId">{{geneBasicInfo.ensemblGeneId}}</a></el-descriptions-item>
                        <el-descriptions-item label="Entrez Gene Id"><a target="_blank" :href="'https://www.ncbi.nlm.nih.gov/gene/?term='+geneBasicInfo.entrezGene">{{geneBasicInfo.entrezGene}}</a>
                        <!-- <img :src="linkIcon" style="width:18px;height:18px;margin-left:5px"> -->
                        </el-descriptions-item>
                        <el-descriptions-item label="Refseq Id">
                            <a v-if="geneBasicInfo.refseqId!=='-'" target="_blank" :href="'https://www.ncbi.nlm.nih.gov/nuccore/'+geneBasicInfo.refseqId">{{geneBasicInfo.refseqId}}</a>
                            <div v-else>{{geneBasicInfo.refseqId}}</div>
                        </el-descriptions-item>
                        <el-descriptions-item label="Protein Id">
                            <a :href="'https://www.uniprot.org/uniprot/'+geneBasicInfo.hdbId" target='_blank'>{{geneBasicInfo.hdbId}}</a>
                        </el-descriptions-item>
                        <el-descriptions-item label="Gene Synonym">
                            <div v-if="geneBasicInfo.geneSynonym!==''&& geneBasicInfo.geneSynonym">{{geneBasicInfo.geneSynonym}}</div>
                            <div v-else>-</div>
                        </el-descriptions-item>
                        <el-descriptions-item label="Gene Symbol">
                            <div v-if="geneBasicInfo.geneSymbol!==''&& geneBasicInfo.geneSymbol">{{geneBasicInfo.geneSymbol}}</div>
                            <div v-else>-</div>
                        </el-descriptions-item>
                        <el-descriptions-item label="Gene Type">{{geneBasicInfo.geneType}}</el-descriptions-item>
                        <el-descriptions-item label="Latin Name">{{geneBasicInfo.speciesName.latinName}}</el-descriptions-item>
                        <el-descriptions-item label="Species Common Name">{{geneBasicInfo.speciesName.commonName}}</el-descriptions-item>
                        <el-descriptions-item label="Taxon Id">{{geneBasicInfo.speciesName.taxonId}}</el-descriptions-item>
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
                <h3 class="var-title">Homologous Gene</h3>
            </div>
            <el-card class="card-border">
                <div class="filt-div">
                    <el-select v-model="form.species.ortho" multiple placeholder="Filter by species" class="filt-spe-select">
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
                    <el-button icon="el-icon-search"  type="success"  plain id="filter-search" @click="searchFilter(form.species.ortho)">Filter</el-button>
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
                    <el-table-column prop="taxId" label="Taxon Id">
                        <template slot-scope="scope">
                            <a :href="'https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id='+scope.row.taxId+'&lvl=3&lin=f&keep=1&srchmode=1&unlock'">
                                {{ scope.row.taxId }}
                            </a>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ensemblId" label="Ensembl Id">
                        <template slot-scope="scope">
                            <a target="_blank" :href="'https://www.ensembl.org/id/'+scope.row.ensemblId">{{scope.row.ensemblId}}</a>
                        </template>
                    </el-table-column>
                    <el-table-column prop="entrezId" label="Entrez Id">
                        <template slot-scope="scope">
                            <a :href="'https://www.ncbi.nlm.nih.gov/gene/?term='+scope.row.entrezId" target='_blank'  v-if="scope.row.entrezId!=='-'">
                                {{ scope.row.entrezId }}
                            </a>
                            <div  v-if="scope.row.entrezId=='-'">{{ scope.row.entrezId }}</div>
                        </template>
                    </el-table-column>
                    <!-- <el-table-column prop="geneSymbol" label="Gene Symbol"></el-table-column> -->
                    <el-table-column prop="hdbGeneId" label="Protein">
                        <template slot-scope="scope" >
                            <a :href="'https://www.uniprot.org/uniprot/'+scope.row.hdbGeneId" target='_blank' v-if="scope.row.hdbGeneId.slice(0,2)!=='EN'">
                                {{ scope.row.hdbGeneId }}
                            </a>
                            <a :href="'https://www.ensembl.org/id/'+scope.row.hdbGeneId+';site=ensembl'" target='_blank' v-if="scope.row.hdbGeneId.slice(0,2)=='EN'">
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
                    <h3 class="var-title">Gene Ontology</h3>
            </div>
        <!-- 提示信息不显示 -->
    <el-card class="card-border draw-card">
        <!-- <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
            <div><i class="el-icon-question question-icon"></i></div>
        </el-tooltip> -->

        <div class="filt-div">
            <el-checkbox v-model="checked.go" style="float:left" class="filt-spe-select" :disabled="waitResponse.go" @change="compareOrthoGo(form.species.go)">Compare Ortholog Genes</el-checkbox>
                <el-select v-model="form.species.go" multiple placeholder="Filter by species" class="filt-spe-select">
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
            
                <el-button icon="el-icon-search"  type="success"  plain id="filter-search" @click="compareOrthoGo(form.species.go)">Filter</el-button>
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
                <!-- <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                    <div><i class="el-icon-question question-icon"></i></div>
                </el-tooltip> -->
                <div class="filt-div">
                <el-checkbox v-model="checked.var" style="float:left" class="filt-spe-select" :disabled="waitResponse.var" @change="compareOrthoVar(form.species.go)">Compare Ortholog Genes</el-checkbox>
                <el-select v-model="form.species.var" multiple placeholder="Filter by species" class="filt-spe-select">
                    <el-option
                        v-for="item in species"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search"  type="success"  plain id="filter-search" @click="compareOrthoVar(form.species.var)">Filter</el-button>
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
                max-height="800px"
            >
                <el-table-column
                    prop="geneName"
                    label="Gene Id"
                    align="center"
                    >
                </el-table-column>
                <el-table-column
                    prop="gene.genealias"
                    label="Gene Symbol"
                    align="center"
                >
                 <template slot-scope="scope">
                        <div v-if="scope.row.gene.genealias!==''&& scope.row.gene.genealias">{{scope.row.gene.genealias}}</div>
                        <div v-else>-</div>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="rsnpId"
                    label="Var Id"
                    align="center">
                    <template slot-scope="scope">
                        <a v-bind:href="'https://ngdc.cncb.ac.cn/gvm/snp/getSNPDetail?snpname='+scope.row.rsnpId+'&chrom='+scope.row.chrom+'&position='+scope.row.position+'&orgId='+scope.row.gwasid" target='_blank'>
                        {{ scope.row.rsnpId }}
                        </a>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="finalposition"
                    label="Position"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="finalallele"
                    label="Allele"
                    align="center"
                >
                </el-table-column>
               

                <el-table-column
                    prop="finalmaf"
                    label="MAF"
                    align="center">
                </el-table-column>
              

                <el-table-column
                    prop="snpClassId"
                    label="Class"
                    align="center">
                </el-table-column>
               
                <el-table-column
                    prop="conseqtype"
                    label="Consequence Type/Effect"
                    align="center">
                </el-table-column>
                
                <el-table-column
                    prop="gene.cdnaPosition"
                    label="cDNA Position"
                    align="center">
                </el-table-column>
                
                <el-table-column
                    prop="alleleChange"
                    label="Allele Change"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="gene.proteinpos"
                    label="Protein Position"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="residueChange"
                    label="Residue Change"
                    align="center">
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
            <!-- <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                <div><i class="el-icon-question question-icon"></i></div>
            </el-tooltip> -->
            <div class="filt-div">
            <el-checkbox v-model="checked.trait" style="float:left" class="filt-spe-select" :disabled="waitResponse.trait" @change="compareOrthoTrait(form.species.go)">Compare Ortholog Genes</el-checkbox>
            <el-select v-model="form.species.trait" multiple placeholder="Filter by species" class="filt-spe-select">
                <el-option
                    v-for="item in species"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                    >
                </el-option>
            </el-select>
            <el-button icon="el-icon-search"  type="success" plain id="filter-search" @click="compareOrthoTrait(form.species.trait)">Filter</el-button>
            <el-button icon="el-icon-delete"  type="primary" plain id="filter-search" @click="clearOrthoTrait()">Clear</el-button>
            </div>

                <!-- </el-container> -->
            <!-- </el-card> -->
            <div id="svg-container-trait"></div>
            <!-- <el-table :data="traitData" class="info-table"  v-loading="goloading" :border="false" v-if="showTraitTable">
                <el-table-column  prop="traitName" label="Trait Name" align="center"></el-table-column>
                <el-table-column  prop="traitId" label="Trait Id" width="80px" align="center"></el-table-column>
                <el-table-column  prop="definition" label="Trait Definition" align="center"></el-table-column>
                <el-table-column  prop="gwasList" label="Gwas Id" align="center"></el-table-column>
                
            </el-table> -->
            <el-table 
                :data="traitData"
                id="subTable"
                :row-style="{height: '0'}"
                max-height="800"
                v-loading="goloading"
                v-if="showTraitTable"
              >
                
                <el-table-column prop="toptraitName" label="Trait Name" align="center"></el-table-column>
                <el-table-column prop="gwasGeneId" label="Gene Id" align="center"></el-table-column>
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
        <div id="item-pos-6">
            <div class="var-box var-infoBox">
                    <h3 class=" var-title">Expression</h3>
            </div>
            <el-card class="card-border draw-card">
                    <!-- 提示信息不显示 -->

                    <!-- <el-container direction="vertical" > -->
                        <!-- <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                            <div><i class="el-icon-question question-icon"></i></div>
                        </el-tooltip> -->
                <div class="filt-div">
                <el-checkbox v-model="checked.expression" style="float:left" class="filt-spe-select" :disabled="waitResponse.expression" @change="compareOrthoExpre(form.species.go)">Compare Ortholog Genes</el-checkbox>
                <el-select v-model="form.species.expression" multiple placeholder="Filter by species" class="filt-spe-select">
                    <el-option
                        v-for="item in species"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        >
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search"  type="success" plain id="filter-search" @click="compareOrthoExpre(form.species.expression)">Filter</el-button>
                <el-button icon="el-icon-delete"  type="primary" plain id="filter-search" @click="clearOrthoExpre()">Clear</el-button>
                </div>
                <div id="svg-container-expression"></div>
                <div class="filter-box" v-if="showExpInfoTable"> <div style="line-height:32px">Filter TPM value > </div><el-input v-model="filterValue" size="small"  style="width:100px;" @change=filterExpDara clearable></el-input></div>
                <el-table :data="expTabledata"  class="info-table"  v-loading="exploading" :border="false" v-if="showExpInfoTable" >
                    <el-table-column  prop="geneId" label="Gene Id" align="center">
                        <template slot-scope="scope">
                            <a :href="'https://ngdc.cncb.ac.cn/gen/gene/'+scope.row.genOrgId+'/'+scope.row.geneId" target='_blank'>
                                {{ scope.row.geneId }}
                            </a>
                        </template>
                    </el-table-column>
                    <el-table-column  prop="projectId" label="Bioproject Id" align="center">
                        <template slot-scope="scope">
                            <a :href="'https://ngdc.cncb.ac.cn/gen/project/accession/'+scope.row.projectId" target='_blank'>
                                {{ scope.row.projectId }}
                            </a>
                        </template>
                    </el-table-column>
                    <el-table-column  prop="tissue" label="Tissue" align="center"></el-table-column>
                    <el-table-column  prop="expAvg" label="Average TPM" align="center" sortable></el-table-column>
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
        showTraitTable:false,
        filterValue:""
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
          this.tableOrthoList=filterOrtho

      },
      clearFilter(){
          this.tableOrthoList=this.orthoInfo
      },
      compareOrthoGo(filtSpecies){
        this.clearOrthoGo()
        this.compareFuntion("go",filtSpecies)
      },
      clearOrthoGo(){
        this.cleatFunction('go')
      },
      compareOrthoVar(filtSpecies){
        this.clearOrthoVar()
        this.compareFuntion('var',filtSpecies)

      },
      clearOrthoVar(){
        this.cleatFunction('var')
      },
      compareOrthoTrait(filtSpecies){
        this.clearOrthoTrait()
        this.compareFuntion("trait",filtSpecies)
      },
      clearOrthoTrait(){
        this.cleatFunction('trait')
      },
      compareOrthoExpre(filtSpecies){
        this.clearOrthoExpre()
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
            }else if(orthoItem.ensemblId!=="-" & orthoItem.ensembId!==""){
                symbol=orthoItem.ensemblId;
            }else if(orthoItem.entrezId!=="-" & orthoItem.entrezId!==""){
                symbol=orthoItem.entrezId;
            }else{
                symbol=orthoItem.hdbGeneId;
            }
           let taxonid=orthoItem.taxId
            // let speciesName=orthoItem.commonName.slice(0,3)
            let params= {'hdbId': hdbId}
            console.log("orthoItem.shortName:",orthoItem);
            if(key=="expression"){params= {'hdbId': hdbId,'classification':this.classification}}
            this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/gene-detail-"+key,{params:params})
            .then(response=>{
                if(response.data.length>0){
                    num+=response.data.length;
                    index += 1;
                    let svgContainer=d3.select("#svgcontainer-"+key)
                    let tooltip=d3.select("#tooltip")
                    console.log("orthoItem:",orthoItem);
                    this.drawSubRectChart(response.data,svgContainer,key,index,tooltip,orthoItem.shortName,orthoItem.latinName,orthoItem.commonName, symbol,taxonid,orthoItem.hdbGeneId)
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
drawGoRectChart(value,svgContainer,dataclass,shortName,latinName,commonName,geneName,taxonid,hdbid){
    let rectHeight=18;
    // 绘制文字部分
    let tooltip = d3.select('.tables')
        .append('div')
        .attr("id", "tooltip")
        .style('position', 'absolute')
        .style('z-index', '1000')
        .style('display','none')
        .style('line-height','18px')
        .style('text-align','justify')
        .style('font-size', '14px')
        .style('text-align','left')
        .style('padding', '15px')
        .style('border-radius',"5%")
        .style('color','white')
        .style('background-color','#808080')
    // 用来判断当前类别，不同类别中间间隔开
    let top_class;
    let class_count=0;
    let class_PADDING=30;
    if(dataclass=="var"){top_class=value[0].voClassification;}else if(dataclass=="go"){
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
    this.drawSubRectChart(value,svgContainer,dataclass,0,tooltip,shortName,latinName,commonName,geneName,taxonid,hdbid)

    },
    // 封装绘制rect方法
    drawSubRectChart(value,svgContainer,dataclass,index,tooltip,shortName,latinName,commonName,geneName,taxonid12,hdbid){
        console.log("value:",value);
        // geneName=geneName.length>10?geneName.slice(0,10)+"..":geneName;
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
            .text(`${geneName.length>10?geneName.slice(0,10)+"..":geneName}(${shortName})`)
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
        .attr("fill", d=>d.opacity>0?"rgba(141,192,252,"+(d.opacity+0.2)+")":"rgba(141,192,252,0)")
        .attr('stroke',"darkgray")
        .attr('cursor',d=>d.opacity>0?'pointer':'')
        .on("mousemove", function(d, i){
            let htmlInfo;
            if( dataclass =="go"){
                htmlInfo=`
            <div><strong>Gene Id: </strong>${geneName}</div>
            <div><strong>Species: </strong>${latinName} (${commonName})</div>
            <div><strong>GO Annotation Number: </strong>${i.goNum>0?i.goNum:"-"}</div>
            <div><strong>GO Id: </strong>${i.goId}</div>
            <div><strong>Annotate Subclass GO Id: </strong>${i.goList==null?"-":i.goList.slice(0,2)}${i.goList!==null&&i.goList.length>2?"...":""}</div>
            <div><strong>Go Term: </strong>${i.goAnnotations}</div>
            <div><strong>Go Classification: </strong>${i.topClass}</div>
            <div><strong>Go Definition: </strong>${i.definition}</div>
            `
            }else if( dataclass =="var"){
                 htmlInfo=`
            <div><strong>Gene Id: </strong>${geneName}</div>
            <div><strong>Species: </strong>${latinName} (${commonName})</div>
            <div>Variant Number: ${i.snpNum>0?i.snpNum:"-"}</div>
            <div>Variant List: ${i.snpList==null?"-":i.snpList.slice(0,2)}${i.snpList!==null&&i.snpList.length>2?"...":""}</div>
            <div>Consequence Type: ${i.voAnnotaion}</div>
            <div>Effect: ${i.voClassification}</div>

            `
            }else if( dataclass =="expression"){
                htmlInfo=`
            <div><strong>Gene Id: </strong>${geneName}</div>
            <div><strong>Species: </strong>${latinName} (${commonName})</div>
            <div>Expression Project Number: ${i.prjNum>0?i.prjNum:"-"}</div>
            <div>Bioproject List: ${i.prjList==null?"-":i.prjList.slice(0,2)}${i.prjList!==null&&i.prjList.length>2?"...":""}</div>
            <div>Expression Classification: ${i.eoClassification}</div>
            <div>Expression Annotation: ${i.eoAnnotation}</div>
            `
            }else {
                htmlInfo=`
            <div><strong>Gene Id: </strong>${geneName}</div>
            <div><strong>Species: </strong>${latinName} (${commonName})</div>
            <div>Gwas Number: ${i.gwasNum>0?i.gwasNum:"-"}</div>
            <div>Gwas List: ${i.gwasList==null?"-":i.gwasList.slice(0,2)}${i.gwasList!==null&&i.gwasList.length>2?"...":""}</div>
            <div>Trait Id: ${i.traitId}</div>
            <div>Trait Name: ${i.traitName}</div>
            `
            }
            let pos_y=d.pageY+20;
            let WIDTH=350
            let pos_x=d.pageX+20;
            if(pos_x+WIDTH>d.clientX){
                pos_x-=WIDTH
            }
            
            tooltip.interrupt().style("display", "block").html(htmlInfo)
                .style("opacity","0.8")
                .style("border","none")
                .style('text-align','left')
                .style('top', pos_y+"px")
                .style('left',pos_x+"px")
                .style('width',WIDTH+"px")
        })
        .on('mouseout', function () {
            tooltip.style('display', 'none')
        })
        .on("click", (d,i)=>{
            // let classification=this.geneBasicInfo.speciesName.classification;
            console.log("d,i:",d,i);
            if(dataclass=="var"){
                if(i.snpList.length>0){
                    this.varLoading=true;
                    this.showVarInfoTable=true;
                    let dataSource=i.dataSource;

                    this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/species-gwasVar",{params:{'taxonId': taxonid12,"hdbId":hdbid}}).then(response=>{
                        // gwasid是用来加链接的，varid加到gvm的链接
                        let gwasid=response.data.gwasOrgid;
                        let gwasGeneId=response.data.gwasGeneId;

                        let BASEPATH="";
                        if(dataSource != null && dataSource.length >0 ){
                        if(dataSource.indexOf("v2")>0){BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV2/v2/variants/getlist?dataSource="}
                        else{BASEPATH="https://ngdc.cncb.ac.cn/gvmRESTV3/v2/variants/getlist?dataSource="}
                        
                        let snpAll=i.snpList.join(',')
                        if(BASEPATH.length>0){
                        let PATH=BASEPATH+dataSource+"&snplist="+snpAll+"&geneId="+gwasGeneId;
                        if(i.snpList.length>0){
                            this.$axios.get(PATH).then(response=>{
                                // this.showVarInfoTable=true;
                                // var table绑定返回的snp数据
                                console.log("response:",response.data);
                                this.varList=[];
                                if(response.data != null){
                                this.varList=response.data.snp;
                                console.log("this.rsnpId:",this.varList);
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
                                    item.gwasid=gwasid;
                                    item.alleleChange=item.gene.sourceAllele+"=>"+item.gene.targetAllele;
                                    item.residueChange=item.gene.sourceResidue+"=>"+item.gene.targetResidue;
                                    if(Array.isArray(item.gene)){
                                        let geneName=[]
                                        let consequence_type=[]
                                        for(let geneitem of item.gene){
                                            geneName.push(geneitem.genename)
                                            if(consequence_type.indexOf(geneitem.conseqtype)==-1){
                                                consequence_type.push(geneitem.conseqtype)
                                            }
                                        }
                                        item.geneName=geneName.join(",");
                                        item.conseqtype=consequence_type.join(",");
                                    }else{
                                        item.geneName=item.gene.genename
                                        item.conseqtype=item.gene.conseqtype
                                    }
                                }
                                this.varLoading=false;
                                }
                            })
                        }
                        }
                        }
                        this.varLoading=false;
                    })
                }
            }else if(dataclass=="go"){
                if(i.goList.length>0){
                    i.goList=i.goList.join(",")
                    this.goInfoData=[i]
                    // this.goInfoData.goList=listgo
                    this.showGoTable=true;
                }
            }else if(dataclass=="trait"){
                if(i.gwasList.length>0){
                    this.goloading=true;
                    this.showTraitTable=true;
                    let gwaslist=i.gwasList.join(",")
                    // this.traitData=[i]
                    this.$axios.get("https://ngdc.cncb.ac.cn/hapi/api/species-gwasorg",{params:{'taxonId': taxonid12,"hdbId":hdbid}}).then(response=>{
                        let gwasid=response.data.gwasOrgid;
                        let gwasGeneId=response.data.gwasGeneId;
                        this.$axios.get("https://ngdc.cncb.ac.cn/gapi/gwas/gwasids?gwasId="+gwaslist+"&organismId="+gwasid+"&offset=0&pagesize=10&total=10&geneId="+gwasGeneId)
                        .then(response=>{
                            let resDara=response.data
                            for(let item of resDara){
                                item.gwasGeneId=gwasGeneId;
                                item.toptraitName=i.traitName;
                            }
                            this.traitData=resDara;
                            // console.log("this.traitData:",this.traitData);
                        }).finally(()=>{
                            setTimeout(() => {
                            this.goloading=false;
                        }, 100);
                    })
                        
                    })
                   
                }
            }else if(dataclass=="expression"){
                if(i.prjList.length>0){
                    this.showExpInfoTable=true;
                    this.exploading=true;
                    let prjlist=i.prjList.join(',')
                    let PATH="https://ngdc.cncb.ac.cn/gen/api/json/gene/expressions?taxonomyId="+i.taxonId+"&accession="+i.geneId+"&bioProjectId="+prjlist
                    this.$axios.get(PATH).then(response=>{
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
                                    "expAvg":Number(expAvg),
                                    "geneId":i.geneId,
                                    "genOrgId":i.genOrgid
                                }
                                expTabledata.push(item)
                                
                            }
                        }
                        this.exploading=false;
                        this.oriexpTabledata=expTabledata;
                        this.expTabledata=expTabledata;
                        
                    })
                }
    }
    })},
    hiligtDbCols({rowIndex, columnIndex}){
        if(rowIndex===0 && columnIndex>=6){
            return "color: #fff;background-color:rgba(92,165,214,0.7) !important"
        }
    },
    filterExpDara(){
        console.log("filterExpDara:",this.filterValue);
        console.log("expTabledata:",this.expTabledata);
        this.expTabledata= this.oriexpTabledata.filter(item=>{
            return Number(item.expAvg)>Number(this.filterValue)
        })
    },
    showOriExp(){
        this.expTabledata= this.oriexpTabledata
    }
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
    // let ensemblGeneId;
    this.$axios
    // 获取gene basic info的接口，目前是查询entrez id
    // 返回两个list数据，0是gbiinfo，1是ortholist
      .get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail',{params: {'hdbId': hdbId, 'taxonid': taxonId}})
      .then(response => {
        this.loading=false;
        let genedetail = response.data[0];
        this.geneBasicInfo=genedetail[0];
        // ensemblGeneId=this.geneBasicInfo.ensemblGeneId;
        let orthoList = response.data[1];
        console.log("orthoList:",orthoList);
        // this.goBsicList=response.data[2];
        // 绘制初始热图
    //    geneSymbol=genedetail[0].geneSymbol;
        this.tableData = genedetail;
        // 存储同源数据
        
        
        let ortho=[]
        let hdblist=[]
        for(let item of  orthoList){
            console.log("item:",item);
            if(item.orthoPosition==1){
                let commonName = item.commonName1?item.commonName1:"-";
                let ensemblId = item.ensemblId?item.ensemblId:"-";
                let entrezId=item.entrezId1?item.entrezId1:"-";
                let geneSymbol=item.geneSymbol?item.geneSymbol:"-";
                let hdbGeneId=item.hdbGeneId1?item.hdbGeneId1:"-";
                let taxId=item.taxId1?item.taxId1:"-";
                let shortName=item.shortName1;
                let dbEvidence=item.dbEvidence;
                let latinName=item.latinName1
                let orthoitem={
                    "commonName":commonName,
                    "ensemblId":ensemblId,
                    "entrezId":entrezId,
                    "geneSymbol":geneSymbol,
                    "hdbGeneId":hdbGeneId,
                    "taxId":taxId,
                    "dbEvidence":dbEvidence,
                    "shortName":shortName,
                    "latinName":latinName
                }
                // 去重，保存唯一的hdbid
                if(hdblist.indexOf(hdbGeneId)==-1){
                    ortho.push(orthoitem)
                    hdblist.push(hdbGeneId)
                }
            }else{
                let commonName = item.commonName2?item.commonName2:"-";
                let ensemblId = item.ensemblId?item.ensemblId:"-";
                let entrezId=item.entrezId2?item.entrezId2:"-";
                let geneSymbol=item.geneSymbol2?item.geneSymbol2:"-";
                let hdbGeneId=item.hdbGeneId2?item.hdbGeneId2:"-";
                let taxId=item.taxId2?item.taxId2:"-"
                let dbEvidence=item.dbEvidence;
                let shortName=item.shortName2;
                let latinName=item.latinName2;
                let orthoitem={
                    "commonName":commonName,
                    "ensemblId":ensemblId,
                    "entrezId":entrezId,
                    "geneSymbol":geneSymbol,
                    "hdbGeneId":hdbGeneId,
                    "taxId":taxId,
                    "dbEvidence":dbEvidence,
                    "shortName":shortName,
                    "latinName":latinName
                }
                if(hdblist.indexOf(hdbGeneId)==-1){
                    ortho.push(orthoitem)
                    hdblist.push(hdbGeneId)
                }
            }
        }
        this.tableOrthoList=ortho;
        this.orthoInfo=ortho;
        //   将多个结果合并成一个
        if(genedetail.length>1){
            let obj=genedetail[0]
            for(let j=1;j<= genedetail.length;j++){
                let obj2=genedetail[j];
                for(let key in obj2){
                    if(key !== "speciesName"){
                        if(obj[key] !== obj2[key]){
                            let newVal =obj[key]+ ","+obj2[key]
                            obj[key]=newVal
                        }
                    }
            }
        }
            this.geneBasicInfo=obj
        }
            console.log("this.geneBasicInfo:",this.geneBasicInfo);

    }).finally(()=>{
        this.fullLoading=false;
        let svg_height=480;
        // let geneName="ENSGALG00000023443"
        let specName=this.geneBasicInfo.speciesName.shortName;
        let latinName=this.geneBasicInfo.speciesName.latinName;
        let commonName=this.geneBasicInfo.speciesName.commonName;
        // let hdbid=this.geneBasicInfo.hdbGeneId;
        let symbol;
        if(this.geneBasicInfo.geneSymbol.length>0){
            symbol=this.geneBasicInfo.geneSymbol;
        }else{
            symbol=this.geneBasicInfo.ensemblGeneId;
        }
        this.classification=this.geneBasicInfo.speciesName.classification;
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail-go-1',{params: {'hdbId': hdbId}}).then(response=>{
            if(response.data.length>0){
                this.goloading=false;
                // this.goList=response.data
                if(response.data.length>0){
                let svgContainer = d3.select("#svg-container-go").append("svg").attr("width", "1450px").attr("height", svg_height+60).attr('id', 'svgcontainer-go');
                this.drawGoRectChart(response.data,svgContainer,"go",specName,latinName,commonName,symbol,this.geneBasicInfo.taxonId,this.geneBasicInfo.hdbGeneId)
                }
            }
        })
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail-var-1',{params: {'hdbId': hdbId}}).then(response=>{
            if(response.data.length>0){
            let svgContainer = d3.select("#svg-container-var").append("svg").attr("width", "98%").attr("height", svg_height).attr('id', 'svgcontainer-var');
            this.drawGoRectChart(response.data,svgContainer,"var",specName,latinName,commonName,symbol,this.geneBasicInfo.taxonId,this.geneBasicInfo.hdbGeneId)
            }
        })
        // let traitGeneName="Os05g0556300"
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail-trait-1',{params: {'hdbId': hdbId}}).then(response=>{
            let svgContainer = d3.select("#svg-container-trait").append("svg").attr("width", "98%").attr("height", svg_height).attr('id', 'svgcontainer-trait');
            if(response.data.length>0){
                this.drawGoRectChart(response.data,svgContainer,"trait",specName,latinName,commonName,symbol,this.geneBasicInfo.taxonId,this.geneBasicInfo.hdbGeneId)
            }
        })
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/species-list',{params: {'taxonId': ""+taxonId}}).then(response=>{
            this.species=response.data
        })
        this.$axios.get('https://ngdc.cncb.ac.cn/hapi/api/gene-detail-expression-1',{params: {'hdbId': hdbId,'classification':this.classification}}).then(response=>{
            let svgContainer = d3.select("#svg-container-expression").append("svg").attr("width", "1450px").attr("height", svg_height).attr('id', 'svgcontainer-expression');
            // this.species=response.data
            if(response.data.length>0){
                this.drawGoRectChart(response.data,svgContainer,"expression",specName,latinName,commonName,symbol,this.geneBasicInfo.taxonId,this.geneBasicInfo.hdbGeneId)
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
