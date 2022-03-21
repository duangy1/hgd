<template>
<div class="table-container">
    <HeaderBar/>
     <Banner />
    <Navigator />
    <div class="breadcrumb">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
            <el-breadcrumb-item>Homolog Gene</el-breadcrumb-item>
        </el-breadcrumb>
    </div>

    <el-row :gutter="20" style="padding-top:20px;padding-bottom:20px; padding-left:20px;">
      <el-col :span="5">
          <div class="grid-content">
                    <el-card class="wrap-card" shadow="none">
                       <div class="genefilter" style="padding-bottom:10px;">
                       <el-form ref="form" size="small" align="left">
                               <div class="filterheader"><i class="el-icon-caret-right"></i> Filter by species</div>
                               <div class="filtercontent">
                               <div class="filtersubtitle">Animals</div>
                               <div>
                                  <el-select v-model="checkSpAnimalList" multiple placeholder="Please select" @change="selectAnimalChanged">
                                    <el-option
                                      v-for="item in animalsItem"
                                      :key="item.taxonId"
                                      :label="item.commonName"
                                      :value="item.taxonId">
                                    </el-option>
                                  </el-select>
                                </div>
                               <div class="filtersubtitle">Plants</div>
                                 <div>
                                 <el-select v-model="checkSpPlantList" multiple placeholder="Please select" @change="selectAnimalChanged">
                                    <el-option
                                             v-for="item in plantsItem"
                                             :key="item.taxonId"
                                             :label="item.commonName"
                                             :value="item.taxonId">
                                    </el-option>
                                   </el-select>

                                </div>
                             </div>
                             <div class="filterheader" style="padding-top:40px;"><i class="el-icon-caret-right"></i> Filter by trait</div>
                             <div class="filtercontent">
                              <treeselect
                                :multiple="true"
                                :options="traitoptions"
                                :disable-branch-nodes="true"
                                :open-on-click="true"
                                :always-open="true"
                                :flat="true"
                                :append-to-body="false"
                                placeholder="Select your favourite(s)..."
                                 v-model="traitvalue"
                                />
                              </div>

                             <div style="padding-top:100px;" class="filterheader"><i class="el-icon-caret-right"></i> Filter by ortholog source</div>
                             <div class="filtercontent" style="padding-left:20px;">
                                 <div> <el-checkbox label="OMA group"></el-checkbox> </div>
                                 <div><el-checkbox label="treefam"></el-checkbox></div>
                                 <div><el-checkbox label="eggnog"></el-checkbox></div>
                             </div>

                       </el-form>

                      </div>
                    </el-card>
          </div>
      </el-col>
      <el-col :span="19" style="margin-left:0px; text-align:left;">
        <div class="grid-content" style="margin-left:0px;">

            <el-card class="wrap-card" shadow="none" style="margin-left:0px;padding:10px 10px;">


                <div >
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
                          <div style="float:right;"><el-button type="primary" size="medium">Download</el-button></div>
                 </div>


                <div style="clear:both;"></div>
                <div  v-for="item in tableData" :key="item.gbiId">
                    <div style="padding-bottom:5px;"><el-checkbox name="type"></el-checkbox>  <a href="" style="font-weight:bold; " v-if="item.geneSymbol !=null">{{item.geneSymbol}}</a> <a href="" style="font-weight:bold; " v-else>{{item.ensemblGeneId}}</a> ({{item.taxonName}})</div>
                    <div style="padding-left:20px;">
                         <div style="padding-bottom:5px;font-size:14px;padding-top:5px;">{{item.geneDescription}}</div>
                        <div style="padding-bottom:5px;font-size:14px;padding-top:5px;"><b>Symobl:</b> {{item.geneSymbol}} </div>

                        <div style="padding-bottom:5px;font-size:14px;"><b>Synonym:</b> {{item.geneSynonym}} </div>
                        <div style="padding-bottom:5px;font-size:14px;"><b>BioType:</b> {{item.geneType}} </div>
                        <div style="padding-bottom:5px;font-size:14px;"><b>Ortholog Gene:</b></div>
                        <div style="padding-bottom:5px;font-size:14px;"><span style="font-size:14px;padding-left:10px;" v-for="orthgene in item.orthoGeneBeanList" :key="orthgene.geneName"><a href="">{{orthgene.geneName}}</a> ({{orthgene.taxonName}})</span></div>
                         <div style="padding-bottom:5px;font-size:14px;padding-top:10px;"><el-tag type="warning" size="small" v-if="item.traitCount >0"  ><a href="#">Trait {{item.traitCount}}</a></el-tag>&nbsp;&nbsp;<el-tag type="success" size="small"><a href="#">GO 5</a></el-tag></div>
                    </div>
                    <el-divider></el-divider>
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
             </el-card>


        </div>
      </el-col>

    </el-row>




    
    <FooterBar/>
</div>
</template>
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
.breadcrumb >>> .el-breadcrumb {
    padding-top:15px;
    padding-left:15px;
    padding-bottom:10px;
    font-size:14px !important;
    border:1px solid #cccccc !important;
}

.grid-content >> .el-pagination__total{
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

.breadcrumb >>> span {
    font-size:14px !important;
}
.genefilter >>> .el-collapse-item__header.is-active{

}
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

import Navigator from '@/components/navigator.vue';
import FooterBar from '@/components/FooterBar.vue';
import HeaderBar from '@/components/HeaderBar.vue';
import Banner from "@/components/banner.vue";
import Treeselect from '@riophae/vue-treeselect'
import 'element-ui/lib/theme-chalk/index.css'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  name: 'Gene',
  components: {
    Navigator,
    FooterBar,
    HeaderBar,
    Banner,
	Treeselect
  },
 data(){

      return {
         traitvalue:[],
         traitoptions: [],
         animalsItem:[],
         plantsItem:[],
         checkSpAnimalList:[],
         checkSpPlantList:[],
         activeNames: ['1','2','3'],
        totalSize:10,
        tableData:[],
        multipleSelection: [],
        tabPosition: 'right',
        loading:true
      }
    },
    watch:{
        'traitvalue' : 'selectTraitFunc'
    },
    methods: {
        selectAnimalChanged(value){
            console.log("select animal changed="+value);
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
            if(this.traitvalue != null && this.traitvalue.length>0){
                            for( i =0 ; i< this.traitvalue.length;i++){
                                traitselect+= this.traitvalue[i]+",";
                            }
            }
            if(traitselect.length>0){
                traitselect =traitselect.substring(0,traitselect.length-1);
            }

             const axiosInstance1 = this.$axios.create({
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
              });
                axiosInstance1
                  .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect}})
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
                         console.log("select trait value="+value);
                        let animalselect = "";
                        if(this.checkSpAnimalList != null && this.checkSpAnimalList.length>0){
                            for( var i =0 ; i<this.checkSpAnimalList.length;i++){

                                animalselect+= this.checkSpAnimalList[i]+",";
                            }
                        }

                        if(this.checkSpPlantList != null && this.checkSpPlantList.length>0){
                             for( i =0 ; i<this.checkSpPlantList.length;i++){
                                 animalselect+= this.checkSpPlantList[i]+",";
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

                         const axiosInstance1 = this.$axios.create({
                            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                            withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
                          });
                            axiosInstance1
                              .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect}})
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
                        if(this.checkSpAnimalList != null && this.checkSpAnimalList.length>0){
                            for( var i =0 ; i<this.checkSpAnimalList.length;i++){

                                animalselect+= this.checkSpAnimalList[i]+",";
                            }
                        }

                        if(this.checkSpPlantList != null && this.checkSpPlantList.length>0){
                             for( i =0 ; i<this.checkSpPlantList.length;i++){
                                 animalselect+= this.checkSpPlantList[i]+",";
                              }
                        }


                        if(animalselect.length>0){
                            animalselect =animalselect.substring(0,animalselect.length-1);
                        }



                        let traitselect="";
                        if(this.traitvalue != null && this.traitvalue.length>0){
                                        for( i =0 ; i< this.traitvalue.length;i++){
                                            traitselect+= this.traitvalue[i]+",";
                                        }
                        }
                        if(traitselect.length>0){
                            traitselect =traitselect.substring(0,traitselect.length-1);
                        }

                         const axiosInstance1 = this.$axios.create({
                            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                            withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
                          });
                            axiosInstance1
                              .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect, 'length': val}})
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
                                    if(this.checkSpAnimalList != null && this.checkSpAnimalList.length>0){
                                        for( var i =0 ; i<this.checkSpAnimalList.length;i++){

                                            animalselect+= this.checkSpAnimalList[i]+",";
                                        }
                                    }

                                    if(this.checkSpPlantList != null && this.checkSpPlantList.length>0){
                                         for( i =0 ; i<this.checkSpPlantList.length;i++){
                                             animalselect+= this.checkSpPlantList[i]+",";
                                          }
                                    }


                                    if(animalselect.length>0){
                                        animalselect =animalselect.substring(0,animalselect.length-1);
                                    }



                                    let traitselect="";
                                    if(this.traitvalue != null && this.traitvalue.length>0){
                                                    for( i =0 ; i< this.traitvalue.length;i++){
                                                        traitselect+= this.traitvalue[i]+",";
                                                    }
                                    }
                                    if(traitselect.length>0){
                                        traitselect =traitselect.substring(0,traitselect.length-1);
                                    }

                                     const axiosInstance1 = this.$axios.create({
                                        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
                                        withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
                                      });
                                        axiosInstance1
                                          .get('http://localhost:9401/gene/filterHomolog',{params: {'taxonids': animalselect, 'traitids': traitselect, 'pageNo': val}})
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
      }
      
  },
  mounted () {



    const axiosInstance = this.$axios.create({
    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},// 设置传输内容的类型和编码
    withCredentials: true,// 指定某个请求应该发送凭据。允许客户端携带跨域cookie，也需要此配置
  });
    axiosInstance
      .get('http://localhost:9401/basic/getSpecies',{params: {speciesType: 1}})
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
      .get('http://localhost:9401/basic/getSpecies',{params: {speciesType: 2}})
      .then(response => {
          console.log(response)
          this.plantsItem = response.data
        })
      .catch(error => {
        console.log(error)
        this.errored = true
      })
      .finally(() => this.loading = false)

 axiosInstance
       .get('http://localhost:9401/basic/getTraitName')
       .then(response => {
           console.log(response)
           this.traitoptions = response.data
         })
       .catch(error => {
         console.log(error)
         this.errored = true
       })
       .finally(() => this.loading = false)

  axiosInstance
                   .get('http://localhost:9401/gene/filterHomolog')
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



   }
}
</script>
