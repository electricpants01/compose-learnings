const { Client } = require('pg');

const client = new Client({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
    port: 5432,
    ssl: {
                rejectUnauthorized: false, // Optional: Set to true if you have the certificate.
            }
});

// Connect to the database before the handler function is invoked
client.connect()
  .then(() => console.log('Connected to PostgreSQL database'))
  .catch(err => console.error('Error connecting to PostgreSQL database:', err));

exports.handler = async (event) => {
    console.log("Received event:", JSON.stringify(event, null, 2));  // Log the entire event for debugging

    const taskId = event.queryStringParameters && event.queryStringParameters.id;
    let sql = 'SELECT * FROM todos';
    const params = [];

    if (taskId) {
        sql += ' WHERE id = $1';
        params.push(taskId);
    }

    try {
        console.log("Executing SQL:", sql, "with params:", params);  // Log the SQL query and parameters

        const res = await client.query(sql, params);

        // Log the result from the database query
        console.log("Query result:", res.rows);

        // Check if there are any results
        if (res.rows.length === 0) {
            return {
                statusCode: 404,
                body: JSON.stringify({ message: 'No tasks found' }),
            };
        }

        return {
            statusCode: 200,
            body: JSON.stringify(res.rows),
        };
    } catch (error) {
        console.error('Error executing query:', error);
        return {
            statusCode: 500,
            body: JSON.stringify({ message: 'Error al leer las tareas', error: error.message }),
        };
    }
};
