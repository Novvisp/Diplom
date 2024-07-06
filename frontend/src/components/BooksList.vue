<template>
    <div>
      <h2>Список</h2>
      <table>
          <tr>
            <th class="ogl">Название</th>
            <th class="time">Время</th>
          </tr>
          <tr v-for="book in books" :key="book.id" >
            
          <router-link class="nav-link" :to="{name: 'Book', params: {id: book.id}}">
            <td class="ogl">
              <div>
              {{ book.title ? book.title : 'Без оглавления' }}
              </div>
            </td>
          </router-link>
            <td class="time">{{ book.time ? book.time.replace('T',' ').slice(0,19) : "time" }}</td>
            
        </tr>
      </table>
      <h3>Статей в очереди на обработку {{ counter }}</h3>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  export default {
    data() {
      return {
        books: [],
        counter: null
      };
    },
    mounted() {
      // Выполнить запрос к серверу для получения списка книг
      this.fetchA();
      this.que();
    },
    methods: {
      async fetchA() {
        try {
          const response = await axios.get("http://localhost:3030/api/getAll");
          this.books = response.data.filter(book => book.time !== null);
        } catch (error) {
          console.error("Ошибка при загрузке данных:", error);
        }
      },
      async que() {
        try {
          const response = await axios.get("http://localhost:3030/api/queueSize");
          this.counter = response.data;
        } catch (error) {
          console.error("Ошибка при загрузке данных:", error);
        }
      }
    }
  };
  </script>
  
  <style scoped>
      table {
        width: 100%;
        border: 2px solid;
      }
      th {
        border: 2px solid;
      }
      tr {
        border: 2px solid;
      }
      .ogl {
        width: 9%;
        border:2px solid;
      }
      .time {
        width: 1%;
        border:2px solid;
      }
  </style>
  