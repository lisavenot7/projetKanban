<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import Tableau from "../components/LesTableaux.vue"

import { ref, onMounted } from 'vue'
import { useRouter } from "vue-router"

const router = useRouter()

const tabs = ref([])
const search = ref('')
const filteredTabs = ref([])

async function fetchTabs(idUser) {
  try {
    const response = await fetch(`http://localhost:10056/comptes/${idUser}/tableaux`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération tableaux", response.status)
      return
    }
    const data = await response.json()
    tabs.value = data.data
    filteredTabs.value = tabs.value
  } catch (err) {
    console.error("Impossible de récupérer les tableaux", err)
  }
}

function searchTabs() {
  const query = search.value.toLowerCase()
  filteredTabs.value = tabs.value.filter(
    tab => tab.tabNom.toLowerCase().includes(query)
  )
}

function goToAjouter() {
  router.push(`/private/tableaux/ajouter`)
}

const token = localStorage.getItem("jwtToken")
const idUser = localStorage.getItem("cptId")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "1") {
    router.push("/admin")
  }
  fetchTabs(idUser)
})
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

