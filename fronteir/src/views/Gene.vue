<template>
<div class="table-container">
    <HeaderBar/>
     <Banner />
    <!-- <Navigator /> -->
  
     <el-breadcrumb separator-class="el-icon-arrow-right"  class="arrow-title">
            <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
            <el-breadcrumb-item>Homolog Gene</el-breadcrumb-item>
      </el-breadcrumb>

    <el-container style="border: 1px solid #eee;margin-top: 15px;">
    
<!--	<el-row :gutter="20" style="padding-top:20px;padding-bottom:20px; "> -->
 <!--     <el-col :span="4"  style="background-color: aliceblue;"> -->         
           <div class=" left" style="background-color: aliceblue;width:250px !important;">
  
                     <div class="genefilter" style=" padding-bottom:10px;width:250px !important;" align="left">
                       
                       <el-form ref="form" size="small" align="left">
                               
								
							<div style="padding-top:10px;padding-left:10px;padding-botton:10px;"><el-button type="primary" size="small" @click="reset()">Clear</el-button></div>
							<div class="filterheader"><i class="el-icon-caret-right"></i> Filter by Species</div>
                               <div class="filtercontent">
                               
                                   
                                 <div id="idOrgAnimal" >
                                  <el-select v-model="checkSpAnimalList" multiple placeholder="Please select" @change="selectAnimalChanged">
                                    <el-option
                                      v-for="item in animalsItem"
                                      :key="item.taxonId"
                                      :label="item.commonName"
                                      :value="item.taxonId">
                                    </el-option>
                                  </el-select>
                                 </div>
                              </div>
                             
                             <div class="filterheader" style="padding-top:20px;"><i class="el-icon-caret-right"></i> Filter by Trait</div>
                             <div class="filtercontent" >
                              <treeselect 
                                :multiple="true"
                                :options="traitAnimaloptions"
                                :disable-branch-nodes="true"
                                :open-on-click="true"
                                :always-open="true"
                                :flat="true"
                                :append-to-body="false"
                                placeholder="Select your favourite(s)..."
                                 v-model="traitAnimalvalue"
                                /> 
                               
                              </div>
                             
                             <div class="filterheader" style="padding-top:90px;"><i class="el-icon-caret-right"></i> Filter by GO</div>
                             <div class="filtercontent" >
                                <treeselect 
                                :multiple="true"
                                :options="gooptions"
                                :disable-branch-nodes="true"
                                :open-on-click="true"
                                :always-open="true"
                                :flat="true"
                                :append-to-body="false"
                                placeholder="Select your favourite(s)..."
                                 v-model="govalue"
                                /> 
                             </div>

                            <div class="filterheader" style="padding-top:330px;"><i class="el-icon-caret-right"></i> Filter by Varation</div>
                             <div class="filtercontent" >
                                <treeselect 
                                :multiple="true"
                                :options="variantoptions"
                                :disable-branch-nodes="true"
                                :open-on-click="true"
                                :always-open="true"
                                :flat="true"
                                :append-to-body="false"
                                placeholder="Select your favourite(s)..."
                                 v-model="variantvalue"
                                /> 
                             </div>
                             <div class="filterheader" style="padding-top:150px;"><i class="el-icon-caret-right"></i> Filter by Expression</div>
                             <div class="filtercontent" >
                                <treeselect 
                                :multiple="true"
                                :options="expoptions"
                                :disable-branch-nodes="true"
                                :open-on-click="true"
                                :always-open="true"
                                :flat="true"
                                :append-to-body="false"
                                placeholder="Select your favourite(s)..."
                                 v-model="expvalue"
                                /> 
                             </div>
                       </el-form>

                      </div>

          </div>

     <!-- </el-col> -->
    <!--  <el-col :span="19" style="margin-left:0px; text-align:left;"> -->
        <div class="grid-content right tables" style="width: 98%; margin-left: 5%; margin-right:5%;" v-loading="screenLoading">

                <div style="padding-top:10px;" >
                      <div style="margin-top:10px; margin-left:0px;padding-bottom:10px;float:left;">
                                        <el-pagination
                                          @size-change="handleSizeChange"
                                          @current-change="handleCurrentChange"
                                          :page-sizes="[5, 10, 20, 50, 100]"
                                          :page-size="5"
                                          layout=" total,sizes, prev, pager, next, jumper"
                                          :total="totalSize"
                                        >
                                        </el-pagination>

                          </div>
                          <div style="float:right;">
						<el-dropdown trigger="click">
      <el-button type="primary">
        Download<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="downloadTxt">Tab seprated file</el-dropdown-item>
        <el-dropdown-item @click.native="downloadCsv">Csv file</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
	</div>
                 </div>
                 


                <div style="clear:both;"></div>
				<div style="text-align:left;padding-bottom:10px;"><el-tag v-for="tag in tags" :key="tag.name" closable :type="info" @close="closeTag(tag)"> {{tag.name}}</el-tag></div>
				<div v-if="totalSize > 0">
                <div align="left" style="padding-bottom:10px;"><el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange"><b>Check All</b></el-checkbox></div> 
				<el-checkbox-group  v-model="checkedHomolog" @change="handleCheckedHomologChange">
                <div  v-for="item in tableData" :key="item.gbiId" style="text-align:left;">
                    <div style="padding-bottom:5px;"><el-checkbox :label="item.hdbGeneId"><a href="" style="font-weight:bold; " v-if="item.showGeneName !=null" @click="toDetailPage(item.hdbGeneId,item.taxonId)">{{item.showGeneName}}</a> (<i>{{item.taxonName}}</i>) </el-checkbox></div>
                    <div style="padding-left:20px;">
                        <div style="padding-bottom:5px;font-size:14px;padding-top:5px;">{{item.geneDescription}}</div>
                        <div style="padding-bottom:5px;font-size:14px;padding-top:5px;" v-if="item.geneSymbol !=null"><b>Symobl:</b> {{item.geneSymbol}} </div>
						<div style="padding-bottom:5px;font-size:14px;padding-top:5px;" v-if="item.uniprotId"><b>Uniprot:</b> <a :href="'https://www.uniprot.org/uniprot/'+item.uniprotId"  target="_blank">{{item.uniprotId}}</a></div>
						<div style="padding-bottom:5px;font-size:14px;padding-top:5px;" v-if="item.ensemblProteinId"><b>Ensembl Protein:</b> <a :href="'https://asia.ensembl.org/Multi/Search/Results?q='+item.ensemblProteinId+';site=ensembl'" target="_blank">{{item.ensemblProteinId}}</a> </div>
                        <div style="padding-bottom:5px;font-size:14px;" v-if="item.geneSynonym != null"><b>Synonym:</b> {{item.geneSynonym}} </div>
                        <div style="padding-bottom:5px;font-size:14px;"><b>BioType:</b> {{item.geneType}} </div>
						
                        <div style="padding-bottom:5px;font-size:14px;"><b>Homolog Gene:</b></div>
                        <div style="padding-bottom:5px;font-size:14px;">
                        
                                    <el-row :outer="20" v-for="orthgeneList in item.orthoGeneBeanList" :key="orthgeneList.index">
                                        <el-col :span="8" style="font-size:14px;padding-left:10px;padding-top:5px;" v-for="orthgene in orthgeneList" :key="orthgene.hdbGeneId"><a href="" @click="toDetailPage(orthgene.hdbGeneId,orthgene.taxonId)">{{orthgene.geneName}}</a> (<i>{{orthgene.taxonName}}</i>)</el-col> 
                                    </el-row>
                        
                        
                        </div>
                         <div style="padding-bottom:5px;font-size:14px;padding-top:10px;"><el-tag type="warning" size="small" v-if="item.traitCount >0"  ><a href="#">Trait {{item.traitCount}}</a></el-tag><el-tag type="success" size="small" v-if="item.goCount >0"><a href="#">GO {{item.goCount}}</a></el-tag><el-tag type="primary" size="small" v-if="item.varCount >0"><a href="#">Varation {{item.varCount}}</a></el-tag><el-tag type="danger" size="small" v-if="item.expCount >0"><a href="#">Expression {{item.expCount}}</a></el-tag></div>
                    </div>
                    <el-divider></el-divider>
                </div>
			</el-checkbox-group>
			
			</div>
			<div v-else style="height:920px;text-align:left;">
				<div style="padding-top:10px;padding-left:10px;">
					No results.
				</div>
			</div>
            <div style="margin-top:10px; margin-left:0px;">
                    <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"

                      :page-sizes="[5, 10, 20, 50, 100]"
                      :page-size="5"
                      layout="total, sizes, prev, pager, next, jumper"
                      :total="totalSize"
                    >
                    </el-pagination>

             </div>



        </div>
     <!-- </el-col> -->

  <!--   </el-row> -->
  
  
  </el-container>



    
    <FooterBar/>
</div>
</template>
<style src="../assets/css/gene-detail.css" scoped></style>
<style scoped>
.filterheader{
    font-weight:bold;
    font-size: 14px;
    padding:10px 10px;

}

.filtersubtitle{
    padding:10px 10px;
    font-size: 14px ;
    font-weight:bold;
}

.filtercontent{
    padding-left:10px;
    padding-right:10px;
}


.grid-content >>> .el-pagination__total{
    font-weight: bold !important;
    color: #985735 !important;
}
.grid-content >>> span{
    font-size:14px !important;
}

.grid-content >>> a{
    color: #037fec;
    text-decoration: none;
    font-size: 14px;
}

.grid-content >>> .el-tag a{
    color: inherit;
}


/* .genefilter >>> .el-collapse-item__header.is-active{

} */
>>> ul li span{
    font-size:14px !important;
}
.genefilter >>> span{
    font-size:14px !important;
}
.genefilter >>> .el-collapse-item__header{
    font-size:15px !important;
    font-weight: bold !important;
}
.genefilter >>> .el-checkbox__label{
    font-size:14px !important;
}
.genefilter >>> label{
    font-size: 14px !important;
    font-weight: normal !important;
}
</style>

<script>

// import Navigator from '@/components/navigator.vue';
import FooterBar from '@/components/FooterBar.vue';
import HeaderBar from '@/components/HeaderBar.vue';
import Banner from "@/components/banner.vue";
import Treeselect from '@riophae/vue-treeselect'
import 'element-ui/lib/theme-chalk/index.css'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
// import { Loading } from 'element-ui';
export default {
  name: 'Gene',
  components: {
    // Navigator,
    FooterBar,
    HeaderBar,
    Banner,
	Treeselect
  },
 data(){

      return {
		tags:[],
        checkAll:false,
		checkedHomolog:[],
        traitAnimalvalue:[],
        traitAnimaloptions: [],
        gooptions:[],
        govalue:[],
        variantoptions:[],
        variantvalue:[],
        expoptions:[],
        expvalue:[],
        animalsItem:[],
        checkSpAnimalList:[],
        activeNames: ['1','2','3'],
        totalSize:10,
        tableData:[],
        multipleSelection: [],
        tabPosition: 'right',
        loading:true,
        screenLoading:true,
		isIndeterminate:false
      }
    },
    watch:{
        'traitAnimalvalue' : 'selectTraitFunc',
		'govalue' : 'selectGoFunc',
		'variantvalue' : 'selectVariantFunc',
		'expvalue' : 'selectExpFunc'
    },
    methods: {
		closeTag(tag){
			let index = this.tags.indexOf(tag);
			this.tags.splice(index,1);
			window.location.href="/gene";
		},
		reset(){
			this.$router.push({
				path: '/gene'
			})
		},
		downloadTxt(){
			console.log("download txt");
			let traitselect="";
           
            if(this.checkedHomolog != null && this.checkedHomolog.length>0){
                   for(var i =0 ; i< this.checkedHomolog.length;i++){
                        traitselect+= this.checkedHomolog[i]+",";
                   }
             }
            
             if(traitselect.length>0){
                traitselect =traitselect.substring(0,traitselect.length-1);
				window.location.href="http://localhost:9401/download/homolog/"+traitselect+"/1";
            }else{
				alert("You must at least choose one");
			}
			
		},
		downloadCsv(){
			console.log("download csv");
			let traitselect="";
           
            if(this.checkedHomolog != null && this.checkedHomolog.length>0){
                   for(var i =0 ; i< this.checkedHomolog.length;i++){
                        traitselect+= this.checkedHomolog[i]+",";
                   }
             }
            
              if(traitselect.length>0){
                traitselect =traitselect.substring(0,traitselect.length-1);
				window.location.href="http://localhost:9401/download/homolog/"+traitselect+"/2";
             }else{
				alert("You must at least choose one");
			}
			
		},
		handleCheckAllChange(val){
			console.log("handleCheckAllChange="+val);
			if(val){
				for(var i =0 ; i< this.tableData.length;i++){
					var homologgene = this.tableData[i];
					if(homologgene != null ){
						console.log(homologgene.hdbGeneId);
						this.checkedHomolog.push(homologgene.hdbGeneId);
					}
					
				}
			}else{
				this.checkedHomolog = [];
			}
			this.isIndeterminate = false;
		},
		handleCheckedHomologChange(value){
			console.log("handleCheckedHomologChange="+value);
			let checkedCount = value.length;
			this.checkAll = checkedCount === this.tableData.length;
			this.isIndeterminate = checkedCount > 0 && checkedCount < this.tableData.length;
		},
        selectAnimalChanged(value){          
            let animalselect = "";
            if(value != null && value.length>0){
                for(var i =0 ; i<value.length;i++){
                    animalselect+= value[i]+",";
                }
            }

            if(animalselect.length>0){
                animalselect =animalselect.substring(0,animalselect.length-1);
            }

            let traitselect="";
           
            if(this.traitAnimalvalue != null && this.traitAnimalvalue.length>0){
                   for( i =0 ; i< this.traitAnimalvalue.length;i++){
                        traitselect+= this.traitAnimalvalue[i]+",";
                   }
             }
            
              if(traitselect.length>0){
                traitselect =traitselect.substring(0,traitselect.length-1);
             }

						let goselect="";
                        if(this.govalue != null && this.govalue.length>0){
                              for( i =0 ; i< this.govalue.length;i++){
                                     goselect+= this.govalue[i]+",";
                                 }
                       }
            
                       if(goselect.length>0){
                             goselect =goselect.substring(0,goselect.length-1);
                        }
                        let varselect="";
                        if(this.variantvalue != null && this.variantvalue.length>0){
                              for( i =0 ; i< this.variantvalue.length;i++){
                                     varselect+= this.variantvalue[i]+",";
                                 }
                       }
            
                       if(varselect.length>0){
                             varselect =varselect.substring(0,varselect.length-1);
                        }                       
                        let expselect="";
                        if(this.expvalue != null && this.expvalue.length>0){
                              for( i =0 ; i< this.expvalue.length;i++){
                                     expselect+= this.expvalue[i]+",";
                                 }
                       }
            
                       if(expselect.length>0){
                             expselect =expselect.substring(0,expselect.length-1);
                        }                        
                        var type= this.$route.query.taxid2;
						if(type == null){
							type=-1;
						}
			
			
						var keyword=this.$route.query.keyword;
						if(keyword != null && keyword.length >0 ){
							
							if(type == null){
								type=8;

							}
						}	



             const axiosInstance1 = this.$axios.create({
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
              });
                axiosInstance1
                  .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect,'goids':goselect, 'variantids':varselect,'expids':expselect,'type':type,'keyword':keyword}})
                  .then(response => {
                      console.log(response)
                      this.tableData = response.data.data
                      this.totalSize = response.data.recordsTotal
                    })
                  .catch(error => {
                    console.log(error)
                    this.errored = true
                  })
                  .finally(() => this.loading = false)



        },

        selectTraitFunc(value){
                       
                        let animalselect = "";
                        if(this.checkSpAnimalList != null && this.checkSpAnimalList.length>0){
                            for( var i =0 ; i<this.checkSpAnimalList.length;i++){
                                  animalselect+= this.checkSpAnimalList[i]+",";
                             }
                         }
                        if(animalselect.length>0){
                            animalselect =animalselect.substring(0,animalselect.length-1);
                        } 
                        
                        let traitselect="";
                        if(value != null && value.length>0){
                                        for( i =0 ; i< value.length;i++){
                                            traitselect+= value[i]+",";
                                        }
                        }
                        if(traitselect.length>0){
                          traitselect =traitselect.substring(0,traitselect.length-1);
                        }
                        let goselect="";
                        if(this.govalue != null && this.govalue.length>0){
                              for( i =0 ; i< this.govalue.length;i++){
                                     goselect+= this.govalue[i]+",";
                                 }
                       }
            
                       if(goselect.length>0){
                             goselect =goselect.substring(0,goselect.length-1);
                        }
                        let varselect="";
                        if(this.variantvalue != null && this.variantvalue.length>0){
                              for( i =0 ; i< this.variantvalue.length;i++){
                                     varselect+= this.variantvalue[i]+",";
                                 }
                       }
            
                       if(varselect.length>0){
                             varselect =varselect.substring(0,varselect.length-1);
                        }                       
                        let expselect="";
                        if(this.expvalue != null && this.expvalue.length>0){
                              for( i =0 ; i< this.expvalue.length;i++){
                                     expselect+= this.expvalue[i]+",";
                                 }
                       }
            
                       if(expselect.length>0){
                             expselect =expselect.substring(0,expselect.length-1);
                        }                        
						var type= this.$route.query.taxid2;
						if(type == null){
							type=-1;
						}
			
			
						var keyword=this.$route.query.keyword;
						if(keyword != null && keyword.length >0 ){
							
							if(type == null){
								type=8;

							}
						}		

                         const axiosInstance1 = this.$axios.create({
                            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                            withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
                          });
                            axiosInstance1
                              .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect, 'goids':goselect, 'variantids':varselect,'expids':expselect,'type':type,'keyword':keyword}})
                              .then(response => {
                                  console.log(response)
                                  this.tableData = response.data.data
                                  this.totalSize = response.data.recordsTotal
                                })
                              .catch(error => {
                                console.log(error)
                                this.errored = true
                              })
                              .finally(() => this.loading = false)

        },
        selectGoFunc(value){
                        let animalselect = "";
                        if(this.checkSpAnimalList != null && this.checkSpAnimalList.length>0){
                            for( var i =0 ; i<this.checkSpAnimalList.length;i++){
                                  animalselect+= this.checkSpAnimalList[i]+",";
                             }
                         }
                        if(animalselect.length>0){
                            animalselect =animalselect.substring(0,animalselect.length-1);
                        } 
                        
                        let traitselect="";
                        if(this.traitAnimalvalue != null && this.traitAnimalvalue.length>0){
                                        for( i =0 ; i< this.traitAnimalvalue.length;i++){
                                            traitselect+= this.traitAnimalvalue[i]+",";
                                        }
                        }
                        if(traitselect.length>0){
                          traitselect =traitselect.substring(0,traitselect.length-1);
                        }
                        let goselect="";
                        if(value != null && value.length>0){
                              for( i =0 ; i< value.length;i++){
                                     goselect+= value[i]+",";
                                 }
                       }
            
                       if(goselect.length>0){
                             goselect =goselect.substring(0,goselect.length-1);
                        }
                        let varselect="";
                        if(this.variantvalue != null && this.variantvalue.length>0){
                              for( i =0 ; i< this.variantvalue.length;i++){
                                     varselect+= this.variantvalue[i]+",";
                                 }
                       }
            
                       if(varselect.length>0){
                             varselect =varselect.substring(0,varselect.length-1);
                        }                       
                        let expselect="";
                        if(this.expvalue != null && this.expvalue.length>0){
                              for( i =0 ; i< this.expvalue.length;i++){
                                     expselect+= this.expvalue[i]+",";
                                 }
                       }
            
                       if(expselect.length>0){
                             expselect =expselect.substring(0,expselect.length-1);
                        }                        
                        

                         const axiosInstance1 = this.$axios.create({
                            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                            withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
                          });
                            axiosInstance1
                              .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect, 'goids':goselect, 'variantids':varselect,'expids':expselect}})
                              .then(response => {
                                  console.log(response)
                                  this.tableData = response.data.data
                                  this.totalSize = response.data.recordsTotal
                                })
                              .catch(error => {
                                console.log(error)
                                this.errored = true
                              })
                              .finally(() => this.loading = false)
              
              
        },
        selectVariantFunc(value){
                        let animalselect = "";
                        if(this.checkSpAnimalList != null && this.checkSpAnimalList.length>0){
                            for( var i =0 ; i<this.checkSpAnimalList.length;i++){
                                  animalselect+= this.checkSpAnimalList[i]+",";
                             }
                         }
                        if(animalselect.length>0){
                            animalselect =animalselect.substring(0,animalselect.length-1);
                        } 
                        
                        let traitselect="";
                        if(this.traitAnimalvalue != null && this.traitAnimalvalue.length>0){
                                        for( i =0 ; i< this.traitAnimalvalue.length;i++){
                                            traitselect+= this.traitAnimalvalue[i]+",";
                                        }
                        }
                        if(traitselect.length>0){
                          traitselect =traitselect.substring(0,traitselect.length-1);
                        }
                        let goselect="";
                        if(this.govalue != null && this.govalue.length>0){
                              for( i =0 ; i< this.govalue.length;i++){
                                     goselect+= this.govalue[i]+",";
                                 }
                       }
            
                       if(goselect.length>0){
                             goselect =goselect.substring(0,goselect.length-1);
                        }
                        let varselect="";
                        if(value != null && value.length>0){
                              for( i =0 ; i< value.length;i++){
                                     varselect+= value[i]+",";
                                 }
                       }
            
                       if(varselect.length>0){
                             varselect =varselect.substring(0,varselect.length-1);
                        }                       
                        let expselect="";
                        if(this.expvalue != null && this.expvalue.length>0){
                              for( i =0 ; i< this.expvalue.length;i++){
                                     expselect+= this.expvalue[i]+",";
                                 }
                       }
            
                       if(expselect.length>0){
                             expselect =expselect.substring(0,expselect.length-1);
                        }                        
                        
						var type= this.$route.query.taxid2;
						if(type == null){
							type=-1;
						}
			
			
						var keyword=this.$route.query.keyword;
						if(keyword != null && keyword.length >0 ){
							
							if(type == null){
								type=8;

							}
						}		
                         const axiosInstance1 = this.$axios.create({
                            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                            withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
                          });
                            axiosInstance1
                              .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect, 'goids':goselect, 'variantids':varselect,'expids':expselect,'type':type,'keyword':keyword}})
                              .then(response => {
                                  console.log(response)
                                  this.tableData = response.data.data
                                  this.totalSize = response.data.recordsTotal
                                })
                              .catch(error => {
                                console.log(error)
                                this.errored = true
                              })
                              .finally(() => this.loading = false)
        },
        selectExpFunc(value){
                        let animalselect = "";
                        if(this.checkSpAnimalList != null && this.checkSpAnimalList.length>0){
                            for( var i =0 ; i<this.checkSpAnimalList.length;i++){
                                  animalselect+= this.checkSpAnimalList[i]+",";
                             }
                         }
                        if(animalselect.length>0){
                            animalselect =animalselect.substring(0,animalselect.length-1);
                        } 
                        
                        let traitselect="";
                        if(this.traitAnimalvalue != null && this.traitAnimalvalue.length>0){
                                        for( i =0 ; i< this.traitAnimalvalue.length;i++){
                                            traitselect+= this.traitAnimalvalue[i]+",";
                                        }
                        }
                        if(traitselect.length>0){
                          traitselect =traitselect.substring(0,traitselect.length-1);
                        }
                        let goselect="";
                        if(this.govalue != null && this.govalue.length>0){
                              for( i =0 ; i< this.govalue.length;i++){
                                     goselect+= this.govalue[i]+",";
                                 }
                       }
            
                       if(goselect.length>0){
                             goselect =goselect.substring(0,goselect.length-1);
                        }
                        let varselect="";
                        if(this.variantvalue != null && this.variantvalue.length>0){
                              for( i =0 ; i< this.variantvalue.length;i++){
                                     varselect+= this.variantvalue[i]+",";
                                 }
                       }
            
                       if(varselect.length>0){
                             varselect =varselect.substring(0,varselect.length-1);
                        }                       
                        let expselect="";
                        if(value != null && value.length>0){
                              for( i =0 ; i< value.length;i++){
                                     expselect+= value[i]+",";
                                 }
                       }
            
                       if(expselect.length>0){
                             expselect =expselect.substring(0,expselect.length-1);
                        }    

						var type= this.$route.query.taxid2;
						if(type == null){
							type=-1;
						}
			
			
						var keyword=this.$route.query.keyword;
						if(keyword != null && keyword.length >0 ){
							
							if(type == null){
								type=8;

							}
						}							
                        

                         const axiosInstance1 = this.$axios.create({
                            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                            withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
                          });
                            axiosInstance1
                              .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect, 'goids':goselect, 'variantids':varselect,'expids':expselect,'type':type,'keyword':keyword}})
                              .then(response => {
                                  console.log(response)
                                  this.tableData = response.data.data
                                  this.totalSize = response.data.recordsTotal
                                })
                              .catch(error => {
                                console.log(error)
                                this.errored = true
                              })
                              .finally(() => this.loading = false)
        },
        handleSizeChange(val) {
		
			let animalselect = "";
			var taxonid =  this.$route.query.taxid1;
			if(taxonid != null && taxonid.length >0 ){
				animalselect=taxonid;
			}
			var orthtaxids="";
			var taxonid2 = this.$route.query.taxid2;
			if(taxonid2 != null && taxonid2.length >0 ){
				orthtaxids=taxonid2;
			}
		
		
                    if(this.checkSpAnimalList != null && this.checkSpAnimalList.length>0){
                              for( var i =0 ; i<this.checkSpAnimalList.length;i++){

                                 animalselect+= this.checkSpAnimalList[i]+",";
                             }
                             if(animalselect.length>0){
                                          animalselect =animalselect.substring(0,animalselect.length-1);
                              }
                   }
                                   


             let traitselect="";
          
             if(this.traitAnimalvalue != null && this.traitAnimalvalue.length>0){
                     for( i =0 ; i< this.traitAnimalvalue.length;i++){
                             traitselect+= this.traitAnimalvalue[i]+",";
                       }
               }
            
                        if(traitselect.length>0){
                            traitselect =traitselect.substring(0,traitselect.length-1);
                        }
                       let goselect="";
                        if(this.govalue != null && this.govalue.length>0){
                              for( i =0 ; i< this.govalue.length;i++){
                                     goselect+= this.govalue[i]+",";
                                 }
                       }
            
                       if(goselect.length>0){
                             goselect =goselect.substring(0,goselect.length-1);
                        }
                        let varselect="";
                        if(this.variantvalue != null && this.variantvalue.length>0){
                              for( i =0 ; i< this.variantvalue.length;i++){
                                     varselect+= this.variantvalue[i]+",";
                                 }
                       }
            
                       if(varselect.length>0){
                             varselect =varselect.substring(0,varselect.length-1);
                        }                       
                        let expselect="";
                        if(this.expvalue != null && this.expvalue.length>0){
                              for( i =0 ; i< this.expvalue.length;i++){
                                     expselect+= this.expvalue[i]+",";
                                 }
                       }
            
                       if(expselect.length>0){
                             expselect =expselect.substring(0,expselect.length-1);
                        }  
						var type= this.$route.query.taxid2;
						if(type == null){
							type=-1;
						}
			
			
						var keyword=this.$route.query.keyword;
						if(keyword != null && keyword.length >0 ){
							
							if(type == null){
								type=8;

							}
						}						
                         const axiosInstance1 = this.$axios.create({
                            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                            withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
                          });
                            axiosInstance1
                              .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect, 'goids':goselect, 'variantids':varselect,'expids':expselect,'orthtaxids':orthtaxids,'type':type,'keyword':keyword, 'length': val}})
                              .then(response => {
                                  console.log(response)
                                  this.tableData = response.data.data
                                  this.totalSize = response.data.recordsTotal
                                })
                              .catch(error => {
                                console.log(error)
                                this.errored = true
                              })
                              .finally(() => this.loading = false)

      },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
			let animalselect = "";
			var taxonid =  this.$route.query.taxid1;
			if(taxonid != null && taxonid.length >0 ){
				animalselect=taxonid;
			}
			var orthtaxids="";
			var taxonid2 = this.$route.query.taxid2;
			if(taxonid2 != null && taxonid2.length >0 ){
				orthtaxids=taxonid2;
			}
                                  
                               
                                    if(this.checkSpAnimalList != null && this.checkSpAnimalList.length>0){
                                        for( var i =0 ; i<this.checkSpAnimalList.length;i++){

                                            animalselect+= this.checkSpAnimalList[i]+",";
                                        }
                                       if(animalselect.length>0){
                                          animalselect =animalselect.substring(0,animalselect.length-1);
                                       }
                                    }
                                   




            let traitselect="";
          
             if(this.traitAnimalvalue != null && this.traitAnimalvalue.length>0){
                     for( i =0 ; i< this.traitAnimalvalue.length;i++){
                             traitselect+= this.traitAnimalvalue[i]+",";
                       }
             }
              if(traitselect.length>0){
                  traitselect =traitselect.substring(0,traitselect.length-1);
             }
           
                       let goselect="";
                        if(this.govalue != null && this.govalue.length>0){
                              for( i =0 ; i< this.govalue.length;i++){
                                     goselect+= this.govalue[i]+",";
                                 }
                       }
            
                       if(goselect.length>0){
                             goselect =goselect.substring(0,goselect.length-1);
                        }
                        let varselect="";
                        if(this.variantvalue != null && this.variantvalue.length>0){
                              for( i =0 ; i< this.variantvalue.length;i++){
                                     varselect+= this.variantvalue[i]+",";
                                 }
                       }
            
                       if(varselect.length>0){
                             varselect =varselect.substring(0,varselect.length-1);
                        }                       
                        let expselect="";
                        if(this.expvalue != null && this.expvalue.length>0){
                              for( i =0 ; i< this.expvalue.length;i++){
                                     expselect+= this.expvalue[i]+",";
                                 }
                       }
            
                       if(expselect.length>0){
                             expselect =expselect.substring(0,expselect.length-1);
                        } 
              
						var type= this.$route.query.taxid2;
						if(type == null){
							type=-1;
						}
			
			
						var keyword=this.$route.query.keyword;
						if(keyword != null && keyword.length >0 ){
							
							if(type == null){
								type=8;

							}
						}

                                     const axiosInstance1 = this.$axios.create({
                                        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                                        withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
                                      });
                                        axiosInstance1
                                          .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect, 'goids':goselect, 'variantids':varselect,'expids':expselect,'orthtaxids':orthtaxids,'type':type,'keyword':keyword,  'pageNo': val}})
                                          .then(response => {
                                              console.log(response)
                                              this.tableData = response.data.data
                                              this.totalSize = response.data.recordsTotal
                                            })
                                          .catch(error => {
                                            console.log(error)
                                            this.errored = true
                                          })
                                          .finally(() => this.loading = false)
      },
      handleChange(val) {
         console.log(val);
      },
      toDetailPage(hdbId,taxonId){
        this.$router.push({
          path: '/gene-detail',
          query:{hdbId,taxonId}
        })
      }
      
  },
  mounted () {
    // let loadingService = Loading.service({
    //       lock: true,
    //       text: 'Loading',
    //       spinner: 'el-icon-loading',
    //       background: 'rgba(0, 0, 0, 0.7)',
    //       fullscreen:true
    //     });

  
    const axiosInstance = this.$axios.create({
    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
    withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
  });
	var taxonid =  this.$route.query.taxid1;
	var params ={};
	if(taxonid != null && taxonid.length >0 ){
		console.log(taxonid);
		params['taxonids'] = taxonid;
		this.tags.push({'name':taxonid});
	}

	var taxonid2 = this.$route.query.taxid2;
	if(taxonid2 != null && taxonid2.length >0 ){
		console.log(taxonid2);
		params['orthtaxids'] = taxonid2;
		this.tags.push({'name':taxonid2});
	}
	
	var type= this.$route.query.taxid2;
	if(type != null && type.length >0 ){
		params['type'] = type;
	}else{
		params['type'] = -1;
	}
	
	var keyword=this.$route.query.keyword;
	if(keyword != null && keyword.length >0 ){
		params['keyword'] = keyword;
		this.tags.push({'name':keyword});
		if(type == null){
			type=8;
			params['type'] = type;
		}
	}

  
    axiosInstance
      .get('http://localhost:9401/basic/getSpeciesModify',{params: {speciesType: 1}})
      .then(response => {
          console.log(response)
          this.animalsItem = response.data
        })
      .catch(error => {
        console.log(error)
        this.errored = true
      })
      .finally(() => this.loading = false)


 axiosInstance
       .get('http://localhost:9401/basic/getTraitName')
       .then(response => {
           this.traitAnimaloptions= response.data
         })
       .catch(error => {
         console.log(error)
         this.errored = true
       })
       .finally(() => this.loading = false)
  axiosInstance
                   .get('http://localhost:9401/gene/filterHomolog',{params: params})
                   .then(response => {
                       console.log("this.tableData:",response)
                       this.tableData = response.data.data
                       this.totalSize = response.data.recordsTotal
                     })
                   .catch(error => {
                     console.log(error)
                     this.errored = true
                   })
                   .finally(() => {
                     this.loading = false
                    //  loadingService.close()
                    this.screenLoading=false;
                     })

  axiosInstance
                   .get('http://localhost:9401/basic/getVoTerm')
                   .then(response => {
                       this.variantoptions= response.data
                     })
                   .catch(error => {
                     console.log(error)
                     this.errored = true
                   })
                   .finally(() => {
                     this.loading = false
                    //  loadingService.close()
                    this.screenLoading=false;
                     })
axiosInstance
                   .get('http://localhost:9401/basic/getExpressionTerm')
                   .then(response => {
                       this.expoptions= response.data
                     })
                   .catch(error => {
                     console.log(error)
                     this.errored = true
                   })
                   .finally(() => {
                     this.loading = false
                    //  loadingService.close()
					this.screenLoading=false;
                     })
axiosInstance
                   .get('http://localhost:9401/basic/getGOTerm')
                   .then(response => {
                       this.gooptions= response.data
                     })
                   .catch(error => {
                     console.log(error)
                     this.errored = true
                   })
                   .finally(() => {
                     this.loading = false
                    //  loadingService.close()
						this.screenLoading=false;
                     })

   }
}
</script>
