const { Client } = require('pg');

const client = new Client({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
    port: 5432, // Default PostgreSQL port
});

// Connect to the database before the handler function is invoked
client.connect()
  .then(() => console.log('Connected to PostgreSQL database'))
  .catch(err => console.error('Error connecting to PostgreSQL database:', err));

exports.handler = async (event) => {
    const { title, description } = JSON.parse(event.body); // Datos enviados en la solicitud POST

    try {
        const sql = 'INSERT INTO todos (title, description) VALUES ($1, $2) RETURNING id';
        const res = await client.query(sql, [title, description]);

        return {
            statusCode: 200,
            body: JSON.stringify({ message: 'Tarea creada con éxito', taskId: res.rows[0].id }),
        };
    } catch (error) {
        return {
            statusCode: 500,
            body: JSON.stringify({ message: 'Error al insertar la tarea', error }),
        };
    } finally {
        await client.end(); // Close the database connection
    }
};
