<template>
  <h1>
    {{msg}}
  </h1>
</template>

<script>
export default {
  name: 'HomeComponent',
  data() {
    return {
      msg: ''
    }
  },
  mounted() {
    fetch('/api/index/hello')
        .then((response) => response.text())
        .then((data) => {
          this.msg = data;
        })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
