<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import Bandeau from "../components/BandeauAdmin.vue"

import {ref,computed} from 'vue'

import usersData from '../bdd/users.json'

const nbComptes = computed(() => usersData.length)

const nbAdmin = computed(() => usersData.filter(user => user.isAdmin === 1).length)

const nbUtil = computed(() => usersData.filter(user => user.isAdmin === 0).length)

const nbAct = computed(() => usersData.filter(user => user.actif === 1).length)

const nbDes = computed(() => usersData.filter(user => user.actif === 0).length)
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
    <div class="admin-stat">
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbAct}}</h1>
        </div>
        <span>Comptes activés</span>
      </div>
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbComptes}}</h1>
        </div>
        <span>Comptes </span>
      </div>
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbDes}}</h1>
        </div>
        <span>Comptes désactivés</span>
      </div>
    </div>
    <div class="admin-stat">
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbAdmin}}</h1>
        </div>
        <span>Comptes utilisateurs</span>
      </div>
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbUtil}}</h1>
        </div>
        <span>Comptes administrateur</span>
      </div>
    </div>
    <h2 class="titre">
      <svg width="40" height="40" viewBox="0 0 24 24" fill="white" xmlns="http://www.w3.org/2000/svg">
        <path d="M12 12c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm0 2c-3.33 0-10 1.67-10 5v3h20v-3c0-3.33-6.67-5-10-5z"/>
      </svg>
      Statistiques des utilisateurs
    </h2>
    <div class="admin-stat">
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbAct}}</h1>
        </div>
        <span>Comptes activés</span>
      </div>
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbComptes}}</h1>
        </div>
        <span>Comptes </span>
      </div>
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbDes}}</h1>
        </div>
        <span>Comptes désactivés</span>
      </div>
    </div>
    <div class="admin-stat">
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbAdmin}}</h1>
        </div>
        <span>Comptes utilisateurs</span>
      </div>
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbUtil}}</h1>
        </div>
        <span>Comptes administrateur</span>
      </div>
    </div>
    <h2 class="titre">
      <svg width="40" height="40" viewBox="0 0 24 24" fill="white" xmlns="http://www.w3.org/2000/svg">
        <path d="M12 12c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm0 2c-3.33 0-10 1.67-10 5v3h20v-3c0-3.33-6.67-5-10-5z"/>
      </svg>
      Statistiques des utilisateurs
    </h2>
    <div class="admin-stat">
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbAct}}</h1>
        </div>
        <span>Comptes activés</span>
      </div>
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbComptes}}</h1>
        </div>
        <span>Comptes </span>
      </div>
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbDes}}</h1>
        </div>
        <span>Comptes désactivés</span>
      </div>
    </div>
    <div class="admin-stat">
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbAdmin}}</h1>
        </div>
        <span>Comptes utilisateurs</span>
      </div>
      <div class="admin-card">
        <div class="icon-circle">
          <h1>{{nbUtil}}</h1>
        </div>
        <span>Comptes administrateur</span>
      </div>
    </div>
  </div>
</template>
