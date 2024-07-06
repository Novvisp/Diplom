<template>
    <div id="app">
        <nav class="navbar navbar-expand-lg navbar-light bg-custom form-box">
            <div class="navbar-nav" id="navbarNav">
              <li class="nav-item">
                <router-link class="nav-link" to="/books">
                  <span class="navbar-brand mb-0 h1">Все статьи</span>
                </router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/upload">
                  <span class="navbar-brand mb-0 h1">Загрузить</span>
                </router-link>
              </li>

          </div>
        </nav>
    </div>
  </template>
  
  <script>
  export default {
        name: "NavBar"
      }
  </script>
  
  <style scoped>
    nav {
      margin-bottom: 30px;
    }
  </style>