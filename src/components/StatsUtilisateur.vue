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

const nbTachesAssignéMoy = 25
const nbTachesRetardMoy = 3
const nbTachesTerminesMoy = 10

const dataTache = computed(() => ({
  labels: ['Moyenne de tâches terminées par tableau', 'Moyenne de tâches en retard par tableau','Moyenne de tâches assignées par tableau'],
  datasets: [
    {
      label: 'Tâches',
      backgroundColor: ['#4BC0C0', '#FF6384', '#FBD2BD'],
      data: [nbTachesTerminesMoy,nbTachesRetardMoy, nbTachesAssignéMoy]
    }
  ]
}))


</script>

<template>
  <div class="charts-container" style="display:flex; gap:50px;">
    <div style="width:350px; height:350px;">
      <h3>Gestion des tâches</h3>
      <Doughnut :data="dataTache" />
    </div>
  </div>
</template>