const { Client } = require('pg');

let client;

const connectToDatabase = async () => {
    if (!client) {
        client = new Client({
            host: process.env.DB_HOST,
            user: process.env.DB_USER,
            password: process.env.DB_PASS,
            database: process.env.DB_NAME,
            port: 5432,
            ssl: {
                rejectUnauthorized: false, // Optional: Set to true if you have the certificate.
            },
        });

        try {
            await client.connect();
            console.log('Connected to PostgreSQL database');
        } catch (err) {
            console.error('Error connecting to PostgreSQL database:', err);
            throw new Error('Database connection failed');
        }
    }
};

exports.handler = async (event) => {
    await connectToDatabase(); // Ensure the database connection is established

    console.log("iniciando :D", event);

    // Check if the body exists and is valid JSON
    let title, description;
    try {
        title = event.title;
        description = event.description;

        if (!title || !description) {
            return {
                statusCode: 400,
                body: JSON.stringify({ message: 'Title and description are required' }),
            };
        }

        console.log("title", title);
        console.log("description", description);

        const sql = 'INSERT INTO todos (title, description) VALUES ($1, $2) RETURNING id';
        const res = await client.query(sql, [title, description]);

        return {
            statusCode: 201, // Use 201 for resource creation
            body: JSON.stringify({ message: 'Tarea creada con éxito', taskId: res.rows[0].id }),
        };
    } catch (error) {
        console.error('Error inserting task:', error); // Log the error details
        return {
            statusCode: 500,
            body: JSON.stringify({ message: 'Error al insertar la tarea', error: error.message }),
        };
    } finally {
        await client.end();
    }
};

const cleanup = async () => {
    if (client) {
        await client.end();
        client = null;
        console.log('Database connection closed');
    }
};
