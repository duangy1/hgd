<template>
    <div class="banner-box">
      <h1 class="module-title">Homolog Gene Database</h1>
    </div>
</template>

<script>

export default {
  name: 'Banner',

}
</script>
<style scoped>
.banner-box{
  background-image: url("../assets/img/banner.jpg");
  color: white;
  height: 160px;
  width: 100%;
  background-repeat: no-repeat;
  background-position: right;
  background-size: cover;
  padding: 0 !important;
  /* background-attachment: fixed; */
  position: relative;
  margin-top: -22px;
}
.module-title{
  text-align: left;
  font-weight: 500;
  padding-top:3%;
  padding-left: 5%;
}
</style>