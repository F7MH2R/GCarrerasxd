<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gráfico de Pastel: Proporción de Intereses por Carrera</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
    <style>
        .chart-container {
            width: 800px;
            height: 800px;
            margin: auto;
        }
        table {
            margin: 20px auto;
        }
        th {
            text-align: center;
            background-color: #f8f9fa;
        }
        td {
            text-align: center;
        }
    </style>
</head>
<body class="bg-light">
        <%@ include file="nav.jsp" %>

    <div class="container py-5">
        <h1 class="text-center mb-4">Gráfico de Pastel: Proporción de Intereses por Carrera</h1>
        
        <!-- Gráfico -->
        <div class="chart-container">
            <canvas id="graficoPastel"></canvas>
        </div>

        <!-- Tabla -->
        <div class="table-responsive">
            <table class="table table-bordered table-striped mt-4">
                <thead class="table-dark">
                    <tr>
                        <th>Carrera</th>
                        <th>Porcentaje (%)</th>
                    </tr>
                </thead>
                <tbody id="tablaPorcentajes">
                    <!-- Filas generadas dinámicamente -->
                </tbody>
            </table>
        </div>
    </div>

    <script>
        const nombresCarreras = JSON.parse('<%= request.getAttribute("nombresCarreras") %>');
        const valores = JSON.parse('<%= request.getAttribute("valores") %>');
        const porcentajes = JSON.parse('<%= request.getAttribute("porcentajes") %>');

        // Verificar en consola los datos
        console.log("Nombres de Carreras:", nombresCarreras);
        console.log("Valores (Cantidad de Intereses):", valores);
        console.log("Porcentajes:", porcentajes);

        const ctx = document.getElementById('graficoPastel').getContext('2d');

        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: nombresCarreras,
                datasets: [{
                    label: 'Porcentaje de Intereses',
                    data: valores,
                    backgroundColor: [
                        '#FF6384', '#36A2EB', '#FFCE56', '#8A2BE2', '#32CD32',
                        '#FFA500', '#DC143C', '#1E90FF', '#FFD700', '#00FA9A'
                    ]
                }]
            },
            options: {
                plugins: {
                    datalabels: {
                        color: '#ffffff',
                        formatter: (value, context) => {
                            const index = context.dataIndex;
                            const porcentaje = porcentajes[index].toFixed(2);
                            return `${porcentaje}% (${value})`;
                        },
                        font: {
                            size: 12,
                            weight: 'bold'
                        },
                        align: 'center',
                        anchor: 'center'
                    },
                    tooltip: {
                        callbacks: {
                            label: function (context) {
                                const porcentaje = porcentajes[context.dataIndex].toFixed(2);
                                return `${context.label}: ${porcentaje}% (${context.raw})`;
                            }
                        }
                    }
                }
            },
            plugins: [ChartDataLabels]
        });

        // Generar la tabla dinámicamente
        const tablaBody = document.getElementById('tablaPorcentajes');
        nombresCarreras.forEach((carrera, index) => {
            const row = document.createElement('tr');
            const cellCarrera = document.createElement('td');
            const cellPorcentaje = document.createElement('td');

            cellCarrera.textContent = carrera;
            cellPorcentaje.textContent = porcentajes[index].toFixed(2);

            row.appendChild(cellCarrera);
            row.appendChild(cellPorcentaje);
            tablaBody.appendChild(row);
        });
    </script>
</body>
</html>
