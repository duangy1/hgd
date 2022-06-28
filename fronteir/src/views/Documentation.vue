<template>
  <div>
    <HeaderBar />
    <Banner />
    <!-- <Navigator /> -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
      <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
      <el-breadcrumb-item>Documentation</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card style="width:92%;border-top: 2px solid rgb(64, 158, 255)!important;margin: auto;">
    
        <div >
            <div style="font-size:18px;text-align:left;padding:10px 20px;font-weight: 700;">Documentation</div>
        <!-- <div class="container"> -->
            <div class="wrapper-box">
                <el-container>
                    <el-aside width="250px" style="overflow: hidden;">
                        <!-- <img src="../assets/img/arrow1.png" style="width:100%;vertical-align:top;margin:10px 0;"> -->
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
                            <span slot="title">2.1 Homolog Gene</span>
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
                                1.What is Homolog Gene Database?
                            </div>
                            <div class="section-info">
                                Homolog Gene Database (HGD) is an integrated homolog gene database for domestic animals, plants and model organisms. Currently, there are 16 domestic animals ,16 domestic plants and 5 model organisms which housed 23,399,653 homolog protein pairs in all. The homolog gene information integrated from 5 public data sources including EggNog,Panther,Treefam,Hieranoid,Inparanoid. At the same time, gene annotated information are widely collected and integrated from National Genomics Data Center (NGDC) resources including GWAS, GVM, GEN and public data sources including Gene Ontology, Ensembl, Uniprot. Users can browse, search and download all these information from HGD.
                            </div>

                            <div class="section-img0">
                                <img src="../assets/img/document-0.png" class="img"/>
                            </div>
                        </div>
                        <div class="section section-2" id="item-pos-2">
                            <div class="section-titile">
                                2.Data Browse
                            </div>
                            <div class="section-info">
                                Users can browse homolog gene, trait, variation, expression and gene ontology information in HGD.
                            </div>
                        </div>
                        <div class="section section-1" id="item-pos-3">                          
                            <div class="section-titile">
                                2.1 Homolog Gene Browse
                            </div>
                            <div class="section-info">
                                Users can browse the whole homolog gene list in the homolog gene page (Figure 1). The basic information of homolog gene including gene name, organism, gene description, uniprot id, ensembl protein, gene symbol, gene synonym, biotype and the homolog gene list for available organisms of this gene. The statistics number of annotated information including trait, go are provided as well.<br/><br/>
                                Users can filter the homolog gene list by species, trait and so on. Users can download the homolog gene by several formats including txt, csv.<br/><br/>
                                Click the gene name will open a detail information which described in section 2.6.<br/><br/>
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
                                The trait information integrated from GWAS database in NGDC. HGD collects 26,023 trait records (6 animals and 5 plants) from GWAS. After mapped to the homolog gene in HGD, user can browse the trait information for multiple organisms at the same time. <br /><br />
                                Users can choose the trait term from the trait ontology on the left panel. Then a homolog gene list including trait name, species common name, taxon id and common name of multiple organisms for the chosen trait will be shown as follows (Figure2). The blue square in the organism’s column means this organism has homolog gene with query gene of given trait. The green square means this organism has homolog gene with the same trait of the query gene of given trait. <br /><br />
                                Click the colored square, two more detailed tables with homolog gene and GWAS trait information will be shown separately.<br /><br />
                                Users can filter the shown organism from the organism check list.
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
                                The variation information integrated from GVM database in NGDC. HGD collects 1,694,958 variations with ensembl VEP annotated information (10 animals and 13 plants) from GVM. After mapped to the homolog gene in HGD, user can browse variation annotated information for multiple organisms at the same time. <br/><br/>
                                Users can choose the term from the variation ontology on the left panel. Then a homolog gene list including VEP annotated consequence type, species common name, taxon id and common name of multiple organisms for the chosen ontology term will be shown as follows (Figure3). The blue square in organism’s column means this organism has homolog gene with query gene of given consequence type. The green square means this organism has homolog gene with the same consequence type of the query gene of given term.<br/><br/> 
                                Click the colored square, two more detailed tables with homolog gene and GVM variation information will be shown separately.<br/><br/>
                                Users can filter the shown organism from the organism check list.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document-3.png" class="img"/>
                                <div class="figure">Figure 3</div>
                            </div>
                        </div>
                        <div class="section section-1" id="item-pos-6">
                            <div class="section-titile">
                                2.4 Expression Browse
                            </div>
                            <div class="section-info">
                                HGD integrates gene expression from GEN database in NGDC. HGD houses 8,541,271 gene with expression ontology of 25 organisms from GEN. After mapped to the homolog gene in HGD, user can browse the expression information for multiple organisms at the same time. <br/><br/>
                                Users can choose the expression term which defined by GEN from the left panel. Then a homolog gene list including expression term, species common name, taxon id and common name of multiple organisms for the chosen expression term will be shown as follows (Figure4). The blue square in organism’s column means this organism has homolog gene with query gene of given expression term. The green square means this organism has homolog gene with the same expression term of the query gene of given expression term.<br/><br/> 
                                Click the colored square, two more detailed tables with homolog gene and GEN expression profile information will be shown separately.<br/><br/>
                                Users can filter the shown organism from the organism check list.
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
                                The gene ontology information from Ensembl are integrated. HGD collects 4,538,549 gene with gene ontology information for 37 organisms from Ensembl. After mapped to the homolog gene in HGD, user can browse the gene ontology information for multiple organisms at the same time. <br/><br/>
                                Users can choose the GO term from the ontology on the left panel. Then a gene list including GO name, species common name, taxon id and common name of multiple organism for the chosen GO term will be shown as follows (Figure5). The blue square in organism’s column means this organism has homolog gene with query gene of given GO term. The green square means this organism has homolog gene with the same GO term of the query gene of given GO term. <br/><br/>
                                Click the colored square, two more detailed tables with homolog gene and GO information will be shown separately.<br/><br/>
                                Users can filter the shown organism from the organism check list.
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
                                The gene detail information contains 6 parts to describe a homolog gene including gene basic information, homolog gene, GO annotation, Variants, Trait and Expression Informations (Figure 6).<br/><br/>
                                The gene basic information mainly collected from Ensembl and Uniprot, which provides information including gene symbol, gene synonym, gene type, species name, position, gene description and external id from other database such as entrez id, refseq id and so on.<br/><br/>
                                The homolog gene list shows the homolog genes in other organisms. The table column includes species, taxon id, ensembl id, gene symbol, protein, entrez id and the integrated homolog information from Pather, TreeFam, Eggnog, Inparanold, Haranold. Users can filter the organisms from the search box.<br/><br/>
                                The gene ontology describes the GO annotated information for this gene by several colored squares under each GO term. The color is normalized by the GO annotated number. When hover on the square, the annotated GO term number will be shown. When click on, a table list including the annotated information will be shown. When check on the Compared Homolog Gene, the GO annotated information of the homolog gene will be shown as below. Users can filter the organism from the search box.<br/><br/>
                                The variants describe the variation annotated information for this gene by several colored squares under each VEP term (Sequence Ontology). The color is normalized by the VEP annotated variation number. When hover on the square, the annotated variation number will be shown. When click on, a table list including the variation information will be shown. When check on the Compared Homolog Gene, the variation annotated information of the homolog gene will be shown as below. Users can filter the organism from the search box.<br/><br/>
                                The trait describes the trait information for this gene by several colored squares under each trait term. The color is normalized by the trait number. When hover on the square, the trait number will be shown. When click on, a table list including the trait information will be shown. When check on the Compared Homolog Gene, the trait information of the homolog gene will be shown as below. Users can filter the organism from the search box.<br/><br/>
                                The expression describes the expression information for this gene by several colored squares under each expression term. The color is normalized by the bioproject number. When hover on the square, the bioproject number will be shown. When click on, a table list including the average expression value information of tissue will be shown. When check on the Compared Homolog Gene, the expression information of the homolog gene will be shown as below. Users can filter the organism from the search box.<br/><br/>
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
                                HGD provides two kinds of information including species list and homolog protein statistics for species. HGD houses homolog data of 37 species (16 animals, 16 plants and 5 model organisms). When click the pie chart, the species list will be changed at the same time. Users can see various data types for annotated homolog gene of each species. Click the link will jump to the according data module.<br/><br/>
                                The homolog protein pairs between all 37 species are counted and shown as a heat map. When mouse hover on the heat map, the number of homolog protein pairs will be shown. When click the heat map, a homolog gene list of the two selected species will be shown in Homolog Gene. By default, HGD uses the species of x axis as query species.
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
                                User can search homolog gene by gene name, ensemble gene id, ensemble protein name, uniport protein name, Entrez id and so on (Figure 8). A homolog gene list will be shown as below.
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
                                HGD provide the homolog protein, trait, variation, expression information for each organism to download (Figure 9).
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

<style src="../assets/css/documentation.css" scoped></style>
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