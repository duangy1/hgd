<template>
  <div class="hello">
      <HeaderBar />
      <Banner />
      <!-- <div id="bgimg-div">
      </div>
       -->
      <div class="flex-yc">
        <!-- <Navigator /> -->
        <div class="eles-container">
          <!-- <div class="db-introduce">
            <h1 class="db-title">Homolog gene Database</h1>
            <p class="db-info">HDB integrated  homolog gene with traits, SNPs, expression for housed animals and plants from public resources.</p>
          </div> -->
          <el-form :inline="true" :form="searchForm"  >
          <div class="search-box" style="padding-top:40px;">
            <div style="margin:auto">
            <el-form-item>
			<el-select v-model="searchForm.type" :popper-append-to-body="false" placeholder="HGD" class="select"> 
                <el-option
                  v-for="item in dataClass"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  >
                </el-option>
            </el-select>
			</el-form-item>
			<el-form-item>
				<el-input  v-model="searchForm.keyword" prefix-icon="el-icon-search"  placeholder="Enter query..." style="float:left;width: 400px;" ></el-input>
			</el-form-item>
			<el-form-item>
				<el-button class="search-button"   type="primary" size="medium" @click="onSubmit">Search</el-button>
			</el-form-item>
           </div>
          </div>
		</el-form>
          <div class="egs">
            <a href="#" class="example-txt">e.g.&nbsp;</a>
            
            <el-link type="info" class="eg-ele" href="/gene?type=1&keyword=Gallus">Gallus;</el-link>
            <el-link type="info" class="eg-ele" href="/gene?type=1&keyword=Pig">Pig;</el-link>
            <el-link type="info" class="eg-ele" href="/gene?type=2&keyword=GRK3">GRK3;</el-link>     
            <el-link type="info" class="eg-ele" href="/gene?type=3&keyword=ENSGALG00000000011">ENSGALG00000000011;</el-link>
            <el-link type="info" class="eg-ele" href="/gene?type=4&keyword=ENSGALP00000000096">ENSGALP00000000096;</el-link>
            <el-link type="info" class="eg-ele" href="/gene?type=5&keyword=101750305">101750305;</el-link>
            <el-link type="info" class="eg-ele" href="/gene?type=6&keyword=101750305">F1NMX3;</el-link>
            <el-link type="info" class="eg-ele" href="/gene?type=7&keyword=101750305">protein_coding;</el-link>
          </div>
        </div>
      </div>
    <div class="2ndpagebox">
  <!-- 特色物种介绍 -->
<section class="engineer-area ptb-100">
  
  
<!-- <el-tabs v-model="activeName" @tab-click="handleClick" >
  <el-tab-pane label="Animals" name="first"> -->
    <div class="species-show">
    <!-- <div class="section-title">
        <h3 class="feature-species">Feature Species</h3>
    </div> -->
    <div class="container">
      <div class="engineer-slider owl-theme owl-carousel" style="padding-top:30px;">

      <!-- <div class="engineer-item">
		<a href="/gene?taxonid=9606"> <img src="../assets/img/species/Homo_sapiens.svg" alt="Engineer">
        <div class="engineer-bottom">
          <h4>Homo sapines</h4>
        </div>
		</a>
      </div> -->
      <!-- <div class="engineer-item">
      <img src="../assets/img/species/Danio_rerio.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Danio rerio</h4>
      </div>
      </div> -->


      <div class="engineer-item">
        <a href="/gene?taxid1=9913"><img src="../assets/img/species/cattle.svg" alt="Engineer" with="100" height="100">
        <div class="engineer-bottom">
          <h4><i>Bos taurus</i></h4>
        </div></a>
      </div>

      <div class="engineer-item">
       <a href="/gene?taxid1=9031"><img src="../assets/img/species/chicken.svg" alt="Engineer" with="100" height="100">
        <div class="engineer-bottom">
          <h4><i>Gallus gallus</i></h4>
        </div>
		</a>
      </div>

      <!-- <div class="engineer-item">
      <img src="../assets/img/species/Canis_lupus_familiaris.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Canis lupus familiaris</h4>
      </div>
      </div> -->
      <!-- <div class="engineer-item">
      <img src="../assets/img/species/Capra_hircus.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Capra hircus</h4>
      </div>
      </div> -->

      <!-- <div class="engineer-item">
      <img src="../assets/img/species/Danio_rerio.svg" alt="Engineer" >
      <div class="engineer-bottom">
      <h4>Danio rerio</h4>
      </div>
      </div>
      -->
      
      <div class="engineer-item">
      <a href="/gene?taxid1=9823"><img src="../assets/img/species/pig.svg" alt="Engineer" with="100" height="100">
      <div class="engineer-bottom">
      <h4><i>Sus scrofa</i></h4>
      </div></a>
      </div>
      
      <div class="engineer-item">
      <a href="/gene?taxid1=4530"><img src="../assets/img/species/rice.svg" alt="Engineer" with="100" height="100">
      <div class="engineer-bottom">
      <h4><i>Oryza sativa</i></h4>
      </div></a>
      </div>
      <div class="engineer-item">
      <a href="/gene?taxid1=4577"><img src="../assets/img/species/maize.svg" alt="Engineer" with="100" height="100">
      <div class="engineer-bottom">
      <h4><i>Zea mays</i></h4>
      </div></a>
      </div>

      <div class="engineer-item">
      <a href="/gene?taxid1=3708"><img src="../assets/img/species/rapseed.svg" alt="Engineer" with="100" height="100">
      <div class="engineer-bottom">
      <h4><i>Brassica napus</i></h4>
      </div></a>
      </div>

      <!-- <div class="engineer-item">
      <img src="../assets/img/species/Caenorhabditis_elegans.svg" alt="Engineer" with="100" height="100">
      <div class="engineer-bottom">
      <h4>Caenorhabditis elegans</h4>
      </div>
      </div> -->

      



      </div>
    </div>
    <!-- <div class="container-1">
      <div class="engineer-slider owl-theme owl-carousel">
      <div class="engineer-item">
      <img src="../assets/img/species/Arabidopsis_thaliana.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Arabidopsis thaliana</h4>
      </div>
      </div>
      <div class="engineer-item">
      <img src="../assets/img/species/Brassica_napus.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Brassica napus</h4>
      </div>
      </div>
      
      <div class="engineer-item">
      <img src="../assets/img/species/Glycine_max.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Glycine max</h4>
      </div>
      </div>
      <div class="engineer-item">
      <img src="../assets/img/species/Gossypium_hirsutum.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Gossypium hirsutum</h4>
      </div>
      </div>
      
      <div class="engineer-item">
      <img src="../assets/img/species/Oryza_sativa.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Oryza sativa</h4>
      </div>
      </div> -->
      

<!-- 
      <div class="engineer-item">
      <img src="../assets/img/species/Macaca_mulatta.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Macaca mulatta</h4>
      </div>
      </div> -->
      <!-- </div>
    
    </div> -->
     <div class="section-title">
        <a href="http://localhost:9405/species" style="text-decoration:none"><h5 class="full-list">...More</h5></a>
    </div>
    </div>
  
  <!-- </el-tab-pane> -->
  <!-- <el-tab-pane label="Plants" name="second">
      <div class="container">
      <div class="engineer-slider owl-theme owl-carousel">
      <div class="engineer-item">
      <img src="../assets/img/species/Arabidopsis_thaliana.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Arabidopsis thaliana</h4>
      </div>
      </div>
      <div class="engineer-item">
      <img src="../assets/img/species/Brassica_napus.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Brassica napus</h4>
      </div>
      </div>
      
      <div class="engineer-item">
      <img src="../assets/img/species/Glycine_max.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Glycine max</h4>
      </div>
      </div>
      <div class="engineer-item">
      <img src="../assets/img/species/Gossypium_hirsutum.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Gossypium hirsutum</h4>
      </div>
      </div>
      
      <div class="engineer-item">
      <img src="../assets/img/species/Oryza_sativa.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Oryza sativa</h4>
      </div>
      </div>
      </div>
      </div>
    
  </el-tab-pane> -->
  <!-- <el-tab-pane label="Models" name="third">
     <div class="container">
      <div class="engineer-slider owl-theme owl-carousel">
      <div class="engineer-item">
      <img src="../assets/img/species/Arabidopsis_thaliana.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Arabidopsis thaliana</h4>
      </div>
      </div>
      
      <div class="engineer-item">
      <img src="../assets/img/species/Danio_rerio.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Danio rerio</h4>
      </div>
      </div>
      <div class="engineer-item">
      <img src="../assets/img/species/Drosophila_melanogaster.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Drosophila melanogaster</h4>
      </div>
      </div>
      
      <div class="engineer-item">
      <img src="../assets/img/species/Macaca_mulatta.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Macaca mulatta</h4>
      </div>
      </div>

      <div class="engineer-item">
      <img src="../assets/img/species/Caenorhabditis_elegans.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Caenorhabditis elegans</h4>
      </div>
      </div>


      </div>
      </div>
    
  </el-tab-pane>
</el-tabs> -->
    <!-- <div class="section-title">
        <a class="list"><h4 class="full-species">View Full List</h4></a>
  </div> -->
</section>
    <!-- 特征介绍 -->
    <section id="section-id-1633053288840" class="sppb-section">
      <div class="row">
							<div class="col-md-4">
								<div class="service">
									<i class="fa fa-clipboard"></i>
									<h2>Multiple Resources</h2>
									<p>Curated datas from inparanoid,hieranoid,OMA,eggNog,Treefam,Panther database.</p>
								</div>
							</div>
							<div class="col-md-4">
								<div class="service">
									<i class="fa fa-archive"></i>
									<h2>Various Annotations</h2>
									<p>Integrated NGDC multiple gene annotation data.</p>
								</div>
							</div>
							<div class="col-md-4">
								<div class="service">
									<i class="fa fa-users"></i>
									<h2>User-Friendly</h2>
									<p>Multiple GUI surface to facilitate your use.</p>
								</div>
							</div>
						</div>
    </section>
    <section id="section-id-1633135858321" class="sppb-section">
      <div class="sppb-row-container">
        <div class="sppb-row">
          <div class="sppb-col-md-12" id="column-wrap-id-1633135858011">
            <div id="column-id-1633135858011" class="sppb-column">
              <div class="sppb-column-addons">
              
              <div id="sppb-addon-wrapper-1633309203379"
                class="sppb-addon-wrapper">
                <div id="sppb-addon-1633309203379"
                  class=" sppb-wow fadeInUp clearfix "
                  data-sppb-wow-duration="2000ms">
                  <div class="sppb-addon sppb-addon-articles ">
                    <div class="sppb-addon-content">
                      <div class="sppb-row">
                        <div class="sppb-col-md-4" id="left-mofule">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="/gene"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog1.jpg"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="/species">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">
                                  </span><span
                                  class="sppb-meta-category">Species</span>
                              </div>
                              </a>
                              <h3><a href="/species"
                                  itemprop="url">Homolog Pairs Heatmap</a></h3>
                              <div
                                class="sppb-article-introtext">
                                <p>16 animals</p>
								<p>16 plants</p>
								<p>5 other organism</p>
                              </div><a
                                class="sppb-readmore"
                                href="/documentation"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
                        
						<div class="sppb-col-md-4">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="/gene"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog5.jpg"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="/gene">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">
                                  </span><span
                                  class="sppb-meta-category">Gene</span>
                              </div>
                              </a>
                              <h3><a href="/gene"
                                  itemprop="url">Homolog Gene Browse</a></h3>
                              <div
                                class="sppb-article-introtext">
								<p>&nbsp;</p>
								<p>37 species</p>
                                <p>23,399,653 Homolog Protein Pairs</p>
                              </div><a
                                class="sppb-readmore"
                                href="/documentation"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
						
						
                        <div class="sppb-col-md-4">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="/traits"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog2.jpg"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="/traits">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">
                                  </span><span
                                  class="sppb-meta-category">Traits</span>
                              </div>
                              </a>
                              <h3><a href="/traits"
                                  itemprop="url">
                                  Traits Associated with Homolog Gene</a></h3>
                              <div
                                class="sppb-article-introtext">
                                <p>6 species</p>
								<p>15 trait ontology term</p>
								<p>13,209 homolog gene</p>
                              </div><a
                                class="sppb-readmore"
                                href="/documentation"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
                        
						
						<div class="sppb-col-md-4">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="/variants"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog3.jpg"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="/variants">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">
                                  </span><span
                                  class="sppb-meta-category">Variation</span>
                              </div>
                              </a>
                              <h3><a href="/variants"
                                  itemprop="url">Variations Related with Homolog Gene</a></h3>
                              <div
                                class="sppb-article-introtext">
                                <p>11 species</p>
								<p>18 sequence ontology term</p>
								<p>146,621 homolog gene</p>
                              </div><a
                                class="sppb-readmore"
                                href="javascript:;"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
                        <div class="sppb-col-md-4" id="left-mofule">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="/expression"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog4.jpg"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="/expression">
                              <div class="article-meta">
                                
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">
                                </span>
                                  <span
                                  class="sppb-meta-category">Expression</span>
                               
                              </div>
                               </a>
                              <h3><a href="/expression"
                                  itemprop="url">Expression Related with Homolog Gene</a></h3>
                              <div
                                class="sppb-article-introtext">
                                <p>22 species</p>
								<p>55 expression ontology term</p>
								<p>403,655 homolog gene</p>
                              </div><a
                                class="sppb-readmore"
                                href="/documentation"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
                        
                        <div class="sppb-col-md-4">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="/go"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog5.jpg"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">
                                  </span><span
                                  class="sppb-meta-category">Gene Ontology</span>
                              </div>
                              <h3><a href="/go"
                                  itemprop="url">Gene Ontology Related with Homolog Gene</a></h3>
                              <div
                                class="sppb-article-introtext">
                               <p>28 species</p>
								<p>58 gene ontology term</p>
								<p>384,684 homolog gene</p>
                              </div><a
                                class="sppb-readmore"
                                href="/documentation"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>

                       
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
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
  </div>
</template>


<style  src="../assets/css/index.css" scoped></style>
<style  src="../assets/css/font-awesome.css" scoped></style>
<script>
// import Navigator from '@/components/navigator.vue';
import FooterBar from '@/components/FooterBar.vue';
import HeaderBar from '@/components/HeaderBar.vue';
import Banner from "@/components/banner.vue";
// import animate from "@/assets/js/animate.js"
export default {
  name: 'Home',
  components: {
    // Navigator,
    FooterBar,
    HeaderBar,
    Banner
},data(){
  return{
    showModule:false,
    dataClass:[
		{value:'1',label:'Organism'},
		{value:'2',label:'Gene Symbol/Synonym'},
		{value:'3',label:'Ensembl Gene'},
		{value:'4',label:'Ensembl Protein'},
		{value:'5',label:'Entrez Gene'},
		{value:'6',label:'Uniprot Id'},
		{value:'7',label:'BioType'},
		{value:'8',label:'All Fields'}
    ],
	searchForm:{
		type:'',
		keyword:''
	},
    activeName: 'first'
  }
},
  methods:{
    // animate:animate()
    onSubmit(){
		var type=this.searchForm.type;
		var keyword=this.searchForm.keyword;
		this.$router.push({
          path: '/gene',
          query:{type,keyword}
        })
		
	}
  },
}
</script>
