<template>
  <div>
    <h2>Загрузка файлов</h2>
      <div id="app">
         <h3>{{ headerText }}</h3>
      </div>
    <input type="file" ref="fileInput" @change="handleFileChange" multiple />
    <button @click="submitFiles">Загрузить файлы</button>
    <div v-if="uploadedFiles.length > 0">
      <h3>Файлы:</h3>
      <ul>
        <li v-for="(file, index) in uploadedFiles" :key="index">{{ file.name }}</li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  el: '#app',
  data() {
    return {
      headerText : '',
      uploadedFiles: []
    };
    
  },
  methods: {
    handleFileChange(event) {
      // Обновляем массив загруженных файлов
      this.uploadedFiles = Array.from(event.target.files);
      
    },
    async submitFiles() {
      // Выполняем запрос на сервер для загрузки файлов
      try {
        const formData = new FormData();
        this.uploadedFiles.forEach((file) => {
          formData.append('file', file);
        });
        console.log("sdfsdfg");
        this.headerText = 'Загрузка файлов';
        const response = await axios.post("http://localhost:3030/api/upload", formData);
        console.log(response.data);
        this.headerText = 'Файлы загружены';
      } catch (error) {
        console.error("Ошибка при загрузке файлов:", error);
      }
    }
  }
};
</script>

<style scoped>
/* Ваши стили здесь */
</style>
