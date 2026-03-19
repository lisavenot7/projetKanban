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
  fetchTableaux()
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
    nbAct.value = users.value.filter(user => user.cptIsActive === 1).length
    nbDes.value = users.value.filter(user => user.cptIsActive === 0).length
    fetchTabUser()
    fetchParticipantsStats()
  } catch (err) {
    console.error("Impossible de récupérer les utilisateurs", err)
  }
}

async function fetchParticipantsStats() {
  try {
    let totalParticipants = 0
    for (const tab of tableaux.value) {
      const res = await fetch(`http://localhost:10056/tableaux/${tab.tabId}/participants`, {
        headers: { Authorization: `Bearer ${token}` }
      })
      if (!res.ok) {
        console.error("Erreur participants", res.status)
        continue
      }
      const data = await res.json()
      const nb = data.data.length
      totalParticipants += nb
    }
    nbUserTab.value = tableaux.value.length
      ? (totalParticipants / tableaux.value.length).toFixed(2)
      : 0

  } catch (err) {
    console.error("Erreur stats participants", err)
  }
}

async function fetchTableaux() {
  try {
    const response = await fetch("http://localhost:10056/tableaux", {
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
    const res = await response.json()
    tableaux.value = res.data
    nbTableaux.value = tableaux.value.length
    fetchTaches()
  } catch (err) {
    console.error("Impossible de récupérer les tableaux", err)
  }
}

async function fetchTaches() {
  try {
    const response = await fetch("http://localhost:10056/taches", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération tache", response.status)
      return
    }
    const res = await response.json()
    taches.value = res.data
    nbTaches.value = taches.value.length
    nbTacheTableau.value=(nbTaches.value/nbTableaux.value).toFixed(2)
  
  } catch (err) {
    console.error("Impossible de récupérer les taches", err)
  }
}

async function fetchTabUser() {
  try {
    let totalTableaux = 0
    tabParUser.value = []

    for (const user of users.value) {
      const [creesRes, participesRes] = await Promise.all([
        fetch(`http://localhost:10056/comptes/${user.cptId}/tableaux/crees`, {
          headers: { Authorization: `Bearer ${token}` }
        }),
        fetch(`http://localhost:10056/comptes/${user.cptId}/tableaux/participes`, {
          headers: { Authorization: `Bearer ${token}` }
        })
      ])

      const crees = await creesRes.json()
      const participes = await participesRes.json()

      const nb = crees.data.length + participes.data.length

      totalTableaux += nb

      tabParUser.value.push({
        userId: user.cptId,
        nbTableaux: nb
      })
    }

    // moyenne de tableaux par utilisateur
    nbTableauxUser.value = users.value.length
      ? (totalTableaux / users.value.length).toFixed(2)
      : 0

  } catch (err) {
    console.error("Erreur stats tableaux par user", err)
  }
}

const nbComptes = ref('')
const nbAdmin = ref('')
const nbUtil = ref('')
const nbAct = ref('')
const nbDes = ref('')

const showUserStat = ref(false)
const toggleUserStat = () => {
  showUserStat.value = !showUserStat.value
}

const tableaux = ref([])
const taches = ref([])

const tabParUser = ref([])

const nbTaches = ref("")
const nbTableaux = ref("")
const nbTableauxUser = ref(0)
const nbTacheTableau = ref("")
const nbUserTab = ref("")

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
      Statistiques des tableaux et des tâches
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M6 9l6 6 6-6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </h2>
    <div v-if="showTabStat" class="stats-container">
      <div class="cards-wrapper-9">
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTableaux}}</h1></div>
          <span>Nombre de tableaux</span>
        </div>
          <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTaches}}</h1></div>
          <span>Nombre de tâches</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTacheTableau}}</h1></div>
          <span>Moyenne de tâches par tableau </span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTableauxUser}}</h1></div>
          <span>moyenne de tableaux par utilisateur</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbUserTab}}</h1></div>
          <span>moyenne de participants à un tableau</span>
        </div>
        
      </div>
    </div>
    <br/><br/>
  </div>
</template>
