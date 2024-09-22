const { Client } = require('pg');

const client = new Client({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
    port: 5432,
});

exports.handler = async (event) => {
    await client.connect();
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
        await client.end();
    }
};
