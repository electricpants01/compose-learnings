const { Client } = require('pg');

const client = new Client({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
    port: 5432,
});

// Connect to the database before the handler function is invoked
client.connect()
  .then(() => console.log('Connected to PostgreSQL database'))
  .catch(err => console.error('Error connecting to PostgreSQL database:', err));

exports.handler = async (event) => {
    // Use the existing client object for queries
    const taskId = event.queryStringParameters && event.queryStringParameters.id;
    let sql = 'SELECT * FROM todos';
    const params = [];

    if (taskId) {
        sql += ' WHERE id = $1';
        params.push(taskId);
    }

    try {
        const res = await client.query(sql, params);
        return {
            statusCode: 200,
            body: JSON.stringify(res.rows),
        };
    } catch (error) {
        return {
            statusCode: 500,
            body: JSON.stringify({ message: 'Error al leer las tareas', error }),
        };
    } finally {
        // Close the connection after the function is done
        await client.end();
    }
};