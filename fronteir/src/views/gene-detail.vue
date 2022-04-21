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
                <span slot="title">Traits Informations</span>
            </el-menu-item>
            
            <el-menu-item index="5">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">Variants</span>
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
        <div class="left-tab" >
            <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Gene Informations</span></p>
            <el-divider class="divider"></el-divider>
            <!-- <h2 style="clear:left" class="h2-title">Gene Detail Information</h2> -->
            <el-descriptions :column="1" class="descriptions" :size="size" >
                <el-descriptions-item label="Ensembl Gene Id">{{geneBasicInfo.ensemblGeneId}}</el-descriptions-item>
                <el-descriptions-item label="Entrez Gene Id">{{geneBasicInfo.entrezGene}}</el-descriptions-item>
                <el-descriptions-item label="Entrez Gene Id">{{geneBasicInfo.refseqId}}</el-descriptions-item>
                <el-descriptions-item label="Gene Synonym">{{geneBasicInfo.geneSynonym}}</el-descriptions-item>
                <el-descriptions-item label="Gene Type">{{geneBasicInfo.geneType}}</el-descriptions-item>
                <el-descriptions-item label="Gene Symbol">{{geneBasicInfo.geneSymbol}}</el-descriptions-item>
                <el-descriptions-item label="Chromosom">{{geneBasicInfo.chromosom}}</el-descriptions-item>
                <el-descriptions-item label="Gene Start">{{geneBasicInfo.geneStart}}</el-descriptions-item>
                <el-descriptions-item label="Gene End">{{geneBasicInfo.geneEnd}}</el-descriptions-item>
                <el-descriptions-item label="Gene Description">{{geneBasicInfo.geneDescription}}</el-descriptions-item>
            </el-descriptions>
        
        </div>
        <div class="info-div">
            <!-- <el-divider class="divider"></el-divider> -->
            <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Gene Ortholog Informations</span></p>
            <el-divider class="divider"></el-divider>
            <el-card style="padding:2%" shadow="none">
                <div class="filt-div">
                    <el-select v-model="form.species" multiple placeholder="Choose Species" class="filt-spe-select">
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
                    <el-button icon="el-icon-search"  type="primary" round id="filter-search" @click="searchFilter(form.species)"></el-button>
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
                        <el-checkbox v-model="checked.go" style="float:left" class="compare-o-g" @change="compareOrthoGo(checked.go)">Compare Ortholog Genes</el-checkbox>
                        <el-select v-model="form.species" multiple placeholder="Choose Species" class="filt-spe-select-go">
                            <!-- <el-option label="Org1" value="shanghai"></el-option>
                            <el-option label="Org2" value="beijing"></el-option> -->
                            <el-option
                                v-for="item in species"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-container>
                </el-card>
                <div id="svg-container"></div>
            </div>
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
            <div id="item-pos-4">
                <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Variant</span></p>
                <el-divider class="divider"></el-divider>
                <el-card style="padding:2%" shadow="none">
                    <!-- 提示信息不显示 -->
                    
                    <el-container direction="vertical" >
                        <el-tooltip content="Bottom center" placement="hieranoid" effect="light">
                            <div><i class="el-icon-question question-icon"></i></div>
                        </el-tooltip>
                        <el-checkbox v-model="checked.vo" style="float:left" class="compare-o-g" @change="compareOrthoVar(checked.vo)">Compare Ortholog Genes</el-checkbox>
                        <el-select v-model="form.species" multiple placeholder="Choose Species" class="filt-spe-select-go">
                            <!-- <el-option label="Org1" value="shanghai"></el-option>
                            <el-option label="Org2" value="beijing"></el-option> -->
                            <el-option
                                v-for="item in species"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-container>
                </el-card>
                <div id="svg-container-var"></div>
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
                        <el-checkbox v-model="checked.trait" style="float:left" class="compare-o-g" @change="compareOrthoTrait(checked.trait)">Compare Ortholog Genes</el-checkbox>
                        <el-select v-model="form.species" multiple placeholder="Choose Species" class="filt-spe-select-go">
                            <!-- <el-option label="Org1" value="shanghai"></el-option>
                            <el-option label="Org2" value="beijing"></el-option> -->
                            <el-option
                                v-for="item in species"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-container>
                </el-card>
                <div id="svg-container-trait"></div>
            </div>
            <p class="p-ele"><span class="style_categoryLabel__3AJmg style_gene__1pSX1 style_pageCategory__1ur8P" >Expression</span></p>
            <el-divider class="divider"></el-divider>
            <h1 style="text-align:left;font-weight:500;color:darkgray">Expression Informations</h1>
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
        form:{"species":""},
        checked:{"go":"","vo":"","trait":""},
        species:[],
        navBarFixed:"false",
        tableHeight:800
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
          console.log("filterOrtho:",filterOrtho);
          this.tableOrthoList=filterOrtho

      },
      getDetainInfo(){
          console.log("detail");
      },
      compareOrthoGo(value){
          console.log("compare ortho:",value);
      },
      compareOrthoVar(value){
          console.log("compare ortho:",value);
        //   取当前table的同源基因，循环查询对应的var数据，如果有的话，添加一行
        //   for(let i=0;i<this.tableOrthoList.length;i++){
        //       let orthoItem=this.tableOrthoList[i];
        //         console.log("orthoItem:",orthoItem.entrezId);
        //   }
      },
      compareOrthoTrait(value){
          console.log("compare ortho:",value);
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
      //  当滚动超过 90 时，实现吸顶效果
      if (scrollTop > 288) {
        this.navBarFixed = true;
      } else {
        this.navBarFixed = false;
      }
    },
    drawGoRectChart(value,divName,dataclass){
    let svg_height=280;
    let svgContainer = d3.select(divName).append("svg").attr("width", "100%").attr("height", svg_height).attr('id', 'svgcontainer');
    let rect_top=247;
    let RECT_WIDTH=20;
    let rect_height=20;
    
    for(let iii in value ){
        let text_top=142;
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
                .attr("x", "0")
                .attr("y", text_top)
                .text(d => {
                    return  d.goAnnotations
                })
        }else if(dataclass=='vo'){
             textgs.append("text")
            .attr("fill", "black")
            .attr("text-anchor", "start")
            .attr("font-family", "sans-serif")
            .attr("font-size", gene_fontsize)
            .attr("x", "0")
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
            .attr("x", "0")
            .attr("y", text_top)
            .text(d => {
                return  d.trait_name
            })
        }
        }
    

    let group_total = svgContainer.append('g').attr("id", "group_total")
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
        
    
    let rect_group = group_total.selectAll("rect")
        .data(value)
        .enter()
    rect_group.append('rect')
        .attr("x", (d, i) => {
            let startRate = i*22
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
                htmlInfo=`<p>Go Id: ${i.goId}</p>
            <p>Go Term: ${i.goTerm}</p>
            <p>Go Classification: ${i.topClass}</p>
            <p>Go Definition: ${i.definition}</p>
            `
            }else if( dataclass =="vo"){
                 htmlInfo=`<p>Variant Number: ${i.snpNum>0?i.snpNum:"-"}</p>
            <p>Variant List: ${i.snpList.length>0?"-":i.snpList.slice(0,2)}${i.snpList.length>2?"...":""}</p>
            <p>Variant Annotaion: ${i.voAnnotaion}</p>
            <p>Variant Classification: ${i.voClassification}</p>
            
            `
            }else{
                htmlInfo=`<p>Trait Id: ${i.trait_id}</p>
            <p>Trait Name: ${i.trait_name}</p>
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
        .on("click", d=>{console.log(d);})
        ;

    
    },
    hiligtDbCols({rowIndex, columnIndex}){
        if(rowIndex===0 && columnIndex>=4){
            return "color: #fff;background-color:rgba(92,165,214,0.7) !important"
        }
    }
  },
  mounted () {
    //   跳转链接
    //   http://www.treefam.org/family/TF328386
    const axiosInstance = this.$axios.create({
    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
    withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
  });
    axiosInstance
      .get('http://localhost:9401/api/gene-detail',{params: {'geneName': "770642", 'taxonid': "9031"}})
      .then(response => {
        this.loading=false;
        let genedetail = JSON.parse(JSON.stringify(response.data[0]));
        this.geneBasicInfo=genedetail[0];
        let orthoList = JSON.parse(JSON.stringify(response.data[1]));
        this.goBsicList=response.data[2];
        this.drawGoRectChart(response.data[2],"#svg-container","go");
        this.tableData = genedetail;
        // 存储同源数据
        this.orthoInfo=orthoList;
        this.tableOrthoList=orthoList;

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
        let ensid="WBGene00000001"
        this.$axios.get('http://localhost:9401/api/gene-detail-go/'+ensid).then(response=>{
            this.goloading=false;
            this.goList=response.data
        })
        this.$axios.get('http://localhost:9401/api/gene-detail-var',{params: {'geneName': "ENSGALG00000023443"}}).then(response=>{
            console.log("var response:",response.data);
            this.drawGoRectChart(response.data,"#svg-container-var","vo")
        })
        this.$axios.get('http://localhost:9401/api/gene-detail-trait').then(response=>{
            this.drawGoRectChart(response.data,"#svg-container-trait","trait")
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
