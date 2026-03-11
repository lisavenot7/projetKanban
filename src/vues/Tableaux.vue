<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import Tableau from "../components/LesTableaux.vue"

import { ref, onMounted } from 'vue'
import { useRouter} from "vue-router"

const router = useRouter()
import tabsData from '../bdd/tableaux.json'

const tabs = ref([])
const search = ref('')
const filteredTabs = ref([])

const id = 1

function getUserTabs(userId) {
  return tabsData.filter(
    tab => tab.createur === userId || tab.participants.includes(userId)
  )
}

onMounted(() => {
  tabs.value = getUserTabs(id) 
  filteredTabs.value = tabs.value
})

function searchTabs() {
  const query = search.value.toLowerCase()
  filteredTabs.value = tabs.value.filter(
    tab => tab.titre.toLowerCase().includes(query)
  )
}

function goToAjouter() {
  router.push(`/private/tableaux/ajouter`)
}
</script>

<template>
  <Navbar />
  <div class="home">
    <br/><br/><br/>
    <div class="bandeau">
      <div class="bandeau-item">
        <input id="search" v-model="search" @keyup.enter="searchTabs" placeholder="Rechercher un tableau" />
        <button class="gestion" @click="searchTabs">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="#0F171E" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search">
            <circle cx="11" cy="11" r="8"/>
            <line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
        </button>
      </div>
      <div class="bandeau-item">
        <button class="ajouter" @click="goToAjouter">Ajouter un nouveau tableau</button>
      </div>
    </div>
    <div>
      <Tableau v-if="filteredTabs.length > 0" title="Vos tableaux" :tab="filteredTabs" />
      <p v-else style="color: white; font-size: 18px; padding: 20px 40px;">
        Aucun Tableau trouvé
      </p>
    </div>
  </div>
</template>

