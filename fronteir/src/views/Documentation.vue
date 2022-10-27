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
                        <!-- <img src="./assets/img/document/arrow1.png" style="width:100%;vertical-align:top;margin:10px 0;"> -->
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
                            <span slot="title">2.Data Processing</span>
                        </el-menu-item>
                        <el-menu-item index="3">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">3.Data Browse</span>
                        </el-menu-item>
                        
                        
                        <el-menu-item index="4">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">4.Download</span>
                        </el-menu-item>
                        <el-menu-item index="5">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">5.An using example</span>
                        </el-menu-item>
                       
                    </el-menu>
                    </el-aside>
                    <el-main>
                        <div class="section-1" >
                            <div class="section-titile" id="item-pos-1">
                                1.What is Homologous Gene Database?
                            </div>
                            <div class="section-info">
                                The Homologous Gene Database (HGD) is a comprehensive homologous resource integrating multi-species, multi-resources and multi-omics data and provides public and one-stop data service for browsing, retrieval, comparison and downloading. By integrating several existing homologous gene resources that vary inferring methods and homology relationship, HGD is able to efficiently eliminate the difficulties for researchers in choosing and mapping homology results from one species to another. Besides, by offering various gene function annotations, HGD makes it convenient to conduct comprehensive homologous functional studies on large-scale genome sequences. It aims to facilitate the broad research community dedicated to exploring the functional effects of homologous genes on different aspects of annotations to help users gain a deeper understanding of gene function. Homologous data was integrated from multiple long-time maintained and high-quality resources including eggNOG, Panther, Treefam, Hieranoid, and Inparanoid. Meanwhile, HGD integrates various annotations from public resources such as GWAS Atlas, GVM, GEN, Ensembl, and Uniprot. Users can browse, search and download all the information mentioned above from HGD.                         
                        </div>
                    </div>

                        <div class="section section-2" id="item-pos-2">
                            <div class="section-titile" >
                                2. Data Processing
                            </div>
                            <div class="section-info">
                                The whole data process procedure is shown as follows.
                            </div>
                            <div class="section-img0">
                                <img src="../assets/img/document/Figure1.png" class="img"/>
                                <div class="figure">Figure 1. The overview of HGD</div>
                            </div>
                            <div class="section-titile">
                                2.1 Data collection
                            </div>
                            <div class="section-info">
                                HGD features comprehensive collection of homology data from diverse resources and integration of multi-omics annotations for multiple species. 
                                High quality resources was selected based on the quality gauge of performance offered by The Quest for Orthologs in terms of precision and recall:
                            </div>
                            <div class="section-info">
                                (1)More number of predicted orthologs relationships (higher recall)
                            </div>
                            <div class="section-info">
                                (2)Higher positive predictive value rate (better precision)
                            </div>
                            <div class="section-img0">
                                <img src="../assets/img/document/Figure2.png" class="img"/>
                                <div class="figure">Figure 2. The homologous database assessment from The Quest</div>
                            </div>
                            <div class="section-info">
                                After the quality control, several long-maintained and high-quality homologous data resources was selected, including Panther, TreeFam, Hieranoid, Inparanoid and eggNOG. The final list of homologous gene data integrated in the current version of HGD is based on further ID mapping and manual curation.           
                            </div>
                            <div class="section-info">
                                In addition to homologous data, multiple kinds of annotation data comprises traits, variations, gene expression and gene ontology that are arranged and annotated to homologous genes based on specific type of ontology was also housed in HGD. Annotation data from NGDC databases was retrieved through data interfaces, including trait data from GWAS Atlas (https://ngdc.cncb.ac.cn/gwas), variation data from GVM (https://ngdc.cncb.ac.cn/gvm), expression data from GEN (https://ngdc.cncb.ac.cn/gen). The gene ontology data was firstly retrieved from Ensembl Biomart (http://www.ensembl.org/biomart/martview/).                            </div>
                            <div class="section-info">
                                Besides, the ID mapping between files is essential not only for the integration of data but also for the genes' functional annotations. The original ID mapping files was downloaded from Uniprot (https://www.uniprot.org/), Ensembl and NCBI (https://www.ncbi.nlm.nih.gov/) and were used for specific kinds of ID conversion respectively.
                            </div>
                            <br />
                            <div class="section-titile">
                                2.2 Data processing
                            </div>
                            <div class="section-info">
                                Collecting and integrating all the valuable data from multiple resources, HGD committed to provide a one-stop public data services for browsing, retrieval, comparison and downloading  with no login required. 
                            </div>
                            <div class="section-info">
                                The entire data procedure includes three main parts consists of homology data pre-processing, ID mapping and homologous gene annotation. 
                            </div>
                            <br />
                            <div class="section-titile">
                            (1)Homologous data processing
                            </div>
                            <div class="section-info">
                                
                                The homologous data processing is the most complicated among the entire data processing procedure, which can be divided into four parts including raw file preprocessing, data filter, ID mapping and file merge. The overview of the whole process of data retrieve and data process is shown below .
                            </div>
                            <div class="section-info">
                                Homologous data was downloaded from existed homologous databases as mentioned above. Since the original files from these 5 databases are different in data format, the files was firstly preprocessed to change into identical pair-wise format. After that, a variety of ID mapping was performed in order to change all the files into same kinds of IDs including Uniprot ID, Ensembl ID and NCBI IDs. Finally, the files was merged in a non-redundant way and the group/cluster IDs defined by the source database was reserved to facilitate for tracing the data.                            </div>
                           
                            <div class="section-img0">
                                <img src="../assets/img/document/Figure3.png" class="img"/>
                                <div class="figure">Figure 3. The homologs data processing</div>
                            </div>
                            <div class="section-titile">
                                (2)ID mapping processing and gene annotation
                            </div>
                            <div class="section-info">
                                
                                A diagram of ID mapping processing is shown below. The core part is the constructed of double strand ID mapping HASH map based on the ID mapping files from Ensembl, Uniprot and NCBI databases. In the constructing of double strand HASH map of ID mapping files, the obsolete Uniprot IDs were removed. After that, the merge of homologous files and the annotation of traits, variants, expression, GO data to homologous genes was all based on the ID mapping files. The structure of data processing of this part is shown below.
                            </div>
                            <div class="section-info">
                                The annotation data comprises trait, variation, expression and gene ontology in HGD is essential in getting a better understanding of gene function between homologous genes. Based on ID mapping file, multiple IDs was converted into Uniprot IDs, Ensembl IDs and NCBI IDs, and finally annotated to the homologous genes correspondingly.
                            </div>
                            <div class="section-img0">
                                <img src="../assets/img/document/Figure4.png" class="img"/>
                                <div class="figure">Figure 4. Homologous Gene Annotation</div>
                            </div>
                        </div>
                   





                        <div class="section section-3">
                            <div class="section-titile"  id="item-pos-3">
                                3.Data Browse
                            </div>
                            <div class="section-info">
                                Users can browse homologous genes, traits, variation, expression and gene ontology information in HGD.
                            </div>
                        
                        <div >     
                            <br />                     
                            <div class="section-titile">
                                3.1 Data retrieve
                            </div>
                            <div class="section-info">
                                By handling a wide range of ID mappings, HGD supports searching for homologs by various keywords, including gene symbol, gene synonym, gene name, Uniprot ID, Ensembl protein ID, protein name, Ensembl gene ID, NCBI gene ID and so on. Users can search for interested genes/proteins by any preferred condition.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure5.png" class="img"/>
                                <div class="figure">Figure 5.Search</div>
                            </div>
                        </div>
                        <div >
                            <div class="section-titile" id="item-pos-13">
                                3.2 Homologous Gene Browse
                            </div>
                            <div class="section-info">
                                In 'Homologous Gene' module, user can browse the entire homologous genes list (Figure 3).  
                            </div>
                            <div class="section-info">
                                Users can filter the list of homologous genes by species and annotation (trait, variation, expression, GO) ontology terms in the left panel of the page. Downloads are available in several formats including txt and csv.
                            </div>
                            <div class="section-info">
                                Clicking on a gene name will open the detail page described in section 2.3 of the selected gene.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure6.png" class="img"/>
                                <div class="figure">Figure 6.Homologous Gene</div>
                            </div>
                        </div>
                        <div>
                            <div class="section-titile" id="item-pos-11">
                                3.3 Annotation modules (Trait, Variation, Expression, GO)
                            </div>
                            <div class="section-info">
                                In these modules, the annotation data was integrated using ontology terms and each type of annotation data has a different strategy. Specifically, when integrating trait information, Animal Trait Ontology and Plant Trait Ontology were used; when integrating variation information, the ontology vocabulary of Sequence Ontology was used ; when integrating expression information, the Disease Ontology ontology vocabulary, BRENDA Tissue Ontology and Biological Context were used. In addition, Gene Ontology was reserved as 60 canonical terms based on the number of annotated genes. The original data from Ensembl Biomart was sorted by the number of annotated gene, and top 20 terms was selected in the categories of molecular function,cellular component and biological process respectively which formed the 60 terms as a result.                            </div>
                            <div class="section-info">
                                In modules of Trait, Variation, Expression, and Gene Ontology, the ontology terms is displayed in a tree structure, and user can view it in the left-hand drawer panel by click the button in the upper left corner. Clicking on the tree node, the data will be filtered by the selected ontology term. To see all data without the filter of ontology, user can click the “Remove filter” button in the page. These modules also support searching of gene ID/protein ID or filter the data by species through the search box.                            </div>
                            <div class="section-info">
                                The homologs with annotation is displayed in the table as the main part of the page. Genes is in the left side of the table and homologs is in the right side with multiple colored blocks  as different representations. The blue block represents homologs, the orange block represents that this homologous gene have at least one annotation data, and the green block represents that this pair of homologs have a same annotation ontology term. By click these blocks, user can see a more detailed data table below (Figure 4). Above the table, there are two buttons. One is to adjust the table column and the other is to download the data in the table in “.xlsx” format.                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure6.1.png" class="img"/>
                                <div class="figure">Figure 6.1. Module usage</div>
                            </div>
                        </div>
                        <br />
                        <div >
                            <div class="section-titile">
                                3.4 Gene Detail Page
                            </div>
                            <div class="section-info">
                                Clicking on a gene name will open the detail page. The gene detail page contains 6 sections to describe homologous genes, including Gene Basic Information, Homologous Genes, GO annotations, Variants, Traits and Expression Information (Figure 6).
                            </div>
                            <br />
                            <div class="section-titile">
                                (1) Gene basic information
                            </div>
                            <div class="section-info">
                                Gene basic information is mainly collected from Ensembl and Uniprot and provides information including gene symbols, gene synonyms, biotype, species names, positions, gene descriptions and external IDs from other databases such as Entrez ID, Refseq ID and so on.  Statistical quantities of annotation information, including trait and GO, are also provided.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure7.png" class="img"/>
                                <div class="figure">Figure7. Gene basic information</div>
                            </div>
                             <div class="section-titile">
                                (2) Homologous Genes
                            </div>
                            <div class="section-info">
                                
                                The 'Homologous Gene' section shows the homologous genes of the gene. The table columns include species, taxon ID, Ensembl ID, gene symbol, protein, Entrez ID and data source count which give a statistical quantities of the data sources. Homologous data was integrated from Pather, TreeFam, eggNOG, Inparanold and Haranold. Users can filter the organisms by search box.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure8.png" class="img"/>
                                <div class="figure">Figure 8. Homologous gene</div>
                            </div>
                             <div class="section-titile">
                                (3) GO annotations
                            </div>
                            <div class="section-info">
                                In the sections of GO annotations, Variants, Traits and Expression, annotation data is displayed in a heat map. If users hover over the squares, the number of annotation terms with other information such as Gene ID, GO ID, GO definition and so on will be presented  in a tooltip box. By click the 'Compare Ortholog Gene' checkbox, the homologs with annotation data will be added as a row to the heat map. The depth of the color in the heat map represents the amount of annotation data. By click the color block, user can see a detailed information list will be displayed with multiple outer links, and the color of the clicked block will be changed as a remark.
                            </div>
                            <div class="section-info">
                                Users can filter the data by species.
                            </div>
                            <div class="section-info">
                                Here, we would like to describe each of these annotation sections in a more detail way with some screenshots.
                            </div>
                            <div class="section-info">
                                GO annotations: In this section, the gene with its homologs' Gene Ontology data will be displayed as below. By click the colored block, the detail information of GO ID, GO Name, GO classification, Sub GO ID and GO Definition will be displayed.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure9.png" class="img"/>
                                <div class="figure">Figure 9. GO annotations</div>
                            </div>
                            <div class="section-titile">
                                (4) Variants
                            </div>
                            <div class="section-info">
                                
                                Variants section describes the variant annotation information for the gene under each VEP term (Sequence Ontology). In this section, the gene with its homologs' variants data will be displayed as below. By click the colored block, the detail information of Gene ID, Gene Symbol, Var ID, Position, Allele, MAF, Class, Consequence Type/Effect, cDNA Position, Allele Change, Protein Position, Residue Change will be displayed.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure10.png" class="img"/>
                                <div class="figure">Figure 10. Variants</div>
                            </div>
                            <div class="section-titile">
                                (5) Traits
                            </div>
                            <div class="section-info">
                                
                                Traits section describes the trait information for the gene under each trait term. In this section, the gene with its homologs’ trait data will be displayed as below. By click the colored block, the detail information of Trait Name, Gene ID, Var ID, Sub Trait Name, Species, Pubmed ID, Pvalue will be displayed.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure11.png" class="img"/>
                                <div class="figure">Figure 11. Traits</div>
                            </div>
                            <div class="section-titile">
                                (6) Expression
                            </div>
                            <div class="section-info">
                                
                                Expression section describes the expression information for the gene under each expression term. In this section, the gene with its homologs' expression data will be displayed as below. By click the colored block, the detail information of Gene ID, Bioproject ID, Tissue, Average TPM will be displayed.  The 'Average TPM' means the expression TPM value in the table is calculated the average by tissue. The 'Bar-plot' column contains a icon, which can be clicked to see the bar plot of expression TPM value of homologous gene in one Bioproject.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure12.1.png" class="img"/>
                                <img src="../assets/img/document/Figure12.2.png" class="img"/>
                                <div class="figure">Figure 12. Expression</div>
                            </div>
                        </div>
                        <div>
                            <div class="section-titile" id="item-pos-12">
                                3.5 Species 
                            </div>
                            <div class="section-info">
                                HGD provides both species list and species homologous protein statistics and housed homologous data of 37 species (19 animals, 16 plants and 2 model organisms). This module consists of two parts, one is species information list, the other is data statistics displayed by heat map.
                            </div>
                            <div class="section-info">
                                The pie chart in the page show the composition of species. When the pie chart is clicked, the species list will be changed simultaneously. Users can view the type and amount for annotated  data of each species.  For multiple links is supported in corresponding data module, user can click to trace the interested data in outer databases.                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure13.png" class="img"/>
                                <div class="figure">Figure13. Species</div>
                            </div>
                            <div class="section-info">
                                Homologous protein pairs between all 37 species are calculated and  the statistic data is displayed in a heat map. If users hover over the heat map, the number of homologous protein pairs will be shown. By click on the heat map, the page will jump to the gene module and the homologous genes between the two selected species will be showed in a list.                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure14.png" class="img"/>
                                <div class="figure">Figure 14. Species heat map</div>
                            </div>
                        </div>
                        
                    </div>
                        <div class="section section-4">
                            <div class="section-titile"  id="item-pos-4">
                                4.Download
                            </div>
                            <div class="section-info">
                                HGD provides homologous proteins, traits, variations, expression information of each species for download, and all these data can be downloaded without restriction (Figure 9).
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure15.png" class="img"/>
                                <div class="figure">Figure 15. Download</div>
                            </div>
                        </div>
                        <div class="section section-5" >
                            <div class="section-titile" id="item-pos-5">
                                5. An using example
                            </div>
                            <div class="section-info">
                                To better explain the use of HGD, we will take 'EP2' gene with step-by-step operation as an example. 
                            </div>
                            <br />
                            <div class="section-titile" >
                                5.1 Browse EP2 gene in Homologous Gene module
                            </div>
                            <br />
                            <div class="section-info">
                                Step1 Search EP2 through the search box in the home page
                            </div>
                            <div class="section-info">
                                Step2 Filter the data with species 'Rice'
                            </div>
                            <div class="section-info">
                                Step3 Filter the data with trait ontology 'Plant morphology trait' 
                            </div>
                            <div class="section-info">
                                Step4 Click the 'Trait 1','Variation 6' or 'Expresison 15' to jump to corresponding module or click the gene name and jump to gene detail page directly
                            </div>
                            <div class="section-info">
                                Step5 See the detail trait annotation with homologs in different modules about 'EP2' gene
                            </div>
                            <div class="section-info">
                                Step6 Click the gene name and jump to gene detail page.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure16.png" class="img"/>
                                <div class="figure">Figure 16. Screenshot of search result</div>
                                <br />
                                <img src="../assets/img/document/Figure17.png" class="img"/>
                                <div class="figure">Figure 17. Jump to trait module</div></div>
                            </div>
                            <div class="section-titile" >
                                5.2 Browse EP2 gene in annotation modules
                            </div>
                            <div class="section-info">
                                Step1 Select the 'Plant morphology trait' term to filter the data
                            </div>
                            <div class="section-info">
                                Step2 Search interested gene EP2 in the search box. 
                            </div>
                            <div class="section-info">
                                Step3 Click the icon in interested homologous species column to see the detail data.
                            </div>
                            <div class="section-img">
                                <img src="../assets/img/document/Figure18.png" class="img"/>
                                <div class="figure">Figure 18. Search in trait module</div>
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
            console.log("key:",key);
            const target = document.querySelector("#item-pos-"+key);
            console.log("target:",target);
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