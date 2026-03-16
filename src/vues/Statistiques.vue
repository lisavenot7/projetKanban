<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import Bandeau from "../components/BandeauAdmin.vue"
import StatsChart from '../components/StatsUtilisateurs.vue'

import { ref,computed,onMounted } from 'vue'

const users = ref([])
const token = localStorage.getItem("jwtToken")

onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "0") {
    router.push("/private")
  }
  fetchUsers()
})

async function fetchUsers() {
  try {
    const response = await fetch("http://localhost:10056/comptes", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération utilisateurs", response.status)
      return
    }
    const res = await response.json()
    users.value = res.data

    nbComptes.value = users.value.length 
    nbAdmin.value = users.value.filter(user => user.cptIsAdmin === 1).length
    nbUtil.value = users.value.filter(user => user.cptIsAdmin === 0).length

  } catch (err) {
    console.error("Impossible de récupérer les utilisateurs", err)
  }
}

const nbComptes = ref('')
const nbAdmin = ref('')
const nbUtil = ref('')

import usersData from '../bdd/users.json'
const nbAct = computed(() => usersData.filter(user => user.actif === 1).length)
const nbDes = computed(() => usersData.filter(user => user.actif === 0).length)

const showUserStat = ref(false)
const toggleUserStat = () => {
  showUserStat.value = !showUserStat.value
}

const nbTableauxUser = 5
const nbcolonneTableau = 3
const nbTacheTableau = 45
const nbTachesTermines = 20
const nbTachesRetard = 4
const nbTachesPrioritaire = 2
const nbTachesAssigné = 10
const nbUserTab = 4
const nbCommentaireTache = 7

const showTabStat = ref(false)
const toggleTabStat = () => {
  showTabStat.value = !showTabStat.value
}
</script>

<template>
  <Navbar />
  <div class="home">
    <br/><br/>
    <h2 class="titre" @click="toggleUserStat">
      <svg width="64" height="64" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <circle cx="12" cy="8" r="4" stroke="currentColor" stroke-width="2"/>
        <path d="M4 20c0-4 4-6 8-6s8 2 8 6" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
      </svg>
      Statistiques des utilisateurs
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M6 9l6 6 6-6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </h2>
    <div v-if="showUserStat" class="stats-container">
      <div class="chart-wrapper">
        <StatsChart />
      </div>
      
      <div class="cards-wrapper">
       
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbAct}}</h1></div>
          <span>Comptes activés</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbComptes}}</h1></div>
          <span>Comptes</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbDes}}</h1></div>
          <span>Comptes désactivés</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbAdmin}}</h1></div>
          <span>Administrateurs</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbUtil}}</h1></div>
          <span>Utilisateurs</span>
        </div>
      </div>
    </div>


    <br/><br/>
    <h2 class="titre" @click="toggleTabStat">
      <svg width="64" height="64" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <rect x="3" y="4" width="18" height="16" rx="3" stroke="currentColor" stroke-width="2"/>
        <rect x="5" y="7" width="4" height="10" rx="1.5" fill="currentColor"/>
        <rect x="10" y="7" width="4" height="6" rx="1.5" fill="currentColor"/>
        <rect x="15" y="7" width="4" height="8" rx="1.5" fill="currentColor"/>
      </svg>
      Statistiques des tableaux et des tâches (MOYENNE)
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M6 9l6 6 6-6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </h2>
    <div v-if="showTabStat" class="stats-container">
      <div class="cards-wrapper-9">
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTachesPrioritaire}}</h1></div>
          <span>Tâches prioritaires par tableau</span>
        </div>
          <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTachesRetard}}</h1></div>
          <span>Tâches en retard par tableau</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTacheTableau}}</h1></div>
          <span>Tâches par tableau</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTachesTermines}}</h1></div>
          <span>Tâches terminées par tableau</span>
        </div>
       
        
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTachesAssigné}}</h1></div>
          <span>Tâches assigné par utilisateur et tableau</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTableauxUser}}</h1></div>
          <span>Tableaux par utilisateur</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbUserTab}}</h1></div>
          <span>Participants à un tableau</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbcolonneTableau}}</h1></div>
          <span>Colonnes par tableau</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbCommentaireTache}}</h1></div>
          <span>Commentaires par tâches</span>
        </div>
        
      </div>
    </div>
    <br/><br/>
  </div>
</template>
