<template>
  <div id="app">  
    <navbar/>
    <router-view />
  </div>
</template>

<script>
  import NavBar from './components/NavBar'
  export default {
          name: 'App',
          components: {
              navbar: NavBar
          }
      }
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
