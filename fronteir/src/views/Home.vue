<template>
  <div class="hello">
      <HeaderBar />
      <Banner />
      <!-- <div id="bgimg-div">
      </div>
       -->
      <div class="flex-yc">
        <Navigator />
        <div class="eles-container">
          <!-- <div class="db-introduce">
            <h1 class="db-title">Homolog gene Database</h1>
            <p class="db-info">HDB integrated  homolog gene with traits, SNPs, expression for housed animals and plants from public resources.</p>
          </div> -->
          
          <div class="search-box" >
            <el-select :popper-append-to-body="false" placeholder="HDB" style="float:left;height: 50px !important;"> 
                <el-option
                  v-for="item in dataClass"
                  :key="item.id"
                  :label="item"
                  :value="item"
                  >
                </el-option>
            </el-select>
            <el-input size="large" prefix-icon="el-icon-search"  placeholder="Enter query..." style="float:left;width: 100%;" ></el-input>
            <el-button class="search-button" size="medium" type="primary">Search</el-button>
            
          </div>
          <div class="egs">
            <a href="#" class="example-txt">e.g:</a>
            
            <el-link type="info" class="eg-ele">Gallus;</el-link>
            <el-link type="info" class="eg-ele">Chicken;</el-link>
            <el-link type="info" class="eg-ele">P18660;</el-link>
            
            <el-link type="info" class="eg-ele">FBpp0081737;</el-link>
            <el-link type="info" class="eg-ele">ENSGALG00000008542;</el-link>
          </div>
        </div>
      </div>
    <div class="2ndpagebox">
  <!-- 特色物种介绍 -->
<section class="engineer-area ptb-100">
  
  
<!-- <el-tabs v-model="activeName" @tab-click="handleClick" >
  <el-tab-pane label="Animals" name="first"> -->
    <div class="species-show">
    <div class="section-title">
        <h3 class="feature-species">Feature Species</h3>
    </div>
    <div class="container">
      <div class="engineer-slider owl-theme owl-carousel">

      <div class="engineer-item">
        <img src="../assets/img/species/Homo_sapiens.svg" alt="Engineer">
        <div class="engineer-bottom">
          <h4>Homo sapines</h4>
        </div>
      </div>

      <div class="engineer-item">
        <img src="../assets/img/species/Rattus_norvegicus.svg" alt="Engineer">
        <div class="engineer-bottom">
          <h4>Rattus norvegicus</h4>
          <!-- <span>Head Engineer</span> -->
        </div>
      </div>

      <div class="engineer-item">
        <img src="../assets/img/species/Bos_taurus.svg" alt="Engineer">
        <div class="engineer-bottom">
          <h4>Bos taurus</h4>
          <!-- <span>Head Engineer</span> -->
        </div>
      </div>

      <div class="engineer-item">
      <img src="../assets/img/species/Canis_lupus_familiaris.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Canis lupus familiaris</h4>
      <!-- <span>Head Engineer</span> -->
      </div>
      </div>
      <div class="engineer-item">
      <img src="../assets/img/species/Capra_hircus.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Capra hircus</h4>
      <!-- <span>Automobile Engineer</span> -->
      </div>
      </div>

      <div class="engineer-item">
      <img src="../assets/img/species/Danio_rerio.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Danio rerio</h4>
      </div>
      </div>
     
      <div class="engineer-item">
      <img src="../assets/img/species/Gallus_gallus.svg" alt="Engineer">
      <div class="engineer-bottom">
      <h4>Gallus gallus</h4>
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
                              href="http://localhost:9405/gene"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog1.jpg"
                                alt="Top 5 Things That You Need To Have In Your Garden"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="http://localhost:9405/gene">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">01
                                  </span><span
                                  class="sppb-meta-category">Gene</span>
                              </div>
                              </a>
                              <h3><a href="http://localhost:9405/gene"
                                  itemprop="url">Variations,Go And Expression Of Gene</a></h3>
                              <div
                                class="sppb-article-introtext">
                                Suspendisse ut mattis
                                nibh, nec porttitor orci
                                ligula nunc, elementum.
                              </div><a
                                class="sppb-readmore"
                                href="http://localhost:9405/gene"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
                        
                        <div class="sppb-col-md-4">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="http://localhost:9405/traits"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog2.jpg"
                                alt="The Best Tools Needed For Setting Up Your Plant"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="http://localhost:9405/traits">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">02
                                  </span><span
                                  class="sppb-meta-category">Traits</span>
                              </div>
                              </a>
                              <h3><a href="http://localhost:9405/traits"
                                  itemprop="url">The
                                  Traits Related With
                                  Ortholog Gene Info</a></h3>
                              <div
                                class="sppb-article-introtext">
                                Suspendisse ut mattis
                                nibh, nec porttitor orci
                                ligula nunc, elementum.
                              </div><a
                                class="sppb-readmore"
                                href="http://localhost:9405/traits"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
                        <div class="sppb-col-md-4">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="http://localhost:9405/variants"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog3.jpg"
                                alt="Main Rules: Landscape Design And What You See"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="http://localhost:9405/variants">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">03
                                  </span><span
                                  class="sppb-meta-category">Variation</span>
                              </div>
                              </a>
                              <h3><a href="http://localhost:9405/variants"
                                  itemprop="url">The Variations Related With Homolog Gene Info</a></h3>
                              <div
                                class="sppb-article-introtext">
                                Suspendisse ut mattis
                                nibh, nec porttitor orci
                                ligula nunc, elementum.
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
                              href="http://localhost:9405/species"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog4.jpg"
                                alt="All You Need To Know About The Latest Trends"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="http://localhost:9405/species">
                              <div class="article-meta">
                                
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">04
                                </span>
                                  <span
                                  class="sppb-meta-category">Expression</span>
                               
                              </div>
                               </a>
                              <h3><a href="http://localhost:9405/species"
                                  itemprop="url">The Expression Related With Homolog Gene Info</a></h3>
                              <div
                                class="sppb-article-introtext">
                                Suspendisse ut mattis
                                nibh, nec porttitor orci
                                ligula nunc, elementum.
                              </div><a
                                class="sppb-readmore"
                                href="http://localhost:9405/species"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
                        <div class="sppb-col-md-4">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="javascript:;"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog5.jpg"
                                alt="How To Plant Your Favorite Tree In The Best Pot"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="javascript:;">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">05
                                  </span><span
                                  class="sppb-meta-category">Species</span>
                              </div>
                              </a>
                              <h3><a href="javascript:;"
                                  itemprop="url">Homolog
                                  Informations About The Species </a></h3>
                              <div
                                class="sppb-article-introtext">
                                Suspendisse ut mattis
                                nibh, nec porttitor orci
                                ligula nunc, elementum.
                              </div><a
                                class="sppb-readmore"
                                href="javascript:;"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
                        <div class="sppb-col-md-4">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="javascript:;"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog5.jpg"
                                alt="Main Rules: Landscape Design And What You See"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">05
                                  </span><span
                                  class="sppb-meta-category">Mapper</span>
                              </div>
                              <h3><a href="javascript:;"
                                  itemprop="url">Annotate Your Sequence With Our Defined Methods</a></h3>
                              <div
                                class="sppb-article-introtext">
                                Suspendisse ut mattis
                                nibh, nec porttitor orci
                                ligula nunc, elementum.
                              </div><a
                                class="sppb-readmore"
                                href="javascript:;"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div>
                        <!-- <div class="sppb-col-md-4">
                          <div class="sppb-addon-article"><a
                              class="sppb-article-img-wrap"
                              href="javascript:;"
                              itemprop="url"><img
                                class="sppb-img-responsive"
                                src="../assets/img/blog6.jpg"
                                alt="The Connetion Between Soul And Outside Garden"
                                itemprop="thumbnailUrl"
                                loading="lazy"></a>
                            <div
                              class="sppb-article-info-wrap">
                              <a href="javascript:;">
                              <div class="article-meta">
                                <span
                                  class="sppb-meta-date"
                                  itemprop="datePublished">06
                                  </span><span
                                  class="sppb-meta-category">Visualization</span>
                              </div>
                              </a>
                              <h3><a href="javascript:;"
                                  itemprop="url">See Visualizations Of The Ortholog Gene In Multiple Species</a></h3>
                              <div
                                class="sppb-article-introtext">
                                Suspendisse ut mattis
                                nibh, nec porttitor orci
                                ligula nunc, elementum.
                              </div><a
                                class="sppb-readmore"
                                href="javascript:;"
                                itemprop="url">Read
                                More</a>
                            </div>
                          </div>
                        </div> -->
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
import Navigator from '@/components/navigator.vue';
import FooterBar from '@/components/FooterBar.vue';
import HeaderBar from '@/components/HeaderBar.vue';
import Banner from "@/components/banner.vue";
// import animate from "@/assets/js/animate.js"
export default {
  name: 'Home',
  components: {
    Navigator,
    FooterBar,
    HeaderBar,
    Banner
},data(){
  return{
    showModule:false,
    dataClass:[
      "species",
      "Ensembl Gene Id"
    ],
    activeName: 'first'
  }
},
  methods:{
    // animate:animate()
    
  },
}
</script>
