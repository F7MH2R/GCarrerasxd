<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gráfico de Barras: Usuarios por Carrera</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .chart-container {
            width: 70%;
            margin: 20px auto;
        }
    </style>
</head>
<body>
        <%@ include file="nav.jsp" %>

    <h1 class="text-center">Gráfico de Barras: Usuarios por Carrera</h1>
    <div class="chart-container">
        <canvas id="graficoBarras"></canvas>
    </div>

    <script>
        const nombresCarreras = JSON.parse('<%= request.getAttribute("nombresCarreras") %>');
        const valores = JSON.parse('<%= request.getAttribute("valores") %>');

        // Verificar en consola los datos
        console.log("Nombres de Carreras:", nombresCarreras);
        console.log("Valores (Cantidad de Usuarios):", valores);

        const ctx = document.getElementById('graficoBarras').getContext('2d');

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: nombresCarreras,
                datasets: [{
                    label: 'Usuarios Interesados',
                    data: valores,
                    backgroundColor: [
                        '#FF6384', '#36A2EB', '#FFCE56', '#8A2BE2', '#32CD32',
                        '#FFA500', '#DC143C', '#1E90FF', '#FFD700', '#00FA9A'
                    ],
                    borderColor: '#000',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    },
                    tooltip: {
                        callbacks: {
                            label: function (context) {
                                return `${context.label}: ${context.raw}`;
                            }
                        }
                    }
                },
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Carreras'
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Número de Usuarios'
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>
