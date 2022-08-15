<template>
    <div class="banner-box">
      <div style="float:left;background-color: white;border-radius: 50%;width: 105px;height: 105px;margin-top: 25px;margin-right: 20px;margin-left: 30px;">
       <img :src="logo"  style="width:100px;height:45px;margin-top: 31px;margin-left: 3px;" />
      </div>
      
      <div class="wrapper">
        <div class="module-title">Homologous Gene Database</div>
        <div class="subtitle">A data portal of homologous genes across multiple species</div>
      </div>
      <Navigator />
    </div>
    
</template>

<script>
import logo from "@/assets/img/icon/logo.png";
import Navigator from '@/components/navigator.vue';
export default {
  name: 'Banner',
  components: {
    Navigator
  },
  data(){
    return {logo
    }
  }

}
</script>
<style scoped>
.banner-box{
  background-image: url("../assets/img/banner-2.png");
  color: white;
  height: 200px;
  width: 100%;
  background-repeat: no-repeat;
  background-position: right;
  background-size: cover;
  padding: 0 !important;
  /* background-attachment: fixed; */
  position: relative;
  margin-top: -35px;
  display: table;
  /* opacity: 0; */
}
.module-title{
  text-align: left;
  font-weight: 700;
  padding-top:18px;
  color: #f2f2f2;
  font-size: 42px;
}
.subtitle{
  font-size: 20px;
  color: #fefefe;
  text-align: left;
}
.wrapper{
  margin-top: 25px;
  /* margin-left: 10px; */
}
</style>