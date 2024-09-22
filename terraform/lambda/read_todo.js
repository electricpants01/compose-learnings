const { Client } = require('pg');

const client = new Client({
    host: "terraform-20240922181449343900000001.cx0iuaywg46s.us-east-1.rds.amazonaws.com",
    user: "todo_user",
    password: "locoto123",
    database: "todo_db",
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