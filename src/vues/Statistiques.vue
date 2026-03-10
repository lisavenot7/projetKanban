<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import Bandeau from "../components/BandeauAdmin.vue"

import StatsChart from '../components/StatsUtilisateur.vue'

import {ref,computed} from 'vue'

import usersData from '../bdd/users.json'

const nbComptes = computed(() => usersData.length)
const nbAdmin = computed(() => usersData.filter(user => user.isAdmin === 1).length)
const nbUtil = computed(() => usersData.filter(user => user.isAdmin === 0).length)
const nbAct = computed(() => usersData.filter(user => user.actif === 1).length)
const nbDes = computed(() => usersData.filter(user => user.actif === 0).length)


const nbTableauxUser = 5
const nbcolonneTableau = 3
const nbTacheTableau = 45
const nbTachesTermines = 20
const nbTachesRetard = 4
const nbTachesPrioritaire = 2
const nbTachesAssigné = 10
const nbUserTab = 4
const nbCommentaireTache = 7
</script>

<template>
  <Navbar />
  <div class="home">
    <br/><br/>
    <h2 class="titre">
      <svg width="40" height="40" viewBox="0 0 24 24" fill="white" xmlns="http://www.w3.org/2000/svg">
        <path d="M12 12c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm0 2c-3.33 0-10 1.67-10 5v3h20v-3c0-3.33-6.67-5-10-5z"/>
      </svg>
      Statistiques des utilisateurs
    </h2>
    <div class="stats-container">
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
    <h2 class="titre">
      <svg width="40" height="40" viewBox="0 0 24 24" fill="white" xmlns="http://www.w3.org/2000/svg">
        <path d="M12 12c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm0 2c-3.33 0-10 1.67-10 5v3h20v-3c0-3.33-6.67-5-10-5z"/>
      </svg>
      Statistiques des tableaux et des tâches (MOYENNE)
    </h2>
    <div class="stats-container">
      <div class="cards-wrapper-9">
       
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
          <div class="icon-circle"><h1>{{nbTachesPrioritaire}}</h1></div>
          <span>Tâches prioritaires par tableau</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbTachesAssigné}}</h1></div>
          <span>Tâches assigné par utilisateur et tableau</span>
        </div>
      </div>
    </div>
    
  </div>
</template>
