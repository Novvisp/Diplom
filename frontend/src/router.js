import {createWebHistory, createRouter} from 'vue-router'
import BooksList from './components/BooksList.vue'
import Upload from './components/FileUploader.vue'
import Book from './components/BooK.vue'


const routes = [    
  // ... другие маршруты
  {
    path: '/books',
    name: 'BooksList',
    component: BooksList
  },
  {
    path: '/book/:id',
    name: 'Book',
    component: Book
  },
  {
    path: '/upload',
    name: 'Upload',
    component: Upload
  }
]

const router = createRouter({
  history: createWebHistory(), // указываем, что будет создаваться история посещений веб-страниц
  routes
})

export default router