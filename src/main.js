import { createApp } from 'vue'
import App from './App.vue'
import router from './routes/route.js'

createApp(App)
    .use(router)
    .mount('#app')
