<template>
<div>
    <HeaderBar />
    <Banner />
     <el-breadcrumb separator-class="el-icon-arrow-right" class="arrow-title">
      <el-breadcrumb-item :to="{ path: '/' }">Browse</el-breadcrumb-item>
      <el-breadcrumb-item>Contact</el-breadcrumb-item>
    </el-breadcrumb>
    <div>
          <el-card style="width:92%;border-top: 2px solid rgb(64, 158, 255)!important;margin: auto;margin-bottom: 30px;">
          <div style="margin-left:5%"> 
          <div class="section-titile " style="margin-top:20px" >Contact Us</div>
          <div style="margin-top:1%">
            <div style="text-align:left;" class="section-info">HGD is developed and maintained by The BIG Data Center at Beijing Institute of Genomics, Chinese Academy of Sciences.</div>
            <div class="section-1 wraper-info">
                <div class="section-titile">
                    Postal Address
                </div>
                <div class="section-info">
                  Prof. WenMing Zhao<br />
                  Beijing Institute of Genomics<br />
                  Chinese Academy of Sciences<br />
                  No.1 Beichen West Road<br />
                  Chaoyang District, Beijing 100101<br />
                  China
                </div>
            </div>
            <div class="section-1 wraper-info">
                <div class="section-titile">
                    Email
                </div>
                <div class="section-info">
                  hgd(AT)big.ac.cn<br />
                </div>
            </div>
            <div class="section-1 wraper-info">
                <div class="section-titile">
                    Telephone
                </div>
                <div class="section-info">
                  86-10-84097845<br />
                </div>
            </div>
            <div class="section-1 wraper-info">
                <div class="section-titile">
                    Fax
                </div>
                <div class="section-info">
                  86-10-84097298<br />
                </div>
            </div>
            <div class="section-1 wraper-info final">
                <div class="section-titile">
                    Map
                </div>
                <div class="section-info">
                 <img :src="map">
                </div>
            </div>
            </div>
            </div>
        </el-card>
    </div>
    <FooterBar/>
</div>
</template>
<style  src="../assets/css/documentation.css" scoped></style>
<style scoped>
.arrow-title{
    height: 24px;
    vertical-align: middle;
    padding-top: 15px;
    padding-left: 15px;
    font-weight: 700;
  }
  .wraper-info{
    /* margin-left: 2%; */
    margin-top: 1%;
  }
  .final{
    margin-bottom: 20px;
  }

</style>
<script>
import FooterBar from '@/components/FooterBar.vue';
import HeaderBar from '@/components/HeaderBar.vue';
import Banner from "@/components/banner.vue";
import map from "@/assets/img/map.jpg";
export default {
  name: 'Contact',
  components:{
    FooterBar,
    HeaderBar,
    Banner,
  },
  data(){
    return{map}
  }
}
  
</script>