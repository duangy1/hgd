<template>
  <div>
    <HeaderBar />
    <Banner />
    <!-- <Navigator /> -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item>Documentation</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card style="width:92%;border-top: 2px solid rgb(64, 158, 255)!important;margin: auto;">
    
        <div >
            <div style="font-size:18px;text-align:left;padding:10px 20px;font-weight: 700;">Documentation</div>
        <!-- <div class="container"> -->
            <div class="wrapper-box">
                <el-container>
                    <el-aside width="250px" style="overflow: hidden;">
                        <!-- <img src="./assets/img/arrow1.png" style="width:100%;vertical-align:top;margin:10px 0;"> -->
                        <el-menu
                            default-active="2"
                            style="text-align: left;"
                            width="100%"
                            :class="navBarFixed == true ? 'navBarWrap' :''"
                            @select="selectMenu">
                        <el-menu-item index="1">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">1.Introduction</span>
                        </el-menu-item>
                        <el-menu-item index="2">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">2.Data Browse</span>
                        </el-menu-item>
                        <el-menu-item index="3">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">2.1 Homologous Gene</span>
                        </el-menu-item>
                        
                        
                        <el-menu-item index="4">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">2.2 Trait</span>
                        </el-menu-item>
                        <el-menu-item index="5">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">2.3 Variant</span>
                        </el-menu-item>
                        <el-menu-item index="6">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">2.4 Expression</span>
                        </el-menu-item>
                        <el-menu-item index="7">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">2.5 Gene Ontology</span>
                        </el-menu-item>
                        <el-menu-item index="8">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">2.6 Gene Detail Information</span>
                        </el-menu-item>
                         <el-menu-item index="9">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">2.7 Species</span>
                        </el-menu-item>
                        <el-menu-item index="10">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">3. Search</span>
                        </el-menu-item>
                        <el-menu-item index="11">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">4. Download</span>
                        </el-menu-item>
                    </el-menu>
                    </el-aside>
                    <el-main>
                        <div class="section-1" id="item-pos-1">
                            <div class="section-titile">
                                1.What is Homologous Gene Database?
                            </div>
                            <div class="section-info">
                                Homologous Gene Database (HGD) is a comprehensive database that integrates homologous genes from domestic animals, plants and model organisms. Currently, there are 19 species of domestic animals ,16 species of domestic plants and 2 species of model organisms, with a total of 23,399,653 Homologous protein pairs. The Homologous gene information is integrated from 5 public data sources including EggNog, Panther, Treefam, Hieranoid and Inparanoid. Gene annotated information is also collected and integrated from a wide range of public data resources such as GWAS, GVM, GEN ,Gene Ontology, Ensembl and Uniprot. Users can browse, search and download all the information mentioned above from HGD.
                            </div>

                            <div class="section-img0">
                                <img src="../assets/img/document-0.png" class="img"/>
                                <div class="figure">Flow chart of data processing</div>
                            </div>
                        </div>
                        <div class="section section-2" id="item-pos-2">
                            <div class="section-titile">
                                2.Data Browse
                            </div>
                            <div class="section-info">
                                Users can browse homolog genes, traits, variation, expression and gene ontology information in HGD.
                            </div>
                        </div>
                        <div class="section section-1" id="item-pos-3">                          
                            <div class="section-titile">
                                2.1 Homolog Gene Browse
                            </div>
                            <div class="section-info">
                                Users can browse the entire homologous genes list on the homologous gene page (Figure 1). Basic information of homologous genes includes gene name, organism, gene description, Uniprot id, ensembl protein, gene symbol, gene synonym, biotype and a list of homologous genes of the organisms available for the gene. Statistical quantities of annotation information, including trait and GO, are also provided.<br/><br/>
                                Users can filter the list of homologous genes by species, trait and so on. Downloads are available in several formats including txt and csv.<br/><br/>
                                Clicking on a gene name will open the details described in section 2.6.<br/><br/>
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-1.png" class="img"/>
                                <div class="figure">Figure 1</div>
                            </div>
                        </div>
                        <div class="section section-1" id="item-pos-4">
                            <div class="section-titile">
                                2.2 Trait Browse
                            </div>
                            <div class="section-info">
                                Trait information is integrated from GWAS database in NGDC. HGD collects 26,023 trait records (6 animals and 5 plants) from GWAS. After mapping to homolog genes, a total of 14,637 homolog genes with traits are obtained. User can browse the trait information for multiple organisms at the same time. <br /><br />
                                Users can select a trait term from the trait ontology in the left-hand panel. A list of homologous genes will then be displayed (Figure2), with detailed information including trait name, species common name, taxon ID and the common name of multiple organisms for the selected trait. The blue square in the organism column indicates that the organism has homolog genes with the inquired gene of the given trait. The green square indicates that the organism has homolog genes matching the same trait of the inquired gene. <br /><br />
                                Click on the colored square and two more detailed tables of homologous genes and GWAS trait information will be presented respectively.<br /><br />
                                Users can filter the displayed organism from the organism checklist.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-2.png" class="img"/>
                                <div class="figure">Figure 2</div>
                            </div>
                        </div>
                        <div class="section section-1" id="item-pos-5">
                            <div class="section-titile">
                                2.3 Variation Browse
                            </div>
                            <div class="section-info">
                                Variation information is integrated from GVM database in NGDC. HGD collects 1,694,958 variations from GVM with ensembl VEP annotation information (10 animals and 13 plants). After mapping to the homologous gene in HGD, a total of 146,621 homologous genes with variant annotaions are obtained. User can browse variation annotated information for multiple organisms at the same time.<br/><br/>
                                Users can select the term from the variation ontology in the left-hand panel. A list of homologous genes will then be presented (Figure3), including VEP annotated consequence types, species common names, taxon IDs and common names of multiple organisms for the selected ontology term. The blue square in the organism column indicates that the organism has homologous genes with the inquired gene of the given consequence type. The green square indicates that the organism has homologous genes matching the same consequence type of the inquired gene.<br/><br/> 
                                Click on the colored square and two more detailed tables of homologous genes and GVM variation information will be presented respectively.<br/><br/>
                                Users can filter the displayed organism from the organism checklist.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-5.png" class="img"/>
                                <div class="figure">Figure 3</div>
                            </div>
                        </div>
                        <div class="section section-1" id="item-pos-6">
                            <div class="section-titile">
                                2.4 Expression Browse
                            </div>
                            <div class="section-info">
                                Gene expression data is integrated from GEN database in NGDC. HGD collects 8,541,271 genes from GEN with expression ontology of 25 organisms. After mapping to the homolog genes in HGD, a total of 403,655 homolog genes with expression were obtained. Users can browse the expression information of multiple organisms at the same time. <br/><br/>
                                Users can select the expression term defined by GEN in the left-hand panel. A list of homolog genes will then be presented (Figure4), including expression term, the species common name, the taxon ID and the common name of the multiple organisms for the selected expression term. The blue square in the organism column indicates that the organism has homolog genes with the inquired gene of the given expression term. The green square indicates that the organism has homolog genes matching the same expression term of the inquired gene.<br/><br/> 
                                Click on the colored square and two more detailed tables of homologous genes and GEN expression profile information will be presented respectively.<br/><br/>
                                Users can filter the displayed organism from the organism checklist.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-3.png" class="img"/>
                                <div class="figure">Figure 4</div>
                            </div>
                        </div>
                        <div class="section section-1">
                            <div class="section-titile" id="item-pos-7">
                                2.5 Gene Ontology Browse
                            </div>
                            <div class="section-info">
                                Gene ontology data is integrated from Ensembl. HGD collects 4,538,549 genes from Ensembl with gene ontology information of 37 organisms. After mapping to the homolog genes in HGD, a total of 384,684 homolog genes with GO annotation were obtained. Users can browse the gene ontology information of multiple organisms at the same time. <br/><br/>
                                Users can select the GO term from the ontology in the left-hand panel. A gene list will then be presented (Figure5), including GO name, the species common name, taxon ID and the common name of the multiple organisms for the selected GO term. The blue square in the organism column indicates that the organism has homolog genes with the inquired gene of the given GO term. The green square indicates that the organism has homolog genes matching the same GO term of the inquired gene. <br/><br/>
                                Click on the colored square and two more detailed tables of homologous genes and GO information will be presented respectively.<br/><br/>
                                Users can filter the displayed organism from the organism check list.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-4.png" class="img"/>
                                <div class="figure">Figure 5</div>
                            </div>
                        </div>
                        <div class="section section-1">
                            <div class="section-titile" id="item-pos-8">
                                2.6 Gene Detail Information
                            </div>
                            <div class="section-info">
                                Gene detail information contains 6 sections to describe homologous genes, including Gene Basic Information, Homologous Genes, GO annotations, Variants, Traits and Expression Information (Figure 6).<br/><br/>
                                Gene basic information is mainly collected from Ensembl and Uniprot and provides information including gene symbols, gene synonyms, gene types, species names, positions, gene descriptions and external IDs from other databases such as Entrez ID, Refseq ID and so on.<br/><br/>
                                The homologous gene list shows the homologous genes from other organisms. The table columns include species, taxon ID, Ensembl ID, gene symbol, protein, Entrez ID and integrated homologous information from Pather, TreeFam, Eggnog, Inparanold and Haranold. Users can filter the organisms by search box.<br/><br/>
                                Gene ontology describes the GO annotation information for the gene by several colored squares under each GO term. The colors are normalized by the GO annotation numbers. If users hover over the square, the annotated GO term number will be presented. Click and a list of tables containing the annotation information will be presented. When the Compared Homolog Gene is ticked, the GO annotation information of the homolog gene will be displayed as below. Users can filter the organisms by search box.<br/><br/>
                                Variants describes the variant annotation information for the gene by several colored squares under each VEP term (Sequence Ontology). The colors are normalized by the VEP annotated variation numbers. If users hover over the square, the annotated variation number will be presented. Click and a list of tables containing the variation information will be presented. When the Compared Homolog Gene is ticked, the variation annotated information of the homolog gene will be displayed as below. Users can filter the organisms by search box.<br/><br/>
                                Traits describes the trait information for the gene by several colored squares under each trait term. The colors are normalized by the trait numbers. If users hover over the square, the trait number will be presented. Click and a list of tables containing the trait information will be presented. When the Compared Homologous Gene is ticked, the trait information of the homologous gene will be displayed as below. Users can filter the organisms by search box.<br/><br/>
                                Expression describes the expression information for the gene by several colored squares under each expression term. The colors are normalized by the bioproject numbers. If users hover over the square, the bioproject number will be presented. Click and a list of tables containing the average expression value information of tissues will be presented. When the Compared Homologous Gene is ticked, the expression information of the homologous gene will be displayed as below. Users can filter the organism by search box.<br/><br/>
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-6.png" class="img"/>
                                <!-- <div class="figure">Figure 6</div> -->
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-7.png" class="img"/>
                                <div class="figure">Figure 6</div>
                            </div>
                        </div>
                        <div class="section section-1">
                            <div class="section-titile" id="item-pos-9">
                                2.7 Species
                            </div>
                            <div class="section-info">
                                HGD provides both species list and species homologous protein statistics, housing homologous data of 37 species (19 animals, 16 plants and 2 model organisms). When the pie chart is clicked, the species list will change simultaneously. Users can view the various data types for annotated homolog genes of each species. Clicking on a link will jump to the corresponding data module.<br/><br/>
                                Homolog protein pairs between all 37 species are calculated and displayed as a heat map. If users hover over the heat map, the number of homologous protein pairs will be shown. When clicking on the heat map, a list of homologous genes of the two selected species will be displayed in Homologous Gene. By default, HGD uses the species on the x-axis as the query species.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-10.png" class="img"/>
                                <!-- <div class="figure">Figure 6</div> -->
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-11.png" class="img"/>
                                <div class="figure">Figure 7</div>
                            </div>
                        </div>
                         <div class="section section-1">
                            <div class="section-titile" id="item-pos-10">
                                3.Search
                            </div>
                            <div class="section-info">
                                Users can search for homolog genes by gene name, Ensemble gene ID, Ensemble protein name, Uniport protein name, Entrez ID and so on (Figure 8). A homologous gene list will be displayed as below.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-8.png" class="img"/>
                                <div class="figure">Figure 8</div>
                            </div>
                        </div>
                        <div class="section section-1">
                            <div class="section-titile" id="item-pos-11">
                                4.Download
                            </div>
                            <div class="section-info">
                                HGD provides homolog proteins, traits, variations, expression information of each species for download (Figure 9).
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-9.png" class="img"/>
                                <div class="figure">Figure 9</div>
                            </div>
                        </div>
                        
                    </el-main>
                </el-container>
            
            </div>
        </div>
        <div>

           
        </div>
    </el-card>
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
    <FooterBar />
  </div>
</template>

<style src="@/assets/css/documentation.css" scoped></style>
<script>
// import Navigator from "@/components/navigator.vue";
import Banner from "@/components/banner.vue";
import FooterBar from "@/components/FooterBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
export default {
  name: 'Documentation',
  components: {
    // Navigator,
    FooterBar,
    HeaderBar,
    Banner,
    
  },
  data() {
      return {
        tableData: [{
          date: '2016-05-02',
          name: 'Human_Homolog_protein.gz',
          address: '12.3M'
        }, {
          date: '2016-05-04',
          name: 'Cattle_Homolog_protein.gz',
          address: '12.1M'
        }, {
          date: '2016-05-01',
          name: 'Sheep_Homolog_protein.gz',
          address: '11.3M'
        }, {
          date: '2016-05-03',
          name: 'Chicken_Homolog_protein.gz',
          address: '12.3M'
        }],
        navBarFixed:false
      }
    },
    methods:{
        selectMenu(key){
            const target = document.querySelector("#item-pos-"+key)
            target.scrollIntoView({ behavior: 'smooth' })
            // scrollTo() 把内容滚动到指定的坐标。减去导航高度的目的：导航用定位固定在顶部，如果不减去，导航栏会遮挡一部分内容
            window.scrollTo(0, target.offsetTop)
        },
        watchScroll() {
            var scrollTop =
                window.pageYOffset ||
                document.documentElement.scrollTop ||
                document.body.scrollTop;
            //  当滚动超过 288 时，实现吸顶效果
            if (scrollTop > 328) {
                this.navBarFixed = true;
            } else {
                this.navBarFixed = false;
            }
        },
    },
    mounted(){
        window.addEventListener("scroll", this.watchScroll);
    }

}
</script>