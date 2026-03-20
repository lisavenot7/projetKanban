<script setup>
import { Doughnut } from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale
} from 'chart.js'

import { computed, ref, onMounted } from 'vue'

const props = defineProps({
  idUtilisateur: {
    type: Number,
    required: true
  }
})
ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale)


const nbTachesAssignéMoy = ref(0)
const nbTachesRetardMoy = ref(0)
const nbTachesTerminesMoy = ref(0)

const dataTache = ref ({
  labels: ['Moyenne de tâches terminées', 'Moyenne de tâches en retard','Moyenne de tâches en cours / A faire'],
  datasets: [
    {
      label: 'Tâches',
      backgroundColor: ['#4BC0C0', '#FF6384', '#FBD2BD'],
      data: [0,0,0]
    }
  ]
})

const taches=ref([])

async function fetchTaches() {
  try {
    const response = await fetch(`http://localhost:10056/taches`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération des taches", response.status)
      return
    }
    const data = await response.json()
    taches.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer les taches", err)
  }
}

const today = new Date();
today.setHours(0, 0, 0, 0); 

function tachesUser() {
  taches.value = taches.value.filter(
    tache => tache.compteId === props.idUtilisateur
  )
  nbTachesTerminesMoy.value = taches.value.filter(
    tache => tache.tchStatus === "Terminé"
  ).length
  nbTachesRetardMoy.value = taches.value.filter(
    tache => tache.tchStatus != "Terminé" && tache.tchDateLimite!=null && new Date(tache.tchDateLimite)< today
  ).length
  nbTachesAssignéMoy.value = taches.value.filter(
    tache => (tache.tchStatus === "En cours" || tache.tchStatus === "A faire") 
    && (tache.tchDateLimite===null || new Date(tache.tchDateLimite)>= today)
  ).length
  console.log(taches.value)
  console.log(nbTachesAssignéMoy.value)
  dataTache.value = {
  ...dataTache.value,
  datasets: [
    {
      ...dataTache.value.datasets[0],
      data: [
        nbTachesTerminesMoy.value,
        nbTachesRetardMoy.value,
        nbTachesAssignéMoy.value
      ]
    }
  ]
}
}


const token = localStorage.getItem("jwtToken")
onMounted(async () => {
  await fetchTaches()
  tachesUser()
})
</script>

<template>
  <div class="charts-container" style="display:flex; gap:50px;">
    <div style="width:350px; height:350px;">
      <h3>Gestion des tâches</h3>
      <Doughnut :data="dataTache" />
    </div>
  </div>
</template>