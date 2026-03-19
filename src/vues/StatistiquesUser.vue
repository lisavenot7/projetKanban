<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import Bandeau from "../components/BandeauAdmin.vue"

import StatsChart from '../components/StatsUtilisateur.vue'


import {ref,computed,onMounted} from 'vue'
import {useRoute,useRouter} from "vue-router"

const route = useRoute()
const router = useRouter()

const idUser = route.params.id
const user = ref('')

const nbTab = ref("")
const nbCom = ref("")
const nbPar = ref("")

async function fetchUser() {
  try {
    const response = await fetch(`http://localhost:10056/comptes/${idUser}`, {
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
    user.value = res.data
    nbPar.value =user.value.tableauIds.length
    nbTab.value =user.value.tableauxCrees.length  
    fetchCommentaires()
  } catch (err) {
    console.error("Impossible de récupérer les utilisateurs", err)
  }
}

async function fetchCommentaires() {
  try {
    const response = await fetch(`http://localhost:10056/comptes/${idUser}/commentaires`, {
  method: "GET",
  headers: {
    "Authorization": `Bearer ${token}`,
    "Content-Type": "application/json"
  },
  credentials: "include"
});
    if (!response.ok) {
      console.error("Erreur récupération commentaires", response.status)
      return
    }
    const res = await response.json()
    nbCom.value = res.data.length
  } catch (err) {
    console.error("Impossible de récupérer les commentaires", err)
  }
}

const token = localStorage.getItem("jwtToken")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "0") {
    router.push("/private")
  }
  fetchUser()
})
</script>

<template>
  <Navbar />
  <div v-if="user" class="home">
    <br/><br/>
    <h2 style="margin-left:5%;">
      Statistiques de l'utilisateur {{ user.nom }} {{ user.prenom }}
    </h2>
    <div class="stats-container">
      <div class="chart-wrapper">
        <StatsChart :idUtilisateur="user.cptId" />
      </div>
      
      <div class="cards-wrapper">
       
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTab}}</h1></div>
          <span>Tableaux créés</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbCom}}</h1></div>
          <span>Commentaire écrits</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbPar}}</h1></div>
          <span>Tableaux où il participe</span>
        </div>
        
      </div>
    </div>
  </div>
  <div v-else class="home">
  <br/><br/>
    <h2 style="margin-left:5%;">
      Aucun utilisateur trouvé
    </h2>
  </div>
</template>
