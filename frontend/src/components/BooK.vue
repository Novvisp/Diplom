<template>
    <div>
      <button @click="downloadFile">Скачать статью</button>  
      <div>
        <h3>Оглавление</h3>
        {{ book.title ? book.title : 'Без оглавления' }}
      </div>
    <div>
      <h3>Журнал</h3>
      {{ book.journal }}
    </div>
    <div>
      <h3>Абстракция</h3>
      {{ book.abstrakt }}
    </div>
      <div>
        <h3>Авторы</h3>
      <div v-for="author in book.authors" :key=author> 
            <div>{{ author }}</div>       
      </div>
      </div>
       
      <div>
        <h3>Библиографические ссылки</h3>
         <button @click="toggleList">Показать список</button>
    <ol>
      <li v-show="isListVisible" class="list-container" v-for="reference in book.references" :key=reference> 
        
          <ul  v-for="(field, index) in reference.fields" :key="index">
            <li class="li">{{ field }}: {{ reference.specs[index] }}</li>
          </ul>
      </li>
    </ol>
       </div>
      <div>{{ book.time ? book.time.replace('T',' ').slice(0,19) : "time" }}</div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  export default {
    data() {
      return {
        book: {
                  id: null,
                  title: "",
                  journal: "",
                  abstrakt: "",
                  fileName: "", 
                  time: "",
                  authors: [],
                  references: []
              },
      isListVisible: false,
      };
    },
    computed: {
        BookId() {
            return this.$route.params.id
          },
    },
    mounted() {
      // Выполнить запрос к серверу для получения списка книг
      this.fetchA();
    },
    methods: {
      toggleList() {
      this.isListVisible = !this.isListVisible;
      },
      async fetchA() {
        try {
          const response = await axios.get("http://localhost:3030/api/get/"+ this.BookId);
          console.log(response);
          this.book = response.data;
        } catch (error) {
          console.error("Ошибка при загрузке данных:", error);
        }
      },
      downloadFile() {
      // Создайте ссылку для скачивания файла
      const downloadLink = document.createElement('a');
      downloadLink.href = `http://localhost:3030/api/download/${this.book.id}`;
      downloadLink.download = this.book.fileName;

      document.body.appendChild(downloadLink);
      downloadLink.click();
      document.body.removeChild(downloadLink);
    },
    }
  };
  </script>
  
  <style scoped>
   td {
    border:2px solid;
   }
   .li {
    text-align: left;
   }
   .list-container {
    margin-bottom: 20px;
     
   }
  </style>
  