<script setup>
import { computed } from 'vue'
import { Doughnut } from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale
} from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale)

import usersData from '../bdd/users.json'

// Données camembert comptes actifs/désactivés
const actifCount = computed(() => usersData.filter(u => u.actif === 1).length)
const desactiveCount = computed(() => usersData.filter(u => u.actif === 0).length)

const dataActif = computed(() => ({
  labels: ['Activés', 'Désactivés'],
  datasets: [
    {
      label: 'Comptes',
      backgroundColor: ['#4BC0C0', '#FF6384'],
      data: [actifCount.value, desactiveCount.value]
    }
  ]
}))

// Données camembert admin/utilisateurs
const adminCount = computed(() => usersData.filter(u => u.isAdmin === 1).length)
const utilCount = computed(() => usersData.filter(u => u.isAdmin === 0).length)

const dataRole = computed(() => ({
  labels: ['Admin', 'Utilisateurs'],
  datasets: [
    {
      label: 'Comptes',
      backgroundColor: ['#FFCE56', '#36A2EB'],
      data: [adminCount.value, utilCount.value]
    }
  ]
}))
</script>

<template>
  <div class="charts-container" style="display:flex; gap:50px;">
    <div style="width:250px; height:250px;">
      <h3>Comptes activés vs Comptes désactivés</h3>
      <Doughnut :data="dataActif" />
    </div>

    <div style="width:250px; height:250px;">
      <h3>Administrateurs vs Utilisateurs</h3>
      <Doughnut :data="dataRole" />
    </div>
  </div>
</template>