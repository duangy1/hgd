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
    
    
        <!-- <el-card  shadow="none">
            <div  class="tab-list"> -->
    <el-container style="border: 1px solid #eee;margin-top: 15px;">
        <el-aside width="250px" style="background-color: aliceblue;">
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
    
    <div class="tables" style="overflow:hidden">
        <div class="basic-info" id="item-pos-1" >
            <el-card style="width:95%" shadow="none">
                <!-- <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Gene Informations</span></p> -->
                
                <!-- <el-divider class="divider"></el-divider> -->
                <h2 class="b-info">Basic Information</h2>
                <!-- <h2 style="clear:left" class="h2-title">Gene Detail Information</h2> -->
                <el-descriptions :column="1" class="descriptions" :size="size" >
                    <el-descriptions-item label="Ensembl Gene Id">{{geneBasicInfo.ensemblGeneId}}</el-descriptions-item>
                    <el-descriptions-item label="Entrez Gene Id">{{geneBasicInfo.entrezGene}}</el-descriptions-item>
                    <el-descriptions-item label="Entrez Gene Id">{{geneBasicInfo.refseqId}}</el-descriptions-item>
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
        <div class="info-div">
            <!-- <el-divider class="divider"></el-divider> -->
            <div id="item-pos-2">
                <p class="p-ele top-p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Gene Ortholog Informations</span></p>
                <el-divider class="divider"></el-divider>
                <el-card style="padding:2%" shadow="none">
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
                        <el-button icon="el-icon-search"  type="primary" round id="filter-search" @click="searchFilter(form.species.ortho)"></el-button>
                    </div>
                </el-card>
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
                        prop="ensemblId"
                        label="EnsemblId">
                    </el-table-column>
                    <el-table-column
                        prop="protein"
                        label="Protein">
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
                <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Gene Ontology Annotations</span></p>
                <el-divider class="divider"></el-divider>
                <!-- 这里 -->
                <el-card style="padding:2%" shadow="none">
                    <!-- 提示信息不显示 -->
                    
                    <el-container direction="vertical" >
                        <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                            <div><i class="el-icon-question question-icon"></i></div>
                        </el-tooltip>
                        
                        <el-checkbox v-model="checked.go" style="float:left" class="compare-o-g" :disabled="waitResponse.go">Compare Ortholog Genes</el-checkbox>
                        <div class="filt-div-info">
                        <el-select v-model="form.species.go" multiple placeholder="Choose Species" class="filt-spe-select-go">
                            <!-- <el-option label="Org1" value="shanghai"></el-option>
                            <el-option label="Org2" value="beijing"></el-option> -->
                            <el-option
                                v-for="item in species"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                        <el-button type="success" plain id="filter-search" class="clear-btn" @click="compareOrthoGo(form.species.go)">Show</el-button>
                        <el-button type="primary" plain class="clear-btn"  @click="clearOrthoGo()">clear</el-button>
                        </div>
                    </el-container>
                    
                </el-card>
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
                <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Variant</span></p>
                <el-divider class="divider"></el-divider>
                <el-card style="padding:2%" shadow="none">
                    <!-- 提示信息不显示 -->
                    
                    <el-container direction="vertical" >
                        <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                            <div><i class="el-icon-question question-icon"></i></div>
                        </el-tooltip>
                        <el-checkbox v-model="checked.var" style="float:left" class="compare-o-g" :disabled="waitResponse.var">Compare Ortholog Genes</el-checkbox>
                        <div class="filt-div-info">
                            <el-select v-model="form.species.var" multiple placeholder="Choose Species" class="filt-spe-select-go">
                                <!-- <el-option label="Org1" value="shanghai"></el-option>
                                <el-option label="Org2" value="beijing"></el-option> -->
                                <el-option
                                    v-for="item in species"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                            <el-button type="success" plain id="filter-search" class="clear-btn" @click="compareOrthoVar(form.species.var)">Show</el-button>
                            <el-button type="primary" plain class="clear-btn" @click="clearOrthoVar()">clear</el-button>
                        </div>
                    </el-container>
                </el-card>
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
                    label="RSnp Id">
                </el-table-column>
                <el-table-column
                    prop="allele"
                    label="Allele"
                >
                </el-table-column>
                <el-table-column
                    prop="chrom"
                    label="Chromosom"
                >
                </el-table-column>
                <el-table-column
                    prop="clinvarFlag"
                    label="Clinvar Flag">
                </el-table-column>
                
                <el-table-column
                    prop="gwasFlag"
                    label="Gwas Flag">
                </el-table-column>
                <el-table-column
                    prop="maf"
                    label="Maf">
                </el-table-column>
                <el-table-column
                    prop="maffreq"
                    label="Maf Frequency">
                </el-table-column>
                <!-- <el-table-column
                    prop="omimFlag"
                    label="Omim Flag">
                </el-table-column>
                <el-table-column
                    prop="pharamFlag"
                    label="Pharam Flag">
                </el-table-column> -->
                <el-table-column
                    prop="position"
                    label="Position">
                </el-table-column>
                <el-table-column
                    prop="refallele"
                    label="Ref Allele">
                </el-table-column>
                <el-table-column
                    prop="refsnpname"
                    label="Ref Snp Name">
                </el-table-column>
                <el-table-column
                    prop="rowcount"
                    label="Row Count">
                </el-table-column>
                <el-table-column
                    prop="rsid"
                    label="Rsid">
                </el-table-column>
                
                <el-table-column
                    prop="snpClassId"
                    label="Snp Class Id">
                </el-table-column>
                <el-table-column
                    prop="snpEnd"
                    label="Snp End">
                </el-table-column>
                <el-table-column
                    prop="snpStart"
                    label="Snp Start">
                </el-table-column>
                <el-table-column
                    prop="gene.genealias"
                    label="Gene Symbol">
                </el-table-column>
                <el-table-column
                    prop="gene.conseqtype"
                    label="Gene Type">
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
                <el-card style="padding:2%" shadow="none">
                    <!-- 提示信息不显示 -->
                    
                    <el-container direction="vertical" >
                        <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                            <div><i class="el-icon-question question-icon"></i></div>
                        </el-tooltip>
                        <el-checkbox v-model="checked.trait" style="float:left" class="compare-o-g" :disabled="waitResponse.trait">Compare Ortholog Genes</el-checkbox>
                        <div class="filt-div-info">
                            <el-select v-model="form.species.trait" multiple placeholder="Choose Species" class="filt-spe-select-go">
                                <!-- <el-option label="Org1" value="shanghai"></el-option>
                                <el-option label="Org2" value="beijing"></el-option> -->
                                <el-option
                                    v-for="item in species"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                            <el-button type="success" plain id="filter-search" class="clear-btn"  @click="compareOrthoTrait(form.species.trait)">Show</el-button>
                            <el-button type="primary" plain class="clear-btn" @click="clearOrthoTrait()">clear</el-button>
                            
                        </div>
                    </el-container>
                </el-card>
                <div id="svg-container-trait"></div>
            </div>
            <div id="item-pos-5">
            <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Expression</span></p>
            <el-divider class="divider"></el-divider>
            <h1 style="text-align:left;font-weight:500;color:darkgray">Expression Informations</h1>
            </div>
        </div>
    </div>
    </el-container>
    <!-- </el-card> -->
    <FooterBar/>
</div>

</template>
<style src="../assets/css/gene-detail.css" scoped></style>
<script>
// @ is an alias to /src
import HeaderBar from '@/components/HeaderBar.vue';
import FooterBar from '@/components/FooterBar.vue';
import Navigator from '@/components/navigator.vue';
import Banner from "@/components/banner.vue";
import pantherIcon from "@/assets/img/icon/P.svg";
import omaIcon from "@/assets/img/icon/M.svg";
import inparanoidIcon from "@/assets/img/icon/ii.svg";
import eggnogIcon from "@/assets/img/icon/eee.svg";
import hieranoidIcon from "@/assets/img/icon/H.svg";
import treefamIcon from "@/assets/img/icon/t.svg";
import * as d3 from 'd3'
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
        pantherIcon,
        omaIcon,
        inparanoidIcon,
        eggnogIcon,
        hieranoidIcon,
        treefamIcon,
        dbEvidence:"",
        form:{"species":{"go":"","ortho":"","var":"","trait":""}},
        checked:{"go":"","var":"","trait":""},
        species:[],
        navBarFixed:"false",
        tableHeight:800,
        tooltip:"",
        showVarInfoTable:false,
        waitResponse:{"go":false,"var":false,"trait":false},
        clicked:{"go":false,"var":false,"trait":false}
        }
    },

    methods: {
      indexMethod(index) {
        return index * 1;
      },
      addTag(value){
          console.log("value:",value);
      },
      selectMenu(key){
        console.log("select menu");
        const navHeight = document.querySelector('.el-aside').offsetHeight
        // scrollIntoView() js原生方法，实现锚点滚动过渡
        const target = document.querySelector("#item-pos-"+key)
        target.scrollIntoView({ behavior: 'smooth' })
        // scrollTo() 把内容滚动到指定的坐标。减去导航高度的目的：导航用定位固定在顶部，如果不减去，导航栏会遮挡一部分内容
        console.log("target.offsetTop - navHeight",target.offsetTop ,navHeight);
        window.scrollTo(0, target.offsetTop)
      },
      searchFilter(key){
          let filterOrtho=this.orthoInfo.filter(item=>{
              return  key.indexOf(""+item.tax) >-1
          })
        //   console.log("filterOrtho:",filterOrtho);
          this.tableOrthoList=filterOrtho

      },
      getDetainInfo(){
          console.log("detail");
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
              let orthoItem=this.tableOrthoList[i];
              if(orthoItem.ensemblId!==null){
                let speciesName=orthoItem.species.shortName
                this.$axios.get("http://localhost:9401/api/gene-detail-"+key,{params: {'geneName': orthoItem.ensemblId}})
                .then(response=>{
                    index += 1;
                    let svgContainer=d3.select("#svgcontainer-"+key)
                    let tooltip=d3.select("#tooltip")
                    
                    this.drawSubRectChart(response.data,svgContainer,key,index,tooltip,speciesName,orthoItem.ensemblId)
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
    //   menuScroll(){console.log("scroll");},
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
    console.log("indexs:",value,svgContainer,dataclass,speciesName,geneName);
    // let svg_height=280;
    // let svgContainer = d3.select(divName).append("svg").attr("width", "100%").attr("height", svg_height).attr('id', 'svgcontainer');
    
    // 绘制文字部分
    let tooltip = d3.select('.tables')
        .append('div')
        .attr("id", "tooltip")
        .style('position', 'absolute')
        .style('z-index', '10')
        .style('display','none')
        .style('font-size', '16px')
        .style('text-align','left')
        .style('padding', '5px')
        .style('border-radius',"5%")
        .style('color','white')
        .style('background-color','black')
    for(let iii in value ){
        let text_top=222;
        let text_x=240;
        let item=value[iii]
        let group_text = svgContainer.append('g').attr("id", "text_group"+iii).attr("transform","translate("+(iii*22-88)+","+text_top+") rotate(-45)")
        let gene_fontsize=14
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
                    return  d.goAnnotations
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
        }else{
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
    this.drawSubRectChart(value,svgContainer,dataclass,0,tooltip,speciesName,geneName)
    
    },
    // 封装绘制rect方法
    drawSubRectChart(value,svgContainer,dataclass,index,tooltip,speciesName,geneName){
        let rect_top=217;
        // console.log("speciesName:",speciesName);
        let RECT_WIDTH=20;
        let rect_height=20;
        let PADDING=30;
        svgContainer.attr('height',`${300+index*35}`)
        let group_total;
        if(index==0){
            group_total = svgContainer.append('g').attr("id", "group_total_0").attr('transform',`translate(0,${PADDING*index})`)

        }else{
            group_total = svgContainer.append('g').attr("id", "group_total").attr('transform',`translate(0,${PADDING*index})`)
        }
        group_total.append('text')
            .attr('x',0)
            .attr('y',rect_top+rect_height/2+5)
            .text(`${geneName}(${speciesName})`)
        let rect_group = group_total.selectAll("rect")
            .data(value)
            .enter()
    rect_group.append('rect')
        .attr("x", (d, i) => {
            let startRate = 230+i*22
            return startRate
        })
        .attr("y", rect_top )
        .attr("width", RECT_WIDTH )
        .attr("height", rect_height )
        .attr("fill", d=>"rgba(141,192,252,"+(d.opacity+0.03)+")")
        .attr('stroke',"darkgray")
        .attr('cursor',d=>d.snpNum>0?'pointer':'')
        .on("mousemove", function(d, i){
            let htmlInfo;   
            if( dataclass =="go"){
                htmlInfo=`<p>Gwas Number: ${i.goNum>0?i.goNum:"-"}</p>
            <p>Gwas List: ${i.goList==null?"-":i.goList.slice(0,2)}${i.goList!==null&&i.goList.length>2?"...":""}</p>
            <p>Go Id: ${i.goId}</p>
            <p>Go Term: ${i.goTerm}</p>
            <p>Go Classification: ${i.topClass}</p>
            <p>Go Definition: ${i.definition}</p>
            `
            }else if( dataclass =="var"){
                 htmlInfo=`<p>Variant Number: ${i.snpNum>0?i.snpNum:"-"}</p>
            <p>Variant List: ${i.snpList==null?"-":i.snpList.slice(0,2)}${i.snpList!==null&&i.snpList.length>2?"...":""}</p>
            <p>Variant Annotaion: ${i.voAnnotaion}</p>
            <p>Variant Classification: ${i.voClassification}</p>
            
            `
            }else{
                htmlInfo=`<p>Gwas Number: ${i.gwasNum>0?i.gwasNum:"-"}</p>
            <p>Gwas List: ${i.gwasList==null?"-":i.gwasList.slice(0,2)}${i.gwasList!==null&&i.gwasList.length>2?"...":""}</p>
            <p>Trait Id: ${i.traitId}</p>
            <p>Trait Name: ${i.traitName}</p>
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
            let classification=this.geneBasicInfo.speciesName.classification;
            let dataSource=i.dataSource;
            let animalPATH="http://192.168.164.14:9042/gvmRESTV2/v2/variants/getlist?dataSource=";
            let plantPATH="http://192.168.164.14:9201/gvmRESTV3/v2/variants/getlist?dataSource=";
            let snpAll=i.snpList.join(',')
            if(i.snpList.length>0){
                if(dataclass=="var"){
                    if(classification=="animal"){
                        let PATH=animalPATH+dataSource+"&snplist="+snpAll;
                        this.$axios.get(PATH).then(response=>{
                            console.log(response);
                            this.showVarInfoTable=!this.showVarInfoTable;
                            // var table绑定返回的snp数据
                            this.varList=response.data.snp;
                        })
                    }else{
                        let PATH=plantPATH+dataSource+"&snplist="+snpAll;
                        this.$axios.get(PATH).then(response=>{
                            console.log(response);
                            this.showVarInfoTable=!this.showVarInfoTable;   
                            // var table绑定返回的snp数据
                            this.varList=response.data.snp;    
                        })
                    }
                }else if(dataclass=="go"){
                    console.log("i.snpList:",i.snpList);
                }else if(dataclass=="trait"){
                    console.log("i.snpList:",i.snpList);
                }
            }
    })
    },
    hiligtDbCols({rowIndex, columnIndex}){
        if(rowIndex===0 && columnIndex>=4){
            return "color: #fff;background-color:rgba(92,165,214,0.7) !important"
        }
    },
    // drawOrthoVar(dataList){
    //     console.log("dataList:",dataList);
    // }
  },
  mounted () {
    
    //   跳转链接
    //   http://www.treefam.org/family/TF328386
    
    let geneName="770642"
    this.$axios
    // 获取gene basic info的接口，目前是查询entrez id
    // 返回两个list数据，0是gbiinfo，1是ortholist
      .get('http://localhost:9401/api/gene-detail',{params: {'geneName': geneName, 'taxonid': "9031"}})
      .then(response => {
        this.loading=false;
        let genedetail = JSON.parse(JSON.stringify(response.data[0]));
        this.geneBasicInfo=genedetail[0];
        console.log("geneBasicInfo:",this.geneBasicInfo);
        let orthoList = JSON.parse(JSON.stringify(response.data[1]));
        // this.goBsicList=response.data[2];
        // 绘制初始热图
        let svg_height=480;
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
        
        // 根据ensemblid获取对应的go数据和var数据等
        // let ensid=this.geneBasicInfo.ensemblGeneId
        // let ensid="WBGene00000001"
        let geneName="ENSGALG00000023443"
        let specName=this.geneBasicInfo.speciesName.shortName;
        let classification=this.geneBasicInfo.speciesName.classification;
        console.log("classification:",classification);
        this.$axios.get('http://localhost:9401/api/gene-detail-go',{params: {'geneName': geneName,'classification':classification}}).then(response=>{
            this.goloading=false;
            this.goList=response.data
            console.log("goList:",this.goList);
            let svgContainer = d3.select("#svg-container-go").append("svg").attr("width", "100%").attr("height", svg_height).attr('id', 'svgcontainer-go');
            this.drawGoRectChart(response.data,svgContainer,"go",specName,geneName)
        })
        this.$axios.get('http://localhost:9401/api/gene-detail-var',{params: {'geneName': geneName}}).then(response=>{
            console.log("var response:",response.data);
            let svg_height=480;
            let svgContainer = d3.select("#svg-container-var").append("svg").attr("width", "100%").attr("height", svg_height).attr('id', 'svgcontainer-var');
            // console.log("var response.data:",response.data);
            this.drawGoRectChart(response.data,svgContainer,"var",specName,geneName)
            
        })
        let traitGeneName="Os05g0556300"
        this.$axios.get('http://localhost:9401/api/gene-detail-trait',{params: {'geneName': traitGeneName}}).then(response=>{
            let svg_height=280;
            let svgContainer = d3.select("#svg-container-trait").append("svg").attr("width", "100%").attr("height", svg_height).attr('id', 'svgcontainer-trait');
            console.log("trait:",response.data);
            this.drawGoRectChart(response.data,svgContainer,"trait",specName,geneName)
        })
        this.$axios.get('http://localhost:9401/api/species-list').then(response=>{
            console.log("species:",response.data);
            this.species=response.data
        })
        this.getTableMaxHeight(); 
        let _this = this;
        window.onresize = function () {//用于使表格高度自适应的方法  
            _this.getTableMaxHeight();//获取容器当前高度，重设表格的最大高度
        }

    })
      .catch(error => {
        console.log(error)
        this.errored = true
      })
      .finally(() => this.loading = false)
    //   导航栏固定
    window.addEventListener("scroll", this.watchScroll);
  }
  
}
</script>
